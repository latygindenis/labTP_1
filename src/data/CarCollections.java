package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class CarCollections { //Синглтон
    private static CarCollections instance;

    public ArrayList <Car> arrayCarList;
    public TreeSet <UUID> idTreeSet;
    public HashMap <UUID, Long> bornHashMap;

    private CarCollections(){
        arrayCarList = new ArrayList<>();
        idTreeSet = new TreeSet<>();
        bornHashMap = new HashMap<>();
    }

    public static CarCollections getInstance(){ //Создан ли список?
        if (null == instance){
            instance = new CarCollections();
        }
        return instance;
    }
}