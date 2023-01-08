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
    private final Socket socket;

    private final DataInputStream in;
    private final DataOutputStream out;

    private Boolean login;

    private final Mapa map;
    private final Autenticacao aut;
    private final Reservas gestor;
    //private final GestorReservas gestor;

    //public ClientHandler(Mapa m,Autenticacao aut,GestorReservas gestor, Socket socket) throws IOException {
    public ClientHandler(Mapa m,Autenticacao aut,Reservas gestor, Socket socket) throws IOException {
        this.map = m;
        this.aut = aut;
        this.gestor = gestor;
        this.socket = socket;
        this.in  = new DataInputStream(  new BufferedInputStream( this.socket.getInputStream()));
        this.out = new DataOutputStream( new BufferedOutputStream(this.socket.getOutputStream()));
        this.login = false;
    }

    public void run() {
        try {
            String line;
            while ((line = in.readUTF()) != null) {
                
                System.out.println("line = " + line + "\n");

                //outras operações
                if (this.login) {
                    switch (line) {
                        case "list" :
                            int x = in.readInt();
                            int y = in.readInt();
                            out.writeUTF(this.map.lista(x, y)+"\n");
                            out.flush();
                            break;

                        case "print":
                            gestor.imprimeMapa();
                            out.writeUTF("mapa impresso\n");
                            out.flush();
                            break;

                        case "request":
                            x = in.readInt();
                            y = in.readInt();
                            int d = in.readInt();
                            out.writeUTF(gestor.reserva(x,y,d));
                            out.flush();
                            break;

                        case "logout":
                            socket.shutdownOutput();
                            socket.shutdownInput();
                            socket.close();
                            break;

                        default:
                            out.writeUTF("Operacao invalida!\n");
                            out.flush();
                            break;
                    }
                }

                //Fazer login/register
                else {
                    switch( line ) {
                        case "login" :
                            String name = in.readUTF();
                            String password = in.readUTF();
                            this.login = aut.login(name,password);
                            if (this.login) {
                                //System.out.println("Sessão Iniciada");
                                out.writeUTF("Sessao inicicada\n");
                                out.flush();
                            }
                            else {
                                //System.out.println("Username ou Password Invalida");
                                out.writeUTF("Username ou Password Invalida\n");
                                out.flush();
                            }
                            break;

                        case "register":
                            name = in.readUTF();
                            password = in.readUTF();
                            if (aut.register(name,password)) {
                                //System.out.println("Registo com sucesso");
                                out.writeUTF("Registo com sucesso\n");
                                out.flush();
                            }
                            else {
                                //System.out.println("Username já existe");
                                out.writeUTF("Username já existe\n");
                                out.flush();
                            }
                            break;

                        case "logout":
                            socket.shutdownOutput();
                            socket.shutdownInput();
                            socket.close();
                            break;

                        case "Comando inválido!":
                            out.writeUTF("Operaçao nula\n");
                            out.flush();
                            break;

                        default:
                            out.writeUTF("Operaçao nula\n");
                            out.flush();
                            break;
                    }
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
