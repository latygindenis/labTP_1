package presentation;

import consol.Consol;
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
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class HabitatController {
    private Properties properties;
    private HabitatView view;
    private HabitatModel model;

    Properties defaulProp() {
        Properties prop = new Properties();
        prop.setProperty("bornL", "100");
        prop.setProperty("bornH", "100");
        prop.setProperty("periodL", "500");
        prop.setProperty("periodH", "500");
        prop.setProperty("verL", "80");
        prop.setProperty("verH", "80");
        prop.setProperty("priorL", "2");
        prop.setProperty("priorH", "1");
        return prop;
    }

    //binding View&Model
    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("prop.properties")));
        } catch (FileNotFoundException e) {
            try {
                defaulProp().store(new FileOutputStream(new File("prop.properties")), "Config");
                properties = defaulProp();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    void setProp(Properties properties) {

        model.setTimeHeavy(Integer.parseInt(properties.getProperty("bornH")));
        view.liveHeavyArea.setText(String.valueOf(model.getTimeHeavy()));

        model.setTimeLight(Integer.parseInt(properties.getProperty("bornL")));
        view.liveLightArea.setText(String.valueOf(model.getTimeLight()));

        CarHeavy.liveTime = Integer.parseInt(properties.getProperty("periodH"));
        view.timeHeavyArea.setText(String.valueOf(CarHeavy.liveTime));

        CarLight.liveTime = Integer.parseInt(properties.getProperty("periodL"));
        view.timeLightArea.setText(String.valueOf(CarLight.liveTime));

        model.setpLight(Integer.parseInt(properties.getProperty("verL")));
        view.lightSlider.setValue(Integer.parseInt(properties.getProperty("verL")));

        model.setpHeavy(Integer.parseInt(properties.getProperty("verH")));
        view.heavySlider.setValue(Integer.parseInt(properties.getProperty("verH")));

        model.heavyAI.setPriority(Integer.parseInt(properties.getProperty("priorH")));
        view.priorHeavyAI.setSelectedIndex(Integer.parseInt(properties.getProperty("priorH")) - 1);

        model.lightAI.setPriority(Integer.parseInt(properties.getProperty("priorL")));
        view.priorLightAI.setSelectedIndex(Integer.parseInt(properties.getProperty("priorL")) - 1);
    }

    //add listners
    private void init() {
        setProp(properties);
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

        view.timeHeavyArea.addTextListener(timeHeavyTextFieldList);
        view.timeHeavyArea.addActionListener(timeHeavyTextFieldListener);

        view.liveHeavyArea.addTextListener(liveHeavyTextFieldList);
        view.liveHeavyArea.addActionListener(liveHeavyTextFieldListner);
        view.liveLightArea.addTextListener(liveLightTextFieldList);
        view.liveLightArea.addActionListener(liveLightTextFieldListner);
        view.liveObjects.addActionListener(liveObjectsListener);
        view.lightAIButton.addActionListener(lightAIListener);
        view.heavyAIButton.addActionListener(heavyAIListener);
        view.openConsol.addActionListener(consolButtonListener);
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

    private  ActionListener consolButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Consol consol = new Consol(200, 200){
                @Override
                public void command(String cmd) {
                    result = null;
                    if (cmd.equalsIgnoreCase("pL")) {
                        if (model.lightAI.paused) {
                            result = "Интеллект легковой остановлен :(";
                        } else {
                            model.pauseLightAI();
                            result =  "Интеллект легковой приостановлен";
                        }
                    } else if (cmd.equalsIgnoreCase("bL")) {
                        if (model.lightAI.paused) {
                            model.beginLightAI();
                            result = "Интеллект легковой возобновлен";
                        } else {
                            result = "Интеллект легковой уже запустили((";
                        }
                    } else if (cmd.equalsIgnoreCase("pH")){
                        if (model.heavyAI.paused) {
                            result = "Интеллект грузовой уже остановлен :(";
                        } else {
                            model.pauseHeavyAI();
                            result = "Интеллект грузовой приостановлен";
                        }
                    } else if (cmd.equalsIgnoreCase("bH")){
                        if (model.heavyAI.paused) {
                            model.beginHeavyAI();
                            result ="Интеллект грузовой возобновлен";
                        } else {
                            result = "Интеллект грузовой запустили((";
                        }
                    } else if (cmd.equalsIgnoreCase("help")){

                        result = "b(L/H)- запуск интеллекта объектов\n" +
                                "p(L/H) - приостановка интеллекта объектов";
                    }
                    super.command(cmd);
                }
            };
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

            properties.setProperty("bornL", view.liveLightArea.getText());
            properties.setProperty("bornH", view.liveHeavyArea.getText());

            properties.setProperty("periodL", view.timeLightArea.getText());
            properties.setProperty("periodH", view.timeHeavyArea.getText());
            properties.setProperty("verL", String.valueOf(view.lightSlider.getValue()));
            properties.setProperty("verH", String.valueOf(view.heavySlider.getValue()));
            properties.setProperty("priorL", String.valueOf(model.lightAI.getPriority()));
            properties.setProperty("priorH", String.valueOf(model.heavyAI.getPriority()));
            try {
                properties.store(new FileOutputStream(new File("prop.properties")), "Config");
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            try {
                socket.close();
            } catch (Exception el) {
                el.printStackTrace();
                e.getWindow().setVisible(false);
                System.exit(0);
            }
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    };
}
