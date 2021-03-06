package de.schelklingen2008.dasverruecktelabyrinth.server;

import java.util.logging.Logger;

import com.threerings.crowd.data.BodyObject;
import com.threerings.crowd.data.PlaceObject;
import com.threerings.parlor.game.server.GameManager;

import de.schelklingen2008.dasverruecktelabyrinth.model.GameModel;
import de.schelklingen2008.dasverruecktelabyrinth.model.PlayerType;
import de.schelklingen2008.dasverruecktelabyrinth.model.PushButton;
import de.schelklingen2008.dasverruecktelabyrinth.transport.SharedState;
import de.schelklingen2008.util.LoggerFactory;

/**
 * Handles the server side of the game.
 */
public class Manager extends GameManager
{

    /** Is the state transmitted to the clients and managed by the server. */
    private SharedState         sharedState;

    /** Implements the game logic with an own internal model. */
    private GameModel           gameModel;

    private static final Logger sLogger = LoggerFactory.create();

    @Override
    protected PlaceObject createPlaceObject()
    {
        return sharedState = new SharedState();
    }

    @Override
    protected void gameWillStart()
    {
        super.gameWillStart();

        String[] names = new String[getPlayerCount()];
        for (int i = 0; i < names.length; i++)
            names[i] = getPlayer(i).username.toString();

        gameModel = new GameModel(names);
        updateSharedState();
    }

    public void rechtsDrehen(BodyObject client)
    {
        if (getPlayerType(client) == gameModel.getTurnHolder())
        {
            gameModel.rechtsDrehen();
            updateSharedState();
        }
    }

    public void linksDrehen(BodyObject client)
    {
        if (getPlayerType(client) == gameModel.getTurnHolder())
        {
            gameModel.linksDrehen();
            updateSharedState();
        }
    }

    public void insert(BodyObject client, PushButton pPushButton)
    {
        if (gameModel.getTurnHolder() == getPlayerType(client))
        {
            sLogger.fine("insert: " + pPushButton);
            gameModel.insert(pPushButton);
            updateSharedState();
        }
    }

    public void placePlayer(BodyObject client, int x, int y)
    {
        if (gameModel.getTurnHolder() == getPlayerType(client))
        {
            gameModel.placePlayer(x, y, getPlayerType(client));
            if (gameModel.isFinished()) endGame();
            updateSharedState();
        }
    }

    /**
     * Updates the shared state and thus send changes to all clients.
     */
    private void updateSharedState()
    {
        sharedState.setModel(gameModel);
    }

    private PlayerType getPlayerType(BodyObject client)
    {
        return PlayerType.valueOf(getPlayerIndex(client.username));
    }
}
