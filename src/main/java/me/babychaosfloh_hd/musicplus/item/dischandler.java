package me.babychaosfloh_hd.musicplus.item;

import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.entity.Player;

import java.util.logging.Level;

import javax.annotation.Nullable;

public class dischandler {
    private final MusicPlus plugin;
    public dischandler(MusicPlus plugin) {
        this.plugin = plugin;
    }
    private static int modeldata;
    private static ItemMeta discmeta;
    private static int discmodeldata;

    public static Boolean check(Block block, @Nullable ItemStack item, Player player) {
        World world = block.getWorld();
        int x = (int) block.getLocation().getX();
        int y = (int) block.getLocation().getY();
        int  z = (int) block.getLocation().getZ();
        String location = world.getName() + ";" + x + ";" + y + ";" + z;
       @Nullable ItemMeta meta = item.getItemMeta();
       if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().hasItemMeta()) {
           if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
               modeldata = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
           }
       }
       int count = filemanager.getjuke().getInt("count");

       @Nullable ItemStack disc = filemanager.getdisc().getItemStack("disc_" + modeldata);
       if (disc != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
           discmeta = disc.getItemMeta();
           if (discmeta != null) {
               discmodeldata = discmeta.getCustomModelData();

       @Nullable PersistentDataContainer data = discmeta.getPersistentDataContainer();
       @Nullable String message = data.get(new NamespacedKey(MusicPlus.getPlugin(), "message"), PersistentDataType.STRING);
       @Nullable String sound = data.get(new NamespacedKey(MusicPlus.getPlugin(), "sound"), PersistentDataType.STRING);
        //String uuid = data.get(new NamespacedKey(MusicPlus.getPlugin(), "uuid"), PersistentDataType.STRING);
        @Nullable Location pos = block.getLocation();
        @Nullable ItemStack storeItem = player.getInventory().getItemInMainHand();
        @Nullable int ammount = storeItem.getAmount();
        @Nullable PersistentDataContainer storeData = storeItem.getItemMeta().getPersistentDataContainer();
        @Nullable String uuid = storeData.get(new NamespacedKey(MusicPlus.getPlugin(), "uuid"), PersistentDataType.STRING);
        //@Nullable String blank = filemanager.getjuke().getConfigurationSection("discs").getKeys(false).toString();


               //add Custom Disc to juke_storage.yml + remove from inventory
               if (storeItem.hasItemMeta() && storeItem.getItemMeta().hasCustomModelData() && storeItem.getItemMeta().getCustomModelData() == discmodeldata && !filemanager.getjuke().isSet("discs." + location)) {
                   //player.sendMessage(ChatColor.RED + "message " + filemanager.getjuke().get("discs." + location).getClass());
                   world.playSound(pos, "minecraft:sfx.test", 1.0F, 1.0F);
                   Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.INFO, "Played Sound");
                   //player.sendMessage("You clicked a " + block.getType() + " with a " + discmeta.getDisplayName());
                   storeItem.setAmount(1);
                   filemanager.getjuke().set("discs." + location, storeItem);
                   filemanager.getjuke().set("count", count + 1);
                   filemanager.getjuke().options().copyDefaults(true);
                   filemanager.savejuke();
                   //player.sendMessage(item + "added to juke_storage.yml");

            /*
            BlockState blockState = block.getState();
            TileState tileState = (TileState) blockState;
            PersistentDataContainer container = tileState.getPersistentDataContainer();

            if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Name"), PersistentDataType.STRING)){
                container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Name"), PersistentDataType.STRING, uuid);
                tileState.update();
            }
             */

                   storeItem.setAmount(ammount - 1);
                   player.setItemInHand(storeItem);
            /*
            if (MusicPlus.getPlugin().getConfig().isSet("test-features.jukebox_persistent") && MusicPlus.getPlugin().getConfig().getBoolean("test-features.jukebox_persistent")) {
                BlockState blockState = block.getState();
                TileState tileState = (TileState) blockState;
                PersistentDataContainer container = tileState.getPersistentDataContainer();
                MusicPlus.getPlugin().getLogger().log(Level.WARNING, ChatColor.translateAlternateColorCodes('&',"&cYou enabled the Testfeature: \"&l&njukebox_persistent&r\", which might not work as planned!"));
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Name"), PersistentDataType.STRING)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Name"), PersistentDataType.STRING, storeItem.getItemMeta().getDisplayName());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Material"), PersistentDataType.STRING)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Material"), PersistentDataType.STRING, storeItem.getType().toString());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Lore"), PersistentDataType.STRING)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_META_Lore"), PersistentDataType.STRING, storeItem.getItemMeta().getLore().toString());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Amount"), PersistentDataType.INTEGER)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Amount"), PersistentDataType.INTEGER, storeItem.getAmount());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_CustomModelData"), PersistentDataType.INTEGER)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_CustomModelData"), PersistentDataType.INTEGER, storeItem.getItemMeta().getCustomModelData());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Enchants"), PersistentDataType.STRING)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Enchants"), PersistentDataType.STRING, storeItem.getItemMeta().getEnchants().toString());
                }
                if (!container.has(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Enchants"), PersistentDataType.STRING)){
                    container.set(new NamespacedKey(MusicPlus.getPlugin(), "Disc_Enchants"), PersistentDataType.STRING, storeItem.getItemMeta().getItemFlags().toString());
                }

                tileState.update();
            }
             */
               }
           }
       }

        return true;
    }

    public static void del(Player player, Block block) {

        World world = block.getWorld();
        int x = (int) block.getLocation().getX();
        int y = (int) block.getLocation().getY();
        int  z = (int) block.getLocation().getZ();
        String location = world.getName() + ";" + x + ";" + y + ";" + z;
        Location pos = block.getLocation();

        if (filemanager.getjuke().isItemStack("discs." + location)) { //((blank != null && blank != " ") || !filemanager.getjuke().isSet("discs")) || discmodeldata == modeldata || storeItem.getType() == Material.PAPER){

            ItemStack item = filemanager.getjuke().getItemStack("discs." + location);
            item.setAmount(1);

            filemanager.getjuke().set("discs", null);
            filemanager.getjuke().options().copyDefaults(true);
            filemanager.savejuke();
            //player.sendMessage("removed disc!");
            world.dropItemNaturally(pos.add(0,0.5,0), item);
        }
    }
}