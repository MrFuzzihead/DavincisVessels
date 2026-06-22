package com.mrfuzzihead.archimedes;

import java.io.IOException;
import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import com.mrfuzzihead.archimedes.common.ArchimedesConfig;
import com.mrfuzzihead.archimedes.common.CommonProxy;
import com.mrfuzzihead.archimedes.common.command.CommandASHelp;
import com.mrfuzzihead.archimedes.common.command.CommandDisassembleNear;
import com.mrfuzzihead.archimedes.common.command.CommandDisassembleShip;
import com.mrfuzzihead.archimedes.common.command.CommandShipInfo;
import com.mrfuzzihead.archimedes.common.entity.EntityParachute;
import com.mrfuzzihead.archimedes.common.entity.EntitySeat;
import com.mrfuzzihead.archimedes.common.entity.EntityShip;
import com.mrfuzzihead.archimedes.common.handler.ConnectionHandler;
import com.mrfuzzihead.archimedes.common.network.ArchimedesShipsMessageToMessageCodec;
import com.mrfuzzihead.archimedes.common.network.ArchimedesShipsPacketHandler;
import com.mrfuzzihead.archimedes.common.network.NetworkUtil;
import com.mrfuzzihead.archimedes.common.object.ArchimedesObjects;
import com.mrfuzzihead.movingworld.MovingWorld;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
    modid = ArchimedesShipMod.MODID,
    name = ArchimedesShipMod.NAME,
    version = ArchimedesShipMod.VERSION,
    dependencies = ArchimedesShipMod.DEPENDENCIES,
    guiFactory = ArchimedesShipMod.GUIFACTORY)
public class ArchimedesShipMod {

    public static final String MODID = "archimedesships";
    public static final String VERSION = Tags.VERSION;
    public static final String NAME = "Archimedes Ships";
    public static final String DEPENDENCIES = "required-after:movingworld@";
    public static final String GUIFACTORY = "com.mrfuzzihead.archimedes.client.gui.ArchimedesGUIFactory";

    public static CreativeTabs creativeTab = new CreativeTabs("archimedesTab") {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ArchimedesObjects.blockEngine);
        }
    };

    @Instance(value = MODID)
    public static ArchimedesShipMod instance;

    @SidedProxy(
        clientSide = "com.mrfuzzihead.archimedes.client.ClientProxy",
        serverSide = "com.mrfuzzihead.archimedes.common.CommonProxy")
    public static CommonProxy proxy;

    public static ArchimedesObjects objects;

    public static Logger modLog;

    public ArchimedesConfig modConfig;
    public NetworkUtil network;

    public ArchimedesShipMod() {
        network = new NetworkUtil();
    }

    @EventHandler
    public void preInitMod(FMLPreInitializationEvent event) {
        modLog = event.getModLog();

        objects = new ArchimedesObjects();

        MinecraftForge.EVENT_BUS.register(this);

        objects.preInit(event);

        modConfig = new ArchimedesConfig(new Configuration(event.getSuggestedConfigurationFile()));
        modConfig.loadAndSave();

    }

    @EventHandler
    public void initMod(FMLInitializationEvent event) {
        modConfig.postLoad();

        try {
            MovingWorld.instance.metaRotations.registerMetaRotationFile(
                "archimedesships.mrot",
                getClass().getResourceAsStream("/mrot/archimedesships.mrot"));
        } catch (IOException e) {
            modLog.error("UNABLE TO LOAD ARCHIMEDESSHIPS.MROT");
        }

        network.channels = NetworkRegistry.INSTANCE
            .newChannel(MODID, new ArchimedesShipsMessageToMessageCodec(), new ArchimedesShipsPacketHandler());

        objects.init(event);

        MinecraftForge.EVENT_BUS.register(new ConnectionHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new ConnectionHandler());

        EntityRegistry.registerModEntity(EntityShip.class, "shipmod", 1, this, 64, modConfig.shipEntitySyncRate, true);
        EntityRegistry.registerModEntity(EntitySeat.class, "attachment.seat", 2, this, 64, 100, false);
        EntityRegistry
            .registerModEntity(EntityParachute.class, "parachute", 3, this, 32, modConfig.shipEntitySyncRate, true);

        proxy.registerKeyHandlers(modConfig);
        proxy.registerEventHandlers();
        proxy.registerRenderers();

        modConfig.addBlacklistWhitelistEntries();
    }

    @EventHandler
    public void postInitMod(FMLPostInitializationEvent event) {}

    @SuppressWarnings("unchecked")
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        registerASCommand(event, new CommandASHelp());
        registerASCommand(event, new CommandDisassembleShip());
        registerASCommand(event, new CommandShipInfo());
        registerASCommand(event, new CommandDisassembleNear());
        Collections.sort(CommandASHelp.asCommands);
    }

    private void registerASCommand(FMLServerStartingEvent event, CommandBase commandbase) {
        event.registerServerCommand(commandbase);
        CommandASHelp.asCommands.add(commandbase);
    }

    private void registerBlock(String id, Block block) {
        registerBlock(id, block, ItemBlock.class);
    }

    private void registerBlock(String id, Block block, Class<? extends ItemBlock> itemblockclass) {
        block.setBlockName("archimedes." + id);
        block.setBlockTextureName("archimedes:" + id);
        GameRegistry.registerBlock(block, itemblockclass, id);
    }
}
