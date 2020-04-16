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

package me.thevipershow.nonetherroof.config;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public final class Values {

    private List<String> executableCommands;
    private boolean cancel;

    public Values(final FileConfiguration config) {
        updateValues(config);
    }

    public final void updateValues(final FileConfiguration config) {
        executableCommands = config.getStringList("main.punish-executables");
        cancel = config.getBoolean("main.cancel");
    }

    public final void clearAll() {
        executableCommands = null;
        cancel = false;
    }

    public List<String> getExecutableCommands() {
        return executableCommands;
    }

    public boolean isCancel() {
        return cancel;
    }
}
