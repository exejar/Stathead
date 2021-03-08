package me.exejar.stathead.champstats.statapi;

public enum HypixelGames {
    GENERAL("player", "General", new String[]{"Level", "Achievement Points", "Quests"}),
    BEDWARS("Bedwars", "Bedwars", new String[]{"WS", "FKDR", "WLR", "BBLR", "Star"});

    private String apiName, gameName;
    private String[] stats;

    /**
     * @param apiName Name of the Game in Hypixel's API
     * @param gameName Name of the Game which best suits the Player
     */
    HypixelGames(String apiName, String gameName, String[] stats) {
        this.apiName = apiName;
        this.gameName = gameName;
        this.stats = stats;
    }

    public String getApiName() { return this.apiName; }

    public String getGameName() { return this.gameName; }

    public String[] getStatNames() { return this.stats; }

}
