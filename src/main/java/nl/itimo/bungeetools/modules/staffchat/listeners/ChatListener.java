package nl.itimo.bungeetools.modules.staffchat.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.itimo.bungeetools.modules.staffchat.StaffChatModule;
import nl.itimo.bungeetools.utils.Messages;

import java.util.stream.StreamSupport;

public class ChatListener implements Listener {
    private final StaffChatModule staffChatModule;

    public ChatListener(StaffChatModule staffChatModule) {
        this.staffChatModule = staffChatModule;
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) {
            if(event.getMessage().startsWith("/")) return;
            event.setCancelled(true);
            for (ProxiedPlayer proxiedPlayer : this.staffChatModule.getApi().getProxy().getPlayers()){
                if(!this.staffChatModule.getPermission().equals("")){
                    if(!proxiedPlayer.hasPermission(this.staffChatModule.getPermission())) continue;
                }
                if(this.staffChatModule.getStaffchatUsersMuted().contains(proxiedPlayer.getUniqueId())) continue;
                proxiedPlayer.sendMessage(Messages.STAFFCHAT_FORMAT.getMessageAsComponent(
                        "[server]:" + player.getServer().getInfo().getName(),
                        "[sender]:" + player.getName(),
                        "[message]:" + event.getMessage()
                ));
            }
        }
    }
}
