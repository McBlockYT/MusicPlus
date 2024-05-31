package me.babychaosfloh_hd.musicplus.download;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class language {
    private static final MusicPlus that = MusicPlus.getPlugin();
    private static FileConfiguration langFile;
    private static FileConfiguration returnFile;
    private static Boolean result;
    private static File lang;
    private static File rtrn;

    public static boolean download(String local) {
        lang = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "local/" + local + ".yml");
        String version = MusicPlus.getPlugin().getDescription().getVersion();
        if (!lang.exists()) {
            try {
                String rawUrl = "https://raw.githubusercontent.com/McBlockYT/MusicPlus/master/v" + version + "/language/" + local + ".yml";
                URL url = new URL(rawUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String localFilePath = Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder() + "/local/" + local + ".yml";
                BufferedWriter writer = new BufferedWriter(new FileWriter(localFilePath));

                String line;
                while((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }

                reader.close();
                writer.close();
                that.getLogger().info("File downloaded successfully!");
                result = true;
            } catch (Exception var8) {
                that.getLogger().warning(var8.getMessage());
                result = false;
                download("en_us");
            }
        } else {
            result = true;
        }

        langFile = YamlConfiguration.loadConfiguration(lang);
        return result;
    }

    public static FileConfiguration getlang(String local) {
        if ((new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "local/" + local + ".yml")).exists()) {
            rtrn = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "local/" + local + ".yml");
            returnFile = YamlConfiguration.loadConfiguration(rtrn);
        } else {
            rtrn = new File(Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getDataFolder(), "local/en_us.yml");
            returnFile = YamlConfiguration.loadConfiguration(rtrn);
        }

        return returnFile;
    }

    public static void reload() {
        returnFile = YamlConfiguration.loadConfiguration(rtrn);
    }
}