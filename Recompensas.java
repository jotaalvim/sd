
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recompensas implements Runnable{
    private Mapa mapa;
    private Lock l = null;
    private Condition esperar;
    private Boolean flag = true;
    private String coord = "(0,0) (10,11) (14,14)\n";
    // deverá guardar uma lista de pontos de que dá recompensas

    public Recompensas(Mapa m,Lock lock) {
        this.l = lock;
        this.mapa = m;
        this.esperar = l.newCondition();
        this.flag = true;
    }

    public void run() {
        l.lock();
        try{ 
            while (true) {
                while (this.flag) {
                    esperar.await();
                }
                atualizarRecompensas();
                this.flag = true; }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.unlock();
    }

    public void Rsignal() {
        l.lock();
        try{
        this.flag = false;
        this.esperar.signal();
        }
        finally {
            l.unlock();
        }
    }

    public void atualizarRecompensas() {
        System.out.println("atualizei\n");
        System.out.println("novas coordenadas: \n");
        System.out.println(coord);
        //temos que fazer lock para que nenhuma reserva seja feita enquanto de calcula as recompensas 
    }

    public static void getSmallestArea(List<List<Integer>> mapa) {
        int smallestArea = Integer.MAX_VALUE;
        List<List<Integer>> smallestAreaCoords = null;

        for (int i = 0; i < mapa.size(); i++) { //percorre linhas
            for (int j = 0; j < mapa.get(i).size(); j++) { //percorre elementos
                List<List<Integer>> coords = getAreaCoords(mapa, i, j, 2);
                int area = getArea(mapa, coords);
                if (area < smallestArea) {
                    smallestArea = area;
                    smallestAreaCoords = coords;
                }
            }
        }

        for (List<Integer> coords : smallestAreaCoords) {
            System.out.println(coords);
        }
        
    }

    private static List<List<Integer>> getAreaCoords(List<List<Integer>> mapa, int i, int j, int radius) {
        List<List<Integer>> coords = new ArrayList<>();
        // percorremos a região de raio 2 ao redor da posição (i, j)
        for (int x = i - radius; x <= i + radius; x++) {
            for (int y = j - radius; y <= j + radius; y++) {
                if (x >= 0 && x < mapa.size() && y >= 0 && y < mapa.get(x).size()) {
                    // adicionamos as coordenadas da posição (x, y) à lista se estivermos dentro dos limites da matriz
                    coords.add(List.of(x, y));
                }
            }
        }

        return coords;
    }

    private static int getArea(List<List<Integer>> mapa, List<List<Integer>> coords) {
        int area = 0;
        for (List<Integer> coord : coords) {
            area += mapa.get(coord.get(0)).get(coord.get(1));
        }
        return area;
    }

    public static List<Integer> findElementCoords(List<List<Integer>> mapa, int n) {
        for (int i = 0; i < mapa.size(); i++) {
            for (int j = 0; j < mapa.get(i).size(); j++) {
                if (mapa.get(i).get(j) == n) {
                    return List.of(i, j);
                }
            }
        }
        return null;
    }

}


