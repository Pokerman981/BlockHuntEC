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

import static me.pokerman99.BlockHuntEC.Main.rootNode;

public class addBlockLocationCommand implements CommandExecutor {

    Optional<BlockType> blockType;
    CommandSource src;
    String message;
    int total;


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        message = args.getOne("hunt name").toString().toUpperCase();
        blockType = args.getOne(Text.of("block"));
        total = 0;
        this.src = src;
        UUID randomuuid = UUID.randomUUID();

        if (!Main.hunts.values().contains(message)) {
            {
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
        temp.add(randomuuid.toString());

        Main.adding.put(UUID.fromString(src.getIdentifier()), new DATA(temp));

        Utils.sendMessage(src, "&aRight click");
    }

    void createNewHunt() {
        {
            if (!blockType.isPresent()) {
                Utils.sendMessage(src, "&cWhen making a new hunt you must supply a block that the players must check!");
                return;
            }
        }

        {
            Main.hunts.put(message, message);

            rootNode.getNode("hunts").setValue(Main.hunts.<List<String>>values());
            rootNode.getNode("totals", message).setValue(0);
            Main.getInstance().save();

            Utils.sendMessage(src, "&aCreating new hunt named, " + message + "!");
        }
    }

    void alreadyExists() {
        Utils.sendMessage(src, "&cAdding new location to hunt" + message + "!");
        total = rootNode.getNode("totals", message).getInt();
    }

}

