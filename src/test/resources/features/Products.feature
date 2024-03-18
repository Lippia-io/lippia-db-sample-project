Feature: Database CRUD

  Scenario Outline: Obtener un producto por código
    Given el sistema retorna los productos con codigo <codigo>
    Then se valida el formato de los registros procesados

    Examples:
      | codigo   |
      | S10_1678 |

  Scenario Outline: Actualizar la descripción de un producto
    Given se actualiza la descripción <nuevaDescripcion> del producto con codigo <codigo>
    Then el sistema retorna el producto con la descripción actualizada <nuevaDescripcion>

    Examples:
      | codigo   | nuevaDescripcion                |
      | S10_1678 | Laptop Gamer XYZ, nueva versión |

  Scenario Outline: Eliminación de Producto
    Given se elimina el producto con <productCode>
    Then se verifica que el producto con <productCode> no exista en la base de datos

    Examples:
      | productCode |
      | S10_1678    |

  Scenario Outline: Alta de un Producto
    Given se agrega un nuevo producto con los datos <productCode>,<productName>,<productLine>,<productScale>,<productVendor>,<productDescription>,<quantityInStock>,<buyPrice>,<MSRP>
    Then se verifica que el producto con <productCode> exista en la base de datos

    Examples:
      | productCode | productName                           | productLine | productScale | productVendor   | productDescription                      | quantityInStock | buyPrice | MSRP  |
      | S10_1799    | 1969 Harley Davidson Ultimate Chopper | Motorcycles | 1:10         | Min Lin Diecast | This replica features working kickstand | 7933            | 48.81    | 95.70 |
