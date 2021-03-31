package nl.itimo.bungeetools.modules.report.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import nl.itimo.bungeetools.modules.globalmsg.GlobalMsgModule;
import nl.itimo.bungeetools.modules.report.ReportModule;

public class ReportCommand extends Command {
    private final ReportModule reportModule;

    public ReportCommand(ReportModule reportModule, String permission, String recieverPermission) {
        super("report", permission);
        this.reportModule = reportModule;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
