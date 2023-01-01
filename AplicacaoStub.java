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


    public AplicacaoStub() {
        try {
            this.socket = new Socket("localhost", 12345);
            this.out    = new DataOutputStream( this.socket.getOutputStream());
            this.in     = new DataInputStream(  new BufferedInputStream(this.socket.getInputStream()));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void envia(String userInput) {

        try {
        //out.println(userInput);
        //FAZER PARSING
        System.out.println(userInput);

        out.writeUTF(userInput);
        out.flush();

        String response = in.readLine();
        System.out.println(response);

        //socket.shutdownOutput();
        //socket.shutdownInput();
        //socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
