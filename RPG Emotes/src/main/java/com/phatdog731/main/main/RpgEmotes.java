package com.phatdog731.main.main;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Plugin(id = "rpgemotes", name = "RPG Emotes", version = "alpha-0.1")
public class RpgEmotes {
    @Inject
    Game game;

    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public File configuration = null;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> configurationLoader = null;

    public CommentedConfigurationNode configurationNode = null;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Listener
    public void preInit(GamePreInitializationEvent event) {

    }

    @Listener
    public void init(GameInitializationEvent event) {
        try{
            if(!configuration.exists()){
                logger.info("Unable to find default config! Generating!");
                configuration.createNewFile();
                configurationNode = configurationLoader.load();
                configurationNode.getNode("emotes").setComment("This is the default configuration file");
                configurationNode.getNode("emotes").setComment("use 'emotes' to create emotes that you can use by default");
                configurationNode.getNode("vipEmotes").setComment("use 'vipEmotes' to create emotes for VIPs");
                configurationNode.getNode("emotes", "dance", "self").setValue("You dance the night away!");
                configurationNode.getNode("emotes", "dance", "other").setValue("has an intimate dance with %PLAYER%!");
                configurationNode.getNode("emotes", "dance", "enabled").setValue(true);
                configurationNode.getNode("vipEmotes", "arrow", "self").setValue("Looks like you won't be adventuring any time soon...");
                configurationNode.getNode("vipEmotes", "arrow", "other").setValue("has shot %PLAYER% in the knee!");
                configurationNode.getNode("vipEmotes", "arrow", "enabled").setValue(true);
                configurationLoader.save(configurationNode);
            }
            configurationNode = configurationLoader.load();
        }catch(IOException exception){
            exception.printStackTrace();
        }

    }
    @Listener
    public void postInit(GamePostInitializationEvent event) {

    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("RPG Emotes has started!");
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        logger.info("RPG Emotes has Stopped!");
    }

}
