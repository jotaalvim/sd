import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Reserva {
    private Mapa mapa;
    private Integer id;

    private int x;
    private int y;

    private LocalTime inicio;
    private LocalTime fim;
    
    private Lock lR;

    //horas 
    public Reserva (Mapa m,int id) {
        this.inicio = LocalTime.now();
        this.fim = null;
        this.mapa = m;
        this.id = id;
        this.x = 0;
        this.y = 0;
    }

    public String reserva(Integer x, Integer y, Integer d) {
        String result = mapa.reservar(x,y,d);
        if (result != null) {
            this.x = x;
            this.y = y;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] tokens = result.split(" ");
            this.id++;
            return "Codigo: ("+ Integer.toString(id) + ") as: "+ dtf.format(this.inicio)  + " Trotinete em : ("+tokens[0]+", "+tokens[1]+")\n";
        }
        else {
            return "Reserva impossivel\n";
        }
    }

    public String cancel(Integer x, Integer y) {
        mapa.acrescenta(x,y);
        //CALCULAR O PREÇO
        return "Sucesso no cancelamento\n";
    }


}
