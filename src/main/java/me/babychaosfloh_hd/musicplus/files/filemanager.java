package me.babychaosfloh_hd.musicplus.files;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class filemanager {
    private final MusicPlus plugin;
    private static File disc;
    private static File juke;
    private static File note;
    private static File lang;
    private static FileConfiguration jukeFile;
    private static FileConfiguration discFile;
    private static FileConfiguration noteFile;
    private static FileConfiguration langFile;

    public filemanager(MusicPlus plugin) {
        this.plugin = plugin;
    }

    public static void setupjuke() {
        juke = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "juke_storage.yml");
        if (!juke.exists()) {
            try {
                juke.createNewFile();
                MusicPlus.getPlugin().getLogger().log(Level.INFO, "file Created");
            } catch (IOException var1) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Could not create storage file" + var1.getMessage());
            }
        }

        jukeFile = YamlConfiguration.loadConfiguration(juke);
        jukeFile.set("count", 0);
    }

    public static void setupdisc() {
        disc = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "discs.yml");
        if (!disc.exists()) {
            try {
                disc.createNewFile();
            } catch (IOException var1) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Could not create storage file" + var1.getMessage());
            }
        }

        discFile = YamlConfiguration.loadConfiguration(disc);
        if (!discFile.isInt("count")) {
            discFile.set("count", 0);
        }

    }

    public static void setupnote() {
        note = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "note_storage.yml");
        if (!note.exists()) {
            try {
                note.createNewFile();
                MusicPlus.getPlugin().getLogger().log(Level.INFO, "file Created");
            } catch (IOException var1) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Could not create storage file" + var1.getMessage());
            }
        }

        noteFile = YamlConfiguration.loadConfiguration(note);
        if (!noteFile.isInt("count")) {
            noteFile.set("count", 0);
        }

    }

    public static void setuplang() {
        lang = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "language.yml");
        if (!lang.exists()) {
            try {
                lang.createNewFile();
                MusicPlus.getPlugin().getLogger().log(Level.INFO, "file Created");
            } catch (IOException var1) {
                Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Could not create storage file" + var1.getMessage());
            }
        }

        langFile = YamlConfiguration.loadConfiguration(lang);
    }

    public static FileConfiguration getjuke() {
        return jukeFile;
    }

    public static FileConfiguration getlang() {
        return langFile;
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
        } catch (IOException var1) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file" + jukeFile);
        }
        //jukeFile = YamlConfiguration.loadConfiguration(juke);
    }

    public static void savelang() {
        try {
            langFile.save(lang);
        } catch (IOException var1) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file" + langFile);
        }

    }

    public static void savedisc() {
        try {
            discFile.save(disc);
        } catch (IOException var1) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file" + discFile);
        }

    }

    public static void savenote() {
        try {
            noteFile.save(note);
        } catch (IOException var1) {
            Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.WARNING, "Couldn't save file" + noteFile);
        }

    }

    public static void reload() {
        jukeFile = YamlConfiguration.loadConfiguration(juke);
        discFile = YamlConfiguration.loadConfiguration(disc);
        noteFile = YamlConfiguration.loadConfiguration(note);
        //langFile = YamlConfiguration.loadConfiguration(lang);
        //language.reload();
    }
}