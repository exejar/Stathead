package me.dooger.betterlvlhead.events;

import me.dooger.betterlvlhead.champstats.config.ModConfig;
import me.dooger.betterlvlhead.champstats.utils.Handler;
import me.dooger.betterlvlhead.utils.ChatColor;
import me.dooger.betterlvlhead.utils.ChatUtils;
import me.dooger.betterlvlhead.utils.References;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ApiNewEvent {

    @SubscribeEvent
    public void onApiNew(ClientChatReceivedEvent event) {
        String umsg = event.message.getUnformattedText();

        if (umsg.startsWith("Your new API key is ")) {
            Handler.asExecutor(()-> {
                String API_KEY = umsg.replace("Your new API key is ", "");
                ModConfig.getInstance().setApiKey(API_KEY);
                ModConfig.getInstance().save();
                ChatUtils.sendMessage(ChatColor.GREEN + References.MODNAME + " found and set API key!");
            });
        }
    }

}
