SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM products WHERE productCode = ?;

-- Volver a activar restricciones de clave externa
SET FOREIGN_KEY_CHECKS = 1;