package com.magitechserver.listeners;

import com.magitechserver.DiscordHandler;
import com.magitechserver.MagiBridge;
import com.magitechserver.util.Config;
import com.magitechserver.util.Webhooking;
import io.github.nucleuspowered.nucleus.modules.staffchat.StaffChatMessageChannel;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;

import java.util.Optional;

/**
 * Created by Frani on 09/07/2017.
 */
public class SpongeChatListener {

    @Listener
    public void onSpongeMessage(MessageChannelEvent.Chat e, @Root Player p) {
        if(e.getChannel().isPresent()) {
            String message = e.getMessage().toPlain();
            String discordChannel = MagiBridge.getConfig().getString("channel", "nucleus", "global-discord-channel");
            if(e.getChannel().get() instanceof StaffChatMessageChannel) {
                message = "**" + p.getName() + "**: " + message;
                discordChannel = MagiBridge.getConfig().getString("channel", "nucleus", "staff-discord-channel");
            }
            if(Config.useWebhooks()) {
                Webhooking.sendWebhookMessage(p.getName(), e.getMessage().toPlain(), discordChannel);
                return;
            }
            DiscordHandler.sendMessageToChannel(discordChannel, message);
        }
    }

}
