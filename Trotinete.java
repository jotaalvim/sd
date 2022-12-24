public class Trotinete {
    
    private Integer x,y;
    
    public Trotinete() {
        this.x = 0;
        this.y = 0;
    }

    public Trotinete(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX(){
        return this.x;
    }

    public Integer getY(){
        return this.y;
    }

    public void setX(Integer x){
        this.x = x;
    }

    public void setY(Integer y){
        this.y = y;
    }

     public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if( (o == null) || this.getClass() != o.getClass() ){
            return false;
        }
        Trotinete k = (Trotinete) o;
        return this.x == k.getX() && this.y == k.getY();
    }

    public String toString() {
        return "Trotinete[x=" + this.x + ",y=" + this.y + "]";
    }

} 
