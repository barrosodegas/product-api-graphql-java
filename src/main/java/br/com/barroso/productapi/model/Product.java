package br.com.barroso.productapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements Serializable {

    /**
     * UID.
     */
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * Product ID.
     */
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    /**
     * Product SKU.
     */
    @NotNull(message = "Field: Product --> sku is mandatory!")
    @Min(value = 1L, message = "FIeld: Product --> sku must be a positive number and greater than zero!")
    @Column(nullable = false, unique = true)
    private Integer sku;

    /**
     * Product name.
     */
    @NotEmpty(message = "Field: Product --> name is mandatory!")
    @Column(nullable = false)
    private String name;

    /**
     * Product inventory.
     */
    @Valid
    @NotNull(message = "Field: Product --> inventory is mandatory!")
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, optional = false)
    @JoinColumns({@JoinColumn( name = "inventory_id", referencedColumnName = "inventory_id")})
    private Inventory inventory;

}
