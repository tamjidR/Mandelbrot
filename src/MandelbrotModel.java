import java.awt.image.PixelGrabber;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MandelbrotModel implements Serializable {
    int s_WIDTH = 2560/2;
    int s_HEIGHT = 1600/2;
    double winleft = -1.75;
    double winright = .6;
    double winwidth = winright-winleft;
    double winheight = winwidth*1600./2560.;
    double wintop = 1.3;
    double winbot = wintop-winheight;
    int total_iter = 100;
    int deck_size = s_HEIGHT*s_WIDTH;
    Cell[] s_Cells_To_Check;
    Cell[][] all_Cells;



    public void save(String ser) throws FileNotFoundException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("myarray.ser"));
            out.writeObject(this);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("Failed lol");
        }
    }

    public void iterMatrix(){

    }

    private void iterMatrix(int iterations){
        for (int counter=0; counter<iterations; counter++){
            iterMatrixOnce();
        }
    }

    private void iterMatrixOnce(){
        int newdecksize = 0;
        for (int i = 0; i<deck_size; i++){
            Cell p = s_Cells_To_Check[i];
            if (p.iterVal()){
                continue;
            }
            else{
                s_Cells_To_Check[newdecksize] = p;
                newdecksize++;
            }
        }
        deck_size = newdecksize;
        System.out.println(" deck size now: " + deck_size);
    }

    private void initMatrix(){
        s_Cells_To_Check = new Cell[deck_size];
        all_Cells = new Cell[s_WIDTH][s_HEIGHT];
        int newdecksize = 0;
        for (int x = 0; x<s_WIDTH; x++){
            for (int y = 0; y<s_HEIGHT; y++){
                Complex val = getFixedValue(x,y);
                Cell p = new Cell(val,x,y);
                all_Cells[x][y] = p;
                if (p.updateIfClosed()){
                    continue;
                }
                s_Cells_To_Check[newdecksize] = p;
                newdecksize++;
            }
        }
        deck_size = newdecksize;
        System.out.println("Cells created: " + newdecksize);
    }

    private Complex getFixedValue(int x, int y){
        return new Complex(winleft+(winwidth/s_WIDTH*x), wintop-(winheight/s_HEIGHT)*y);
    }

}
