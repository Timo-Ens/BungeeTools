package nl.itimo.bungeetools.modules.report.commands;

import com.google.common.collect.ImmutableSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import nl.itimo.bungeetools.modules.report.ReportModule;
import nl.itimo.bungeetools.utils.Messages;

import java.util.HashSet;
import java.util.Set;

public class ReportCommand extends Command implements TabExecutor {
    private final ReportModule reportModule;

    public ReportCommand(ReportModule reportModule, String permission) {
        super("report", permission);
        this.reportModule = reportModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(ChatColor.RED + "You can't execute this command as console.").create());
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        String usage = "/" + this.getName() + " <player> <reason>";

        if (args.length < 2) {
            player.sendMessage(Messages.USAGE.getMessageAsComponent(
                    "[usage]:" + usage
            ));
            return;
        }

        ProxiedPlayer reportedPlayer = this.reportModule.getApi().getProxy().getPlayer(args[0]);
        if(reportedPlayer == null){
            player.sendMessage(Messages.PLAYER_OFFLINE.getMessageAsComponent(
                    "[player]:" + args[0]
            ));
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            stringBuilder.append(args[i]).append(" ");

        player.sendMessage(Messages.REPORT_SUCCESS.getMessageAsComponent(
                "[player]:" + reportedPlayer.getName(),
                "[reason]:" + stringBuilder.toString()
        ));

        for (ProxiedPlayer proxiedPlayer : this.reportModule.getApi().getProxy().getPlayers()){
            if(!this.reportModule.getRecieverPermission().equals("")){
                if(!proxiedPlayer.hasPermission(this.reportModule.getRecieverPermission())) continue;
            }
            proxiedPlayer.sendMessage(Messages.REPORT_FORMAT.getMessageAsComponent(
                    "[reporter]:" + player.getName(),
                    "[player]:" + reportedPlayer.getName(),
                    "[reason]:" + stringBuilder.toString()
            ));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 1) return ImmutableSet.of();
        Set<String> matches = new HashSet<>();
        String search = args[0].toLowerCase();
        ProxyServer.getInstance().getPlayers().stream().filter(proxiedPlayer -> proxiedPlayer.getName().startsWith(search))
                .forEach(proxiedPlayer -> matches.add(proxiedPlayer.getName()));
        return matches;
    }
}
