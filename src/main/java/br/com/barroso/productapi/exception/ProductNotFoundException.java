package br.com.barroso.productapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * Class responsible for throwing exceptions stating that the product not found.
 *
 * @author Andre Barroso
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException implements Serializable {

    /**
     * UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ProductNotFoundException() {
        super("Product not found!");
    }

    /**
     * The constructor.
     * @param message The message error.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

}
