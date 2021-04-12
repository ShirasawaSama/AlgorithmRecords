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

    public static long pPow(long x, long n) {
        long res = 1;
        while (n != 0) {
            if ((n & 1L) == 1L) res *= x;
            x *= x;
            n >>= 1;
        }
        return res;
    }
}
