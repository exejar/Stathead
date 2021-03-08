package me.exejar.stathead.exejarclick.clickgui.components.sub;

import me.exejar.stathead.Main;
import me.exejar.stathead.exejarclick.clickgui.components.Component;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;

import java.awt.*;

public class EntryButton extends Component {

    private String name;
    private ListDisplay listParent;
    private int offset, x, y, height;
    private boolean hovered, selected;

    public EntryButton(String name, ListDisplay listParent, int offset, int height) {
        this.name = name;
        this.listParent = listParent;
        this.offset = offset;
        this.x = listParent.parent.getX() + listParent.parent.getWidth();
        this.y = listParent.parent.getY() + listParent.offset;
        this.height = height;
    }

    @Override
    public void renderComponent(int color) {
        int rectColor = color;
        if (selected) {
            rectColor = new Color(color).darker().getRGB();
        }

        RenderingUtils.drawRect(listParent.parent.getX(), listParent.parent.getY() + offset, listParent.parent.getX() + listParent.parent.getWidth(), listParent.parent.getY() + offset + this.height, this.hovered ? new Color(rectColor).darker().getRGB() : rectColor);
        Main.fontRenderer.drawString(this.name, listParent.parent.getX() + 5, listParent.parent.getY() + offset + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.name) / 2), Color.WHITE.getRGB());
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = listParent.parent.getY() + offset;
        this.x = listParent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.listParent.open) {
            selected = true;
            // refresh lists
        } else {
            selected = false;
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
        return x > this.x && x < this.x + this.listParent.parent.getWidth() && y > this.y && y < this.y + this.height;
    }

}
