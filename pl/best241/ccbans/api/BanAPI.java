// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.api;

import pl.best241.ccchat.CcChat;
import pl.best241.ccbans.messages.MessagesData;
import pl.best241.ccbans.CcBans;
import pl.best241.ccbans.data.DataStore;
import pl.best241.ccbans.data.BanPlayerData;
import pl.best241.ccbans.data.BanType;
import org.bukkit.entity.Player;

public class BanAPI
{
    public static int banPlayer(final Player banPlayer, final Player adminPlayer, final String reason) {
        final String nick = banPlayer.getName();
        final BanPlayerData data = new BanPlayerData(banPlayer.getUniqueId(), adminPlayer.getName(), BanType.PERM, -1L, reason);
        DataStore.setBanPlayerData(data);
        CcBans.getBackend().addBan(banPlayer.getUniqueId(), data);
        CcBans.publishBanPlayer(data);
        adminPlayer.sendMessage(MessagesData.youBannedPlayerPerm.replace("%nick", nick).replace("%reason", data.getReason()));
        CcChat.broadcastChat(MessagesData.playerWasPermBannedByBroadcast.replace("%player", nick).replace("%admin", data.getBanAdminNick()).replace("%reason", data.getReason()));
        return 0;
    }
}
