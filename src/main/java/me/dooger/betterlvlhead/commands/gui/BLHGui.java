package me.dooger.betterlvlhead.commands.gui;

import me.dooger.betterlvlhead.champstats.config.ModConfig;
import me.dooger.betterlvlhead.champstats.statapi.HypixelGames;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BLHGui extends GuiScreen {

    private final int MODE = 0, STAT = 1;

    GuiButton modeSwapButton, statsSwapButton;
    String modeButtonTitle = "", statButtonTitle = "";
    HashMap<HypixelGames, List<String>> modes = new HashMap<>();

    public BLHGui() {
        List<String> genStats = new ArrayList<>();
        genStats.add("Level");
        genStats.add("Guild");
        genStats.add("Quests");

        List<String> bwStats = new ArrayList<>();
        bwStats.add("WS");
        bwStats.add("FKDR");
        bwStats.add("WLR");
        bwStats.add("BBLR");
        bwStats.add("Star");

        modes.put(HypixelGames.GENERAL, genStats);
        modes.put(HypixelGames.BEDWARS, bwStats);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        int centerX = (width / 2);
        int centerY = (height / 2);

        modeSwapButton.drawButton(mc, mouseX, mouseY);
        statsSwapButton.drawButton(mc, mouseX, mouseY);

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(15, height - fontRendererObj.FONT_HEIGHT, 0);
            GlStateManager.scale(0.75, 0.75, 0.75);
            drawString(fontRendererObj, "Better Level Head - Created by exejar | Based off Sk1er's Better Level Head", 0, 0, new Color(120, 0, 210).getRGB());
        }
        GlStateManager.popMatrix();

    }

    @Override
    public void initGui() {

        int centerX = (width / 2);
        int centerY = (height / 2);

        buttonList.clear();

        buttonList.add(modeSwapButton = new GuiButton(MODE, centerX - 70, centerY - 35, 120, 50, modeButtonTitle));
        buttonList.add(statsSwapButton = new GuiButton(STAT, centerX + 70, centerY + 35, 120, 50, statButtonTitle));
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case MODE:
                
                break;
            case STAT:

                break;
        }
    }

    @Override
    public void onGuiClosed() {
        getStatConfig().save();
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    private ModConfig getStatConfig() { return ModConfig.getInstance(); }

}
