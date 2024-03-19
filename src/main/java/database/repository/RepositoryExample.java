package database.repository;

import database.util.DatabaseUtils;
import database.util.SqlFileReader;
import database.model.Products;
import org.testng.Assert;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryExample {

    List<Products> productsList = new ArrayList<>();
    Products product = new Products();

    public List<Products> selectProducts(String code) throws IOException, SQLException {
        String query = SqlFileReader.getQueryString("products.sql");
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, code);
        ResultSet resultSet = (ResultSet) DatabaseUtils.executeQueryWithParameters(query, parameters);
        while (resultSet.next()) {
            Products product = new Products();
            product.setProductCode(resultSet.getString("productCode"));
            product.setProductName(resultSet.getString("productName"));
            product.setProductLine(resultSet.getString("productLine"));
            product.setProductDescription(resultSet.getString("productDescription"));
            product.setQuantityInStock(resultSet.getInt("quantityInStock"));
            productsList.add(product);
            System.out.println("Producto seleccionado: " + product.getProductDescription());
            System.out.println("-------------------------");
        }

        return productsList;
    }

    public Products selectProduct(String code) throws IOException, SQLException {
        String query = SqlFileReader.getQueryString("products.sql");
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, code);
        ResultSet resultSet = (ResultSet) DatabaseUtils.executeQueryWithParameters(query, parameters);
        boolean productFound = false;
        while (resultSet.next()) {
            product.setProductCode(resultSet.getString("productCode"));
            product.setProductName(resultSet.getString("productName"));
            product.setProductLine(resultSet.getString("productLine"));
            product.setProductDescription(resultSet.getString("productDescription"));
            product.setQuantityInStock(resultSet.getInt("quantityInStock"));
        }
        System.out.println("Producto seleccionado: " + product.getProductDescription());
        System.out.println("-------------------------");
        return product;
    }

    public void updateProduct(String text, String code) throws IOException, SQLException {
        String query = SqlFileReader.getQueryString("updateProduct.sql");
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, text);
        parameters.put(2, code);
        boolean resultSet = (boolean) DatabaseUtils.executeQueryWithParameters(query, parameters);
        if (resultSet) {
            System.out.println("Actualizacion exitosa");
        } else {
            Assert.fail("Error al actualizar");
        }
    }

    public void deleteProduct(String code) throws IOException, SQLException {
        String query = SqlFileReader.getQueryString("deleteProduct.sql");
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, code);
        boolean resultSet = (boolean) DatabaseUtils.executeQueryWithParameters(query, parameters);
        if (resultSet) {
            System.out.println("Eliminacion exitosa");
        } else {
            Assert.fail("Error al eliminar");
        }
    }

    public void insertProduct(String code,String name,String line,String productScale,String vendedor,String description,String quantityInStock,String buyPrice,String MSRP) throws IOException, SQLException {
        String query = SqlFileReader.getQueryString("insertProduct.sql");
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, code);
        parameters.put(2, name);
        parameters.put(3, line);
        parameters.put(4, productScale);
        parameters.put(5, vendedor);
        parameters.put(6, description);
        parameters.put(7, quantityInStock);
        parameters.put(8, buyPrice);
        parameters.put(9, MSRP);
        boolean resultSet = (boolean) DatabaseUtils.executeQueryWithParameters(query, parameters);
        if (resultSet) {
            System.out.println("Alta exitosa");
        } else {
            Assert.fail("Error al insertar");
        }
    }
}
