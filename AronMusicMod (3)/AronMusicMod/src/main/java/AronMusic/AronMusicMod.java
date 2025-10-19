package AronMusic;

import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.sound.GameMusic;
import necesse.inventory.item.miscItem.VinylItem;
import java.awt.Color;

@ModEntry
public class AronMusicMod {
    public void init() {
        System.out.println("🎵 Музыкальный мод Aron Music Mod загружен!");

        // Регистрируем музыку и пластинки
        GameMusic aron_song1 = MusicRegistry.registerMusic("aron_song1", "music/song1", new StaticMessage("Моя песня 1"), new Color(255, 0, 0), new Color(0, 0, 255), new StaticMessage("Музыка от Aron"));
        ItemRegistry.registerItem(aron_song1.getStringID() + "vinyl", new VinylItem(aron_song1), 1000.0F, true, false, new String[0]);

        GameMusic aron_song2 = MusicRegistry.registerMusic("aron_song2", "music/song2", new StaticMessage("Моя песня 2"), new Color(0, 255, 0), new Color(255, 255, 0), new StaticMessage("Музыка от Aron"));
        ItemRegistry.registerItem(aron_song2.getStringID() + "vinyl", new VinylItem(aron_song2), 1000.0F, true, false, new String[0]);

        GameMusic aron_song3 = MusicRegistry.registerMusic("aron_song3", "music/song3", new StaticMessage("Моя песня 3"), new Color(0, 255, 255), new Color(255, 0, 255), new StaticMessage("Музыка от Aron"));
        ItemRegistry.registerItem(aron_song3.getStringID() + "vinyl", new VinylItem(aron_song3), 1000.0F, true, false, new String[0]);

        GameMusic aron_song4 = MusicRegistry.registerMusic("aron_song4", "music/song4", new StaticMessage("Моя песня 4"), new Color(255, 165, 0), new Color(255, 192, 203), new StaticMessage("Музыка от Aron"));
        ItemRegistry.registerItem(aron_song4.getStringID() + "vinyl", new VinylItem(aron_song4), 1000.0F, true, false, new String[0]);

        // Регистрируем только моба и предмет (без поселенца)
        registerDJNPC();

        System.out.println("💿 Пластинки и DJ зарегистрированы!");
    }

    private void registerDJNPC() {
        try {
            // Регистрируем моба
            int mobID = MobRegistry.registerMob("djnpc", AronMusic.npc.DJNPC.class, false);

            // Регистрируем спавн-яйцо
            ItemRegistry.registerItem("djnpc_spawnitem", new AronMusic.item.DJNPCSpawnItem(), 50.0F, true);

            System.out.println("🎧 DJ NPC зарегистрирован с ID: " + mobID);
            System.out.println("🥚 Спавн-яйцо DJNPC зарегистрировано");
        } catch (Exception e) {
            System.out.println("❌ Ошибка регистрации DJ NPC: " + e.getMessage());
        }
    }
}