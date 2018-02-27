package machines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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



    public View(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
    }


    public void drawUI() {
        setLayout(null);
        CarArrayList ms = CarArrayList.getInstance();
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        startButton = new JButton("start");
        startButton.setSize(100, 25);
        startButton.addActionListener(e -> Habitat.startSimulation());

        endButton = new JButton("end");
        endButton.setSize(100, 25);
        endButton.setLocation(0, 30);
        endButton.setEnabled(false);
        endButton.addActionListener(e -> Habitat.endSimulation());

        infoArea = new JTextArea();
        infoArea.setBounds(0, 130, 150, 100);
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
        panelGen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B:
                        timer = new Timer();
                        timer.schedule(new TimerTask() { //Добавление задания в таймер
                            public void run() {
                                Habitat.time++;
                                Habitat.update(Habitat.time);
                                infoArea.setText(
                                        "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                                "Легковые: " + Habitat.amountOfL + "\n" +
                                                "Грузовые: " + Habitat.amountOfG + "\n" +
                                                "Время: " + Habitat.time);

                                startButton.setEnabled(false);
                                endButton.setEnabled(true);
                                repaint();
                            }
                        }, 0, 1000);

                        Habitat.startSimulation();
                        break;
                    case KeyEvent.VK_E:
                        timer.cancel();
                        timer.purge();
                        Habitat.endSimulation();
                        repaint(); //"Очистка" интерфейса
                        showInfoCheckBox.setSelected(true);
                        infoArea.setVisible(true);
                        startButton.setEnabled(true);
                        endButton.setEnabled(false);

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
    }
}
