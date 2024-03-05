package scr;

public class Relu extends activation{
    public double forward(double x){
        return Math.max(0, x);
    }

    public double backward(double x){
        return x > 0 ? 1 : 0;
    }
}
