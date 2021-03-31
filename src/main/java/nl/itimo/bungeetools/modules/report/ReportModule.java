package nl.itimo.bungeetools.modules.report;

import nl.itimo.bungeetools.Bungeetools;
import nl.itimo.bungeetools.modules.AutoloadModule;
import nl.itimo.bungeetools.modules.Module;
import nl.itimo.bungeetools.modules.ModuleAlreadyInitializedException;
import nl.itimo.bungeetools.modules.report.commands.ReportCommand;

@AutoloadModule
public class ReportModule extends Module<Bungeetools> {

    private String permission;
    private String recieverPermission;

    public ReportModule(Bungeetools api) throws ModuleAlreadyInitializedException {
        super(api, "Report");
    }

    public String getPermission() {
        return permission;
    }

    public String getRecieverPermission() {
        return recieverPermission;
    }

    @Override
    public void onEnable() {
        this.permission = getApi().configuration.getString("modules.report.permission");
        this.recieverPermission = getApi().configuration.getString("modules.report.permission_recieve");

        getApi().registerCommands(
                new ReportCommand(this, permission, recieverPermission)
        );
    }

    @Override
    public void onDisable() {

    }
}
