package socket;

import com.google.gson.Gson;
import data.model.CarHeavy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketListener extends Thread{
    String host = "localhost";
    int port = 8000;
    Socket socket;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();

    public SocketListener(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        while (true){
            try {
                inStream = new DataInputStream(socket.getInputStream());
                String string = new String(inStream.readAllBytes());
                System.out.println("Result is " + string);
                /*inStream.close();
                outStream.close();
                socket.close();*/
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
