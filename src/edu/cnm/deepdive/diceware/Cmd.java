package edu.cnm.deepdive.diceware;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cmd {

  private static final String DEFAULT_FILENAME = "eff_large_wordlist.txt";
  private static final int DEFAULT_NUMBER_WORDS = 6;

  public static void main(String[] args)
      throws FileNotFoundException {
    int numWords = DEFAULT_NUMBER_WORDS;
    String fileName = DEFAULT_FILENAME;
    if (args.length > 0) {
      fileName = args[0];
      if (args.length > 1) {
        numWords = Integer.parseInt(args[1]);
      }
    }
    String[] words = readWordList(fileName);
    Random rng = new SecureRandom();
    Generator generator = new Generator(words, rng);
    String[] passPhrase = generator.next(numWords);
    for (String word : passPhrase) {
      System.out.print(word + " ");
    }
  }

  private static String[] readWordList(String filename)
      throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filename))) {
      List<String> words = new LinkedList<>();
      scanner.useDelimiter("\\s*\\d+\\s+");
      while (scanner.hasNext("\\S+")) {
        String word = scanner.next("\\S+");
        words.add(word);
      }
      return words.toArray(new String[words.size()]);
    }
  }
}
