// Source code is decompiled from a .class file using FernFlower decompiler.
package org.example.app.models;

public class SpotManager {
    public SpotManager() {
    }

    public static Spot findAvailableSpot(ParkingLot var0) {
        for (int var1 = 1; var1 <= var0.getCapacity(); ++var1) {
            Spot var2 = new Spot(var1, var0);
            if (var2.isAvailable()) {
                return var2;
            }
        }

        return null;
    }
}
