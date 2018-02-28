package machines;

import data.CarArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class Controllers {

    private Timer timer;

    View v;
    Controllers (View v){
        this.v = v;
    }

    public void startSimulation(long t, int _amountG, int _amountL) {
        timer = new Timer();
        Habitat.amountOfG = _amountG;
        Habitat.amountOfL = _amountL;
        Habitat.time = t;
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                Habitat.time++;
                Habitat.update(Habitat.time);
                if (v.yesButton.isSelected()){
                    v.infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG + "\n" +
                                    "Время: " + Habitat.time);
                }else{
                    v.infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG);
                }
                v.startButton.setEnabled(false);
                v.endButton.setEnabled(true);
                v.repaint();
            }
        }, 0, 1000);
    }

    public void endSimulation() {
        timer.cancel();
        timer.purge();
        if (v.showInfoCheckBox.isSelected()){
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
                v.repaint(); //"Очистка" интерфейса
                v.startButton.setEnabled(true);
                v.endButton.setEnabled(false);
            }
        } else {
            CarArrayList.getInstance().arrayCarList.clear();
            v.repaint(); //"Очистка" интерфейса
            v.startButton.setEnabled(true);
            v.endButton.setEnabled(false);
        }
    }

    ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch ( ((JRadioButton)ae.getSource()).getText() ) {
                case "Да" :
                    v.infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG + "\n" +
                                    "Время: " + Habitat.time);
                    break;
                case "Нет" :
                    v.infoArea.setText(
                            "Количество: " + (Habitat.amountOfL + Habitat.amountOfG) + "\n" +
                                    "Легковые: " + Habitat.amountOfL + "\n" +
                                    "Грузовые: " + Habitat.amountOfG);
                    break;
                default:
                    break;
            }
        }
    };

    ItemListener showInfoCheckBoxListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                v.infoArea.setVisible(true);
            } else {//checkbox has been deselected
                v.infoArea.setVisible(false);
            }
        }
    };

    KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_B:
                    if (v.startButton.isEnabled()){
                        startSimulation(0,0,0);
                    }
                    break;
                case KeyEvent.VK_E:
                    endSimulation();
                    break;
                case KeyEvent.VK_T:
                    if (v.showInfoCheckBox.isSelected()) {
                        v.infoArea.setVisible(false);
                        v.showInfoCheckBox.setSelected(false);
                    } else {
                        v.infoArea.setVisible(true);
                        v.showInfoCheckBox.setSelected(true);
                    }
                    break;
            }
        }
    };

    ActionListener endListner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            endSimulation();
        }
    };

    ActionListener beginListner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            startSimulation(0, 0, 0);
        }
    };
}
