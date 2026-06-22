package com.mrfuzzihead.archimedes.client.control;

import net.minecraft.entity.player.EntityPlayer;

import com.mrfuzzihead.archimedes.ArchimedesShipMod;
import com.mrfuzzihead.archimedes.common.control.ShipControllerCommon;
import com.mrfuzzihead.archimedes.common.entity.EntityShip;
import com.mrfuzzihead.archimedes.common.network.ControlInputMessage;

public class ShipControllerClient extends ShipControllerCommon {

    @Override
    public void updateControl(EntityShip ship, EntityPlayer player, int i) {
        super.updateControl(ship, player, i);
        ControlInputMessage message = new ControlInputMessage(ship, i);
        ArchimedesShipMod.instance.network.sendToServer(message);
    }
}
