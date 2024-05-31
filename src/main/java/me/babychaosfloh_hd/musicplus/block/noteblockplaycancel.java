package me.babychaosfloh_hd.musicplus.block;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;

public class noteblockplaycancel implements Listener {
    public noteblockplaycancel(MusicPlus musicPlus) {
    }

    @EventHandler
    public void onplay(NotePlayEvent e) {
        e.setCancelled(true);
    }
}