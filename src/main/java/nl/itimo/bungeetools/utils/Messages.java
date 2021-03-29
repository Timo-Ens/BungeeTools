package nl.itimo.bungeetools.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import nl.itimo.bungeetools.Bungeetools;

public enum Messages {

    // General
    USAGE("messages.usage"),
    PLAYER_OFFLINE("messages.player_offline"),

    //Global Message
    NO_CONVERSATION("messages.message.no_conversation"),
    MSG_FORMAT("messages.message.msg_format"),

    //Staffchat
    STAFFCHAT_FORMAT("messages.staffchat.staffchat_format");

    private final Bungeetools bungeetools = (Bungeetools) ProxyServer.getInstance().getPluginManager().getPlugin("Bungeetools");
    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public String getMessage() {
        return Colorize.colorize(this.bungeetools.messages.getString(path));
    }

    public TextComponent getMessageAsComponent() {
        return new TextComponent(getMessage());
    }


    public TextComponent getMessageAsComponent(String... strings) {
        String message = getMessage();
        for (String s : strings) {
            String[] split = s.split(":");
            String key = split[0];
            String value = split[1];
            message = message.replace(key, value);
        }
        return new TextComponent(message);
    }

}
