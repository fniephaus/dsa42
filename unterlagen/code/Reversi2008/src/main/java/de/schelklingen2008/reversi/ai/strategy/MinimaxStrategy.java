package de.schelklingen2008.reversi.ai.strategy;

import java.util.Random;

import de.schelklingen2008.reversi.ai.evaluation.EvaluationFunction;
import de.schelklingen2008.reversi.model.GameModel;
import de.schelklingen2008.reversi.model.Piece;
import de.schelklingen2008.reversi.model.Player;

public class MinimaxStrategy implements ReversiStrategy
{

    private final EvaluationFunction evalFunction;
    private final int                depth;
    private final Random             random = new Random();
    private int                      count;
    private final boolean            randomMoveOnEqual;
    private Player                   currentPlayer;

    public MinimaxStrategy(final EvaluationFunction evalFunction, final int depth, final boolean randomMoveOnEqual)
    {
        this.evalFunction = evalFunction;
        this.depth = depth;
        this.randomMoveOnEqual = randomMoveOnEqual;
    }

    public Piece move(final GameModel gameModel)
    {
        count = 0;
        currentPlayer = gameModel.getTurnHolder();
        return minimax(gameModel);
    }

    private Piece minimax(final GameModel gameModel)
    {
        if (!gameModel.hasLegalMoves()) return null;

        Player player = gameModel.getTurnHolder();

        Piece bestMove = null;
        int bestWeight = Integer.MIN_VALUE;

        boolean[][] legalMoves = gameModel.getLegalMoves(player);
        for (int i = 0; i < legalMoves.length; i++)
        {
            for (int j = 0; j < legalMoves[i].length; j++)
            {
                if (!legalMoves[i][j]) continue;

                GameModel newGameModel = new GameModel(gameModel);
                newGameModel.placePiece(i, j, player);

                int weight = minimaxValue(newGameModel, depth);
                if (randomMoveOnEqual && weight == bestWeight && random.nextInt() % 2 == 0)
                {
                    bestMove = new Piece(i, j, player);
                }
                if (weight > bestWeight)
                {
                    bestWeight = weight;
                    bestMove = new Piece(i, j, player);
                }
            }
        }
        return bestMove;
    }

    private int minimaxValue(final GameModel gameModel, final int depth)
    {
        Player player = gameModel.getTurnHolder();

        if (depth == 0 || !gameModel.hasLegalMoves())
        {
            count++;
            return evalFunction.evaluatePosition(gameModel, currentPlayer);
        }

        boolean isMax = player == currentPlayer;
        int bestWeight = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        boolean[][] legalMoves = gameModel.getLegalMoves(player);
        for (int i = 0; i < legalMoves.length; i++)
        {
            for (int j = 0; j < legalMoves[i].length; j++)
            {
                if (!legalMoves[i][j]) continue;

                GameModel newGameModel = new GameModel(gameModel);
                newGameModel.placePiece(i, j, player);

                int weight = minimaxValue(newGameModel, depth - 1);

                if (isMax && weight > bestWeight) bestWeight = weight;
                if (!isMax && weight < bestWeight) bestWeight = weight;
            }
        }
        return bestWeight;
    }

    @Override
    public String toString()
    {
        return "Minimax(" + evalFunction + ", " + depth + ")";
    }

    public int getCount()
    {
        return count;
    }

}
