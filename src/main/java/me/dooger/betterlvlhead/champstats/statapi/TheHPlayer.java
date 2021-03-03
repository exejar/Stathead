package me.dooger.betterlvlhead.champstats.statapi;

import me.dooger.betterlvlhead.champstats.statapi.stats.Stat;
import me.dooger.betterlvlhead.champstats.utils.ServerState;

import java.util.HashMap;
import java.util.List;

public class TheHPlayer extends HPlayer{

    private HashMap<String, List<Stat>> gameStatMap;
    private String playerUUID, playerName;
    private ServerState serverState;
    private HypixelGames game;

    /**
     * @param playerUUID Player's UUID
     * @param playerName Player's Name
     * @param gameBase All HGameBase's you would like the HPlayer to contain
     * (Generally you would like all HGameBases which are complete to be added)
     */
    public TheHPlayer(String playerUUID, String playerName, HGameBase... gameBase) {
        super(playerUUID, playerName, gameBase);
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.game = HypixelGames.BEDWARS;

        gameStatMap = new HashMap<>();
        for (HGameBase game : gameBase) {
            this.gameStatMap.put(game.getGame().getGameName(), game.getFormattedStatList());
        }
    }

    public void setGameStatMap(HGameBase... gameBase) {
        for (HGameBase game : gameBase) {
            this.gameStatMap.put(game.getGame().getGameName(), game.getFormattedStatList());
        }
    }

    public void setPlayingGame(HypixelGames game) {
        this.game = game;
    }

    public HypixelGames getPlayingGame() { return this.game; }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Stat> getGameStats(String gameName) {
        return gameStatMap.get(gameName);
    }

    public void setServerState(ServerState state) {
        this.serverState = state;
    }

    public ServerState getServerState() {
        return this.serverState;
    }

}
