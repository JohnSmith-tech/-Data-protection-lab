package com.johnsmith.lab1;

import java.util.*;

public class Lab1 {
    public static long exponentiation(long a, long x, long p) {
        long y = 1;
        long s = a;
        List<Integer> listX = convertToBinaryNumSys(x);

        for (int i = listX.size() - 1; i >= 0; i--) {
            if (listX.get(i) == 1) {
                y = y * s % p;
            }
            s = s * s % p;
        }
        return y;
    }

    private static List<Integer> convertToBinaryNumSys(long x) {
        List<Integer> list = new ArrayList<>();
        while (x != 0) {
            if (x % 2 > 0) {
                list.add(1);
            } else {
                list.add(0);
            }
            x /= 2;
        }

        Collections.reverse(list);
        return list;
    }

    public static long gcd(long a, long b) {
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static long[] euclidAlgorithm(long a, long b) {
        long[] u = new long[]{a, 1, 0};
        long[] v = new long[]{b, 0, 1};
        long[] t;
        while (v[0] != 0) {
            long q = u[0] / v[0];
            t = new long[]{u[0] % v[0], u[1] - q * v[1], u[2] - q * v[2]};
            u = Arrays.copyOf(v, v.length);
            v = Arrays.copyOf(t, t.length);
        }
        u[0] = gcd(a, b);
        u[1] = v[1];
        u[2] = v[2];
        return u;

    }


    public static boolean testFerma(long p, int k) {
        if (p == 2) return true;
        if (p == 1) return false;
        for (int i = 0; i < k; i++) {
            long a = (long) (Math.random() * (p - 1)) + 1;
            if (gcd(a, p) != 1 || exponentiation(a, p - 1, p) != 1)
                return false;
        }
        return true;
    }

    public static void diffy() {
        boolean flag = true;
        long p = 0;
        long q = 0;
        while (flag) {
            p = (long) (Math.random() * 1000000000) + 3;
            if (testFerma(p, 1000)) {
                q = (p - 1) / 2;
                if (testFerma(q, 1000)) {
                    flag = false;
                }
            }
        }
        System.out.println("P: " + p);
        System.out.println("Q: " + q);

        long g = 0;
        for (int i = 2; i < p - 1; i++) {
            if (exponentiation(i, q, p) != 1) {
                g = i;
                break;
            }
        }

        System.out.println("g: " + g);
        long xa = (long) (Math.random() * 500) + 10;
        long xb = (long) (Math.random() * 500) + 10;
        System.out.println("Xa = " + xa);
        System.out.println("Xb = " + xb);

        long ya = exponentiation(g, xa, p);
        long yb = exponentiation(g, xb, p);
        long zab = exponentiation(yb, xa, p);
        long zba = exponentiation(ya, xb, p);
        System.out.println("Zab = " + zab);
        System.out.println("Zba = " + zba);

    }


    public static int babyStepGiantStep(long y, long p, long a) {
        long m = (long) Math.sqrt((double) p);
        long k = m;
        while (p >= m * k) {
            if (m > k) k++;
            else m++;
        }

        List<Long> firstList = new ArrayList<>();
        List<Long> secondList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            firstList.add(y * exponentiation(a, i, p) % p);
        }
        for (int i = 1; i <= k; i++) {
            secondList.add(exponentiation(a, i * m, p));
        }

        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            map.put(firstList.get(i), i);
        }

        int I = 0;
        int J = 0;
        for (int i = 0; i < k; i++) {
            if (map.containsKey(secondList.get(i))) {
                I = i + 1;
                J = map.get(secondList.get(i));
                break;
            }
        }


        return (int) (I * m - J);
    }


}
