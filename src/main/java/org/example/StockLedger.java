package org.example;

import java.util.HashMap;
import java.util.Map;

/*
 * Stock Ledger tracks inventory across warehouses for an e-commerce fulfillment platform.
 *
 * - A "SKU" uniquely identifies a sellable product variant; inventory is tracked per SKU per warehouse.
 * - An "inventory record" is the current stock state of one SKU at one warehouse.
 * - An "inventory update" is an incoming change to one SKU's stock at one warehouse.
 * - "Available stock" = onHandQuantity - reservedQuantity, floored at 0.
 *
 * Updates may arrive out of order. updatedAt is when the warehouse recorded the stock info.
 * An update older than the record's current updatedAt is outdated and must not replace it.
 *
 * Tasks:
 * 1-1) Read through and understand the code. Feel free to run it.
 * 1-2) The test for StockLedger is not passing due to a bug. Fix StockLedger.
 */
public class StockLedger {
    Map<String, InventoryRecord> inventoryRecords = new HashMap<>();

    public String recordKey(String sku, int warehouseId) {
        return sku + ":" + warehouseId;
    }

    public void registerInventoryRecord(InventoryRecord record) {
        inventoryRecords.put(
            recordKey(record.sku, record.warehouseId),
            record
        );
    }

    public void applyInventoryUpdate(InventoryUpdate update) {
        String key = recordKey(update.sku, update.warehouseId);
        if (!inventoryRecords.containsKey(key)) {
            return;
        }
        InventoryRecord record = inventoryRecords.get(key);
        record.onHandQuantity = update.onHandQuantity;
        record.reservedQuantity = update.reservedQuantity;
        record.updatedAt = update.updatedAt;
    }

    public int getAvailableStock(String sku, int warehouseId) {
        String key = recordKey(sku, warehouseId);
        if (!inventoryRecords.containsKey(key)) {
            return 0;
        }
        InventoryRecord record = inventoryRecords.get(key);
        return Math.max(
            record.onHandQuantity - record.reservedQuantity,
            0
        );
    }
}
