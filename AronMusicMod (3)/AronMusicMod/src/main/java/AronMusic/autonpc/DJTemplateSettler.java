import java.util.function.Supplier;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.util.TicketSystemList;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.levelData.settlementData.ServerSettlementData;
import necesse.level.maps.levelData.settlementData.settler.Settler;

public class TemplateSettler extends Settler {
    String settlerHumanStringID;

    String bossStringID;

    String aquireTipString;

    public TemplateSettler(String settlerHumanStringID, String bossStringID) {
        super(settlerHumanStringID);
        this.settlerHumanStringID = settlerHumanStringID;
        this.aquireTipString = this.settlerHumanStringID.replace("human", "") + "tip";
        this.bossStringID = bossStringID;
    }

    public void loadTextures() {
        this.texture = GameTexture.fromFile("mobs/icons/" + this.settlerHumanStringID);
    }

    public GameMessage getAcquireTip() {
        return (GameMessage)new LocalMessage("settlement", this.aquireTipString);
    }

    public void addNewRecruitSettler(ServerSettlementData data, boolean isRandomEvent, TicketSystemList<Supplier<HumanMob>> ticketSystem) {
        if ((isRandomEvent || !doesSettlementHaveThisSettler(data)) && data.hasCompletedQuestTier(this.bossStringID))
            ticketSystem.addObject(100, getNewRecruitMob(data));
    }
}
