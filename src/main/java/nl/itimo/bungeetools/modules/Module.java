package nl.itimo.bungeetools.modules;

import nl.itimo.bungeetools.Bungeetools;

public abstract class Module<T extends Bungeetools> implements ModuleInterface<T> {

    private final String uid = this.getClass().getName();
    private final T api;
    private final String name;
    private boolean isEnabled;

    public Module(T api, String name) throws ModuleAlreadyInitializedException {
        if (api.modules.stream().anyMatch(module -> {
            return module.getClass().getName().equalsIgnoreCase(this.uid);
        })) {
            throw new ModuleAlreadyInitializedException("The module " + name + " is already initialized.");
        } else{
            this.api = api;
            this.name = name;
            this.isEnabled = true;
        }
    }

    public String getUid() {
        return uid;
    }

    @Override
    public T getApi() {
        return api;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void enable() {
        this.onEnable();
        getApi().getLogger().info("Enabled module " + this.getName());
    }

    public void disable() {
        this.onDisable();
        getApi().getLogger().info("Disabled module " + this.getName());
    }
}
