class Prime {
    public static boolean isPrime(int n){
        if (n < 2) return false;
        for (int i = 2; i <= n / i; i++) if(n % i == 0) return false;
        return true;
    }

    public static void getPrimes(int n){
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, 2, n, true);
        for (int i = 2; i * i <= n; ++i) {
            if (prime[i]) for (int j = i * i; j <= n; j += i) prime[j] = false;
        }
    }
    
    private static boolean isPrime2(long x) {
        if (x < 3) return x == 2;
        if (x % 2 == 0) return false;
        long d = x - 1, r = 0;
        while (d % 2 == 0) {
            d /= 2;
            ++r;
        }
        for (long a : A) {
            long v = fastPower(a, d, x);
            if (v <= 1 || v == x - 1) continue;
            for (int i = 0; i < r; ++i) {
                v = v * v % x;
                if (v == x - 1 && i != r - 1) {
                    v = 1;
                    break;
                }
                if (v == 1) return false;
            }
            if (v != 1) return false;
        }
        return true;
    }
}
