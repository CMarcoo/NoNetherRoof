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
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

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

    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (configValues.isCancel()) {
            if (player.getLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
                if (event.getTo().getY() >= 128.d) {
                    event.setCancelled(true);
                    final double fx = event.getFrom().getX(), fy = event.getFrom().getY(), fz = event.getFrom().getZ();
                    final double tx = event.getTo().getX(), ty = event.getTo().getY(), tz = event.getTo().getZ();
                    configValues.getExecutableCommands().forEach(s -> {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                                s.replace("%PLAYER%", player.getName())
                                        .replace("%PREV_LOC%", fx + " " + fy + " " + fz)
                                        .replace("%PREV_LOC_WORLD%", event.getFrom().getWorld().getName())
                                        .replace("%ROOF_POS%", tx + " " + ty + " " + tz));
                    });
                }
            }
        }
    }
}
