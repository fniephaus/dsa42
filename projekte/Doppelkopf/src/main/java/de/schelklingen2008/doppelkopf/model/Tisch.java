package de.schelklingen2008.doppelkopf.model;

import java.util.Collections;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Tisch
{

    GameModel           spiel;
    public Queue<Karte> mitte;
    Spieler             anDerReihe;
    int                 stichAnzahl;

    public Tisch(GameModel spiel, Spieler faengtAn)
    {
        this.spiel = spiel;
        anDerReihe = faengtAn;
        mitte = new ArrayBlockingQueue<Karte>(4);
    }

    public void gibKarten()
    {
        Stack<Karte> stapel = erzeugeStapel(); // Ausgabekartenstapel erzeugen
        mischeStapel(stapel); // Stapel mischen
        for (Karte k : stapel)
            System.out.println(k.toString());

        // Karten verteilen

        while (!stapel.isEmpty())
        {
            anDerReihe.blatt.add(stapel.pop());
            anDerReihe = spiel.getSpielerListe().Next();
        }
    }

    private Stack<Karte> erzeugeStapel()
    {
        Stack<Karte> stapel = new Stack<Karte>();
        for (Farbe f : Farbe.values())
            for (Bild b : Bild.values())
                for (int i = 0; i < 2; i++)
                    stapel.push(new Karte(f, b));

        return stapel;
    }

    private void mischeStapel(Stack<Karte> stapel)
    {
        Collections.shuffle(stapel);
    }
}
