package analyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class Pattern {
    final int priority;
    final String pattern;
    final String resultString;

    public Pattern(int priority, String pattern, String resultString) {
        this.priority = priority;
        this.pattern = pattern;
        this.resultString = resultString;
    }

    @Override
    public String toString() {
        return pattern;
    }

    public int getPriority() {
        return priority;
    }
}

public class Main {

    public static void main(String[] args) {

        String folderPath = args[0];
        String dataBaseName = args[1];

        List<Pattern> patternsFromDB = new ArrayList<>();

        try (Scanner dataBaseScanner = new Scanner(new File(dataBaseName))) {
            while (dataBaseScanner.hasNextLine()) {
                String[] lineData = dataBaseScanner.nextLine().split(";");
                int priority = Integer.parseInt(lineData[0]);
                String pattern = lineData[1];
                String resultString = lineData[2];
                patternsFromDB.add(new Pattern(priority, pattern, resultString));
            }
        } catch (IOException ignored) {
        }

        patternsFromDB.sort(new PatternPriorityComparator());

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Future<String>> futureList = new ArrayList<>();
        assert files != null;
        for (File file :
                files) {
            try (InputStream inputStream = new FileInputStream(file)) {
                byte[] allBytes = inputStream.readAllBytes();
                String data = new String(allBytes);
                System.out.println(data);
                Future<String> future = executor.submit(() -> {
                    for (Pattern pattern :
                            patternsFromDB) {
                        String currentPattern = pattern.pattern;
                        String resultString = pattern.resultString.replaceAll("\"", "");
                        currentPattern = currentPattern.replaceAll("\"", "");

                        if (RabinKarpSearcher.search(currentPattern, data)) {
                            return file.getName() + ": " + resultString;
                        }
                    }
                    return file.getName() + ": Unknown file type";
                });
                futureList.add(future);
            } catch (IOException ignored) {
            }
        }

        for (Future<String> future :
                futureList) {
            try {
                System.out.println(future.get());
            } catch (Exception ignored) {
            }
        }
    }
}

