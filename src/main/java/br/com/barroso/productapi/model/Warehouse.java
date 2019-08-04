package br.com.barroso.productapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Class that represents the WareHouse entity.
 *
 * @author Andre Barroso
 *
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Warehouse implements Serializable {

    /**
     * UID.
     */
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * Inventory ID.
     */
    @Id
    @Column(name = "warehouse_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wareHouseId;

    /**
     * The WareHouse locality.
     */
    @NotEmpty(message = "Field: Warehouse --> locality is mandatory!")
    @Column(nullable = false)
    private String locality;

    /**
     * The WareHouse quantity.
     */
    @NotNull(message = "Field: Warehouse --> quantity is mandatory!")
    @Column(nullable = false)
    private Integer quantity;

    /**
     * The WareHouse enum type.
     */
    @NotNull(message = "Field: Warehouse --> type is mandatory!")
    @Column(nullable = false)
    private WareHouseTypeEnum type;
}
