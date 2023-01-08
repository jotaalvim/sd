


public class Recompensas {
    
    private Lock l;
    private Mapa mapa;
    // deverá guardar uma lista de pontos de que dá recompensas

    public Recompensas(Mapa m,Lock lock) {
        this.l = this.lock;
        this.mapa = m;
    }

    public void run() {
        Boolean r = true;
        Condition esperar = l.newCondition();
        while (r) {
            esperar.wait();
            r = false;
        }
        this.atualizarRecompensas();
    }
}
