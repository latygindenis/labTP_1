package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class CarArrayList { //Синглтон
    private static CarArrayList instance;

    public ArrayList <Car> arrayCarList;
    public TreeSet <Car> idTreeSet;
    public HashMap <Long, Car> bornHashMap;

    private CarArrayList(){
        arrayCarList = new ArrayList<>();
    }

    public static CarArrayList getInstance(){ //Создан ли список?
        if (null == instance){
            instance = new CarArrayList();
        }
        return instance;
    }
}