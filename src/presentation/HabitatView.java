package presentation;

import data.Car;
import data.CarCollections;
import data.CarHeavy;
import data.CarLight;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class HabitatView extends JFrame {

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;

    JMenu menuSimulation;
    JMenu menuUI;

    JMenuItem startSimulationItem;
    JMenuItem endSimulationItem;
    JCheckBoxMenuItem showInfoItem;
    JCheckBoxMenuItem showTimeItem;

    JPanel mainPanel;
    JPanel panelGen;
    JPanel showTimePanel;
    JPanel testPanel;
    JButton startButton;
    JButton endButton;
    JButton liveObjects;
    JButton lightAIButton;
    JButton heavyAIButton;
    JCheckBox showInfoCheckBox;
    JTextArea infoArea;
    JRadioButton yesButton;
    JRadioButton noButton;
    JLabel showTimeLabel;
    JPanel parentPanel;
    JPanel heavyPanel;
    JPanel lightPanel;

    JLabel timeHeavyLabel;
    JLabel timeLightLabel;
    TextField timeHeavyArea;
    TextField timeLightArea;

    JSlider heavySlider;
    JSlider lightSlider;
    JLabel liveHeavyLabel;
    TextField liveHeavyArea;
    JLabel liveLightLabel;
    TextField liveLightArea;


    public HabitatView(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        drawMenu();
        drawUI();
    }

    private void drawMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuSimulation = new JMenu("Симуляция");
        menuUI = new JMenu("Вид");
        showInfoItem = new JCheckBoxMenuItem("Показывать информацию");
        menuUI.add(showInfoItem);
        startSimulationItem = new JMenuItem("Начать симуляцию");
        endSimulationItem = new JMenuItem("Остановить симуляцию");
        endSimulationItem.setEnabled(false);
        menuSimulation.add(startSimulationItem);
        menuSimulation.add(endSimulationItem);
        menuBar.add(menuSimulation);
        menuBar.add(menuUI);
        setJMenuBar(menuBar);
    }
    private void drawHeavyPanel(){
        heavyPanel = new JPanel();
        heavyPanel.setLayout(null);
        heavyPanel.setMaximumSize(new Dimension(300, 125));
        heavyPanel.setBounds(0,160, 300, 125);
        timeHeavyLabel = new JLabel("Период появления грузовой машины:");
        timeHeavyLabel.setBounds(0, 0, 230, 25);
        timeHeavyArea = new TextField();
        timeHeavyArea.setBounds(230, 0, 30, 25);

        liveHeavyLabel = new JLabel("Время жизни грузовой машины:");
        liveHeavyLabel.setBounds(0, 30, 230, 25);
        liveHeavyArea = new TextField();
        liveHeavyArea.setText(String.valueOf(CarHeavy.liveTime));
        liveHeavyArea.setBounds(230, 30, 30, 25);

        heavySlider = new JSlider(JSlider.HORIZONTAL, 0, 100,50);
        heavySlider.setBounds(0, 60, 300, 50);
        heavySlider.setMajorTickSpacing(10);
        heavySlider.setPaintTicks(true);
        heavySlider.setPaintLabels(true);
        heavySlider.setSnapToTicks(true);

        heavyPanel.add(timeHeavyLabel);
        heavyPanel.add(timeHeavyArea);
        heavyPanel.add(liveHeavyLabel);
        heavyPanel.add(liveHeavyArea);
        heavyPanel.add(heavySlider);
    }
    private void drawLightPanel(){
        lightPanel = new JPanel();
        lightPanel.setLayout(null);
        lightPanel.setMaximumSize(new Dimension(300, 125));
        lightPanel.setBounds(0,295, 300, 125);

        timeLightLabel = new JLabel("Период появления легковой машины:");
        timeLightLabel.setBounds(0, 0, 230, 25);
        timeLightArea = new TextField();
        timeLightArea.setBounds(230, 0, 30, 25);


        liveLightLabel = new JLabel("Время жизни легковой машины:");
        liveLightLabel.setBounds(0, 30, 230, 25);
        liveLightArea = new TextField();
        liveLightArea.setText(String.valueOf(CarLight.liveTime));
        liveLightArea.setBounds(230, 30, 30, 25);


        lightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100,50);
        lightSlider.setBounds(0, 60, 300, 50);
        lightSlider.setMajorTickSpacing(10);
        lightSlider.setPaintTicks(true);
        lightSlider.setPaintLabels(true);
        lightSlider.setSnapToTicks(true);

        lightPanel.add(timeLightLabel);
        lightPanel.add(timeLightArea);
        lightPanel.add(liveLightLabel);
        lightPanel.add(liveLightArea);
        lightPanel.add(lightSlider);
    }

    private void drawUI() {
        Border blackline;

        blackline = BorderFactory.createLineBorder(Color.black);

        parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        testPanel = new JPanel();
        testPanel.setLayout(null);
        testPanel.setBounds(0, 0, 400, 150);
        testPanel.setMaximumSize(new Dimension(600, 150));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 30, 100,125 );

        startButton = new JButton("Start");
        startButton.setBounds(0, 0, 100, 25);
        endButton = new JButton("End");
        endButton.setBounds(0,30,100, 25);
        endButton.setEnabled(false);

        liveObjects = new JButton("Live Obj");
        liveObjects.setBounds(0,60,100, 25);
        liveObjects.setEnabled(false);

        buttonPanel.add(startButton);
        buttonPanel.add(endButton);
        buttonPanel.add(liveObjects);


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(110, 0, 300, 250);
        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setBounds(0, 0, 300, 25);
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setBounds(0, 30, 100, 70);
        showTimePanel = new JPanel();
        showTimePanel.setLayout(null);
        showTimePanel.setBounds(0, 100, 150, 70);
        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setBounds(0, 0, 150, 25);
        yesButton = new JRadioButton("Да");
        yesButton.setBounds(5, 25, 45, 25);
        noButton = new JRadioButton("Нет");
        noButton.setBounds(60, 25, 45, 25);
        noButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);
        showTimePanel.add(showTimeLabel);
        showTimePanel.add(yesButton);
        showTimePanel.add(noButton);
        showTimePanel.setVisible(false);
        infoPanel.add(showInfoCheckBox);
        infoPanel.add(infoArea);
        infoPanel.add(showTimePanel);
        testPanel.add(buttonPanel);
        testPanel.add(infoPanel);


        drawHeavyPanel();
        drawLightPanel();
        lightAIButton = new JButton("Light AI");
        lightAIButton.setBounds(0, 450, 100, 25);
        heavyAIButton = new JButton("Heavy AI");
        heavyAIButton.setBounds(105, 450, 100, 25);

        mainPanel.add(testPanel);
        mainPanel.add(heavyPanel);
        mainPanel.add(lightPanel);
        mainPanel.add(lightAIButton);
        mainPanel.add(heavyAIButton);
        mainPanel.setPreferredSize(new Dimension(300, 500));


        panelGen = new JPanel()  {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                synchronized (CarCollections.getInstance().arrayCarList){
                    for (Car car : CarCollections.getInstance().arrayCarList) {
                        car.paint(g);
                    }
                }

            }
        };
        panelGen.setBorder(blackline);
        add(parentPanel);

        parentPanel.add(panelGen, BorderLayout.CENTER);
        parentPanel.add(mainPanel, BorderLayout.EAST);
        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    void startSimulation(String statisticWithTime, String statisticWithoutTime) {
        if (yesButton.isSelected()) {
            infoArea.setText(statisticWithTime);
        } else {
            infoArea.setText(statisticWithoutTime);
        }
        startButton.setEnabled(false);
        startSimulationItem.setEnabled(false);
        endSimulationItem.setEnabled(true);
        endButton.setEnabled(true);
        liveObjects.setEnabled(true);
//        System.out.println(CarCollections.getInstance().arrayCarList.size());
        repaint();
    }
    void stopSimulation() {
        repaint(); //"Очистка" интерфейса
        startButton.setEnabled(true);
        startSimulationItem.setEnabled(true);
        endSimulationItem.setEnabled(false);
        endButton.setEnabled(false);
        liveObjects.setEnabled(false);
    }
}
