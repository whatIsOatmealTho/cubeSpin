public class Cube {
    Vector[] vectors;

    public Cube() {
        vectors = new Vector[8];
        for (int i = 0; i < 8; i++) {
            vectors[i] = new Vector(1,1,1);
        }
        vectors[0] = new Vector(100,100,300);
        vectors[1] = new Vector(-100,100,300);
        vectors[2] = new Vector(100,-100,300);
        vectors[3] = new Vector(-100,-100,300);

        vectors[4] = new Vector(100,100,500);
        vectors[5] = new Vector(-100,100,500);
        vectors[6] = new Vector(100,-100,500);
        vectors[7] = new Vector(-100,-100,500);
    }
}
