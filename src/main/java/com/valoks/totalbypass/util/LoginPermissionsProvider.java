package com.valoks.totalbypass.util;

import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;

public class LoginPermissionsProvider implements PermissionProvider {

    public static final LoginPermissionsProvider INSTANCE = new LoginPermissionsProvider();

    @Override
    public PermissionFunction createFunction(PermissionSubject permissionSubject) {
        if(!(permissionSubject instanceof Player player)) return null;

        return new LoginPermissionsFunction(player);
    }
}

class LoginPermissionsFunction implements PermissionFunction {

    private final Player player;
    private boolean connected = false;

    public LoginPermissionsFunction(Player player) {
        this.player = player;
    }

    @Override
    public Tristate getPermissionValue(String s) {
        if(s.equalsIgnoreCase("antivpn.bypass")) {
            if(!connected) {
                if(player.getCurrentServer().isPresent()) {
                    connected = true;
                    return Tristate.UNDEFINED;
                }

                return Tristate.TRUE;
            } else return Tristate.UNDEFINED;
        }

        return Tristate.UNDEFINED;
    }
}