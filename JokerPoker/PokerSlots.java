/*

	PokerSlots.java

	A class that handles the "slots", that is, the cards that are displayed in a 
	JokerPoker game.  It also handles the scoring logic and the "hold cards" game 
	interaction. This class owns the one and only Deck object. 

	Methods:
		public 			PokerSlots(boolean bJokers)
		public void		NewHand()
		public void		ToggleHold(boolean first, boolean second, boolean third,
									boolean forth, boolean fifth)
		public void		DrawCards()
		public String	toString()
		public int		HandType()
		public int		HandValue(int type)
		public String	HandString(int type)
		public String	HandHelp()
		private boolean	IsFiveOfAKind()
		private boolean	IsFourOfAKind()
		private boolean	IsRoyalFlush()
		private boolean	IsStraightFlush()
		private boolean	IsFullHouse()
		private boolean	IsFlush()
		private boolean	IsStraight()
		private boolean	IsThreeOfAKind()
		private boolean	IsTwoPair()
		private boolean	IsPair()
		private int		NumJokers()
		private	void	CardSort(Card[] cards)

	Fields:
		private Card[]		aryHand
		private boolean[]	aryHold
		private Deck		deck
*/


public class PokerSlots
{
/*
 * Class constants
 */
	// Hand Types
		public static final int kLoser = 0;
		public static final int kPair = 1;
		public static final int	kTwoPair = 2;
		public static final int kThreeOfAKind = 3;
		public static final int kStraight = 4;
		public static final int kFlush = 5;
		public static final int kFullHouse = 6;
		public static final int kStraightFlush = 7;
		public static final int kRoyalFlush = 8;
		public static final int kFourOfAKind = 9;
		public static final int kFiveOfAKind = 10;

/* 
 *	PokerSlots constructor
 */
	public PokerSlots(boolean bJokers)
	{
		int	i;

		aryHand = new Card[5];
		aryHold = new boolean[5];

		deck = new Deck(true, bJokers);
		
		for	(i = 0; i < 5; i++)
		{								 
			aryHand[i] = null;
			aryHold[i] = true;
		}
	}

/*
 *	NewHand
 *	
 *	Shuffles the Deck and draws five cards.  Sets all of the Hold switches to off.
 */
	public void	NewHand()
	{
		int	i;
		
		deck.Shuffle();

		for (i = 0; i < 5; i++)
		{
			aryHand[i] = deck.TopDeck();
			aryHold[i] = false;
		}

/* !!!!! Test Code !!!!! */
/*
		aryHand[0] = new Card(2, Card.DIAMOND);
	//	aryHand[1] = new Card(1, Card.CLUB);
		aryHand[1] = new Card(Card.JOKER, Card.JOKER);
	//	aryHand[2] = new Card(Card.JOKER, Card.JOKER);
		aryHand[2] = new Card(6, Card.CLUB);
		aryHand[3] = new Card(8, Card.SPADE);
		aryHand[4] = new Card(Card.JACK, Card.CLUB);
*/
	}

/*
 *	ToggleHold
 *	
 *	Toggles the hold switch for all the given slots.
 */
	public void	ToggleHold(boolean first, boolean second, boolean third,
							boolean forth, boolean fifth)
	{
		if (first)
			aryHold[0] = !aryHold[0];

		if (second)
			aryHold[1] = !aryHold[1];

		if (third)
			aryHold[2] = !aryHold[2];

		if (forth)
			aryHold[3] = !aryHold[3];

		if (fifth)
			aryHold[4] = !aryHold[4];
	}
	
/*
 *	DrawCards
 *	
 *	Draws cards for all slots not marked for holding.
 */
	public void	DrawCards()
	{
		int i;

		for (i = 0; i < 5; i++)
		{
			if (!aryHold[i])
				aryHand[i] = deck.TopDeck();

			aryHold[i] = false;
		}
	}
	
/*
 *	toString
 *	
 *	Overrides toString method.  Returns string representation
 *		of a PokerSlots object.	 This is used by the JokerPoker app to
 *		display the hand on the console.
 */
	public String toString()
	{
		String	str;
		int		i;

		str = "";

		str = str + "---------------\n";
		
		for (i = 0; i < 5; i++)
		{
			str = str + (aryHand[i] + " ");
		}

		str = str + "\n";

		for (i = 0; i < 5; i++)
		{
			if (aryHold[i])
				str = str + " H ";
			else
				str = str + "   ";

			if (aryHand[i].getValue() == 10)
				str = str + " ";
		}

		str = str + "\n---------------";

		return(str);		
	}
	
/*
 *	HandType
 *	
 *	Returns the type of the best poker hand to be made from the Cards in aryHand.
 */
	public int HandType()
	{
		if (IsFiveOfAKind())
			return kFiveOfAKind;
		else if (IsFourOfAKind())
			return kFourOfAKind;
		else if (IsRoyalFlush())
			return kRoyalFlush;
		else if (IsStraightFlush())
			return kStraightFlush;
		else if (IsFullHouse())
			return kFullHouse;
		else if (IsFlush())
			return kFlush;
		else if (IsStraight())
			return kStraight;
		else if (IsThreeOfAKind())
			return kThreeOfAKind;
		else if (IsTwoPair())
			return kTwoPair;
		else if (IsPair())
			return kPair;
		else 
			return kLoser;
	}

/*
 *	HandValue
 *	
 *	Returns the dollar value of the given hand type based on $1 being bet.
 */
	public int HandValue(int type)
	{
		int nValue = 0;

		switch (type)
		{
			case kLoser:
				nValue = 0;
				break;

			case kPair:
				nValue = 1;
				break;

			case kTwoPair:
				nValue = 2;
				break;

			case kThreeOfAKind:
				nValue = 3;
				break;

			case kStraight:
				nValue = 5;
				break;

			case kFlush:
				nValue = 8;
				break;

			case kFullHouse:
				nValue = 10;
				break;						  

			case kStraightFlush:
				nValue = 30;
				break;

			case kRoyalFlush:
				nValue = 40;
				break;

			case kFourOfAKind:
				nValue = 50;
				break;

			case kFiveOfAKind:
				nValue = 100;
				break;
		}

		return nValue;
	}

/*
 *	HandString
 *	
 *	Returns the string representation of the given hand type.
 */
	public String HandString(int type)
	{
		String	str;

		str = "";

		switch (type)
		{
			case kLoser:
				str = "Not A Winner";
				break;

			case kPair:
				str = "Pair of Jacks or Better";
				break;

			case kTwoPair:
				str = "Two Pair";
				break;

			case kThreeOfAKind:
				str = "Three of a Kind";
				break;

			case kStraight:
				str = "Straight";
				break;

			case kFlush:
				str = "Flush";
				break;

			case kFullHouse:
				str = "Full House";
				break;						  

			case kStraightFlush:
				str = "Straight Flush!";
				break;

			case kRoyalFlush:
				str = "Royal Flush!!";
				break;

			case kFourOfAKind:
				str = "Four of a Kind!!!";
				break;

			case kFiveOfAKind:
				str = "Five of a Kind!!!!!";
				break;
		}
		
		return str;
	}

/*
 *	HandHelp
 *	
 *	Returns the dollar value of the given hand type based on $1 being bet.
 */
	public String HandHelp()
	{
		return ("100:1 - Five of a Kind\n" + " 50:1 - Four of a Kind\n" + 
				" 40:1 - Royal Flush\n" + " 30:1 - Straight Flush\n" +
				" 10:1 - Full House\n" + "  8:1 - Flush\n" + 
				"  5:1 - Straight\n" + "  3:1 - Three of a Kind\n" +
				"  2:1 - Two Pair\n" + "  1:1 - Pair of Jacks or Better");
	}

/*
 *	IsFiveOfAKind
 *	
 *	Determins whether aryHand is a Five Of A Kind.
 */
	private boolean	IsFiveOfAKind()
	{
		int	i = 0;
		int	iValue = 0;
		int	value1 = 0;

	// scan for any different value (can't be 5 of a kind)
		for (i = 0; i < 5; i++)
		{
			iValue = aryHand[i].getValue();

			if (iValue != Card.JOKER)
			{
				if (value1 == 0)
					value1 = iValue;
				else
					if (value1 != iValue)
						return false;
			}
		}

		return true;
	}

/*
 *	IsFourOfAKind
 *	
 *	Determins whether aryHand is a Four Of A Kind.
 */
	private boolean	IsFourOfAKind()
	{
		int		i = 0;
		int		j = 0;
		int		numJokers;
		Card[]	sortedCards = new Card[5];
		int		iValue = 0;
		int		value1 = 0;
		int		value1Count = 0;
		int		value2 = 0;
		int		value2Count = 0;

		numJokers = NumJokers();

 // bubble sort the hand
		for (i = 0; i < 5; i++)
			sortedCards[i] = aryHand[i];

		CardSort(sortedCards);

// check for values
		value1 = sortedCards[0].getValue();
		value1Count++;

		for (i = 1; i < 5 - numJokers; i++)
		{
			iValue = sortedCards[i].getValue();
			if (value1 == iValue)
				value1Count++;
			else
			{
				if (value2 == 0)
				{
					value2 = iValue;
					value2Count++;
				}
				else if (value2 != iValue)
					return false;
				else
					value2Count++;
			}
		}

		if (value1Count + numJokers >= 4)
			return true;
		else if (value2Count + numJokers >= 4)
			return true;

		return false;
	}

/*
 *	IsRoyalFlush
 *	
 *	Determins whether aryHand is a Royal Flush.
 */
	private boolean	IsRoyalFlush()
	{
		int	i = 0;
		int	iValue = 0;

	// scan for any value less then 10 - can't be a royal flush
		for (i = 0; i < 5; i++)
		{
			iValue = aryHand[i].getValue();

			if (iValue == Card.ACE) iValue = Card.KING + 1;
			
			if (iValue < 10)
				return false;
		}

		if (!(IsFlush() && IsStraight()))
			return false;
		else
			return true;
	}

/*
 *	IsStraightFlush
 *	
 *	Determins whether aryHand is a Straight Flush.
 */
	private boolean	IsStraightFlush()
	{
		return (IsFlush() && IsStraight());
	}

/*
 *	IsFullHouse
 *	
 *	Determins whether aryHand is a Full House.
 */
	private boolean	IsFullHouse()
	{
		int		i = 0;
		int		j = 0;
		int		iSuit = 0;
		int		jSuit = 0;
		int		numJokers;
		Card[]	sortedCards = new Card[5];
		int		iValue = 0;
		int		value1 = 0;
		int		value2 = 0;

		numJokers = NumJokers();

 // bubble sort the hand
		for (i = 0; i < 5; i++)
			sortedCards[i] = aryHand[i];

		CardSort(sortedCards);

// check for more than two values (can't be a full house)
		value1 = sortedCards[0].getValue();
		for (i = 1; i < 5 - numJokers; i++)
		{
			iValue = sortedCards[i].getValue();
			if (value1 != iValue)
			{
				if (value2 == 0)
					value2 = iValue;
				else if (value2 != iValue)
					return false;
			}
		}

		return true;
	}

/*
 *	IsFlush
 *	
 *	Determins whether aryHand is a Flush.
 */
	private boolean	IsFlush()
	{
		int		i = 0;
		int		j = 0;
		int		iSuit = 0;
		int		jSuit = 0;
		int		numJokers;
		Card[]	sortedCards = new Card[5];

		numJokers = NumJokers();

 // bubble sort the hand
		for (i = 0; i < 5; i++)
			sortedCards[i] = aryHand[i];

		CardSort(sortedCards);

// check for flush
		for (i = 0; i < 4 - numJokers; i++)
		{
			iSuit = sortedCards[i].getSuit();
			jSuit = sortedCards[i + 1].getSuit();

			if (iSuit != jSuit)
				return false;
		}

		return true;
	}

/*
 *	IsStraight
 *	
 *	Determins whether aryHand is a Straight.
 */
	private boolean	IsStraight()
	{
		int		i = 0;
		int		j = 0;
		int		iValue = 0;
		int		jValue = 0;
		int 	numJokers = 0;
		Card[]	sortedCards = new Card[5];
		int		varience = 0;	// counts how many hole are left 
								//	in the middle of a straight

		numJokers = NumJokers();

 // bubble sort the hand
		for (i = 0; i < 5; i ++)
			sortedCards[i] = aryHand[i];

		CardSort(sortedCards);

// check for straight
		for (i = 0; i < 4 - numJokers; i++)
		{
			iValue = sortedCards[i].getValue();
			jValue = sortedCards[i + 1].getValue();

			if (iValue == jValue)
				return false;

			if (iValue == Card.ACE) iValue = Card.KING + 1;
			if (jValue == Card.ACE) jValue = Card.KING + 1;
			
			varience += (jValue - 1) - iValue;
			
			if (varience > numJokers)
				return false;
		}
		
		return true;
	}

/*
 *	IsThreeOfAKind
 *	
 *	Determins whether aryHand is a Three Of A Kind.
 */
	private boolean	IsThreeOfAKind()
	{
		int	i = 0;
		int	j = 0;
		int k = 0;
		int numJokers = 0;

		numJokers = NumJokers();

		if (numJokers == 2)		// there must be 3 of a kind
			return true;

// scan for 3 of a kind
		for (i = 0; i < 4; i++)
		{
			for (j = i + 1; j < 5; j++)
			{
				if (aryHand[i].getValue() == aryHand[j].getValue()) // a pair
				{
					if (numJokers > 0)	// with a pair and a joker there must be 3
						return true;	//	of a kind

					for (k = j + 1; k < 5; k++)
					{
						if (aryHand[i].getValue() == aryHand[k].getValue()) // 3 of a kind
							return true;
					}
				}	
			}
		}
		
		return false;
	}

/*
 *	IsTwoPair
 *	
 *	Determins whether aryHand is a Two Pair.
 */
	private boolean	IsTwoPair()
	{
		int	i = 0;
		int	j = 0;
		int	numJokers = 0;
		int firstPairValue = 0;
		int jokerSlot = -1;

		numJokers = NumJokers();

		if (numJokers == 2)		// there must be two pair
			return true;

// scan for first pair - must be natural
	 firstPair:
	 	for (i = 0; i < 4; i++)
		{
			for (j = i + 1; j < 5; j++)
			{
				if (aryHand[i].getValue() == aryHand[j].getValue())
				{
					firstPairValue = aryHand[i].getValue();
					break firstPair;
				}
			}
		}

// if no natural pair found	there can't be two pair
		if (firstPairValue == 0)
			return false;

 // if a joker is in the hand there must be two pair
		if (numJokers > 0)
			return true;

// if a second natural pair is found there must be two pair
	 	for (i = 0; i < 4; i++)
		{
			if (aryHand[i].getValue() != firstPairValue)
			{
				for (j = i + 1; j < 5; j++)
				{
					if (aryHand[i].getValue() == aryHand[j].getValue())
					{
						// this is a second natural pair
						return true;
					}
				}
			}
		}

		return false;
	}

/*
 *	IsPair
 *	
 *	Determins whether aryHand is a Pair.
 */
	private boolean	IsPair()
	{
		int	i = 0;
		int	j = 0;
		int numJokers = 0;
		int	iValue = 0;
		Card[]	sortedCards = new Card[5];

		numJokers = NumJokers();

 // bubble sort the hand
		for (i = 0; i < 5; i ++)
			sortedCards[i] = aryHand[i];

		CardSort(sortedCards);

// scan for pairs
		for (i = 0; i < 5 - numJokers; i++)
		{
			iValue = sortedCards[i].getValue();
			if (iValue == Card.ACE) iValue = Card.KING + 1;
			if ((iValue >= Card.JACK) && (iValue < Card.JOKER))
			{
				if (numJokers > 0)	// there must be a pair of jacks or better
					return true;

				if (i > 4) break;

				for (j = i + 1; j < 5; j++)
				{
					if (sortedCards[i].getValue() == sortedCards[j].getValue())
						return true;
				}
			}
		}
		
		return false;
	}

/*
 *	NumJokers
 *	
 *	Returns the number of Jokers in the aryHand.
 */
	private int	NumJokers()
	{
		int	i = 0;
		int numJokers = 0;

		for (i = 0; i < 5; i++)
		{
			if (aryHand[i].getValue() == Card.JOKER)
				numJokers++;
		}

		return numJokers;
	}

/*
 *	CardSort
 *
 *	Bubble sorts the given array of card with K < A and A < Jo
 */
	private	void CardSort(Card[] cards)
	{
		int		i = 0;
		int		j = 0;
		int		iValue = 0;
		int		jValue = 0;
		Card	storageCard;
		
		for (i = 0; i < cards.length - 1; i++)
		{
			for (j = i + 1; j < cards.length; j++)
			{
				iValue = cards[i].getValue();
				jValue = cards[j].getValue();

				if (iValue == Card.ACE) iValue = Card.KING + 1;
				if (jValue == Card.ACE) jValue = Card.KING + 1;

				if (iValue > jValue)
				{
					storageCard = cards[i];
					cards[i] = cards[j];
					cards[j] = storageCard;
				}
			}
		}
	}

/*
 *	fields
 */
	private Card[]		aryHand;
	private boolean[]	aryHold;
	private Deck		deck;

/*
 *	main
 *
 *	Test app
 */
	public static void main(String[] args)
	{
		PokerSlots	slots;

		slots = new PokerSlots(true);

		slots.NewHand();
		System.out.println(slots);

/*
		slots.ToggleHold(true, true, true, true, true);
		System.out.println(slots);

		slots.ToggleHold(false, true, false, false, false);
		System.out.println(slots);

		slots.DrawCards();
		System.out.println(slots);
*/

		int type;

		type = slots.HandType();
		System.out.println(slots.HandValue(type));
		System.out.println(slots.HandString(type));

		System.out.println(slots);

		System.out.println(slots.HandHelp());
	}
}

 