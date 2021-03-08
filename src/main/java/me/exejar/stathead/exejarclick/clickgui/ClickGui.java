package me.exejar.stathead.exejarclick.clickgui;

import me.exejar.stathead.Main;
import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.exejarclick.clickgui.components.Component;
import me.exejar.stathead.exejarclick.clickgui.components.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

public class ClickGui extends GuiScreen {

    public Frame mainFrame;

    public static int frameWidth = 300;
    public static int frameHeight = 150;
    public static int barHeight = 15;
    public static Color rectColor = new Color(55, 55, 55, 180);
    private Color headerColor = new Color(255, 80, 80, 180);
    public static Color subColor = new Color(100, 100, 100, 180);
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
        if (!rainbow) {
            color = Color.HSBtoRGB(System.currentTimeMillis() % 8000L / 8000.0F, 0.7f, 1.0f);
        } else {
            color = headerColor.getRGB();
        }
        mainFrame.renderFrame(Main.fontRenderer, color);
        mainFrame.updatePosition(mouseX, mouseY);

        int i = mainFrame.getX() + mainFrame.getWidth() / 2;
        int j = mainFrame.getY() + mainFrame.getHeight() - 5;

        drawEntityOnScreen(i, j, 50, (float)(i) - mouseX, (float)(j - 80) - mouseY, this.mc.thePlayer);

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

    private void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = entity.renderYawOffset;
        float f1 = entity.rotationYaw;
        float f2 = entity.rotationPitch;
        float f3 = entity.prevRotationYawHead;
        float f4 = entity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        entity.renderYawOffset = f;
        entity.rotationYaw = f1;
        entity.rotationPitch = f2;
        entity.prevRotationYawHead = f3;
        entity.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}
