package machines;

import data.CarArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class HabitatController {

    private Timer timer;

    HabitatView view;
    HabitatModel habitat;
    public HabitatController(HabitatView view, HabitatModel habitat){
        this.view = view;
        this.habitat = habitat;
        init();
    }
    void init(){
        view.lightSlider.addChangeListener(lightChangeListener);
        view.heavySlider.addChangeListener(heavyChangeListner);
        view.startButton.addActionListener(beginListner);
        view.endButton.addActionListener(endListner);
        view.yesButton.addActionListener(radioListener);
        view.noButton.addActionListener(radioListener);
        view.showInfoCheckBox.addItemListener(showInfoCheckBoxListener);
        view.panelGen.addKeyListener(keyAdapter);
        view.timeLightArea.setText(String.valueOf(habitat.getTimeLight()));
        view.timeHeavyArea.setText(String.valueOf(habitat.getTimeHeavy()));
    }

    public void startSimulation(long t, int _amountG, int _amountL) {
        timer = new Timer();
        habitat.amountOfG = _amountG;
        habitat.amountOfL = _amountL;
        habitat.time = t;
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                habitat.time++;
                habitat.update(habitat.time);
                if (view.yesButton.isSelected()){
                    view.infoArea.setText(
                            "Количество: " + (habitat.amountOfL + habitat.amountOfG) + "\n" +
                                    "Легковые: " + habitat.amountOfL + "\n" +
                                    "Грузовые: " + habitat.amountOfG + "\n" +
                                    "Время: " + habitat.time);
                }else{
                    view.infoArea.setText(
                            "Количество: " + (habitat.amountOfL + habitat.amountOfG) + "\n" +
                                    "Легковые: " + habitat.amountOfL + "\n" +
                                    "Грузовые: " + habitat.amountOfG);
                }
                view.startButton.setEnabled(false);
                view.endButton.setEnabled(true);
                view.repaint();
            }
        }, 0, 1000);
    }

    public void endSimulation() {
        timer.cancel();
        timer.purge();
        if (view.showInfoCheckBox.isSelected()){
            Object[] options = {"Resume",
                    "Stop"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Количество: " + (habitat.amountOfL + habitat.amountOfG) + "\n" +
                            "Легковые: " + habitat.amountOfL + "\n" +
                            "Грузовые: " + habitat.amountOfG + "\n" +
                            "Время: " + habitat.time,
                    "StopDialog",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0){
                startSimulation(habitat.time, habitat.amountOfG, habitat.amountOfL);

            } else{
                CarArrayList.getInstance().arrayCarList.clear();
                view.repaint(); //"Очистка" интерфейса
                view.startButton.setEnabled(true);
                view.endButton.setEnabled(false);
            }
        } else {
            CarArrayList.getInstance().arrayCarList.clear();
            view.repaint(); //"Очистка" интерфейса
            view.startButton.setEnabled(true);
            view.endButton.setEnabled(false);
        }
    }

    ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch ( ((JRadioButton)ae.getSource()).getText() ) {
                case "Да" :
                    view.infoArea.setText(
                            "Количество: " + (habitat.amountOfL + habitat.amountOfG) + "\n" +
                                    "Легковые: " + habitat.amountOfL + "\n" +
                                    "Грузовые: " + habitat.amountOfG + "\n" +
                                    "Время: " + habitat.time);
                    break;
                case "Нет" :
                    view.infoArea.setText(
                            "Количество: " + (habitat.amountOfL + habitat.amountOfG) + "\n" +
                                    "Легковые: " + habitat.amountOfL + "\n" +
                                    "Грузовые: " + habitat.amountOfG);
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
                view.infoArea.setVisible(true);
            } else {//checkbox has been deselected
                view.infoArea.setVisible(false);
            }
        }
    };

    KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_B:
                    if (view.startButton.isEnabled()){
                        startSimulation(0,0,0);
                    }
                    break;
                case KeyEvent.VK_E:
                    endSimulation();
                    break;
                case KeyEvent.VK_T:
                    if (view.showInfoCheckBox.isSelected()) {
                        view.infoArea.setVisible(false);
                        view.showInfoCheckBox.setSelected(false);
                    } else {
                        view.infoArea.setVisible(true);
                        view.showInfoCheckBox.setSelected(true);
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

    ChangeListener lightChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider)e.getSource();
            double value = slider.getValue();
            habitat.setpLight(value/100);
        }
    };

    ChangeListener heavyChangeListner = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider)e.getSource();
            double value = slider.getValue();
            habitat.setpHeavy(value/100);
        }
    };
}
