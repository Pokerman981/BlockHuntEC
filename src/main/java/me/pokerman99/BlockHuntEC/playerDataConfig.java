package me.pokerman99.BlockHuntEC;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class playerDataConfig {

    private File file;
    private CommentedConfigurationNode playerDataConfig;
    private ConfigurationLoader<CommentedConfigurationNode> playerDataLoader;

    public playerDataConfig(String uuid) {
        try {
            setFile(new File(Main.getInstance().ConfigDir.toFile().getParentFile(), "blockhuntec/playerdata/" + uuid + ".conf"));
            getFile().getParentFile().mkdirs();

            playerDataLoader = HoconConfigurationLoader.builder().setFile(getFile()).build();
            playerDataConfig = playerDataLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public CommentedConfigurationNode getPlayerDataConfig() {
        return playerDataConfig;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getPlayerDataLoader() {
        return playerDataLoader;
    }

    public void setCreationTime() {
        try {
            if (playerDataConfig.getNode("Date-Created").isVirtual()) {
                playerDataConfig.getNode("Date-Created").setValue(Instant.now().toString());
                playerDataLoader.save(playerDataConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
