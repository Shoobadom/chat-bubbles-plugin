package me.shoobadom.chatbubbles.events;


import me.shoobadom.chatbubbles.custom.Files;
import me.shoobadom.chatbubbles.scheduler.tick;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class Events implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void plrChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            // add plr to Async chat queue
            tick.addPlayerChat(event.getPlayer().getUniqueId(),event.getMessage());
            event.setCancelled(!Files.getBool("display-message-in-chat"));
        }
    }

    @EventHandler
    public void plrLeave(PlayerQuitEvent event) { // remove player speech
        tick.removePlayerChat(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void plrDie(PlayerDeathEvent event) { // remove player speech
        tick.removePlayerChat(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void plrPortal(PlayerPortalEvent event) {
        tick.removePlayerChat(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void plrMove(PlayerMoveEvent event) {
        Material m =event.getPlayer().getLocation().getBlock().getType();
        if (m == Material.NETHER_PORTAL || m == Material.END_PORTAL) {
            // players cant enter portals while something is riding them
            tick.removePlayerChat(event.getPlayer().getUniqueId());
        }

    }

}
