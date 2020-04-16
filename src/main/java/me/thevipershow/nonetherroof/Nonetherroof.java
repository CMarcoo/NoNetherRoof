/*
 * Software licensed by TheViperShow on 16/04/20, 20:13
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package me.thevipershow.nonetherroof;

import java.util.HashMap;
import java.util.UUID;
import me.thevipershow.nonetherroof.config.Values;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nonetherroof extends JavaPlugin {

    private Values configValues;
    private final HashMap<UUID, Location> playersLocation = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configValues = new Values(getConfig());
    }

    @Override
    public void onDisable() {
        configValues.clearAll();
    }
}
