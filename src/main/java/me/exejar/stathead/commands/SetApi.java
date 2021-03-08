package me.exejar.stathead.commands;

import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.champstats.utils.Handler;
import me.exejar.stathead.utils.ChatColor;
import me.exejar.stathead.utils.ChatUtils;
import me.exejar.stathead.utils.References;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SetApi extends SHCommandBase {
    @Override
    public String getCommandName() {
        return "setapi";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            ChatUtils.sendCommand("api new");
        } else if (!args[0].isEmpty()){
            Handler.asExecutor(()-> {
                ModConfig.getInstance().setApiKey(args[0]);
                ModConfig.getInstance().save();
                ChatUtils.sendMessage(ChatColor.GREEN + References.MODNAME + " API key saved!");
            });
        }
    }

}
