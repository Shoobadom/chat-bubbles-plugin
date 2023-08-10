package me.shoobadom.chat;


import me.shoobadom.chat.commands.MainCommand;
import me.shoobadom.chat.custom.Files;
import me.shoobadom.chat.events.Events;
import me.shoobadom.chat.scheduler.tick;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public final class Chat extends JavaPlugin {
    private static Chat instance;

    @Override
    public void onEnable() {
        instance =this;
        getServer().getPluginManager().registerEvents(new Events(),instance);
        Files.setup();

        tick.enableTick();

        MainCommand cmds = new MainCommand();
        getCommand("chatbubbles").setExecutor(cmds);

        new Metrics(this,19449);
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            tick.removePlayerChat(player.getUniqueId());
        }
    }

    public static Chat getInstance() {
        return instance;
    }
}
