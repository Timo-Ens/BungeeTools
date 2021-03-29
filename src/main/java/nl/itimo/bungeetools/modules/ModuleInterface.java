package nl.itimo.bungeetools.modules;

import nl.itimo.bungeetools.Bungeetools;

public interface ModuleInterface <T extends Bungeetools> {
    T getApi();

    default void onEnable() {}
    default void onDisable() {}

    void enable();
    void disable();
}
