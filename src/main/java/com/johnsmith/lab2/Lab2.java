package com.johnsmith.lab2;

import com.johnsmith.lab1.Lab1;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class Lab2 {


    public static void startCiphers(String fileName) throws URISyntaxException {
        byte[] bytes = readFile(fileName);
        String fileExtension = '.' + (String) Arrays.stream(fileName.split("\\.")).toArray()[1];
        shamir(bytes, "./result/resultShamir" + fileExtension);
        elGamal(bytes, "./result/resultElGamal" + fileExtension);
        rsa(bytes, "./result/resultRSA" + fileExtension);
        vernam(bytes, "./result/resultVernam" + fileExtension);

    }

    private static byte[] readFile(String fileName) throws URISyntaxException {
        URL resource = Lab2.class.getResource('/' + fileName);
        assert resource != null;
        File file = new File(resource.toURI());

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[]{};

    }

    private static void writeFile(byte[] data, String fileName) throws URISyntaxException {
        File file = new File(fileName);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void shamir(byte[] data, String resultFileName) throws URISyntaxException {
        boolean flag = true;
        long p = 0;

        while (flag) {
            p = (long) (Math.random() * 1000000000) + 3;
            if (Lab1.testFerma(p, 1000)) {
                flag = false;
            }
        }

        long ca = (long) ((Math.random() * 1000000) + 3);
        while (Lab1.gcd(ca, p - 1) != 1) {
            ca = (long) (Math.random() * 100) + 3;
        }

        long da = Lab1.euclidAlgorithm(ca, p - 1)[1];
        if (da < 0) {
            da += p - 1;
        }

        long cb = (long) (Math.random() * 1000000) + 3;
        while (Lab1.gcd(cb, p - 1) != 1) {
            cb = (long) (Math.random() * 1000000) + 3;
        }

        long db = Lab1.euclidAlgorithm(cb, p - 1)[1];
        if (db < 0) {
            db += p - 1;
        }

        byte[] decode = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            long x1 = Lab1.exponentiation(data[i], ca, p);
            long x2 = Lab1.exponentiation(x1, cb, p);
            long x3 = Lab1.exponentiation(x2, da, p);
            byte x4 = (byte) Lab1.exponentiation(x3, db, p);
            decode[i] = x4;
        }

        writeFile(decode, resultFileName);

    }


    public static void elGamal(byte[] data, String resultFileName) throws URISyntaxException {
        boolean flag = true;
        long p = 0;
        long q = 0;
        while (flag) {
            p = (long) (Math.random() * 1000) + 3;
            if (Lab1.testFerma(p, 1000)) {
                flag = false;
            }
        }


        long g = (long) ((Math.random() * p - 1) + 3);
        while (Lab1.exponentiation(g, 1, p) != 1) {
            g = (long) ((Math.random() * p - 1) + 3);
        }


        long x = (long) ((Math.random() * p - 1) + 1);
        long k = (long) ((Math.random() * p - 2) + 2);
        long y = Lab1.exponentiation(g, x, p);
        long a = Lab1.exponentiation(g, k, p);

        byte[] decode = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            long b = data[i] * Lab1.exponentiation(y, k, p);
            byte m1 = (byte) ((b * Lab1.exponentiation(a, p - 1 - x, p)) % p);
            decode[i] = m1;
        }

        writeFile(decode, resultFileName);

    }

    public static void rsa(byte[] data, String resultFileName) throws URISyntaxException {
        boolean flag = true;
        long p = 0;
        long q = 0;

        while (flag) {
            p = (long) (Math.random() * 10000) + 3;
            if (Lab1.testFerma(p, 100)) {
                flag = false;
            }
        }

        flag = true;
        while (flag) {
            q = (long) (Math.random() * 10000) + 3;
            if (Lab1.testFerma(q, 100)) {
                flag = false;
            }
        }

        long n = p * q;

        long f = (p - 1) * (q - 1);

        long d = (long) ((Math.random() * (f - 1)) + 1);
        while (Lab1.gcd(d, f) != 1) {
            d = (long) ((Math.random() * (f - 1)) + 1);
        }

        long c = Lab1.euclidAlgorithm(d, f)[1];
        if (c < 0) {
            c += f;
        }

        byte[] decode = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            long e = Lab1.exponentiation(data[i], d, n);
            byte m1 = (byte) Lab1.exponentiation(e, c, n);
            decode[i] = m1;
        }

        writeFile(decode, resultFileName);

    }

    public static void vernam(byte[] data, String resultFileName) throws URISyntaxException {
        int currPositionDecode = 0;
        byte[] result = new byte[data.length];

        for (byte datum : data) {
            String binaryMessage = Integer.toBinaryString(datum);
            byte[] decode = new byte[binaryMessage.length()];
            StringBuilder key = new StringBuilder();

            for (int j = 0; j < binaryMessage.length(); j++) {
                key.append((int) (Math.random() * 1));
            }

            StringBuilder xor = new StringBuilder();
            for (int j = 0; j < binaryMessage.length(); j++) {
                xor.append(binaryMessage.charAt(j) ^ key.charAt(j));
            }

            for (int j = 0; j < binaryMessage.length(); j++) {
                decode[j] = (byte) (xor.charAt(j) ^ key.charAt(j));
            }

            long byteFromBinary = 0;
            for (int j = 0; j < binaryMessage.length(); j++) {
                if (decode[j] == 1) {
                    byteFromBinary += Math.pow((double) 2, (double) binaryMessage.length() - 1 - j);
                }
            }
            result[currPositionDecode] = (byte) byteFromBinary;
            currPositionDecode++;
        }

        writeFile(result, resultFileName);

    }


}
