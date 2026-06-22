package com.mrfuzzihead.archimedes.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.mrfuzzihead.archimedes.ArchimedesShipMod;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientOpenGuiMessage extends ArchimedesShipsMessage {

    public int guiID;

    public ClientOpenGuiMessage() {
        guiID = 0;
    }

    public ClientOpenGuiMessage(int id) {
        guiID = id;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf, Side side) {
        buf.writeInt(guiID);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf, EntityPlayer player, Side side) {
        guiID = buf.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayer player) {
        player.openGui(ArchimedesShipMod.instance, guiID, player.worldObj, 0, 0, 0);
    }

}
