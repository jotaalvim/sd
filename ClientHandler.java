package Server;

import Exceptions.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class ClientHandler implements Runnable {
    // private static final int N = 10;
    private final Socket socket;
    private final Autenticacao aut;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientHandler(Autenticacao aut, Socket socket) throws IOException {
        this.aut = aut;
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    public void run() {
        String line;
        while ((line = in.readLine()) != null) {
            //out.prilinentln(line);
            aut.register("1234","1234");
            //aut.login("1234","1234");
            if (aut.login("1234","1234")){
                System.out.println("Sessão Iniciada");
            }
            else System.out.println("Username ou Password Invalida");
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}
