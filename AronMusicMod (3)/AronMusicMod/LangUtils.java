package AronMusic.autonpc;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.registries.LogicGateRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.inventory.InventoryItem;

public class LangUtils {
    private final GameMessage localizationNotFound = new StaticMessage("N/A");

    public GameMessage getItemLang(InventoryItem inventoryItem) {
        GameMessage message = inventoryItem.item.getNewLocalization();
        if (this.isLocalizationFound(message)) {
            return message;
        } else {
            String itemStringID = inventoryItem.item.getStringID();
            message = ObjectRegistry.getObject(itemStringID).getObjectItem().getNewLocalization();
            if (this.isLocalizationFound(message)) {
                return message;
            } else {
                int gateID = LogicGateRegistry.getLogicGateID(itemStringID);
                message = LogicGateRegistry.getLogicGate(gateID).getNewLocalization();
                if (this.isLocalizationFound(message)) {
                    return message;
                } else {
                    message = TileRegistry.getTile(itemStringID).getTileItem().getNewLocalization();
                    return this.isLocalizationFound(message) ? message : this.localizationNotFound;
                }
            }
        }
    }

    public boolean isLocalizationFound(GameMessage message) {
        return !message.isSame(this.localizationNotFound);
    }
}