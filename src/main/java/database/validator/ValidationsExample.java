package database.validator;

import database.model.Products;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ValidationsExample {

    public void validaFormatoCamposProducts(List<Products> productsList) {
        for (Products product : productsList) {
            List<String> errors = validateProduct(product);
            Assert.assertTrue(errors.isEmpty(),"Invalid product: " + errors);
        }
    }

    public static List<String> validateProduct(Products product) {
        List<String> errors = new ArrayList<>();

        // Validar productCode
        if (product.getProductCode() == null || product.getProductCode().isEmpty()) {
            errors.add("productCode cannot be empty");
        } else if (product.getProductCode().length() > 15) {
            errors.add("productCode cannot be longer than 15 characters");
        }

        // Validar productName
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            errors.add("productName cannot be empty");
        }

        // Validar productLine
        if (product.getProductLine() == null || product.getProductLine().isEmpty()) {
            errors.add("productLine cannot be empty");
        }
         if (product.getQuantityInStock() < 0) {
           errors.add("quantityInStock cannot be negative");
         }
        return errors;
    }
}
