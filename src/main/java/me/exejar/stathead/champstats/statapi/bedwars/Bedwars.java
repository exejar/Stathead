package me.exejar.stathead.champstats.statapi.bedwars;

import com.google.gson.JsonObject;
import me.exejar.stathead.champstats.statapi.HPlayer;
import me.exejar.stathead.champstats.statapi.HypixelGames;
import me.exejar.stathead.champstats.statapi.exception.ApiRequestException;
import me.exejar.stathead.champstats.statapi.exception.InvalidKeyException;
import me.exejar.stathead.champstats.statapi.exception.PlayerNullException;
import me.exejar.stathead.champstats.statapi.stats.Stat;
import me.exejar.stathead.champstats.statapi.stats.StatInt;
import me.exejar.stathead.champstats.statapi.stats.StatString;
import me.exejar.stathead.utils.ChatUtils;
import me.exejar.stathead.utils.ChatColor;
import me.exejar.stathead.utils.References;

import java.util.ArrayList;
import java.util.List;

public class Bedwars extends BedwarsUtils {

    private JsonObject bedwarsJson;
    private List<Stat> statList;
    private List<Stat> formattedStatList;
    public Stat gamesPlayed, finalKills, finalDeaths, wins, losses, kills, deaths, bedsBroken, bedsLost, winstreak, star;

    public Bedwars(String playerName, String playerUUID) {
        super(playerName, playerUUID);
        statList = new ArrayList<>();
        if (setData(HypixelGames.BEDWARS)) {
            formattedStatList = new ArrayList<>();
            statList = setStats(
                    star = new StatInt("Star", "bedwars_level", achievementObj),
                    gamesPlayed = new StatInt("Games Played", "games_played_bedwars", bedwarsJson),
                    finalKills = new StatInt("Final Kills", "final_kills_bedwars", bedwarsJson),
                    finalDeaths = new StatInt("Final Deaths", "final_deaths_bedwars", bedwarsJson),
                    wins = new StatInt("Wins", "wins_bedwars", bedwarsJson),
                    losses = new StatInt("Losses", "losses_bedwars", bedwarsJson),
                    kills = new StatInt("Kills", "kills_bedwars", bedwarsJson),
                    deaths = new StatInt("Deaths", "deaths_bedwars", bedwarsJson),
                    bedsBroken = new StatInt("Beds Broken", "beds_broken_bedwars", bedwarsJson),
                    bedsLost = new StatInt("Beds Lost", "beds_lost_bedwars", bedwarsJson),
                    winstreak = new StatInt("Winstreak", "winstreak_bedwars", bedwarsJson));
        } else {
            formattedStatList.add(new StatString("Star", ChatColor.RED + "NICKED"));
        }
    }

    public Bedwars(String playerName, String playerUUID, HPlayer hPlayer) {
        super(playerName, playerUUID);
        if (setData(HypixelGames.BEDWARS)) {
            statList = new ArrayList<>();
            formattedStatList = new ArrayList<>();
            setStatsAsync(statList, hPlayer,
                    star = new StatInt("Star", "bedwars_level", achievementObj),
                    gamesPlayed = new StatInt("Games Played", "games_played_bedwars", bedwarsJson),
                    finalKills = new StatInt("Final Kills", "final_kills_bedwars", bedwarsJson),
                    finalDeaths = new StatInt("Final Deaths", "final_deaths_bedwars", bedwarsJson),
                    wins = new StatInt("Wins", "wins_bedwars", bedwarsJson),
                    losses = new StatInt("Losses", "losses_bedwars", bedwarsJson),
                    kills = new StatInt("Kills", "kills_bedwars", bedwarsJson),
                    deaths = new StatInt("Deaths", "deaths_bedwars", bedwarsJson),
                    bedsBroken = new StatInt("Beds Broken", "beds_broken_bedwars", bedwarsJson),
                    bedsLost = new StatInt("Beds Lost", "beds_lost_bedwars", bedwarsJson),
                    winstreak = new StatInt("Winstreak", "winstreak_bedwars", bedwarsJson));
        }

    }

    @Override
    public boolean setData(HypixelGames game) {
        this.isNicked = false;
        this.hasPlayed = false;
        JsonObject obj = null;
        boolean isFunctional = false;

        try {
            obj = getGameData(getPlayerUUID(), game);
            isFunctional = true;
        } catch (ApiRequestException ignored) {
        } catch (PlayerNullException ex) {
            this.isNicked = true;
        } catch (InvalidKeyException ex) {
            ChatUtils.sendMessage(ChatColor.RED + "Invalid API Key");
        }

        try {
            if (!this.isNicked && isFunctional) {
                this.hasPlayed = true;
                this.bedwarsJson = obj;
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
        return String.format("%s%s", getFkdrColor(getFkdr(this)), getFkdr(this));
    }

    @Override
    public HypixelGames getGame() {
        return HypixelGames.BEDWARS;
    }

    @Override
    public List<Stat> getStatList() {
        return this.statList;
    }

    @Override
    public List<Stat> getFormattedStatList() {
        return this.formattedStatList;
    }

    @Override
    public void setFormattedStatList() {
        StatString ws = new StatString("WS");
        ws.setValue(getWSColor(((StatInt)winstreak).getValue()).toString() + ((StatInt)winstreak).getValue());
        formattedStatList.add(ws);

        StatString fkdr = new StatString("FKDR");
        fkdr.setValue(getFkdrColor(getFkdr(this)).toString() + getFkdr(this));
        formattedStatList.add(fkdr);

        StatString wlr = new StatString("WLR");
        wlr.setValue(getWlrColor(getWlr(this)).toString() + getWlr(this));
        formattedStatList.add(wlr);

        StatString bblr = new StatString("BBLR");
        bblr.setValue(getBblrColor(getBblr(this)).toString() + getBblr(this));
        formattedStatList.add(bblr);

        StatString star = new StatString("Star");
        star.setValue(getStarColor(((StatInt)this.star).getValue()).toString() + ((StatInt)this.star).getValue());
        formattedStatList.add(star);
    }
}
