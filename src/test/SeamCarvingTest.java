import edu.princeton.cs.algs4.Picture;
import org.junit.Before;
import org.junit.Test;

public class SeamCarvingTest {

    private SeamCarver sc;

    @Before
    public void setUp() {
        sc = new SeamCarver(new Picture("/seam/5x6.png"));
    }

    @Test
    public void testRemoveLeftSeam() {
        sc.removeVerticalSeam(new int[]{0, 0, 0, 0, 0, 0});

    }

    @Test
    public void testRemoveRightSeam() {
        sc.removeVerticalSeam(new int[]{4, 4, 4, 4, 4, 4});
    }

    @Test
    public void testRemoveTopSeam() {
        sc.removeHorizontalSeam(new int[]{0, 0, 0, 0, 0});
    }

    @Test
    public void testRemoveBottomSeam() {
        sc.removeHorizontalSeam(new int[]{5, 5, 5, 5, 5});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEnergy() {
        sc.energy(5, 5);
    }

    private Double[][] getEnergyGrid(SeamCarver sc) {
        Double[][] energyGrid = new Double[sc.height()][sc.width()];
        for (int y = 0; y < sc.height(); y++) {
            for (int x = 0; x < sc.width(); x++) {
                energyGrid[y][x] = roundTo(sc.energy(x, y), 2);
            }
        }
        return energyGrid;
    }


    private double roundTo(double d, int decimalPlaces) {
        double pow = Math.pow(10, decimalPlaces);
        return Math.round(d * pow) / pow;
    }
}
