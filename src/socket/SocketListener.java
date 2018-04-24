package socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import data.model.*;
import data.model.req.CarsRequest;
import presentation.HabitatModel;
import presentation.HabitatView;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener extends Thread {
    private final HabitatView view;
    private final HabitatModel model;
    String host = "localhost";
    int port = 8000;
    Socket socket;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();

    public SocketListener(Socket socket, HabitatView view, HabitatModel model) throws IOException {
        this.socket = socket;
        this.view = view;
        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());
        this.model = model;
    }


    public void run() {
        while (true) {
            try {
                byte[] bytes = new byte[1024];
                inStream.read(bytes);
                String string = new String(bytes);
                System.out.println("Result is " + string);
                JsonReader reader = new JsonReader(new StringReader(string));
                reader.setLenient(true);
                CarsRequest carsRequest;
                carsRequest = gson.fromJson(reader, CarsRequest.class);
                ArrayList<NewCar> newCars = new ArrayList<>();
                ArrayList<Car> cars = new ArrayList<>();
                switch (carsRequest.getStatus()) {
                    case "update":
                        System.out.println("update list");
                        CarCollections.getInstance().users.clear();
                        CarCollections.getInstance().users = carsRequest.getUsers();
                        view.updateUsersList();
                        break;
                    case "first connect":
                        System.out.println("first start");
                        CarCollections.getInstance().id = carsRequest.getId();
                        CarCollections.getInstance().users = carsRequest.getUsers();
                        view.updateUsersList();
                        break;
                    case "swap":
                        System.out.println("swap echo");
                        break;
                    case "swap req":
                        System.out.println("swap req from " + carsRequest.getId());
                        newCars.clear();
                        for(int i=0; i<CarCollections.getInstance().arrayCarList.size(); i++) {
                            Car car = CarCollections.getInstance().arrayCarList.get(i);
                            String type = car instanceof CarLight ? "light" : "heavy";
                            newCars.add(new NewCar(car.getX(), car.getY(), car.getId(), type));
                        }
                        CarsRequest res = new CarsRequest("swap res", CarCollections.getInstance().id, carsRequest.getId(), newCars, CarCollections.getInstance().idTreeSet, CarCollections.getInstance().bornHashMap);
                        res.setTime(HabitatModel.time);
                        String req = gson.toJson(res);
                        outStream.write(req.getBytes());
                    case "swap res":
                        System.out.println("swap res from " + carsRequest.getId());
                        HabitatModel.time = carsRequest.getTime();
                        CarCollections.getInstance().idTreeSet = carsRequest.idTreeSet;
                        CarCollections.getInstance().bornHashMap = carsRequest.bornHashMap;
                        for(int i=0; i<carsRequest.arrayCarList.size(); i++) {
                            NewCar newCar = carsRequest.arrayCarList.get(i);
                            Car car = newCar.getType().equals("light") ? new CarLight() : new CarHeavy();
                            car.setX(newCar.getPosX());
                            car.setY(newCar.getPosY());
                            car.setId(newCar.getId());
                            cars.add(car);
                        }
                        CarCollections.getInstance().arrayCarList = cars;
                        break;
                    default:
                        System.out.println("def ");
                }
            } catch (Exception e) {
                System.out.println(e);
                try {
                    socket.close();
                    inStream.close();
                    stop();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
