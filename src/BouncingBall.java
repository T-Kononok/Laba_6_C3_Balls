import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BouncingBall implements Runnable {

    private static final int MAX_RADIUS = 50;
    private static final int MIN_RADIUS = 3;
    private static final int MAX_SPEED = 15;
    private Field field;
    private int radius;
    private double x;
    private double y;
    private int speed;
    private double speedX;
    private double speedY;
    private BufferedImage image;

    BouncingBall(Field field) throws IOException {

        this.field = field;
        radius = (int) (Math.random() * (MAX_RADIUS - MIN_RADIUS)) + MIN_RADIUS;
        speed = 5 * MAX_SPEED / radius;
        if (speed>MAX_SPEED)
            speed = MAX_SPEED;
        double angle = Math.random()*2*Math.PI;
        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);
        image = ImageIO.read(new File("D:/Джава/Laba_6_C3_Balls/ball" + (int)(Math.random()*8) + ".png"));
        x = Math.random()*(field.getSize().getWidth()-2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight()-2*radius) + radius;
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void run() {
        try {
            while(true) {
                field.canMove();
                if (x + speedX <= radius) {
                    speedX = -speedX;
                    x = radius;
                } else
                if (x + speedX >= field.getWidth() - radius) {
                    speedX = -speedX;
                    x = field.getWidth()-radius;
                } else
                if (y + speedY <= radius) {
                    speedY = -speedY;
                    y = radius;
                } else
                if (y + speedY >= field.getHeight() - radius) {
                    speedY = -speedY;
                    y = field.getHeight()-radius;
                } else {
                    x += speedX;
                    y += speedY;
                    for (Rectangle rectangle : field.getRectangles()) {
                        if (rectangle.edgeX(x, y, radius, speedX, speedY) != 0) {
                            x = rectangle.edgeX(x, y, radius, speedX, speedY);
                            speedX = -speedX;
                        }
                        if (rectangle.edgeY(x, y, radius, speedX, speedY) != 0) {
                            y = rectangle.edgeY(x, y, radius, speedX, speedY);
                            speedY = -speedY;
                        }
                    }
                }
                Thread.sleep(16-speed);
            }
        } catch (InterruptedException ignored) { }
    }

    void paint(Graphics2D canvas) {

        canvas.drawImage(image,(int) x-radius, (int) y-radius, 2*radius,2*radius, null);
    }
}

