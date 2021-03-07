package me.exejar.stathead.champstats.statapi;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class HWorld {

    private HashMap<EntityPlayer, HPlayer> worldPlayers;

    public HWorld() {
        worldPlayers = new HashMap<>();
    }

    public void removePlayer(EntityPlayer player) {
        worldPlayers.remove(player);
    }

    public void addPlayer(EntityPlayer player, HPlayer hPlayer) {
        worldPlayers.put(player, hPlayer);
    }

    public void clearPlayers() {
        worldPlayers.clear();
    }

    public HashMap<EntityPlayer, HPlayer> getWorldPlayers() {
        return this.worldPlayers;
    }

}
