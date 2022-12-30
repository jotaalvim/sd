//package Server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class ClientHandler implements Runnable {
    // private static final int N = 10;
    private final Socket socket;
    private final Mapa map;
    private final Autenticacao aut;
    private final DataInputStream in;
    private final DataOutputStream out;

    private Boolean login;

    public ClientHandler(Mapa m,Autenticacao aut, Socket socket) throws IOException {
        this.map = m;
        this.aut = aut;
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.login = false;
    }

    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (this.login) {
                    //outras operações
                    if (tokens[0].equals("list")) {
                    }
                    if (tokens[0].equals("get")) {
                    }
                    if (tokens[0].equals("print")) {
                        System.out.println("mapa");
                        out.writeUTF( this.map.toString() + System.lineSeparator());
                        out.flush();
                    }
                }

                else {
                    //Fazer login/register
                    if (tokens[0].equals("login")) {
                        if (aut.login(tokens[1],tokens[2]) ) {
                            System.out.println("Sessão Iniciada");
                            this.login = true;
                            out.writeBytes("Sessao inicicada\n");
                            out.flush();
                        }
                        else {
                            System.out.println("Username ou Password Invalida");
                            out.writeBytes("Username ou Password Invalida\n");
                            out.flush();
                        }
                    }
                    if (tokens[0].equals("register")) {
                        if (aut.register(tokens[1],tokens[2])) {
                            System.out.println("Registo com sucesso");
                            out.writeBytes("Registo com sucesso\n");
                            out.flush();
                        }
                        else {
                            System.out.println("Username já existe");
                            out.writeBytes("Username já existe\n");
                            out.flush();
                        }
                    }
                }

            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
