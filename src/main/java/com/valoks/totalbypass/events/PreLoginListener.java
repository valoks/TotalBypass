package com.valoks.totalbypass.events;

import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.proxy.LoginPhaseConnection;

import static com.valoks.totalbypass.util.Constants.NAME_TAKEN;

public class PreLoginListener {

    @Subscribe
    public void preLogin(PreLoginEvent event) {
        LoginPhaseConnection connection = (LoginPhaseConnection) event.getConnection();
        String username = event.getUsername();

        boolean handlerCreated = PlayerHandler.createHandler(connection, username);

        if(!handlerCreated) event.setResult(PreLoginEvent.PreLoginComponentResult.denied(NAME_TAKEN));
    }
}
