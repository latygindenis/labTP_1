package machines;

import data.Car;
import data.CarArrayList;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;

    Controllers controllers;
    JLabel mes = null;
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




    public View(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
    }

    public void drawUI() {
        Controllers controllers = new Controllers(this);
        setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);
        startButton.addActionListener(controllers.beginListner);

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);
        endButton.addActionListener(controllers.endListner);

        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setBounds(10, 125, 100, 20);



        yesButton = new JRadioButton("Да");
        yesButton.setFocusable(false);
        yesButton.addActionListener(controllers.radioListener);
        yesButton.setBounds(0, 145, 50, 25);

        noButton = new JRadioButton("Нет");
        noButton.setFocusable(false);
        noButton.addActionListener(controllers.radioListener);
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
        showInfoCheckBox.addItemListener(controllers.showInfoCheckBoxListener);

        timeHeavyArea = new JTextField(String.valueOf(Habitat.timeHeavy));
        timeHeavyArea.setBounds(0, 250, 20, 20);


        timeLightArea = new JTextField(String.valueOf(Habitat.timeLight));
        timeLightArea.setBounds(0, 280, 20, 20);

        heavySlider = new JSlider(JSlider.VERTICAL, 0, 100,(int) (Habitat.pHeavy*100));
        heavySlider.setMajorTickSpacing(10);
        heavySlider.setPaintTicks(true);
        heavySlider.setPaintLabels(true);
        heavySlider.setSnapToTicks(true);
        heavySlider.setBounds(40, 250, 50, 200);

        lightSlider = new JSlider(JSlider.VERTICAL, 0, 100,(int) (Habitat.pLight*100));
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
        panelGen.addKeyListener(controllers.keyAdapter);
        panelGen.setBounds(10, 10, 520, 520);
        mainPanel.setBounds(530, 10, 300, 500);
        mes = new JLabel("", JLabel.RIGHT);
        panelGen.add(mes);
        add(panelGen);
        add(mainPanel);
        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
