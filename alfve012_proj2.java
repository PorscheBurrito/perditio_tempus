import java.util.Random;

//  CARD. A playing card. It's immutable.

final class Card
{

//  RANK NAME. Printable names of card ranks. We don't use index 0.

  private static final String[] rankName =
  {
    "",        //   0
    "ace",     //   1
    "two",     //   2
    "three",   //   3
    "four",    //   4
    "five",    //   5
    "six",     //   6
    "seven",   //   7
    "eight",   //   8
    "nine",    //   9
    "ten",     //  10
    "jack",    //  11
    "queen",   //  12
    "king"     //  13
  };

  private int rank;  //  Card rank, between 1 and 13.

//  CARD. Constructor. Make a new CARD with a given RANK.

  public Card(int rank)
  {
    if (1 <= rank && rank <= 13)
    {
      this.rank = rank;
    }
    else
    {
      throw new IllegalArgumentException("Illegal rank.");
    }
  }

//  GET RANK. Return the RANK of this CARD.

  public int getRank()
  {
    return rank;
  }

//  TO STRING. Return a STRING that describes this CARD, for printing only.

  public String toString()
  {
    return rankName[rank];
  }
}

class Deck
{
	private int card = 0; // Shows how many cards dealt
	private Card[] deck= new Card[53];
	public Deck()
	{
		for(int j=0; j<52; j+=13)
		{
			for(int i=1; i <= 13; i++)
			{
				deck[i+j] = new Card(i);
			}
		}
	}
	public Card deal()
	{
		if(card==53) 
			throw new IllegalStateException("No more cards remain to be dealt.");
		else
		{
			card++;
			return deck[card];
		}
	}
	public void shuffle()
	{
		if(card>0)
			throw new IllegalStateException("Cards already dealt.");
		else
		{
			for(int i=deck.length-1; i > 0; i--)
			{
				Random k = new Random();
				int j = Math.abs(k.nextInt())%(i)+1;
				Card temp = deck[i];
				deck[i] = deck[j];
				deck[j] = temp;
			}
		}
	}
}

class Pile
{
	private class Layer
	{
		private Card card;
		private Layer next;
		private Layer(Card card, Layer next)
		{
			this.card = card;
			this.next = next;
		}
	}
	private Layer top;
	public Pile()
	{
		top = null;
	}
	public void add(Card card)
	{
		top = new Layer(card, top);
	}
	public Card turn()
	{
		if(isEmpty())
			throw new IllegalStateException("Pile is empty.");
		else
		{
			Layer temp = top;
			top = top.next;
			return temp.card;
		}
	}
	public boolean isEmpty()
	{
		return top == null;
	}
}

class Tableau
{
	Pile[] pile = new Pile[14];
	Deck deck = new Deck();
	public Tableau()
	{
		deck.shuffle();
		for(int i=1; i < 14; i++)
		{
			pile[i] = new Pile();
			for(int j=1; j<5; j++)
			{
				pile[i].add(deck.deal());
			}
		}
	}
	private boolean hasWon()
	{
		for(int i = 1; i < 14; i++)
		{
			if(!pile[i].isEmpty())
				return false;
		}
		return true;
	}
	public void play()
	{
		int p = 1;
		while(!pile[p].isEmpty())
		{
			Card temp = pile[p].turn();
			System.out.print("Got ");
			System.out.print(temp.toString());
			System.out.print(" from pile ");
			System.out.print(p);
			System.out.println(".");
			p = temp.getRank();
		}
		if(hasWon())
			System.out.println("You won!");
		else // if (pile[p].isEmpty && !hasWon())
		{
			System.out.print("Pile ");
			System.out.print(p);
			System.out.println(" is empty. You lost!");
		}
	}
}
class Perdito // main
{
	public static void main(String [] args)
	{
		Tableau x = new Tableau();
		x.play();
	}
}
