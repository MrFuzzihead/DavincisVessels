package com.mrfuzzihead.archimedes.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.mrfuzzihead.archimedes.ArchimedesShipMod;
import com.mrfuzzihead.archimedes.common.entity.EntityShip;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class ShipMessage extends ArchimedesShipsMessage {

    public EntityShip ship;

    public ShipMessage() {
        ship = null;
    }

    public ShipMessage(EntityShip ship) {
        this.ship = ship;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf, Side side) {
        buf.writeInt(ship.getEntityId());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf, EntityPlayer player, Side side) {
        int entityID = buf.readInt();

        Entity entity = player.worldObj.getEntityByID(entityID);
        if (entity instanceof EntityShip) {
            ship = (EntityShip) entity;
        } else {
            ArchimedesShipMod.modLog.warn("Unable to find Ship entity with ID " + entityID);
        }
    }
}
