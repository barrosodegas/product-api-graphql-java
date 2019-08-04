package br.com.barroso.productapi.repository;

import br.com.barroso.productapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class ProductRepository.
 * @author Andre Barroso
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Method responsible to find product by code.
     * @param productId Product ID.
     * @return Product.
     */
    @Query("SELECT p FROM Product p WHERE p.productId=:productId")
    public Optional<Product> findById(@Param("productId") Integer productId);

    /**
     * Method responsible to find product by code.
     * @param sku Product Code.
     * @return Product.
     */
    @Query("SELECT p FROM Product p WHERE p.sku=:sku")
    public Optional<Product> findBySku(@Param("sku") Integer sku);
}
