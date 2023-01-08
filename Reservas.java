import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Reservas {
    private Mapa mapa;
    private Integer id;
    private Lock lR;

    //horas 
    public Reservas (Mapa m) {
        this.mapa = m;
        this.id = 0;
    }

    public String reserva(Integer x, Integer y, Integer d) {
        String result = mapa.reservar(x,y,d);
        if (result != null) {
            String[] tokens = result.split(" ");
            this.id++;
            return "Codigo: ("+ Integer.toString(id) + ") Trotinete em : ("+tokens[0]+", "+tokens[1]+")\n";
        }
        return "Reserva impossivel\n";
    }

    public String paraReserva(Integer x, Integer y, Integer cod,Integer comparacao) {
        if (cod  == comparacao) {
            mapa.acrescenta(x,y);
        }
        return null;

    }

    public void imprimeMapa() {
        System.out.println(this.mapa.toString());
    }

}
