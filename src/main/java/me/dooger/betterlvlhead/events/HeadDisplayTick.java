package me.dooger.betterlvlhead.events;

import me.dooger.betterlvlhead.events.render.HPlayerDisplay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HeadDisplayTick {

    /* Can be converted to a Display Manager similar to sk1ers if Tab or Chat stats are wanted */
    HPlayerDisplay display;

    public HeadDisplayTick(HPlayerDisplay display) {
        this.display = display;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START || Minecraft.getMinecraft().theWorld == null)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.isGamePaused() && mc.thePlayer != null) {
            display.onTick();
        }
    }

}
