package me.exejar.stathead.exejarclick.clickgui.components;

import me.exejar.stathead.champstats.statapi.HypixelGames;
import me.exejar.stathead.exejarclick.clickgui.ClickGui;
import me.exejar.stathead.exejarclick.clickgui.util.CustomFontRenderer;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame {

    public List<Component> components;
    public String name;
    private int x, y, barHeight, width, height;
    public int dragX, dragY;
    private boolean dragging;
    private ListDisplay gameList, statList;

    public Frame(String name, int x, int y) {
        this.components = new ArrayList<>();
        this.name = name;
        this.x = x;
        this.y = y;
        this.dragging = false;
        this.barHeight = ClickGui.barHeight;
        this.width = ClickGui.frameWidth;
        this.height = ClickGui.frameHeight;

        int spacer = ClickGui.barHeight;

        List<String> gameEntryList = new ArrayList<>();
        for (HypixelGames game : HypixelGames.values()) {
            gameEntryList.add(game.getGameName());
        }

        List<String> statEntryList = new ArrayList<>(Arrays.asList(HypixelGames.GENERAL.getStatNames()));

        gameList = new ListDisplay(this, "Games", this.x, this.y, 100, 0, gameEntryList, true);
        statList = new ListDisplay(this, "Stats", this.x, this.y, 100, this.width - 100, statEntryList, false);

        this.components.add(statList);
        this.components.add(gameList);
    }

    public void renderFrame(CustomFontRenderer fontRenderer, int color) {
        RenderingUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, color);
        RenderingUtils.drawRect(this.x, this.y + this.barHeight, this.x + this.width, this.y + this.barHeight + this.height, ClickGui.rectColor.getRGB());
        fontRenderer.drawString(this.name, (this.x + (float)(this.width / 2 - fontRenderer.getWidth(this.name) / 2)), this.y + (float)(this.barHeight / 2 - fontRenderer.getHeight(this.name) / 2), 0xFFFFFFFF);

        for (Component component : components) {
            component.renderComponent(color);
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component component : components) {
            component.setOff(off);
        }
    }

    public void refreshList(List<String> update) {
        this.statList.updateList(update);
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public int getWidth() { return this.width; }

    public int getBarHeight() { return this.barHeight; }

    public int getHeight() { return this.height; }

    public List<Component> getComponents() { return this.components; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDragging(boolean drag) { this.dragging = drag; }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.dragging) {
            setX(mouseX - dragX);
            setY(mouseY - dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }

}
