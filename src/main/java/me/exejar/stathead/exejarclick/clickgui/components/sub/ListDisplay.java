package me.exejar.stathead.exejarclick.clickgui.components.sub;

import me.exejar.stathead.Main;
import me.exejar.stathead.exejarclick.clickgui.components.Component;
import me.exejar.stathead.exejarclick.clickgui.components.Frame;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListDisplay extends Component {

    public Frame parent;
    public int offset;
    private boolean hovered;
    public boolean open;
    private List<Component> subComponents;
    private int height;

    public ListDisplay(Frame parent, int offset, List<String> entries) {
        this.parent = parent;
        this.offset = offset;
        this.subComponents = new ArrayList<>();

        int elementHeight = 20;
        this.height = elementHeight;
        int spacer = offset + elementHeight;

        for (String s : entries) {
            this.subComponents.add(new EntryButton(s, this, offset, spacer));
            spacer += elementHeight;
        }
    }

    @Override
    public void renderComponent(int color) {
        RenderingUtils.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + this.height + offset, this.hovered ? new Color(color).darker().getRGB() : color);
        Main.fontRenderer.drawString(this.parent.name, parent.getX() + 2, parent.getY() + offset + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.parent.name) / 2), Color.WHITE.getRGB());

        if (this.open) {
            for (Component component : subComponents) {
                component.renderComponent(color);
            }
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        for (Component component : this.subComponents) {
            component.updateComponent(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) & button == 1) {
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
        offset = newOff;
        int spacer = offset + this.height;
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
        return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + this.height + this.offset;
    }

}
