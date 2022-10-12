package com.johnsmith.lab2;

import com.johnsmith.lab1.Lab1;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab2 {


    public static void shamirCipher(String nameFile) throws URISyntaxException {
        byte[] bytes = readFile(nameFile);
    }

    private static byte[] readFile(String nameFile) throws URISyntaxException {
        URL resource = Lab2.class.getResource('/' + nameFile);
        assert resource != null;
        File file = new File(resource.toURI());
        List<String> list = new ArrayList<>();
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[]{};
    }

    public static void shamir(long m) {
        boolean flag = true;
        long p = 0;
        while (flag) {
            p = (long) (Math.random() * 1000000000) + 3;
            if (Lab1.testFerma(p, 1000)) {
                flag = false;
            }
        }

        System.out.println("P = " + p);

        long ca = (long) ((Math.random() * 10000) + 3);
        while (Lab1.gcd(ca, p - 1) != 1) {
            ca = (long) (Math.random() * 10000) + 3;
        }


        long[] gcd = Lab1.euclidAlgorithm(ca, p - 1);
        System.out.println(Arrays.toString(gcd));
        long da = gcd[1] % (p - 1);

        long cb = (long) (Math.random() * 10000) + 3;
        while (Lab1.gcd(cb, p - 1) != 1) {
            cb = (long) (Math.random() * 10000) + 3;
        }

        gcd = Lab1.euclidAlgorithm(cb, p - 1);
        long db = gcd[1] % (p - 1);


//        long ca = (long) (Math.random() * 1000000000) + 3;
//        long da = (long) (Math.random() * 1000000000) + 3;
//
//        while ((ca * da) % (p - 1) != 1) {
//            ca = (long) (Math.random() * 1000000000) + 3;
//
//            da = (long) (Math.random() * 1000000000) + 3;
//
//        }
//
//        long cb = (long) (Math.random() * 1000000000) + 3;
//
//        long db = (long) (Math.random() * 1000000000) + 3;
//        while ((cb * db) % (p - 1) != 1) {
//            cb = (long) (Math.random() * 1000000000) + 3;
//            db = (long) (Math.random() * 1000000000) + 3;
//        }
//
//        System.out.println(ca);
//        System.out.println(da);
//        System.out.println(cb);
//        System.out.println(db);


        long x1 = Lab1.exponentiation(m, ca, p);
        long x2 = Lab1.exponentiation(x1, cb, p);
        long x3 = Lab1.exponentiation(x2, da, p);
        long x4 = Lab1.exponentiation(x3, db, p);
        System.out.println("X1 = " + x1);
        System.out.println("X2 = " + x2);
        System.out.println("X3 = " + x3);
        System.out.println("X4 = " + x4);

    }


    public static void elGamal(long m) {
        boolean flag = true;
        long p = 0;
        long q = 0;
        while (flag) {
            p = (long) (Math.random() * 1000000000) + 3;
            if (Lab1.testFerma(p, 1000)) {
                flag = false;
            }
        }



    }


}
