package presentation;

import data.Car;
import data.CarArrayList;

import javax.swing.*;
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
    JButton startButton;
    JButton endButton;
    JCheckBox showInfoCheckBox;
    JTextArea infoArea;
    JRadioButton yesButton;
    JRadioButton noButton;
    JLabel showTimeLabel;

    JLabel timeHeavyLabel;
    JLabel timeLightLabel;
    JLabel pTimeHeavyLabel;
    JLabel pTimeLightLabel;
    TextField timeHeavyArea;
    TextField timeLightArea;

    JSlider heavySlider;
    JSlider lightSlider;

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

    private void drawUI() {
        setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);


        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setBounds(0, 70, 200, 25);
        showInfoCheckBox.setFocusable(false);

        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setBounds(20, 170, 100, 20);

        yesButton = new JRadioButton("Да");
        yesButton.setFocusable(false);
        yesButton.setBounds(20, 190, 50, 25);

        noButton = new JRadioButton("Нет");
        noButton.setFocusable(false);
        noButton.setBounds(70, 190, 50, 25);
        noButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);

        infoArea = new JTextArea();
        infoArea.setBounds(0, 100, 150, 65);
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setFocusable(false);


        timeHeavyLabel = new JLabel("Период появления грузовой машины:");
        timeHeavyLabel.setBounds(10, 240, 240, 20);
        timeHeavyArea = new TextField();
        timeHeavyArea.setBounds(240, 240, 20, 20);


        timeLightLabel = new JLabel("Период появления легковой машины:");
        timeLightLabel.setBounds(10, 340, 240, 20);
        timeLightArea = new TextField();
        timeLightArea.setBounds(240, 340, 20, 20);


        pTimeHeavyLabel = new JLabel("Вероятность появления грузовой машины:");
        pTimeHeavyLabel.setBounds(10, 260, 260, 20);
        heavySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        heavySlider.setMajorTickSpacing(10);
        heavySlider.setPaintTicks(true);
        heavySlider.setPaintLabels(true);
        heavySlider.setSnapToTicks(true);
        heavySlider.setBounds(10, 280, 250, 50);

        pTimeLightLabel = new JLabel("Вероятность появления легковой машины:");
        pTimeLightLabel.setBounds(10, 360, 260, 20);
        lightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        lightSlider.setMajorTickSpacing(10);
        lightSlider.setPaintTicks(true);
        lightSlider.setPaintLabels(true);
        lightSlider.setSnapToTicks(true);
        lightSlider.setBounds(10, 380, 250, 50);

        mainPanel.add(yesButton);
        mainPanel.add(noButton);
        mainPanel.add(showTimeLabel);
        mainPanel.add(startButton);
        mainPanel.add(endButton);
        mainPanel.add(showInfoCheckBox);
        mainPanel.add(infoArea);
        mainPanel.add(timeHeavyArea);
        mainPanel.add(timeLightArea);
        mainPanel.add(heavySlider);
        mainPanel.add(lightSlider);
        mainPanel.add(timeHeavyLabel);
        mainPanel.add(timeLightLabel);
        mainPanel.add(pTimeHeavyLabel);
        mainPanel.add(pTimeLightLabel);

        panelGen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                g.drawRect(10, 10, 500, 500);
                for (Car car : CarArrayList.getInstance().arrayCarList) {
                    car.paint(g);
                }
            }
        };
        panelGen.setFocusable(true); //Разрешить обработку клавиш
        panelGen.setBounds(10, 10, 520, 520);
        mainPanel.setBounds(530, 10, 300, 500);
        add(panelGen);
        add(mainPanel);
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
        repaint();
    }
}
