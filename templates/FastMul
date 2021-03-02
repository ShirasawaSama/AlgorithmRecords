class FastMul {
    public static long fastPower(long base, long power) {
        long result = 1;
        while (power > 0) {
            if ((power & 1L) == 0L) {
                result = result * base % 1000;
            }
            power >>= 1;
            base = (base * base) % 1000;
        }
        return result;
    }
}
