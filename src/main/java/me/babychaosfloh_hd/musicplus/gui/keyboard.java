package me.babychaosfloh_hd.musicplus.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.download.language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class keyboard implements Listener {
    private static final MusicPlus that = MusicPlus.getPlugin();
    private static List<ItemStack> nonekey = new ArrayList();
    private static Inventory keyboard;
    private static String[] keys;
    private static double count;
    private static String local;
    private static List<String> word;
    private static int slots;

    public keyboard(MusicPlus musicPlus) {
    }

    public static void keyboard(Player player) {
        local = player.getLocale();
        if (language.download(local)) {
            keys = language.getlang(local).getString("menu_keyboard_keyboard").split("");
            count = (double)keys.length;

            for(slots = 36; count / 9.0D - (double)((int)count / 9) != 0.0D; ++count) {
            }

            if (count / 9.0D - (double)((int)count / 9) != 0.0D) {
                that.getLogger().log(Level.INFO, "Decimal value");
            } else {
                that.getLogger().log(Level.INFO, "Integer value");
            }

            if (count > (double)(slots - 9) && count < 54.0D) {
                slots = (int)count;
            }
        }

        Inventory keyboard = Bukkit.createInventory(player, slots, language.getlang(local).getString("menu_keyboard_title") + " - " + local);

        for(int integer = 0; integer < keys.length; ++integer) {
            ItemStack key = new ItemStack(Material.PAPER);
            ItemMeta keyMeta = key.getItemMeta();
            keyMeta.setDisplayName(keys[integer]);
            key.setItemMeta(keyMeta);
            keyboard.addItem(new ItemStack[]{key});
        }

        ItemStack delete = new ItemStack(Material.PAPER);
        ItemMeta delMeta = delete.getItemMeta();
        delMeta.setDisplayName(language.getlang(local).getString("menu_keyboard_delete"));
        delete.setItemMeta(delMeta);
        ItemStack space = new ItemStack(Material.PAPER);
        ItemMeta spaceMeta = space.getItemMeta();
        spaceMeta.setDisplayName(language.getlang(local).getString("menu_keyboard_space"));
        space.setItemMeta(spaceMeta);
        ItemStack confirm = new ItemStack(Material.GREEN_WOOL);
        ItemMeta confirmMeta = space.getItemMeta();
        confirmMeta.setDisplayName(language.getlang(local).getString("menu_confirm_confirm"));
        confirm.setItemMeta(confirmMeta);
        ItemStack cancel = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelMeta = space.getItemMeta();
        cancelMeta.setDisplayName(language.getlang(local).getString("menu_confirm_cancel"));
        cancel.setItemMeta(cancelMeta);
        nonekey.add(cancel);
        nonekey.add(confirm);
        nonekey.add(delete);
        keyboard.setItem(slots - 1, cancel);
        keyboard.setItem(slots - 2, confirm);
        keyboard.setItem(slots - 3, space);
        keyboard.setItem(slots - 4, space);
        keyboard.setItem(slots - 5, space);
        keyboard.setItem(slots - 6, space);
        keyboard.setItem(slots - 7, space);
        keyboard.setItem(slots - 8, delete);
        keyboard.setItem(slots - 9, delete);
        player.openInventory(keyboard);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == keyboard && e.getCurrentItem().getType() != null) {
            ItemStack key = e.getCurrentItem();
            ItemMeta keyMeta = key.getItemMeta();
            String keyname = keyMeta.getDisplayName();
            if (!nonekey.contains(key)) {
                word.add(keyname);
                that.getLogger().info(word.toString());
            } else if (keyname.equals(language.getlang(local).getString("menu_keyboard_delete"))) {
                int i = word.size();
                word.subList(i - 1, i - 1);
                that.getLogger().info(word.toString());
            }
        }

    }
}