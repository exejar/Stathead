package me.exejar.stathead.champstats.statapi.general;

import me.exejar.stathead.champstats.statapi.HGameBase;

import java.text.DecimalFormat;

public abstract class GeneralUtils extends HGameBase {
    public GeneralUtils(String playerName, String playerUUID) {
        super(playerName, playerUUID);
    }

    private String plsSplit(double o) {
        return new DecimalFormat("##.##").format(o);
    }

    public String getNetworkLevelPrecise(long xp) {
        return plsSplit((Math.sqrt(xp + 15312.5) - (125 / Math.sqrt(2))) / (25 * Math.sqrt(2)));
    }

    public String getNetworkLevel(long xp) {
        return Integer.toString((int)((Math.sqrt(xp + 15312.5) - (125 / Math.sqrt(2))) / (25 * Math.sqrt(2))));
    }

}
