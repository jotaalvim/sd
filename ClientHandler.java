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

                //outras operações
                if (this.login) {
                    if (tokens[0].equals("get")) {
                    }
                    if (tokens[0].equals("list")) {
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);

                        out.writeBytes(this.map.lista(x, y)+"\n");
                        out.flush();
                    }
                    if (tokens[0].equals("print")) {
                        System.out.println(this.map.toString());
                        System.out.println("mapa");

                        out.writeBytes("mapa imprimido\n");
                        out.flush();
                    }
                    if (tokens[0].equals("logout")) {
                        socket.shutdownOutput();
                        socket.shutdownInput();
                        socket.close();
                    }
                }

                //Fazer login/register
                else {
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
