// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.listeners;

import org.bukkit.event.EventHandler;
import pl.best241.ccbans.data.BanPlayerData;
import java.util.UUID;
import pl.best241.ccbans.CcBans;
import java.util.Date;
import java.text.DateFormat;
import org.bukkit.ChatColor;
import pl.best241.ccbans.messages.MessagesData;
import pl.best241.ccbans.data.BanType;
import pl.best241.ccbans.data.DataStore;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    @EventHandler
    public static void onAsyncJoin(final AsyncPlayerPreLoginEvent event) {
        final UUID uniqueId = event.getUniqueId();
        if (DataStore.containsBanPlayerData(uniqueId)) {
            final BanPlayerData data = DataStore.getBanPlayerData(uniqueId);
            if (data.getBanType() == BanType.PERM) {
                event.setKickMessage(MessagesData.permBanCantJoinServer.replace("%nick", data.getBanAdminNick()).replace("%reason", data.getReason()));
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            }
            else if (data.getBanType() == BanType.TEMP) {
                if (data.getTime() > System.currentTimeMillis()) {
                    event.setKickMessage(MessagesData.tempBanCantJoinServer.replace("%nick", data.getBanAdminNick()).replace("%reason", data.getReason()).replace("%time", ChatColor.GOLD + DateFormat.getInstance().format(new Date(data.getTime()))));
                    event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                }
                else {
                    CcBans.getBackend().removeBan(data.getBannedUUID());
                    CcBans.publishUnbanPlayer(data.getBannedUUID());
                }
            }
        }
    }
}
