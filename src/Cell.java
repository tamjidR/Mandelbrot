public class Cell {
    private Complex currentVal;
    private Complex fixedVal;
    private int iterationsSoFar;
    public boolean isClosed;
    private int x;
    private int y;


    public Cell(Complex val, int x, int y){
        this(val,val,0,false,x,y);
    }

    public Cell(Complex currentVal, Complex fixedVal, int iterationsSoFar, boolean isClosed, int x, int y){
        this.currentVal = currentVal;
        this.fixedVal = fixedVal;
        this.iterationsSoFar = iterationsSoFar;
        this.isClosed = isClosed;
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }



    public boolean iterVal(){
        this.currentVal = (currentVal.times(currentVal)).plus(fixedVal);
        iterationsSoFar++;
        return updateIfClosed();
    }

    public boolean isClosed(){
        return this.isClosed;
    }

    public int getIterationsSoFar(){
        return this.iterationsSoFar;
    }

    public boolean updateIfClosed(){
        if(this.currentVal.abs()>2.){
            this.isClosed = true;
        }
        return this.isClosed;
    }
}
