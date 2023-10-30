package com.valoks.totalbypass.events;

import com.valoks.totalbypass.TotalBypass;
import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;

import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.proxy.LoginPhaseConnection;
import org.slf4j.Logger;

import static com.valoks.totalbypass.util.Constants.*;

public class PreLoginListener {

    @Subscribe
    public void preLogin(PreLoginEvent event) {
        LoginPhaseConnection connection = (LoginPhaseConnection) event.getConnection();
        String username = event.getUsername();

        boolean handlerCreated = PlayerHandler.createHandler(connection, username);

        if(!handlerCreated) event.setResult(PreLoginEvent.PreLoginComponentResult.denied(NAME_TAKEN));
    }
}
