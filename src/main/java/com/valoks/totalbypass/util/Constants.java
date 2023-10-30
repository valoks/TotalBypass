package com.valoks.totalbypass.util;

import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import net.kyori.adventure.text.Component;

public class Constants {

    public static final ChannelIdentifier CHANNEL_IDENTIFIER = MinecraftChannelIdentifier.create("minecraftafk", "token");
    public static final Component KICK_MESSAGE = Component.text("Not recognized by MinecraftAFK");
    public static final Component NAME_TAKEN = Component.translatable("multiplayer.disconnect.name_taken");
    public static final String API_URL = "https://minecraftafk.com/api/verify";
}
