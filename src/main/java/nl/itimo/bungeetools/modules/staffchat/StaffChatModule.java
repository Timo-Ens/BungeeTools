package nl.itimo.bungeetools.modules.staffchat;

import nl.itimo.bungeetools.Bungeetools;
import nl.itimo.bungeetools.modules.AutoloadModule;
import nl.itimo.bungeetools.modules.Module;
import nl.itimo.bungeetools.modules.ModuleAlreadyInitializedException;
import nl.itimo.bungeetools.modules.staffchat.commands.StaffChatCommand;
import nl.itimo.bungeetools.modules.staffchat.listeners.ChatListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AutoloadModule
public class StaffChatModule extends Module<Bungeetools> {

    private final List<UUID> staffchatUsers = new ArrayList<>();
    private final List<UUID> staffchatUsersMuted = new ArrayList<>();
    private String permission;

    public StaffChatModule(Bungeetools api) throws ModuleAlreadyInitializedException {
        super(api, "StaffChat");
    }

    public String getPermission() {
        return permission;
    }

    public List<UUID> getStaffchatUsersMuted() {
        return staffchatUsersMuted;
    }

    public List<UUID> getStaffchatUsers() {
        return staffchatUsers;
    }

    @Override
    public void onEnable() {
        this.permission = getApi().configuration.getString("modules.staffchat.permission");
        getApi().registerCommands(
                new StaffChatCommand(this, this.permission)
        );

        getApi().registerListener(
                new ChatListener(this)
        );
    }

    @Override
    public void onDisable() {
        getApi().unregisterCommands(
                new StaffChatCommand(this, "")
        );

        getApi().unregisterListener(
                new ChatListener(this)
        );
    }

}
