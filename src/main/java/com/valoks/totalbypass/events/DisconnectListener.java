package com.valoks.totalbypass.events;

import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;

public class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        PlayerHandler.removeHandler(event.getPlayer());
    }

}
