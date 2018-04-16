package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class CarCollections { //Синглтон
    private static CarCollections instance;


    public final ArrayList <Car> arrayCarList;
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

    public synchronized void cleanCollections (Long time){
        for (int i=0; i<CarCollections.getInstance().arrayCarList.size(); i++){
            Car curCar = CarCollections.getInstance().arrayCarList.get(i);
            Long curBornTime = CarCollections.getInstance().bornHashMap.get(curCar.getId());
            if (curCar instanceof CarHeavy){
                if (time - CarCollections.getInstance().bornHashMap.get(curCar.getId()) >= CarHeavy.liveTime){
                    CarCollections.getInstance().bornHashMap.remove(curCar.getId());
                    CarCollections.getInstance().idTreeSet.remove(curCar.getId());
                    CarCollections.getInstance().arrayCarList.remove(i);
                }
            } else {
                if (time - CarCollections.getInstance().bornHashMap.get(curCar.getId()) >= CarLight.liveTime){
                    CarCollections.getInstance().bornHashMap.remove(curCar.getId());
                    CarCollections.getInstance().idTreeSet.remove(curCar.getId());
                    CarCollections.getInstance().arrayCarList.remove(i);
                }
            }
        }
    }

    public StringBuilder liveObjString (){

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<CarCollections.getInstance().arrayCarList.size(); i++){
            Car curCar = CarCollections.getInstance().arrayCarList.get(i);
            if (curCar instanceof CarHeavy){
                stringBuilder.append("Heavy ");
            } else {
                stringBuilder.append("Light ");
            }
            stringBuilder.append(curCar.getId() + " ");
            stringBuilder.append(CarCollections.getInstance().bornHashMap.get(curCar.getId()));
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }
}