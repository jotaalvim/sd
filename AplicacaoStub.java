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


public class AplicacaoStub {

    private  DataInputStream in = null;
    private  DataOutputStream out = null;
    private  Socket socket = null; 

    public AplicacaoStub() throws IOException {
            this.socket = new Socket("localhost", 12345);
            this.out    = new DataOutputStream( new BufferedOutputStream(this.socket.getOutputStream()));
            this.in     = new DataInputStream(  new BufferedInputStream( this.socket.getInputStream()));
    }
    
    public void envia(String userInput) {
        try {
            String[] tokens = userInput.split(" ");

            switch (tokens[0]) {
                case "login":
                    out.writeUTF("login");
                    out.writeUTF(tokens[1]);
                    out.writeUTF(tokens[2]);
                    out.flush();
                    break;

                case "register":
                    out.writeUTF("register");
                    out.writeUTF(tokens[1]);
                    out.writeUTF(tokens[2]);
                    out.flush();
                    break;

                case "list":
                    out.writeUTF("list");
                    int x = Integer.parseInt(tokens[1]) ;
                    int y = Integer.parseInt(tokens[2]) ;
                    out.writeInt(x);
                    out.writeInt(y);
                    out.flush();
                    break;

                case "logout":
                    out.writeUTF("logout");
                    out.flush();
                    break;

                case "help":
                    Menu.opcoes();
                    out.writeUTF("nada");
                    out.flush();
                    break;

                case "park":
                    out.writeUTF("park");
                    x = Integer.parseInt(tokens[1]) ;
                    y = Integer.parseInt(tokens[2]) ;
                    int d = Integer.parseInt(tokens[3]) ;
                    out.writeInt(x);
                    out.writeInt(y);
                    out.writeInt(d);
                    out.flush();
                    break;


                case "request":
                    out.writeUTF("request");
                    x = Integer.parseInt(tokens[1]) ;
                    y = Integer.parseInt(tokens[2]) ;
                    d = Integer.parseInt(tokens[3]) ;

                    out.writeInt(x);
                    out.writeInt(y);
                    out.writeInt(d);
                    out.flush();
                    break;

                case "print":
                    out.writeUTF("print");
                    out.flush();

                    break;

                default:
                    out.writeUTF("Comando inválido!\n");
                    out.flush();
                    break;

            }
            
            // recebe uma resposta do servidor e escreve-a
            String response = in.readLine();
            System.out.println(response);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
