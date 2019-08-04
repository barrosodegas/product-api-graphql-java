package br.com.barroso.productapi.service;

import br.com.barroso.productapi.exception.ProductAlreadyException;
import br.com.barroso.productapi.exception.ProductNotFoundException;
import br.com.barroso.productapi.model.Inventory;
import br.com.barroso.productapi.model.Product;
import br.com.barroso.productapi.model.Warehouse;
import br.com.barroso.productapi.repository.ProductRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class of service responsible for processing customer requisitions by searching,
 * creating, updating and removing products in the system.
 *
 * @author Andre Barroso
 *
 */
@Service
@GraphQLApi
public class ProductService {

    /**
     * Logger of service.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;

    /**
     * Method responsible for fetching a product through the sku.
     *
     * @param productId Product ID
     * @return Entity base.
     */
    @GraphQLQuery(name = "productById")
    public Product findProductById(Integer productId) {

        logger.info("Searching the product with the ID: " + productId);

        Optional<Product> opEntityBase = this.repository.findById(productId);

        opEntityBase.ifPresent( p -> logger.info("Product " + p.getProductId() + " found!") );

        opEntityBase.orElseThrow(ProductNotFoundException::new);

        return opEntityBase.get();
    }

    /**
     * Method responsible for fetching a product through the sku.
     *
     * @param sku Product sku
     * @return Entity base.
     */
    @GraphQLQuery(name = "productBySku")
    public Product findProductBySku(Integer sku) {

        logger.info("Searching the product with the SKU: " + sku);

        Optional<Product> opEntityBase = this.repository.findBySku(sku);

        opEntityBase.ifPresent( p -> logger.info("Product " + p.getSku() + " found!") );

        opEntityBase.orElseThrow(ProductNotFoundException::new);

        return opEntityBase.get();
    }

    /**
     * Method responsible for find all products.
     *
     * @return Product list
     */
    @GraphQLQuery(name = "products")
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    /**
     * Method responsible for creating a new product.
     *
     * @param product New Product
     * @return Entity base
     */
    @GraphQLMutation(name = "createProduct")
    public Product createProduct(Product product) {

        Optional<Product> opEntityBase = this.repository.findBySku(product.getSku());

        opEntityBase.ifPresent( p -> {
            logger.error("Product " + p.getSku() + " already registered!");
            throw new ProductAlreadyException("Product already registered!");
        });

        logger.info("Creating the product with the SKU: " + product.getSku());

        this.repository.save(product);

        logger.info("Product " + product.getSku() + " created!");

        return product;
    }

    /**
     * Method responsible for updating a product.
     *
     * @param product Entity base
     * @return Result.
     */
    @GraphQLMutation(name = "updateProduct")
    public boolean updateProduct(Product product) {

        Optional<Product> opEntityBase = this.repository.findBySku(product.getSku());

        if( !opEntityBase.isPresent() ) {
            logger.error("Product " + product.getSku() + " not found!");
            throw new ProductNotFoundException("Product not found to update!");
        }

        logger.info("Updating the product with the SKU: " + product.getSku());

        this.prepareProductToUpdate(opEntityBase, product);

        this.repository.save(opEntityBase.get());

        logger.info("Product " + product.getSku() + " updated!");

        return true;
    }

    /**
     * Method responsible for remove a product by ID.
     *
     * @param productId Product sku
     * @return Result.
     */
    @GraphQLMutation(name = "removeProductById")
    public boolean removeProductById(Integer productId) {

        Optional<Product> opEntityBase = this.repository.findById(productId);

        opEntityBase.ifPresent( p -> {

            logger.info("Removing the product with the SKU: " + p.getProductId());
            this.repository.delete(p);
            logger.info("Product " + p.getProductId() + " removed!");
        });

        if(!opEntityBase.isPresent()) {
            logger.error("Product " + productId + " not found!");
            throw new ProductNotFoundException("Product not found to delete!");
        }
        return true;
    }

    /**
     * Method responsible for remove a product by sku.
     *
     * @param sku Product sku
     * @return Result.
     */
    @GraphQLMutation(name = "removeProductBySku")
    public boolean removeProductBySku(Integer sku) {

        Optional<Product> opEntityBase = this.repository.findBySku(sku);

        opEntityBase.ifPresent( p -> {

            logger.info("Removing the product with the SKU: " + p.getSku());
            this.repository.delete(p);
            logger.info("Product " + p.getSku() + " removed!");
        });

        if(!opEntityBase.isPresent()) {
            logger.error("Product " + sku + " not found!");
            throw new ProductNotFoundException("Product not found to delete!");
        }
        return true;
    }

    /**
     * Method responsible for preparing a product to be updated.
     *
     * @param opEntityBase Entity base
     * @param product Produt to update
     */
    private void prepareProductToUpdate(Optional<Product> opEntityBase, Product product) {

        opEntityBase.ifPresent( p -> {
            p.setName(product.getName());
        });

        // If collection is empty return.
        if(product.getInventory() == null || product.getInventory().getWarehouses() == null || product.getInventory().getWarehouses().isEmpty()) {
            return;
        }

        opEntityBase.ifPresent( p -> {
            p.setName(product.getName());

            // If entity base collection is empty input new collection
            if(p.getInventory() == null || p.getInventory().getWarehouses() == null || p.getInventory().getWarehouses().isEmpty()) {

                if(p.getInventory() == null) {
                    p.setInventory(new Inventory());
                }
                p.getInventory().setWarehouses(product.getInventory().getWarehouses());
            } else {

                // Update if WH exists or insert.
                product.getInventory().getWarehouses().forEach( pWh -> {

                    Optional<Warehouse> wh = p.getInventory().getWarehouses().stream().filter(eWh -> {

                        return eWh.getLocality() != null && eWh.getLocality().equalsIgnoreCase(pWh.getLocality())
                                && eWh.getType() != null && eWh.getType().equals(pWh.getType());
                    }).findFirst();

                    wh.ifPresent( w -> w.setQuantity(pWh.getQuantity()) );

                    if(!wh.isPresent()) {
                        p.getInventory().getWarehouses().add(pWh);
                    }
                });
            }
        });
    }

}
