package scr;

class Sigmoid extends activation{
    public double forward(double x){
        return 1 / (1 + Math.exp(-x));
    }

    public double backward(double x){
        return x * (1 - x);
    }
}

class Relu extends activation{
    public double forward(double x){
        return Math.max(0, x);
    }

    public double backward(double x){
        return x > 0 ? 1 : 0;
    }
}

class Tanh extends activation{
    public double forward(double x){
        return Math.tanh(x);
    }

    public double backward(double x){
        return 1 - x *x;
    }
}





    