package com.anderhurtado.java.spigot.luckycommands.data;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.Serializable;

public class ExecutableConditionalCommands implements Serializable {

    public double probability; // Probability out of 1
    public ExecutableCommand[] commandIfSuccess;
    public ExecutableCommand[] commandIfUnsuccess;

    public boolean executeCommands() {
        boolean success = probability > Math.random();
        ExecutableCommand[] commands;
        if(success) commands = commandIfSuccess;
        else commands = commandIfUnsuccess;
        if(commands != null) for(ExecutableCommand cmd : commands) cmd.executeCommand();
        return success;
    }

    public static class ExecutableCommand implements Serializable {
        public String executeAs = "@"; // Name of the player or just @ to execute as the console
        public String command;

        public CommandSender getSender() {
            if(executeAs == null) return null;
            if(executeAs.equals("@")) return Bukkit.getConsoleSender();
            else return Bukkit.getPlayerExact(executeAs);
        }

        public void executeCommand() {
            CommandSender sender = getSender();
            if(sender != null && command != null) Bukkit.dispatchCommand(sender, command);
        }
    }

}
