package de.schelklingen2008.jipbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.schelklingen2008.jipbo.client.Constants;

/**
 * Maintains the rules and the state of the game.
 */
public class GameModel implements Serializable
{

    private Card[]     mBuildPile;
    private List<Card> mStockCards;
    private Player[]   mPlayers;

    private Player     turnHolder;

    public GameModel()
    {
        // test initialisation
        // mBuildPile = new Card[] { new Card(2), new Card(4), new Card(6), new Card(8), new Card(-1) };
        // List<Card> mStockPile = new ArrayList<Card>();
        // mStockPile.add(new Card(12));
        // mStockPile.add(new Card(0));
        // mStockPile.add(new Card(12));
        // mPlayers = new Player[] {
        // new Player("Ich", mStockPile, new Card[] { new Card(1), new Card(-2), new Card(12), new Card(10),
        // new Card(0) }, new Card[] { new Card(5), new Card(2), new Card(-2), new Card(10) }),
        // new Player("Fabio", mStockPile, new Card[] { new Card(3), new Card(2), new Card(11), new Card(0),
        // new Card(0) }, new Card[] { new Card(2), new Card(0), new Card(12), new Card(2) }),
        // new Player("Peter", mStockPile, new Card[] { new Card(1), new Card(0), new Card(12), new Card(10),
        // new Card(0) }, new Card[] { new Card(4), new Card(7), new Card(0), new Card(2) }),
        // new Player("Bulgarien", mStockPile, new Card[] { new Card(1), new Card(0), new Card(12), new
        // Card(10),
        // new Card(0) }, new Card[] { new Card(1), new Card(3), new Card(11), new Card(6) }) };
    }

    public void createGame(String[] pNames)
    {
        mStockCards = shuffleStockCards();

        mBuildPile = new Card[] { new Card(-2), new Card(-2), new Card(-2), new Card(-2), new Card(-1) };

        mPlayers = new Player[pNames.length];
        for (int i = 0; i < pNames.length; i++)
        {

            mPlayers[i] = new Player(pNames[i], shuffleStockPile(), null);
            if (i == 0)
            {
                refreshDrawPile(mPlayers[i]);
            }
        }
    }

    private List<Card> shuffleStockCards()
    {
        List<Card> rStockCards = new ArrayList<Card>();

        for (int i = 1; i <= 12; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                rStockCards.add(new Card(i));
            }

        }
        for (int i = 1; i <= 18; i++)
        {
            rStockCards.add(new Card(0));
        }
        Collections.shuffle(rStockCards);

        return rStockCards;
    }

    private List<Card> shuffleStockPile()
    {
        List<Card> rStockPile = new ArrayList<Card>();

        for (int i = 1; i <= Constants.DURATION; i++)
        {
            rStockPile.add(mStockCards.get(i));
            mStockCards.remove(i);
            Collections.shuffle(mStockCards);
        }

        Collections.shuffle(rStockPile);
        return rStockPile;
    }

    // private Card[] shuffleDrawPile()
    // {
    // Card[] rDrawPile = new Card[5];
    // for (int i = 0; i < rDrawPile.length; i++)
    // {
    // rDrawPile[i] = getLastStockCard();
    // removeLastStockCard();
    // }
    // return rDrawPile;
    // }

    public void refreshDrawPile(Player pPlayer)
    {
        if (!pPlayer.equals(turnHolder)) throw new IllegalStateException("It is not the players turn: "
                                                                         + pPlayer
                                                                         + " (rDP)");
        for (int i = 0; i < pPlayer.getDrawPile().length; i++)
        {
            if (pPlayer.getDrawPile()[i].getNumber() == -2)
            {
                pPlayer.getDrawPile()[i].setNumber(getLastStockCard().getNumber());
                removeLastStockCard();
            }
        }
    }

    public Player[] getPlayers()
    {
        return mPlayers;
    }

    public Player getPlayerIndexOf(int pN)
    {
        return mPlayers[pN];
    }

    public int getPlayerSize()
    {
        return mPlayers != null ? mPlayers.length : 0;
    }

    public String getPlayerNameIndexOf(int pN)
    {
        return mPlayers[pN].getName();
    }

    public Card[] getBuildPile()
    {
        return mBuildPile;
    }

    public List<Card> getStockCards()
    {
        return mStockCards;
    }

    public Card getLastStockCard()
    {
        return mStockCards.get(mStockCards.size() - 1);
    }

    public void removeLastStockCard()
    {
        mStockCards.remove(mStockCards.size() - 1);
    }

    public int getPlayerIDByName(String pName)
    {
        for (int i = 0; i < mPlayers.length; i++)
        {
            if (mPlayers[i].getName().equals(pName)) return i;
        }
        throw new IllegalStateException("no player found.");
    }

    public Player getPlayerByName(String pName)
    {
        return mPlayers[getPlayerIDByName(pName)];
    }

    public void putCard(String pPlayerName, int pFromPlace, int pCard, boolean pFromHand, int pToPlace)
    {
        int playerID = getPlayerIDByName(pPlayerName);
        Player player = mPlayers[playerID];
        if (!player.equals(turnHolder)) throw new IllegalStateException("It is not the players turn: "
                                                                        + player
                                                                        + " (pC)");

        if (pFromHand
            && mPlayers[playerID].getDrawPile()[pFromPlace].getNumber() == pCard
            || !pFromHand
            && mPlayers[playerID].getDiscardPile()[pFromPlace].getNumber() == pCard)
        {
            // here comes the joker
            if (pCard == 0)
            {
                if (mBuildPile[pToPlace].getNumber() == -2)
                {
                    pCard = 1;
                }
                else
                {
                    pCard = mBuildPile[pToPlace].getNumber() + 1;
                }
            }
            // update BuildPile
            mBuildPile[pToPlace].setNumber(pCard);

            // remove PlayerPiles
            if (pFromHand)
            {
                mPlayers[playerID].removeDrawPileCard(pFromPlace);
            }
            else
            {
                if (pFromPlace < 4)
                {
                    mPlayers[playerID].removeDiscardPileCard(pFromPlace);
                }
                else
                {
                    mPlayers[playerID].removeLastStockPile();
                }
            }
        }
    }

    public void placeCardInDiscardPile(String pPlayerName, int pFromPlace, int pCard, int pToPlace)
    {
        if (pCard >= 0)// must be a normal card
        {
            int playerID = getPlayerIDByName(pPlayerName);
            Player player = mPlayers[playerID];
            if (!player.equals(turnHolder)) throw new IllegalStateException("It is not the players turn: "
                                                                            + player
                                                                            + " (pCIDP)");
            Card[] playerDiscardPile = player.getDiscardPile();

            playerDiscardPile[pToPlace].setNumber(pCard);
            player.removeDrawPileCard(pFromPlace);

            setTurnHolder(getPlayerIndexOf(getPlayerSize() == playerID ? 0 : playerID));

        }
    }

    public Player getTurnHolder()
    {
        return turnHolder;
    }

    public boolean setTurnHolder(Player pTurnHolder)
    {
        boolean changed = turnHolder != pTurnHolder;
        turnHolder = pTurnHolder;
        return changed;
    }

    public boolean isTurnHolder(Player player)
    {
        return player == getTurnHolder();
    }

    public boolean isFinished()
    {
        return getTurnHolder() == null;
    }

    private void clear()
    {
        turnHolder = null;
    }

    public boolean isWinner(Player player)
    {
        return getWinner() == player;
    }

    public Player getWinner()
    {
        if (!isFinished()) return null;
        for (int i = 0; i < mPlayers.length; i++)
        {
            if (mPlayers[i].getStockPile().size() == 0)
            {
                return mPlayers[i];
            }
        }
        return null;
    }
}