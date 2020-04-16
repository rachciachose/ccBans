// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans;

import pl.best241.ccbans.commands.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import pl.best241.rdbplugin.pubsub.PubSub;
import pl.best241.ccbans.parser.GsonParser;
import pl.best241.ccbans.data.BanPlayerData;
import pl.best241.ccbans.listeners.PubSubListener;
import org.bukkit.event.Listener;
import pl.best241.ccbans.listeners.PlayerListener;
import pl.best241.ccbans.messages.MessagesData;
import pl.best241.ccbans.data.DataStore;
import pl.best241.ccbans.backend.RedisBackend;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import pl.best241.ccbans.backend.Backend;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CcBans extends JavaPlugin
{
    private static Plugin plugin;
    private static Backend backend;
    
    public void onEnable() {
        if (!this.getServer().getPluginManager().isPluginEnabled("rdbPlugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "rdbPlugin not enabled! ccBans not started!");
            this.setEnabled(false);
        }
        CcBans.plugin = (Plugin)this;
        CcBans.backend = new RedisBackend();
        DataStore.loadAll();
        MessagesData.loadMessages((Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PubSubListener(), (Plugin)this);
    }
    
    public static Plugin getPlugin() {
        return CcBans.plugin;
    }
    
    public static Backend getBackend() {
        return CcBans.backend;
    }
    
    public static void publishBanPlayer(final BanPlayerData data) {
        PubSub.broadcast("ccBans.playerBanned", GsonParser.parseToJson(data));
    }
    
    public static void publishUnbanPlayer(final UUID uuid) {
        PubSub.broadcast("ccBans.playerUnban", uuid.toString());
    }
    
    public static void publishBanIp(final String address) {
        PubSub.broadcast("ccBans.ipBanned", address);
    }
    
    public static void publishUnbanIp(final String address) {
        PubSub.broadcast("ccBans.ipunbanned", address);
    }
    
    public static void publishKick(final UUID uuid) {
        PubSub.broadcast("ccBans.kick", uuid.toString());
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String lable, final String[] args) {
        CommandExecutor.handleCommand(sender, cmd.getName(), args);
        return false;
    }
}
