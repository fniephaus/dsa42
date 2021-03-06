package de.schelklingen2008.billiards.model;

import java.io.Serializable;

import de.schelklingen2008.billiards.util.Vector2d;

public class BallPlacementConstraints implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3752601701300956200L;

    private final double minX, minY, maxX, maxY;

    public BallPlacementConstraints(double minX, double minY, double maxX, double maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public double getMinX()
    {
        return minX;
    }

    public double getMinY()
    {
        return minY;
    }

    public double getMaxX()
    {
        return maxX;
    }

    public double getMaxY()
    {
        return maxY;
    }

    public boolean checkPosition(Vector2d position)
    {
        return position.getX() >= minX && position.getX() <= maxX && position.getY() >= minY && position.getY() <= maxY;
    }

}
