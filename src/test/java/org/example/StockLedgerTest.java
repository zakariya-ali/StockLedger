package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StockLedgerTest {
    @Test
    public void testApplyInventoryUpdate1() {
        // Test that a newer update replaces the record's stored values, including a
        // boundary update whose timestamp equals the record's current timestamp.
        StockLedger ledger = new StockLedger();
        ledger.registerInventoryRecord(
            new InventoryRecord("SKU-1", 101, 100, 20, 3000)
        );
        ledger.applyInventoryUpdate(
            new InventoryUpdate("SKU-1", 101, 80, 10, 2000)
        );
        InventoryRecord record =
            ledger.inventoryRecords.get("SKU-1:101");
        assertEquals(100, record.onHandQuantity);
        assertEquals(20, record.reservedQuantity);
        assertEquals(3000, record.updatedAt);
        assertEquals(80, ledger.getAvailableStock("SKU-1", 101));
        ledger.applyInventoryUpdate(
            new InventoryUpdate("SKU-1", 101, 120, 30, 4000)
        );
        assertEquals(120, record.onHandQuantity);
        assertEquals(30, record.reservedQuantity);
        assertEquals(4000, record.updatedAt);
        assertEquals(90, ledger.getAvailableStock("SKU-1", 101));
        // Update timestamp equals the record's current timestamp (boundary).
        ledger.applyInventoryUpdate(
            new InventoryUpdate("SKU-1", 101, 200, 50, 4000)
        );
        assertEquals(200, record.onHandQuantity);
        assertEquals(50, record.reservedQuantity);
        assertEquals(4000, record.updatedAt);
    }

    @Test
    public void testApplyInventoryUpdate2() {
        // Test that an update for an unknown SKU and warehouse pair is ignored.
        StockLedger ledger = new StockLedger();
        ledger.registerInventoryRecord(
            new InventoryRecord("SKU-1", 101, 100, 20, 3000)
        );
        ledger.applyInventoryUpdate(
            new InventoryUpdate("SKU-2", 202, 50, 5, 4000)
        );
        assertEquals(1, ledger.inventoryRecords.size());
        assertFalse(ledger.inventoryRecords.containsKey("SKU-2:202"));
    }

    @Test
    public void testApplyInventoryUpdate3() {
        // Test that available stock is floored at 0 when an applied update leaves reserved
        // quantity greater than on-hand quantity.
        StockLedger ledger = new StockLedger();
        ledger.registerInventoryRecord(
            new InventoryRecord("SKU-1", 101, 100, 20, 1000)
        );
        ledger.applyInventoryUpdate(
            new InventoryUpdate("SKU-1", 101, 30, 50, 2000)
        );
        InventoryRecord record =
            ledger.inventoryRecords.get("SKU-1:101");
        assertEquals(30, record.onHandQuantity);
        assertEquals(50, record.reservedQuantity);
        assertEquals(0, ledger.getAvailableStock("SKU-1", 101));
    }
}
