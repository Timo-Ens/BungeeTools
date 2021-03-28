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

    private HashMap<UUID, UUID> playerConversations = new HashMap<>();

    public GlobalMsgModule(Bungeetools api) throws ModuleAlreadyInitializedException {
        super(api, "Global Msg");
    }

    public void onEnable() {
        getApi().registerCommands(
                new MessageCommand(this),
                new ReplyCommand(this)
        );

    }

    public void onDisable() {
        getApi().unregisterCommands(
                new MessageCommand(this),
                new ReplyCommand(this)
        );
    }

    public HashMap<UUID, UUID> getPlayerConversations() {
        return playerConversations;
    }
}
