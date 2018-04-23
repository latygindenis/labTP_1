package socket;

import com.google.gson.Gson;
import data.model.*;
import data.model.req.CarsRequest;
import presentation.HabitatModel;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
        ArrayList<NewCar> newCars = new ArrayList<>();
        for(int i = 0; i<CarCollections.getInstance().arrayCarList.size(); i++) {
            Car car = CarCollections.getInstance().arrayCarList.get(i);
            String type = car instanceof CarLight ? "light" : "heavy";
            newCars.add(new NewCar(car.getX(), car.getY(), car.getId(), type));
        }
        CarsRequest request = new CarsRequest("swap req", CarCollections.getInstance().id, swapId, newCars, CarCollections.getInstance().idTreeSet, CarCollections.getInstance().bornHashMap);
        request.setTime(HabitatModel.time);
        String req = gson.toJson(request);
        System.out.println("swap req " + req);
        outStream.write(req.getBytes());
    }
}
