package gg.steve.mc.splugin.gui.exception;

import gg.steve.mc.splugin.exception.AbstractException;
import gg.steve.mc.splugin.exception.ExceptionClass;

@ExceptionClass
public class InventoryClickActionNotFoundException extends AbstractException {
    private String clickActionUniqueName;

    public InventoryClickActionNotFoundException(String clickActionUniqueName) {
        this.clickActionUniqueName = clickActionUniqueName;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the inventory click action: " + this.clickActionUniqueName + ", because it is not registered.";
    }
}
