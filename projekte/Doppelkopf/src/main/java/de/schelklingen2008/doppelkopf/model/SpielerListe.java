package de.schelklingen2008.doppelkopf.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SpielerListe extends ArrayList<Spieler> implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int               anDerReihe;

    public SpielerListe()
    {
    }

    public Spieler getAnDerReihe()
    {
        return get(anDerReihe);
    }

    public void setAnDerReihe(Spieler neuAnDerReihe)
    {
        anDerReihe = indexOf(neuAnDerReihe);
    }

    public Spieler getNext()
    {
        return get(nextAnDerReihe(anDerReihe));
    }

    public Spieler next()
    {
        rotieren();
        // Spieler naechster = get(nextAnDerReihe(anDerReihe));

        return getAnDerReihe();
    }

    public void rotieren()
    {
        anDerReihe = nextAnDerReihe(anDerReihe);
    }

    public Spieler getSpieler(String spielerName)
    {
        Spieler gesuchterSpieler = null;
        for (Spieler p : this)
        {
            if (p.getName().equals(spielerName)) gesuchterSpieler = p;
        }

        if (gesuchterSpieler == null) throw new RuntimeException("Spieler '" + spielerName + "' nicht gefunden;");
        return gesuchterSpieler;
    }

    private int nextAnDerReihe(int aktuell)
    {
        if (aktuell + 1 < size())
            return aktuell + 1;
        else
            return 0;
    }

    @Override
    public String toString()
    {
        if (size() == 0) return "[keine Spieler]";
        String ausgabe = "";
        for (Spieler p : this)
        {
            ausgabe += p.getName() + " ";
        }
        return ausgabe;
    }
}