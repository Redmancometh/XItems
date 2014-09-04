package Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
public class DBUtil
{
    public static void Setup(java.sql.Connection c) throws SQLException
    {
	Statement statement = c.createStatement();
	statement.execute("CREATE TABLE Inventories(uuid varchar(50), items varchar(500), armor varchar(100), enderchest varchar(500));");    
    }
    public static void writeItemsInitial(Player p) throws SQLException
    {
	String inv = Util.InventoryStringDeSerializer.InventoryToString(p.getInventory());
	String uuid = p.getUniqueId().toString();
	String enderchest = Util.InventoryStringDeSerializer.InventoryToString(p.getEnderChest());
	Inventory armor = Bukkit.getServer().createInventory(null, 9);
	armor.addItem(p.getInventory().getArmorContents());
	String armorString = Util.InventoryStringDeSerializer.InventoryToString(armor);
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("INSERT INTO Inventories(uuid, items, armor, enderchest) VALUES (?,?,?,?)");
	ps.setString(1, uuid);
	ps.setString(2, inv);
	ps.setString(3, armorString);
	ps.setString(4, enderchest);
	ps.execute();
    }
    public static void writeItems(Player p) throws SQLException
    {
	String inv = Util.InventoryStringDeSerializer.InventoryToString(p.getInventory());
	String enderchest = Util.InventoryStringDeSerializer.InventoryToString(p.getEnderChest());
	Inventory armor = Bukkit.getServer().createInventory(null, 9);
	armor.addItem(p.getInventory().getArmorContents());
	String armorString = Util.InventoryStringDeSerializer.InventoryToString(armor);
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("UPDATE Inventories SET items=?, armor=?, enderchest=? WHERE uuid=?");
	ps.setString(1, inv);
	ps.setString(2, armorString);
	ps.setString(3, enderchest);
	ps.setString(4, uuid);
	ps.execute();
    }
    public static String getItems(Player p) throws SQLException
    {
	String inv = "";
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("SELECT items FROM Inventories where uuid=?;");
	ps.setString(1, uuid);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){return rs.getString(1);}
	return inv;
    }
    public static String getArmor(Player p) throws SQLException
    {
	String inv = "";
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("SELECT armor FROM Inventories where uuid=?;");
	ps.setString(1, uuid);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){return rs.getString(1);}
	return inv;
    }
    public static String getEnderChest(Player p) throws SQLException
    {
	String inv = "";
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("SELECT enderchest FROM Inventories where uuid=?;");
	ps.setString(1, uuid);
	ResultSet rs = ps.executeQuery();
	while(rs.next()){return rs.getString(1);}
	return inv;
    }
    public static boolean doesPlayerExist(Player p) throws SQLException
    {
	String uuid = p.getUniqueId().toString();
	PreparedStatement ps = null;
	ps = XItems.XItems.getConnection().prepareStatement("SELECT items FROM Inventories where UUID=?");
	ps.setString(1, uuid);
	ResultSet rs=ps.executeQuery();
	if(rs.next())
	{
	    if(rs.getString(1).contains(";"))
	    {return true;}
	    else{return false;}}
	else{return false;}
    }
}
