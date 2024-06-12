package me.babychaosfloh_hd.musicplus.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.download.language;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private static List<String> word = new ArrayList<>();
    private static String out = "";
    private static  String keyname;
    private static String[] keys;
    private static Inventory inv;
    public static ItemStack add;
    private static double count;
    private static String local;
    private static int slots;

    public keyboard(MusicPlus musicPlus) {
    }

    public static void keyboard(Player player, ItemStack item) {

        local = player.getLocale();
        add = item;
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

        ItemStack num = new ItemStack(Material.PAPER);
        ItemMeta numMeta = num.getItemMeta();
        numMeta.setDisplayName(language.getlang(local).getString("menu_keyboard_num"));
        num.setItemMeta(numMeta);

        nonekey.add(cancel);
        nonekey.add(confirm);
        nonekey.add(delete);
        nonekey.add(num);

        keyboard.setItem(slots - 1, cancel);
        keyboard.setItem(slots - 2, confirm);
        keyboard.setItem(slots - 3, space);
        keyboard.setItem(slots - 4, space);
        keyboard.setItem(slots - 5, space);
        keyboard.setItem(slots - 6, space);
        keyboard.setItem(slots - 7, space);
        keyboard.setItem(slots - 8, delete);
        keyboard.setItem(slots - 9, delete);
        keyboard.setItem(slots - 10, num);
        keyboard.setItem(slots - 11, num);

        inv = keyboard;
        player.openInventory(keyboard);
    }

    public static void numPad(Player player) {

        Inventory num = Bukkit.createInventory(player, 9, language.getlang(local).getString("menu_numpad_title"));

        ItemStack wip = new ItemStack(Material.BARRIER);
        ItemMeta wipMeta = wip.getItemMeta();
        wipMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "WORK IN PROGRESS");
        wipMeta.setLore(language.getlang(local).getStringList("menu_numpad_workingporgress"));
        wip.setItemMeta(wipMeta);

        num.setItem(4, wip);
        player.openInventory(num);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        that.getLogger().info("Key Clicked");
        if (e.getView().getTopInventory().equals(inv) && e.getClickedInventory().equals(inv)) {

            ItemStack key = e.getCurrentItem().clone();
            ItemMeta keyMeta = key.getItemMeta();
            Player player = Bukkit.getPlayer(e.getWhoClicked().getName());

            if (!keyMeta.getDisplayName().equals("Space")) {
                keyname = keyMeta.getDisplayName();
            }
            else {
                keyname = " ";
            }
            that.getLogger().info("Key is: " + key.getItemMeta().getDisplayName());
            if (keyname.equals(language.getlang(local).getString("menu_keyboard_num"))) {
                guimanager.open = true;
                numPad(player);
            }
            if (!nonekey.contains(key)) {
                word.add(keyname);
                out = "";
                for (String str : word) {
                    out = out + str;
                }
                that.getLogger().info(out);
            }
            else if (keyname.equals(language.getlang(local).getString("menu_keyboard_delete")) && !word.isEmpty()) {
                int i = word.size();
                word.remove(i - 1);
                out = "";
                for (String str : word) {
                    out = out + str;
                }
                that.getLogger().info(out);
            }
            else if (keyname.equals(language.getlang(local).getString("menu_confirm_confirm"))) {
                out = "";
                filemanager.getnote().set(add.getType().toString(), out);
                filemanager.savenote();
                filemanager.reload();
                guimanager.open = true;
                guimanager.NoteMenu(player);

            }
            else if (keyname.equals(language.getlang(local).getString("menu_confirm_cancel"))) {
                guimanager.open = true;
                guimanager.NoteMenu(player);
            }
            e.setCancelled(true);
        }
    }
}