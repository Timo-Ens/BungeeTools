package nl.itimo.bungeetools.modules.staffchat.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nl.itimo.bungeetools.modules.staffchat.StaffChatModule;
import nl.itimo.bungeetools.utils.Messages;

public class ChatListener implements Listener {
    private final StaffChatModule staffChatModule;

    public ChatListener(StaffChatModule staffChatModule){
        this.staffChatModule = staffChatModule;
    }

    @EventHandler
    public void onChat(ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if(this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())){
            event.setCancelled(true);
            staffChatModule.getApi().getProxy().getPlayers().stream()
                    .filter(proxiedPlayer -> proxiedPlayer.hasPermission(this.staffChatModule.getPermission()))
                    .filter(proxiedPlayer -> !this.staffChatModule.getStaffchatUsersMuted().contains(proxiedPlayer.getUniqueId()))
                    .forEach(proxiedPlayer -> {
                        Messages.MSG_FORMAT.getMessageAsComponent(
                                "[server]:" + player.getServer().getInfo().getName(),
                                "[sender]:" + player.getName(),
                                "[messages]:" + event.getMessage()
                        );
                    });
        }
    }
}
