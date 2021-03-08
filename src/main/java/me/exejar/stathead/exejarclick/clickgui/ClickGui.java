package me.exejar.stathead.exejarclick.clickgui;

import me.exejar.stathead.Main;
import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.exejarclick.clickgui.components.Component;
import me.exejar.stathead.exejarclick.clickgui.components.Frame;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class ClickGui extends GuiScreen {

    public Frame mainFrame;

    public static int frameWidth = 500;
    public static int frameHeight = 350;
    public static int barHeight = 15;
    public static Color rectColor = new Color(55, 55, 55, 180);
    private Color headerColor = new Color(255, 80, 80, 180);
    public static Color subColor = new Color(40, 200, 255, 180);
    public int color;
    private boolean rainbow;

    public ClickGui() {
        mainFrame = new Frame("Stathead", 0, 0);
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if (rainbow) {
            color = Color.HSBtoRGB(System.currentTimeMillis() % 8000L / 8000.0F, 0.7f, 1.0f);
        } else {
            color = headerColor.getRGB();
        }
        mainFrame.renderFrame(Main.fontRenderer, color);
        mainFrame.updatePosition(mouseX, mouseY);
        for (Component component : mainFrame.getComponents()) {
            component.updateComponent(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mainFrame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
            mainFrame.setDragging(true);
            mainFrame.dragX = mouseX - mainFrame.getX();
            mainFrame.dragY = mouseY - mainFrame.getY();
        }
        for (Component component : mainFrame.getComponents()) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        mainFrame.setDragging(false);
        for (Component component : mainFrame.getComponents()) {
            component.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    @Override
    public void onGuiClosed() {
        ModConfig.getInstance().save();
    }

    public void setRainbow(boolean rainbow) { this.rainbow = rainbow; }

    public boolean isRainbow() { return this.rainbow; }

}
