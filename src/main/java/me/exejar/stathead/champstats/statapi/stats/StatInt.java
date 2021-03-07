package me.exejar.stathead.champstats.statapi.stats;

import com.google.gson.JsonObject;

public class StatInt extends Stat {
    private int value;

    /**
     * @param statName Name of the Stat
     * @param jsonName Json Name of the Stat in Hypixel's API
     * @param gameObject JsonObject of the desired Game Stat
     */
    public StatInt(String statName, String jsonName, JsonObject gameObject) {
        super(statName, jsonName, gameObject);
    }

    public StatInt(String statName, int stat) {
        super(statName);
        this.value = stat;
    }

    public StatInt(String statName) {
        super(statName);
    }

    @Override
    public void setStat() {
        try {
            this.value = Integer.parseInt(gameObject.get(jsonName).toString());
        } catch (NullPointerException ex) {
            this.value = 0;
        }
    }

    public void setValue(int value) { this.value = value; }

    public int getValue() { return this.value; }
}
