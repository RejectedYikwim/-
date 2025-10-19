package AronMusic.item;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item.Rarity;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;

public class DJNPCSpawnItem extends ConsumableItem {
    private final String settlerStringID;
    public boolean IsSingleUse;

    public DJNPCSpawnItem() {
        super(100, false); // stackSize, singleUse
        this.IsSingleUse = false;
        this.itemCooldownTime.setBaseValue(2000);
        this.setItemCategory(new String[]{"consumable", "mob"});
        this.dropsAsMatDeathPenalty = false;
        this.keyWords.add("npc");
        this.keyWords.add("djnpc");
        this.keyWords.add("music");
        this.keyWords.add("vinyl");
        this.keyWords.add("summon");
        this.rarity = Rarity.UNCOMMON;
        this.incinerationTimeMillis = 30000;
        this.settlerStringID = "djnpc";
    }

    public boolean getConstantUse(InventoryItem item) {
        return false;
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent) {
        if (level.isServer()) {
            Mob mob = MobRegistry.getMob(this.settlerStringID, level);
            Point spawnPoint = this.findSpawnLocation(level, mob, player.getX() / 32, player.getY() / 32, 1);
            level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            level.getServer().network.sendToClientsWithEntity(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), mob);
        }

        if (level.isClient()) {
            SoundManager.playSound(GameResources.pop, SoundEffect.effect(player).pitch(0.8F));
        }

        return item;
    }

    protected Point findSpawnLocation(Level level, Mob mob, int tileX, int tileY, int maxTileRange) {
        ArrayList<Point> possibleSpawns = new ArrayList();

        for(int x = tileX - maxTileRange; x <= tileX + maxTileRange; ++x) {
            for(int y = tileY - maxTileRange; y <= tileY + maxTileRange; ++y) {
                if (x != tileX || y != tileY) {
                    int posX = x * 32 + 16;
                    int posY = y * 32 + 16;
                    if (!mob.collidesWith(level, posX, posY)) {
                        possibleSpawns.add(new Point(posX, posY));
                    }
                }
            }
        }

        if (!possibleSpawns.isEmpty()) {
            return (Point)possibleSpawns.get(GameRandom.globalRandom.nextInt(possibleSpawns.size()));
        } else {
            return new Point(tileX * 32 + 16, tileY * 32 + 16);
        }
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent) {
        return null;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "spawnmob", "mob", MobRegistry.getDisplayName(MobRegistry.getMobID(this.settlerStringID)))));
        tooltips.add(Localization.translate("itemtooltip", this.settlerStringID + "tooltip"));
        if (this.IsSingleUse) {
            tooltips.add(Localization.translate("itemtooltip", "singleuse"));
        } else {
            tooltips.add(Localization.translate("itemtooltip", "infiniteuse"));
        }

        return tooltips;
    }
}