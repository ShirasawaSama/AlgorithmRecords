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
}
