package nl.itimo.bungeetools.utils;

import net.md_5.bungee.api.ChatColor;

public class Colorize {
    public static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
