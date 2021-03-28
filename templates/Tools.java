class Tools {
    public static int gcd(int a, int b) { // 最大公约数
	    return b == 0 ? a : gcd(b, a % b);
    }
    public static int gcd2(int x, int y) {
	    int z = y;
	    while (x % y != 0) {
            z = x % y;
            x = y;
            y = z;
        }
	    return z;
    }
    
    public static int lcm(int a, int b) { // 最小公倍数
        return a * b / gcd(a, b);
    }
}
