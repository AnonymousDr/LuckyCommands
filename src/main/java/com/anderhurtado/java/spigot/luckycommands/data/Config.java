package com.anderhurtado.java.spigot.luckycommands.data;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static boolean PRINT_OUTPUT;

    public static void loadConfig(FileConfiguration config) {
        PRINT_OUTPUT = config.getBoolean("printOutput", true);
    }

    private Config() {}
}
