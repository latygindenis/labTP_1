package machines;

import data.Car;
import data.CarArrayList;

import javax.swing.*;
import java.awt.*;

public class HabitatView extends JFrame{

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;

    JMenu menuSimulation = null;
    JMenu menuUI = null;

    JMenuItem startSimulationItem = null;
    JMenuItem endSimulationItem = null;
    JCheckBoxMenuItem showInfoItem = null;
    JCheckBoxMenuItem showTimeItem = null;

    JPanel mainPanel = null;
    JPanel panelGen = null;
    JButton startButton = null;
    JButton endButton = null;
    JCheckBox showInfoCheckBox = null;
    JTextArea infoArea = null;
    JRadioButton yesButton = null;
    JRadioButton noButton = null;
    JLabel showTimeLabel = null;
    JTextField timeHeavyArea = null;
    JTextField timeLightArea = null;

    JSlider heavySlider = null;
    JSlider lightSlider = null;

    public HabitatView(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        drawMenu();
        drawUI();
    }

    private void drawMenu(){
        JMenuBar menuBar = new JMenuBar();
        menuSimulation = new JMenu("Симуляция");
        menuUI = new JMenu("Вид");

        showInfoItem = new JCheckBoxMenuItem("Показывать информацию");
        showTimeItem = new JCheckBoxMenuItem("Показывать время");

        menuUI.add(showInfoItem);
        menuUI.add(showTimeItem);


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
        Font font = new Font("Verdana", Font.PLAIN, 11);


        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);

        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setBounds(10, 125, 100, 20);

        yesButton = new JRadioButton("Да");
        yesButton.setFocusable(false);
        yesButton.setBounds(0, 145, 50, 25);

        noButton = new JRadioButton("Нет");
        noButton.setFocusable(false);
        noButton.setBounds(60, 145, 50, 25);
        noButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);

        infoArea = new JTextArea();
        infoArea.setBounds(0, 180, 150, 65);
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setFocusable(false);

        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setBounds(0, 100, 200, 25);
        showInfoCheckBox.setFocusable(false);

        timeHeavyArea = new JTextField();
        timeHeavyArea.setBounds(0, 250, 20, 20);

        timeLightArea = new JTextField();
        timeLightArea.setBounds(0, 280, 20, 20);

        heavySlider = new JSlider(JSlider.VERTICAL, 0, 100,50);
        heavySlider.setMajorTickSpacing(10);
        heavySlider.setPaintTicks(true);
        heavySlider.setPaintLabels(true);
        heavySlider.setSnapToTicks(true);
        heavySlider.setBounds(40, 250, 50, 200);

        lightSlider = new JSlider(JSlider.VERTICAL, 0, 100,50);
        lightSlider.setMajorTickSpacing(10);
        lightSlider.setPaintTicks(true);
        lightSlider.setPaintLabels(true);
        lightSlider.setSnapToTicks(true);
        lightSlider.setBounds(90, 250, 50, 200);

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

        panelGen = new JPanel()  {
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
}
