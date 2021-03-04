package me.dooger.betterlvlhead.champstats.statapi.general;

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

    private List<Stat> statList;
    public Stat level, quests, guildname;

    public General(String playerName, String playerUUID) {
        super(playerName, playerUUID);
        statList = new ArrayList<>();
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
        try {
            this.level = new StatString("Level", setSk1erData(getPlayerUUID(), "LEVEL").getAsString());
            this.quests = new StatString("Quests", setSk1erData(getPlayerUUID(), "QUESTS").getAsString());
            this.guildname = new StatString("Guild", setSk1erData(getPlayerUUID(), "GUILD_NAME").getAsString());
        } catch (PlayerNullException ex) {
            this.isNicked = true;
        }

        try {
            if (!this.isNicked) {
                this.hasPlayed = true;
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
        return this.statList;
    }

    @Override
    public List<Stat> getFormattedStatList() {
        return this.statList;
    }

    @Override
    public void setFormattedStatList() {
    }


}
