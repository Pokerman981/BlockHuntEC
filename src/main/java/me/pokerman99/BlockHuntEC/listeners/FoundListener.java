package me.pokerman99.BlockHuntEC.listeners;

import me.pokerman99.BlockHuntEC.Main;
import me.pokerman99.BlockHuntEC.Objects.FoundEvent;
import me.pokerman99.BlockHuntEC.Utils;
import me.pokerman99.BlockHuntEC.playerDataConfig;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class FoundListener {


    @Listener
    public void onFoundListener(FoundEvent event, @First Player player) throws IOException {
        playerDataConfig playerDataConfig = new playerDataConfig(player.getIdentifier());
        CommentedConfigurationNode playerConfig = playerDataConfig.getPlayerDataConfig();
        playerDataConfig.setCreationTime();

        int id = Integer.valueOf(event.getData().getBlockhuntdata().get(1));
        String huntname = event.getData().getBlockhuntdata().get(2);
        String rdmUuid = event.getData().getBlockhuntdata().get(3);

        if (playerConfig.getNode("active-hunts", huntname, "mark").isVirtual()) {
            playerConfig.getNode("active-hunts", huntname, "mark").setValue(0);
            playerDataConfig.getPlayerDataLoader().save(playerConfig);
        }

        int huntLimit = Main.rootNode.getNode("hunts", huntname, "limit").getInt();
        int playerMark = playerConfig.getNode("active-hunts", huntname, "mark").getInt();

        //if not complete aka not there
        boolean playerIsCompleted = playerConfig.getNode("completed-hunts", huntname).isVirtual();

        if (playerIsCompleted == false) {
            Utils.sendMessage(player, "&cYou have already successfully found all the Pokes!");
            return;
        }

        //Not in order and making sure it's not done
        if ((playerMark + 1) != id && playerIsCompleted == false) {
            Utils.sendMessage(player, "&cYou must claim the signs in order you are currently at " + playerMark + "/" + huntLimit);
            return;
        }
        //TODO Make it say the hunts name
        if (playerIsCompleted) {
            Utils.sendMessage(player, "&aYou have successfully found Poke " + id + "/" + Main.rootNode.getNode("hunts", huntname, "total").getInt());
            playerConfig.getNode("active-hunts", huntname, "mark").setValue(id);
            playerDataConfig.getPlayerDataLoader().save(playerConfig);

            playerMark = playerConfig.getNode("active-hunts", huntname, "mark").getInt();
        }

        if (playerMark == huntLimit) {
            Utils.sendMessage(player, "&aYou have successfully found all the Pokes");
            playerConfig.getNode("completed-hunts", huntname).setValue(true);
            playerDataConfig.getPlayerDataLoader().save(playerConfig);
        }
            //TODO if player count is past limit cancel saying they completed it
    }


}
