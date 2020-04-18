import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel {

    private boolean paused;
    private ArrayList<BouncingBall> balls = new ArrayList<>(10);
    private ArrayList<Rectangle> rectangles = new ArrayList<>(10);
    private Rectangle mov = null;

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
        for (Rectangle rectangle: rectangles) {
            rectangle.paint(canvas);
            if (rectangle.getMoved()) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                mov = rectangle;
            }
        }
        if (mov != null)
            if (!mov.getMoved())
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    void addBall() throws IOException {
        balls.add(new BouncingBall(this));
    }

    void addRectangle() {
        rectangles.add(new Rectangle(this));
    }

    void pause() {
        paused = true;
    }

    synchronized void resume() {
        paused = false;
        notifyAll();
    }

    ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    synchronized void canMove() throws InterruptedException {
        if (paused) {
            wait();
        }
    }
}
