package com.anderhurtado.java.spigot.luckycommands.commands;

import com.anderhurtado.java.spigot.luckycommands.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExecuteRandomly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("luckycommands.command.executerandomly")) {
            sender.sendMessage(ChatColor.RED+"You don't have enough permissions to run this command!");
            return false;
        }
        if(args.length < 3) {
            sender.sendMessage(ChatColor.RED+"Usage: /"+label+" <Player who will run the command | @ (Console)> <Probability out of 1> <Command to run> [@ELSE <Command if probability condition is false>]");
            sender.sendMessage(ChatColor.YELLOW+"Sample: /"+label+" @ 0.15 money give PLAYER 1 @ELSE money take PLAYER 1");
            return true;
        }
        CommandSender executor;
        if(args[0].equals("@")) executor = Bukkit.getConsoleSender();
        else {
            executor = Bukkit.getPlayerExact(args[0]);
            if(executor == null) {
                sender.sendMessage(ChatColor.RED+"The player "+args[0]+" isn't online!");
                return true;
            }
        }
        double probability;
        try {
            probability = Double.parseDouble(args[1]);
        } catch (Exception Ex) {
            sender.sendMessage(ChatColor.RED+args[1]+" is not a valid number!");
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=2; i<args.length; i++) {
            if(i > 2) sb.append(' ');
            sb.append(args[i]);
        }
        String mainCommand = sb.toString(), elseCommand;
        if(mainCommand.contains(" @ELSE ")) {
            String[] split = mainCommand.split(" @ELSE ", 2);
            if(split.length != 2) {
                sender.sendMessage(ChatColor.RED+"Usage: /"+label+" <Player who will run the command | @ (Console)> <Probability out of 1> <Command to run> [@ELSE <Command if probability condition is false>]");
                sender.sendMessage(ChatColor.YELLOW+"Sample: /"+label+" @ 0.15 money give PLAYER 1 @ELSE money take PLAYER 1");
                return true;
            }
            mainCommand = split[0];
            elseCommand = split[1];
        } else elseCommand = null;
        boolean success = probability > Math.random();
        if(success) Bukkit.dispatchCommand(executor, mainCommand);
        else if(elseCommand != null) Bukkit.dispatchCommand(executor, elseCommand);
        if(Config.PRINT_OUTPUT) {
            if(success) sender.sendMessage(ChatColor.YELLOW+"Command executed. "+ChatColor.GREEN+"Luck went successful.");
            else sender.sendMessage(ChatColor.YELLOW+"Command executed. "+ChatColor.RED+"Luck went unsuccessful.");
        }
        return true;
    }
}
