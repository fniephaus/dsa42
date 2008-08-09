package de.schelklingen2008.risiko.model;

import java.awt.Color;
import java.io.Serializable;

/**
 * Maintains the rules and the state of the game.
 */
public class GameModel implements Serializable
{

    private Country[] c = new Country[30];
    private Player[]  p = new Player[2];  // TODO variabel machen
    private Player    turnholder;
    private int       player;

    public GameModel()
    {
        initCountrys();
        player = 0;
    }

    public Country getCountry(int i)
    {
        return c[i];
    }

    public Country[] getCountryArray()
    {
        return c;
    }

    public Country getCountrybyColor(Color pc)
    {
        for (int i = 0; i < 30; i++)
        {
            if (c[i].getColor().equals(pc)) return c[i];
        }
        return c[0]; // TODO anders machen
    }

    public Player[] getPlayerArray()
    {
        return p;
    }

    public Player getTurnholder()
    {
        return turnholder;
    }

    private void initCountrys()
    {
        c[0] = new Country("Island", 0, 90, 160, 255, 255, 0);
        c[1] = new Country("Irland", 1, 128, 498, 216, 230, 40);
        c[2] = new Country("Gro�britanien", 2, 210, 518, 248, 199, 5);
        c[3] = new Country("Norwegen", 3, 378, 307, 74, 37, 0);
        c[4] = new Country("Finnland", 4, 578, 253, 199, 98, 31);
        c[5] = new Country("Schweden", 5, 467, 346, 128, 128, 0);
        c[6] = new Country("D�nemark", 6, 383, 439, 128, 64, 0);
        c[7] = new Country("Russland", 7, 822, 336, 0, 255, 128);
        c[8] = new Country("Estland", 8, 607, 351, 0, 255, 0);
        c[9] = new Country("Lettland", 9, 598, 403, 0, 64, 0);
        c[10] = new Country("Litauen", 10, 595, 444, 18, 180, 20);
        c[11] = new Country("Wei�russland", 11, 660, 485, 128, 255, 0);
        c[12] = new Country("Ukraine", 12, 710, 562, 0, 136, 45);
        c[13] = new Country("Polen", 13, 527, 528, 1, 41, 12);
        c[14] = new Country("Italien", 14, 400, 740, 255, 128, 128);
        c[15] = new Country("Griechenland", 15, 652, 848, 255, 0, 255);
        c[16] = new Country("Jugoslawien", 16, 546, 732, 255, 0, 128);
        c[17] = new Country("Albanien", 17, 605, 819, 128, 0, 255);
        c[18] = new Country("Bulgarien", 18, 652, 672, 255, 128, 255);
        c[19] = new Country("Rum�nien", 19, 688, 758, 128, 0, 128);
        c[20] = new Country("Portugal", 20, 25, 803, 100, 2, 2);
        c[21] = new Country("Frankreich", 21, 247, 674, 234, 67, 11);
        c[22] = new Country("Spanien", 22, 113, 826, 234, 0, 11);
        c[23] = new Country("BeNeLux", 23, 313, 549, 0, 0, 160);
        c[24] = new Country("Deutschland", 24, 380, 564, 0, 128, 255);
        c[25] = new Country("Tschechien", 25, 480, 610, 0, 64, 128);
        c[26] = new Country("Schweiz", 26, 367, 685, 0, 196, 196);
        c[27] = new Country("Slowakei", 27, 557, 633, 196, 196, 255);
        c[28] = new Country("�sterreich", 28, 465, 675, 13, 13, 255);
        c[29] = new Country("Ungarn", 29, 550, 673, 44, 162, 224);
    }

    public boolean isWinner(Player player2)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void setAllCountriesUnselected()
    {
        for (int i = 0; i < 30; i++)
        {
            c[i].setSelected(false);
        }
    }

    public void setPlayerArray(Player[] p)
    {
        this.p = p;
    }

    public void setTurnholder()
    {
        turnholder = p[player++];
    }

    public Player valueOf(int playerIndex)
    {

        // TODO Auto-generated method stub
        return p[playerIndex];

    }

}
