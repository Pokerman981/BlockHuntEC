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

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String message = args.getOne("hunt name").toString().toUpperCase();
        Optional<BlockType> blockType = args.getOne(Text.of("block"));
        int total = 0;
        UUID randomuuid = UUID.randomUUID();

        if (!Main.hunts.values().contains(message)) {
            {
                if (!blockType.isPresent()) {
                    Utils.sendMessage(src, "&cWhen making a new hunt you must supply a block!");
                    return CommandResult.empty();
                }
            }

            {
                Main.hunts.put(message, message);

                rootNode.getNode("hunts").setValue(Main.hunts.<List<String>>values());
                rootNode.getNode("totals", message).setValue(0);
                Main.getInstance().save();

                Utils.sendMessage(src, "&aCreating new hunt named, " + message + "!");
            }
        } else {
            {
                Utils.sendMessage(src, "&cAdding new location to hunt" + message + "!");
                total = rootNode.getNode("totals", message).getInt();
            }
        }


        List<String> temp = new ArrayList<>();
        temp.add("blockhuntec");
        temp.add(String.valueOf((total + 1)));
        temp.add(randomuuid.toString());

        Main.adding.put(UUID.fromString(src.getIdentifier()), new DATA(temp));

        Utils.sendMessage(src, "&aRight click");



        return CommandResult.success();
    }
}
