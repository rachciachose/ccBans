// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.commands;

import java.util.UUID;
import java.util.Date;
import java.text.DateFormat;
import pl.best241.ccbans.parser.TimeParser;
import pl.best241.ccchat.CcChat;
import pl.best241.ccbans.CcBans;
import pl.best241.ccbans.data.DataStore;
import pl.best241.ccbans.data.BanPlayerData;
import pl.best241.ccbans.data.BanType;
import pl.best241.ccbans.messages.MessagesData;
import pl.best241.ccsectors.api.CcSectorsAPI;
import org.bukkit.command.CommandSender;

public class CommandExecutor
{
    public static void handleCommand(final CommandSender sender, final String cmd, final String[] args) {
        if (cmd.equalsIgnoreCase("ban")) {
            if (sender.hasPermission("ccBans.ban")) {
                if (args.length >= 2) {
                    final String nick = args[0];
                    final StringBuilder reasonBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; ++i) {
                        if (i != 1) {
                            reasonBuilder.append(" ");
                        }
                        reasonBuilder.append(args[i]);
                    }
                    final UUID uuid = CcSectorsAPI.getUUID(nick);
                    if (uuid == null) {
                        sender.sendMessage(MessagesData.playerNotInDatabase);
                        return;
                    }
                    final BanPlayerData data = new BanPlayerData(uuid, sender.getName(), BanType.PERM, -1L, reasonBuilder.toString());
                    DataStore.setBanPlayerData(data);
                    CcBans.getBackend().addBan(uuid, data);
                    CcBans.publishBanPlayer(data);
                    sender.sendMessage(MessagesData.youBannedPlayerPerm.replace("%nick", nick).replace("%reason", data.getReason()));
                    CcChat.broadcastChat(MessagesData.playerWasPermBannedByBroadcast.replace("%player", nick).replace("%admin", data.getBanAdminNick()).replace("%reason", data.getReason()));
                }
                else {
                    sender.sendMessage(MessagesData.banTimeUsage);
                }
            }
            else {
                sender.sendMessage(MessagesData.noPermissions);
            }
        }
        else if (cmd.equalsIgnoreCase("unban")) {
            if (sender.hasPermission("ccBans.unban")) {
                if (args.length == 1) {
                    final String nick = args[0];
                    final UUID uuid2 = CcSectorsAPI.getUUID(nick);
                    if (uuid2 == null) {
                        sender.sendMessage(MessagesData.playerNotInDatabase);
                        return;
                    }
                    if (!DataStore.containsBanPlayerData(uuid2)) {
                        sender.sendMessage(MessagesData.playerIsNotBanned);
                        return;
                    }
                    DataStore.removeBanPlayerData(uuid2);
                    CcBans.publishUnbanPlayer(uuid2);
                    CcBans.getBackend().removeBan(uuid2);
                    sender.sendMessage(MessagesData.unbannedPlayer.replace("%nick", nick));
                }
                else {
                    sender.sendMessage(MessagesData.unbanUsage);
                }
            }
            else {
                sender.sendMessage(MessagesData.noPermissions);
            }
        }
        else if (cmd.equalsIgnoreCase("tempban")) {
            if (sender.hasPermission("ccBans.tempban")) {
                if (args.length >= 3) {
                    final String nick = args[0];
                    final String rawTime = args[1];
                    final Long time = TimeParser.parseTime(rawTime);
                    if (time == null) {
                        sender.sendMessage(MessagesData.wrongTimeFormat);
                        return;
                    }
                    final StringBuilder reasonBuilder2 = new StringBuilder();
                    for (int j = 2; j < args.length; ++j) {
                        if (j != 2) {
                            reasonBuilder2.append(" ");
                        }
                        reasonBuilder2.append(args[j]);
                    }
                    final UUID uuid3 = CcSectorsAPI.getUUID(nick);
                    if (uuid3 == null) {
                        sender.sendMessage(MessagesData.playerNotInDatabase);
                        return;
                    }
                    if (DataStore.containsBanPlayerData(uuid3)) {
                        sender.sendMessage(MessagesData.playerIsBannedYet);
                        return;
                    }
                    final BanPlayerData data2 = new BanPlayerData(uuid3, sender.getName(), BanType.TEMP, System.currentTimeMillis() + time, reasonBuilder2.toString());
                    DataStore.setBanPlayerData(data2);
                    CcBans.publishBanPlayer(data2);
                    CcBans.getBackend().addBan(uuid3, data2);
                    final String date = DateFormat.getInstance().format(new Date(System.currentTimeMillis() + time));
                    final String reason = reasonBuilder2.toString();
                    sender.sendMessage(MessagesData.youTempBannedPlayer.replace("%nick", nick).replace("%uuid", uuid3.toString()).replace("%reason", reason).replace("%time", date));
                    CcChat.broadcastChat(MessagesData.playerWasTempBannedByBroadcast.replace("%player", nick).replace("%admin", data2.getBanAdminNick()).replace("%reason", data2.getReason()).replace("%time", date));
                }
                else {
                    sender.sendMessage(MessagesData.tempbanUsage);
                }
            }
            else {
                sender.sendMessage(MessagesData.noPermissions);
            }
        }
        else if (cmd.equalsIgnoreCase("banip")) {
            if (sender.hasPermission("ccBans.ipban")) {
                if (args.length == 1) {
                    final String data3 = args[0];
                    if (DataStore.isIpBanned(data3)) {
                        sender.sendMessage(MessagesData.addressIsAlredyBanned);
                        return;
                    }
                    CcBans.getBackend().addIpBan(data3);
                    CcBans.publishBanIp(data3);
                    sender.sendMessage(MessagesData.youBannedAddress.replace("%ip", data3));
                }
                else {
                    sender.sendMessage(MessagesData.ipbanUsage);
                }
            }
            else {
                sender.sendMessage(MessagesData.noPermissions);
            }
        }
        else if (cmd.equalsIgnoreCase("unbanip")) {
            if (sender.hasPermission("ccBans.ipunban")) {
                if (args.length == 1) {
                    final String data3 = args[0];
                    if (!DataStore.isIpBanned(data3)) {
                        sender.sendMessage(MessagesData.addressIsNotBanned);
                        return;
                    }
                    CcBans.getBackend().removeIpBan(data3);
                    CcBans.publishUnbanIp(data3);
                    sender.sendMessage(MessagesData.youUnbannedAddress);
                }
                else {
                    sender.sendMessage(MessagesData.ipunbanUsage);
                }
            }
            else {
                sender.sendMessage(MessagesData.noPermissions);
            }
        }
        else if (cmd.equalsIgnoreCase("baninfo")) {
            if (sender.hasPermission("ccBans.baninfo") && args.length == 1) {
                final String nick = args[0];
                final UUID uuid2 = CcSectorsAPI.getUUID(nick);
                if (uuid2 == null) {
                    sender.sendMessage(MessagesData.playerNotInDatabase);
                    return;
                }
                if (!DataStore.containsBanPlayerData(uuid2)) {
                    sender.sendMessage(MessagesData.playerIsNotBanned);
                    return;
                }
                final BanPlayerData banData = DataStore.getBanPlayerData(uuid2);
                String time2 = "Forever";
                if (banData.getBanType() == BanType.TEMP) {
                    time2 = DateFormat.getInstance().format(new Date(banData.getTime()));
                }
                sender.sendMessage(MessagesData.baninfo.replace("%nick", nick).replace("%uuid", banData.getBannedUUID().toString()).replace("%admin", banData.getBanAdminNick()).replace("%reason", banData.getReason()).replace("%type", banData.getBanType().toString()).replace("%time", time2));
            }
        }
        else if (cmd.equalsIgnoreCase("kick") && sender.hasPermission("ccBans.kick")) {
            if (args.length >= 2) {
                final String nick = args[0];
                final StringBuilder reasonBuilder = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    if (i != 1) {
                        reasonBuilder.append(" ");
                    }
                    reasonBuilder.append(args[i]);
                }
                final UUID uuid = CcSectorsAPI.getUUID(nick);
                if (uuid == null) {
                    sender.sendMessage(MessagesData.playerNotInDatabase);
                    return;
                }
                CcBans.publishKick(uuid);
                sender.sendMessage(MessagesData.youKickedPlayer.replace("%nick", nick).replace("%reason", reasonBuilder.toString()));
                CcChat.broadcastChat(MessagesData.playerWasKickedByBroadcast.replace("%player", nick).replace("%admin", sender.getName()).replace("%reason", reasonBuilder.toString()));
            }
            else {
                sender.sendMessage(MessagesData.kickUsage);
            }
        }
    }
}
