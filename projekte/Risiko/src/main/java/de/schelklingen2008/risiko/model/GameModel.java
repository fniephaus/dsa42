package de.schelklingen2008.risiko.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Maintains the rules and the state of the game.
 */
public class GameModel implements Serializable
{

    private Country[] c = new Country[29];
    private Player[]  p;
    private Player    turnholder;
    private int       turnholderIndex;
    private int       index;

    public GameModel(String[] names)
    {
        initPlayers(names);
        initCountrys();
        initNeigbours();
        distributeCountrys();

        turnholderIndex = 0;
        turnholder = p[0];
    }

    private void distributeCountrys()
    {
        for (int i = 0; i < c.length; i++)
        {
            if (index > p.length)
            {
                index = 0;
            }
            c[i].setOccupier(p[index]);
            index++;
        }
    }

    private void initPlayers(String[] names)
    {
        p = new Player[names.length];
        for (int i = 0; i < names.length; i++)
        {
            p[i] = new Player(names[i], i, Color.BLUE);
        }
    }

    public Country getCountry(int i)
    {
        return c[i];
    }

    public Country[] getCountryArray()
    {
        return c;
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

    private void initNeigbours()
    {
        // TODO f�llen
        // init neighbours Island
        ArrayList island = new ArrayList<Country>();
        island.add(0, c[1]); // Irland
        island.add(1, c[2]); // GB
        island.add(2, c[3]); // Norwegen
        c[0].setNeighbours(island);

        // init neighbours Irland
        ArrayList irland = new ArrayList<Country>();
        irland.add(0, c[0]); // Island
        irland.add(1, c[2]); // GB
        c[1].setNeighbours(irland);

        // init neighbours GB
        ArrayList gb = new ArrayList<Country>();
        gb.add(0, c[1]); // Irland
        gb.add(1, c[23]); // BeNeLux
        gb.add(2, c[21]); // Frankreich
        gb.add(3, c[0]); // Island
        c[2].setNeighbours(gb);

        // init neighbours Norwegen

        // init neighbours Finnland

        // init neighbours Schweden

        // init neighbours D�nemark

        // init neighbours Russland

        // init neighbours Estland

        // init neighbours Lettland

        // init neighbours Litauen

        // init neighbours Weissrussland

        // init neighbours Ukraine

        // init neighbours Polen

        // init neighbours Italien

        // init neighbours Griechenland

        // init neighbours Jugoslawien

        // init neighbours Albanien

        // init neighbours Bulgarien

        // init neighbours Rum�nien

        // init neighbours Portugal
        ArrayList portugal = new ArrayList<Country>();
        portugal.add(0, c[22]); // Spanien
        c[20].setNeighbours(portugal);

        // init neighbours Frankreich
        ArrayList frankreich = new ArrayList<Country>();
        frankreich.add(0, c[2]); // GB
        frankreich.add(1, c[23]); // BeNeLux
        frankreich.add(2, c[24]); // Deutschland
        frankreich.add(3, c[26]); // Schweiz
        frankreich.add(4, c[14]); // Italien
        frankreich.add(5, c[22]); // Spanien

        // init neighbours Spanien
        ArrayList spanien = new ArrayList<Country>();
        spanien.add(0, c[20]); // Portugal
        spanien.add(1, c[21]); // Frankreich
        c[22].setNeighbours(spanien);

        // init neighbours BeNeLux
        ArrayList benelux = new ArrayList<Country>();
        benelux.add(0, c[2]); // GB
        benelux.add(1, c[24]);// Deutschland
        benelux.add(2, c[21]);// Frankreich
        c[23].setNeighbours(benelux);

        // init neighbours Deutschland
        ArrayList deutschland = new ArrayList<Country>();
        deutschland.add(0, c[23]);// BeNeLux
        deutschland.add(1, c[6]);// D�nemark
        deutschland.add(2, c[13]);// Polen
        deutschland.add(3, c[25]);// Tschechien
        deutschland.add(4, c[28]);// �stereich
        deutschland.add(5, c[26]);// Schweiz
        deutschland.add(6, c[21]);// Frankreich
        c[24].setNeighbours(deutschland);

        // init neighbours Tschechien
        // Deutschland
        // Polen
        // Slowakei
        // �sterreich

        // init neighbours Schweiz
        // Deutschland
        // �sterreich
        // Italien
        // Frankreich

        // init neighbours Slowakei

        // init neighbours �sterreich

        // init neighbours Ungarn

    }

    public boolean isWinner(Player player2)
    {
        for (int i = 0; i < c.length; i++)
        {
            if (player2.equals(c[i].getOccupier()))
            {

            }
            else
                return false;
        }
        return true;
    }

    public Country getCountryByColor(Color pc)
    {
        for (int i = 0; i < 30; i++)
        {
            if (c[i].getColor().equals(pc)) return c[i];
        }
        throw new IllegalArgumentException();
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

    public void setNextTurnholder()
    {
        turnholderIndex++;
        if (turnholderIndex == getPlayerCount()) turnholderIndex = 0;
        turnholder = p[turnholderIndex];
    }

    public Player valueOf(int playerIndex)
    {
        return p[playerIndex];
    }

    private int getPlayerCount()
    {
        return p.length;
    }

    public Player getPlayerbyName(String name)
    {
        for (int i = 0; i < p.length; i++)
        {
            if (name == p[i].getPlayerName())
            {
                return p[i];
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isLegalMoveAttack(Player pPlayer, Country country1, Country country2)
    {
        if (pPlayer.equals(turnholder))
        {
            if (pPlayer.equals(country1.getOccupier()))
            {
                if (country1.isNeighbour(country2))
                {
                    if (country1.getUnits() > 1)
                    {
                        return true;
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }

    public boolean isLegalMoveSet(Player pPlayer, Country country1)
    {
        if (pPlayer.equals(turnholder))
        {
            if (pPlayer.equals(country1.getOccupier()))
            {
                if (pPlayer.getUnitsToSet() > 0)
                {
                    return true;
                }
                else
                    return false;

            }
            else
                return false;
        }
        else
            return false;
    }

    public int dice()
    {
        Random r = new Random();
        int d = r.nextInt(6);
        d = d + 1;
        return d;

    }

    public void placeUnit(Country c)
    {
        c.setUnits(c.getUnits() + 1);
        c.getOccupier().setUnitsToSet(c.getOccupier().getUnitsToSet() - 1);
    }

    public void attack(Country attacker, Country defender)
    {
        int dicesattacker;
        int dicesdefender;

        if (attacker.getUnits() > 3)
            dicesattacker = 3;
        else
            dicesattacker = attacker.getUnits() - 1;

        if (defender.getUnits() > 1)
            dicesdefender = 2;
        else
            dicesdefender = 1;

        int[] da = new int[dicesattacker];
        int[] dd = new int[dicesdefender];
        for (int i = 0; i < dicesattacker; i++)
        {
            da[i] = dice();
        }
        for (int i = 0; i < dicesdefender; i++)
        {
            dd[i] = dice();
        }
        int vergleicha = 0;
        int vergleichd = 0;

        for (int i = 0; i < Math.min(dicesattacker, dicesdefender); i++)
        {

            if (da.length == 1)
            {
                vergleicha = da[0];
            }
            else if (da.length == 2)
            {
                vergleicha = Math.max(da[0], da[1]);
                da[da[0] < da[1] ? 1 : 0] = 0;
            }

            else if (da.length == 3)
            {
                vergleicha = Math.max(da[0], Math.max(da[1], da[2]));
                if (da[0] > da[1] && da[0] > da[2])
                    da[0] = 0;
                else if (da[1] > da[0] && da[1] > da[2])
                    da[1] = 0;
                else
                    da[2] = 0;
            }

            if (dd.length == 1)
            {
                vergleichd = dd[0];
            }
            else if (dd.length == 2)
            {
                vergleichd = Math.max(dd[0], dd[1]);
                dd[dd[0] < dd[1] ? 1 : 0] = 0;
            }

            if (vergleicha > vergleichd)
            {
                defender.setUnits(defender.getUnits() - 1);
            }
            else
                attacker.setUnits(attacker.getUnits() - 1);

        }

        if (defender.getUnits() == 0)
        {
            defender.setOccupier(attacker.getOccupier());
            defender.setUnits(attacker.getUnits() / 2);
            attacker.setUnits(attacker.getUnits() - attacker.getUnits() / 2);
        }

    }
}
