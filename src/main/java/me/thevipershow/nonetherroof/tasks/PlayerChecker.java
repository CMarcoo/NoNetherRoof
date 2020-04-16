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

import java.util.HashMap;
import java.util.UUID;
import me.thevipershow.nonetherroof.config.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerChecker implements Listener {

    private static PlayerChecker instance = null;

    private final HashMap<UUID, GenericPair<Location, Location>> playersLocation;
    private final Values configValues;
    private final JavaPlugin plugin;

    private PlayerChecker(HashMap<UUID, GenericPair<Location, Location>> playersLocation, Values configValues, JavaPlugin plugin) {
        this.playersLocation = playersLocation;
        this.configValues = configValues;
        this.plugin = plugin;
    }

    public static PlayerChecker getInstance(final HashMap<UUID, GenericPair<Location, Location>> playersLocation, final Values configValues, final JavaPlugin plugin) {
        return instance == null ? new PlayerChecker(playersLocation, configValues, plugin) : instance;
    }

    @EventHandler(ignoreCancelled = true)
    protected void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final Location iLo = player.getLocation();
        playersLocation.putIfAbsent(uuid, new GenericPair<>(null, null));
    }

    @EventHandler(ignoreCancelled = true)
    protected void onPlayerQuit(PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        playersLocation.remove(uuid);
    }

    public void updateLocations() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            playersLocation.keySet().forEach(uuid -> {
                final Player iPlayer = Bukkit.getPlayer(uuid);
                if (iPlayer != null) {
                    playersLocation.get(uuid).setFirst(iPlayer.getLocation());
                }
            });
        }, 1L, 10L);
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            playersLocation.keySet().forEach(uuid1 -> {
                final Player iPlayer1 = Bukkit.getPlayer(uuid1);
                if (iPlayer1 != null) {
                    playersLocation.get(uuid1).setSecond(iPlayer1.getLocation());
                }
            });
        }, 5L, 10L);
    }

    public void performPunishments() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            playersLocation.keySet().forEach(uuid -> {
                final Player iPlayer = Bukkit.getPlayer(uuid);
                if (iPlayer != null) {
                    final GenericPair<Location, Location> locs = playersLocation.get(uuid);
                    final Location iLocation = locs.getSecond();
                    final Location iLocation1 = locs.getFirst();
                    if (iLocation.getWorld().getEnvironment() == World.Environment.NETHER
                            && iLocation.getY() >= 128.0d) {
                        configValues.getExecutableCommands().forEach(s -> {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s
                                    .replace("%PLAYER%", iPlayer.getName())
                                    .replace("%PREV_LOC%", iLocation1.getBlockX() + " " + iLocation1.getBlockY() + " " + iLocation1.getBlockZ()));
                        });
                    }
                }
            });
        }, 35L, 10L);
    }


}
