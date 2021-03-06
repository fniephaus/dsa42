package de.schelklingen2008.risiko.server;

import com.threerings.crowd.data.BodyObject;
import com.threerings.crowd.data.PlaceObject;
import com.threerings.parlor.game.server.GameManager;

import de.schelklingen2008.risiko.model.GameModel;
import de.schelklingen2008.risiko.model.Player;
import de.schelklingen2008.risiko.transport.SharedState;

/**
 * Handles the server side of the game.
 */
public class Manager extends GameManager
{

    /** Is the state transmitted to the clients and managed by the server. */
    private SharedState sharedState;

    /** Implements the game logic with an own internal model. */
    private GameModel   gameModel;

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
        for (int i = 0; i < getPlayerCount(); i++)
        {
            names[i] = getPlayer(i).username.toString();
        }
        gameModel = new GameModel(names);
        // gameModel.getCountryArray()[0].setOccupier(gameModel.getPlayerArray()[0]);

        updateSharedState();
        gameModel.removenewHistory();
    }

    /**
     * Updates the shared state and thus send changes to all clients.
     */
    private Player getPlayer(BodyObject client)
    {
        return gameModel.valueOf(getPlayerIndex(client.username));
    }

    public void placeUnit(BodyObject client, int index)
    {
        gameModel.placeUnit(gameModel.getCountry(index));
        updateSharedState();
        gameModel.removenewHistory();
    }

    public void EndofTurn(BodyObject client)
    {

        gameModel.setNextTurnholder();
        updateSharedState();
        gameModel.removenewHistory();
    }

    public void moveUnit(BodyObject client, int fromindex, int toindex)
    {
        gameModel.moveUnit(gameModel.getCountry(fromindex), gameModel.getCountry(toindex));
        updateSharedState();
    }

    public void Attack(BodyObject client, int attindex, int defindex)
    {
        gameModel.attack(gameModel.getCountry(attindex), gameModel.getCountry(defindex));
        updateSharedState();
        gameModel.removenewHistory();
    }

    private void updateSharedState()
    {
        sharedState.setModel(gameModel);
    }
}
