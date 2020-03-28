package io.github.katsumag.prace.GUI;

import io.github.katsumag.prace.Prace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class JobHolder implements InventoryHolder {
    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */

    private Prace main;

    public JobHolder(Prace main){
        this.main = main;
    }

    @Override
    public Inventory getInventory() {
        return new Selector(main).inventory;
    }
}
