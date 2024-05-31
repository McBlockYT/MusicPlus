package me.babychaosfloh_hd.musicplus.block;

import java.util.List;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class commandblockhandler {
    private static final MusicPlus that = MusicPlus.getPlugin();
    public static String next;

    public static void setnote(Player player, Block block) {
        World world = block.getWorld();
        Location pos = block.getLocation();
        BlockState blockState = block.getState();
        TileState tileState = (TileState)blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();
        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER)) {
            int i = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            if (i < 24) {
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, i + 1);
                tileState.update();
                playnote(block);
            } else if (i == 24) {
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, 0);
                tileState.update();
                playnote(block);
            }
        } else if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER)) {
            container.set(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER, 0);
            tileState.update();
            playnote(block);
        }

    }

    public static void playnote(Block block) {
        World world = block.getWorld();
        Location pos = block.getLocation();
        BlockState blockState = block.getState();
        TileState tileState = (TileState)blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();
        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER) && container.has(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING)) {
            String song = (String)container.get(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING);
            int i = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            int pitch = 2 ^ (i - 12) / 12;
            world.playSound(pos, song, 3.0F, (float)pitch);
        }

    }

    public static void setinstrument(Player player, Block block) {
        World world = block.getWorld();
        Location pos = block.getLocation();
        BlockState blockState = block.getState();
        TileState tileState = (TileState)blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();
        List<?> instrument = filemanager.getnote().getList("instrument");
        String prefix = filemanager.getnote().getString("prefix");
        String material = block.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().toString();
        next = prefix + filemanager.getnote().getString(material);
        int pitch = (Integer)container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
        player.sendMessage(pos + ";    " + next + ";    " + pitch + ";    " + material + ";  " + prefix + ";  " + container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER));
        container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
        tileState.update();
    }
}