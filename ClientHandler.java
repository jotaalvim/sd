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
    private Boolean reserva;
    private String username;

    private final Mapa map;
    private final Autenticacao aut;
    private final GestorReserva gestor;
    private final Recompensas recompensas;

    public ClientHandler(Mapa m,Autenticacao aut,GestorReserva gestor,Recompensas recompensas, Socket socket) throws IOException {
        this.map = m;
        this.aut = aut;
        this.gestor = gestor;
        this.socket = socket;
        this.recompensas = recompensas;
        this.in  = new DataInputStream ( new BufferedInputStream( this.socket.getInputStream ()));
        this.out = new DataOutputStream( new BufferedOutputStream(this.socket.getOutputStream()));

        this.login = false;
        this.reserva = false;
    }

    public void run() {
        try {
            String line;
            String result;
            while (true ) {
                line = in.readUTF();// != null ;
                System.out.println("line = " + line + "\n");

                //outras operações
                if (this.login) {
                    switch (line) {
                        case "list" :
                            int x = in.readInt();
                            int y = in.readInt();
                            out.writeUTF(this.map.lista(x, y));
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
                            if (this.reserva) {
                                out.writeUTF("Ja tem uma trotinete reservada\n");
                                out.flush();
                            }
                            else {
                                result = gestor.request(x,y,d);
                                if (result != null) {
                                    recompensas.Rsignal();
                                    this.reserva = true;
                                    out.writeUTF(result);
                                    out.flush();

                                }
                                else {
                                    out.writeUTF("Impossivel requesitar nessa posicao\n");
                                    out.flush();
                                }
                            }
                            break;
                        case "park":
                            x = in.readInt();
                            y = in.readInt();
                            d = in.readInt();
                            result = gestor.park(x,y,d);
                            if (result != null) {
                                recompensas.Rsignal();
                                this.reserva = false;
                                out.writeUTF(result);
                                out.flush();
                            }
                            else {
                                out.writeUTF("Codigo invalido\n");
                                out.flush();
                            }
                            break;


                         case "logout":
                            aut.offline(this.username);
                            out.writeUTF("ate breve!\n");
                            out.flush();
                            this.username = "";
                            this.login = false;
                            //socket.shutdownOutput();
                            //socket.shutdownInput();
                            //socket.close();
                            break;

                        case "nada":
                            out.writeUTF("\n");
                            out.flush();
                            break;

                        //case null:
                        //    aut.offline(this.username);
                        //    break;
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

                            if ( aut.logado(name) ) {
                                out.writeUTF("Ja existe um utilizador online com esse nome\n");
                                out.flush();
                            }
                            else {
                                this.login = aut.login(name,password);
                                if (this.login) {
                                    this.username = name;
                                    aut.online(name);
                                    //System.out.println("Sessão Iniciada");
                                    out.writeUTF("Sessao inicicada\n");
                                    out.flush();
                                }
                                else {
                                    //System.out.println("Username ou Password Invalida");
                                    out.writeUTF("Username ou Password Invalida\n");
                                    out.flush();
                                }
                            }
                            break;

                        case "nada":
                            out.writeUTF("\n");
                            out.flush();
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

                        default:
                            out.writeUTF("Operacao nula, faca login primeiro\n");
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
