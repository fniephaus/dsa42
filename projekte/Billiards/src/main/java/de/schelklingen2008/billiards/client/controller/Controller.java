package de.schelklingen2008.billiards.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.threerings.crowd.client.PlaceView;
import com.threerings.crowd.data.PlaceConfig;
import com.threerings.crowd.data.PlaceObject;
import com.threerings.crowd.util.CrowdContext;
import com.threerings.parlor.game.client.GameController;
import com.threerings.presents.dobj.AttributeChangeListener;
import com.threerings.presents.dobj.AttributeChangedEvent;
import com.threerings.presents.dobj.ElementUpdateListener;
import com.threerings.presents.dobj.ElementUpdatedEvent;
import com.threerings.presents.dobj.EntryAddedEvent;
import com.threerings.presents.dobj.EntryRemovedEvent;
import com.threerings.presents.dobj.EntryUpdatedEvent;
import com.threerings.presents.dobj.SetListener;
import com.threerings.toybox.util.ToyBoxContext;
import com.threerings.util.MessageBundle;

import de.schelklingen2008.billiards.client.Constants;
import de.schelklingen2008.billiards.client.model.GameContext;
import de.schelklingen2008.billiards.client.view.BoardView;
import de.schelklingen2008.billiards.client.view.GamePanel;
import de.schelklingen2008.billiards.model.GameEventAdapter;
import de.schelklingen2008.billiards.model.ShotEvent;
import de.schelklingen2008.billiards.transport.SharedState;
import de.schelklingen2008.util.LoggerFactory;

/**
 * Handles the client side of the game.
 */
public class Controller extends GameController
{

    private static final Logger sLogger = LoggerFactory.create();

    private GameContext gameContext = new GameContext();

    private List<GameChangeListener> changeListeners = new ArrayList<GameChangeListener>();

    private SharedState sharedState;

    private ToyBoxContext toyBoxContext;

    private BoardProcessThread boardProcessThread;

    @Override
    public void init(CrowdContext crowdContext, PlaceConfig placeConfig)
    {
        sLogger.finer("trace");

        toyBoxContext = (ToyBoxContext) crowdContext;
        gameContext.setMyName(toyBoxContext.getUsername().toString());

        super.init(crowdContext, placeConfig);
    }

    @Override
    protected PlaceView createPlaceView(CrowdContext crowdContext)
    {
        sLogger.finer("trace");
        return new GamePanel(this);
    }

    @Override
    public void didLeavePlace(PlaceObject placeObject)
    {
        super.didLeavePlace(placeObject);
        sLogger.finer("trace");

        sharedState = null;
    }

    @Override
    public void willEnterPlace(PlaceObject placeObject)
    {
        sLogger.finer("trace");

        sharedState = (SharedState) placeObject;
        sharedState.addListener(new SharedStateListener());
        updateGameContext();

        super.willEnterPlace(placeObject);
    }

    public void leaveButtonClicked()
    {
        toyBoxContext.getLocationDirector().moveBack();
    }

    private void updateGameContext()
    {
        sLogger.info("Updating game context.");

        if (sharedState == null) return;

        gameContext.setPlayers(sharedState.getPlayerNames());

        if (sharedState.getModel() == null) return;

        sLogger.info("Receiving new game model.");

        gameContext.setGameModel(sharedState.getModel());

        gameContext.getGameModel().addGameEventListener(new GameEventAdapter()
        {

            @Override
            public void shotTaken(ShotEvent e)
            {
                sLogger.info(String.format("Invoking takeShot(%.2f, %.2f)", e.getAngle(), e.getVelocity()));
                sharedState.manager.invoke("takeShot", e.getAngle(), e.getVelocity());
            }

        });

        fireGameChange();
    }

    public void addChangeListener(GameChangeListener listener)
    {
        changeListeners.add(listener);
    }

    public void removeChangeListeners()
    {
        changeListeners.clear();
    }

    private void fireGameChange()
    {
        for (GameChangeListener listener : changeListeners)
            listener.gameChanged();
    }

    private MessageBundle getMsgBundle()
    {
        return toyBoxContext.getMessageManager().getBundle(Constants.BUNDLE_NAME);
    }

    public String getMessage(String key)
    {
        return getMsgBundle().get(key);
    }

    public GameContext getGameContext()
    {
        return gameContext;
    }

    public ToyBoxContext getToyBoxContext()
    {
        return toyBoxContext;
    }

    public void startBoardProcessThread(BoardView boardView)
    {
        if (boardProcessThread == null || !boardProcessThread.isAlive())
        {
            boardProcessThread = new BoardProcessThread(this, boardView);
            boardProcessThread.start();
        }
    }

    private class SharedStateListener implements AttributeChangeListener, SetListener, ElementUpdateListener
    {

        public void attributeChanged(AttributeChangedEvent event)
        {
            updateGameContext();
        }

        public void elementUpdated(ElementUpdatedEvent event)
        {
            updateGameContext();
        }

        public void entryAdded(EntryAddedEvent event)
        {
            updateGameContext();
        }

        public void entryRemoved(EntryRemovedEvent event)
        {
            updateGameContext();
        }

        public void entryUpdated(EntryUpdatedEvent event)
        {
            updateGameContext();
        }
    }
}
