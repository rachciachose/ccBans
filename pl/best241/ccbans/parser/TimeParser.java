// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccbans.parser;

import org.apache.commons.lang.StringUtils;

public class TimeParser
{
    public static Long parseTime(final String rawTimeData) {
        final String[] args = rawTimeData.split(":");
        if (args.length != 2) {
            return null;
        }
        final String rawTime = args[0];
        final String rawData = args[1];
        if (StringUtils.isNumeric(rawTime)) {
            final long time = Integer.parseInt(rawTime);
            final String s = rawData;
            long data = 0L;
            switch (s) {
                case "s": {
                    data = 1000L;
                    break;
                }
                case "m": {
                    data = 60000L;
                    break;
                }
                case "g": {
                    data = 3600000L;
                    break;
                }
                case "d": {
                    data = 86400000L;
                    break;
                }
                case "t": {
                    data = 604800000L;
                    break;
                }
                case "M": {
                    data = -1702967296L;
                    break;
                }
                case "r": {
                    data = 693628928L;
                    break;
                }
                default: {
                    return null;
                }
            }
            return time * data;
        }
        return null;
    }
}
