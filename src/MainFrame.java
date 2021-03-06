import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem = new JMenuItem("Остановить всех");
    private JMenuItem pauseRandMenuItem = new JMenuItem("Остановить случайного");
    private JMenuItem resumeMenuItem = new JMenuItem("Возобновить движение");
    private Field field = new Field();

    private MainFrame() {

        super("Лаба 6: мячики");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenuItem ballItem = new JMenuItem("Добавить мяч");
        ballItem.addActionListener(ev -> {
            try { field.addBall(); }
            catch (IOException e) { e.printStackTrace(); }
            pauseMenuItem.setEnabled(true);
            pauseRandMenuItem.setEnabled(true);
        });
        menuBar.add(ballItem);
        JMenuItem rectangleItem = new JMenuItem("Добавить препятствие");
        rectangleItem.addActionListener(ev -> field.addRectangle());
        menuBar.add(rectangleItem);

        pauseMenuItem.addActionListener(ev -> {
            field.pause();
            pauseMenuItem.setEnabled(false);
            pauseRandMenuItem.setEnabled(false);
            resumeMenuItem.setEnabled(true);
        });
        menuBar.add(pauseMenuItem);
        pauseMenuItem.setEnabled(false);

        pauseRandMenuItem.addActionListener(ev -> {
            field.pauseRand();
            pauseMenuItem.setEnabled(true);
            pauseRandMenuItem.setEnabled(false);
            resumeMenuItem.setEnabled(true);
        });
        menuBar.add(pauseRandMenuItem);
        pauseRandMenuItem.setEnabled(false);

        resumeMenuItem.addActionListener(ev -> {
            field.resume();
            pauseMenuItem.setEnabled(true);
            pauseRandMenuItem.setEnabled(true);
            resumeMenuItem.setEnabled(false);
        });
        menuBar.add(resumeMenuItem);
        resumeMenuItem.setEnabled(false);

        getContentPane().add(field, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
