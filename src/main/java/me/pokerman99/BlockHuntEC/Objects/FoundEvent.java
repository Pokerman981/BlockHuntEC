package me.pokerman99.BlockHuntEC.Objects;

import me.pokerman99.BlockHuntEC.data.DATA;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

public class FoundEvent extends AbstractEvent implements Event, TargetPlayerEvent, Cancellable{

    private final Player player;
    private final DATA data;
    private final Cause cause;
    private boolean cancelled = false;

    public FoundEvent(Player player, DATA data, Cause cause) {
        this.player = player;
        this.data = data;
        this.cause = cause;
    }

    @Override
    public boolean isCancelled(){
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel){
        this.cancelled = cancel;
    }

    @Override
    public Cause getCause(){
        return this.cause;
    }

    @Override
    public Player getTargetEntity(){
        return this.player;
    }

    public DATA getData(){
        return data;
    }

}
