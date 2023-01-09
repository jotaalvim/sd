import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.time.Duration;
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
            return Integer.toString(id-1) + "as: "+ dtf.format(this.inicio)  + " Trotinete em : ("+tokens[0]+", "+tokens[1]+")\n";
        }
        else {
            return null;
        }
    }

    public String park(Integer x, Integer y) {
        mapa.acrescenta(x,y);
        return "Sucesso no estacionamento ";
    }

    public double calculaPreco(int x, int y) {
        LocalTime agora = LocalTime.now();
        long tempo = Duration.between(this.inicio, agora).toMinutes();
        return tempo * 0.4 +  ( Math.abs(x - this.x) + Math.abs(y - this.y) )* 0.8;
    }
}
