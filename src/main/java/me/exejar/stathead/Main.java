package me.exejar.stathead;

import me.exejar.stathead.champstats.statapi.HGameBase;
import me.exejar.stathead.champstats.statapi.HPlayer;
import me.exejar.stathead.champstats.statapi.HypixelGames;
import me.exejar.stathead.champstats.statapi.bedwars.Bedwars;
import me.exejar.stathead.champstats.statapi.general.General;
import me.exejar.stathead.champstats.utils.Handler;
import me.exejar.stathead.commands.SetApi;
import me.exejar.stathead.commands.StatheadGui;
import me.exejar.stathead.events.render.AboveHeadDisplay;
import me.exejar.stathead.events.HeadDisplayTick;
import me.exejar.stathead.events.render.TagRenderer;
import me.exejar.stathead.exejarclick.clickgui.util.CustomFontRenderer;
import me.exejar.stathead.utils.References;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.UUID;

@Mod(modid = References.MODID, name = References.MODNAME, clientSideOnly = true, version = References.VERSION, acceptedMinecraftVersions = "1.8.9")
public class Main {

    private AboveHeadDisplay tagDisplay;

    private static Main instance;
    public static CustomFontRenderer fontRenderer;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        fontRenderer = new CustomFontRenderer(new Font("Tahoma", Font.PLAIN, 16), true);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        tagDisplay = new AboveHeadDisplay();
        registerCommands(new SetApi(), new StatheadGui());
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
            tagDisplay.removeFromStatAssembly(uuid);
        });
    }
}
