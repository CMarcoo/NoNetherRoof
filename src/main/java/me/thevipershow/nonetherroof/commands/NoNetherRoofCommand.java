/*
 * Software licensed by TheViperShow on 17/04/20, 00:02
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package me.thevipershow.nonetherroof.commands;

import me.thevipershow.nonetherroof.config.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class NoNetherRoofCommand implements CommandExecutor {

    private static NoNetherRoofCommand instance = null;

    private final Values config;
    private final JavaPlugin plugin;

    private NoNetherRoofCommand(final Values config, final JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    public static NoNetherRoofCommand getInstance(final Values config, final JavaPlugin plugin) {
        if (instance == null) {
            instance = new NoNetherRoofCommand(config, plugin);
            return instance;
        }
        return instance;
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("nonetherroof.reload")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                config.updateValues(plugin.getConfig());
                sender.sendMessage("> Config reloaded");
            } else {
                sender.sendMessage("> Invalid command");
            }
        }
        return true;
    }
}
