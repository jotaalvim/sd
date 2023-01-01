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

public class Cliente {

    public static void main(String[] args) {
        AplicacaoStub astub = new AplicacaoStub();
        BufferedReader systemIn = new BufferedReader(   new InputStreamReader(System.in));
        String userInput;
        try {

        while ((userInput = systemIn.readLine()) != null) {
            astub.envia(userInput);

        }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
