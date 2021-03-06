package de.schelklingen2008.doppelkopf.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.schelklingen2008.doppelkopf.client.controller.Controller;
import de.schelklingen2008.doppelkopf.client.controller.GameChangeListener;
import de.schelklingen2008.doppelkopf.model.GameModel;
import de.schelklingen2008.doppelkopf.model.Spieler;

public class ButtonPanel extends JPanel implements GameChangeListener
{
	private static final long serialVersionUID = 1L;
	
    GameModel          spiel;
    private Controller controller;
    JButton            hochzeitButton, pingbutton;
    JTextArea          nachrichtenBox;
    JScrollPane        scroller;
    Spieler            ich;

    public ButtonPanel(Controller pController)
    {
    	this.setBackground(BoardView.tischFarbe);
    	this.setLayout(new FlowLayout());
    	
        controller = pController;
        pController.addChangeListener(this);

        nachrichtenBox = new JTextArea(5, 40);
        nachrichtenBox.setLineWrap(true);
        nachrichtenBox.setWrapStyleWord(true);
        nachrichtenBox.setEditable(false);
        nachrichtenBox.setBackground(Color.decode("#0000AA00"));
        scroller = new JScrollPane(nachrichtenBox);
        
        
//        scroller.setPreferredSize(new Dimension(500, 80));
//        scroller.setViewportView(nachrichtenBox);

        hochzeitButton = new JButton("Der erste Fremde");
        hochzeitButton.setVisible(false);
        hochzeitButton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                controller.ersterFremderButtonClicked();
                hochzeitButton.setEnabled(false);
            }
        });
        add(hochzeitButton);
        
        pingbutton = new JButton("Ping");
        pingbutton.setVisible(true);
        pingbutton.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                controller.pingButtonClicked();
            }
        });
        add(pingbutton);
        
        add(scroller);
        setPreferredSize(new Dimension(800, 100));

    }

    public void gameChanged()
    {
        spiel = controller.getGameContext().getGameModel();
        ich = controller.getGameContext().getMyPlayer();

        // Nachrichtenbox
        String inhalt = "";
        List<String> nachrichten = spiel.getNachrichten();
        for (String s : nachrichten)
        {
            inhalt += s;
            inhalt += "\n";
        }
        nachrichtenBox.setText(inhalt);
//        nachrichtenBox.repaint();

        
        // Hochzeitsbutton
        if (spiel.getTisch().getHochzeitSpieler() == ich)
        	hochzeitButton.setVisible(true);
        else
        	hochzeitButton.setVisible(false);
    }

}
