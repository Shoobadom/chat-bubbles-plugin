package me.shoobadom.chatbubbles;


import me.shoobadom.chatbubbles.commands.MainCommand;
import me.shoobadom.chatbubbles.custom.Files;
import me.shoobadom.chatbubbles.events.Events;
import me.shoobadom.chatbubbles.scheduler.tick;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public final class ChatBubbles extends JavaPlugin {
    private static ChatBubbles instance;

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

    public static ChatBubbles getInstance() {
        return instance;
    }
}
