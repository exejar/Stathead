package me.exejar.stathead.commands;

import me.exejar.stathead.exejarclick.clickgui.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class StatheadGui extends SHCommandBase {

    public ClickGui clickGui;

    @Override
    public String getCommandName() {
        return "stathead";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.clickGui == null) this.clickGui = new ClickGui();
        Minecraft.getMinecraft().displayGuiScreen(this.clickGui);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
