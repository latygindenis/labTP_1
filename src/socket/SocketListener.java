package socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import data.model.CarCollections;
import data.model.req.CarsRequest;
import presentation.HabitatView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;

public class SocketListener extends Thread {
    private final HabitatView view;
    String host = "localhost";
    int port = 8000;
    Socket socket;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();

    public SocketListener(Socket socket, HabitatView view) throws IOException {
        this.socket = socket;
        this.view = view;
        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());
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
                switch (carsRequest.getStatus()) {
                    case "update":
                        System.out.println("update list");
                        CarCollections.getInstance().users = carsRequest.getUsers();
                        view.updateUsersList();
                        break;
                    case "first connect":
                        System.out.println("first start");
                        CarCollections.getInstance().id = carsRequest.getId();
                        break;
                    case "swap":
                        System.out.println("swap echo");
                        break;
                    case "swap req":
                        System.out.println("swap req from " + carsRequest.getId());
                        CarsRequest res = new CarsRequest("swap res", CarCollections.getInstance().id, carsRequest.getId(), CarCollections.getInstance().arrayCarList, CarCollections.getInstance().idTreeSet, CarCollections.getInstance().bornHashMap);
                        String req = gson.toJson(res);
                        outStream.write(req.getBytes());
                        CarCollections.getInstance().idTreeSet = carsRequest.idTreeSet;
                        CarCollections.getInstance().bornHashMap = carsRequest.bornHashMap;
                        CarCollections.getInstance().arrayCarList = carsRequest.arrayCarList;
                        break;
                    case "swap res":
                        System.out.println("swap res from " + carsRequest.getId());
                        CarCollections.getInstance().idTreeSet = carsRequest.idTreeSet;
                        CarCollections.getInstance().bornHashMap = carsRequest.bornHashMap;
                        CarCollections.getInstance().arrayCarList = carsRequest.arrayCarList;
                        break;
                    default:
                        System.out.println("def ");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
