package database.service;

import database.repository.RepositoryExample;
import model.Products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ServiceExample {

    RepositoryExample repositoryExampleProducts = new RepositoryExample();

    public List<Products> selectProducts(String code) throws SQLException, IOException {
        return repositoryExampleProducts.selectProducts(code);
    }

    public void updateProductDescrip(String text, String code) throws SQLException, IOException {
         repositoryExampleProducts.updateProduct(text,code);
    }

    public Products selectProduct(String code) throws SQLException, IOException {
        return repositoryExampleProducts.selectProduct(code);
    }

    public void deletedProduct(String code) throws SQLException, IOException {
        repositoryExampleProducts.deleteProduct(code);
    }

    public void insertProduct(String code,String name,String line,String productScale,String vendedor,String description,String quantityInStock,String buyPrice,String MSRP) throws SQLException, IOException {
        repositoryExampleProducts.insertProduct(code, name, line, productScale,vendedor, description,quantityInStock,buyPrice,MSRP);
    }

}
