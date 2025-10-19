package AronMusic.autonpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;

public class BaseNPC extends HumanShop {
    public int talkOptions = -1;

    public BaseNPC(int nonSettlerHealth, int settlerHealth, String settlerStringID) {
        super(nonSettlerHealth, settlerHealth, settlerStringID);
        this.attackCooldown = 500;
        this.attackAnimTime = 500;
        this.setSwimSpeed(1.0F);
    }

    public BaseNPC(int nonSettlerHealth, int settlerHealth, String settlerStringID, int attackCooldown, int attackAnimTime) {
        super(nonSettlerHealth, settlerHealth, settlerStringID);
        this.attackCooldown = attackCooldown;
        this.attackAnimTime = attackAnimTime;
        this.setSwimSpeed(1.0F);
    }

    protected ArrayList<GameMessage> getMessages(ServerClient client) {
        if (this.talkOptions == -1)
            return getLocalMessages("humantalk", 5);
        return getLocalMessages(this.settlerStringID + "talk", this.talkOptions);
    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }

    public List<InventoryItem> getRecruitItems(ServerClient client) {
        GameRandom random = new GameRandom((long)this.getSettlerSeed() * 29L);
        return Collections.singletonList(new InventoryItem("coin", random.getIntBetween(500, 1000)));
    }
}