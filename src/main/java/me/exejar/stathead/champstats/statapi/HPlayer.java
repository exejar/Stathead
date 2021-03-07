package me.exejar.stathead.champstats.statapi;

import me.exejar.stathead.champstats.statapi.stats.Stat;

import java.util.HashMap;
import java.util.List;

public class HPlayer {

    private HashMap<String, List<Stat>> gameStatMap;
    private String playerUUID, playerName;

    /**
     * @param playerUUID Player's UUID
     * @param playerName Player's Name
     * @param gameBase All HGameBase's you would like the HPlayer to contain
     * (Generally you would like all HGameBases which are complete to be added)
     */
    public HPlayer(String playerUUID, String playerName, HGameBase... gameBase) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;

        gameStatMap = new HashMap<>();
        for (HGameBase game : gameBase) {
            this.gameStatMap.put(game.getGame().getGameName(), game.getFormattedStatList());
        }
    }

    public HPlayer(String playerUUID, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
    }

    public void addGames(HGameBase... gameBases) {
        for (HGameBase game : gameBases) {
            this.gameStatMap.put(game.getGame().getGameName(), game.getFormattedStatList());
        }
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Stat> getGameStats(String gameName) {
        return gameStatMap.get(gameName);
    }

    public void setGameList(String gameName, List<Stat> stats) {
        this.gameStatMap.put(gameName, stats);
    }

}
