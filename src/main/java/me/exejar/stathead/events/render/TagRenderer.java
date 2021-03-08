package me.exejar.stathead.events.render;

import me.exejar.stathead.Main;
import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.champstats.statapi.HPlayer;
import me.exejar.stathead.champstats.statapi.HypixelGames;
import me.exejar.stathead.champstats.statapi.stats.Stat;
import me.exejar.stathead.champstats.statapi.stats.StatString;
import me.exejar.stathead.utils.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.EnumUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class TagRenderer {

    private Main main;

    public TagRenderer(Main main) {
        this.main = main;
    }

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre event) {
        if (main == null || main.getTagDisplay() == null)
            return;

        EntityPlayer player = event.entityPlayer;

        AboveHeadDisplay display = main.getTagDisplay();

        HPlayer hPlayer = display.getCache().get(player.getUniqueID());

        /* No Idea why sk1er had a for loop of multiple displays...? */

        if (display.loadOrRender(player) && hPlayer != null) {
            /* Might add sk1er's idea for allowing players to view their own tags */
            //if (player.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID()))

            double offset = 0.9;
            Scoreboard scoreboard = player.getWorldScoreboard();
            ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);

            if (scoreObjective != null && player.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) < 10 * 10)
                offset *= 2;
            if (player.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID()))
                offset = 0;

            renderName(event, hPlayer, player, event.x, event.y + offset * .3D, event.z);
        }
    }

    public void renderName(RenderPlayerEvent event, HPlayer hPlayer, EntityPlayer entityIn, double x, double y, double z) {
        FontRenderer fontrenderer = event.renderer.getFontRendererFromRenderManager();
        float f = (float) (1.6F);
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();

        int xMultiplier = 1;
        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().gameSettings != null && Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            xMultiplier = -1;
        }

        GlStateManager.translate((float)x, (float)y + entityIn.height + 0.5, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);

        GlStateManager.rotate(-event.renderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(event.renderer.getRenderManager().playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);

        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int i = 0;

        String stat = "";

        ModConfig config = ModConfig.getInstance();

        if (EnumUtils.isValidEnum(HypixelGames.class, config.getStatMode().toUpperCase())) {
            List<Stat> statList = hPlayer.getGameStats(HypixelGames.valueOf(config.getStatMode().toUpperCase()).getGameName());

            for (Stat s : statList) {
                if (s.getStatName().equalsIgnoreCase(config.getStatName())) {
                    stat = ((StatString)s).getValue() + " " + s.getStatName();
                }
            }
        }

        int j = fontrenderer.getStringWidth(stat) / 2;
        GlStateManager.disableTexture2D();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        renderString(fontrenderer, stat);

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderString(FontRenderer renderer, String stat) {
        int x = -renderer.getStringWidth(stat) / 2;
        render(renderer, ChatColor.GREEN.toString(), stat, x);
    }

    private void render(FontRenderer renderer, String statColor, String stat, int x) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(true);

        int y = 0;
        GlStateManager.color(255, 255, 255, .5F);
        renderer.drawString(statColor + stat, x, y, Color.WHITE.darker().darker().darker().darker().darker().getRGB() * 255);

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.color(255, 255, 255, .5F);

        renderer.drawString(statColor + stat, x, y, Color.WHITE.darker().getRGB());
    }

}
