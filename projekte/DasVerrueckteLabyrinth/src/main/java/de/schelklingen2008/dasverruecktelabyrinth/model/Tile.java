package de.schelklingen2008.dasverruecktelabyrinth.model;

import java.io.Serializable;

public class Tile implements Serializable
{

    public static final Tile HORIZONTAL = new Tile(false, false, true, true, null);
    public static final Tile VERTICAL   = new Tile(true, true, false, false, null);
    public static final Tile CROSS      = new Tile(true, true, true, true, null);

    public static final Tile CURVE1     = new Tile(false, true, false, true, null);
    public static final Tile CURVE2     = new Tile(false, true, true, false, null);
    public static final Tile CURVE3     = new Tile(true, false, true, false, null);
    public static final Tile CURVE4     = new Tile(true, false, false, true, null);

    private boolean          up, down, right, left;
    private TreasureCard     tc         = null;

    public Tile(boolean pUp, boolean pDown, boolean pRight, boolean pLeft, TreasureCard pTc)
    {
        up = pUp;
        down = pDown;
        right = pRight;
        left = pLeft;
        tc = pTc;
    }

    public boolean getUp()
    {
        return up;
    }

    public boolean getDown()
    {

        return down;
    }

    public boolean getRight()
    {

        return right;
    }

    public boolean getLeft()
    {

        return left;
    }

    public void turnRight()
    {
        boolean t = left;
        left = down;
        down = right;
        right = up;
        up = t;
    }

    public void turnLeft()
    {
        boolean t = left;
        left = up;
        up = right;
        right = down;
        down = t;
    }

    public TreasureCard getTC()
    {
        return tc;
    }

    public boolean isCurve1()
    {
        if (getLeft() == true && getDown() == true && getRight() == false && getUp() == false) return true;

        return false;
    }

    public boolean isCurve2()
    {
        if (getLeft() == false && getDown() == true && getRight() == true && getUp() == false) return true;

        return false;
    }

    public boolean isCurve3()
    {
        if (getLeft() == false && getDown() == false && getRight() == true && getUp() == true) return true;

        return false;
    }

    public boolean isCurve4()
    {
        if (getLeft() == true && getDown() == false && getRight() == false && getUp() == true) return true;

        return false;
    }

    public boolean isHorizontal()
    {
        if (getLeft() == true && getDown() == false && getRight() == true && getUp() == false) return true;

        return false;
    }

    public boolean isVertical()
    {
        if (getLeft() == false && getDown() == true && getRight() == false && getUp() == true) return true;

        return false;
    }

    public boolean isCross()
    {
        if (getLeft() == true && getDown() == true && getRight() == true && getUp() == true) return true;

        return false;
    }

    @Override
    public String toString()
    {
        if (isCross()) return "+";
        if (isHorizontal()) return "-";
        if (isVertical()) return "|";
        if (isCurve1()) return "1";
        if (isCurve2()) return "2";
        if (isCurve3()) return "3";
        if (isCurve4()) return "4";
        return " ";
    }

}
