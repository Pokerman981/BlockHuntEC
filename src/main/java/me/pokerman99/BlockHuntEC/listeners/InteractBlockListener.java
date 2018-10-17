package me.pokerman99.BlockHuntEC.listeners;

import me.pokerman99.BlockHuntEC.Main;
import me.pokerman99.BlockHuntEC.Objects.FoundEvent;
import me.pokerman99.BlockHuntEC.Utils;
import me.pokerman99.BlockHuntEC.data.DATA;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

import static me.pokerman99.BlockHuntEC.Main.rootNode;

public class InteractBlockListener {

    @Listener
    public void onRightClick(InteractBlockEvent.Secondary.MainHand event, @First Player player) {
        if (!event.getTargetBlock().getLocation().isPresent()) return;
        Location<World> location = event.getTargetBlock().getLocation().get();
        Optional<TileEntity> tileEntity = location.getTileEntity();


        if (!tileEntity.isPresent() || !tileEntity.get().getType().equals(TileEntityTypes.SIGN)) return;

        if (Main.removing.contains(player.getIdentifier())) {
            {
                event.setCancelled(true);
                onBreakEvent(location);
                return;
            }
        } else if (Main.adding.containsKey(player.getUniqueId())) {
            {
                onAddingEvent(Main.adding.get(player.getUniqueId()), location, player);
                return;
            }
        } else if (location.get(DATA.class).isPresent()) {
            DATA data = location.get(DATA.class).get();
            {
                //if (!data.getBlockhuntdata().get(0).equals("blockhuntec")) return;

                Cause cause = Cause.builder().append(player).build(EventContext.builder().add(EventContextKeys.PLAYER_SIMULATED, player.getProfile()).build());
                Sponge.getEventManager().post(new FoundEvent(player, data, cause));
            }
        }

    }

    void onBreakEvent(Location<World> location) {
        //TODO
        MessageChannel.TO_ALL.send(Text.of("TODO #1"));
    }

    void onAddingEvent(DATA data, Location<World> locationObj, Player player){
        String name = data.getBlockhuntdata().get(2);
        String id = data.getBlockhuntdata().get(1);
        String uuid = data.getBlockhuntdata().get(3);
        String location = locationObj.getBlockPosition() + " " + Utils.getDim(player.getWorldUniqueId().get());

        if (locationObj.getTileEntity().get().offer(new DATA(data.getBlockhuntdata())).isSuccessful() == false) {
            Utils.sendMessage(player, "&cThe data could not be saved to the block! Try again.");
            return;
        }



        rootNode.getNode("hunts", name, "locations", id).setValue(location + " - " + uuid);
        rootNode.getNode("hunts", name, "total").setValue(id);
        Main.getInstance().save();

        Utils.sendMessage(player, "&aSuccessfully added location to " + name + " at " + location);

        Main.adding.remove(player.getUniqueId());


    }

    @Listener
    public void onLeftClick(InteractBlockEvent.Primary event, @First Player player) {
        if (!event.getTargetBlock().getLocation().isPresent()) return;

        Location<World> location = event.getTargetBlock().getLocation().get();
        Optional<TileEntity> tileEntity = location.getTileEntity();


        {
            if (!tileEntity.isPresent()) return;
            if (!location.getTileEntity().get().getType().equals(TileEntityTypes.SKULL)) return;

            Optional<DATA> data = tileEntity.get().get(DATA.class);
            if (!data.isPresent()) return;
            if (!data.get().getBlockhuntdata().get(1).equals("blockhuntec")) return;

        }

        {
            event.setCancelled(true);
            Utils.sendMessage(player, "&cYou cannot break this block!");
        }

    }
}
