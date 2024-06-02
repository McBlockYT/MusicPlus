package me.babychaosfloh_hd.musicplus.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.download.language;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class guimanager implements Listener {
    private final MusicPlus plugin;
    private static final MusicPlus that = MusicPlus.getPlugin();
    private static List<ItemStack> itemStacks;
    public static Boolean open = false;
    private static List<String> lore = new ArrayList();
    public static String confriminv;
    public static ItemStack delItem;
    public static Inventory conf;
    private static Inventory inv;
    public static String local;
    private static int slots;

    public guimanager(MusicPlus plugin) {
        this.plugin = plugin;
    }

    public static void MainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 45, language.getlang(local).getString("menu_music_manager_title"));
        ItemStack disc_menu = new ItemStack(Material.PAPER, 1);
        ItemMeta disc_menu_meta = disc_menu.getItemMeta();
        disc_menu_meta.setDisplayName(language.getlang(local).getString("menu_music_manager_disc_manager"));
        disc_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        disc_menu.setItemMeta(disc_menu_meta);
        ItemStack note_menu = new ItemStack(Material.PAPER, 1);
        ItemMeta note_menu_meta = disc_menu.getItemMeta();
        note_menu_meta.setDisplayName(language.getlang(local).getString("menu_music_manager_note_manager"));
        note_menu_meta.setCustomModelData(MusicPlus.getPlugin().getConfig().getInt("test-features.disc_menu_model"));
        note_menu.setItemMeta(note_menu_meta);
        ItemStack close = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + language.getlang(local).getString("menu_music_manager_close"));
        close.setItemMeta(closeMeta);
        menu.setItem(20, disc_menu);
        menu.setItem(24, note_menu);
        menu.setItem(44, close);
        inv = menu;
        player.openInventory(menu);
    }

    public static void DiscMenu(Player player) {
        ConfigurationSection rootSection = filemanager.getdisc().getRoot();
        itemStacks = new ArrayList(filemanager.getdisc().getInt("count"));
        int i = 0;
        Iterator var3 = rootSection.getKeys(false).iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            if (filemanager.getdisc().isItemStack(key)) {
                ItemStack itemStack = rootSection.getItemStack(key);
                itemStacks.add(itemStack);
                ++i;
                if (i == filemanager.getdisc().getInt("count")) {
                    break;
                }
            }
        }

        double count = filemanager.getdisc().getDouble("count");

        for(slots = 9; count / 9.0D - (double)((int)count / 9) != 0.0D; ++count) {
        }

        if (count / 9.0D - (double)((int)count / 9) != 0.0D) {
            that.getLogger().log(Level.INFO, "Decimal value");
        } else {
            that.getLogger().log(Level.INFO, "Integer value");
        }

        if (count > (double)(slots - 1) && count < 54.0D) {
            slots = (int)count;
        }

        that.getLogger().log(Level.WARNING, "Disc Slots" + slots);
        Inventory menu = Bukkit.createInventory(player, slots, language.getlang(local).getString("menu_disc_manager_title"));

        for(int integer = 0; integer < itemStacks.size(); ++integer) {
            ItemStack disc = (ItemStack)itemStacks.get(integer);
            menu.addItem(new ItemStack[]{disc});
        }

        ItemStack back = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + language.getlang(local).getString("menu_any_back"));
        back.setItemMeta(backMeta);
        inv = menu;
        menu.setItem((int)count - 1, back);
        player.openInventory(menu);
    }

    public static void NoteMenu(Player player) {
        ConfigurationSection rootSection = filemanager.getnote().getRoot();
        itemStacks = new ArrayList(filemanager.getnote().getInt("count"));
        int i = 0;
        Iterator var3 = rootSection.getKeys(false).iterator();

        ItemStack back;
        ItemMeta backMeta;
        while(var3.hasNext()) {
            String key = (String)var3.next();
            that.getLogger().log(Level.INFO, key);
            if (filemanager.getnote().isString(key) && Material.matchMaterial(key) != null) {
                that.getLogger().log(Level.INFO, "Deserializing the item data");
                Material material = Material.valueOf(key);
                that.getLogger().log(Level.INFO, material.toString());
                back = new ItemStack(material);
                backMeta = back.getItemMeta();
                backMeta.setDisplayName(rootSection.getString(key));
                back.setItemMeta(backMeta);
                that.getLogger().log(Level.INFO, back.toString());
                itemStacks.add(back);
                ++i;
                if (i == filemanager.getnote().getInt("count")) {
                    break;
                }
            }
        }

        double count = filemanager.getnote().getDouble("count");

        for(slots = 9; count / 9.0D - (double)((int)count / 9) != 0.0D; ++count) {
        }

        if (count > (double)(slots - 2) && count < 54.0D) {
            slots = (int)count;
        }

        Inventory menu = Bukkit.createInventory(player, slots, language.getlang(local).getString("menu_note_manager_title"));
        back = new ItemStack(Material.STRUCTURE_VOID);
        backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + language.getlang(local).getString("menu_any_back"));
        back.setItemMeta(backMeta);
        ItemStack add = new ItemStack(Material.BRUSH);
        ItemMeta addMeta = add.getItemMeta();
        addMeta.setDisplayName(ChatColor.GREEN + language.getlang(local).getString("menu_note_manager_add_notes"));
        add.setItemMeta(addMeta);
        menu.setItem((int)count - 1, back);
        menu.setItem((int)count - 2, add);
        lore = language.getlang(local).getStringList("menu_note_manager_item_lore");
        that.getLogger().info(lore.toString());

        for(int integer = 0; integer < itemStacks.size(); ++integer) {
            ItemStack note = (ItemStack)itemStacks.get(integer);
            ItemMeta noteMeta = note.getItemMeta();
            noteMeta.setLore(lore);
            note.setItemMeta(noteMeta);
            menu.addItem(new ItemStack[]{note});
        }

        inv = menu;
        player.openInventory(menu);
    }

    public static void NoteAddGui(Player player) {
        Inventory menu = Bukkit.createInventory(player, InventoryType.DISPENSER, language.getlang(local).getString("menu_note_add_material_title"));
        ItemStack blocked = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta blockdMeta = blocked.getItemMeta();
        blockdMeta.setDisplayName(" ");
        blocked.setItemMeta(blockdMeta);
        menu.setItem(0, blocked);
        menu.setItem(1, blocked);
        menu.setItem(2, blocked);
        menu.setItem(3, blocked);
        menu.setItem(5, blocked);
        menu.setItem(6, blocked);
        menu.setItem(7, blocked);
        menu.setItem(8, blocked);
        inv = menu;
        player.openInventory(menu);
    }

    public static void confirmdelGui(Player player, ItemStack item) {
        delItem = item;
        Inventory menu = Bukkit.createInventory(player, 27, language.getlang(local).getString("menu_confirm_title"));
        ItemStack confirm = new ItemStack(Material.RED_WOOL);
        ItemMeta confMeta = confirm.getItemMeta();
        confMeta.setDisplayName(language.getlang(local).getString("menu_confirm_confirm"));
        confirm.setItemMeta(confMeta);
        ItemStack cancel = new ItemStack(Material.GREEN_WOOL);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(language.getlang(local).getString("menu_confirm_cancel"));
        cancel.setItemMeta(cancelMeta);
        menu.setItem(13, item);
        menu.setItem(11, confirm);
        menu.setItem(15, cancel);
        inv = menu;
        player.openInventory(menu);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        if (!open) {
            conf = null;
            inv = null;
        } else {
            open = false;
        }

    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();
        ItemStack CurrentItem = e.getCurrentItem();
        Material material = MusicPlus.getPlugin().getConfig().getItemStack("disc_44444").getType();
        Inventory playerinv = e.getView().getBottomInventory();
        if (e.getView().getTopInventory().equals(inv) && (e.getClickedInventory().equals(inv) || e.getClickedInventory().equals(playerinv))) {
            that.getLogger().log(Level.INFO, CurrentItem.toString());
            that.getLogger().log(Level.INFO, CurrentItem.getItemMeta().toString());
            if (CurrentItem.getItemMeta().getDisplayName().equalsIgnoreCase(language.getlang(local).getString("menu_music_manager_disc_manager"))) {
                open = true;
                DiscMenu(player);
            } else if (CurrentItem.getItemMeta().getDisplayName().equalsIgnoreCase(language.getlang(local).getString("menu_music_manager_note_manager"))) {
                open = true;
                NoteMenu(player);
            } else if (CurrentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + language.getlang(local).getString("menu_any_back"))) {
                open = true;
                MainMenu(player);
            } else if (CurrentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + language.getlang(local).getString("menu_music_manager_close"))) {
                player.closeInventory();
            } else if (itemStacks.contains(e.getCurrentItem()) && e.getView().getTitle().equals(language.getlang(local).getString("menu_note_manager_title"))) {
                open = true;
                confirmdelGui(player, e.getCurrentItem());
            } else if (CurrentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + language.getlang(local).getString("menu_note_manager_add_notes"))) {
                open = true;
                NoteAddGui(player);
            }

            int count = 0;
            ListIterator var7 = player.getInventory().iterator();

            while(var7.hasNext()) {
                ItemStack i = (ItemStack)var7.next();
                if (i == null) {
                    ++count;
                }
            }

            count -= 5;
            player.sendMessage(String.valueOf(count));
            if (e.getView().getTitle().equals(language.getlang(local).getString("menu_disc_manager_title")) && itemStacks.contains(e.getCurrentItem()) && count > 0 && player.hasPermission("musicplus.give")) {
                player.getInventory().addItem(new ItemStack[]{e.getCurrentItem()});
            } else if (e.getView().getTitle().equals(language.getlang(local).getString("menu_disc_manager_title")) && itemStacks.contains(e.getCurrentItem()) && count == 0) {
                player.sendMessage(ChatColor.RED + "Sorry you got no space in your inventory");
            } else if (e.getView().getTitle().equals(language.getlang(local).getString("menu_disc_manager_title")) && itemStacks.contains(e.getCurrentItem()) && !player.hasPermission("musicplus.give")) {
                player.sendMessage(ChatColor.RED + "Sorry you only have the permission to view the Custom Music Discs!");
            } else if (e.getView().getTitle().equals(language.getlang(local).getString("menu_confirm_title")) && e.getCurrentItem().getType().equals(Material.RED_WOOL)) {
                that.getLogger().warning("deleting: " + delItem);
                filemanager.getnote().set(delItem.getType().toString(), null);
                filemanager.savenote();
                filemanager.reload();
                open = true;
                NoteMenu(player);
            }

            if (e.getView().getTitle().equals(language.getlang(local).getString("menu_note_add_material_title"))) {
                if (CurrentItem.getType().isBlock()) {
                    ItemStack clone = CurrentItem.clone();
                    ItemMeta cloneMeta = clone.getItemMeta();
                    cloneMeta.setLore(language.getlang(local).getStringList("menu_note_add_material_item_lore"));
                    clone.setItemMeta(cloneMeta);
                    inv.setItem(4, clone);
                }
                if (e.getSlot() == 4 && e.isLeftClick()) {
                    keyboard.keyboard(player, e.getCurrentItem().clone());
                } else if (e.getSlot() == 4 && e.isShiftClick() && e.isRightClick()) {
                    open = true;
                    NoteMenu(player);
                }
            }

            e.setCancelled(true);
        }

    }
}