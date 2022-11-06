package com.johnsmith.lab2;

import com.johnsmith.lab1.Lab1;

import java.net.Socket;
import java.net.URISyntaxException;
import java.util.*;

import static com.johnsmith.lab2.WorkingWithFiles.*;

public class Lab2 {


    public static void startCiphers(String fileName) throws URISyntaxException {
        byte[] bytes = readFileToByteArray(fileName);
        String fileExtension = '.' + (String) Arrays.stream(fileName.split("\\.")).toArray()[1];
        shamir(bytes, "./result/resultShamir" + fileExtension);
        elGamal(bytes, "./result/resultElGamal" + fileExtension);
        rsa(bytes, "./result/resultRSA" + fileExtension);
        vernam(bytes, "./result/resultVernam" + fileExtension);
    }


    public static void shamir(byte[] data, String resultFileName) {
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


        List<Long> codeLong = new ArrayList<>();
        for (byte datum : data) {
            long x1 = Lab1.exponentiation(datum, ca, p);
            codeLong.add(x1);
        }


        writeFileLong(codeLong, "./coding/shamir.txt");
        List<Long> coding = readFileToArrayOfLong("./coding/shamir.txt");

        for (int i = 0; i < coding.size(); i++) {
            long x2 = Lab1.exponentiation(coding.get(i), cb, p);
            long x3 = Lab1.exponentiation(x2, da, p);
            byte x4 = (byte) Lab1.exponentiation(x3, db, p);
            decode[i] = x4;
        }

        writeFileByte(decode, resultFileName);

    }


    public static void elGamal(byte[] data, String resultFileName) {
        boolean flag = true;
        long p = 0;
        long q = 0;
        while (flag) {
            p = (long) (Math.random() * 1000000000);
            if (Lab1.testFerma(p, 100)) {
                q = (p - 1) / 2;
                if (Lab1.testFerma(q, 100)) {
                    flag = false;
                }
            }
        }

        long g = (long) (Math.random() * p - 1);
        if (g == 0 || g == 1) {
            g = 2;
        }

        while (Lab1.exponentiation(g, q, p) != 1) {
            g = (long) (Math.random() * p - 1);
            if (g == 0 || g == 1) {
                g = 2;
            }
        }


        long x = (long) ((Math.random() * p - 1) + 1);
        long y = Lab1.exponentiation(g, x, p);

        byte[] decode = new byte[data.length];

        List<Long> codeLong = new ArrayList<>();
        List<Long> listA = new ArrayList<>();
        for (byte datum : data) {
            long k = (long) ((Math.random() * p - 2) + 2);
            listA.add(Lab1.exponentiation(g, k, p));
            long b = (datum * Lab1.exponentiation(y, k, p)) % p;
            codeLong.add(b);
        }

        writeFileLong(codeLong, "./coding/el_gamal.txt");
        List<Long> codeByte = readFileToArrayOfLong("./coding/el_gamal.txt");


        for (int i = 0; i < data.length; i++) {

            byte m1 = (byte) ((codeByte.get(i) * Lab1.exponentiation(listA.get(i), p - 1 - x, p)) % p);
            decode[i] = m1;
        }

        writeFileByte(decode, resultFileName);

    }

    public static void rsa(byte[] data, String resultFileName) {
        boolean flag = true;
        long p = 0;
        long q = 0;

        while (flag) {
            p = (long) (Math.random() * 10000) + 255;
            if (Lab1.testFerma(p, 100)) {
                flag = false;
            }
        }

        flag = true;
        while (flag) {
            q = (long) (Math.random() * 10000) + 255;
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


        List<Long> codeLong = new ArrayList<>();
        for (byte datum : data) {
            long e = Lab1.exponentiation(datum, d, n);
            codeLong.add(e);
        }

        writeFileLong(codeLong, "./coding/rsa.txt");
        List<Long> codeByte = readFileToArrayOfLong("./coding/rsa.txt");


        byte[] decode = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            byte m1 = (byte) Lab1.exponentiation(codeByte.get(i), c, n);
            decode[i] = m1;

        }

        writeFileByte(decode, resultFileName);

    }


    public static void vernam(byte[] data, String resultFileName) {
        int currPositionDecode = 0;
        byte[] result = new byte[data.length];
        List<String> codeList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        for (byte datum : data) {
            String binaryMessage = Integer.toBinaryString(datum);
            StringBuilder key = new StringBuilder();

            for (int j = 0; j < binaryMessage.length(); j++) {
                key.append((int) (Math.random() * 2));
            }
            keyList.add(key.toString());
            StringBuilder xor = new StringBuilder();
            for (int j = 0; j < binaryMessage.length(); j++) {
                xor.append(binaryMessage.charAt(j) ^ key.charAt(j));
            }
            codeList.add(xor.toString());


        }

        writeFileString(codeList, "./coding/vernam.txt");
        codeList = readFileToArrayOfString("./coding/vernam.txt");

        for (int i = 0; i < codeList.size(); i++) {
            List<Byte> decodeList = new ArrayList<>();

            for (int j = 0; j < codeList.get(i).length(); j++) {
                decodeList.add((byte) (codeList.get(i).charAt(j) ^ keyList.get(i).charAt(j)));
            }

            long byteFromBinary = 0;

            for (int j = 0; j < codeList.get(i).length(); j++) {
                if (decodeList.get(j) == 1) {
                    byteFromBinary += Math.pow(2, (double) codeList.get(i).length() - 1 - j);
                }
            }
            result[currPositionDecode] = (byte) byteFromBinary;
            currPositionDecode++;

        }
        writeFileByte(result, resultFileName);

    }


}
