package me.dooger.betterlvlhead.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {

    public static void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    public static void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.translateAlternateColorCodes(message)));
    }

    public static void sendCommand(String command) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + command);
    }

    public static void sleep(int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException ignored) {}
    }

}
