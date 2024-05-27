package me.babychaosfloh_hd.musicplus.block;

import com.jeff_media.customblockdata.CustomBlockData;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Level;

public class noteblockhandler {

    private static final MusicPlus that = MusicPlus.getPlugin();

    public static void test(Block block) {
        //BlockState state = block.getState();

        PersistentDataContainer customBlockData = new CustomBlockData(block, that);
        customBlockData.set(new NamespacedKey(MusicPlus.getPlugin(), "test"), PersistentDataType.STRING, "Well done!");
        that.getLogger().log(Level.INFO, customBlockData.get(new NamespacedKey(MusicPlus.getPlugin(), "test"), PersistentDataType.STRING));
    }
}
