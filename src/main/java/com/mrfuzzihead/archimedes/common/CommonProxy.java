package com.mrfuzzihead.archimedes.common;

import net.minecraftforge.common.MinecraftForge;

import com.mrfuzzihead.archimedes.ArchimedesShipMod;
import com.mrfuzzihead.archimedes.client.gui.ASGuiHandler;
import com.mrfuzzihead.archimedes.common.handler.CommonHookContainer;
import com.mrfuzzihead.archimedes.common.handler.CommonPlayerTicker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public CommonPlayerTicker playerTicker;
    public CommonHookContainer hookContainer;

    public CommonHookContainer getHookContainer() {
        return new CommonHookContainer();
    }

    public void registerKeyHandlers(ArchimedesConfig cfg) {}

    public void registerEventHandlers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ArchimedesShipMod.instance, new ASGuiHandler());

        playerTicker = new CommonPlayerTicker();
        FMLCommonHandler.instance()
            .bus()
            .register(playerTicker);
        MinecraftForge.EVENT_BUS.register(hookContainer = getHookContainer());
    }

    public void registerRenderers() {}

}
