package de.schelklingen2008.poker.model;

import java.util.ArrayList;
import java.util.List;

public class PatternChecker
{

    // private static final int PAIR = 2;
    // private static final int FOUR_OF_A_KIND = 8;
    private static final int ANZAHL       = 7;
    public int               counter      = 0;
    private List<Card>       cards;

    private int              pairCard1    = -1;
    private int              pairCard2    = -1;
    private int              straightCard = -1;

    public PatternChecker(List<Card> cards)
    {
        this.cards = cards;
    }

    public static List<Card> sort(List<Card> cardList)
    {
        // List<Card> newList = new ArrayList();
        for (int i = 0; i < ANZAHL - 1; i++)
        {
            for (int j = 0; j < ANZAHL - 1; j++)
            {
                if (cardList.get(j).greaterThan(cardList.get(j + 1)))
                {
                    Card card = new Card(0, 0);
                    card = cardList.get(j);
                    cardList.set(j, cardList.get(j + 1));
                    cardList.set(j + 1, card);

                }
            }
        }
        return cardList;
    }

    // public int numberOfPairs()
    // {
    // int pairCounter = 0;
    //
    // for (int i = 0; i < ANZAHL; i++)
    // {
    // counter = 1;
    // for (int j = i + 1; j < ANZAHL; j++)
    // {
    // if (cards.get(i).getValueInt() == cards.get(j).getValueInt())
    // {
    // counter++;
    // pairCounter++;
    //
    // }
    // }
    //
    // }
    //
    // return pairCounter;
    //
    // }

    public Pattern mehrlinge()
    {
        int[] mehrlingsCounter = new int[13];
        List<Pattern> mehrlingsList = new ArrayList();
        Pattern pattern = new Pattern(0, 0, 0);
        int pairCounter = 0;
        int tripleCounter = 0;

        for (int i = 0; i < 13; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (cards.get(j).getValueInt() == i) mehrlingsCounter[i]++;
            }
            if (mehrlingsCounter[i] > 1)
            {
                System.out.println("Mehrling der Art " + i + " Anzahl: " + mehrlingsCounter[i]);
                if (mehrlingsCounter[i] == 2) pairCounter++;
            }
        }
        System.out.println(pairCounter);

        return pattern;
    }

    public int getCount(int i)
    {
        int mehrlingsCounter = 0;
        for (int j = 0; j < 7; j++)
        {
            if (cards.get(j).getValueInt() == i) mehrlingsCounter++;
        }
        // if (mehrlingsCounter > 1)
        // {
        // System.out.println("Mehrling der Art " + i + " Anzahl: " + mehrlingsCounter);
        //
        // }

        return mehrlingsCounter;

    }

    public boolean isPair() // es muss zus�tzlich getestet werden, ob isFullHouse auch true ist
    {
        boolean pairFound = false;
        for (int i = 0; i < 13; i++)
        {
            if (getCount(i) == 2)
            {
                if (pairFound)
                    return false;
                else
                    pairCard1 = i;
                pairFound = true;
            }
        }
        return pairFound;

    }

    public boolean isTwoPair()
    {
        int pairCounter = 0;
        for (int i = 0; i < 13; i++)
        {
            if (getCount(i) == 2)
            {
                pairCounter++;
                if (pairCard1 == -1)
                    pairCard1 = i;
                else
                    pairCard2 = i;
            }
        }
        if (pairCounter == 2) return true; // falls es genau zwei P�rchen gibt
        return false;

    }

    public boolean isThreeOfAKind() // es muss zus�tzlich getestet werden, ob isFullHouse auch true ist
    {
        boolean toakFound = false; // toak: three of a kind
        for (int i = 0; i < 13; i++)
        {
            if (getCount(i) == 3)
            {
                if (toakFound)
                    return false;
                else
                {
                    pairCard2 = i;
                    toakFound = true;
                }
            }
        }
        return toakFound;

    }

    public boolean isFourOfAKind()
    {
        boolean foakFound = false; // foak: four of a kind
        for (int i = 0; i < 13; i++)
        {
            if (getCount(i) == 4)
            {
                if (foakFound)
                    return false;
                else
                {
                    pairCard1 = i;
                    foakFound = true;
                }

            }
        }
        return foakFound;

    }

    public boolean straight(int pos, int length)
    {

        if (pos + length - 1 > 7) return false;

        if (length == 1) return true;

        // falls zwei aufeinanderfolgende Karten die gleiche Zahl haben
        int thisValue = cards.get(pos).getValueInt();
        int nextValue = cards.get(pos + 1).getValueInt();
        // schauen ob die Stra�e danach weitergeht
        if (nextValue == thisValue && straight(pos + 1, length)) return true;

        // falls die aufeinanderfolgenden karten nicht die gleiche Zahl haben
        if (nextValue != thisValue + 1) return false;

        if (straight(pos + 1, length - 1)) return true;

        return false;
        // falls die n�chste Karte genau um eins h�her ist und es danach mit der Stra�e weitergeht
    }

    public boolean isStraight()
    {
        if (straight(0, 5))
        {
            straightCard = cards.get(0).getValueInt();
            return true;
        }
        if (straight(1, 5))
        {
            straightCard = cards.get(1).getValueInt();
            return true;
        }
        if (straight(2, 5))
        {
            straightCard = cards.get(2).getValueInt();
            return true;
        }
        return false;
    }

    public boolean is10Straight()
    {
        if (straight(8, 5)) return true;
        return false;
    }

    public boolean isFlush()
    {
        int[] flushCounter = new int[4];
        for (int i = 0; i < 4; i++)
        {
            // z�hle wie viele karten es mit der SuitNr. i gibt
            for (int j = 0; j < ANZAHL; j++)
            {
                if (cards.get(j).getSuitInt() == i) flushCounter[i]++;
            }
            if (flushCounter[i] >= 5)
            {
                // System.out.println("Flush mit der Farbe" + i);
                return true;
            }

        }
        return false;
    }

    public boolean isFullHouse()
    {
        if (isPair() && isThreeOfAKind()) return true;
        return false;
    }

    public boolean isStraightFlush()
    {
        if (isStraight() && isFlush()) return true;
        return false;
    }

    public boolean isRoyalFlush()
    {
        if (isStraightFlush() && is10Straight()) return true;
        return false;
    }

    public Pattern getHighestPattern()
    {
        int i = 0;
        int val1 = -1;
        int val2 = -1;

        if (isPair())
        {
            i = 1;
            val1 = pairCard1;
        }
        if (isTwoPair())
        {
            i = 2;
            if (pairCard1 >= pairCard2)
            {
                val1 = pairCard1;
                val2 = pairCard2;
            }
            else
            {
                val1 = pairCard2;
                val2 = pairCard1;
            }
        }
        if (isThreeOfAKind())
        {
            i = 3;
            val1 = pairCard2;
        }
        if (isStraight())
        {
            i = 4;
            val1 = straightCard;
        }
        if (isFlush()) i = 5;
        if (isFullHouse())
        {
            i = 6;
            val1 = pairCard2; // Kartenart des Drillings
            val2 = pairCard1; // Kartenart des P�rchens
        }
        if (isFourOfAKind())
        {
            i = 7;
            val1 = pairCard1;
        }
        if (isStraightFlush())
        {
            i = 8;
            val1 = straightCard;
        }
        if (isRoyalFlush()) i = 9;

        Pattern pattern = new Pattern(i, val1, val2);

        return pattern;

    }

    public int getPairCard1()
    {
        return pairCard1;
    }

    public int getPairCard2()
    {
        return pairCard2;
    }

    public int getStraightCard()
    {
        return straightCard;
    }

}
