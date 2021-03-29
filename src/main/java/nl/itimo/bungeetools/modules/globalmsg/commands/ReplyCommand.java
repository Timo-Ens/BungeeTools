package nl.itimo.bungeetools.modules.globalmsg.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nl.itimo.bungeetools.modules.globalmsg.GlobalMsgModule;
import nl.itimo.bungeetools.utils.Messages;

public class ReplyCommand extends Command {

    private final GlobalMsgModule globalMsgModule;

    public ReplyCommand(GlobalMsgModule globalMsgModule, String permission) {
        super("reply", permission, "r");
        this.globalMsgModule = globalMsgModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You can't execute this command as console.").create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String usage = "/" + this.getName() + " <message>";

        if (args.length < 1) {
            player.sendMessage(Messages.USAGE.getMessageAsComponent(
                    "[usage]:" + usage
            ));
            return;
        }

        if (!this.globalMsgModule.getPlayerConversations().containsKey(player.getUniqueId())) {
            player.sendMessage(Messages.NO_CONVERSATION.getMessageAsComponent());
            return;
        }

        ProxiedPlayer receiver = this.globalMsgModule.getApi().getProxy()
                .getPlayer(this.globalMsgModule.getPlayerConversations().get(player.getUniqueId()));
        if (receiver == null) {
            player.sendMessage(Messages.PLAYER_OFFLINE.getMessageAsComponent("[player]:" + args[0]));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) stringBuilder.append(arg).append(" ");

        player.sendMessage(Messages.MSG_FORMAT.getMessageAsComponent(
                "[sender]:" + player.getName(),
                "[reciever]:" + receiver.getName(),
                "[message]:" + stringBuilder.toString()
        ));

        receiver.sendMessage(Messages.MSG_FORMAT.getMessageAsComponent(
                "[sender]:" + player.getName(),
                "[reciever]:" + receiver.getName(),
                "[message]:" + stringBuilder.toString()
        ));
    }


}
