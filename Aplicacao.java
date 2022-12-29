

public class Aplicacao {
    private Autenticacao aut;
    private Mapa map; 

    public Aplicacao() {
        this.aut = new Autenticacao();
        this.map = new Mapa(20);
    }   
    public static void main(String[] args) {
        Mapa a = new Mapa(5);
    }
}


