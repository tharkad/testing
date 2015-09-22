/*

	Deck.java

	A generic deck of Cards class

	Methods:
		public 			Deck(boolean bShuffle, boolean jokers)
		public void 	Shuffle()
		public void 	Collect()
		public Card 	TopDeck()
		public int 		getCardsLeft()
		public String	toString()
		private void 	Fill()

	Fields:
		private	int		nJokers;		// number of jokers in the deck?
		private Card[]	aryStack;		// the ordered list of cards
		private Card[]	aryDeck;		// the shuffled list of cards
		private	int		nCardsLeft = 0;	// the number of cards left in the deck
		
*/

public class Deck
{
/*
 *	Deck constructor
 */
	public Deck(boolean bShuffle, int numJokers)
	{
		nJokers = numJokers;
		
		aryDeck = new Card[52 + numJokers];
		aryStack = new Card[52 + numJokers];

		Fill();

		if (bShuffle)
			Shuffle();
	}

/*
 *	Shuffle
 *
 *	Shuffles the deck
 */
	public void Shuffle()
	{
		int		n = 0;
		int		cardCounter = 0;
		int		slot = 0;
		Card	cardStorage;
		int		nDeckSize;

		nDeckSize = 52 + nJokers;

		for (n = 0; n < 5; n++)
		{
			for (cardCounter = 0; cardCounter < nDeckSize; cardCounter++)
			{
				slot = (int)(Math.random() * (nDeckSize - 1));
				cardStorage = aryDeck[slot];
				aryDeck[slot] = aryDeck[cardCounter];
				aryDeck[cardCounter] = cardStorage;
			}
		}

		nCardsLeft = nDeckSize;
	}

/*
 *	Collect
 *
 *	Aranges the deck in the most recent order.  Used to "redeal."
 */
	public void Collect()
	{
		nCardsLeft = 52 + nJokers;;
	}

/*
 *	TopDeck
 *
 *	Returns the top card of the deck.
 */
	public Card TopDeck()
	{
		Card	topCard;
		int		nDeckSize;

		nDeckSize = 52 + nJokers;

		if (nCardsLeft <= 0)
			return(null);
		
		topCard = aryDeck[nDeckSize - nCardsLeft];
		nCardsLeft--;

		return(topCard);
	}

/*
 *	getCardsLeft
 *
 *	Accessor for the nCardsLeft field
 */
	public int getCardsLeft()
	{
		return nCardsLeft;
	}

/*
 *	toString
 *	
 *	Overrides toString method.  Returns string representation
 *		of a Card object.
 */
	public String toString()
	{
		String	str;
		int		i;	
		int		nDeckSize;

		nDeckSize = 52 + nJokers;

		str = nCardsLeft + " Cards Left:  ";

		for (i = 0; i < nDeckSize; i++)
			str = str + (" " + aryDeck[i]);

		return str;
	}

/*
 *	Fill
 *
 *	Private method called by the consructor to fill the stack and deck
 */
	private void Fill()
	{
		int suit;
		int value;
		int index = 0;

		for (suit = Card.SPADE; suit <= Card.CLUB; suit++)
			for (value = Card.ACE; value <= Card.KING; value++)
			{
				aryStack[index] = new Card(value, suit);
				aryDeck[index] = aryStack[index];

				index++;
			}

		for (index = 0; index < nJokers; index++)
		{
			aryStack[52 + index] = new Card(Card.JOKER, Card.JOKER);
			aryDeck[52 + index] = aryStack[52 + index];
			nCardsLeft = 54;
		}
		
		nCardsLeft = 52 + nJokers;
	}

/*
 *	fields
 */
	private	int		nJokers = 0;	// are Jokers included in the deck?
	private Card[]	aryStack;		// the ordered list of cards
	private Card[]	aryDeck;		// the shuffled list of cards
	private	int		nCardsLeft = 0;	// the number of cards left in the deck

/*
 *	main
 *
 *	Test app
 */
	public static void main(String[] args)
	{
		Deck	deck;
		Card	cardCard;
		Card[]	aryCards;

		deck = new Deck(true, 100);

		aryCards = new Card[5];

		aryCards[0] = deck.TopDeck();
		System.out.println(aryCards[0]);

		System.out.println(deck);

		cardCard = deck.TopDeck();
		System.out.println(cardCard);

		cardCard = deck.TopDeck();
		System.out.println(cardCard);

		System.out.println(deck.getCardsLeft());

		deck.Collect();

		System.out.println(deck.getCardsLeft());
	
		cardCard = deck.TopDeck();
		System.out.println(cardCard);

		cardCard = deck.TopDeck();
		System.out.println(cardCard);

		cardCard = deck.TopDeck();
		System.out.println(cardCard);
	}
}