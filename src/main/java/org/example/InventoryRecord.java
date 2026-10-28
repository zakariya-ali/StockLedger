package org.example;

public class InventoryRecord {
    String sku;
    int warehouseId;
    int onHandQuantity;
    int reservedQuantity;
    int updatedAt;

    public InventoryRecord(
        String sku,
        int warehouseId,
        int onHandQuantity,
        int reservedQuantity,
        int updatedAt
    ) {
        this.sku = sku;
        this.warehouseId = warehouseId;
        this.onHandQuantity = onHandQuantity;
        this.reservedQuantity = reservedQuantity;
        this.updatedAt = updatedAt;
    }
}
