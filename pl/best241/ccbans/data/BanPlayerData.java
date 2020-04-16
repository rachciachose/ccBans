// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.data;

import java.util.UUID;

public class BanPlayerData
{
    private final UUID uuid;
    private final String banAdmin;
    private final BanType banType;
    private final long time;
    private final String reason;
    
    public BanPlayerData(final UUID uuid, final String banAdmin, final BanType banType, final long time, final String reason) {
        this.uuid = uuid;
        this.banAdmin = banAdmin;
        this.banType = banType;
        this.time = time;
        this.reason = reason;
    }
    
    public UUID getBannedUUID() {
        return this.uuid;
    }
    
    public String getBanAdminNick() {
        return this.banAdmin;
    }
    
    public BanType getBanType() {
        return this.banType;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public String getReason() {
        return this.reason;
    }
}
