package me.exejar.stathead.champstats.statapi.stats;

import com.google.gson.JsonObject;

public class StatLong extends Stat {
    private long value;

    /**
     * @param statName Name of the Stat
     * @param jsonName Json Name of the Stat in Hypixel's API
     * @param gameObject JsonObject of the desired Game Stat
     */
    public StatLong(String statName, String jsonName, JsonObject gameObject) { super(statName, jsonName, gameObject); }

    public StatLong(String statName, int stat) {
        super(statName);
        this.value = stat;
    }

    public StatLong(String statName) { super(statName); }

    @Override
    public void setStat() {
        try {
            this.value = Long.parseLong(gameObject.get(jsonName).toString());
        } catch (NullPointerException ex) {
            this.value = 0;
        }
    }

    public void setValue(long value) { this.value = value; }

    public long getValue() { return this.value; }
}
