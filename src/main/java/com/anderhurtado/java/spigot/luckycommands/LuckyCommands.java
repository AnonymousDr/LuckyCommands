package com.anderhurtado.java.spigot.luckycommands;

import com.anderhurtado.java.spigot.luckycommands.commands.ExecuteRandomly;
import com.anderhurtado.java.spigot.luckycommands.commands.ExecuteRandomlyJSON;
import com.anderhurtado.java.spigot.luckycommands.data.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class LuckyCommands extends JavaPlugin {

    public static final String WIKI_LINK = "https://github.com/AnonymousDr/LuckyCommands/wiki";

    @Override
    public void onEnable() {
        getCommand("executerandomly").setExecutor(new ExecuteRandomly());
        getCommand("executerandomlyjson").setExecutor(new ExecuteRandomlyJSON());
        saveDefaultConfig();
        Config.loadConfig(getConfig());
    }
}
