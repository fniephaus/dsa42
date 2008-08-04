package de.schelklingen2008.dasverruecktelabyrinth.client.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import de.schelklingen2008.dasverruecktelabyrinth.model.GameModel;
import de.schelklingen2008.dasverruecktelabyrinth.model.PlayerType;
import de.schelklingen2008.util.LoggerFactory;

/**
 * Provides all necessary game information. On top of the game model it adds information on player
 * names and on which player corresponds to the client the context is used in.
 */
public class GameContext
{
    private static final Logger sLogger = LoggerFactory.create();

    /** Contains the rules and the state of the game. */
    private GameModel gameModel = new GameModel();

    /** Is the name of the player playing in this client. */
    private String myName;

    /** Provides a name for each player in the game. */
    private Map<PlayerType, String> playerTypeNames = new HashMap<PlayerType, String>();

    public String getName(PlayerType playerType)
    {
        return playerTypeNames.get(playerType);
    }

    public String getMyName()
    {
        return myName;
    }

    public void setMyName(String myName)
    {
        sLogger.fine("setMyName: " + myName);
        this.myName = myName;
    }

    public void setPlayers(String[] names)
    {
        playerTypeNames.clear();
        playerTypeNames.put(PlayerType.valueOf(0), names[0]);
        playerTypeNames.put(PlayerType.valueOf(1), names[1]);
    }

    public PlayerType getMyPlayerType()
    {
        if (myName == null) return null;
        if (myName.equals(playerTypeNames.get(PlayerType.WHITE))) return PlayerType.WHITE;
        if (myName.equals(playerTypeNames.get(PlayerType.BLACK))) return PlayerType.BLACK;
        return null;
    }

    public GameModel getGameModel()
    {
        return gameModel;
    }
}
