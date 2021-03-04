package me.dooger.betterlvlhead.events;

import me.dooger.betterlvlhead.champstats.statapi.HPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HPlayerDisplay {

    /**
     * This class's base was taken from sk1er's Levelhead Mod.
     * Wanted to keep his cache method as it works totally fine and does not need many changes.
     */

    protected final ConcurrentHashMap<UUID, HPlayer> cache = new ConcurrentHashMap<>();
    protected final List<UUID> existedMoreThan5Seconds = new ArrayList<>();

    public abstract void onTick();

    public abstract void checkCacheSize();

    public abstract void onDelete();

    public ConcurrentHashMap<UUID, HPlayer> getCache() {
        return this.cache;
    }

    public List<UUID> getExistedMoreThan5Seconds() {
        return this.existedMoreThan5Seconds;
    }

}
