package nl.itimo.bungeetools.modules.staffchat.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import nl.itimo.bungeetools.modules.staffchat.StaffChatModule;

public class StaffChatCommand extends Command implements TabExecutor {

    private final StaffChatModule staffChatModule;
    private final String[] options = new String[]{"toggle", "enable", "disable", "togglemute","mute", "unmute"};


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
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("toggle")){
                if (this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) {
                    this.staffChatModule.getStaffchatUsers().remove(player.getUniqueId());
                } else {
                    this.staffChatModule.getStaffchatUsers().add(player.getUniqueId());
                }
            }

            if(args[0].equalsIgnoreCase("enable")){
                if(this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) return;
                this.staffChatModule.getStaffchatUsers().add(player.getUniqueId());
            }

            if(args[0].equalsIgnoreCase("disable")){
                if(!this.staffChatModule.getStaffchatUsers().contains(player.getUniqueId())) return;
                this.staffChatModule.getStaffchatUsers().remove(player.getUniqueId());
            }

            if(args[0].equalsIgnoreCase("togglemute")){
                if (this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) {
                    this.staffChatModule.getStaffchatUsersMuted().remove(player.getUniqueId());
                } else {
                    this.staffChatModule.getStaffchatUsersMuted().add(player.getUniqueId());
                }
            }

            if(args[0].equalsIgnoreCase("mute")){
                if(this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) return;
                this.staffChatModule.getStaffchatUsersMuted().add(player.getUniqueId());
            }

            if(args[0].equalsIgnoreCase("unmute")){
                if(!this.staffChatModule.getStaffchatUsersMuted().contains(player.getUniqueId())) return;
                this.staffChatModule.getStaffchatUsersMuted().remove(player.getUniqueId());
            }
        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}