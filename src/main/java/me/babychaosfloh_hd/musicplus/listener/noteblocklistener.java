package me.babychaosfloh_hd.musicplus.listener;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.block.noteblockhandler;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class noteblocklistener implements Listener {
    private final MusicPlus plugin;
    private static final MusicPlus that = MusicPlus.getPlugin();

    public noteblocklistener(MusicPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.isCancelled() && e.getHand() == EquipmentSlot.HAND && e.getClickedBlock().getType() == Material.NOTE_BLOCK && MusicPlus.testfeatures.contains("custom_jukebox")) {
            Block clicked = e.getClickedBlock();
            Player player = e.getPlayer();
            GameMode mode = GameMode.valueOf("SURVIVAL");
            if (clicked.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR && player.getGameMode().equals(mode) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                player.sendMessage("message");
                noteblockhandler.setnote(clicked);
                noteblockhandler.setinstrument(player, clicked);
                e.setCancelled(true);
            }

            if (player.getGameMode().equals(mode) && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                player.sendMessage("message");
                noteblockhandler.setinstrument(player, clicked);
                noteblockhandler.playnote(clicked);
            }
        }

    }
}