package socket;

import com.google.gson.Gson;
import data.model.CarHeavy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Handler;

public class SocketEmitter {
    private final DataOutputStream outStream;
    Socket socket;
    Handler handler;
    private Gson gson = new Gson();

    public SocketEmitter(Socket socket) throws IOException {
        this.socket = socket;
        outStream = new DataOutputStream(socket.getOutputStream());
    }

    public void setCar() {
        CarHeavy carHeavy = new CarHeavy(0, 1);
        String json = gson.toJson(carHeavy);
        System.out.println("json " + json);
        try {
            outStream.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
