package machines;

import javax.swing.*; // графичейский интерфейс
import java.awt.*;
import java.awt.event.*; // для событий
import java.util.ArrayList; // для списка
import java.util.Timer;
import java.util.TimerTask;

public class Habitat extends JFrame{ // обработка событий клавиатуры

    private double pHeavy; //Вероятность появления CarHeavy
    private double pLight; //Вероятность появления CarLight
    private long time = 0;
    private int timeHeavy; //Период появления CarHeavy
    private int timeLight; //Период появления CarLight

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;

    private JLabel mes = null;
    private JPanel mainPanel = null;
    private JPanel panelGen = null;
    private JButton startButton = null;
    private JButton endButton = null;
    private JCheckBox showInfoCheckBox = null;
    private JTextArea infoArea = null;
    private boolean mas = true;
    private Timer timer;

    private int amountOfG = 0;
    private int amountOfL = 0;

    ArrayList<Car> list;

    public Habitat(){
        this.wLength = 500;
        this.wHeight = 500;
        this.wPosX = 100;
        this.wPosY = 100;
    }

    Habitat(int wLength, int wHeight, int wPosX, int wPosY){
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        pHeavy = 0.5;
        pLight = 0.5;
        timeHeavy = 1;
        timeLight = 1;
    }

    private void update(long t){
        if (t%timeHeavy==0){ //Каждые timeHeavy секунд
            if(pHeavy >(float)Math.random()){ // Если прошло по вероятности
                amountOfG++;
                Car rb = new CarHeavy(10 + (int)(Math.random() * 410),10 + (int)(Math.random() * 410));
                list.add(rb);
                panelGen.repaint();
            }
        }

        if(t%timeLight==0){ //Каждые timeLight секунд
            if(pLight>(float)Math.random()){ //Если прошло по вероятности
                amountOfL++;
                Car rb = new CarLight(10 + (int)(Math.random() * 410), 10 + (int)(Math.random() * 410));
                list.add(rb);
                panelGen.repaint();

            }
        }
        infoArea.setText(
                "Количество: " +(amountOfL + amountOfG) + "\n" +
                        "Легковые: " + amountOfL + "\n" +
                        "Грузовые: " + amountOfG +"\n"+
                        "Время: " + time );

    }

    public void startSimulation(){
        amountOfG = 0;
        amountOfL = 0;
        time = 0;
        infoArea.setText("");
        startButton.setEnabled(false);
        endButton.setEnabled(true);
        timer = new Timer();
        timer.schedule(new TimerTask(){ //Добавление задания в таймер
            public void run(){
                    time++;
                    update(time);
            }
        } , 0, 1000);
    }
    public void endSimulation(){
            timer.cancel();
            timer.purge();
            list.clear();
            repaint(); //"Очистка" интерфейса
            amountOfG=0;
            amountOfL =0;
            time = 0;
        showInfoCheckBox.setSelected(true);
        infoArea.setVisible(true);
        startButton.setEnabled(true);
        endButton.setEnabled(false);
    }

    public void drawUI(){
        setLayout(null);
        list = new ArrayList<Car>();
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);
        startButton.addActionListener(e -> startSimulation());

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);
        endButton.addActionListener(e -> endSimulation());

        infoArea = new JTextArea();
        infoArea.setBounds(0, 130, 150, 100);
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setFocusable(false);

        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setBounds(0,100, 200, 25 );
        showInfoCheckBox.setFocusable(false);
        showInfoCheckBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                infoArea.setVisible(true);
            } else {//checkbox has been deselected
                infoArea.setVisible(false);
            }
        });

        mainPanel.add(startButton);
        mainPanel.add(endButton);
        mainPanel.add(showInfoCheckBox);
        mainPanel.add(infoArea);
        panelGen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                g.drawRect(10, 10, 500, 500);
                for(Car car:list){
                    car.paint(g);
                }
            }
        };
        panelGen.setFocusable(true); //Разрешить обработку клавиш
        panelGen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B:
                        startSimulation();
                        break;
                    case KeyEvent.VK_E:
                        endSimulation();
                        break;
                    case KeyEvent.VK_T:
                        if (showInfoCheckBox.isSelected()){
                            infoArea.setVisible(false);
                            showInfoCheckBox.setSelected(false);
                        }else {
                            infoArea.setVisible(true);
                            showInfoCheckBox.setSelected(true);
                        }
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panelGen.setBounds(10, 10, 520, 520);
        mainPanel.setBounds(530, 10, 200, 500);
        mes = new JLabel("", JLabel.RIGHT);
        panelGen.add(mes);
        add(panelGen);
        add(mainPanel);

        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timer = new Timer(); //Создание таймера
    }
}