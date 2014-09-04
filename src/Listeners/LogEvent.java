package Listeners;

import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import Util.DBUtil;
import Util.InventoryStringDeSerializer;

public class LogEvent implements Listener
{
    @EventHandler
    public void writeToDB(final PlayerQuitEvent e) throws SQLException
    {
	Player p = e.getPlayer();
	if (DBUtil.doesPlayerExist(p))
	{
	    DBUtil.writeItems(e.getPlayer());
	}
	else
	{
	    DBUtil.writeItemsInitial(e.getPlayer());
	}
    }

    @EventHandler
    public void setInv(final PlayerLoginEvent e) throws SQLException
    {
	if (DBUtil.doesPlayerExist(e.getPlayer()))
	{
	    String invraw = DBUtil.getItems(e.getPlayer());
	    String armorraw = DBUtil.getArmor(e.getPlayer());
	    String enderraw = DBUtil.getEnderChest(e.getPlayer());
	    final Inventory inv = InventoryStringDeSerializer.StringToInventory(invraw);
	    final Inventory armor = InventoryStringDeSerializer.StringToInventory(armorraw);
	    final Inventory enderchest = InventoryStringDeSerializer.StringToInventory(enderraw);
	    new BukkitRunnable()
	    {
		public void run()
		{
		    ItemStack[] armors = armor.getContents();
		    Player p = e.getPlayer();
		    p.getInventory().setContents(inv.getContents());
		    p.getEnderChest().setContents(enderchest.getContents());
		    setArmor(p, armors);
		    p.updateInventory();
		}
	    }.runTask(XItems.XItems.pl);
	}
    }

    public void setArmor(Player p, ItemStack[] armor)
    {
	p.getInventory().setBoots(armor[0]);
	p.getInventory().setLeggings(armor[1]);
	p.getInventory().setChestplate(armor[2]);
	p.getInventory().setHelmet(armor[3]);
    }
}
