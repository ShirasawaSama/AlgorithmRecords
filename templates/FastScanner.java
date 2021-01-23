import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FastScanner {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 16384);
    private StringTokenizer st;
    public FastScanner() { eat(""); }
    public void eat(String s) { st = new StringTokenizer(s); }
    public String nextLine() {
        try { return br.readLine(); } catch (Exception e) { return null; }
    }
    public boolean hasNext() {
        while (!st.hasMoreTokens()) {
            String s = nextLine();
            if (s == null) return false;
            eat(s);
        }
        return true;
    }
    public String next() {
        hasNext();
        return st.nextToken();
    }
    public int nextInt() { return Integer.parseInt(next()); }
}
