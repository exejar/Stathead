package me.exejar.stathead.exejarclick.clickgui.components;

import me.exejar.stathead.Main;
import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.champstats.statapi.HypixelGames;
import me.exejar.stathead.exejarclick.clickgui.ClickGui;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;
import org.apache.commons.lang3.EnumUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntryButton extends Component {

    private String name;
    private ListDisplay listParent;
    private int offset, x, y, height, width;
    private boolean hovered, selected;

    public EntryButton(String name, ListDisplay listParent, int offset, int height) {
        this.name = name;
        this.listParent = listParent;
        this.offset = offset;
        this.x = listParent.x;
        this.y = listParent.y;
        this.width = listParent.width;
        this.height = height;
    }

    @Override
    public void renderComponent(int color) {
        int rectColor = ClickGui.subColor.getRGB();
        if (selected) {
            rectColor = ClickGui.subColor.darker().getRGB();
        }

        RenderingUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hovered ? new Color(rectColor).darker().getRGB() : rectColor);
        if (this.listParent.gameList) {
            Main.fontRenderer.drawString(this.name, this.x + 2, this.y + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.name) / 2), Color.WHITE.getRGB());
        } else {
            Main.fontRenderer.drawString(this.name, this.x + width - (Main.fontRenderer.getWidth(this.name) + 2), this.y + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.name) / 2), Color.WHITE.getRGB());
        }

    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = listParent.y + this.offset;
        this.x = listParent.x;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.listParent.open) {
            if (this.listParent.gameList) {
                List<String> gameStats = new ArrayList<>();

                if (EnumUtils.isValidEnum(HypixelGames.class, this.name.toUpperCase())) {
                    ModConfig.getInstance().setStatMode(this.name);

                    String[] statNames = HypixelGames.valueOf(this.name.toUpperCase()).getStatNames();
                    gameStats.addAll(Arrays.asList(statNames));
                    this.listParent.parent.refreshList(gameStats);
                }
            }

            setSelected(true);
        } else if (this.listParent.isMouseOnEntryList(mouseX, mouseY)) {
            setSelected(false);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }

    public void setSelected(boolean selected) {
        if (!this.listParent.gameList) {
            if (selected) {
                ModConfig.getInstance().setStatName(this.name);
            }
        }
        this.selected = selected;
    }

}
