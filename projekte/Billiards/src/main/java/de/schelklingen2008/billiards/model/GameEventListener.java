package de.schelklingen2008.billiards.model;

public interface GameEventListener
{

    public void gameStarted();

    public void shotTaken(ShotEvent e);

    public void ballSunk(BallSunkEvent e);

}