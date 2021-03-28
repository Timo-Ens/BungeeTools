package nl.itimo.bungeetools.utils;

import nl.itimo.bungeetools.Bungeetools;

public class Messages {

    private final Bungeetools bungeetools;
    public Messages(Bungeetools bungeetools){
        this.bungeetools = bungeetools;
    }

    public String getMessage(MessagePaths messagePaths){
        return this.bungeetools.messages.getString(messagePaths.getPath());
    }

    public enum MessagePaths {
        USAGE("messages.usage");

        private final String path;

        MessagePaths(String path) {
            this.path = path;
        }

        public String getPath(){
            return this.path;
        }
    }

}
