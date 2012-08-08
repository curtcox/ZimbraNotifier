package util;
import java.util.Iterator;
import strings.ForkedString;

/**
 * Iterators that return the same thing every time.
 * @author Curt
 */
public final class All {

    public static <T> Iterator<ForkedString<T>> of(final ForkedString<T> string) {
        return new InfiniteIterator<ForkedString<T>>(){
            @Override public ForkedString next() { return string; }
        };
    }

    static abstract class Forever implements Iterator<Boolean> {
        @Override public boolean hasNext() { return true; }
        @Override public Boolean next() { return true; }
        @Override public void remove() {}
    }

    public static Iterable<Boolean> TRUE = new Iterable<Boolean>() {
        @Override public Iterator<Boolean> iterator() {
            return new Forever() {};
        }
    };

    public static Iterable<Boolean> times(final int times) {
        return new Iterable<Boolean>() {
            @Override public Iterator<Boolean> iterator() {
                return new Forever() {
                    int i = 0;
                    @Override public boolean hasNext() { return i < times; }
                    @Override public Boolean next() { i++; return true; }
                    @Override public void remove() {}
                };
            }
        };
    }

    public static <T> Iterator<ForkedString<T>> ofTimes(final ForkedString<T> string, final int times) {
        return new Iterator<ForkedString<T>>() {
            int i = 0;
            @Override public boolean hasNext() { return i < times; }
            @Override public ForkedString<T> next() { i++; return string; }
            @Override public void remove() {}
        };
    }

    public static Iterable<Boolean> everyNSeconds(final int n) {
        return new Iterable<Boolean>() {
            @Override public Iterator<Boolean> iterator() {
                return new Forever() {
                    boolean first = true;
                    @Override public Boolean next() {
                        if (first) {
                            first = false;
                            return true;
                        } else {
                            sleepNSeconds(n);
                            return true;
                        }
                    }
                };
            }
        };
    }

    private static void sleepNSeconds(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
