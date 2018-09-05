package me.pokerman99.BlockHuntEC.listeners;

import me.pokerman99.BlockHuntEC.Main;
import me.pokerman99.BlockHuntEC.Utils;
import me.pokerman99.BlockHuntEC.data.DATA;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class InteractBlockListener {

    @Listener
    public void onRightClick(InteractBlockEvent.Secondary event, @First Player player) {
        if (!(event instanceof InteractBlockEvent.Secondary.MainHand)) return;
        if (!event.getTargetBlock().getLocation().isPresent()) return;
        Location<World> location = event.getTargetBlock().getLocation().get();
        Optional<TileEntity> tileEntity = location.getTileEntity();

        if (!tileEntity.isPresent() || !location.getTileEntity().get().getType().equals(TileEntityTypes.SKULL)) return;

        if (Main.removing.contains(player.getIdentifier())) {
            onBreakEvent(tileEntity.get().get(DATA.class));
        }

    }

    public void onBreakEvent(Optional<DATA> data) {
        if (!data.isPresent()) return;

        data.get();

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
