package de.schelklingen2008.reversi.server;

import java.util.logging.Logger;

import com.threerings.crowd.data.BodyObject;
import com.threerings.crowd.data.PlaceObject;
import com.threerings.parlor.game.server.GameManager;

import de.schelklingen2008.reversi.model.GameModel;
import de.schelklingen2008.reversi.model.Player;
import de.schelklingen2008.reversi.transport.SharedState;
import de.schelklingen2008.util.LoggerFactory;

/**
 * Handles the server side of the game.
 */
public class Manager extends GameManager
{

    private static final Logger sLogger = LoggerFactory.create();

    /** Is the state transmitted to the clients and managed by the server. */
    private SharedState         sharedState;

    /** Implements the game logic with an own internal model. */
    private GameModel           gameModel;

    @Override
    protected PlaceObject createPlaceObject()
    {
        return sharedState = new SharedState();
    }

    @Override
    protected void gameWillStart()
    {
        super.gameWillStart();

        gameModel = new GameModel();
        updateSharedState();
    }

    /**
     * Is called by clients to make a move and place a piece on the board.
     */
    public void placePiece(BodyObject client, int x, int y)
    {
        try
        {
            gameModel.placePiece(x, y, getPlayer(client));
        }
        catch (Exception e)
        {
            String msg = "cannot place piece: x: "
                         + x
                         + " y: "
                         + y
                         + " player: "
                         + client.username
                         + " reason: "
                         + e.getMessage();
            sLogger.warning(msg);
        }
        updateSharedState();
        if (gameModel.isFinished()) endGame();
    }

    /**
     * Updates the shared state and thus send changes to all clients.
     */
    private void updateSharedState()
    {
        sharedState.setModel(gameModel);
    }

    private Player getPlayer(BodyObject client)
    {
        return Player.valueOf(getPlayerIndex(client.username));
    }
}
