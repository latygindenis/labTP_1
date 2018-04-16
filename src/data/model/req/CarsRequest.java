package data.model.req;

import data.model.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class CarsRequest {
    String status;
    String id;
    String swapId;
    ArrayList<String> users = new ArrayList<>();
    public ArrayList<Car> arrayCarList;
    public TreeSet<UUID> idTreeSet;
    public HashMap<UUID, Long> bornHashMap;

    public CarsRequest(String status, String id, ArrayList<String> users) {
        this.status = status;
        this.id = id;
        this.users = users;
    }

    public String getSwapId() {
        return swapId;
    }

    public void setSwapId(String swapId) {
        this.swapId = swapId;
    }

    public CarsRequest(String status, String id, String swapId, ArrayList<Car> arrayCarList, TreeSet<UUID> idTreeSet, HashMap<UUID, Long> bornHashMap) {
        this.status = status;
        this.id = id;
        this.swapId = swapId;
        this.arrayCarList = arrayCarList;
        this.idTreeSet = idTreeSet;
        this.bornHashMap = bornHashMap;
    }

    public CarsRequest(String status, ArrayList<Car> arrayCarList, TreeSet<UUID> idTreeSet, HashMap<UUID, Long> bornHashMap) {
        this.status = status;
        this.arrayCarList = arrayCarList;
        this.idTreeSet = idTreeSet;
        this.bornHashMap = bornHashMap;
    }

    public CarsRequest() {
    }

    public CarsRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
