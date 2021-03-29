package nl.itimo.bungeetools.modules.globalmsg.commands;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import nl.itimo.bungeetools.modules.globalmsg.GlobalMsgModule;
import nl.itimo.bungeetools.utils.Messages;

import java.util.HashSet;
import java.util.Set;

public class MessageCommand extends Command implements TabExecutor {

    private final GlobalMsgModule globalMsgModule;

    public MessageCommand(GlobalMsgModule globalMsgModule, String permission) {
        super("message", permission, "msg");
        this.globalMsgModule = globalMsgModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You can't execute this command as console.").create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String usage = "/" + this.getName() + " <player> <message>";

        if (args.length < 2) {
            player.sendMessage(Messages.USAGE.getMessageAsComponent(
                    "[usage]:" + usage
            ));
            return;
        }

        ProxiedPlayer receiver = this.globalMsgModule.getApi().getProxy().getPlayer(args[0]);
        if (receiver == null) {
            player.sendMessage(Messages.PLAYER_OFFLINE.getMessageAsComponent("[player]:" + args[0]));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            stringBuilder.append(args[i]).append(" ");

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

        if (this.globalMsgModule.getPlayerConversations().containsKey(player.getUniqueId())) {
            this.globalMsgModule.getPlayerConversations().replace(player.getUniqueId(), receiver.getUniqueId());
        } else {
            this.globalMsgModule.getPlayerConversations().put(player.getUniqueId(), receiver.getUniqueId());
        }

        if (this.globalMsgModule.getPlayerConversations().containsKey(receiver.getUniqueId())) {
            this.globalMsgModule.getPlayerConversations().replace(receiver.getUniqueId(), player.getUniqueId());
        } else {
            this.globalMsgModule.getPlayerConversations().put(receiver.getUniqueId(), player.getUniqueId());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length > 2 || args.length == 0) return ImmutableSet.of();
        Set<String> matches = new HashSet<>();
        String search = args[0].toLowerCase();
        ProxyServer.getInstance().getPlayers().stream().filter(proxiedPlayer -> proxiedPlayer.getName().startsWith(search))
                .forEach(proxiedPlayer -> matches.add(proxiedPlayer.getName()));
        return matches;
    }
}
