//package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket ss         = new ServerSocket(12345);
        Lock l                  = new ReentrantLock();
        Autenticacao users      = new Autenticacao();
        Mapa map                = new Mapa(20,l);
        GestorReserva gestor    = new GestorReserva(map);
        Recompensas recompensas = new Recompensas(map,l);

        while(true) {
            Socket socket = ss.accept();
            ClientHandler clientHandler = new ClientHandler(map, users, gestor, recompensas, socket);

            Thread threadHandler    = new Thread( clientHandler );
            Thread threadRecompensa = new Thread( recompensas );

            threadHandler.start();
            threadRecompensa.start();
        }
    }
}
