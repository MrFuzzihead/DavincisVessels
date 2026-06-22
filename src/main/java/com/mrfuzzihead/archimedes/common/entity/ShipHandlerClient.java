package com.mrfuzzihead.archimedes.common.entity;

import com.mrfuzzihead.movingworld.common.entity.EntityMovingWorld;
import com.mrfuzzihead.movingworld.common.entity.MovingWorldHandlerClient;

public class ShipHandlerClient extends MovingWorldHandlerClient {

    private EntityMovingWorld movingWorld;

    public ShipHandlerClient(EntityMovingWorld movingWorld) {
        super(movingWorld);
    }

    @Override
    public EntityMovingWorld getMovingWorld() {
        return movingWorld;
    }

    @Override
    public void setMovingWorld(EntityMovingWorld movingWorld) {
        this.movingWorld = movingWorld;
    }
}
