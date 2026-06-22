package com.mrfuzzihead.archimedes.common.entity;

import net.minecraft.entity.player.EntityPlayer;

import com.mrfuzzihead.movingworld.common.entity.EntityMovingWorld;
import com.mrfuzzihead.movingworld.common.entity.MovingWorldHandlerServer;

public class ShipHandlerServer extends MovingWorldHandlerServer {

    private EntityMovingWorld movingWorld;
    private boolean firstChunkUpdate;

    public ShipHandlerServer(EntityShip entityship) {
        super(entityship);
        firstChunkUpdate = true;
    }

    @Override
    public EntityMovingWorld getMovingWorld() {
        return movingWorld;
    }

    @Override
    public void setMovingWorld(EntityMovingWorld movingWorld) {
        this.movingWorld = movingWorld;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (movingWorld.riddenByEntity == null) {
            player.mountEntity(movingWorld);
            return true;
        } else {
            if (player.ridingEntity != null) player.mountEntity(null);
            return movingWorld.getCapabilities()
                .mountEntity(player);
        }
    }

    @Override
    public void onChunkUpdate() {
        super.onChunkUpdate();
        if (firstChunkUpdate) {
            ((ShipCapabilities) movingWorld.getCapabilities()).spawnSeatEntities();
            movingWorld.getDataWatcher()
                .updateObject(
                    27,
                    ((ShipCapabilities) movingWorld.getCapabilities()).canSubmerge() ? new Byte((byte) 1)
                        : new Byte((byte) 0));
        }
    }
}
