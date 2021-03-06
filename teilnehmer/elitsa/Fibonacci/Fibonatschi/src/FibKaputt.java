public class FibKaputt
{

    private static final int CACHE_SIZE = 100;

    private long[]           cache      = new long[CACHE_SIZE];

    public FibKaputt()
    {
        cache[0] = 1;
        cache[1] = 1;
    }

    public long compute(final int n)
    {
        long[] cache2 = cache;
        if (cache2[n - 1] != 0) return cache2[n - 1];

        long n2 = compute(n - 2);
        long n1 = compute(n - 1);
        long result = n1 + n2;
        cache2[n - 1] = result;
        return result;
    }

    public static void main(final String[] args)
    {
        FibKaputt e = new FibKaputt();
        for (int i = 1; i <= CACHE_SIZE; i++)
        {
            long result = e.compute(i);
            System.out.println("e(" + i + ") = " + result);
        }
    }

}
