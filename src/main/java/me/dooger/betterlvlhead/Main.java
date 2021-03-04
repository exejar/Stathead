package me.dooger.betterlvlhead;

import me.dooger.betterlvlhead.champstats.statapi.HGameBase;
import me.dooger.betterlvlhead.champstats.statapi.HPlayer;
import me.dooger.betterlvlhead.champstats.statapi.HypixelGames;
import me.dooger.betterlvlhead.champstats.statapi.bedwars.Bedwars;
import me.dooger.betterlvlhead.champstats.statapi.general.General;
import me.dooger.betterlvlhead.champstats.utils.Handler;
import me.dooger.betterlvlhead.events.render.AboveHeadDisplay;
import me.dooger.betterlvlhead.events.HeadDisplayTick;
import me.dooger.betterlvlhead.events.render.TagRenderer;
import me.dooger.betterlvlhead.utils.References;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;
import java.util.UUID;

@Mod(modid = References.MODID, name = References.MODNAME, clientSideOnly = true, version = References.VERSION, acceptedMinecraftVersions = "1.8.9")
public class Main {

    private AboveHeadDisplay tagDisplay;

    private static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        tagDisplay = new AboveHeadDisplay();
        registerListeners(new TagRenderer(this), new HeadDisplayTick(tagDisplay));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public static Main getInstance() { return instance; }

    private void registerListeners(Object... objects) {
        for (Object o : objects) {
            MinecraftForge.EVENT_BUS.register(o);
        }
    }

    private void registerCommands(ICommand... command) {
        Arrays.stream(command).forEachOrdered(ClientCommandHandler.instance::registerCommand);
    }

    public AboveHeadDisplay getTagDisplay() { return this.tagDisplay; }

    public void fetchStats(EntityPlayer entityPlayer, HypixelGames game) {
        Handler.asExecutor(()-> {
            final UUID uuid = entityPlayer.getUniqueID();
            HGameBase gameBase = null;



            /* need to find a better way of doing this async..im lazy :p */
            switch (game) {
                case GENERAL:
                    gameBase = new General(entityPlayer.getName(), uuid.toString().replace("-", ""));
                    break;
                case BEDWARS:
                    gameBase = new Bedwars(entityPlayer.getName(), uuid.toString().replace("-", ""));
                    break;
            }

            if (gameBase == null) {
                return;
            }

            HPlayer hPlayer = new HPlayer(uuid.toString().replace("-", ""), entityPlayer.getName(), gameBase);

            tagDisplay.getCache().put(uuid, hPlayer);
        });
    }
}
