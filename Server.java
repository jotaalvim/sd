package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(12345);
        Autenticacao users = new Autenticacao();
        while(true) {
            Socket socket = ss.accept();

            Thread clientHandler = new Thread(new ClientHandler(users, socket));

            clientHandler.start();
        }
    }
}