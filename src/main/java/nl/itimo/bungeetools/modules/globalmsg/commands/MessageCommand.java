package nl.itimo.bungeetools.modules.globalmsg.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.itimo.bungeetools.modules.globalmsg.GlobalMsgModule;

public class MessageCommand extends Command {

    private final GlobalMsgModule globalMsgModule;

    public MessageCommand(GlobalMsgModule globalMsgModule) {
        super("message", "", "msg");
        this.globalMsgModule = globalMsgModule;
    }

    public MessageCommand(GlobalMsgModule globalMsgModule, String permission){
        super("message", permission, "msg");
        this.globalMsgModule = globalMsgModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You can't execute this command as console.").create());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length > 2){

        }

    }
}
