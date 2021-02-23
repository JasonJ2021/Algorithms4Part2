package SeamCarver;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int height;
    private int width;
    private Picture picturecopy;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if (picture == null){
            throw new IllegalArgumentException();
        }
        this.height = picture.height();
        this.width = picture.width();
        picturecopy = new Picture(picture);
    }

    // current picture
    public Picture picture(){
        return new Picture(picturecopy);
    }

    // width of current picture
    public int width(){
        return width;
    }

    // height of current picture
    public int height(){
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        validateCol(x);
        validateRow(y);
        if (x == 0 || x == width -1 ||  y == 0 || y == height -1){
            return 1000.0;
        }
        int left = picturecopy.getRGB(x-1,y);
        int right = picturecopy.getRGB(x+1,y);
        int up = picturecopy.getRGB(x,y-1);
        int down = picturecopy.getRGB(x,y+1);
        return Math.sqrt(compute(left,right) + compute(up,down));
    }
    private double compute(int rgb1 , int rgb2){
        int r1 = (rgb1 >> 16)&0xff;
        int g1 = (rgb1 >> 8)&0xff;
        int b1 = (rgb1 >> 0)&0xff;
        int r2 = (rgb2 >> 16)&0xff;
        int g2 = (rgb2 >> 8)&0xff;
        int b2 = (rgb2 >> 0)&0xff;
        return Math.pow(r1-r2,2) + Math.pow(g1-g2 ,2) + Math.pow(b1-b2,2);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    // sequence of indices for vertical seam

    public int[] findVerticalSeam(){
        //这里因为上面energy 行列倒换了 ， 所以数组也要倒换
        double[][] energy = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energy[col][row] = energy(col,row);
            }
        }
        int[][] edgeTo = new int[width][height];
        double[][] costTo = new double[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0){
                    costTo[col][row] = energy[col][row];
                }else{
                    costTo[col][row] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int row = 0; row < height - 1; row++) {
            for (int col = 0; col < width; col++) {
                if (costTo[col][row + 1] > costTo[col][row] + energy[col][row+1]){
                    costTo[col][row+ 1] = costTo[col][row] + energy[col][row+1];
                    edgeTo[col][row + 1] = col;
                }
                if (col -1 >0){
                    if (costTo[col-1][row + 1 ] > costTo[col][row] + energy[col-1][row+1]){
                        costTo[col-1][row +1] = costTo[col][row] + energy[col-1][row+1];
                        edgeTo[col-1][row + 1] = col;
                    }
                }
                if (col +1 < width){
                    if (costTo[col+1][row + 1] > costTo[col][row] + energy[col+1][row + 1]){
                        costTo[col+1][row + 1] = costTo[col][row] + energy[col+1][row+1];
                        edgeTo[col+1][row + 1] = col;
                    }
                }
            }

        }
        int minCol = 0;
        double minenergy = Double.POSITIVE_INFINITY;
        for (int col = 0; col < width; col++) {
            if (minenergy > costTo[col][height-1]){
                minenergy = costTo[col][height-1];
                minCol = col;
            }
        }
        int[] seam = new int[height];
        for (int row = height-1; row >=0; row--) {
            seam[row] = minCol;
            minCol = edgeTo[minCol][row];
        }
        return seam;
    }
    private void validateCol(int col){
        if (col < 0 || col > width - 1){
            throw new IllegalArgumentException();
        }
    }
    private void validateRow(int row){
        if (row < 0 || row > height -1){
            throw new IllegalArgumentException();
        }
    }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){
        if (seam == null) {
            throw new IllegalArgumentException("the argument to removeHorizontalSeam() is null\n");
        }
        if (seam.length != width) {
            throw new IllegalArgumentException("the length of seam not equal width\n");
        }
        validateSeam(seam);
        if (height <= 1) {
            throw new IllegalArgumentException("the height of the picture is less than or equal to 1\n");
        }
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if (seam == null)throw new IllegalArgumentException();
        if (seam.length != height)throw new IllegalArgumentException();
        validateSeam(seam);
        if (width <= 1){
            throw new IllegalArgumentException();
        }
        Picture temp = new Picture(width - 1 , height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < -1; col++) {
                validateCol(seam[row]);
                if (seam[row] > col){
                    temp.setRGB(col,row,picturecopy.getRGB(col,row));
                }else{
                    temp.setRGB(col,row,picturecopy.getRGB(col+1,row));
                }
            }
        }
        picturecopy = temp;
        width--;
    }
    private void transpose(){
        //翻转一个图像
        Picture tempPicture = new Picture(height,width);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                tempPicture.setRGB(col,row,picturecopy.getRGB(row,col)) ;
            }
        }
        picturecopy = tempPicture;
        int temp = width;
        width = height;
        height = temp;
    }
    //  unit testing (optional)
    private void validateSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("two adjacent entries differ by more than 1 in seam\n");
            }
        }
    }
    public static void main(String[] args){}

}