package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * @author WrightWJ
 * The Class Hangman.
 */
public class Hangman 
{
	
	/** The length. */
	private int length;
	
	/** The num guess. */
	private int numGuess;
	
	/** The guessed. */
	private ArrayList<Character> guessed;
	
	/** The input. */
	private static ArrayList<String> input;
	
	/** The out. */
	private char[] out;
	
	/** The game guess. */
	private String gameGuess = "";
	
	/** The cheat. */
	public static boolean cheat;
	
	/** The end. */
	private boolean end;

	//create new hangman game with default values
	/**
	 * Instantiates a new hangman.
	 */
	//no cheat, empty arraylists.
	public Hangman() {  
		guessed = new ArrayList<Character>();
		input = new ArrayList<String>();
		cheat = false;
	}

	/**
	 * pre: number must be greater than 0 or less than max guesses
	 * post: will set guesses properly.
	 *
	 * @param guess the new guesses
	 */
	public void setGuesses(int guess) {
		numGuess = guess;
	}
	
	/**
	 * Gets the guesses.
	 *
	 * @return the guesses
	 */
	public int getGuesses() {
		return numGuess;
	}
	
	/**
	 * pre: must pass a length that is between 0 and 137
	 * post: will set proper length.
	 *
	 * @param l the new length
	 */
	public void setLength(int l) {
		length = l;
		out = new char[length];
		for (int i =0;i<length;i++) {
			out[i]='_';
		}
		loadWords();
	}
	
	/**
	 * pre: none
	 * post: will return length of the word.
	 *
	 * @return length of the word
	 */
	public int getLength() {
		return length;
	}
	/**
	 * pre: file must exist
	 * post: will read file and add correct numbers to arraylist
	 * adds all words of a certain length to an arraylist.
	 */
	// sorts through the file and gets all words of a specified length.
	private void loadWords() {
		try 
		{
			Scanner sc = new Scanner(new FileReader(new File("dictionary.txt")));
			String next;
			while (sc.hasNext()) 
			{
				//if next line is == length, add it to list
				next = sc.nextLine();
				if (next.length()>length) 
				{
					input.add(next);
				}
			}
			sc.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Something wrong happened, exiting program.");
			System.exit(1);
		}
	}

	//checks if string/char has been guessed before
	/**
	 * Check.
	 *
	 * @param guess the guess the user input
	 * @return true, if successful
	 */
	//returns true if it is a valid char.
	public boolean check(String guess) {
		if (guess.length()>1 || guess.length()<1 || guessed.contains(guess.charAt(0)) || !Character.isLetter(guess.charAt(0))) {
			return false;
		}
		guessed.add(guess.charAt(0));
		addHash(guess.charAt(0));
		numGuess--;
		return true;
	}
	
	/**
	 * Adds to the hash table
	 *
	 * @param guess the user's guess
	 */
	//create map using key and places the char accordingly.
	private void addHash(char guess) {
		String tmp = new String(out);
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		//parse words to get keys
		//I got a lot of help here, Hashes confuse me a little still.
		for (String s:input) 
		{
			char[] key = new char[length];
			for (int i = 0;i<length;i++) 
			{
				if (s.charAt(i)==guess) 
				{
					key[i]=guess;
				}
				else
				{
					key[i]=out[i];
				}

			}
			String keys = new String(key);
			addWords(keys,s,map);
		}
		for (String keys: map.keySet()) {
			if (!map.keySet().contains(tmp)) {
				tmp=keys;
			}
			else
			{
				if (map.get(keys).size() > map.get(tmp).size()) {
					tmp = keys;
				}
			}
		}
		input = map.get(tmp);
		if (numGuess == 1) {
			if (map.keySet().contains(tmp)) {
				input = new ArrayList<>(map.get(tmp));
				gameGuess = map.get(tmp).get(0);
			}
			end = true;
		}
		//set output
		for (int i = 0; i<length;i++) {
			out[i]=tmp.charAt(i);
		}
		if (input.size()==1) {
			gameGuess=input.get(0);
		}
	}
	
	/**
	 * Adds the words to the hashmap.
	 *
	 * @param keys the key for the given value
	 * @param s the string to add
	 * @param map the map
	 */
	//adds words to hashmaps
	private void addWords(String keys, String s, HashMap<String, ArrayList<String>> map) {
		if (map.get(keys)==null) {
			map.put(keys, new ArrayList<>());
		}
		map.get(keys).add(s);
	}
	
	/**
	 * Game over.
	 *
	 * @return true, if game is over
	 */
	public boolean gameOver() {
		String tmp = new String(out);
		if (end) {
			return true;
		}
		if (gameGuess.equals(tmp)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Reset.
	 */
	public void reset() {
		end = false;
		guessed.clear();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Guesses remaining: " + numGuess+""
				+ "\nLetters guessed: ");
		for (char c: guessed) {
			sb.append(c + ", ");
		}
		if (cheat) {
			sb.append("Number of words left in map: "+ input.size());
		}
		if (end) {
			sb.append("The final word is: "+gameGuess);
		}
		return sb.toString();
	}
}
