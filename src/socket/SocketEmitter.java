package socket;

import com.google.gson.Gson;
import data.model.CarCollections;
import data.model.CarHeavy;
import data.model.req.CarsRequest;

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

    public void sendData(CarsRequest request) {
        try {
            String req = gson.toJson(request);
            outStream.write(req.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        socket.getInputStream().close();
        socket.getOutputStream().close();
        socket.close();
    }

    public void swap(String swapId) throws IOException {
        CarsRequest request = new CarsRequest("swap req", CarCollections.getInstance().id, swapId, CarCollections.getInstance().arrayCarList, CarCollections.getInstance().idTreeSet, CarCollections.getInstance().bornHashMap);
        String req = gson.toJson(request);
        System.out.println("onConnect " + req);
        outStream.write(req.getBytes());
    }
}
