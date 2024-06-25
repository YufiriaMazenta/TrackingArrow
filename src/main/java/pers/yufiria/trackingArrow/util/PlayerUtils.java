package pers.yufiria.trackingArrow.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {

    public static boolean handBow(Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if (handItem.getType().equals(Material.BOW))
            return true;
        if (handItem.getType().equals(Material.CROSSBOW))
            return true;
        if (offHandItem.getType().equals(Material.BOW))
            return true;
        return offHandItem.getType().equals(Material.CROSSBOW);
    }

}
