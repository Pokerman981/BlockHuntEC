package me.pokerman99.BlockHuntEC.commands;

import me.pokerman99.BlockHuntEC.Main;
import me.pokerman99.BlockHuntEC.Utils;
import me.pokerman99.BlockHuntEC.data.DATA;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static me.pokerman99.BlockHuntEC.Main.hunts;
import static me.pokerman99.BlockHuntEC.Main.rootNode;

public class addBlockLocationCommand implements CommandExecutor {

    private CommandSource src;
    private String message;
    private Optional<Integer> limit;
    private int total;


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        message = args.getOne("hunt name").get().toString().toUpperCase();
        limit = args.getOne(Text.of("limit"));
        total = 0;
        this.src = src;
        UUID randomuuid = UUID.randomUUID();

        if (!Main.hunts.contains(message)) {
            {
                if (!limit.isPresent()) {
                    Utils.sendMessage(src, "&cYou must supply a limit whe making a new hunt!");
                    return CommandResult.empty();
                }

                createNewHunt();

            }
        } else {
            {
                alreadyExists();
            }
        }

        continueAddInfoToBlock(randomuuid);

        return CommandResult.success();
    }

    void continueAddInfoToBlock(UUID randomuuid) {
        List<String> temp = new ArrayList<>();
        temp.add("blockhuntec");
        temp.add(String.valueOf((total + 1)));
        temp.add(message);
        //temp.add(blockType.get().getId());
        temp.add(randomuuid.toString());

        Main.adding.put(UUID.fromString(src.getIdentifier()), new DATA(temp));

        Utils.sendMessage(src, "&aRight click any block to set location!");
    }

    void createNewHunt() {
        {
            Main.hunts.add(message);

            rootNode.getNode("hunts", "Zenabled").setValue(Main.hunts);
            rootNode.getNode("hunts", message ,"total").setValue(0);
            rootNode.getNode("hunts", message ,"limit").setValue(limit.get());
            Main.getInstance().save();

            Utils.sendMessage(src, "&aCreating new hunt named, " + message + "!");
        }
    }

    void alreadyExists() {
        Utils.sendMessage(src, "&cAdding new location to hunt " + message + "!");
        total = rootNode.getNode("hunts", message ,"total").getInt();
    }

}

