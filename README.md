# Lippia DB Sample Project Documentation

## Purpose:
The "Lippia DB Sample Project" aims to demonstrate how to work with a relational database in a Java project using the Lippia Framework. Through Gherkin scenarios, basic operations like retrieving, updating, inserting, and deleting products in a database are showcased.

## System Requirements:
-   JDK: [https://docs.oracle.com/en/java/javase/index.html](https://docs.oracle.com/en/java/javase/index.html)
-   Maven: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
-   Git client: [https://www.atlassian.com/git/tutorials/install-git](https://www.atlassian.com/git/tutorials/install-git)
-   Docker 18.09+: [https://docs.docker.com/install/linux/docker-ce/ubuntu/](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
-   Docker compose 1.24+: [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

# Getting started

`$ mvn clean test`

## Project structure
A typical Lippia Test Automation project usually looks like this
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── Database
│   │   │       ├── service
│   │   │       ├── repository
│   │   │       ├── util
│   │   │       └── validator
│   │   ├── models
│   │   └── report
│   └── test
│       ├── java
│       │   └── database
│       │       └── hooks
│       │       └── steps
│       └── resources
│           ├── features
│           └── queries
└── pom.xml
```

Folder's description:

|Path   |Description     |
|-------|----------------|
|main\java\...\examples\services\*.java  | Folder with all the **Services** matching steps with java code |
|main\java\...\examples\steps\*Steps.java  | Folder with all the **steps** which match with Gherkin Test Scenarios |
|test\resources\features\*.feature  | Folder with all the **feature files** containing **Test Scenarios** and **Sample Data** |
|main\java\...\examples\**repository**\*.java  | Folder with all the ****repository**** class to interact with the database. |
|main\java\...\examples\**model**\*.java  | Folder with all the ****model**** class to model entities. |
|main\resources|Configuration files and SQL queries.|
|src/test/java/validator|Folder for data validation.|

## Database Configuration
The database connection is configured using the following properties in the `pom.xml`:

	<crwdr.db.host>localhost</crwdr.db.host>
	<crwdr.db.port>3306</crwdr.db.port>
	<crwdr.db.user>root</crwdr.db.user>
	<crwdr.db.password>5656</crwdr.db.password>
	<crwdr.db.name>databasePrueba</crwdr.db.name>

## Feature File
The Test Scenarios can be written using BDD metodology. This project includes Cucumber as BDD interpreter which is supported by Lippia by default. On each declared step you can insert the calls defined from service classes

	Feature: Database CRUD  
	  Scenario Outline: Obtener un producto por código  
	    Given el sistema retorna los productos con codigo "codigo"  
	    Then se valida el formato de los registros procesados  
	    Examples:  
	      | codigo |  
	      | S10_1678 |

## Step Object
In the steps files we connect the gherkin with java code, and here is the connection with the services, in these classes there should not be logic

	@Given("el sistema retorna los productos con codigo (.*)")  
	public void elSistemaRetornaLosProductosConCodigoCodigo(String codigo) throws SQLException, IOException {  
	        productsList = serviceExample.selectProducts(codigo);  
	}  

	@Then("se valida el formato de los registros procesados")  
	public void seValidaElFormatoDeLosRegistrosProcesados() {  
	    validarProducts.validaFormatoCamposProducts(productsList);  
	}

## Services Class
The `serviceExample` class contains methods to perform operations in the database:

	public class ServiceExample {  
 
    repositoryExampleProducts repositoryExampleProducts = new repositoryExampleProducts();  
  
    public List<Products> selectProducts(String code) throws SQLException, IOException {  
        return repositoryExampleProducts.selectProducts(code);  
    }  
  
    public void updateProductDescrip(String text, String code) throws SQLException, IOException {  
         repositoryExampleProducts.updateProduct(text,code);  
    }  
  
    public Products selectProduct(String code) throws SQLException, IOException {  
        return repositoryExampleProducts.selectProduct(code);

## Repository Class
The `repositoryExample` class implements SQL 			queries to interact with the database:

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

##  Validation Class
The `validation` class contains methods to validate product data:

	public class ValidationsExample {  
  
    public void validaFormatoCamposProducts(List<Products> productsList) {  
        for (Products product : productsList) {  
            List<String> errors = validateProduct(product);  
            Assert.assertTrue(errors.isEmpty(),"Producto inválido: " + errors);  
        }  
    }  
  
    public static List<String> validateProduct(Products product) {  
        List<String> errors = new ArrayList<>();  
  
	  if (product.getProductCode() == null || product.getProductCode().isEmpty()) {  
	            errors.add("productCode cannot be empty");  
	        } else if (product.getProductCode().length() > 15) {  
	            errors.add("productCode cannot be longer than 15 characters");  
	        }  
	  
	  if (product.getProductName() == null || product.getProductName().isEmpty()) {  
	            errors.add("productName cannot be empty");       }
	  }

### testng.xml
	<?xml version="1.0" encoding="UTF-8"?>  
	<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">  
	<suite name="BDD Test Suite" verbose="1" parallel="tests" thread-count="1" configfailurepolicy="continue">  
	    <test name="Test database Scenario" annotations="JDK" preserve-order="true">  
	        <classes>  
	            <class name="DataBaseRunner"/>  
	        </classes>  
	    </test>  
	</suite>