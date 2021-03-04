package me.dooger.betterlvlhead.champstats.statapi;

import com.google.gson.JsonObject;
import me.dooger.betterlvlhead.champstats.statapi.stats.Stat;
import me.dooger.betterlvlhead.champstats.utils.Handler;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class HGameBase extends HypixelAPI {

    private String playerName, playerUUID;
    private EntityPlayer player;
    public boolean isNicked;
    public boolean hasPlayed;

    public HGameBase(String playerName, String playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public HGameBase(EntityPlayer player) {
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueID().toString();
        this.player = player;
    }

    public HGameBase(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return String of all Formatted Stats combined
     */
    public abstract String getFormattedStats();

    /**
     * @return Game Enumeration of sub-classes Game
     */
    public abstract HypixelGames getGame();

    /**
     * @return List of every Stat from Game
     */
    public abstract List<Stat> getStatList();

    /**
     * @return List of every Formatted Stat from Game
     */
    public abstract List<Stat> getFormattedStatList();

    public abstract void setFormattedStatList();

    /**
     * Method to set the Game Data
     */
    public abstract boolean setData(HypixelGames game);

    public String getPlayerName() {
        return this.playerName;
    }

    public String getPlayerUUID() {
        return this.playerUUID;
    }

    public EntityPlayer getPlayerEntity() {
        return this.player;
    }

    public boolean getIsNicked() {
        return this.isNicked;
    }

    public boolean getHasPlayed() {
        return this.hasPlayed;
    }

    protected List<Stat> setStats(Stat... stats) {
        LinkedList<Stat> statList = new LinkedList<>();

        for (Stat stat : stats) {
            stat.setStat();
            statList.add(stat);
        }

        setFormattedStatList();
        return statList;
    }

    protected void setStatsAsync(List<Stat> statList,  HPlayer hPlayer, Stat... stats) {
        Handler.asExecutor(()-> {
            for (Stat stat : stats) {
                stat.setStat();
                statList.add(stat);
            }
            setFormattedStatList();
            hPlayer.setGameList(getGame().getGameName(), getFormattedStatList());
        });
    }

    private int findIndexInArray(Stat[] arr, Stat s) {
        int index = Arrays.binarySearch(arr, s);
        return (index < 0) ? -1 : index;
    }

}
