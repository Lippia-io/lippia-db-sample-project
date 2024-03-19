package steps;

import com.crowdar.core.PageSteps;
import database.service.ServiceExample;
import database.validator.ValidationsExample;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import database.model.Products;
import org.testng.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class exampleSteps extends PageSteps {
    ServiceExample serviceExample = new ServiceExample();

    ValidationsExample validarProducts = new ValidationsExample();
    List<Products> productsList = new ArrayList<>();

    Products product = new Products();

    @Given("el sistema retorna los productos con codigo (.*)")
    public void elSistemaRetornaLosProductosConCodigoCodigo(String codigo) throws SQLException, IOException {
            productsList = serviceExample.selectProducts(codigo);
    }

    @Then("se valida el formato de los registros procesados")
    public void seValidaElFormatoDeLosRegistrosProcesados() {
        validarProducts.validaFormatoCamposProducts(productsList);
    }


    @Given("se actualiza la descripción (.*) del producto con codigo (.*)")
    public void seActualizaLaDescripciónNuevaDescripcionDelProductoConCodigoCodigo(String descripcion, String code) throws SQLException, IOException {
        serviceExample.updateProductDescrip(descripcion, code);
        product = serviceExample.selectProduct(code);
    }


    @Then("el sistema retorna el producto con la descripción actualizada (.*)")
    public void elSistemaRetornaElProductoConLaDescripciónActualizadaNuevaDescripcion(String nuevaDescripcion) {
        Assert.assertEquals(product.getProductDescription(), nuevaDescripcion);
    }

    @Given("se elimina el producto con (.*)")
    public void seEliminaElProductoConProductCode(String code) throws SQLException, IOException {
        serviceExample.deletedProduct(code);
    }

    @Then("se verifica que el producto con (.*) no exista en la base de datos")
    public void seVerificaQueElProductoConProductCodeNoExistaEnLaBaseDeDatos(String code) throws SQLException, IOException {
        product = serviceExample.selectProduct(code);
        Assert.assertNull(product);
    }

    @Given("se agrega un nuevo producto con los datos (.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*)")
    public void seAgregaUnNuevoProductoConLosDatos(String code,String name,String line,String productScale,String vendedor,String description,String quantityInStock,String buyPrice,String MSRP) throws SQLException, IOException {
        serviceExample.insertProduct(code, name, line, productScale,vendedor, description,quantityInStock,buyPrice,MSRP);
    }

    @Then("se verifica que el producto con (.*) exista en la base de datos")
    public void seVerificaQueElProductoConProductCodeExistaEnLaBaseDeDatos(String code) throws SQLException, IOException {
        product = serviceExample.selectProduct(code);
        Assert.assertNotNull(product);
    }
}
