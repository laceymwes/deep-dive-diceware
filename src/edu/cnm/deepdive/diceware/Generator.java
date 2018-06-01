package edu.cnm.deepdive.diceware;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Generates user defined number of randomly selected words from a user provided array of words.
 */
public class Generator {

  private static final String NEGATIVE_NUMBER_WORDS_MESSAGE = "Number of words to be selected must be more than zero.";
  private static final String NULL_RNG_MESSAGE = "Random number generator must not be null.";
  private static final String NULL_WORDS_MESSAGE = "Array of words must not be null.";
  private static final String EMPTY_WORDS_MESSAGE = "Array of words must not be empty.";
  private static final String INSUFFICIENT_DISTINCT_WORDS_MESSAGE = "Number of distinct words must not exceed number of words in pool.";

  private String[] words;
  private Random rng;

  /**
   * Initializes {@link #words words} and {@link #rng rng}
   *
   * @param words array of words to be used in random selection.
   * @param rng generates random number for words array element selection.
   * @throws NullPointerException words or rng parameter objects must be properly initialized.
   * @throws IllegalArgumentException words array must be populated.
   */
  public Generator(String[] words, Random rng)
      throws NullPointerException, IllegalArgumentException {
    if (rng == null) {
      throw new NullPointerException(NULL_RNG_MESSAGE);
    }
    if (words == null) {
      throw new NullPointerException(NULL_WORDS_MESSAGE);
    }
    if (words.length == 0) {
      throw new IllegalArgumentException(EMPTY_WORDS_MESSAGE);
    }
    Set<String> pool = new HashSet<>();
    for (String word : words) {
      word = word.toLowerCase();
      if (!pool.contains(word)) {
        pool.add(word);
      }
    }
    this.words = pool.toArray(new String[pool.size()]); // copy array to leave original un-modified
    this.rng = rng; // same ^
  }

  /**
   * Supplies one randomly generated word selected from {@link #words words}.
   *
   * @return  one randomly selected word from {@link #words words} array.
   * @throws NegativeArraySizeException number of requested words must not exceed size of words array.
   */
  public String next()
      throws NegativeArraySizeException{
    return words[rng.nextInt(words.length)];
  }

  /**
   * Randomly selects and returns numWords from {@link #words words}.
   *
   * @param numWords number of randomly selected words to be returned.
   * @param duplicatesAllowed duplicate word selections are, or are not allowed.
   * @return returns array of randomly selected words.
   * @throws NegativeArraySizeException number of words requested must be a valid number.
   * @throws IllegalArgumentException number of random words requested must not be larger than the words array provided.
   */
  public String[] next(int numWords, boolean duplicatesAllowed)
      throws NegativeArraySizeException, IllegalArgumentException {
    if (numWords < 0) {
      throw new NegativeArraySizeException(NEGATIVE_NUMBER_WORDS_MESSAGE);
    }
    if (!duplicatesAllowed && numWords > words.length) {
      throw new IllegalArgumentException(
          INSUFFICIENT_DISTINCT_WORDS_MESSAGE);
    }
    List<String> selection = new LinkedList<>();
    while (selection.size() < numWords) {
      String pick = next();
      if (duplicatesAllowed || !selection.contains(pick)) {
        selection.add(pick);
      }
    }
    return selection.toArray(new String[selection.size()]);
  }

  /**
   * Randomly selects and returns numWords from {@link #words words}.
   *
   * @param numWords number of randomly selected words to be returned.
   * @return invokes {@link #next(int, boolean) next()}  method, and assumes duplicates are allowed.
   */
  public String[] next(int numWords) {
    return next(numWords, true); // assumes duplicatesAllowed is true
  }
}
