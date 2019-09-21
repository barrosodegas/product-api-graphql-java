
# CRUD API GraphQL with Spring Boot and Lombok example.

## Importante install Lombok plugin in your ide.

## URL to playground: http://localhost:4000/gui

### Querys (See Gui URL) or:

- query { productById(productId: 1) { name } } 
- query { productBySku(sku: 1) { name } } 
- query { products { productId name } }
- query { products { productId name inventory { warehouses { locality quantity } } } }

- mutation {
  createProduct(
    product: {
      sku: 12345678
      name: "Creme 2"
      inventory: {
        warehouses: [{ locality: "ES", quantity: 10, type: PHYSICAL_STORE }]
      }
    }
  ) {
    productId
    name
  }
}

- mutation {
  updateProduct (product: {
      sku: 12345678
      name: "Creme 6"
  })
}

- mutation {
  removeProductById(productId: 1)
}
