package de.schelklingen2008.doppelkopf.model;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.schelklingen2008.util.LoggerFactory;

/**
 * Maintains the rules and the state of the game.
 */
public class GameModel implements Serializable
{

    private static final Logger logger       = LoggerFactory.create();

    private Tisch               tisch;
    private List<Integer>[]     punkte;
    private SpielerListe        spielerliste = new SpielerListe();
    private boolean             rundeBeendet;

    public GameModel()
    {

    }

    public GameModel(String[] spielerNamen)
    {
        if (spielerNamen.length != 4) throw new RuntimeException("Falsche Spieleranzahl f�r Doppelkopf");

        // Spieler hinzuf�gen
        for (String spielername : spielerNamen)
            spielerliste.add(new Spieler(spielername));

        logger.log(Level.INFO, spielerliste.toString());

        // Spiel starten
        neuesSpiel();
    }

    private void neuesSpiel()
    {
        tisch = new Tisch(spielerliste);
        tisch.gibKarten();
    }

    public boolean isFinished()
    {
        return false;
    }

    public Tisch getTisch()
    {
        return tisch;
    }

    public boolean isRundeBeendet()
    {
        return rundeBeendet;
    }

    public void karteAusspielen(String spielerName, Karte karte)
    {
        Spieler spieler = spielerliste.getSpieler(spielerName);

    }
}