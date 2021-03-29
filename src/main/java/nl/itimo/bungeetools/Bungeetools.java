package nl.itimo.bungeetools;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import nl.itimo.bungeetools.modules.Module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class Bungeetools extends Plugin {

    public Collection<Module<?>> modules = new ArrayList<>();
    public Configuration configuration;
    public Configuration messages;

    @Override
    public void onEnable() {
        setupConfig();
        loadModules();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getProxy().getPluginManager().registerListener(this, listener));
    }

    public void unregisterListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getProxy().getPluginManager().unregisterListener(listener));
    }

    public void registerCommands(Command... commands) {
        Arrays.stream(commands).forEach(command -> getProxy().getPluginManager().registerCommand(this, command));
    }

    public void unregisterCommands(Command... commands) {
        Arrays.stream(commands).forEach(command -> getProxy().getPluginManager().unregisterCommand(command));
    }

    private void setupConfig() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");
        try {
            if (!configFile.exists()) {
                InputStream inputStream = getResourceAsStream("config.yml");
                Files.copy(inputStream, configFile.toPath());
            }
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File messagesFile = new File(getDataFolder(), "messages.yml");
        try {
            if (!messagesFile.exists()) {
                InputStream inputStream = getResourceAsStream("messages.yml");
                Files.copy(inputStream, messagesFile.toPath());
            }
            this.messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadModules() {
        String pkg = "nl.itimo.bungeetools.modules";
        String routeAnnotation = pkg + ".AutoloadModule";
        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()
                             .acceptPackages(pkg)
                             .scan()) {
            System.out.println(scanResult.getAllClasses().size());
            for (ClassInfo routeClassInfo : scanResult.getClassesWithAnnotation(routeAnnotation)) {
                Object module;
                try {
                    module = routeClassInfo.loadClass().getDeclaredConstructor(this.getClass()).newInstance(this);
                } catch (Exception e) {
                    this.getLogger().severe("Something went wrong while registering module  class" + routeClassInfo.getName());
                    e.printStackTrace();
                    return;
                }
                Module module1 = (Module<?>) module;
                module1.enable();
                modules.add(module1);
            }
        }
    }
}
