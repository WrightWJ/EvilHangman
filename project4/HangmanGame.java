package project4;

import javax.swing.JOptionPane;

/**
 * @author WrightWJ
 * The Class HangmanGame.
 */
public class HangmanGame {
	
	/**
	 * The main method.
	 *	gets user input and calls Hangman accordingly.
	 */
	public static void main(String[] args) {
		Hangman game = new Hangman();
		String guess;
		boolean done = false;

		game.setGuesses(Integer.parseInt(JOptionPane.showInputDialog("Input number of guessesyou want\nmore than 0, less than 26")));
		game.setLength(Integer.parseInt(JOptionPane.showInputDialog("Input how long you want the word to be\nenter less than 127, but greater than 0.")));
		Hangman.cheat = Boolean.parseBoolean(JOptionPane.showInputDialog("Enter true to see guesses remaining\nfalse to play fairly"));
		do 
		{
			//loop for the letter guess
			do 
			{
				guess = JOptionPane.showInputDialog("Enter your letter guess");
			}while(!game.check(guess));
			//is finished/is won
			if(game.gameOver()) 
			{
				done=true;
				System.out.println("Congrats, you guessed correctly");
			}
			else if(game.getGuesses()==0)
			{
				done=true;
				System.out.println("Sorry, you lost");
			}
			else 
			{
				System.out.println("Guess again");
			}
			System.out.println(game.toString());
		}
		while(!done); 


	}
}
