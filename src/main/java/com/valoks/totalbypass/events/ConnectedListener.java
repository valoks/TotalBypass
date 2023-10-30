package com.valoks.totalbypass.events;

import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

public class ConnectedListener {

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        PlayerHandler.removeHandler(event.getPlayer());
    }
}
