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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlayerChecker {

    private static PlayerChecker instance = null;

    private final HashMap<UUID, Location> playersLocation;
    private final Values configValues;

    private PlayerChecker(HashMap<UUID, Location> playersLocation, Values configValues) {
        this.playersLocation = playersLocation;
        this.configValues = configValues;
    }

    public static PlayerChecker getInstance(final HashMap<UUID, Location> playersLocation, final Values configValues) {
        return instance == null ? new PlayerChecker(playersLocation, configValues) : instance;
    }

    private void performMapUpdate(final Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            playersLocation.keySet().forEach(uuid -> {
                final Player iPlayer = Bukkit.getPlayer(uuid);
                if (iPlayer == null)
                    playersLocation.remove(uuid);
            });
            Bukkit.getOnlinePlayers().forEach(p -> playersLocation.putIfAbsent(p.getUniqueId(), p.getLocation()));
        }, 20L, (20L * 60L));
    }

    private void performPunishments(final Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            
        }, 40L, 20L);
    }


}
