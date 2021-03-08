package me.exejar.stathead.exejarclick.clickgui.components;

import me.exejar.stathead.Main;
import me.exejar.stathead.exejarclick.clickgui.ClickGui;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListDisplay extends Component {

    public Frame parent;
    public int x, y, width, yoff, xoff;
    private boolean hovered;
    public boolean open;
    private List<Component> subComponents;
    private int height;
    public boolean gameList;
    public String name;

    public ListDisplay(Frame parent, String name, int x, int y, int yoff, int xoff, List<String> entries, boolean gameList) {
        this.parent = parent;
        this.name = name;
        this.x = x + xoff;
        this.y = y + yoff;
        this.yoff = yoff;
        this.xoff = xoff;
        this.subComponents = new ArrayList<>();
        this.gameList = gameList;

        this.width = 100;

        int elementHeight = ClickGui.barHeight;
        this.height = elementHeight;
        int spacer = this.y + elementHeight;

        for (String s : entries) {
            this.subComponents.add(new EntryButton(s, this, spacer, elementHeight));
            spacer += elementHeight;
        }
    }

    @Override
    public void renderComponent(int color) {
        RenderingUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hovered ? new Color(color).darker().getRGB() : color);
        Main.fontRenderer.drawStringWithShadow(this.name, this.x + ((float)(this.width / 2) - (Main.fontRenderer.getWidth(this.name) / 2)), this.y + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.name) / 2), Color.WHITE.getRGB());

        if (this.open) {
            for (Component component : subComponents) {
                component.renderComponent(color);
            }
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.getY() + this.yoff;
        this.x = parent.getX() + this.xoff;
        for (Component component : this.subComponents) {
            component.updateComponent(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) & (button == 0 || button == 1)) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component component : this.subComponents) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component component : this.subComponents) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void setOff(int newOff) {
        int spacer = newOff;

        for (Component component : this.subComponents) {
            component.setOff(spacer);
            spacer += this.height;
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return this.height * (this.subComponents.size() + 1);
        }
        return this.height;
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.height + this.y;
    }

    public void updateList(List<String> entries) {
        /* Could use a concurrent CopyOnWriteArrayList, although this is the exact same outcome. */
        int elementHeight = ClickGui.barHeight;
        int spacer = elementHeight;

        List<Component> comp = new ArrayList<>();

        for (String s : entries) {
            EntryButton button = new EntryButton(s, this, spacer, elementHeight);

            if (entries.indexOf(s) == 0) {
                button.setSelected(true);
            }

            comp.add(button);
            spacer += elementHeight;
        }

        this.subComponents = comp;
    }

    public boolean isMouseOnEntryList(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y + this.height && y < this.y + this.height + (this.height * this.subComponents.size() + 1);
    }

}
