import javax.swing.Timer;

public class Main {
    static Canvas canvas;

    public static void main(String[] args) throws InterruptedException {
        canvas = new Canvas();
        Timer timer = new Timer(30, e -> {
            canvas.angleX += Math.toRadians(3);
            canvas.angleZ += Math.toRadians(3);
            canvas.angleY += Math.toRadians(3);
            canvas.repaint();
        });
        timer.start();
    }
}
