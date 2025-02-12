import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Canvas extends JPanel {
    private final float d = 400;
    public double angleX = 0;
    public double angleZ = 0;
    public double angleY = 0;
    JFrame frame;
    int r, g, b, centerX, centerY;
    int colorPhase = 0;
    Cube cube = new Cube();
    int[][] coordinates = new int[8][2];

    public Canvas() {
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

        ArrayList<int[]> coords2 = new ArrayList<>();

        rotateX(coordinates);
        //rotateZ(coords2);
        //rotateY(coords2);

        cycleRGB(10);

        // draw lines between coordinates
        g2d.setColor(new Color(r, g, b));
        drawCube(g2d, coordinates);
    }

    public void rotateX(int[][] coords) {
        double centerXCube = 0;
        double centerYCube = 0;
        double centerZCube = 0;

        for (Vector v : cube.vectors) {
            centerXCube += v.x();
            centerYCube += v.y();
            centerZCube += v.z();
        }

        centerXCube /= cube.vectors.length;
        centerYCube /= cube.vectors.length;
        centerZCube /= cube.vectors.length;

        for (int i = 0; i < cube.vectors.length; i++) {
            double x = cube.vectors[i].x() - centerXCube;
            double y = cube.vectors[i].y() - centerYCube;
            double z = cube.vectors[i].z() - centerZCube;

            double newX = x;
            double newY = y * Math.cos(angleX) - z * Math.sin(angleX);
            double newZ = y * Math.sin(angleX) + z * Math.cos(angleX);

            newX += centerXCube;
            newY += centerYCube;
            newZ += centerZCube;

            double xProj = (d * newX) / (newZ + d);
            double yProj = (d * newY) / (newZ + d);

            //int[] coord = {centerX + (int) xProj, centerY - (int) yProj};
            coords[i][0] = centerX + (int) xProj;
            coords[i][1] = centerY + (int) yProj;
        }
    }

    public void rotateZ(ArrayList<int[]> coords) {
        double centerXCube = 0;
        double centerYCube = 0;
        double centerZCube = 0;

        for (Vector v : cube.vectors) {
            centerXCube += v.x();
            centerYCube += v.y();
            centerZCube += v.z();
        }

        centerXCube /= cube.vectors.length;
        centerYCube /= cube.vectors.length;
        centerZCube /= cube.vectors.length;

        for (int i = 0; i < cube.vectors.length; i++) {
            double x = cube.vectors[i].x() - centerXCube;
            double y = cube.vectors[i].y() - centerYCube;
            double z = cube.vectors[i].z() - centerZCube;

            double newX = x * Math.cos(angleZ) - y * Math.sin(angleZ);
            double newY = x * Math.sin(angleZ) + y * Math.cos(angleZ);
            double newZ = z;

            newX += centerXCube;
            newY += centerYCube;
            newZ += centerZCube;

            double xProj = (d * newX) / (newZ + d);
            double yProj = (d * newY) / (newZ + d);

            int[] coord = {centerX + (int) xProj, centerY - (int) yProj};

            coords.add(coord);
        }
    }

    public void rotateY(ArrayList<int[]> coords) {
        double centerXCube = 0;
        double centerYCube = 0;
        double centerZCube = 0;

        for (Vector v : cube.vectors) {
            centerXCube += v.x();
            centerYCube += v.y();
            centerZCube += v.z();
        }

        centerXCube /= cube.vectors.length;
        centerYCube /= cube.vectors.length;
        centerZCube /= cube.vectors.length;

        for (int i = 0; i < cube.vectors.length; i++) {
            double x = cube.vectors[i].x() - centerXCube;
            double y = cube.vectors[i].y() - centerYCube;
            double z = cube.vectors[i].z() - centerZCube;

            double newX = x * Math.cos(angleY) + z * Math.sin(angleY);
            double newZ = -x * Math.sin(angleY) + z * Math.cos(angleY);
            double newY = y;

            newX += centerXCube;
            newY += centerYCube;
            newZ += centerZCube;

            double xProj = (d * newX) / (newZ + d);
            double yProj = (d * newY) / (newZ + d);

            int[] coord = {centerX + (int) xProj, centerY - (int) yProj};

            coords.add(coord);
        }
    }

    public void cycleRGB(int step) {
        switch (colorPhase) {
            case 0:
                this.g += step;
                if (this.g >= 255) {
                    this.g = 255;
                    colorPhase = 1;
                }
                break;
            case 1:
                r -= step;
                if (r <= 0) {
                    r = 0;
                    colorPhase = 2;
                }
                break;
            case 2:
                b += step;
                if (b >= 255) {
                    b = 255;
                    colorPhase = 3;
                }
                break;
            case 3:
                this.g -= step;
                if (this.g <= 0) {
                    this.g = 0;
                    colorPhase = 4;
                }
                break;
            case 4:
                r += step;
                if (r >= 255) {
                    r = 255;
                    colorPhase = 5;
                }
                break;
            case 5:
                b -= step;
                if (b <= 0) {
                    b = 0;
                    colorPhase = 0;
                }
                break;
        }
    }

    public void drawCube(Graphics2D g2d, int[][] coords) {
        g2d.drawLine(coords[0][0], coords[0][1], coords[1][0], coords[1][1]);
        g2d.drawLine(coords[1][0], coords[1][1], coords[3][0], coords[3][1]);
        g2d.drawLine(coords[3][0], coords[3][1], coords[2][0], coords[2][1]);
        g2d.drawLine(coords[2][0], coords[2][1], coords[0][0], coords[0][1]);

        g2d.drawLine(coords[4][0], coords[4][1], coords[5][0], coords[5][1]);
        g2d.drawLine(coords[5][0], coords[5][1], coords[7][0], coords[7][1]);
        g2d.drawLine(coords[7][0], coords[7][1], coords[6][0], coords[6][1]);
        g2d.drawLine(coords[6][0], coords[6][1], coords[4][0], coords[4][1]);

        g2d.drawLine(coords[0][0], coords[0][1], coords[4][0], coords[4][1]);
        g2d.drawLine(coords[1][0], coords[1][1], coords[5][0], coords[5][1]);
        g2d.drawLine(coords[2][0], coords[2][1], coords[6][0], coords[6][1]);
        g2d.drawLine(coords[3][0], coords[3][1], coords[7][0], coords[7][1]);
    }
}
