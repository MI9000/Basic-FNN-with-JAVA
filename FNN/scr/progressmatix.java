package scr;

abstract class ProgressMatrix {
    
    public abstract QUASO add(QUASO other);
    public abstract QUASO subtract(QUASO other);
    public abstract QUASO mulScal(double scalar);
    public abstract QUASO dot(QUASO other);
    public abstract QUASO mul(QUASO other);
    public abstract QUASO transpose();
    public abstract void T();

}

