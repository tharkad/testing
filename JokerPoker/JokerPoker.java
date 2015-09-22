/*

	JokerPoker.java

	A class that holds the main JokerPoker app. 

	Methods:
		main
*/

import java.io.*;

public class JokerPoker
{
/*
 *	main
 */
	public static void main(String[] args) throws IOException
	{
		PokerSlots	slots;
		int			nAccount = 1000;
		boolean		bQuit = false;
		int			nLastBet = 10;
		int			nBet = 0;
		Integer		oBet;
		String		prompt;
		String		word;
		boolean		bGotBet;
		boolean		bGotHolds;
		String		holdErrorString;
		boolean[]	holdSlots;
		String		holdSubString;
		int			nHandType;
		int			nHandValue;

		holdErrorString = "\nWhat?  Enter the cards you want to hold or \"h\" for help or \"q\" to quit.";
 		holdSlots = new boolean[5];
		
		slots = new PokerSlots(true);

		System.out.println("Welcome to JokerPoker!  My first Java App.  By Mike Houser - 10/1/98\nThe game is 5 card draw.  You may draw up to 5 cards each hand.\nEnter \"h\" at anytime for a payout list or \"q\" to quit.\n\nHere's $1000.  Enjoy the game!");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
// The main game loop, handles a hand.
	gameLoop:
		while (true)
		{
			slots.NewHand();

		// bet loop
			bGotBet = false;
			while (!bGotBet)
			{
				System.out.print("\nYou have $" + nAccount);

			// display place your bet and get bet
				prompt = "\n" + "Place your bet [" + nLastBet + "] ";
				System.out.print(prompt + ":");
//				word = Console.readString(prompt);
				word = br.readLine();
				
				if (word.equals(""))
				{
					nBet = nLastBet;
					bGotBet = true;
				}
				else
				{
					try
					{
						oBet = Integer.valueOf(word);
						if (oBet.intValue() > nAccount)
							System.out.println("\nYou don't have that much money!");
						else
						{
							nBet = oBet.intValue();
							bGotBet = true;
						}
					}
					catch (NumberFormatException e)
					{
						word.toLowerCase();
						if (word.startsWith("h"))
							System.out.println("\n" + slots.HandHelp());
						else if (word.startsWith("q"))
							break gameLoop;
						else 
							System.out.println("\nWhat?  Enter your bet or \"h\" for help or \"q\" to quit.");
					}
				}
			}

			nLastBet = nBet;
			
		// hold loop
			bGotHolds = false;
			while (!bGotHolds)
			{
				for (int i = 0; i < 5; i++)
					holdSlots[i] = false;

				System.out.println("\n\n" + slots);
				nHandType = slots.HandType();
				if (nHandType != PokerSlots.kLoser)
					System.out.println(slots.HandString(nHandType));

				prompt ="\nPick cards to hold by entering their slot number (e.g. 134)\n  or press enter to draw [draw]";
				System.out.print(prompt + ":");
//				word = Console.readString(prompt);
				word = br.readLine();

				if (word.equals(""))
				{
					bGotHolds = true;
				}
				else
				{
					try
					{
						oBet = Integer.valueOf(word);
					
					// hold analysis code
					holdAnalyzeLoop:
						for (int i = 0; i < word.length(); i++)
						{
							holdSubString = word.substring(i, i + 1);
							if (holdSubString.equals("1"))
								holdSlots[0] = true;
							else if (holdSubString.equals("2"))
								holdSlots[1] = true;
							else if (holdSubString.equals("3"))
								holdSlots[2] = true;
							else if (holdSubString.equals("4"))
								holdSlots[3] = true;
							else if (holdSubString.equals("5"))
								holdSlots[4] = true;
							else
							{
								System.out.println(holdErrorString);
								break holdAnalyzeLoop;
							}
						}

						slots.ToggleHold(holdSlots[0], holdSlots[1], holdSlots[2], 
								holdSlots[3], holdSlots[4]);
					}
					catch (NumberFormatException e)
					{
						word.toLowerCase();
						if (word.startsWith("h"))
							System.out.println("\n" + slots.HandHelp());
						else if (word.startsWith("q"))
							break gameLoop;
						else 
							System.out.println(holdErrorString);
					}
				}
			}

		//end of hand
			slots.DrawCards();
			System.out.println("\n\n" + slots + "\n");
			
			nHandType = slots.HandType();
			nHandValue = slots.HandValue(nHandType);
			System.out.println(slots.HandString(nHandType));
			if (nHandType != PokerSlots.kLoser)
				System.out.println("You won $" + (nHandValue * nBet) + "!\n\n");

			nAccount = nAccount + ((nHandValue * nBet) - nBet);
			
			if (nAccount <= 0)
				break gameLoop;
			
			if (nLastBet > nAccount)
				nLastBet = nAccount;
		}

		System.out.println("\nYou ended up with $" + nAccount);
		System.out.println("\nThanks for playing!\n\n");
	}
}