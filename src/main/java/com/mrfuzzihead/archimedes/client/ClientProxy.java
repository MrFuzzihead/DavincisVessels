package com.mrfuzzihead.archimedes.client;

import com.mrfuzzihead.archimedes.client.control.ShipKeyHandler;
import com.mrfuzzihead.archimedes.client.handler.ClientHookContainer;
import com.mrfuzzihead.archimedes.client.render.RenderBlockGauge;
import com.mrfuzzihead.archimedes.client.render.RenderBlockSeat;
import com.mrfuzzihead.archimedes.client.render.RenderParachute;
import com.mrfuzzihead.archimedes.client.render.TileEntityGaugeRenderer;
import com.mrfuzzihead.archimedes.common.ArchimedesConfig;
import com.mrfuzzihead.archimedes.common.CommonProxy;
import com.mrfuzzihead.archimedes.common.entity.EntityParachute;
import com.mrfuzzihead.archimedes.common.entity.EntityShip;
import com.mrfuzzihead.archimedes.common.object.block.BlockGauge;
import com.mrfuzzihead.archimedes.common.object.block.BlockSeat;
import com.mrfuzzihead.archimedes.common.tileentity.TileEntityGauge;
import com.mrfuzzihead.movingworld.client.render.RenderMovingWorld;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

    public ShipKeyHandler shipKeyHandler;

    @Override
    public ClientHookContainer getHookContainer() {
        return new ClientHookContainer();
    }

    @Override
    public void registerKeyHandlers(ArchimedesConfig cfg) {
        shipKeyHandler = new ShipKeyHandler(cfg);
        FMLCommonHandler.instance()
            .bus()
            .register(shipKeyHandler);
    }

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityShip.class, new RenderMovingWorld());
        RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new RenderParachute());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGauge.class, new TileEntityGaugeRenderer());
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHelm.class, new TileEntityHelmRenderer());
        BlockGauge.gaugeBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
        BlockSeat.seatBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(BlockSeat.seatBlockRenderID, new RenderBlockSeat());
        RenderingRegistry.registerBlockHandler(BlockGauge.gaugeBlockRenderID, new RenderBlockGauge());
    }
}
