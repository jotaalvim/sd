
import java.io.IOException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recompensas implements Runnable{
    
    private Mapa mapa;
    private Lock l = null;
    private Condition esperar;
    // deverá guardar uma lista de pontos de que dá recompensas

    public Recompensas(Mapa m,Lock lock) {
        this.l = lock;
        this.mapa = m;
        this.esperar = l.newCondition();
    }

    public void run() {
        l.lock();
        try{ 
            Boolean f = true; 
            while (f) {
                esperar.await();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.unlock();
    }

    public void atualizarRecompensas() {
    }
}
