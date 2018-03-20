package presentation;

import data.Car;
import data.CarCollections;

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
    JPanel showTimePanel;
    JButton startButton;
    JButton endButton;
    JCheckBox showInfoCheckBox;
    JTextArea infoArea;
    JRadioButton yesButton;
    JRadioButton noButton;
    JLabel showTimeLabel;
    JPanel parentPanel;

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
        parentPanel = new JPanel();
        parentPanel.setLayout(new GridLayout(1, 2));


        mainPanel = new JPanel();
        mainPanel.setAlignmentX(1);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        startButton = new JButton("start");
       startButton.setMaximumSize(new Dimension(150, 25));
        startButton.setAlignmentX(1);
        endButton = new JButton("end");
        endButton.setMaximumSize(new Dimension(150, 25));
        endButton.setEnabled(false);
        endButton.setAlignmentX(1);


        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setFocusable(false);
        showInfoCheckBox.setAlignmentX((float) 0.8);




        showTimePanel = new JPanel();
        showTimePanel.setLayout(new BoxLayout(showTimePanel, BoxLayout.LINE_AXIS));

        showTimeLabel = new JLabel("Показать время?");
        yesButton = new JRadioButton("Да");
        yesButton.setFocusable(false);

        noButton = new JRadioButton("Нет");
        noButton.setFocusable(false);

        noButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);
        showTimePanel.add(showTimeLabel);
        showTimePanel.add(yesButton);
        showTimePanel.add(noButton);
        showTimePanel.setVisible(false);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setFocusable(false);
        infoArea.setMaximumSize(new Dimension(300, 75));


        JPanel timeHeavyPanel = new JPanel();
        timeHeavyPanel.setLayout(new BoxLayout(timeHeavyPanel, BoxLayout.LINE_AXIS));
        timeHeavyLabel = new JLabel("Период появления грузовой машины:");
        timeHeavyArea = new TextField();
        timeHeavyArea.setMaximumSize(new Dimension(50, 25));

        timeHeavyPanel.add(timeHeavyLabel);
        timeHeavyPanel.add(timeHeavyArea);
        timeHeavyPanel.setAlignmentX((float) 0.1);

        timeLightLabel = new JLabel("Период появления легковой машины:");
        timeLightArea = new TextField();
        timeLightArea.setMaximumSize(new Dimension(50,25));



        pTimeHeavyLabel = new JLabel("Вероятность появления грузовой машины:");
        pTimeHeavyLabel.setAlignmentX(1);
        heavySlider = new JSlider(JSlider.HORIZONTAL, 0, 100,50);
        heavySlider.setMajorTickSpacing(10);
        heavySlider.setPaintTicks(true);
        heavySlider.setPaintLabels(true);
        heavySlider.setSnapToTicks(true);

        pTimeLightLabel = new JLabel("Вероятность появления легковой машины:");
        lightSlider = new JSlider(JSlider.HORIZONTAL, 0, 100,50);
        lightSlider.setMajorTickSpacing(10);
        lightSlider.setPaintTicks(true);
        lightSlider.setPaintLabels(true);
        lightSlider.setSnapToTicks(true);



        mainPanel.add(startButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(endButton);
        mainPanel.add(showInfoCheckBox);
        mainPanel.add(infoArea);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(showTimePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(timeHeavyPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(pTimeHeavyLabel);
        mainPanel.add(heavySlider);
        mainPanel.add(timeLightLabel);
        mainPanel.add(timeLightArea);
        mainPanel.add(pTimeLightLabel);
        mainPanel.add(lightSlider);
        mainPanel.setMaximumSize(new Dimension(300, 500));


        panelGen = new JPanel()  {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                g.drawRect(10, 10, getWidth()-40, getHeight()-40);
                for (Car car : CarCollections.getInstance().arrayCarList) {
                    car.paint(g);
                }
               // System.out.print(mainPanel.getWidth()+ " " + mainPanel.getHeight());
            }
        };
        panelGen.setFocusable(true); //Разрешить обработку клавиш
        add(parentPanel);

        parentPanel.add(panelGen);
        parentPanel.add(mainPanel);
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
    void stopSimulation() {
        repaint(); //"Очистка" интерфейса
        startButton.setEnabled(true);
        startSimulationItem.setEnabled(true);
        endSimulationItem.setEnabled(false);
        endButton.setEnabled(false);
    }
}
