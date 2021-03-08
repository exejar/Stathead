package me.exejar.stathead.exejarclick.clickgui.components.sub;

import me.exejar.stathead.Main;
import me.exejar.stathead.exejarclick.clickgui.ClickGui;
import me.exejar.stathead.exejarclick.clickgui.components.Component;
import me.exejar.stathead.exejarclick.clickgui.util.RenderingUtils;

import java.awt.*;

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

//        return x > this.x && x < this.x + this.listParent.parent.getWidth() && y > this.y && y < this.y + this.height;

        RenderingUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hovered ? new Color(rectColor).darker().getRGB() : rectColor);
        Main.fontRenderer.drawString(this.name, this.x + 2, this.y + (float)(this.height / 2 - Main.fontRenderer.getHeight(this.name) / 2), Color.WHITE.getRGB());
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
            this.listParent.parent.refreshLists();
            selected = true;
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
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }

}
