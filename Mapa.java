//package Mapa;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Mapa {

    private Integer n;
    private List< List<Integer> > mapa;

    private Lock l = new ReentrantLock();

    public Mapa(Integer n) {
        this.n = n;
        this.mapa = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            this.mapa.add( new ArrayList<>() );
            for (int j = 0; j < n; j++) {
                this.mapa.get(i).add(0);
            }
        }
    }

    public Mapa(Mapa a) {
        this.n = a.n;
        this.mapa = a.getMapa();
    }

    public Integer getN() {
        return this.n;
    }

    public List<List<Integer>> getMapa() {
        List<List<Integer>> copy = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            copy.add( new ArrayList<>() );
            for (int j = 0; j < n; j++) {
                int k = this.mapa.get(i).get(j);
                copy.get(i).add(j);
            }
        }
        return copy;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(this.mapa.get(i).get(j));
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    public void acrescenta (Integer x, Integer y) {
        try {
            l.lock();
            int i = this.mapa.get(y).get(x);
            this.mapa.get(y).set(i++,x);
        }
        finally {
            l.unlock();
        }
    }

}
