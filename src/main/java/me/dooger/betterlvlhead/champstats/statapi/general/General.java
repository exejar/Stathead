package me.dooger.betterlvlhead.champstats.statapi.general;

import com.google.gson.JsonObject;
import me.dooger.betterlvlhead.champstats.statapi.HPlayer;
import me.dooger.betterlvlhead.champstats.statapi.HypixelGames;
import me.dooger.betterlvlhead.champstats.statapi.exception.PlayerNullException;
import me.dooger.betterlvlhead.champstats.statapi.stats.Stat;
import me.dooger.betterlvlhead.champstats.statapi.stats.StatString;
import me.dooger.betterlvlhead.utils.ChatColor;
import me.dooger.betterlvlhead.utils.References;

import java.util.ArrayList;
import java.util.List;

public class General extends GeneralUtils {

    private JsonObject generalJson;
    private List<Stat> statList;
    private List<Stat> formattedStatList;
    public Stat level, quests, guildname;

    public General(String playerName, String playerUUID) {
        super(playerName, playerUUID);
        statList = new ArrayList<>();
        formattedStatList = new ArrayList<>();
        if (setData(HypixelGames.GENERAL)) {
            statList.add(level);
            statList.add(quests);
            statList.add(guildname);
        } else {
            statList.add(new StatString("Level", ChatColor.RED + "NICKED"));
        }
    }

    public General(String playerName, String playerUUID, HPlayer hPlayer) {
        super(playerName, playerUUID);
    }

    @Override
    public boolean setData(HypixelGames game) {
        this.isNicked = false;
        this.hasPlayed = false;
        JsonObject obj = null;
        try {
            obj = setSk1erData(getPlayerUUID(), "LEVEL");
        } catch (PlayerNullException ex) {
            this.isNicked = true;
        }

        try {
            if (!this.isNicked) {
                this.hasPlayed = true;
                this.generalJson = obj;
                return true;
            }
            return false;
        } catch (NullPointerException ex) {
            if (!this.isNicked) {
                System.err.println(String.format("%s Maybe %s has never played %s before", References.MODNAME, getPlayerName(), game.getGameName()));
            }

            System.err.println(References.MODNAME + " " + ex.getStackTrace().toString());
            return false;
        }
    }

    @Override
    public String getFormattedStats() {
        return null;
    }

    @Override
    public HypixelGames getGame() {
        return HypixelGames.GENERAL;
    }

    @Override
    public List<Stat> getStatList() {
        return null;
    }

    @Override
    public List<Stat> getFormattedStatList() {
        return null;
    }

    @Override
    public void setFormattedStatList() {

    }
}
