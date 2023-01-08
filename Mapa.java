//package Mapa;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Mapa {

    private Integer n;
    private List< List<Integer> > mapa;

    private Lock l;// = new ReentrantLock();

    public Mapa(Integer n,Lock lock) {
        this.l = lock;
        this.n = n;
        this.mapa = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            this.mapa.add( new ArrayList<>() );
            for (int j = 0; j < n; j++) {
                this.mapa.get(i).add(0);
            }
        }

        this.acrescenta(1,1); this.acrescenta(1,1); this.acrescenta(3,1); this.acrescenta(4,7); this.acrescenta(4,7); this.acrescenta(4,7); this.acrescenta(4,7); this.acrescenta(4,7); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,18); this.acrescenta(4,7); this.acrescenta(4,7); this.acrescenta(4,11); this.acrescenta(4,12); this.acrescenta(4,12); this.acrescenta(4,12); this.acrescenta(7,10); this.acrescenta(7,10); this.acrescenta(7,10); this.acrescenta(7,10); this.acrescenta(4,7); this.acrescenta(4,7);

        this.acrescenta(4,7);

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
            i++;
            this.mapa.get(y).set(x,i);
        }
        finally {
            l.unlock();
        }
    }


    public boolean retira (Integer x, Integer y) {
        try {
            l.lock();
            if (x<0 || y < 0) return false;
            else {
            int i = this.mapa.get(y).get(x);
            if (i == 0) return false;
            i--;
            this.mapa.get(y).set(x,i);
            return true;
            }
        }
        finally {
            l.unlock();
        }
    }

    public String lista(Integer x, Integer y) {
        List<Integer> coordx = new ArrayList<>();
        List<Integer> coordy = new ArrayList<>();
        int soma = 0;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int v = this.mapa.get(i).get(j);
                if ( Math.abs(x - j) + Math.abs(i - y) <= 2 && v > 0) {
                    coordx.add(j);
                    coordy.add(i);
                    soma += v;
                }
            }
        }
        String result = "Existem " + Integer.toString(soma)  + " trotinetes perto de si:"; 
        for(int i = 0; i < coordx.size(); i++) {
                result += "(" + Integer.toString(coordx.get(i)) + ", "+ Integer.toString(coordy.get(i)) +") ";
            }
        return result+"\n";
    }

    

    public String reservar(Integer x, Integer y, Integer d) {
        l.lock();
        try {
            int xf = -1 , yf = -1;
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.n; j++) {
                    int v = this.mapa.get(i).get(j);
                    if ( Math.abs(x - j) + Math.abs(i - y) <= d && v > 0) {
                        xf = j;
                        yf = i;
                        break;
                    }
                }
            }

            if (retira(xf,yf)) {
                return  Integer.toString(xf)+" " + Integer.toString(yf);
            }
            else {
                return null;
            }
        }
        finally {
           l.unlock();
        }
    }


    public static int menorAreaNoRaio(List<List<Integer>> mapa) {
        int menorArea = Integer.MAX_VALUE;

        for (int i = 0; i < mapa.size(); i++) {
            for (int j = 0; j < mapa.get(i).size(); j++) {
                // calculamos a área da região de raio 2 ao redor da posição (i, j)
                int area = getArea(mapa, i, j, 2);

                if (area < menorArea) {
                    menorArea = area;
                }
            }
        }

        return menorArea;
    }

    private static int getArea(List<List<Integer>> mapa, int i, int j, int raio) {
        int area = 0;

        // percorremos a região de raio 2 ao redor da posição (i, j)
        for (int x = i - raio; x <= i + raio; x++) {
            for (int y = j - raio; y <= j + raio; y++) {
                if (x >= 0 && x < mapa.size() && y >= 0 && y < mapa.get(x).size()) {
                    // adicionamos o valor da posição (x, y) à área se estivermos dentro dos limites do mapa
                    area += mapa.get(x).get(y);
                }
            }
        }
        return area;
    }
}
