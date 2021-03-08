package me.exejar.stathead.events.render;

import me.exejar.stathead.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AboveHeadDisplay extends HPlayerDisplay {

    public boolean loadOrRender(EntityPlayer player) {
        if (player == null || player.getUniqueID().version() != 4) {
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
        return !(player.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) > renderDistance)
                && (!player.hasCustomName() || !player.getCustomNameTag().isEmpty())
                && !player.getDisplayNameString().isEmpty()
                && existedMoreThan5Seconds.contains(player)
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
            if (!existedMoreThan5Seconds.contains(entityPlayer)) {
                if (!timeCheck.containsKey(entityPlayer.getUniqueID()))
                    timeCheck.put(entityPlayer.getUniqueID(), 0);
                int old = timeCheck.get(entityPlayer.getUniqueID());
                if (old > 100) {
                    if (!existedMoreThan5Seconds.contains(entityPlayer))
                        existedMoreThan5Seconds.add(entityPlayer);
                } else if (!entityPlayer.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
                    timeCheck.put(entityPlayer.getUniqueID(), old + 1);
            }

            if (loadOrRender(entityPlayer)) {
                final UUID uuid = entityPlayer.getUniqueID();
                if (!Main.getInstance().theHWorld().getWorldPlayers().containsKey(entityPlayer) && !statAssembly.contains(uuid)) {
                    statAssembly.add(uuid);
                    Main.getInstance().fetchStats(entityPlayer);
                }
            }
        }
    }

    @Override
    public void checkCacheSize() {
        int max = Math.max(150, 500);
        if (cache.size() > max) {
            List<EntityPlayer> safePlayers = new ArrayList<>();
            for (EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (existedMoreThan5Seconds.contains(player)) {
                    safePlayers.add(player);
                }
            }

            existedMoreThan5Seconds.clear();
            existedMoreThan5Seconds.addAll(safePlayers);

            for (EntityPlayer player : Main.getInstance().theHWorld().getWorldPlayers().keySet()) {
                if (!safePlayers.contains(player)) {
                    Main.getInstance().theHWorld().removePlayer(player);
                }
            }
        }
    }

    @Override
    public void onDelete() {
        Main.getInstance().theHWorld().clearPlayers();
        cache.clear();
        existedMoreThan5Seconds.clear();
    }
}
