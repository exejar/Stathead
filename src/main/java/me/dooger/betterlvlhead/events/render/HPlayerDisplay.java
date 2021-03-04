package me.dooger.betterlvlhead.events.render;

import me.dooger.betterlvlhead.champstats.statapi.HPlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HPlayerDisplay {

    /**
     * This class's base was taken from sk1er's Levelhead Mod.
     * Wanted to keep his cache method as it works totally fine and does not need many changes.
     * Also wanted the original mod's rendering system to be as similar as possible to this mod's.
     */

    protected final ConcurrentHashMap<UUID, HPlayer> cache = new ConcurrentHashMap<>();
    protected final List<UUID> statAssembly = new ArrayList<>();
    protected final List<UUID> existedMoreThan5Seconds = new ArrayList<>();
    protected final Map<UUID, Integer> timeCheck = new HashMap<>();

    public abstract void onTick();

    public abstract void checkCacheSize();

    public abstract void onDelete();

    public ConcurrentHashMap<UUID, HPlayer> getCache() {
        return this.cache;
    }

    public List<UUID> getExistedMoreThan5Seconds() {
        return this.existedMoreThan5Seconds;
    }

    public Map<UUID, Integer> getTimeCheck() {
        return timeCheck;
    }

    /* Returns a list of players that are currently being stat checked */
    public List<UUID> getStatAssembly() {
        return this.statAssembly;
    }

    public void removeFromStatAssembly(UUID uuid) {
        this.statAssembly.remove(uuid);
    }

}
