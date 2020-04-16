// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.data;

import java.util.Collection;
import java.util.Map;
import pl.best241.ccbans.CcBans;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore
{
    private static final ConcurrentHashMap<UUID, BanPlayerData> players;
    private static final ArrayList<String> ipBans;
    
    public static boolean containsBanPlayerData(final UUID uuid) {
        return DataStore.players.containsKey(uuid);
    }
    
    public static BanPlayerData getBanPlayerData(final UUID uuid) {
        return DataStore.players.get(uuid);
    }
    
    public static void setBanPlayerData(final BanPlayerData data) {
        DataStore.players.put(data.getBannedUUID(), data);
    }
    
    public static void removeBanPlayerData(final UUID uuid) {
        DataStore.players.remove(uuid);
    }
    
    public static void addIpBan(final String address) {
        DataStore.ipBans.add(address);
    }
    
    public static void removeIpBan(final String address) {
        DataStore.ipBans.remove(address);
    }
    
    public static boolean isIpBanned(final String address) {
        return DataStore.ipBans.contains(address);
    }
    
    public static void loadAll() {
        DataStore.players.putAll(CcBans.getBackend().getAllBans());
        DataStore.ipBans.addAll(CcBans.getBackend().getIpBans());
    }
    
    static {
        players = new ConcurrentHashMap<UUID, BanPlayerData>();
        ipBans = new ArrayList<String>();
    }
}
