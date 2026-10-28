package org.example;

public class InventoryUpdate {
    String sku;
    int warehouseId;
    int onHandQuantity;
    int reservedQuantity;
    int updatedAt;

    public InventoryUpdate(
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
