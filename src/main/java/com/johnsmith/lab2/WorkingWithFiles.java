package com.johnsmith.lab2;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkingWithFiles {

    public static byte[] readFileToByteArray(String fileName) throws URISyntaxException {
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

    public static List<Long> readFileToArrayOfLong(String fileName) {


        List<Long> longList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                longList.add(Long.valueOf(scanner.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return longList;

    }

    public static List<String> readFileToArrayOfString(String fileName) {

        List<String> codeList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                codeList.add(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return codeList;
    }

    public static void writeFileByte(byte[] data, String fileName) {
        File file = new File(fileName);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeFileString(List<String> code, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String l : code) {
                writer.write(l);
                writer.write('\n');
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeFileLong(List<Long> code, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (Long l : code) {
                writer.write(String.valueOf(l));
                writer.write('\n');
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
