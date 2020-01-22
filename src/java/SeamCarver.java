import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SeamCarver {

    private static final int BORDER_ENERGY = 1000;

    private static class Pixel {
        int x, y;

        Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class ColorUtil {

        public static int getRed(int value) {
            return (value >> 16) & 0xFF;
        }

        public static int getGreen(int value) {
            return (value >> 8) & 0xFF;
        }

        public static int getBlue(int value) {
            return value & 0xFF;
        }
    }

    private Picture picture;
    private boolean transposed;
    private int[] lastSeamRemoved;
    private double[][] energyGrid;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture cannot be null");
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) throw new IllegalArgumentException("x or y are out of bounds");
        if (x == 0 || x == width()-1 || y == 0 || y == height()-1) return BORDER_ENERGY;
        final int xGradientSquare = gradientSquare(new Pixel(x - 1, y), new Pixel(x + 1, y));
        final int yGradientSquare = gradientSquare(new Pixel(x, y - 1), new Pixel(x, y + 1));
        return Math.sqrt(xGradientSquare + yGradientSquare);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        startStopWatch("findVerticalSeam");

        startStopWatch("initEnergyGrid");
        initEnergyGrid();
        stopStopwatch("initEnergyGrid");

        startStopWatch("initDistTo");
        double[][] distTo = initDistTo(energyGrid[0]);
        stopStopwatch("initDistTo");

        int[][] edgeTo = new int[picture.height()][picture.width()];

        startStopWatch("relaxBelowPixels");
        for (int y = 0; y < picture.height() - 1; y++) {
            for (int x = 0; x < picture.width(); x++) {
                relaxBelowPixels(energyGrid, distTo, edgeTo, y, x);
            }
        }
        stopStopwatch("relaxBelowPixels");

        int minEnergyX = findMinEnergy(distTo[picture.height() - 1]);

        final int[] path = pathToBottom(edgeTo, minEnergyX);

//        printStats(energyGrid, distTo, edgeTo, minEnergyX, path);

        stopStopwatch("findVerticalSeam");
        return path;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // TODO Don’t explicitly transpose the Picture or int[][] until you need to do so.
        // TODO For example, if you perform a sequence of 50 consecutive horizontal seam removals, you should need only two transposes (not 100).
        transpose();
        final int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        startStopWatch("removeVerticalSeam");

        validateSeam(seam, height(), width()-1);
        Picture newPic = new Picture(picture.width()-1, picture.height());
        for (int y = 0; y < seam.length; y++) {
            int newX = 0;
            for (int oldX = 0; oldX < picture.width(); oldX++) {
                if (oldX != seam[y]) {
                    newPic.setRGB(newX++, y, picture.getRGB(oldX, y));
                }
            }
        }
        stopStopwatch("removeVerticalSeam");
        lastSeamRemoved = seam;
        picture = newPic;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width(), height()-1);
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    private void validateSeam(int[] seam, int expectedLen, int picSize) {
        if (picSize <= 0) throw new IllegalArgumentException("picture is too small, cannot proceed");
        if (seam == null || seam.length != expectedLen) throw new IllegalArgumentException("seam cannot be null or exceed picture size");
        validateRange(seam[0], 0, picSize);
        for (int i = 1; i < seam.length; i++) {
            validateRange(seam[i], 0, picSize);
            if (Math.abs(seam[i-1] - seam[i]) > 1) throw new IllegalArgumentException("seam elements differ by more than 1");
        }
    }

    private void validateRange(int el, int min, int max) {
        if (el < min || el > max) throw new IllegalArgumentException("element is out of bounds");
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        final SeamCarver sc = new SeamCarver(new Picture("/seam/6x5.png"));
        final int[] vSeam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(vSeam));
        final int[] hSeam = sc.findHorizontalSeam();
        System.out.println(Arrays.toString(hSeam));
    }

    private int gradientSquare(Pixel p1, Pixel p2) {
        final int c1 = picture.getRGB(p1.x, p1.y);
        final int c2 = picture.getRGB(p2.x, p2.y);
        int r = ColorUtil.getRed(c1) - ColorUtil.getRed(c2);
        int g = ColorUtil.getGreen(c1) - ColorUtil.getGreen(c2);
        int b = ColorUtil.getBlue(c1) - ColorUtil.getBlue(c2);

        return r*r + g*g + b*b;
    }

    // Transpose picture. Note that double transpose yields original picture, i.e. picture after transpose(transpose()) remains the same
    private void transpose() {
        Picture newPic = new Picture(height(), width());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                newPic.setRGB(y, x, picture.getRGB(x, y));
            }
        }
        picture = newPic;
        transposed = !transposed;
    }

    private int findMinEnergy(double[] distTo) {
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyX = -1;
        for (int x = 0; x < picture.width(); x++) {
            if (distTo[x] < minEnergy) {
                minEnergy = distTo[x];
                minEnergyX = x;
            }
        }
        return minEnergyX;
    }

    private int[] pathToBottom(int[][] edgeTo, int x) {
        int[] seam = new int[picture.height()];
        seam[picture.height()-1] = x;
        for (int y = picture.height()-1; y > 0; y--) {
            x = seam[y-1] = edgeTo[y][x];
        }
        return seam;
    }

    private void relaxBelowPixels(double[][] energyGrid, double[][] distTo, int[][] edgeTo, int y, int x) {
        final Pixel from = new Pixel(x, y);
        relax(energyGrid, distTo, edgeTo, from, new Pixel(x, y+1));
        relax(energyGrid, distTo, edgeTo, from, new Pixel(x-1, y+1));
        relax(energyGrid, distTo, edgeTo, from, new Pixel(x+1, y+1));
    }

    private void relax(double[][] energyGrid, double[][] distTo, int[][] edgeTo, Pixel from, Pixel to) {
        if (to.x < 0 || to.x >= picture.width()) return;

        final double distFrom = distTo[from.y][from.x];
        double newDist = distFrom + energyGrid[to.y][to.x];
        if (newDist < distTo[to.y][to.x]) {
            distTo[to.y][to.x] = newDist;
            edgeTo[to.y][to.x] = from.x;
        }
    }

    private void initEnergyGrid() {
        final int picHeight = picture.height();
        final int picWidth = picture.width();
        if (lastSeamRemoved == null) {
            energyGrid = new double[picHeight][picWidth];
            for (int y = 0; y < picHeight; y++) {
                for (int x = 0; x < picWidth; x++) {
                    energyGrid[y][x] = energy(x, y);
                }
            }
        }
        else {
            double[][] newEnergyGrid = new double[picHeight][picWidth];
            for (int y = 0; y < picHeight; y++) {
                final int rightX = lastSeamRemoved[y];
                final int leftX = rightX > 0 ? rightX - 1 : 0;
                System.arraycopy(energyGrid[y], 0, newEnergyGrid[y], 0, leftX);
                System.arraycopy(energyGrid[y], rightX+2, newEnergyGrid[y], rightX+1, picWidth-rightX-1);
                for (int x = leftX; x <= rightX; x++) {
                    newEnergyGrid[y][x] = energy(x, y);
                }
            }
            energyGrid = newEnergyGrid;
        }

    }

    private double[][] initDistTo(double[] firstEnergyRow) {
        double[][] distTo = new double[picture.height()][picture.width()];
        System.arraycopy(firstEnergyRow, 0, distTo[0], 0, picture.width());
        for (int i = 1; i < distTo.length; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
        }
        return distTo;
    }

    private void printStats(double[][] energyGrid, double[][] distTo, int[][] edgeTo, int minEnergyX, int[] path) {
        System.out.println("Energy grid:");
        printDoubles(energyGrid);
        System.out.println("Min distances:");
        printDoubles(distTo);
        System.out.println("Min paths:");
        printIntegers(edgeTo);
        System.out.println("minEnergyX: " + minEnergyX);
    }

    private void printDoubles(double[][] doubles) {
        for (double[] doubleArray : doubles) {
            for (double d : doubleArray) {
                System.out.printf("%9.2f ", d);
            }
            System.out.println();
        }
    }

    private void printIntegers(int[][] integers) {
        for (int[] integerArray : integers) {
            for (int i : integerArray) {
                System.out.printf("%d ", i);
            }
            System.out.println();
        }
    }


    // for performance testing
    // need to call startStopWatch/getElapsedTime in pairs for correct work

//    private void startStopWatch(String name) {}
//    private void stopStopwatch(String name) {}
//    public void setMeasureTime(boolean measureTime) {}

    private boolean measureTime;
    private Map<String, Stopwatch> stopwatches = new HashMap<>();
    private Map<String, Double> measuredTimes = new LinkedHashMap<>();

    private void startStopWatch(String name) {
        if (measureTime) {
            stopwatches.put(name, new Stopwatch());
        }
    }

    private void stopStopwatch(String name) {
        if (measureTime) {
            if (!stopwatches.containsKey(name)) throw new IllegalArgumentException("No stopwatch associated with " + name);
            double time = stopwatches.remove(name).elapsedTime();
            measuredTimes.merge(name, time, Double::sum);
        }
    }

    public void setMeasureTime(boolean measureTime) {
        this.measureTime = measureTime;
    }

    public void printMeasuredTimes() {
        measuredTimes.forEach((name, time) -> System.out.printf("Time for %s: %f\r\n", name, time));
    }

}
