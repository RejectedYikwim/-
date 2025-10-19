package AronMusic.autonpc;

import necesse.inventory.InventoryItem;

public class CustomShopItem {
    String itemStringID;

    int minPrice;

    int maxPrice;

    int range;

    unlockCondition condition = unlockCondition.ALWAYS;

    String conditionID;

    public CustomShopItem(String itemStringID, int minPrice, int maxPrice, int range) {
        this.itemStringID = itemStringID;
        float itemPrice = (new InventoryItem(itemStringID)).getBrokerValue();
        this.minPrice = minPrice + (int)itemPrice;
        this.maxPrice = maxPrice + (int)itemPrice;
        this.range = range;
    }

    public CustomShopItem addExtraValue(int value) {
        this.minPrice += value;
        this.maxPrice += value;
        return this;
    }

    public CustomShopItem getItemFirst() {
        this.condition = unlockCondition.GET_FIRST;
        this.conditionID = this.itemStringID;
        return this;
    }

    public CustomShopItem getItemFirst(String itemStringID) {
        this.condition = unlockCondition.GET_FIRST;
        this.conditionID = itemStringID;
        return this;
    }

    public CustomShopItem killMobFirst(String itemStringID) {
        this.condition = unlockCondition.KILL_FIRST;
        this.conditionID = itemStringID;
        return this;
    }

    public CustomShopItem neverObtainable() {
        this.condition = unlockCondition.NEVER;
        return this;
    }

    public CustomShopItem alwaysObtainable() {
        this.condition = unlockCondition.ALWAYS;
        return this;
    }

    public enum unlockCondition {
        NEVER, ALWAYS, KILL_FIRST, GET_FIRST;
    }
}
