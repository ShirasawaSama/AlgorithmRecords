class FastMul {
    public static long fastPower(long a, long n, long p) {
        long ans = 1;
        while (n != 0) {
            if ((n & 1) != 0) ans = ans * a % p;
            a = a * a % p;
            n >>= 1;
        }
        return ans;
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
