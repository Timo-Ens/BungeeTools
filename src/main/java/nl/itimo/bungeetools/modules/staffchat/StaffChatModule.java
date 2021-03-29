package nl.itimo.bungeetools.modules.staffchat;

import nl.itimo.bungeetools.Bungeetools;
import nl.itimo.bungeetools.modules.AutoloadModule;
import nl.itimo.bungeetools.modules.Module;
import nl.itimo.bungeetools.modules.ModuleAlreadyInitializedException;

import java.util.*;

@AutoloadModule
public class StaffChatModule extends Module<Bungeetools> {

    private final List<UUID> staffchatUsers = new ArrayList<>();

    public StaffChatModule(Bungeetools api) throws ModuleAlreadyInitializedException {
        super(api, "StaffChat");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
