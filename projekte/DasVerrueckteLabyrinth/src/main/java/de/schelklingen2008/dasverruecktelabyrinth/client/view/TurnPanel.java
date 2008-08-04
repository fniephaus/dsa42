package de.schelklingen2008.dasverruecktelabyrinth.client.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.schelklingen2008.dasverruecktelabyrinth.client.controller.Controller;
import de.schelklingen2008.dasverruecktelabyrinth.client.controller.GameChangeListener;
import de.schelklingen2008.dasverruecktelabyrinth.client.model.GameContext;
import de.schelklingen2008.dasverruecktelabyrinth.model.GameModel;
import de.schelklingen2008.dasverruecktelabyrinth.model.PlayerType;

/**
 * Displays a list of players and turn change information in a turn-based game.
 */
public class TurnPanel extends JPanel implements GameChangeListener
{
    private Controller controller;

    public TurnPanel(Controller controller)
    {
        this.controller = controller;
        controller.addChangeListener(this);
    }

    public void gameChanged()
    {
        removeAll();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (PlayerType playerType : PlayerType.values())
        {
            String name = getGameContext().getName(playerType);
            add(new JLabel(name));
        }

        revalidate();
        repaint();
    }

    private GameContext getGameContext()
    {
        return controller.getGameContext();
    }

    private GameModel getGameModel()
    {
        return getGameContext().getGameModel();
    }
}
