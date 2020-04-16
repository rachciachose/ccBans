// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.messages;

import org.bukkit.plugin.Plugin;

public class MessagesData
{
    public static String playerNotInDatabase;
    public static String youBannedPlayerPerm;
    public static String banTimeUsage;
    public static String noPermissions;
    public static String playerIsNotBanned;
    public static String unbannedPlayer;
    public static String unbanUsage;
    public static String wrongTimeFormat;
    public static String playerIsBannedYet;
    public static String youTempBannedPlayer;
    public static String tempbanUsage;
    public static String addressIsAlredyBanned;
    public static String youBannedAddress;
    public static String ipbanUsage;
    public static String addressIsNotBanned;
    public static String youUnbannedAddress;
    public static String ipunbanUsage;
    public static String baninfo;
    public static String youKickedPlayer;
    public static String kickUsage;
    public static String permBanCantJoinServer;
    public static String tempBanCantJoinServer;
    public static String youWereKickedFromServer;
    public static String playerWasPermBannedByBroadcast;
    public static String playerWasTempBannedByBroadcast;
    public static String playerWasKickedByBroadcast;
    private static MessagesConfig config;
    
    public static void loadMessages(final Plugin plugin) {
        (MessagesData.config = new MessagesConfig(plugin, "messages.yml")).saveDefaultConfig();
        MessagesData.config.reloadCustomConfig();
        MessagesData.playerNotInDatabase = MessagesData.config.getString("playerNotInDatabase");
        MessagesData.youBannedPlayerPerm = MessagesData.config.getString("youBannedPlayerPerm");
        MessagesData.banTimeUsage = MessagesData.config.getString("banTimeUsage");
        MessagesData.noPermissions = MessagesData.config.getString("noPermissions");
        MessagesData.playerIsNotBanned = MessagesData.config.getString("playerIsNotBanned");
        MessagesData.unbannedPlayer = MessagesData.config.getString("unbannedPlayer");
        MessagesData.unbanUsage = MessagesData.config.getString("unbanUsage");
        MessagesData.wrongTimeFormat = MessagesData.config.getString("wrongTimeFormat");
        MessagesData.playerIsBannedYet = MessagesData.config.getString("playerIsBannedYet");
        MessagesData.youTempBannedPlayer = MessagesData.config.getString("youTempBannedPlayer");
        MessagesData.tempbanUsage = MessagesData.config.getString("tempbanUsage");
        MessagesData.addressIsAlredyBanned = MessagesData.config.getString("addressIsAlredyBanned");
        MessagesData.youBannedAddress = MessagesData.config.getString("youBannedAddress");
        MessagesData.ipbanUsage = MessagesData.config.getString("ipbanUsage");
        MessagesData.addressIsNotBanned = MessagesData.config.getString("addressIsNotBanned");
        MessagesData.youUnbannedAddress = MessagesData.config.getString("youUnbannedAddress");
        MessagesData.ipunbanUsage = MessagesData.config.getString("ipunbanUsage");
        MessagesData.baninfo = MessagesData.config.getString("baninfo");
        MessagesData.youKickedPlayer = MessagesData.config.getString("youKickedPlayer");
        MessagesData.kickUsage = MessagesData.config.getString("kickUsage");
        MessagesData.permBanCantJoinServer = MessagesData.config.getString("permBanCantJoinServer");
        MessagesData.tempBanCantJoinServer = MessagesData.config.getString("tempBanCantJoinServer");
        MessagesData.youWereKickedFromServer = MessagesData.config.getString("youWereKickedFromServer");
        MessagesData.playerWasPermBannedByBroadcast = MessagesData.config.getString("playerWasPermBannedByBroadcast");
        MessagesData.playerWasTempBannedByBroadcast = MessagesData.config.getString("playerWasTempBannedByBroadcast");
        MessagesData.playerWasKickedByBroadcast = MessagesData.config.getString("playerWasKickedByBroadcast");
    }
}
