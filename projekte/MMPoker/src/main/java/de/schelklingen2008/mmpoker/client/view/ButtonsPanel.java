package de.schelklingen2008.mmpoker.client.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.schelklingen2008.mmpoker.client.controller.Controller;
import de.schelklingen2008.mmpoker.client.controller.GameChangeListener;
import de.schelklingen2008.mmpoker.model.GameModel;

public class ButtonsPanel extends JPanel implements ActionListener, GameChangeListener
{

    private Controller controller;
    JTextField         betField;

    public ButtonsPanel(Controller pController)
    {
        controller = pController;
        controller.addChangeListener(this);
        gameChanged();
    }

    public void actionPerformed(ActionEvent e)
    {
        GameModel model = controller.getGameContext().getGameModel();
        if (model == null) return;
        betField.setText(model.autoErgaenzen());
        controller.callButtonClicked(betField.getText());
        controller.raiseButtonClicked(betField.getText());
    }

    public void gameChanged()
    {
        removeAll();

        JButton callButton = new JButton("call");
        callButton.addActionListener(this);
        JButton raiseButton = new JButton("raise");
        raiseButton.addActionListener(this);
        JButton foldButton = new JButton("fold");
        foldButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                controller.foldButtonClicked();
            }
        });
        JButton checkButton = new JButton("check");
        checkButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                controller.checkButtonClicked();
            }
        });

        betField = new JTextField();

        betField.setMaximumSize(new Dimension(100, 25));
        betField.setPreferredSize(new Dimension(100, 25));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        add(Box.createHorizontalGlue());
        add(callButton);
        add(Box.createHorizontalStrut(5));
        add(raiseButton);
        add(Box.createHorizontalStrut(5));
        add(foldButton);
        add(Box.createHorizontalStrut(5));
        add(checkButton);
        add(Box.createHorizontalStrut(5));
        add(betField);
        add(Box.createHorizontalStrut(5));
    }
}
