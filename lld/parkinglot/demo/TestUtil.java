package lld.parkinglot.demo;

public final class TestUtil {
    static void assertTrue(boolean condition, String msg) {
        if (!condition) throw new AssertionError("Assertion failed: " + msg);
    }

    static void assertEquals(double expected, double actual, double eps, String msg) {
        if (Math.abs(expected - actual) > eps) {
            throw new AssertionError("Assertion failed: " + msg + " expected=" + expected + " actual=" + actual);
        }
    }

    static void assertThrows(Class<? extends Throwable> exType, Runnable r, String msg) {
        try {
            r.run();
            throw new AssertionError("Assertion failed: " + msg + " expected exception " + exType.getSimpleName());
        } catch (Throwable t) {
            if (!exType.isInstance(t)) {
                throw new AssertionError("Assertion failed: " + msg + " expected " + exType.getSimpleName()
                        + " but got " + t.getClass().getSimpleName());
            }
        }
    }
}

