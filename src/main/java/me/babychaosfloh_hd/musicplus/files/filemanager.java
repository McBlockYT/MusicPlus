package me.babychaosfloh_hd.musicplus.files;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.ChatColor;

import java.util.logging.Level;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.bukkit.plugin.java.JavaPlugin;

public class filemanager {

    private final MusicPlus plugin;

    public filemanager(MusicPlus plugin) {
        this.plugin = plugin;
    }

    private static File disc;
    private static File juke;
    private static File note;
    private static FileConfiguration jukeFile;
    private static FileConfiguration discFile;
    private static FileConfiguration noteFile;

    // Finds or generates the custom config file
    public static void setupjuke() {
        juke = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "juke_storage.yml");

        if (!juke.exists()) {
            try {
                juke.createNewFile();
                MusicPlus.getPlugin().getLogger().log(Level.INFO, "file Created");
            } catch (IOException e) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING,
                        "Could not create storage file" + e.getMessage());
            }
        }
        jukeFile = YamlConfiguration.loadConfiguration(juke);
        //if (!jukeFile.isSet("count")) {
            jukeFile.set("count", 0);
            //jukeFile.createSection("discs");
        //}
    }

    public static void setupdisc() {
        disc = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "discs.yml");

        if (!disc.exists()) {
            try {
                disc.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING,
                        "Could not create storage file" + e.getMessage());
            }
        }
        discFile = YamlConfiguration.loadConfiguration(disc);
        discFile.set("count", 0);
    }

    public static void setupnote() {
        note = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "note_storage.yml");

        if (!note.exists()) {
            try {
                note.createNewFile();
                MusicPlus.getPlugin().getLogger().log(Level.INFO, "file Created");
            } catch (IOException e) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING,
                        "Could not create storage file" + e.getMessage());
            }
        }
        noteFile = YamlConfiguration.loadConfiguration(note);
    }

    public static FileConfiguration getjuke() {
        return jukeFile;
    }
    public static FileConfiguration getdisc() {
        return discFile;
    }

    public static FileConfiguration getnote() {
        return noteFile;
    }

    public static void savejuke() {
        try {
            jukeFile.save(juke);
            } catch (IOException e) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file");
            }
    }

    public static void savedisc() {
        try {
            discFile.save(disc);
        } catch (IOException e) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file");
        }
    }

    public static void savenote() {
        try {
            noteFile.save(note);
        } catch (IOException e) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file");
        }
    }

    public static void reload() {
        jukeFile = YamlConfiguration.loadConfiguration(juke);
        discFile = YamlConfiguration.loadConfiguration(disc);
        noteFile = YamlConfiguration.loadConfiguration(note);
    }
}