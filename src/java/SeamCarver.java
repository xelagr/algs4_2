import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

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

    private int[][] colors;
    private boolean vertical;
    private double[][] energyGrid;
    private int height;
    private int width;
//    public final StopwatchManager stopwatchManager = new StopwatchManager();

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture cannot be null");
        this.vertical = true;
        this.height = picture.height();
        this.width = picture.width();
        initColors(picture);
        initEnergy();
    }

    // current picture
    public Picture picture() {
        if (!vertical) transpose();
        return recreatePicture();
    }

    private Picture recreatePicture() {
        Picture picture = new Picture(internalWidth(), internalHeight());
        for (int y = 0; y < internalHeight(); y++) {
            for (int x = 0; x < internalWidth(); x++) {
                picture.setRGB(x, y, colors[y][x]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!vertical) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        validateRange(x, 0, internalWidth()-1);
        validateRange(y, 0, internalHeight()-1);
        return energyGrid[y][x];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (!vertical) transpose();
        return findVerticalSeamInternal();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (vertical) transpose();
        return findVerticalSeamInternal();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (!vertical) transpose();
        removeVerticalSeamInternal(seam);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (vertical) transpose();
        removeVerticalSeamInternal(seam);
    }

    private int internalWidth() {
        return vertical ? width : height;
    }

    // height of current picture
    private int internalHeight() {
        return vertical ? height : width;
    }

    private void initColors(Picture picture) {
        this.colors = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.colors[y][x] = picture.getRGB(x, y);
            }
        }
    }

    private void initEnergy() {
        this.energyGrid = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.energyGrid[y][x] = calculateEnergy(x, y);
            }
        }
    }

    private double calculateEnergy(int x, int y) {
        if (x == 0 || x == internalWidth() -1 || y == 0 || y == internalHeight() -1) return BORDER_ENERGY;
        final int xGradientSquare = gradientSquare(new Pixel(x - 1, y), new Pixel(x + 1, y));
        final int yGradientSquare = gradientSquare(new Pixel(x, y - 1), new Pixel(x, y + 1));
        return Math.sqrt(xGradientSquare + yGradientSquare);
    }

    private int[] findVerticalSeamInternal() {
//        stopwatchManager.start("findVerticalSeamInternal");
        double[][] distTo = initDistTo();
        int[][] edgeTo = new int[internalHeight()][internalWidth()];

        for (int y = 0; y < internalHeight()-1; y++) {
            for (int x = 0; x < internalWidth(); x++) {
                relaxBelowPixels(distTo, edgeTo, y, x);
            }
        }

        int minEnergyX = findMinEnergy(distTo[internalHeight()-1]);
        int[] path = pathToBottom(edgeTo, minEnergyX);
//        stopwatchManager.stop("findVerticalSeamInternal");
        return path;
    }

    private void removeVerticalSeamInternal(int[] seam) {
//        stopwatchManager.start("removeVerticalSeamInternal");

        validateSeam(seam, internalHeight(), internalWidth() -1);

//        stopwatchManager.start("updateEnergyGrid");
        updateColors(seam);
        updateEnergy(seam);
//        stopwatchManager.stop("updateEnergyGrid");

//        stopwatchManager.stop("removeVerticalSeamInternal");
    }

    private void updateEnergy(int[] seam) {
        for (int y = 0; y < internalHeight(); y++) {
            final int rightX = seam[y];
            double[] newEnergyRow = new double[internalWidth()];

            int rightLen = Math.max(internalWidth() - rightX, 0);
            System.arraycopy(energyGrid[y], rightX + 1, newEnergyRow, rightX, rightLen);
            if (rightX < newEnergyRow.length - 1) {
                newEnergyRow[rightX] = calculateEnergy(rightX, y);
            }

            int leftX = rightX - 1;
            int leftLen = Math.max(leftX, 0);
            System.arraycopy(energyGrid[y], 0, newEnergyRow, 0, leftLen);
            if (leftX >= 0) {
                newEnergyRow[leftX] = calculateEnergy(leftX, y);
            }
            energyGrid[y] = newEnergyRow;
        }
    }

    private void updateColors(int[] seam) {
        for (int y = 0; y < seam.length; y++) {
            int[] newColors = new int[internalWidth()-1];
            System.arraycopy(colors[y], 0, newColors, 0, seam[y]);
            System.arraycopy(colors[y], seam[y]+1, newColors, seam[y], internalWidth() - seam[y] - 1);
            colors[y] = newColors;
        }
        if (vertical) width--;
        else height--;
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
        if (el < min || el > max) throw new IllegalArgumentException("element " + el + " should be between " + min + " and " + max);
    }

    private int gradientSquare(Pixel p1, Pixel p2) {
        final int c1 = colors[p1.y][p1.x];
        final int c2 = colors[p2.y][p2.x];
        int r = ColorUtil.getRed(c1) - ColorUtil.getRed(c2);
        int g = ColorUtil.getGreen(c1) - ColorUtil.getGreen(c2);
        int b = ColorUtil.getBlue(c1) - ColorUtil.getBlue(c2);

        return r*r + g*g + b*b;
    }

    // Transpose picture. Note that double transpose yields original picture, i.e. picture after transpose(transpose()) remains the same
    private void transpose() {
        int[][] newColors = new int[internalWidth()][internalHeight()];
        double[][] newEnergyGrid = new double[internalWidth()][internalHeight()];
        for (int x = 0; x < internalWidth(); x++) {
            for (int y = 0; y < internalHeight(); y++) {
                newColors[x][y] = colors[y][x];
                newEnergyGrid[x][y] = energyGrid[y][x];
            }
        }
        colors = newColors;
        energyGrid = newEnergyGrid;
        vertical = !vertical;
    }

    private int findMinEnergy(double[] distTo) {
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyX = -1;
        for (int x = 0; x < internalWidth(); x++) {
            if (distTo[x] < minEnergy) {
                minEnergy = distTo[x];
                minEnergyX = x;
            }
        }
        return minEnergyX;
    }

    private int[] pathToBottom(int[][] edgeTo, int x) {
        int[] seam = new int[internalHeight()];
        seam[internalHeight() -1] = x;
        for (int y = internalHeight() -1; y > 0; y--) {
            x = seam[y-1] = edgeTo[y][x];
        }
        return seam;
    }

    // TODO can be probably optimized by recalculating only values around last removed seam
    private void relaxBelowPixels(double[][] distTo, int[][] edgeTo, int y, int x) {
        final Pixel from = new Pixel(x, y);
        relax(distTo, edgeTo, from, new Pixel(x, y+1));
        relax(distTo, edgeTo, from, new Pixel(x-1, y+1));
        relax(distTo, edgeTo, from, new Pixel(x+1, y+1));
    }

    private void relax(double[][] distTo, int[][] edgeTo, Pixel from, Pixel to) {
        if (to.x < 0 || to.x >= internalWidth()) return;

        final double distFrom = distTo[from.y][from.x];
        double newDist = distFrom + energyGrid[to.y][to.x];
        if (newDist < distTo[to.y][to.x]) {
            distTo[to.y][to.x] = newDist;
            edgeTo[to.y][to.x] = from.x;
        }
    }

    private double[][] initDistTo() {
        double[][] distTo = new double[internalHeight()][internalWidth()];
        System.arraycopy(energyGrid[0], 0, distTo[0], 0, internalWidth());
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

    public static void main(String[] args) {
        String picString = "#000203 #080006 #060109 #070401 #010707 #020001 \n" +
                "       #050202 #040209 #090509 #030409 #010209 #040200 \n" +
                "       #090401 #060306 #080208 #020907 #060706 #010606 \n" +
                "       #020501 #080200 #050901 #060100 #070707 #060306 \n" +
                "       #030409 #080509 #060209 #010502 #080506 #030902 \n" +
                "       #020300 #020605 #010307 #020406 #010707 #050400";
        Picture picture = parsePicString(picString);
        SeamCarver sc = new SeamCarver(picture);
        printEnergy(sc);
        sc.removeHorizontalSeam(new int[] {1, 1, 1, 1, 1, 1});
        printEnergy(sc);
        int[] seam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(seam));
    }

    private static Picture parsePicString(String s) {
        String[] lines = s.split("\n");
        String[][] hexColors = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String[] colors = lines[i].trim().split(" ");
            hexColors[i] = colors;
        }
        Picture picture = new Picture(hexColors[0].length, hexColors.length);
        for (int y = 0; y < hexColors.length; y++) {
            for (int x = 0; x < hexColors[y].length; x++) {
                int color = Integer.parseInt(hexColors[y][x].substring(1), 16);
                picture.setRGB(x, y, color);
            }
        }
        System.out.println(picture);
        return picture;
    }

    private static void printEnergy(SeamCarver sc) {
        StdOut.printf("Printing energy calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.2f ", sc.energy(col, row));
            StdOut.println();
        }
    }

}
