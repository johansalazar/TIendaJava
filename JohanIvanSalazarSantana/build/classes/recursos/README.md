# PRUEBA TÉCNICA — Tienda (Java Swing + SQL Server)

Este proyecto cumple los requerimientos: 
- vender productos.
- generar pedidos al alcanzar stock mínimo.
- mostrar estadísticas (más/menos vendido, ingresos y promedio).

## Requisitos
- Java 17
- Maven 3.9+
- SQL Server 2019+

## Instalación
1. Crear BD `Tienda` en SQL Server y ejecutar `schema.sql`.
2. Editar `src\johanivansalazarsantana\dao\DB.java` con credenciales y URL.
3. Compilar y ejecutar:
```


java -jar target/tienda-swing-sqlserver-1.0.0.jar
```

## Notas
- La UI replica el storyboard con tabla de productos, botones **Vender Producto** y **Pedir Producto**, y panel **Cálculos**.
- IVA se almacena como `tax_rate`, y el **precio final** se calcula como `base_price * (1 + tax_rate)`.
- Los pedidos se registran en la tabla `[Order]` y aumentan `stock` en `reorder_qty`.
- Las ventas se registran en `Sale` y actualizan estadísticas en tiempo real.
