package machines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class View extends JFrame{

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;
    private Timer timer;

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




    public View(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
    }
    private void startSimulation(long t, int _amountG, int _amountL) {
        timer = new Timer();
        Habitat.amountOfG = _amountG;
        Habitat.amountOfL = _amountL;
        Habitat.time = t;
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                Habitat.time++;
                Habitat.update(Habitat.time);
                if (yesButton.isSelected()){
                    infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG + "\n" +
                                    "Время: " + Habitat.time);
                }else{
                    infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG);
                }


                startButton.setEnabled(false);
                endButton.setEnabled(true);
                repaint();
            }
        }, 0, 1000);
    }

    private void endSimulation() {
        timer.cancel();
        timer.purge();
        if (showInfoCheckBox.isSelected()){
            Object[] options = {"Resume",
                    "Stop"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                            "Легковые: " + Habitat.amountOfL + "\n" +
                            "Грузовые: " + Habitat.amountOfG + "\n" +
                            "Время: " + Habitat.time,
                    "StopDialog",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0){
                startSimulation(Habitat.time, Habitat.amountOfG, Habitat.amountOfL);

            } else{
                CarArrayList.getInstance().arrayCarList.clear();
                repaint(); //"Очистка" интерфейса
                startButton.setEnabled(true);
                endButton.setEnabled(false);
            }
        } else {
            CarArrayList.getInstance().arrayCarList.clear();
            repaint(); //"Очистка" интерфейса
            startButton.setEnabled(true);
            endButton.setEnabled(false);
        }
    }


    public void drawUI() {
        setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);
        startButton.addActionListener(e -> startSimulation(0, 0, 0));

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);
        endButton.addActionListener(e -> endSimulation());

        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setBounds(10, 125, 100, 20);

        yesButton = new JRadioButton("Да");
        yesButton.setFocusable(false);
        yesButton.addActionListener(radioListener);
        yesButton.setBounds(0, 145, 50, 25);

        noButton = new JRadioButton("Нет");
        noButton.setFocusable(false);
        noButton.addActionListener(radioListener);
        noButton.setBounds(60, 145, 50, 25);
        noButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);


        infoArea = new JTextArea();
        infoArea.setBounds(0, 180, 150, 100);
        infoArea.setEditable(false);
        infoArea.setVisible(false);
        infoArea.setFocusable(false);





        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setBounds(0, 100, 200, 25);
        showInfoCheckBox.setFocusable(false);
        showInfoCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                infoArea.setVisible(true);
            } else {//checkbox has been deselected
                infoArea.setVisible(false);
            }
        });


        mainPanel.add(yesButton);
        mainPanel.add(noButton);
        mainPanel.add(showTimeLabel);
        mainPanel.add(startButton);
        mainPanel.add(endButton);
        mainPanel.add(showInfoCheckBox);
        mainPanel.add(infoArea);
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
        panelGen.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B:
                        if (startButton.isEnabled()){
                            startSimulation(0,0,0);
                        }
                        break;
                    case KeyEvent.VK_E:
                        endSimulation();
                        break;
                    case KeyEvent.VK_T:
                        if (showInfoCheckBox.isSelected()) {
                            infoArea.setVisible(false);
                            showInfoCheckBox.setSelected(false);
                        } else {
                            infoArea.setVisible(true);
                            showInfoCheckBox.setSelected(true);
                        }
                        break;
                }
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
    }

    ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch ( ((JRadioButton)ae.getSource()).getText() ) {
                case "Да" :
                    infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG + "\n" +
                                    "Время: " + Habitat.time);
                    break;
                case "Нет" :
                    infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG);
                    break;
                default:
                    break;
            }
        }
    };
}
