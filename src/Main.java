import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

    static BufferedImage s_img;
    final static int s_WIDTH = 2560*2;
    final static int s_HEIGHT = 1600*2;
    final static double winleft = -1.75;
    final static double winright = .6;
    final static double winwidth = winright-winleft;
    final static double winheight = winwidth*1600./2560.;
    final static double wintop = 1.3;
    final static double winbot = wintop-winheight;
    final static int total_iter = 100*2;
    final static int[] black = new int[]{0,0,0,0};
    static int deck_size = s_HEIGHT*s_WIDTH;

    static Cell[] s_pixels_To_Check;

    public static void main(String args[])throws IOException{
        s_img = new BufferedImage(s_WIDTH, s_HEIGHT,BufferedImage.TYPE_INT_RGB);
        initMatrix();
        for (int counter=0; counter<total_iter; counter++){
            System.out.println("iterations: " + counter + " of " + total_iter);
            iterMatrix();
        }
        save();
    }//main() ends here



    private static void save(){
        save("Output.jpg", "jpg");
    }

    private static void save(String pathname, String formatname){
        try{
            File f = new File(pathname);
            ImageIO.write(s_img, formatname, f);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private static void iterMatrix(){
        int newdecksize = 0;
        for (int i = 0; i<deck_size; i++){
            Cell p = s_pixels_To_Check[i];
            if (p.iterVal()){
                setARGB(p,getcolor(p.getIterationsSoFar()));
            }
            else{
                s_pixels_To_Check[newdecksize] = p;
                newdecksize++;
            }
        }
        deck_size = newdecksize;
//        System.out.println(" deck size now: " + deck_size);
    }

    private static void initMatrix(){
        s_pixels_To_Check = new Cell[deck_size];
        int newdecksize = 0;
        for (int x = 0; x<s_WIDTH; x++){
            for (int y = 0; y<s_HEIGHT; y++){
                Complex val = getFixedValue(x,y);
                if (val.abs()>2.){
                    setARGB(x,y,getcolor(0));
                    continue;
                }
                Cell p = new Cell(val,x,y);
                setARGB(p,black);
                s_pixels_To_Check[newdecksize] = p;
                newdecksize++;
            }
        }
        deck_size = newdecksize;
        System.out.println("Pixels created: " + newdecksize);

    }

    private static Complex getFixedValue(int x, int y){
        return new Complex(winleft+(winwidth/s_WIDTH*x), wintop-(winheight/s_HEIGHT)*y);
    }

    private static int[] getcolor(int n){
        double pathval = getLinearPathval(n);
        return getTwoColorGradient(pathval,new int[]{0,0,0,127},new int[]{0,255,255,255},new int[]{0,200,0,0});
    }


    private static int[] getcolorGradientExpBlue(int n){
        return getcolorGradient(getExpPathval(n,.4), new int[]{0,0,0,127},new int[]{0,255,255,255});
    }

    private static int[] getTwoColorGradient(double n, int[]start, int[]middle, int[]end){
        if (n<.5){
            return getcolorGradient(n*2,start,middle);
        }
        return getcolorGradient((n-.5)*2,middle,end);
    }

    private static double getLinearPathval(int n){
        return (1.*n)/total_iter;
    }

    private static double getExpPathval(int n, double exp){
        return Math.pow((1.*n)/total_iter,exp);
    }


    private static int[] getcolorGradient(double n, int[]start, int[]end){
        int[] retColor = new int[]{
                (int) (start[0]*(1-n)+end[0]*n),
                (int) (start[1]*(1-n)+end[1]*n),
                (int) (start[2]*(1-n)+end[2]*n),
                (int) (start[3]*(1-n)+end[3]*n),};
        return retColor;
    }

    private static int[] getcolorRotation1(int n){
        int[][] colors = new int[][]{
                new int[]{0,220,156,211},
                new int[]{0,185,79,155},
                new int[]{0,160,59,146},
                new int[]{0,137,0,107},
                new int[]{0,97,34,80},
                new int[]{0,167,76,126},
                new int[]{0,173,50,109},
                new int[]{0,214,51,120},
                new int[]{0,217,87,154}};
        return colors[n%9];
    }
    private static void setARGB(Cell p, int[]ARGB){
        setARGB(p.getX(),p.getY(),ARGB);
    }

    private static void setARGB(int x, int y, int[]ARGB){
        int a = ARGB[0];
        int r = ARGB[1];
        int g = ARGB[2];
        int b = ARGB[3];
        int p = (a<<24) | (r<<16) | (g<<8) | b;
        s_img.setRGB(x,y,p);
    }

//    //get pixel value
//    int p = s_img.getRGB(0,0);
//
//    //get alpha
//    int a = (p>>24) & 0xff;
//
//    //get red
//    int r = (p>>16) & 0xff;
//
//    //get green
//    int g = (p>>8) & 0xff;
//
//    //get blue
//    int b = p & 0xff;

}//class ends here