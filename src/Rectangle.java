import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

public class Rectangle implements Runnable {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private Field field;
    private Color color;
    private double x;
    private double y;
    private double x0;
    private double y0;
    private boolean moved;

    Rectangle(Field field) {

        this.field = field;
        MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
        field.addMouseMotionListener(mouseMotionHandler);
        field.addMouseListener(mouseMotionHandler);
        color = new Color((float)Math.random(), (float)Math.random(),
                (float)Math.random());
        x = Math.random()*(field.getSize().getWidth()-WIDTH);
        y = Math.random()*(field.getSize().getHeight()-HEIGHT);
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void run() {
        try {
            while(true) {
                if (x <= 0) {
                    x = 0;
                } else
                if (x >= field.getWidth() - WIDTH) {
                    x = field.getWidth() - WIDTH;
                } else
                if (y <= 0) {
                    y = 0;
                } else
                if (y >= field.getHeight() - HEIGHT) {
                    y = field.getHeight() - HEIGHT;
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException ignored) { }
    }

    void paint(Graphics2D canvas) {
        canvas.setColor(color);
        canvas.setPaint(color);
        RoundRectangle2D rect = new RoundRectangle2D.Float((float) x, (float) y, WIDTH, HEIGHT, 50, 50);
        canvas.draw(rect);
        canvas.fill(rect);
    }

    boolean getMoved() {
        return moved;
    }

    double edgeX(double ballX, double ballY, int radius, double speedX,  double speedY) {
        if (ballY + speedY >= y && ballY + speedY <= y + HEIGHT) {
            if (ballX + speedX <= radius + x + WIDTH && ballX + speedX > x) {
                return radius + x + WIDTH;
            } else if (ballX + speedX >= x - radius && ballX + speedX < x + WIDTH) {
                return x - radius;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    double edgeY(double ballX, double ballY, int radius, double speedX,  double speedY) {
        if (ballX + speedX >= x && ballX + speedX <= x + WIDTH) {
            if (ballY + speedY <= radius + y + HEIGHT && ballY + speedY > y) {
                return radius + y + HEIGHT;
            } else if (ballY + speedY >= y - radius && ballY + speedY < y + HEIGHT) {
                return y - radius;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    public class MouseMotionHandler implements MouseMotionListener, MouseListener {

        public void mouseMoved(MouseEvent ev) {
            moved = ev.getX() > x && ev.getX() < x + WIDTH && ev.getY() > y && ev.getY() < y + HEIGHT;
        }

        public void mouseDragged(MouseEvent e) {
            if (moved) {
                x += e.getX() - x0;
                y += e.getY() - y0;
                x0 = e.getX();
                y0 = e.getY();
            }
        }

        public void mouseClicked(MouseEvent e) { }

        public void mouseEntered(MouseEvent arg0) { }

        public void mouseExited(MouseEvent arg0) { }

        public void mousePressed(MouseEvent e) {
            if (moved) {
                x0 = e.getX();
                y0 = e.getY();
            }
        }

        public void mouseReleased(MouseEvent e) { }
    }
}

