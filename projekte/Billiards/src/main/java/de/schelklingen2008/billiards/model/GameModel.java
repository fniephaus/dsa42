package de.schelklingen2008.billiards.model;

import static de.schelklingen2008.billiards.GlobalConstants.MAX_X;
import static de.schelklingen2008.billiards.GlobalConstants.MAX_Y;
import static de.schelklingen2008.billiards.GlobalConstants.PLAYERS;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import de.schelklingen2008.billiards.GlobalConstants;
import de.schelklingen2008.billiards.model.Ball.BallType;
import de.schelklingen2008.billiards.util.Vector2d;

/**
 * Maintains the rules and the state of the game.
 */
public class GameModel
{

    private Player[] players = new Player[2];
    private Player turnHolder = null;
    // TODO Make this private
    public boolean inMotion = false; // Are there any balls in
    // motion?

    private List<Ball> balls = new ArrayList<Ball>();
    private List<Ball> ballsOnTable = new ArrayList<Ball>();

    private List<Wall> walls = new ArrayList<Wall>();

    private Ball whiteBall, blackBall;

    public boolean isInMotion()
    {
        return inMotion;
    }

    public List<Ball> getBalls()
    {
        return balls;
    }

    public List<Ball> getBallsOnTable()
    {
        return ballsOnTable;
    }

    private void addPlayers()
    {
        for (int i = 0; i < PLAYERS; i++)
        {
            players[i] = new Player(i);
        }
    }

    private void addBalls()
    {
        whiteBall = new Ball(Ball.BallType.WHITE, Color.WHITE);
        blackBall = new Ball(Ball.BallType.BLACK, Color.BLACK);

        balls.add(whiteBall);
        balls.add(blackBall);

        for (Color color : Ball.BALL_COLORS)
        {
            balls.add(new Ball(Ball.BallType.SOLID, color));
            balls.add(new Ball(Ball.BallType.STRIPED, color));
        }
    }

    private void addWalls()
    {
        walls.add(new Wall(new Vector2d(0, 0), new Vector2d(GlobalConstants.MAX_X, 0)));
        walls.add(new Wall(new Vector2d(0, GlobalConstants.MAX_Y), new Vector2d(GlobalConstants.MAX_X,
                                                                                GlobalConstants.MAX_Y)));
        walls.add(new Wall(new Vector2d(0, 0), new Vector2d(0, GlobalConstants.MAX_Y)));
        walls.add(new Wall(new Vector2d(GlobalConstants.MAX_X, 0), new Vector2d(GlobalConstants.MAX_X,
                                                                                GlobalConstants.MAX_Y)));
    }

    public GameModel()
    {

        addPlayers();
        addBalls();
        addWalls();

        walls.add(new Wall(new Vector2d(0, 200), new Vector2d(200, 0)));

        setUpGame();

    }

    public void setUpGame()
    {

        for (Player player : players)
        {
            player.resetScore();
        }

        inMotion = false;

        for (Ball ball : balls)
        {
            ball.setSunk(false);
        }

        ballsOnTable.clear();

        ballsOnTable.addAll(balls);

        resetBalls();

    }

    private void resetBalls()
    {

        for (Ball ball : balls)
        {
            ball.setVelocity(Vector2d.ZERO);
        }

        List<Ball> tmpBalls = new ArrayList<Ball>(balls);
        Collections.shuffle(tmpBalls);

        whiteBall.setPosition(new Vector2d(0.25d * MAX_X, 0.5d * MAX_Y));
        blackBall.setPosition(new Vector2d(0.75d * MAX_X, 0.5d * MAX_Y));

        if (tmpBalls.get(tmpBalls.size() - 1).getType().equals(tmpBalls.get(tmpBalls.size() - 5).getType()))
        {
            BallType ball2Type =
                tmpBalls.get(tmpBalls.size() - 1).getType() == BallType.SOLID ? BallType.STRIPED : BallType.SOLID;
            for (int i = 0; i < tmpBalls.size(); i++)
            {
                if (tmpBalls.get(i).getType() == ball2Type)
                {
                    Collections.swap(tmpBalls, i, tmpBalls.size() - 1);
                    break;
                }
            }
        }

        int i = 0;
        for (Ball ball : tmpBalls)
        {
            if (ball == whiteBall || ball == blackBall)
            {
                continue;
            }
            ball.setPosition(Ball.INITIAL_BALL_POSITIONS[i++]);
        }

    }

    public Player getPlayer(int id)
    {

        return players[id];

    }

    public boolean isTurnHolder(Player player)
    {
        return player.equals(turnHolder);
    }

    public void sinkBall(Ball ball)
    {
        ball.setSunk(true);
        ballsOnTable.remove(ball);
    }

    public boolean isTurnHolder(int player)
    {
        return turnHolder.getId() == player;
    }

    public void processTimeStep(double deltaT)
    {
        final double EPSILON = 0.0001d;

        if (!inMotion || deltaT < EPSILON)
        {
            return;
        }

        double remainingTime = deltaT;
        PriorityQueue<Collision> collisions = new PriorityQueue<Collision>();

        do
        {

            for (int i = 0; i < ballsOnTable.size(); i++)
            {

                Ball ball1 = ballsOnTable.get(i);

                if (ball1.isInMotion())
                {

                    for (Wall wall : walls)
                    {
                        Collision collision = Collision.getWallCollision(ball1, wall);
                        if (collision != null && collision.getTime() <= remainingTime)
                        {
                            collisions.offer(collision);
                        }
                    }

                }

                for (int j = i + 1; j < ballsOnTable.size(); j++)
                {
                    Ball ball2 = ballsOnTable.get(j);

                    if (!ball1.isInMotion() && !ball2.isInMotion())
                    {
                        continue;
                    }

                    Collision collision = Collision.getBallCollision(ball1, ball2);
                    if (collision != null && collision.getTime() <= remainingTime)
                    {
                        collisions.offer(collision);
                    }
                }
            }

            if (!collisions.isEmpty())
            {
                double firstCollisionTime = collisions.peek().getTime();

                Collision collision = collisions.poll();

                moveBalls(collision.getTime());
                remainingTime -= collision.getTime();

                do
                {
                    collision.handle();
                    collision = collisions.poll();
                } while (!collisions.isEmpty() && collision.getTime() - firstCollisionTime < EPSILON);
            }
            else
            {
                moveBalls(remainingTime);
                remainingTime = 0;
            }

        } while (remainingTime > 0);

    }

    private void moveBalls(double deltaT)
    {

        boolean tmpInMotion = false;

        for (Ball ball : ballsOnTable)
        {
            ball.move(deltaT);
            tmpInMotion |= ball.isInMotion();
        }

        if (!tmpInMotion)
        {
            inMotion = false;
        }

    }

    public Ball getWhiteBall()
    {
        return whiteBall;
    }

}