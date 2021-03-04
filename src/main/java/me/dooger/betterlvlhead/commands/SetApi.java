package me.dooger.betterlvlhead.commands;

import me.dooger.betterlvlhead.champstats.config.ModConfig;
import me.dooger.betterlvlhead.champstats.utils.Handler;
import me.dooger.betterlvlhead.utils.ChatColor;
import me.dooger.betterlvlhead.utils.ChatUtils;
import me.dooger.betterlvlhead.utils.References;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SetApi extends BLHCommandBase {
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
