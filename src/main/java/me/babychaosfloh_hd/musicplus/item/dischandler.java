package me.babychaosfloh_hd.musicplus.item;

import java.util.logging.Level;
import javax.annotation.Nullable;

import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.morepersistentdatatypes.DataType;
import me.babychaosfloh_hd.musicplus.MusicPlus;
import me.babychaosfloh_hd.musicplus.files.filemanager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class dischandler {
    private final MusicPlus plugin;
    private static final MusicPlus that = MusicPlus.getPlugin();
    private static int modeldata;
    private static ItemMeta discmeta;
    private static int discmodeldata;

    public dischandler(MusicPlus plugin) {
        this.plugin = plugin;
    }

    public static Boolean check(Block block, @Nullable ItemStack item, Player player) {
        World world = block.getWorld();
        int x = (int)block.getLocation().getX();
        int y = (int)block.getLocation().getY();
        int z = (int)block.getLocation().getZ();
        String location = world.getName() + ";" + x + ";" + y + ";" + z;
        ItemMeta meta = item.getItemMeta();
        if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
            modeldata = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
        }

        if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() == Material.PAPER) {
            PersistentDataContainer data = new CustomBlockData(block, that);
            ItemStack disc = item.clone();
            disc.setAmount(1);
            if (!data.has(new NamespacedKey(that, "disc"), DataType.ITEM_STACK)) {
                data.set(new NamespacedKey(that, "disc"), DataType.ITEM_STACK, disc);
                player.sendMessage("Inserted!");
                ItemStack storeItem = player.getInventory().getItemInMainHand();
                int ammount = storeItem.getAmount();
                storeItem.setAmount(ammount - 1);
                player.setItemInHand(storeItem);
            }
        }

        /*
        int count = filemanager.getjuke().getInt("count");
        ItemStack disc = filemanager.getdisc().getItemStack("disc_" + modeldata);
        if (disc != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            discmeta = disc.getItemMeta();
            if (discmeta != null) {
                discmodeldata = discmeta.getCustomModelData();
                PersistentDataContainer data = discmeta.getPersistentDataContainer();
                String message = (String)data.get(new NamespacedKey(MusicPlus.getPlugin(), "message"), PersistentDataType.STRING);
                String sound = (String)data.get(new NamespacedKey(MusicPlus.getPlugin(), "sound"), PersistentDataType.STRING);
                Location pos = block.getLocation();
                ItemStack storeItem = player.getInventory().getItemInMainHand();
                int ammount = storeItem.getAmount();
                PersistentDataContainer storeData = storeItem.getItemMeta().getPersistentDataContainer();
                String uuid = (String)storeData.get(new NamespacedKey(MusicPlus.getPlugin(), "uuid"), PersistentDataType.STRING);
                if (storeItem.hasItemMeta() && storeItem.getItemMeta().hasCustomModelData() && storeItem.getItemMeta().getCustomModelData() == discmodeldata && !filemanager.getjuke().isSet("discs." + location)) {
                    world.playSound(pos, "minecraft:sfx.test", 1.0F, 1.0F);
                    Bukkit.getServer().getPluginManager().getPlugin("MusicPlus").getLogger().log(Level.INFO, "Played Sound");
                    storeItem.setAmount(1);
                    filemanager.getjuke().set("discs." + location, storeItem);
                    filemanager.getjuke().set("count", count + 1);
                    filemanager.getjuke().options().copyDefaults(true);
                    filemanager.savejuke();
                    storeItem.setAmount(ammount - 1);
                    player.setItemInHand(storeItem);
                }
            }
        }
        */

        return true;
    }

    public static void del(Player player, Block block) {
        World world = block.getWorld();
        int x = (int)block.getLocation().getX();
        int y = (int)block.getLocation().getY();
        int z = (int)block.getLocation().getZ();
        String location = world.getName() + ";" + x + ";" + y + ";" + z;
        Location pos = block.getLocation();

        PersistentDataContainer data = new CustomBlockData(block, that);
        if (data.has(new NamespacedKey(that, "disc"), DataType.ITEM_STACK)) {
            ItemStack drop = data.get(new NamespacedKey(that, "disc"), DataType.ITEM_STACK);
            //world.dropItemNaturally(pos.add(0.0D, 0.5D, 0.0D), drop);
            data.remove(new NamespacedKey(that, "disc"));
            player.sendMessage("removed");
        }

        /*
        if (filemanager.getjuke().isItemStack("discs." + location)) {

            player.sendMessage(location);
            player.sendMessage(filemanager.getjuke().getItemStack("discs." + location).toString());

            ItemStack item = filemanager.getjuke().getItemStack("discs." + location);
            item.setAmount(1);
            world.dropItemNaturally(pos.add(0.0D, 0.5D, 0.0D), item);
            filemanager.getjuke().set("discs." + location, null);
            //filemanager.getjuke().options().copyDefaults(true);
            filemanager.savejuke();
        }
         */

    }
}