package me.babychaosfloh_hd.musicplus.block;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.logging.Level;

public class commandblockhandler {

    private static final MusicPlus that = MusicPlus.getPlugin();
    public static String next;
   // public static int pitch;

    public static void setnote(Player player, Block block) {

        World world = block.getWorld();
        Location pos = block.getLocation();
        BlockState blockState = block.getState();
        TileState tileState = (TileState) blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();


        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER)) {

            int i = container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            //pitch = 2^((i - 12) / 12);

            if (i < 24){
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
        TileState tileState = (TileState) blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();

        if (container.has(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER) && container.has(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING)) {

            String song = container.get(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING);
            int i = container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);
            int pitch = 2^((i - 12) / 12);
            world.playSound(pos, song, 3 , pitch);
        }
    }

    public static void setinstrument(Player player, Block block) {

        World world = block.getWorld();
        Location pos = block.getLocation();
        BlockState blockState = block.getState();
        TileState tileState = (TileState) blockState;
        PersistentDataContainer container = tileState.getPersistentDataContainer();
        List<?> instrument = filemanager.getnote().getList("instrument");
        String prefix = filemanager.getnote().getString("prefix");
        String material = block.getLocation().subtract(0, 1, 0).getBlock().getType().toString();

        next = prefix + filemanager.getnote().getString(material);
        //player.sendMessage(filemanager.getnote().getString("DIRT"));

        int pitch = container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER);;

        player.sendMessage(pos + ";    "+ next + ";    " + pitch + ";    " + material + ";  " + prefix + ";  " + container.get(new NamespacedKey(MusicPlus.getPlugin(), "note"), PersistentDataType.INTEGER));
        //playnote(block);

        container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
        tileState.update();
        /*
        if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING)){
            container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
            tileState.update();
        }
        else {
            String current = container.get(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING);
            //playnote(block);

            int integer = instrument.indexOf(current);
            int i = integer + 1;
            int size = instrument.size();

            MusicPlus.getPlugin().getLogger().log(Level.INFO, size + "  " + i + "  " + integer + "  " + current);

            if (i <= size - 1) {
                next = prefix + filemanager.getnote().getString(instrument.get(i).toString());
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
                tileState.update();
            } else {
                next = prefix + instrument.get(0).toString();
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "instrument"), PersistentDataType.STRING, next);
                tileState.update();
            }

             */
    }
}
