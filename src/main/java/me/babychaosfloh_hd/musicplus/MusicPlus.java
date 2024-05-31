package me.babychaosfloh_hd.musicplus;

import jdk.internal.foreign.Utils;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import me.babychaosfloh_hd.musicplus.item.debugitem;
import me.babychaosfloh_hd.musicplus.listener.jukeboxlistener;
import me.babychaosfloh_hd.musicplus.listener.noteblocklistener;
import me.babychaosfloh_hd.musicplus.gui.guimanager;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;


import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.Material;

import org.bukkit.permissions.Permission;

import java.util.*;
//import java.util.Arrays;
//import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

import java.time.LocalDate;
import java.util.logging.Logger;

public class MusicPlus extends JavaPlugin {

    private static MusicPlus plugin;
    public static List<String> testfeatures;


    @Override
    public void onEnable() {
        plugin = this;

        getLogger().log(Level.INFO, "Plugins booting up!");
        getServer().getPluginManager().registerEvents(new jukeboxlistener(this), this);
        getServer().getPluginManager().registerEvents(new noteblocklistener(this), this);
        getServer().getPluginManager().registerEvents(new guimanager(this), this);
        getServer().getPluginManager().registerEvents(new debugitem(this), this);

        this.saveDefaultConfig();

        filemanager.setupjuke();
        filemanager.setupdisc();
        filemanager.setupnote();

        if (getConfig().isList("test_features")) {
            testfeatures = new ArrayList<>(getConfig().getStringList("test_features"));
        }
    }
    @Override
    public void onDisable() {
        long startTime = System.currentTimeMillis();
        getLogger().log(Level.INFO, "Stoping Plugin!");
        long endTime = System.currentTimeMillis();
        long elapsedTimeMillis = (endTime - startTime); // Calaculate the time
        getLogger().log(Level.INFO, "Took " + elapsedTimeMillis + " ms to stop the plugin");
        //filemanager.savedisc();
        //filemanager.savejuke();

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("getpaper") && getConfig().isSet("disc_44444") && getConfig().isItemStack("disc_44444")) {

            //int testdata = getConfig().getInt("disc_44444.custommodeldata");
            //String name = ChatColor.translateAlternateColorCodes('&', getConfig().getString("disc_44444.name"));

            //checking to see what command was run
            //if (label.equalsIgnoreCase("getpaper")){}

            getLogger().log(Level.INFO, "I have been run by someone.");

            //Check to see if a Player ran the command or something else
            if (sender instanceof Player && sender.hasPermission("musicplus.test")){

                Player p = (Player) sender;
                ItemStack item = new ItemStack(getConfig().getItemStack("disc_44444"));
                ItemMeta meta = item.getItemMeta();
                int CustomModelData = meta.getCustomModelData();
                UUID uuid = UUID.randomUUID();

                PersistentDataContainer data = meta.getPersistentDataContainer();
                data.set(new NamespacedKey(this, "uuid"), PersistentDataType.STRING, uuid.toString());

                item.setItemMeta(meta);
                p.getInventory().addItem(item);
                //p.sendMessage(ChatColor.GREEN + "You got a PAPER.");

            }
            else if(!sender.hasPermission("musicplus.test")) {
                getLogger().log(Level.WARNING, "You don't have the permission \"musicplus.test\"!");
            }
            else if (sender instanceof ConsoleCommandSender){

                getLogger().log(Level.WARNING, "ONLY a player can run this!");

            }else if(sender instanceof BlockCommandSender){

                getLogger().log(Level.WARNING, "ONLY a Player can run this!");

            }

        }
        else if (!getConfig().isSet("disc_44444") || !getConfig().isItemStack("disc_44444")) {
            getLogger().log(Level.WARNING, "Could not find \"disc_44444\" in config.yml/Is not an ItemStack!");
        }

        if (command.getName().equalsIgnoreCase("music_menu")) {
            if (sender instanceof Player && sender.hasPermission("musicplus.menu")) {
                Player player = (Player) sender;
                guimanager.MainMenu(player);

            }else if (sender instanceof ConsoleCommandSender){

                getLogger().log(Level.WARNING, "ONLY a player can run this!");

            }else if(sender instanceof BlockCommandSender){

                getLogger().log(Level.WARNING, "ONLY a Player can run this!");

            }
        }

        if (command.getName().equalsIgnoreCase("MsP-reload")) {
            Player p = (Player) sender;
            filemanager.reload();
        }


        //return true if the command was used correctly, but im generally just return true no matter what
        return true;
    }

    public static MusicPlus getPlugin(){
        return plugin;
    }

}
