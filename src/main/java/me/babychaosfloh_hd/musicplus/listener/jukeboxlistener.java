package me.babychaosfloh_hd.musicplus.listener;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.item.dischandler;

import java.util.logging.Level;

import org.bukkit.Tag;
import org.bukkit.block.Jukebox;
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

import java.util.*;

public class jukeboxlistener implements Listener {

    private final MusicPlus plugin;

    public jukeboxlistener(MusicPlus plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.isCancelled() && e.getHand() == EquipmentSlot.HAND && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType() == Material.JUKEBOX) {

            Block clicked = e.getClickedBlock();
            //World world = clicked.getWorld();
            Location pos = clicked.getLocation();
            Player player = e.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            dischandler.del(player, clicked);

            if ((item.getType() != Material.AIR && (item.getType() == MusicPlus.getPlugin().getConfig().getItemStack("disc_44444").getType())) && dischandler.check(clicked, item, player) == true) {
                //this.plugin.getLogger().log(Level.INFO, "Random text");
            }
        }
    }
}