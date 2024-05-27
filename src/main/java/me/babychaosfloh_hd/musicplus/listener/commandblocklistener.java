package me.babychaosfloh_hd.musicplus.listener;

import jdk.internal.foreign.Utils;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.block.commandblockhandler;
import me.babychaosfloh_hd.musicplus.block.noteblockhandler;

import org.bukkit.GameMode;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


//import org.bukkit.event.player.Action;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Material;

import org.bukkit.event.block.Action;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class commandblocklistener implements Listener {

    private final MusicPlus plugin;
    private static final MusicPlus that = MusicPlus.getPlugin();

    public commandblocklistener(MusicPlus plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.isCancelled() && e.getHand() == EquipmentSlot.HAND && e.getClickedBlock().getType() == Material.NOTE_BLOCK && MusicPlus.testfeatures.contains("custom_jukebox")) {

                Block clicked = e.getClickedBlock();
                //World world = clicked.getWorld();
                Location pos = clicked.getLocation();
                Player player = e.getPlayer();
                ItemStack item = player.getInventory().getItemInMainHand();
                CommandBlock block = (CommandBlock) clicked.getState();
                GameMode mode = GameMode.valueOf("SURVIVAL");

                if (clicked.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                    if (player.getGameMode().equals(mode) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        player.sendMessage("message");
                        //commandblockhandler.playnote(clicked);
                        //commandblockhandler.setinstrument(player, clicked);
                        //commandblockhandler.setnote(player, clicked);
                        noteblockhandler.test(clicked);
                        }
                    }
                    if (player.getGameMode().equals(mode) && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        player.sendMessage("message");
                        commandblockhandler.setinstrument(player, clicked);
                        commandblockhandler.playnote(clicked);
                    }
        }
    }
}