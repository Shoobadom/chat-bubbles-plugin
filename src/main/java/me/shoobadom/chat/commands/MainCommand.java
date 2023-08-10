package me.shoobadom.chat.commands;

import me.shoobadom.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    private Chat instance = Chat.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("chatbubbles")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.AQUA + "Welcome to Shoobadom's Chat Bubbles. Use '/cb help' for help!");
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.YELLOW+"There are no commands associated with Shoobadom's Chat Bubbles\n...yet. Use '/cb info' to receive information about this plugin.");

            } else if (args[0].equalsIgnoreCase("info")) {
                String msg = ChatColor.GOLD+""+ChatColor.BOLD+"Shoobadom's Chat Bubbles"+ChatColor.RESET+ChatColor.GOLD+" ("+instance.getDescription().getName() + ")"+ChatColor.BOLD+"\nVersion " + instance.getDescription().getVersion()+ChatColor.RESET+ChatColor.AQUA;
                msg += "\nThis plugin creates chat bubbles containing player messages.\nThe maximum number of bubbles and the duration they stay above players (and a variety of other options) can be customised in the config file."
                        + ChatColor.YELLOW+"\nThanks for downloading this plugin!\n";
                sender.sendMessage(msg);
            } else {
                sender.sendMessage(ChatColor.RED + "Error. Invalid command. For help, use '/cb help'");
            }

        }
        return true;

    }
}
