package me.babychaosfloh_hd.musicplus.listener;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.item.dischandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class jukeboxlistener implements Listener {
    private final MusicPlus plugin;

    public jukeboxlistener(MusicPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.isCancelled() && e.getHand() == EquipmentSlot.HAND && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType() == Material.JUKEBOX) {
            Block clicked = e.getClickedBlock();
            Location pos = clicked.getLocation();
            Player player = e.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            dischandler.del(player, clicked);
            if (dischandler.check(clicked, item, player)) {
            }
        }

    }
}