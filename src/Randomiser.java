import java.util.concurrent.atomic.AtomicLong;

public class Randomiser {
    private AtomicLong x;
    private long a;
    private long c;
    private long m;
    private int k = 0;
    public Randomiser() {
        this.a = 25214903917L;
        this.m = 2 ^ 48;
    }
    public Randomiser(long a, long c, long m) {
        this.a = a;
        this.c = c;
        this.m = m;
    }
    public Randomiser withSeed(int c) {
        this.c = c;
        x = new AtomicLong(c);
        return this;
    }
    public int next() {
        if (k > 4) k = 0;
        else k++;
        return (int) ((a * x.getAndIncrement() + c) % (m+k));
    }
}
