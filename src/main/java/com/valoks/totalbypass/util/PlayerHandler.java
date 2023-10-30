package com.valoks.totalbypass.util;

import com.valoks.totalbypass.TotalBypass;
import com.valoks.totalbypass.api.ApiResult;
import com.valoks.totalbypass.api.MinecraftAFK;
import com.velocitypowered.api.proxy.LoginPhaseConnection;
import com.velocitypowered.api.proxy.Player;
import org.slf4j.Logger;

import java.security.Permission;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.valoks.totalbypass.util.Constants.CHANNEL_IDENTIFIER;

public class PlayerHandler {

    private static final Map<String, PlayerHandler> handlers = new HashMap<>();

    private final LoginPhaseConnection player;
    private Player realPlayer;
    private final String username;
    private final CompletableFuture<ApiResult> verified;
    private final String connectedFrom;
    private boolean removePermission = false;

    public PlayerHandler(LoginPhaseConnection player, String username) {
        this.player = player;
        this.username = username;
        this.connectedFrom = player.getRemoteAddress().getAddress().getHostAddress();
        verified = new CompletableFuture<>();
        verify();

        Logger logger = TotalBypass.INSTANCE.getLogger();
        logger.info("Created handler for player " + username);
        handlers.put(username, this);
    }

    public static boolean createHandler(LoginPhaseConnection connection, String username) {
        if(handlers.containsKey(username)) return false;

        new PlayerHandler(connection, username);

        return true;
    }

    public static void removeHandler(Player player) {
        Logger logger = TotalBypass.INSTANCE.getLogger();
        PlayerHandler handler = handlers.get(player.getUsername());

        if(handler == null) return;

        handler.clear();

        handlers.remove(player.getUsername());

        logger.info("Removed player " + player.getUsername() + " from handlers");
    }

    public static PlayerHandler getHandler(Player player) {
        return handlers.get(player.getUsername());
    }

    public boolean confirmHost(String connectedFrom) {
        return this.connectedFrom.equals(connectedFrom);
    }

    public CompletableFuture<ApiResult> isVerified() {
        return verified;
    }

    public boolean update(Player player) {
        this.realPlayer = player;
        removePermission = !player.hasPermission("antivpn.bypass");

        boolean isBound = true;
        if(removePermission) isBound = TotalBypass.INSTANCE.getPermissionsBinding().addPermission(player, "antivpn.bypass");

        return isBound;
    }

    public void clear() {
        if(removePermission) TotalBypass.INSTANCE.getPermissionsBinding().removePermission(realPlayer, "antivpn.bypass");
    }

    private void verify() {
        player.sendLoginPluginMessage(CHANNEL_IDENTIFIER, new byte[0], response -> {
            if(response == null) {
                verified.complete(ApiResult.UNVERIFIED);
                return;
            }

            String token = new String(response);

            MinecraftAFK.verify(token, username).thenAccept(verified::complete);
        });
    }
}
