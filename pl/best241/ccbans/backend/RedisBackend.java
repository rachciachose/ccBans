// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import pl.best241.ccbans.data.BanType;
import java.util.LinkedHashMap;
import java.util.HashMap;
import redis.clients.jedis.Jedis;
import pl.best241.ccbans.parser.GsonParser;
import pl.best241.rdbplugin.JedisFactory;
import pl.best241.ccbans.data.BanPlayerData;
import java.util.UUID;

public class RedisBackend implements Backend
{
    @Override
    public void addBan(final UUID uuid, final BanPlayerData banData) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.hset("ccBans.bannedListData", uuid.toString(), GsonParser.parseToJson(banData));
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    @Override
    public void removeBan(final UUID uuid) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.hdel("ccBans.bannedListData", new String[] { uuid.toString() });
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    @Override
    public BanPlayerData getPlayerBan(final UUID uuid) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final String rawData = jedis.hget("ccBans.bannedListData", uuid.toString());
        JedisFactory.getInstance().returnJedis(jedis);
        return (BanPlayerData)GsonParser.parseToObject(rawData);
    }
    
    @Override
    public HashMap<UUID, BanPlayerData> getAllBans() {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final Map<String, String> rawData = (Map<String, String>)jedis.hgetAll("ccBans.bannedListData");
        JedisFactory.getInstance().returnJedis(jedis);
        final HashMap<UUID, BanPlayerData> data = new HashMap<UUID, BanPlayerData>();
        for (final String rawUUID : rawData.keySet()) {
            final LinkedHashMap<String, Object> rawBanData = (LinkedHashMap<String, Object>)GsonParser.parseToObject(rawData.get(rawUUID));
            final UUID uuid = UUID.fromString(rawBanData.get("uuid"));
            final String banAdmin = rawBanData.get("banAdmin");
            final BanType banType = BanType.valueOf(rawBanData.get("banType"));
            final Long time = (long)(Object)rawBanData.get("time");
            final String reason = rawBanData.get("reason");
            final BanPlayerData banData = new BanPlayerData(uuid, banAdmin, banType, time, reason);
            data.put(uuid, banData);
        }
        return data;
    }
    
    @Override
    public void addIpBan(final String ipAddress) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.sadd("ccBans.ipBans", new String[] { ipAddress.toLowerCase() });
        JedisFactory.getInstance().returnJedis(jedis);
    }
    
    @Override
    public Set<String> getIpBans() {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        final Set<String> smembers = (Set<String>)jedis.smembers("ccBans.ipBans");
        JedisFactory.getInstance().returnJedis(jedis);
        return smembers;
    }
    
    @Override
    public boolean isIpBanned(final String ban) {
        final Set<String> ipBans = this.getIpBans();
        for (final String ipBan : ipBans) {
            final Pattern pattern = Pattern.compile(ipBan);
            final Matcher matcher = pattern.matcher(ban);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void removeIpBan(final String ban) {
        final Jedis jedis = JedisFactory.getInstance().getJedis();
        jedis.srem("ccBans.ipBans", new String[] { ban });
        JedisFactory.getInstance().returnJedis(jedis);
    }
}
