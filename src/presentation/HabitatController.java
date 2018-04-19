package presentation;

import data.model.CarCollections;
import data.model.CarHeavy;
import data.model.CarLight;
import socket.SocketEmitter;
import socket.SocketListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

public class HabitatController {

    private HabitatView view;
    private HabitatModel model;

    //binding View&Model
    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        init();
    }

    //add listners
    private void init() {
        view.startSimulationItem.addActionListener(beginListener);
        view.endSimulationItem.addActionListener(endListener);
        view.showInfoItem.addActionListener(menuInfoListener);
        view.lightSlider.addChangeListener(lightChangeListener);
        view.heavySlider.addChangeListener(heavyChangeListner);
        view.startButton.addActionListener(beginListener);
        view.endButton.addActionListener(endListener);
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

        view.liveHeavyArea.addTextListener(liveHeavyTextFieldList);
        view.liveHeavyArea.addActionListener(liveHeavyTextFieldListner);
        view.liveLightArea.addTextListener(liveLightTextFieldList);
        view.liveLightArea.addActionListener(liveLightTextFieldListner);
        view.liveObjects.addActionListener(liveObjectsListener);
        view.lightAIButton.addActionListener(lightAIListener);
        view.heavyAIButton.addActionListener(heavyAIListener);
        view.priorHeavyAI.addActionListener(heavyAIPriorListener);
        view.priorLightAI.addActionListener(lightAIPriorListener);
        view.socketButton.addActionListener(onSocketClickListener);
        view.swapButton.addActionListener(onSwapClickListener);
        view.window[0].addWindowListener(windowClose);

    }

    private ActionListener liveObjectsListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.stopSimulation(false);
            showLiveObj();
        }
    };

    private ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (((JRadioButton) ae.getSource()).getText()) {
                case "Да":
                    view.infoArea.setText(
                            "Количество: " + (model.amountLight + model.amountHeavy) + "\n" +
                                    "Легковые: " + model.amountLight + "\n" +
                                    "Грузовые: " + model.amountHeavy + "\n" +
                                    "Время: " + model.time);
                    break;
                case "Нет":
                    view.infoArea.setText(
                            "Количество: " + (model.amountLight + model.amountHeavy) + "\n" +
                                    "Легковые: " + model.amountLight + "\n" +
                                    "Грузовые: " + model.amountHeavy);
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
                view.showTimePanel.setVisible(true);
            } else {//checkbox has been deselected
                view.infoArea.setVisible(false);
                view.showInfoItem.setState(false);
                view.showTimePanel.setVisible(false);
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
                        model.startSimulation(true);
                    }
                    break;
                case KeyEvent.VK_E:
                    model.stopSimulation(view.showInfoCheckBox.isSelected());
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

    private ActionListener endListener = e -> model.stopSimulation(view.showInfoCheckBox.isSelected());

    private ActionListener beginListener = e -> model.startSimulation(true);

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


    private int formValidation(TextField textField) {
        //Валидация формы
        try {
            Integer value = Integer.parseInt(textField.getText());
            if (value > 0) {
                return value;
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {
            return 0;
        }
    }


    private ActionListener timeLightTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            int curTimeLight = formValidation(view.timeLightArea);
            if (curTimeLight > 0) model.setTimeLight(curTimeLight);
            view.panelGen.requestFocus();
        }
    };

    private ActionListener timeHeavyTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            int curTimeHeavy = formValidation(view.timeHeavyArea);
            if (curTimeHeavy > 0) model.setTimeHeavy(curTimeHeavy);
            view.panelGen.requestFocus();
        }
    };

    private ActionListener liveLightTextFieldListner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int curLiveTimeHeavy = formValidation(view.liveHeavyArea);
            if (curLiveTimeHeavy > 0) {
                CarHeavy.liveTime = curLiveTimeHeavy;
            }
            view.panelGen.requestFocus();
        }
    };

    private ActionListener liveHeavyTextFieldListner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int curLiveTimeHeavy = formValidation(view.liveHeavyArea);
            if (curLiveTimeHeavy > 0) CarHeavy.liveTime = curLiveTimeHeavy;
            view.panelGen.requestFocus();
        }
    };

    private TextListener timeLightTextFieldList = new TextListener() {
        @Override
        public void textValueChanged(TextEvent e) {
            int curTimeLight = formValidation(view.timeLightArea);
            if (curTimeLight > 0) model.setTimeLight(curTimeLight);
        }
    }; //Обработка текста во время ввода
    private TextListener timeHeavyTextFieldList = new TextListener() {
        @Override
        public void textValueChanged(TextEvent e) {
            int curTimeHeavy = formValidation(view.timeHeavyArea);
            if (curTimeHeavy > 0) model.setTimeHeavy(curTimeHeavy);
        }
    }; //Обработка текста во время ввода

    private TextListener liveHeavyTextFieldList = new TextListener() {
        @Override
        public void textValueChanged(TextEvent e) {
            int curLiveTimeHeavy = formValidation(view.liveHeavyArea);
            if (curLiveTimeHeavy > 0) CarHeavy.liveTime = curLiveTimeHeavy;
        }
    };

    private TextListener liveLightTextFieldList = new TextListener() {
        @Override
        public void textValueChanged(TextEvent e) {
            int curLiveTimeLight = formValidation(view.liveLightArea);
            if (curLiveTimeLight > 0) CarLight.liveTime = curLiveTimeLight;
        }
    };

    private ActionListener lightAIListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.lightAI.paused) {
                model.beginLightAI();
            } else {
                model.pauseLightAI();
            }
        }
    };


    private ActionListener heavyAIListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.heavyAI.paused) {
                model.beginHeavyAI();
            } else {
                model.pauseHeavyAI();
            }
        }
    };
    Socket socket;
    SocketListener socketListener;
    SocketEmitter socketEmitter;
    private boolean open;
    private ActionListener onSocketClickListener = e -> {
        String host = "localhost";
        int port = 8000;
        if (!open) {
            view.socketButton.setText("Закрыть");
            try {
                socket = new Socket(host, port);
                socketListener = new SocketListener(socket, view, model);
                socketListener.start();
                socketEmitter = new SocketEmitter(socket);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            view.socketButton.setText("Открыть");
            try {
                socket.getInputStream().close();
                socket.getOutputStream().close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        open = !open;
    };
    private ActionListener onSwapClickListener = e -> {
        try {
            socketEmitter.swap(CarCollections.getInstance().users.get(view.usersList.getSelectedIndex()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };
    private ActionListener heavyAIPriorListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.heavyAI.setPriority(Integer.parseInt(String.valueOf(view.priorHeavyAI.getSelectedItem())));
            System.out.println("heavyAI priority is: " + model.heavyAI.getPriority());
        }
    };

    private ActionListener lightAIPriorListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.lightAI.setPriority(Integer.parseInt(String.valueOf(view.priorLightAI.getSelectedItem())));
            System.out.println("lightAI priority is: " + model.lightAI.getPriority());
        }
    };


    private void showLiveObj() {
        Object[] options = {"Resume"};
        int n = JOptionPane.showOptionDialog(new JFrame(),
                CarCollections.getInstance().liveObjString(),
                "LiveObjects",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n == 0) {
            model.startSimulation(false);
        }
    }

    private WindowAdapter windowClose = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            model.lightAI.isGoing = false;
            model.heavyAI.isGoing = false;
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    };
}
