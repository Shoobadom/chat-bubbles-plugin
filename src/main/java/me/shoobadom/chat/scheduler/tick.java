package me.shoobadom.chat.scheduler;


import me.shoobadom.chat.Chat;
import me.shoobadom.chat.custom.ChatBubble;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class tick {
    private static final HashMap<UUID, String> toChat = new HashMap<>();

    private static final HashMap<UUID,ChatBubble> plrChat = new HashMap<>();


    private final static Plugin plugin = Chat.getInstance();
    public static void enableTick() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, () -> {


            for (UUID id : toChat.keySet()) {

                if (plrChat.get(id) != null) {
                    plrChat.get(id).newChat(toChat.get(id));
                } else {
                    // create new chat for player
                    plrChat.put(id, new ChatBubble(plugin.getServer().getPlayer(id),toChat.get(id)));
                }


            }
            ArrayList<UUID> io = new ArrayList<>();
            for (UUID id : plrChat.keySet()) {
                if (!plrChat.get(id).incrementDur(2)) {
                    //plrChat.remove(id);
                    io.add(id);
                }
            }
            for (UUID id : io) {
                plrChat.remove(id);
            }



            toChat.clear();

        }, 0L, 2L);
    }
    public static void addPlayerChat(UUID uuid, String chat) {
        // need to convert from asynchronous to actually doing it on server
        // (chat is not kept in sync with rest of server)
        toChat.put(uuid,chat);
    }

    public static void removePlayerChat(UUID uuid) {
        if (plrChat.get(uuid) != null) {
            plrChat.get(uuid).delete();
            plrChat.remove(uuid);
        }

    }




}