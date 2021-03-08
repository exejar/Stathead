package me.exejar.stathead.exejarclick.clickgui.components;

public abstract class Component {

    public abstract void renderComponent(int color);

    public abstract void updateComponent(int mouseX, int mouseY);

    public abstract void mouseClicked(int mouseX, int mouseY, int button);

    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    public abstract void setOff(int newOff);

    public int getHeight() { return 0; }

}
