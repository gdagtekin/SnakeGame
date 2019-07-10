package yilan_oyunu;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Gökhan DAĞTEKİN
 * 
 * 2017
 */
public class SnakeGame implements ActionListener, KeyListener {

    public boolean food = false;
    public boolean endGame = false;
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    int direction = DOWN;
    private JFrame jframe;
    private DrawingPanel drawingPanel;
    public static SnakeGame snake;
    public int snakeLength = 5;
    public int[] x = new int[2400];
    public int[] y = new int[2400];
    private int xHead = x[snakeLength - 1], yHead = y[snakeLength - 1];
    public int xFood, yFood;
    public int points = 0;
    private int speed = 100, level = 5;
    private Timer t;

    public SnakeGame() {
        jframe = new JFrame("Snake Game");
        jframe.setVisible(true);
        jframe.setSize(600, 400);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(drawingPanel = new DrawingPanel());
        jframe.addKeyListener(this);
        Dimension ekran = Toolkit.getDefaultToolkit().getScreenSize();
        int Ekranx = (int) ((ekran.getWidth() - jframe.getWidth()) / 2);
        int Ekrany = (int) ((ekran.getHeight() - jframe.getHeight()) / 2);
        jframe.setLocation(Ekranx, Ekrany);
        do {
            String sLevel = JOptionPane.showInputDialog(jframe, "Enter a level (Between 1-10): ", "5");
            level = Integer.parseInt(sLevel);
        } while (level <= 0 || level > 10);
        speed = 300 / level;
        t = new Timer(speed, this);

        start();
    }

    public void start() {
        endGame = false;
        x[0] = 10;
        x[1] = 10;
        x[2] = 10;
        x[3] = 10;
        x[4] = 10;
        y[0] = 0;
        y[1] = 10;
        y[2] = 20;
        y[3] = 30;
        y[4] = 40;
        snakeLength = 5;
        points = 0;
        xHead = x[snakeLength - 1];
        yHead = y[snakeLength - 1];
        direction = DOWN;
        food = true;
        generateFood();
        t.start();
    }

    public void actionPerformed(ActionEvent e) {
        drawingPanel.repaint();

        collision();
        if (direction == UP) {
            if (yHead > 0) {
                for (int i = 0; i < snakeLength - 1; i++) {
                    y[i] = y[i + 1];
                    x[i] = x[i + 1];
                }
                y[snakeLength - 1] -= 10;
                xHead = x[snakeLength - 1];
                yHead = y[snakeLength - 1];

            } else {
                endGame = true;
            }
        } else if (direction == DOWN) {
            if (yHead < 360) {
                for (int i = 0; i < snakeLength - 1; i++) {
                    y[i] = y[i + 1];
                    x[i] = x[i + 1];
                }
                y[snakeLength - 1] += 10;
                xHead = x[snakeLength - 1];
                yHead = y[snakeLength - 1];
            } else {
                endGame = true;

            }
        } else if (direction == RIGHT) {
            if (xHead < 590) {
                for (int i = 0; i < snakeLength - 1; i++) {
                    y[i] = y[i + 1];
                    x[i] = x[i + 1];
                }
                x[snakeLength - 1] += 10;
                xHead = x[snakeLength - 1];
                yHead = y[snakeLength - 1];
            } else {
                endGame = true;

            }
        } else if (direction == LEFT) {
            if (xHead > 0) {
                for (int i = 0; i < snakeLength - 1; i++) {
                    y[i] = y[i + 1];
                    x[i] = x[i + 1];
                }
                x[snakeLength - 1] -= 10;
                xHead = x[snakeLength - 1];
                yHead = y[snakeLength - 1];
            } else {
                endGame = true;

            }
        }
        if (xHead == xFood && yHead == yFood) {
            snakeLength++;
            x[snakeLength - 1] = x[snakeLength - 2];
            y[snakeLength - 1] = y[snakeLength - 2];
            points += level;
            generateFood();
        }
        if (endGame) {
            t.stop();
        }
    }

    public boolean xFoodD = false, yFoodD = false;
    public int control = 0;

    public void generateFood() {
        food = true;
        Random rn = new Random();
        xFood = rn.nextInt(56) * 10;
        yFood = rn.nextInt(36) * 10;
        while (true) {
            if (xFood <= 20) {
                xFood = rn.nextInt(56) * 10;
            } else {
                xFoodD = true;
            }
            if (yFood <= 20) {
                yFood = rn.nextInt(36) * 10;
            } else {
                yFoodD = true;
            }
            if (xFoodD && yFoodD) {
                for (int i = 0; i < snakeLength - 1; i++) {
                    if (xFood == x[i] && yFood == y[i]) {
                        control++;
                    }
                }
                if (control != 0) {
                    xFood = rn.nextInt(56) * 10;
                    yFood = rn.nextInt(36) * 10;
                    xFoodD = false;
                    yFoodD = false;
                } else if (control == 0) {
                    break;
                }
            }
        }

    }

    public void collision() {
        for (int i = 0; i < snakeLength - 2; i++) {
            if (xHead == x[i] && yHead == y[i]) {
                endGame = true;
            }
        }
    }

    public void keyPressed(Event e) {

    }

    public static void main(String[] args) {
        snake = new SnakeGame();

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ((keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) && direction != RIGHT) {
            direction = LEFT;
        }
        if ((keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) && direction != LEFT) {
            direction = RIGHT;
        }
        if ((keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) && direction != UP) {
            direction = DOWN;
        }
        if ((keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) && direction != DOWN) {
            direction = UP;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            if (endGame) {
                if (level < 0 || level > 10) {
                    do {
                        String sLevel = JOptionPane.showInputDialog(jframe, "Enter a level (Between 1-10): ", "5");
                        level = Integer.parseInt(sLevel);
                    } while (level < 0 || level > 10);
                    speed = 300 / level;
                    t = new Timer(speed, this);
                } else {
                    start();
                }
            }
        }
        if (keyCode == KeyEvent.VK_R) {
            if (endGame) {
                do {
                    String sLevel = JOptionPane.showInputDialog(jframe, "Enter a level (Between 1-10): ", "5");
                    level = Integer.parseInt(sLevel);
                } while (level < 0 || level > 10);
                speed = 300 / level;
                t = new Timer(speed, this);
                start();
            }

        }

    }
}
