/*
 * Software licensed by TheViperShow on 16/04/20, 20:29
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package me.thevipershow.nonetherroof.tasks;

import me.thevipershow.nonetherroof.config.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public final class PlayerChecker implements Listener {
    private static PlayerChecker instance = null;

    private final Values configValues;
    private final JavaPlugin plugin;

    private PlayerChecker(Values configValues, JavaPlugin plugin) {
        this.configValues = configValues;
        this.plugin = plugin;
    }

    public static PlayerChecker getInstance(final Values configValues, final JavaPlugin plugin) {
        if (instance == null) {
            instance = new PlayerChecker(configValues, plugin);
            return instance;
        }
        return instance;
    }

    private <T extends PlayerEvent & Cancellable> void performCheck(T event, final Location from, final Location to) {
        event.setCancelled(true);
        final double fx = from.getX(), fy = from.getY(), fz = from.getZ();
        final double tx = to.getX(), ty = to.getY(), tz = to.getZ();
        configValues.getExecutableCommands().forEach(s -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                s.replace("%PLAYER%", event.getPlayer().getName())
                        .replace("%PREV_LOC%", fx + " " + fy + " " + fz)
                        .replace("%PREV_LOC_WORLD%", from.getWorld().getName())
                        .replace("%ROOF_POS%", tx + " " + ty + " " + tz)));
    }

    @EventHandler(ignoreCancelled = true)
    public final void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission("nonetherroof.bypass")) {
            if (configValues.isCancel()) {
                if (player.getLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
                    if (event.getTo().getY() >= 128.d) {
                        performCheck(event, event.getFrom(), event.getTo());
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public final void onPlayerTeleport(PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission("nonetherroof.bypass")) {
            final Location locationTo = event.getTo();
            if (locationTo.getWorld().getEnvironment() == World.Environment.NETHER) {
                if (locationTo.getY() >= 128.d) {
                    performCheck(event, event.getFrom(), locationTo);
                }
            }
        }
    }
}