package com.valoks.totalbypass.events;

import com.valoks.totalbypass.TotalBypass;
import com.valoks.totalbypass.api.ApiResult;
import com.valoks.totalbypass.util.LoginPermissionsProvider;
import com.valoks.totalbypass.util.PlayerHandler;
import com.velocitypowered.api.event.Continuation;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

public class PermissionsListener {

    @Subscribe
    public void onPermissionsSetup(PermissionsSetupEvent event, Continuation continuation) {
        if(!(event.getSubject() instanceof Player player)) {
            continuation.resume();
            return;
        }

        PlayerHandler handler = PlayerHandler.getHandler(player);

        if(handler == null) {
            continuation.resume();
            return;
        }

        handler.isVerified().thenAccept(result -> {
            if(result == ApiResult.VERIFIED) {
                if(!handler.update(player)) event.setProvider(LoginPermissionsProvider.INSTANCE);
            }

            continuation.resume();
        });
    }

}
