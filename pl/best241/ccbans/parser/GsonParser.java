// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.parser;

import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

public class GsonParser
{
    private static final Gson gson;
    
    public static String parseToJson(final Object object) {
        return GsonParser.gson.toJson(object);
    }
    
    public static Object parseToObject(final String json) {
        return GsonParser.gson.fromJson(json, (Class)Object.class);
    }
    
    static {
        gson = new GsonBuilder().create();
    }
}
