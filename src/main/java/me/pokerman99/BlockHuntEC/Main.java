package me.pokerman99.BlockHuntEC;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import me.pokerman99.BlockHuntEC.commands.addBlockLocationCommand;
import me.pokerman99.BlockHuntEC.data.DATA;
import me.pokerman99.BlockHuntEC.listeners.FoundListener;
import me.pokerman99.BlockHuntEC.listeners.InteractBlockListener;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

@Plugin(id = "blockhuntec",
        name = "BlockHuntEC",
        version = "1.0",
        description = "Plugin for Justin's servers providing a block hunt for the players")

public class Main {

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @org.spongepowered.api.config.ConfigDir(sharedRoot = false)
    public Path ConfigDir;

    @Inject
    public PluginContainer plugin;
    public PluginContainer getPlugin() {
        return this.plugin;
    }

    public static CommentedConfigurationNode rootNode;
    public static CommentedConfigurationNode config() {
        return rootNode;
    }


    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public static Main instance;

    public static Main getInstance(){
        return instance;
    }

    public static EconomyService economyService;

    public static Map<UUID, DATA> adding = new HashMap<>();
    public static List<String> removing = new ArrayList<>();

    public static List<String> hunts = new ArrayList<>();


    @Listener
    public void onInit(GameInitializationEvent event) throws IOException {
        {
            Optional<EconomyService> optionalEconomyService = Sponge.getServiceManager().provide(EconomyService.class);
            economyService = optionalEconomyService.get();
            rootNode = loader.load();
            instance = this;
        }

        {
            if (!defaultConfig.toFile().exists()) {
                generateConfig();
                save();
            }
        }

        { //So I can store data in blocks
            dataRegistration();
            populateVariables();
            registerCommands();
            registerListeners();
        }
    }

    void generateConfig() {
        rootNode.getNode("config-version").setValue(1.0);

        List<String> hunts = new ArrayList<>();
        rootNode.getNode("hunts", "Zenabled").setValue(hunts);
    }

    void populateVariables() {
        {
            try {
                rootNode.getNode("hunts", "Zenabled").getList(TypeToken.of(String.class)).forEach(s -> hunts.add(s));
            } catch (ObjectMappingException e) {e.printStackTrace();}
        }

    }

    void registerCommands() {
        CommandSpec addBlockLcationCommand = CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("hunt name"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("limit"))))
                .permission("blockhuntec.admin.addlocation")
                .executor(new addBlockLocationCommand())
                .build();

        CommandSpec blockHuntMainCommand = CommandSpec.builder()
                .permission("blockhuntec.admin")
                .child(addBlockLcationCommand, "add")
                .build();

        Sponge.getCommandManager().register(instance, blockHuntMainCommand, "blockhunt");
    }

    void registerListeners() {
        Sponge.getEventManager().registerListeners(instance, new InteractBlockListener());
        Sponge.getEventManager().registerListeners(instance, new FoundListener());
    }

    void dataRegistration() {
        DataRegistration.builder()
                .dataClass(DATA.class)
                .immutableClass(DATA.Immutable.class)
                .builder(new DATA.Builder())
                .manipulatorId("blockhunts")
                .dataName("blockhunt")
                .buildAndRegister(Sponge.getPluginManager().getPlugin("blockhuntec").get());
    }











































    public void save() {
        try {
            loader.save(config());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
