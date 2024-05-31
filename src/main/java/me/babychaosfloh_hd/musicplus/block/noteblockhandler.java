package me.babychaosfloh_hd.musicplus.block;

import com.jeff_media.customblockdata.CustomBlockData;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class noteblockhandler {
    private static final MusicPlus that = MusicPlus.getPlugin();
    public static String next;

    public static void setnote(Block block) {
        PersistentDataContainer container = new CustomBlockData(block, that);
        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER)) {
            int i = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            if (i < 24) {
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, i + 1);
                playnote(block);
            } else if (i == 24) {
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, 0);
                playnote(block);
            }
        } else if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER)) {
            container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, 0);
            playnote(block);
        }

    }

    public static void playnote(Block block) {
        World world = block.getWorld();
        Location pos = block.getLocation();
        PersistentDataContainer container = new CustomBlockData(block, that);
        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER) && container.has(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING)) {
            String song = (String)container.get(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING);
            int i = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            double exponent = (double)(i - 12) / 12.0D;
            double result = Math.pow(2.0D, exponent);
            float pitch = (float)result;
            world.playSound(pos, song, 3.0F, pitch);
        }

    }

    public static void setinstrument(Player player, Block block) {
        World world = block.getWorld();
        Location pos = block.getLocation();
        PersistentDataContainer container = new CustomBlockData(block, that);
        String prefix = filemanager.getnote().getString("prefix");
        String material = block.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().toString();
        next = prefix + filemanager.getnote().getString(material);
        int pitch = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
        player.sendMessage(pos + ";    " + next + ";    " + pitch + ";    " + material + ";  " + prefix + ";  " + container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER));
        container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
    }
}