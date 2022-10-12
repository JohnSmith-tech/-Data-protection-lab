package com.johnsmith;

import com.johnsmith.lab1.Lab1;
import com.johnsmith.lab2.Lab2;

import java.net.URISyntaxException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        long aExp = 2;
//        long xExp = 301;
//        long pExp = 11;
//        System.out.println("Fast mod exponentiation: ");
//        System.out.println(aExp + "^" + xExp + " % " + pExp + " = " + Lab1.exponentiation(aExp, xExp, pExp));
//
//        long aEuclid = 28;
//        long bEuclid = 19;
//        System.out.println("\nEuclid algorithm:\n(" + aEuclid + "," + bEuclid + ") = " + Arrays.toString(Lab1.euclidAlgorithm(aEuclid, bEuclid)));
//
//        System.out.println("\nDiffie–Hellman:");
//        Lab1.diffy();
//
//        long yBabyStep = 9;
//        long pBabyStep = 23;
//        long aBabyStep = 2;
//
//        System.out.println("\nBabyStepGiantStep:");
//        System.out.println("y = " + yBabyStep + " a = " + aBabyStep + " p = " + pBabyStep + " => x = " + Lab1.babyStepGiantStep(yBabyStep, pBabyStep, aBabyStep));

            Lab2.shamir(100);

//        try {
//            Lab2.shamirCipher("text1.txt");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


    }
}
