package br.com.barroso.productapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * Class that represents the product Inventory entity.
 *
 * @author Andre Barroso
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventory implements Serializable {

    /**
     * UID.
     */
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * Inventory ID.
     */
    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer inventoryId;

    /**
     * Ware houses list.
     */
    @Valid
    @NotEmpty(message = "Field: Inventory --> wareHouses is mandatory!")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "inventory_id")
    private List<Warehouse> warehouses;

}
