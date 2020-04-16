// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import pl.best241.rdbplugin.pubsub.PubSub;
import pl.best241.ccbans.CcBans;
import java.util.Date;
import java.text.DateFormat;
import org.bukkit.ChatColor;
import pl.best241.ccbans.messages.MessagesData;
import org.bukkit.Bukkit;
import pl.best241.ccbans.data.DataStore;
import pl.best241.ccbans.data.BanPlayerData;
import pl.best241.ccbans.data.BanType;
import java.util.UUID;
import pl.best241.ccbans.parser.GsonParser;
import java.util.LinkedHashMap;
import pl.best241.rdbplugin.events.PubSubRecieveMessageEvent;
import org.bukkit.event.Listener;

public class PubSubListener implements Listener
{
    @EventHandler
    public static void onMessageRecieve(final PubSubRecieveMessageEvent event) {
        if (event.getPayload().getSubChannel().equals("ccBans.playerBanned")) {
            final LinkedHashMap<String, Object> rawData = (LinkedHashMap<String, Object>)GsonParser.parseToObject(event.getPayload().getMessage());
            final UUID uuid = UUID.fromString(rawData.get("uuid"));
            final String banAdmin = rawData.get("banAdmin");
            final BanType banType = BanType.valueOf(rawData.get("banType"));
            final Long time = (long)(Object)rawData.get("time");
            final String reason = rawData.get("reason");
            final BanPlayerData data = new BanPlayerData(uuid, banAdmin, banType, time, reason);
            DataStore.setBanPlayerData(data);
            final Player player = Bukkit.getPlayer(data.getBannedUUID());
            if (player != null && player.isOnline()) {
                if (data.getBanType() == BanType.PERM) {
                    kickPlayer(player, MessagesData.permBanCantJoinServer.replace("%nick", data.getBanAdminNick()).replace("%reason", data.getReason()));
                }
                else if (data.getBanType() == BanType.TEMP) {
                    kickPlayer(player, MessagesData.tempBanCantJoinServer.replace("%nick", data.getBanAdminNick()).replace("%reason", data.getReason()).replace("%time", ChatColor.GOLD + DateFormat.getInstance().format(new Date(data.getTime()))));
                }
            }
        }
        else if (event.getPayload().getSubChannel().equals("ccBans.playerUnban")) {
            final UUID uuid2 = UUID.fromString(event.getPayload().getMessage());
            DataStore.removeBanPlayerData(uuid2);
        }
        else if (event.getPayload().getSubChannel().equals("ccBans.ipBanned")) {
            DataStore.addIpBan(event.getPayload().getMessage());
        }
        else if (event.getPayload().getSubChannel().equals("ccBans.ipunbanned")) {
            DataStore.removeIpBan(event.getPayload().getMessage());
        }
        else if (event.getPayload().getSubChannel().equals("ccBans.kick")) {
            final Player player2 = Bukkit.getPlayer(UUID.fromString(event.getPayload().getMessage()));
            if (player2 != null && player2.isOnline()) {
                kickPlayer(player2, MessagesData.youWereKickedFromServer);
            }
        }
        else if (event.getChannel().equals("reloadAllMessagesRequest")) {
            MessagesData.loadMessages(CcBans.getPlugin());
            PubSub.broadcast("reloadAllMessagesResponse", CcBans.getPlugin().getName());
        }
    }
    
    public static void kickPlayer(final Player player, final String message) {
        if (Bukkit.isPrimaryThread()) {
            player.kickPlayer(message);
        }
        else {
            Bukkit.getScheduler().runTask(CcBans.getPlugin(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    player.kickPlayer(message);
                }
            });
        }
    }
}
