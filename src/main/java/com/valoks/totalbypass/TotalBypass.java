package com.valoks.totalbypass;

import com.google.inject.Inject;
import com.valoks.totalbypass.events.*;
import com.valoks.totalbypass.util.PermissionsBinding;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "afkcc-total-bypass", name="AFKCC Total Bypass", version ="0.1.0-SNAPSHOT", url="https://valoks.com", description="Bypass IP bans for users connecting from MinecraftAFK Web version", authors = { "Frej Alexander Nielsen" })
public class TotalBypass {

    public static TotalBypass INSTANCE;
    private final ProxyServer server;
    private final Logger logger;
    private PermissionsBinding permissionsBinding;

    @Inject
    public TotalBypass(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        INSTANCE = this;

        server.getEventManager().register(this, new PreLoginListener());
        server.getEventManager().register(this, new PermissionsListener());
        server.getEventManager().register(this, new DisconnectListener());
        server.getEventManager().register(this, new LoginListener());
        server.getEventManager().register(this, new ConnectedListener());

        permissionsBinding = new PermissionsBinding();
        try {
            permissionsBinding.bind();
        } catch(Exception e) {
            logger.warn("No permissions plugin found, using our own permissions provider");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
    public PermissionsBinding getPermissionsBinding() {
        return this.permissionsBinding;
    }
}
