package me.shoobadom.chat.custom;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class ChatBubble {

    private final TextDisplay[] bubbles = new TextDisplay[Math.max(1,Files.getInt("bubble-count"))];
    private final int[] dur = new int[bubbles.length];
    private final Player p;
    private final float plrAdjustment = (float) (1.1 + Files.getDouble("bubble-offset-from-player"));

    private final float distanceBetweenBubbles = (float) Math.max(0.0,Files.getDouble("distance-between-bubbles"));

    private final int counter = (int) (20 * Math.max(0.0,Files.getDouble("delay")));

    public ChatBubble(Player player, String msg) {
        p = player;

        newChat(msg);
    }

    public void newChat(String msg) {

        if (bubbles[bubbles.length-1] != null) {
            bubbles[bubbles.length-1].remove();
        }
        for (int i=bubbles.length-1;i>0;i--) {

            bubbles[i] = bubbles[i-1];
            dur[i] = dur[i-1];
        }

        Location loc = p.getLocation();
        TextDisplay td = p.getWorld().spawn(loc, TextDisplay.class);
        td.setText(msg);
        td.setBillboard(Display.Billboard.VERTICAL);
        td.setTransformation(new Transformation(new Vector3f(0F,plrAdjustment,0F),new AxisAngle4f(),new Vector3f(1,1,1),new AxisAngle4f()));


        bubbles[0]=td;
        dur[0]=0;
        p.addPassenger(bubbles[0]);
        p.setCustomNameVisible(true);
        for (int i=bubbles.length-1;i>0;i--) { // only if there are 2 or 3 msgs does this run:

            if (bubbles[i] != null) {

                float transl = (float) (Files.readPixels(bubbles[0].getText()) /200 +1)*0.3F+distanceBetweenBubbles;// 0.3 is almost the EXACT height. Add constant of
                bubbles[i].setTransformation(new Transformation(bubbles[i].getTransformation().getTranslation().add(new Vector3f(0F,transl,0F)), new AxisAngle4f(),new Vector3f(1,1,1), new AxisAngle4f()));

            }
        }



// Set the size of the font

    }

    public boolean incrementDur(int val) {

        for (int i = 0; i<dur.length; i++) {

            if (dur[i]+val >= counter) {
                // delete message
                dur[i]=0;
                if (bubbles[i] != null) {
                    bubbles[i].remove();
                }

                bubbles[i]=null;

            } else {
                dur[i] = dur[i]+val;
            }
        }

        return !(bubbles[0] == null);

    }

    public void delete() {
        for (int i=0;i<bubbles.length;i++) {
            if (bubbles[i] != null) {
                bubbles[i].remove();
            }
            bubbles[i]=null;
        }
    }



}
