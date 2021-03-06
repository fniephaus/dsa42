package de.schelklingen2008.reversi.ai.tournament;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

public class MatchPanel extends JPanel implements MatchObserver
{

    private static final Color COL_BACKGROUND = new Color(0, 100, 0);
    private static final Color COL_RESULT     = Color.WHITE;
    private static final Color COL_BLANK      = Color.GRAY;
    public static final int    WIDTH          = 100;
    public static final int    HEIGHT         = 60;

    private List<Match>        matchList;

    public MatchPanel(List<Match> matches)
    {
        matchList = matches;
        if (matchList == null) return;
        for (Match match : matchList)
            match.addObserver(this);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D gfx = (Graphics2D) g;
        paintBackground(gfx);
        if (!isBlank()) paintResult(gfx);
    }

    private void paintResult(Graphics2D gfx)
    {
        gfx.setColor(COL_RESULT);

        int white = 0;
        int black = 0;
        for (Match match : matchList)
        {
            white += match.getPointsWhite();
            black += match.getPointsBlack();
        }
        Font boldFont = gfx.getFont().deriveFont(Font.BOLD);
        gfx.setFont(boldFont);
        String text = white + " : " + black;
        Rectangle2D bounds = boldFont.getStringBounds(text, gfx.getFontRenderContext());
        LineMetrics lm = boldFont.getLineMetrics(text, gfx.getFontRenderContext());
        gfx.drawString(text, (int) (WIDTH - bounds.getWidth()) / 2, HEIGHT / 2 - lm.getStrikethroughOffset());
    }

    private boolean isBlank()
    {
        return matchList == null;
    }

    private void paintBackground(Graphics2D gfx)
    {
        gfx.setColor(isBlank() ? COL_BLANK : COL_BACKGROUND);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);
        gfx.setColor(Color.BLACK);
        gfx.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
    }

    public void matchFinished()
    {
        repaint();
    }
}
