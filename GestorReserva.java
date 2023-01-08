import java.util.Map;
import java.util.HashMap;

public class GestorReserva {
    Mapa mapa;
    Map<Integer,Reserva> reservas;
    Integer ids;

    public GestorReserva(Mapa m) {
        this.mapa = m;
        this.reservas = new HashMap<Integer,Reserva>();
        this.ids = 0;
    }
    
    public Integer getIds() {
        return this.ids;
    }

    public void setIds(Integer x) {
        this.ids = x;
    }

    public Map <Integer,Reserva> getReservas() {
        HashMap<Integer,Reserva> aux = new HashMap<Integer,Reserva>();
        for(Integer k: this.reservas.keySet()) {
            aux.put(k, reservas.get(k));
        }
        return aux;
    }

    public void setReservas(HashMap<Integer,Reserva> reservas2) {
        HashMap<Integer,Reserva> aux = new HashMap<Integer,Reserva>();
        for(Integer k: reservas2.keySet()) {
            aux.put(k, reservas2.get(k));
        }
        this.reservas = aux;
    }

    public void imprimeMapa() {
        System.out.println(this.mapa.toString());
    }

    public String request(Integer x, Integer y, Integer d) {
        Reserva res = new Reserva (this.mapa,this.ids);

        String resultado = res.reserva(x,y,d);
        
        if (!resultado.equals("Reserva impossivel\n")) {
            this.reservas.put(ids,res);
            ids++;
        }
        return resultado;
    }

    public String cancel(Integer x, Integer y, Integer codigo) {
        String resultado;
        if (this.reservas.containsKey(codigo)) {
            resultado = this.reservas.get(codigo).cancel(x,y);
        }
        else {
            resultado = "Calcelamento inválido\n";
        }
        return resultado;
    }




}
