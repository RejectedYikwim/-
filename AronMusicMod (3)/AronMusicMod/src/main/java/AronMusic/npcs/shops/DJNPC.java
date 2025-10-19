package AronMusic.npc;

import AronMusic.autonpc.CustomShopItem;
import AronMusic.autonpc.ShopNPC;
import java.util.Collections;
import java.util.List;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.inventory.InventoryItem;
import necesse.gfx.gameTexture.GameTexture;

public class DJNPC extends ShopNPC {
    public static String npcID = "djnpc";

    public DJNPC() {
        super(80, 120, npcID);
        this.talkOptions = 8;
        this.attackCooldown = 1000;
        this.attackAnimTime = 800;

        // Загружаем иконку для NPC из resources
        loadTextures();
    }

    // Метод для загрузки иконки из resources
    public void loadTextures() {
        try {
            // Пробуем разные пути в resources
            GameTexture iconTexture = GameTexture.fromFile("items/djnpc_spawnitem");
            this.texture = iconTexture;
            System.out.println("✅ Иконка DJNPC загружена из resources/items/");

        } catch (Exception e) {
            System.out.println("❌ Путь items/djnpc_spawnitem не сработал: " + e.getMessage());

            try {
                // Пробуем другой путь
                GameTexture iconTexture = GameTexture.fromFile("djnpc_spawnitem");
                this.texture = iconTexture;
                System.out.println("✅ Иконка DJNPC загружена из resources/");

            } catch (Exception e2) {
                System.out.println("❌ Все пути не сработали, используем стандартную текстуру");
            }
        }
    }

    public List<InventoryItem> getRecruitItems(ServerClient client) {
        return Collections.emptyList();
    }

    public void setDefaultArmor(HumanDrawOptions drawOptions) {
    }

    @Override
    public void setShopContent() {
        this.itemsList.add(new CustomShopItem("aron_song1vinyl", 800, 800, 0));
        this.itemsList.add(new CustomShopItem("aron_song2vinyl", 850, 850, 0));
        this.itemsList.add(new CustomShopItem("aron_song3vinyl", 900, 900, 0));
        this.itemsList.add(new CustomShopItem("aron_song4vinyl", 950, 950, 0));
    }
}