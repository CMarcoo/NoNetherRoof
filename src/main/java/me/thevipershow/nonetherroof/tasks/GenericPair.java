/*
 * Software licensed by TheViperShow on 16/04/20, 22:22
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package me.thevipershow.nonetherroof.tasks;

public class GenericPair<A, B> {
    private A first;  // The first element of this pair
    private B second; // The second element of this pair

    /**
     * Creates a new Pair with two elements stored in order.
     *
     * @param first  the first element.
     * @param second the second element.
     */
    public GenericPair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first element stored in the pair.
     *
     * @return The first element.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Get the second element stored in the pair.
     *
     * @return The second element.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Sets the first element stored in the pair.
     *
     * @param first an Object
     */
    public void setFirst(A first) {
        this.first = first;
    }

    /**
     * Sets the second element stored in the pair.
     *
     * @param second an Object
     */
    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenericPair<?, ?>) {
            return (((GenericPair<?, ?>) obj).first.equals(first)
                    && ((GenericPair<?, ?>) obj).getSecond().equals(second));
        }
        return false;
    }
}
