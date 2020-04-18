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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import me.thevipershow.nonetherroof.config.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@SuppressWarnings("ConstantConditions")
public final class PlayerChecker implements Listener {
    private static PlayerChecker instance = null;

    private final Values configValues;
    private final HashMap<UUID, LocalDateTime> lastExecuted;

    private PlayerChecker(Values configValues, HashMap<UUID, LocalDateTime> lastExecuted) {
        this.configValues = configValues;
        this.lastExecuted = lastExecuted;
    }

    public static PlayerChecker getInstance(final Values configValues, final HashMap<UUID, LocalDateTime> lastExecuted) {
        if (instance == null) {
            instance = new PlayerChecker(configValues, lastExecuted);
            return instance;
        }
        return instance;
    }

    private void executeExecutables(final Location from, final Location to, final String playerName) {
        final double fx = from.getX(), fy = from.getY(), fz = from.getZ();
        final double tx = to.getX(), ty = to.getY(), tz = to.getZ();
        configValues.getExecutableCommands().forEach(s -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                s.replace("%PLAYER%", playerName)
                        .replace("%PREV_LOC%", fx + " " + fy + " " + fz)
                        .replace("%PREV_LOC_WORLD%", from.getWorld().getName())
                        .replace("%ROOF_POS%", tx + " " + ty + " " + tz)));
    }

    private <T extends PlayerEvent & Cancellable> void performCheck(T event, final Location from, final Location to) {
        if (configValues.isCancel()) {
            if (event.getPlayer().hasPermission("nonetherroof.bypass")) {
                if (to.getWorld().getEnvironment() == World.Environment.NETHER && to.getY() >= 128.d) {
                    event.setCancelled(true);
                    final UUID targetUUID = event.getPlayer().getUniqueId();
                    final String playerName = event.getPlayer().getName();
                    final LocalDateTime currentTime = LocalDateTime.now();
                    if (configValues.getExecutablesDelay() == -1L) {
                        executeExecutables(from, to, playerName);
                    } else if (!lastExecuted.containsKey(targetUUID)) {
                        executeExecutables(from, to, playerName);
                        lastExecuted.put(targetUUID, currentTime);
                    } else if (Math.abs(currentTime.getSecond() - lastExecuted.get(targetUUID).getSecond()) >= configValues.getExecutablesDelay()) {
                        executeExecutables(from, to, playerName);
                        lastExecuted.put(targetUUID, currentTime);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public final void onPlayerMove(PlayerMoveEvent event) {
        performCheck(event, event.getFrom(), event.getTo());
    }

    @EventHandler(ignoreCancelled = true)
    public final void onPlayerTeleport(PlayerTeleportEvent event) {
        performCheck(event, event.getFrom(), event.getTo());
    }
}