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
### Setting Up the Database

Firstly, you need to create the database in Docker. To do this, execute the following commands:

	docker build . -t mysql:0.0.0 --no-cache
	docker run -td -p 3306:3306 --name mysql mysql:0.0.0

These commands will create an image for MySQL and set up the database. The `-td` flag ensures the container runs in the background. Port `3306` is exposed and mapped to the host, allowing you to connect to the MySQL instance from your local machine.
The necessary files to set up the MySQL database are located in the **dockerDatabase** folder

Once the container is running, you can proceed with running your tests that interact with this MySQL database. Make sure your application configuration points to `localhost:3306` for the database host and use the credentials you set during the Docker container setup.

### Running Tests

Now that the database is set up and running, you can execute your tests. Ensure your test configuration points to the correct database host, port, username, and password.

    `$ mvn clean test`
+ Additionally, other options are available for running the tests, as outlined in the following table: <a id='table_mvn'>maven commands</a>
   ```
     * -D is used to define system properties or command-line properties, which Maven will utilize during the project's building and/or execution process.
     * Using -P followed by the profile name allows Maven to apply the configurations associated with that specific profile during the project's build process.
     * -Pparallel: indicates the profile that enables the opening of multiple execution threads.
          
             |                             Command                               |                    Description                       |
             |-------------------------------------------------------------------|------------------------------------------------------|
             | clean test -DforkCount=0                                          | In case you need to debug, for use in the IDE runner |
             | mvn clean test -DforkCount=0 "-Dcucumber.options=--tags @Demo"    | Specifying a tag and including the debug option      |
             | mvn clean test  "-Dcucumber.options=--tags '@Demo and @DB'"       | Multiple tags                                        |
            
   
   ```

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
│   │   │       ├── models
│   │   │       └── validator
│   │   |
│   │   └── report
│   └── test
│       ├── java
│       │   └── database
│       │       └── hooks
│       │   └── steps
│       └── resources
│           ├── features
│           └── queries
└── pom.xml
```

Folder's description:

| Path                                         | Description                                                                             |
|----------------------------------------------|-----------------------------------------------------------------------------------------|
| main/java/.../database/**services**/*.java   | Folder with all the **Services** matching steps with java code                          |
| main/java/.../database/**repository**/*.java | Folder with all the ****repository**** class to interact with the database.             |
| main/java/.../database/**model**/.java       | Folder with all the ****model**** class to model entities.                              |
| main/java/.../database/validator             | Folder for data validation.                                                             |
| test/resources/queries                       | Folder for all the SQL queries used in this project to interact with the database.      |
| test/java/.../steps/*Steps.java              | Folder with all the **steps** which match with Gherkin Test Scenarios                   |
| test/resources/features/*.feature            | Folder with all the **feature files** containing **Test Scenarios** and **Sample Data** |

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