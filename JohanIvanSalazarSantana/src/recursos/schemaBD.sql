/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  JohanIvánSalazarSant
 * Created: 1/09/2025
 */

CREATE DATABASE tienda;
GO

USE tienda;
GO

IF OBJECT_ID('dbo.Sale', 'U') IS NOT NULL DROP TABLE dbo.Sale;
IF OBJECT_ID('dbo.[Order]', 'U') IS NOT NULL DROP TABLE dbo.[Order];
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE dbo.Product;
GO

CREATE TABLE dbo.Product(
  id INT IDENTITY PRIMARY KEY,
  name NVARCHAR(100) NOT NULL,
  type NVARCHAR(20) NOT NULL CHECK (type IN ('papeleria','supermercado','drogueria')),
  stock INT NOT NULL,
  min_stock INT NOT NULL,
  base_price DECIMAL(10,2) NOT NULL,
  tax_rate DECIMAL(5,2) NOT NULL,
  reorder_qty INT NOT NULL DEFAULT 20
);
GO

CREATE TABLE dbo.Sale(
  id INT IDENTITY PRIMARY KEY,
  product_id INT NOT NULL FOREIGN KEY REFERENCES dbo.Product(id),
  quantity INT NOT NULL,
  unit_final_price DECIMAL(10,2) NOT NULL,
  total_value AS (quantity * unit_final_price) PERSISTED,
  sale_date DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

CREATE TABLE dbo.[Order](
  id INT IDENTITY PRIMARY KEY,
  product_id INT NOT NULL FOREIGN KEY REFERENCES dbo.Product(id),
  quantity INT NOT NULL,
  order_date DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

INSERT INTO dbo.Product(name,type,stock,min_stock,base_price,tax_rate,reorder_qty)
VALUES
(N'lápiz','papeleria',18,10,10.00,0.16,20),
(N'aspirina','drogueria',25,10,97.77,0.12,20),
(N'borrador','papeleria',30,15,178.71,0.16,20),
(N'pan','supermercado',15,12,144.23,0.04,20);
GO
