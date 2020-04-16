// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.backend;

import java.util.Set;
import java.util.HashMap;
import pl.best241.ccbans.data.BanPlayerData;
import java.util.UUID;

public interface Backend
{
    void addBan(final UUID p0, final BanPlayerData p1);
    
    void removeBan(final UUID p0);
    
    BanPlayerData getPlayerBan(final UUID p0);
    
    HashMap<UUID, BanPlayerData> getAllBans();
    
    void addIpBan(final String p0);
    
    Set<String> getIpBans();
    
    boolean isIpBanned(final String p0);
    
    void removeIpBan(final String p0);
}
