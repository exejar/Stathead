package me.exejar.stathead.champstats.statapi.stats;

import com.google.gson.JsonObject;

public class StatDouble extends Stat {
    private double value;

    /**
     * @param statName Name of the Stat
     * @param jsonName Json Name of the Stat in Hypixel's API
     * @param gameObject JsonObject of the desired Game Stat
     */
    public StatDouble(String statName, String jsonName, JsonObject gameObject) {
        super(statName, jsonName, gameObject);
    }

    public StatDouble(String statName, double stat) {
        super(statName);
        this.value = stat;
    }

    public StatDouble(String statName) {
        super(statName);
    }

    @Override
    public void setStat() {
        try {
            this.value = gameObject.get(jsonName).getAsDouble();
        } catch (NullPointerException ex) {
            this.value = 0;
        }
    }

    public void setValue(double value) { this.value = value; }

    public double getValue() { return this.value; }
}
