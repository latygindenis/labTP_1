package machines;

import java.util.ArrayList;

public class CarArrayList { //Синглтон
    private static CarArrayList instance;

    ArrayList <Car> arrayCarList;

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