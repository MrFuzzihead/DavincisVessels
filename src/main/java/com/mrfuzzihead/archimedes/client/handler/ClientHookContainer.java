package com.mrfuzzihead.archimedes.client.handler;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.mrfuzzihead.archimedes.common.entity.EntityShip;
import com.mrfuzzihead.archimedes.common.handler.CommonHookContainer;
import com.mrfuzzihead.movingworld.MovingWorld;
import com.mrfuzzihead.movingworld.common.network.RequestMovingWorldDataMessage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHookContainer extends CommonHookContainer {

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        if (event.world.isRemote && event.entity instanceof EntityShip) {
            if (((EntityShip) event.entity).getMovingWorldChunk().chunkTileEntityMap.isEmpty()) {
                return;
            }

            RequestMovingWorldDataMessage msg = new RequestMovingWorldDataMessage((EntityShip) event.entity);
            MovingWorld.instance.network.sendToServer(msg);
        }
    }

}
