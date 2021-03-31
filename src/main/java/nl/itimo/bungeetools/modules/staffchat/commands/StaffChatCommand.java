package nl.itimo.bungeetools.modules.staffchat.commands;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import nl.itimo.bungeetools.modules.staffchat.StaffChatModule;
import nl.itimo.bungeetools.utils.Messages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StaffChatCommand extends Command implements TabExecutor {

    private final StaffChatModule staffChatModule;

    public StaffChatCommand(StaffChatModule staffChatModule, String permission) {
        super("staffchat", permission, "sc");
        this.staffChatModule = staffChatModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You can't execute this command as console.").create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String usage = "/" + this.getName() + " toggle, togglemute";

        if(args.length == 0){
            player.sendMessage(Messages.USAGE.getMessageAsComponent(
                    "[usage]:" + usage
            ));
            return;
        }

        if(args[0].equalsIgnoreCase("toggle")){
            if (this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) {
                this.staffChatModule.getStaffchatUsers().remove(player.getUniqueId());
                player.sendMessage(Messages.STAFFCHAT_DISABLED.getMessageAsComponent());
            } else {
                this.staffChatModule.getStaffchatUsers().add(player.getUniqueId());
                player.sendMessage(Messages.STAFFCHAT_ENABLED.getMessageAsComponent());
            }
        }

        if(args[0].equalsIgnoreCase("enable")){
            if(this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) return;
            this.staffChatModule.getStaffchatUsers().add(player.getUniqueId());
            player.sendMessage(Messages.STAFFCHAT_ENABLED.getMessageAsComponent());
        }

        if(args[0].equalsIgnoreCase("disable")){
            if(!this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) return;
            this.staffChatModule.getStaffchatUsers().remove(player.getUniqueId());
            player.sendMessage(Messages.STAFFCHAT_DISABLED.getMessageAsComponent());
        }

        if(args[0].equalsIgnoreCase("togglemute")){
            if (this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) {
                this.staffChatModule.getStaffchatUsersMuted().remove(player.getUniqueId());
                player.sendMessage(Messages.STAFFCHAT_MUTED.getMessageAsComponent());
            } else {
                this.staffChatModule.getStaffchatUsersMuted().add(player.getUniqueId());
                player.sendMessage(Messages.STAFFCHAT_UNMUTED.getMessageAsComponent());
            }
        }

        if(args[0].equalsIgnoreCase("mute")){
            if(this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) return;
            this.staffChatModule.getStaffchatUsersMuted().add(player.getUniqueId());
            player.sendMessage(Messages.STAFFCHAT_MUTED.getMessageAsComponent());
        }

        if(args[0].equalsIgnoreCase("unmute")){
            if(!this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) return;
            this.staffChatModule.getStaffchatUsersMuted().remove(player.getUniqueId());
            player.sendMessage(Messages.STAFFCHAT_UNMUTED.getMessageAsComponent());
        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 1) return ImmutableSet.of();
        Set<String> matches = new HashSet<>();
        String search = args[0].toLowerCase();
        String[] strings = new String[]{"toggle", "enable", "disable", "togglemute", "mute", "unmute"};
        Arrays.stream(strings).filter(s -> s.startsWith(search)).forEach(matches::add);
        return matches;
    }
}
