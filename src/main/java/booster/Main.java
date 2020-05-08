package booster;

import cn.nukkit.scheduler.Task;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.Player;
import java.util.HashSet;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener
{
    public static HashSet<Player> boosting;
    
    static {
        Main.boosting = new HashSet<Player>();
    }
    
    public void onEnable() {
        this.getLogger().notice("\ud574\ub2f9 \ud50c\ub7ec\uadf8\uc778\uc740 \ub125\uc11c\uc2a4\uc11c\ubc84\uc5d0\uc11c \uc81c\uc791\ub418\uc5c8\uc2b5\ub2c8\ub2e4");
        this.getLogger().info("§6\ud574\ub2f9 \uc800\uc791\ubb3c\uc744 \uc0c1\uc5c5\uc801\uc778 \uc6a9\ub3c4\ub85c \ud310\ub9e4\ud558\ub294\uac74 \uae08\uc9c0\ub418\uc5b4\uc788\uc2b5\ub2c8\ub2e4");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getPlayer().getLevel().getBlock(new Vector3((double)(int)Math.round(event.getPlayer().x - 0.5), (double)(int)Math.round(event.getPlayer().y - 1.0), (double)(int)Math.round(event.getPlayer().z - 0.5)));
        if (block.getId() == 42 | block.getId() == 41 | block.getId() == 57) {
            switch (block.getId()) {
                case 42: {
                    player.setMotion(this.getShotting(player, 1));
                    this.setFall(player);
                }
                case 41: {
                    player.setMotion(this.getShotting(player, 2));
                    this.setFall(player);
                }
                case 57: {
                    player.setMotion(this.getShotting(player, 3));
                    this.setFall(player);
                }
            }
        }
    }
    
    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        if (event.getReasonEnum() == PlayerKickEvent.Reason.FLYING_DISABLED && Main.boosting.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    
    public Vector3 getShotting(final Player player, final int xx) {
        final double x = -Math.sin(player.yaw / 180.0 * 3.141592653589793) * Math.cos(player.pitch / 180.0 * 3.141592653589793);
        final double y = -Math.sin(player.pitch / 180.0 * 3.141592653589793);
        final double z = Math.cos(player.yaw / 180.0 * 3.141592653589793) * Math.cos(player.pitch / 180.0 * 3.141592653589793);
        return new Vector3(x * xx, y * xx, z * xx);
    }
    
    public void setFall(final Player player) {
        Main.boosting.add(player);
        Server.getInstance().getScheduler().scheduleDelayedTask((Task)new Task() {
            public void onRun(final int currentTick) {
                if (Main.boosting.contains(player)) {
                    Main.boosting.remove(player);
                }
            }
        }, 200);
    }
}
