package me.dooger.betterlvlhead.events.render;

import me.dooger.betterlvlhead.Main;
import me.dooger.betterlvlhead.champstats.statapi.HypixelGames;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;

import java.util.ArrayList;
import java.util.UUID;

public class AboveHeadDisplay extends HPlayerDisplay {

    public boolean loadOrRender(EntityPlayer player) {
        if (player == null) {
            return false;
        }

        if (player.isInvisible())
            return false;

        for (PotionEffect effect : player.getActivePotionEffects()) {
            /* Checks if player has invis potion active */
            if (effect.getPotionID() == 14) {
                return false;
            }
        }

        if (!renderFromTeam(player)) {
            return false;
        }

        /* If Player has an entity on top of their head (riding them) */
        if (player.riddenByEntity != null) {
            return false;
        }

        /* Taken from sk1er */
        int renderDistance = 64;

        /* Some checks to make sure that their stat tag will render properly */
        return !(player.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) > 64)
                && (!player.hasCustomName() || !player.getCustomNameTag().isEmpty())
                && !player.getDisplayNameString().isEmpty()
                && existedMoreThan5Seconds.contains(player.getUniqueID())
                && !player.isSneaking();
    }

    /* Taken from sk1er Levelhead in attempt to keep the rendering system as similar to the original mod */
    protected boolean renderFromTeam(EntityPlayer player) {
        if (player == Minecraft.getMinecraft().thePlayer) return true;
        Team team = player.getTeam();
        Team team1 = Minecraft.getMinecraft().thePlayer.getTeam();

        if (team != null) {
            Team.EnumVisible enumVisible = team.getNameTagVisibility();
            switch (enumVisible) {
                case NEVER:
                    return false;
                case HIDE_FOR_OTHER_TEAMS:
                    return team1 == null || team.isSameTeam(team1);
                case HIDE_FOR_OWN_TEAM:
                    return team1 == null || !team.isSameTeam(team1);
                case ALWAYS:
                default:
                    return true;
            }
        }
        return true;
    }

    @Override
    public void onTick() {
        for (EntityPlayer entityPlayer : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (!existedMoreThan5Seconds.contains(entityPlayer.getUniqueID())) {
                if (!timeCheck.containsKey(entityPlayer.getUniqueID()))
                    timeCheck.put(entityPlayer.getUniqueID(), 0);
                int old = timeCheck.get(entityPlayer.getUniqueID());
                if (old > 100) {
                    if (!existedMoreThan5Seconds.contains(entityPlayer.getUniqueID()))
                        existedMoreThan5Seconds.add(entityPlayer.getUniqueID());
                } else if (!entityPlayer.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
                    timeCheck.put(entityPlayer.getUniqueID(), old + 1);
            }

            if (loadOrRender(entityPlayer)) {
                final UUID uuid = entityPlayer.getUniqueID();
                if (!cache.containsKey(uuid) && !statAssembly.contains(uuid)) {
                    statAssembly.add(uuid);
                    Main.getInstance().fetchStats(entityPlayer, HypixelGames.GENERAL);
                }
            }
        }
    }

    @Override
    public void checkCacheSize() {
        int max = Math.max(150, 500);
        if (cache.size() > max) {
            ArrayList<UUID> safePlayers = new ArrayList<>();
            for (EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (existedMoreThan5Seconds.contains(player.getUniqueID())) {
                    safePlayers.add(player.getUniqueID());
                }
            }

            existedMoreThan5Seconds.clear();
            existedMoreThan5Seconds.addAll(safePlayers);

            for (UUID uuid : cache.keySet()) {
                if (!safePlayers.contains(uuid)) {
                    cache.remove(uuid);
                }
            }
        }
    }

    @Override
    public void onDelete() {
        cache.clear();
        existedMoreThan5Seconds.clear();
    }
}
