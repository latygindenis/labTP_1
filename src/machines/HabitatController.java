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
    private HabitatView view;
    private HabitatModel model;

    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        view.startSimulationItem.addActionListener(beginListner);
        view.endSimulationItem.addActionListener(endListner);
        view.showInfoItem.addActionListener(menuInfoListener);
        view.lightSlider.addChangeListener(lightChangeListener);
        view.heavySlider.addChangeListener(heavyChangeListner);
        view.startButton.addActionListener(beginListner);
        view.endButton.addActionListener(endListner);
        view.yesButton.addActionListener(radioListener);
        view.noButton.addActionListener(radioListener);
        view.showInfoCheckBox.addItemListener(showInfoCheckBoxListener);
        view.panelGen.addKeyListener(keyAdapter);
        view.timeLightArea.addTextListener(timeLightTextFieldList);
        view.timeLightArea.addActionListener(timeLightTextFieldListener);
        view.timeLightArea.setText(String.valueOf(model.getTimeLight()));

        view.timeHeavyArea.addTextListener(timeHeavyTextFieldList);
        view.timeHeavyArea.addActionListener(timeHeavyTextFieldListener);
        view.timeHeavyArea.setText(String.valueOf(model.getTimeHeavy()));
    }

    private void startSimulation(long t, int _amountG, int _amountL) {
        timer = new Timer();
        model.amountOfG = _amountG;
        model.amountOfL = _amountL;
        model.time = t;
        timer.schedule(new TimerTask() { //Добавление задания в таймер
            public void run() {
                model.time++;
                model.update(model.time);
                if (view.yesButton.isSelected()) {
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Легковые: " + model.amountOfL + "\n" +
                                    "Грузовые: " + model.amountOfG + "\n" +
                                    "Время: " + model.time);
                } else {
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Легковые: " + model.amountOfL + "\n" +
                                    "Грузовые: " + model.amountOfG);
                }
                view.startButton.setEnabled(false);
                view.startSimulationItem.setEnabled(false);
                view.endSimulationItem.setEnabled(true);
                view.endButton.setEnabled(true);
                view.repaint();
            }
        }, 0, 1000);
    }

    private void endSimulation() {
        timer.cancel();
        timer.purge();
        if (view.showInfoCheckBox.isSelected()) {
            Object[] options = {"Resume",
                    "Stop"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                            "Легковые: " + model.amountOfL + "\n" +
                            "Грузовые: " + model.amountOfG + "\n" +
                            "Время: " + model.time,
                    "StopDialog",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                startSimulation(model.time, model.amountOfG, model.amountOfL);

            } else {
                CarArrayList.getInstance().arrayCarList.clear();
                view.repaint(); //"Очистка" интерфейса
                view.startButton.setEnabled(true);
                view.endButton.setEnabled(false);
            }
        } else {
            CarArrayList.getInstance().arrayCarList.clear();
            view.repaint(); //"Очистка" интерфейса
            view.startButton.setEnabled(true);
            view.startSimulationItem.setEnabled(true);
            view.endSimulationItem.setEnabled(false);
            view.endButton.setEnabled(false);
        }
    }

    private ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {


            switch (((JRadioButton) ae.getSource()).getText()) {
                case "Да":
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Легковые: " + model.amountOfL + "\n" +
                                    "Грузовые: " + model.amountOfG + "\n" +
                                    "Время: " + model.time);
                    break;
                case "Нет":
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Легковые: " + model.amountOfL + "\n" +
                                    "Грузовые: " + model.amountOfG);
                    break;
                default:
                    break;
            }
        }
    };

    private ItemListener showInfoCheckBoxListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                view.infoArea.setVisible(true);
                view.showInfoItem.setState(true);
            } else {//checkbox has been deselected
                view.infoArea.setVisible(false);
                view.showInfoItem.setState(false);
            }
        }
    };

    private ActionListener menuInfoListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.showInfoItem.getState()) {
                view.infoArea.setVisible(true);
                view.showInfoCheckBox.setSelected(true);
            } else {
                view.infoArea.setVisible(false);
                view.showInfoCheckBox.setSelected(false);
            }
        }
    };


    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_B:
                    if (view.startButton.isEnabled()) {
                        startSimulation(0, 0, 0);
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

    private ActionListener endListner = e -> endSimulation();

    private ActionListener beginListner = e -> startSimulation(0, 0, 0);

    private ChangeListener lightChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            double value = slider.getValue();
            model.setpLight(value / 100);
            view.panelGen.requestFocus();
        }
    };

    private ChangeListener heavyChangeListner = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            double value = slider.getValue();
            model.setpHeavy(value / 100);
            view.panelGen.requestFocus();
        }
    };


    private void timeHeavyValidation() {
        //Валидация формы
        try {
            Integer value;
            value = Integer.parseInt(view.timeHeavyArea.getText());
            if (value > 0) {
                model.setTimeHeavy(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private void timeLightValidation() {
        try { //Валидация формы
            Integer value;
            value = Integer.parseInt(view.timeLightArea.getText());
            if (value > 0) {
                model.setTimeLight(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private ActionListener timeLightTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLightValidation();
            view.panelGen.requestFocus();
        }
    };

    private ActionListener timeHeavyTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            timeHeavyValidation();
            view.panelGen.requestFocus();
        }
    };

    private TextListener timeLightTextFieldList = e -> timeLightValidation(); //Обработка текста во время ввода
    private TextListener timeHeavyTextFieldList = e -> timeHeavyValidation(); //Обработка текста во время ввода
}
