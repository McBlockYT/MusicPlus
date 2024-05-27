package me.babychaosfloh_hd.musicplus.gui;

import com.mojang.datafixers.kinds.IdF;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class guimanager implements Listener {

    private final MusicPlus plugin;

    public guimanager(MusicPlus plugin) {
        this.plugin = plugin;
    }

    private static final MusicPlus that = MusicPlus.getPlugin();
    private static Inventory inv;
    private static List<ItemStack> itemStacks;

    public static void  MainMenu(Player player) {

        Inventory menu = Bukkit.createInventory(player, 45, "Music Manager");
        ItemStack disc_menu = new ItemStack(Material.PAPER, 1);
        ItemMeta disc_menu_meta = disc_menu.getItemMeta();
        disc_menu_meta.setDisplayName("Disc Manager");
        disc_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        disc_menu.setItemMeta(disc_menu_meta);

        ItemStack note_menu = new ItemStack(Material.PAPER, 1);
        ItemMeta note_menu_meta = disc_menu.getItemMeta();
        note_menu_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "WORK IN PROGRESS!!");
        note_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        note_menu.setItemMeta(note_menu_meta);


        menu.setItem(20, disc_menu);
        menu.setItem(24, note_menu);

        inv = menu;
        player.openInventory(menu);
    }
    public static void DiscMenu(Player player) {

        ConfigurationSection rootSection = filemanager.getdisc().getRoot();
        itemStacks = new ArrayList<>(filemanager.getdisc().getInt("count"));
        int i = 0;

        for (String key : rootSection.getKeys(false)) {
            if (filemanager.getdisc().isItemStack(key)) {
                //that.getLogger().log(Level.INFO, "Deserializing the item data");
                ItemStack itemStack = rootSection.getItemStack(key);
                itemStacks.add(itemStack);
                //that.getLogger().log(Level.INFO, "guimanager.java[line:68]      " + itemStack.toString());
                i++;
                if (i == filemanager.getdisc().getInt("count")) {
                    //that.getLogger().log(Level.INFO, "breaking: for loop");
                    break;
                }
            }
        }
        //that.getLogger().log(Level.INFO, itemStacks.toString());

        Inventory menu = Bukkit.createInventory(player, 45, "Disc Manager");
        ItemStack disc_menu = new ItemStack(that.getConfig().getItemStack("disc_44444").getType(), 1);
        ItemMeta disc_menu_meta = disc_menu.getItemMeta();
        disc_menu_meta.setDisplayName("Disc Manager");
        disc_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        disc_menu.setItemMeta(disc_menu_meta);

        ItemStack note_menu = new ItemStack(Material.PAPER, 1);
        ItemMeta note_menu_meta = disc_menu.getItemMeta();
        note_menu_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "WORK IN PROGRESS!!");
        note_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        note_menu.setItemMeta(note_menu_meta);


        for (int integer = 0; integer < itemStacks.size(); integer++){
            ItemStack disc = itemStacks.get(integer);
            //Add disc to gui
            menu.addItem(disc);
        }

        inv = menu;
        player.openInventory(menu);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack CurrentItem = e.getCurrentItem();
        Material material = MusicPlus.getPlugin().getConfig().getItemStack("disc_44444").getType();
        Inventory playerinv = e.getView().getBottomInventory();


        MusicPlus.getPlugin().getLogger().log(Level.INFO, "onMenuClick is triggered");
        if (e.getView().getTopInventory() == inv && (e.getClickedInventory() == inv || e.getClickedInventory() == playerinv)) {
            DiscMenu(player);


            int count = 0;
            for (ItemStack i : player.getInventory()) {
                if (i == null) {
                    count++;
                }
            }

            count = count - 5;
            player.sendMessage(String.valueOf(count));
            if (e.getView().getTitle() == "Disc Manager" && itemStacks.contains(e.getCurrentItem()) && count > 0 && player.hasPermission("musicplus.give")) {
                player.getInventory().addItem(e.getCurrentItem());
            } else if (e.getView().getTitle() == "Disc Manager" && itemStacks.contains(e.getCurrentItem()) &&  count == 0) {
                player.sendMessage(ChatColor.RED + "Sorry you got no space in your inventory");
            } else if (e.getView().getTitle() == "Disc Manager" && itemStacks.contains(e.getCurrentItem()) && !player.hasPermission("musicplus.give")) {
                player.sendMessage(ChatColor.RED + "Sorry you only have the permission to view the Custom Music Discs!");
            }
            e.setCancelled(true);
        }
    }
}
