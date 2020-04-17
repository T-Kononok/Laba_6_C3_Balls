import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel {

    private boolean paused;
    private ArrayList<BouncingBall> balls = new ArrayList<>(10);

    Field() {
        setBackground(Color.WHITE);
        Timer repaintTimer = new Timer(10, ev -> repaint());
        repaintTimer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }

    void addBall() {
        balls.add(new BouncingBall(this));
    }

    void pause() {
        paused = true;
    }

    synchronized void resume() {
        paused = false;
        notifyAll();
    }

    synchronized void canMove() throws InterruptedException {
        if (paused) {
            wait();
        }
    }
}
