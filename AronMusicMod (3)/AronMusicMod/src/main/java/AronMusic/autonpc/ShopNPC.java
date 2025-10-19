package AronMusic.autonpc;

// ТОЛЬКО ЭТИ ИМПОРТЫ - все классы в одном пакете
import java.util.ArrayList;
import necesse.engine.localization.message.GameMessage;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import necesse.inventory.InventoryItem;

public class ShopNPC extends BaseNPC {
    public ArrayList<CustomShopItem> itemsList = new ArrayList();
    private final LangUtils langUtils = new LangUtils();

    public ShopNPC(int nonSettlerHealth, int settlerHealth, String settlerStringID) {
        super(nonSettlerHealth, settlerHealth, settlerStringID);
        this.setShopContent();
        this.loadCustomShop();
    }

    public ShopNPC(int nonSettlerHealth, int settlerHealth, String settlerStringID, int attackCooldown, int attackAnimTime) {
        super(nonSettlerHealth, settlerHealth, settlerStringID, attackCooldown, attackAnimTime);
        this.setShopContent();
        this.loadCustomShop();
    }

    public void setShopContent() {
    }

    public void loadCustomShop() {
        for(CustomShopItem item : this.itemsList) {
            switch (item.condition) {
                case ALWAYS:
                    this.shop.addSellingItem(item.itemStringID, new SellingShopItem())
                            .setStaticPriceBasedOnHappiness(item.minPrice, item.maxPrice, item.range);
                    break;
                case GET_FIRST:
                    this.shop.addSellingItem(item.itemStringID, new SellingShopItem())
                            .setItem((random, client, mob) -> {
                                boolean isUnlocked = client.characterStats().items_obtained.isStatItemObtained(item.itemStringID);
                                return isUnlocked ?
                                        new InventoryItem(item.itemStringID) :
                                        this.addLockItem(item);
                            })
                            .setStaticPriceBasedOnHappiness(item.minPrice, item.maxPrice, item.range);
                    break;
                case KILL_FIRST:
                    this.shop.addSellingItem(item.itemStringID, new SellingShopItem())
                            .setItem((random, client, mob) -> {
                                boolean isUnlocked = client.characterStats().mob_kills.getKills(item.conditionID) > 0;
                                return isUnlocked ?
                                        new InventoryItem(item.itemStringID) :
                                        this.addLockItem(item);
                            })
                            .setStaticPriceBasedOnHappiness(item.minPrice, item.maxPrice, item.range);
                    break;
                case NEVER:
                    break;
            }
        }
    }

    public InventoryItem addLockItem(CustomShopItem item) {
        InventoryItem inventoryItem = new InventoryItem("lockeditem");
        inventoryItem = this.getItemLang(inventoryItem, item);
        switch (item.condition) {
            case GET_FIRST:
                inventoryItem.getGndData().setString("unlockGettingFirst", item.conditionID);
                break;
            case KILL_FIRST:
                inventoryItem.getGndData().setString("unlockWithKill", item.conditionID);
                break;
        }
        return inventoryItem;
    }

    public InventoryItem getItemLang(InventoryItem inventoryItem, CustomShopItem item) {
        GameMessage message = langUtils.getItemLang(new InventoryItem(item.itemStringID));
        inventoryItem.getGndData().setString("itemLangName", message.translate());
        return inventoryItem;
    }
}