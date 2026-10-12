import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Canvas extends JPanel {
    private static final double D = 400;
    private static final int[][] EDGES = {
            {0, 1}, {1, 3}, {3, 2}, {2, 0},
            {4, 5}, {5, 7}, {7, 6}, {6, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    public double angleX = 0;
    public double angleZ = 0;
    public double angleY = 0;
    JFrame frame;
    int centerX, centerY;
    private final double[] center;
    private final double[][] offsets;

    public Canvas() {
        Vector[] vectors = new Cube().vectors;
        double cx = Arrays.stream(vectors).mapToInt(Vector::x).average().orElse(0);
        double cy = Arrays.stream(vectors).mapToInt(Vector::y).average().orElse(0);
        double cz = Arrays.stream(vectors).mapToInt(Vector::z).average().orElse(0);
        center = new double[]{cx, cy, cz};
        offsets = Arrays.stream(vectors)
                .map(v -> new double[]{v.x() - cx, v.y() - cy, v.z() - cz})
                .toArray(double[][]::new);

        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(centerX, 0, centerX, getHeight());
        g2d.drawLine(0, centerY, getWidth(), centerY);

        double cos = Math.cos(angleY);
        double sin = Math.sin(angleY);
        int[][] coords = new int[8][2];
        for (int i = 0; i < 8; i++) {
            double[] o = offsets[i];
            double x = o[0] * cos + o[2] * sin + center[0];
            double y = o[1] + center[1];
            double z = -o[0] * sin + o[2] * cos + center[2];
            coords[i][0] = centerX + (int) (D * x / (z + D));
            coords[i][1] = centerY + (int) (D * y / (z + D));
        }

        g2d.setColor(Color.BLACK);
        for (int[] e : EDGES) {
            g2d.drawLine(coords[e[0]][0], coords[e[0]][1], coords[e[1]][0], coords[e[1]][1]);
        }
    }
}
