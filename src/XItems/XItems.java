package XItems;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Listeners.LogEvent;
import Util.DBUtil;
import code.husky.mysql.MySQL;

public class XItems extends JavaPlugin
{
    BukkitTask connect;
    public static MySQL MySQL;
    public static Plugin pl;
    public static Connection c;

    public void onEnable()
    {
	pl=this;
	PluginManager pm = Bukkit.getServer().getPluginManager();
	MySQL=new MySQL((Plugin) this, "localhost", "3306", "items", "root", "Thunder11284!");
	MySQL.openConnection();
	c=MySQL.getConnection();
	connect = new BukkitRunnable(){public void run(){if (!MySQL.checkConnection()){XItems.openConnect();}}}.runTaskTimer(this, 20, 60);
	pm.registerEvents(new LogEvent(), this);
    }
    public static void openConnect(){MySQL.openConnection();}
    public static Connection getConnection(){return c;}
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	if(cmd.getName().equalsIgnoreCase("setup"))
	{
	    try
	    {
		DBUtil.Setup(c);
	    }
	    catch (SQLException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	if(cmd.getName().equalsIgnoreCase("write"))
	{
	    try
	    {
		DBUtil.writeItems((Player)sender);
	    }
	    catch (SQLException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return true;
    }
}
