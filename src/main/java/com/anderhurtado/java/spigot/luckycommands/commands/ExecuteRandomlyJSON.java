package com.anderhurtado.java.spigot.luckycommands.commands;

import com.anderhurtado.java.spigot.luckycommands.data.Config;
import com.anderhurtado.java.spigot.luckycommands.data.ExecutableConditionalCommands;
import com.anderhurtado.java.spigot.luckycommands.LuckyCommands;
import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExecuteRandomlyJSON implements CommandExecutor {

    private static final Gson JSON_PARSER = new Gson();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("luckycommands.command.executerandomlyjson")) {
            sender.sendMessage(ChatColor.RED+"You don't have enough permissions to run this command!");
            return false;
        }
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED+"Visit the wiki to learn how to use this command: "+ LuckyCommands.WIKI_LINK);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<args.length; i++) {
            if(i > 0) sb.append(' ');
            sb.append(args[i]);
        }
        ExecutableConditionalCommands conditionalCommands;
        try {
            conditionalCommands = JSON_PARSER.fromJson(sb.toString(), ExecutableConditionalCommands.class);
        } catch (Exception Ex) {
            sender.sendMessage(ChatColor.RED+"An error occurred while parsing JSON! Check the wiki here: "+ LuckyCommands.WIKI_LINK);
            return true;
        }
        boolean success = conditionalCommands.executeCommands();
        if(Config.PRINT_OUTPUT) {
            if(success) sender.sendMessage(ChatColor.YELLOW+"Command executed. "+ChatColor.GREEN+"Luck went successful.");
            else sender.sendMessage(ChatColor.YELLOW+"Command executed. "+ChatColor.RED+"Luck went unsuccessful.");
        }
        return true;
    }
}
