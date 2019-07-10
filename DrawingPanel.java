/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yilan_oyunu;

/**
 *
 * @author Gökhan DAĞTEKİN
 * 
 * 2017
 */
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
public class DrawingPanel extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        SnakeGame yilan = SnakeGame.snake;
        g.setColor(Color.BLACK);
        for (int i = 0; i < yilan.snakeLength; i++) {
            g.fillRect(yilan.x[i], yilan.y[i], 10, 10);
        }
        if (yilan.food) {
            g.setColor(Color.RED);
            g.fillRect(yilan.xFood, yilan.yFood, 10, 10);
        }
        if (yilan.endGame == false) {
            g.setColor(Color.BLACK);
            g.drawString("Points: " + yilan.points, 280, 20);
        }
        if (yilan.endGame) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER.", 240, 150);
            g.drawString("POINTS: " + yilan.points, 240, 170);
            g.drawString("Press SPACE button to play again, press R to set the level.", 120, 190);
        }
    }
}
