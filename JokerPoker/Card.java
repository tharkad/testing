/*

	Card.java

	A generic card class

	Methods:
		public 			Card(int v, int s)
		public int		getValue()
		public int		getSuit()
		public String	toString()

	Fields:
		private int		value
		private int		suit

*/

public class Card
{
/*
 * Class constants
 */
	public static final int ACE = 1;
	public static final int	JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int SPADE = 1;
	public static final int HEART = 2;
	public static final int DIAMOND = 3;
	public static final int CLUB = 4;
	public static final int JOKER = 15;

	
/* 
 *	Card constructor
 */
	public Card(int v, int s)
	{
		value = v;
		suit = s;
	}


/*
 *	getValue
 *	
 *	value field accessor
 */
	public int getValue()
	{
		return value;
	}


/*
 *	getSuit
 *	
 *	suit field accessor
 */
	public int getSuit()
	{
		return suit;
	}


/*
 *	toString
 *	
 *	Overrides toString method.  Returns string representation
 *		of a Card object.
 */
	public String toString()
	{  
		String strValue;
		String strSuit;

		switch (value)
		{
			case ACE:
				strValue = "A";
				break;

			case JACK:
				strValue = "J";
				break;

			case QUEEN:
				strValue = "Q";
				break;

			case KING:
				strValue = "K";
				break;

			case JOKER:
				strValue = "j";
				break;

			default:
				strValue = String.valueOf(value);
		}

		switch (suit)
		{
			case SPADE:
				strSuit = "S";
				break;

			case HEART:
				strSuit = "H";
				break;

			case DIAMOND:
				strSuit = "D";
				break;

			case CLUB:
				strSuit = "C";
				break;

			case JOKER:
				strSuit = "o";
				break;

			default:
				strSuit = String.valueOf(suit);
		}

		return strValue + strSuit;
	}


/*
 *	fields
 */
	private int value = 0;
	private int suit = 0;


/*
 *	main
 *
 *	Test app
 */
	public static void main(String[] args)
	{
		Card cdA;
		Card cdB;

		cdA = new Card(ACE, CLUB);
		cdB = new Card(8, DIAMOND);

		System.out.println(cdA);
		System.out.println(cdB);

		if (cdA.getSuit() == CLUB)
			System.out.println("CLUB");
		else
			System.out.println("Not CLUB");

		if (cdB.getSuit() == CLUB)
			System.out.println("CLUB");
		else
			System.out.println("Not CLUB");

		if (cdA.getValue() == ACE)
			System.out.println("ACE");
		else
			System.out.println("Not ACE");

		if (cdB.getValue() == ACE)
			System.out.println("ACE");
		else
			System.out.println("Not ACE");
	}

}