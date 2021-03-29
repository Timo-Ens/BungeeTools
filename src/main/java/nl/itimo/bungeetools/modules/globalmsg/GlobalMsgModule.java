package nl.itimo.bungeetools.modules.globalmsg;

import nl.itimo.bungeetools.Bungeetools;
import nl.itimo.bungeetools.modules.AutoloadModule;
import nl.itimo.bungeetools.modules.Module;
import nl.itimo.bungeetools.modules.ModuleAlreadyInitializedException;
import nl.itimo.bungeetools.modules.globalmsg.commands.MessageCommand;
import nl.itimo.bungeetools.modules.globalmsg.commands.ReplyCommand;

import java.util.HashMap;
import java.util.UUID;

@AutoloadModule
public class GlobalMsgModule extends Module<Bungeetools> {

    private final HashMap<UUID, UUID> playerConversations = new HashMap<>();
    private String permission;

    public GlobalMsgModule(Bungeetools api) throws ModuleAlreadyInitializedException {
        super(api, "GlobalMsg");
    }

    public String getPermission() {
        return permission;
    }

    public void onEnable() {
        this.permission = getApi().configuration.getString("modules.globalmsg.permission");
        getApi().registerCommands(
                new MessageCommand(this, this.permission),
                new ReplyCommand(this, this.permission)
        );
    }

    public void onDisable() {
        getApi().unregisterCommands(
                new MessageCommand(this, ""),
                new ReplyCommand(this, "")
        );
    }

    public HashMap<UUID, UUID> getPlayerConversations() {
        return playerConversations;
    }
}
