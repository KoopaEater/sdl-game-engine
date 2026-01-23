package engine.renderer;

public final class HashUtil {
    private HashUtil() {}

    // Mix a 64-bit value into another 64-bit value (SplitMix64 finalizer)
    public static long mix64(long z) {
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    // Build a seed from 4 ints (pixel + sample indices) and mix it.
    public static long seed4(int a, int b, int c, int d) {
        long x = 0;
        x ^= (long) a * 0x9E3779B97F4A7C15L;
        x ^= (long) b * 0xC2B2AE3D27D4EB4FL;
        x ^= (long) c * 0x165667B19E3779F9L;
        x ^= (long) d * 0x85EBCA77C2B2AE63L;
        return mix64(x);
    }

    // Convert top 53 bits to a double in [0,1)
    public static double toUnitDouble(long h) {
        return ((h >>> 11) * 0x1.0p-53); // 2^-53
    }

    // Convenience: deterministic jitter in [-0.5, +0.5)
    public static double jitterCentered(int px, int py, int k, int l, int axisSalt) {
        long h = seed4(px, py, k, l) ^ (long) axisSalt * 0x9E3779B97F4A7C15L;
        return toUnitDouble(mix64(h)) - 0.5;
    }
}
