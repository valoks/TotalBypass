package com.valoks.totalbypass.events;

import com.valoks.totalbypass.TotalBypass;
import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

import static com.valoks.totalbypass.util.Constants.KICK_MESSAGE;

public class LoginListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onLogin(LoginEvent event) {
        Logger logger = TotalBypass.INSTANCE.getLogger();
        Player player = event.getPlayer();

        PlayerHandler handler = PlayerHandler.getHandler(player);

        if(handler == null) return;

        if(!handler.confirmHost(player.getRemoteAddress().getAddress().getHostAddress())) {
            logger.info("Player " + player.getUsername() + " tried to join from a different IP address than the one they verified with.");
            event.setResult(ResultedEvent.ComponentResult.denied(KICK_MESSAGE));
            player.disconnect(KICK_MESSAGE);
        }
    }
}
