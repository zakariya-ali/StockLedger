package org.example;

public class Main {
    public static void main(String[] args) {
        StockLedger ledger = new StockLedger();
        ledger.registerInventoryRecord(new InventoryRecord("SKU-1", 101, 100, 20, 3000));
        System.out.println("Available: " + ledger.getAvailableStock("SKU-1", 101));

        // Outdated update (2000 < 3000) - should be ignored
        ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 80, 10, 2000));
        System.out.println("After outdated update: " + ledger.getAvailableStock("SKU-1", 101));

        ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 120, 30, 4000));
        System.out.println("After newer update: " + ledger.getAvailableStock("SKU-1", 101));
    }
}
