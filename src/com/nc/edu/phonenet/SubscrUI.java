package com.nc.edu.phonenet;
import com.nc.edu.phonenet.model.Subscriber;
import com.nc.edu.phonenet.read_write.SubscrReaderWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ксения on 2/6/2016.
 */
public class SubscrUI extends JFrame {
   /* public SubscrUI() {
        super("Абоненты");
        setTitle("Абоненты");
        setSize(370, 100);
        setLocation(500, 200);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setResizable(false);

        final JPanel grid = new JPanel(new GridLayout(1, 3) );
        // добавление кнопок
        JButton but1 = new JButton("Найти");
        JButton but2 = new JButton("Добавить");
        JButton but3 = new JButton("Изменить");
        JButton but4 = new JButton("Записать");
        grid.add(but1);
        grid.add(but2);
        grid.add(but3);
        grid.add(but4);

        JPanel flow = new JPanel(new FlowLayout(
                FlowLayout.CENTER ));
        add(flow, BorderLayout.SOUTH);

        flow.add(grid);

        setVisible(true);

        //слушатель кнопки Найти
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame findForm = new JFrame("Найти");
                findForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                findForm.setSize(300,200);
                findForm.setLocation(500, 200);
                findForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(1, 2) );
                final JTextField text1 = new JTextField("", 10);
                JButton but4 = new JButton("Найти");
                grid2.add(text1);
                grid2.add(but4);

                JPanel border = new JPanel(new BorderLayout());
                findForm.add(border, BorderLayout.NORTH);
                border.add(grid2,BorderLayout.NORTH);
                final DefaultListModel listModel = new DefaultListModel();
                final JList findList = new JList(listModel);
                findList.setSelectedIndex(0);
                findList.setFocusable(false);
                border.add(new JScrollPane(findList), BorderLayout.SOUTH);

                //слушатель кнопки Найти на второй форме
                but4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listModel.removeAllElements();
                        String str = text1.getText();
                        if (str.isEmpty())
                            listModel.addElement("Пожалуйста, введите запрос");
                        SubscrReaderWriter sr = new SubscrReaderWriter("SubscriberList.txt");
                        java.util.List<Subscriber> ss =  sr.readSubscr();
                        for (Subscriber subscr : ss) {
                            String subname = subscr.getName();
                            String subnumber = subscr.getPhnumber();
                            if ((subname.contains(str) || subnumber.contains(str)) && !str.isEmpty()) {
                               listModel.addElement(subscr.getPhnumber() + ' ' + subscr.getName() + ' ' + Double.valueOf(subscr.getBalance()).toString());
                            }
                        }
                        if (listModel.isEmpty())
                            listModel.addElement("Совпадений не найдено");
                   }
                });

            }
        });

        //слушатель кнопки Добавить
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame addForm = new JFrame("Добавить");
                addForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addForm.setSize(400,200);
                addForm.setLocation(500, 200);
                addForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(5, 2) );
                JPanel grid3 = new JPanel(new GridLayout(1, 1) );
                JButton but4 = new JButton("Добавить");
                final JTextField text1 = new JTextField("", 10);
                JLabel jlab1 = new JLabel("Введите номер:");
                final JTextField text2 = new JTextField("", 10);
                JLabel jlab2 = new JLabel("Введите фамилию:");
                final JTextField text3 = new JTextField("", 10);
                JLabel jlab3 = new JLabel("Введите имя:");
                final JTextField text4 = new JTextField("", 10);
                JLabel jlab4 = new JLabel("Введите отчество:");
                final JTextField text5 = new JTextField("", 10);
                JLabel jlab5 = new JLabel("Введите баланс:");

                grid2.add(jlab1);
                grid2.add(text1);
                grid2.add(jlab2);
                grid2.add(text2);
                grid2.add(jlab3);
                grid2.add(text3);
                grid2.add(jlab4);
                grid2.add(text4);
                grid2.add(jlab5);
                grid2.add(text5);

                grid3.add(but4);

                JPanel border = new JPanel(new BorderLayout());
                addForm.add(border, BorderLayout.CENTER);
                border.add(grid2,BorderLayout.CENTER);

                JPanel border2 = new JPanel(new BorderLayout());
                addForm.add(border2, BorderLayout.SOUTH);
                border2.add(grid3,BorderLayout.CENTER);

                but4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str1 = text1.getText();
                        String str2 = text2.getText();
                        String str3 = text3.getText();
                        String str4 = text4.getText();
                        String str5 = text5.getText();
                        SubscrReaderWriter sw = new SubscrReaderWriter("SubscriberList.txt");
                            sw.writeSubscr(str1 + ' ' + str2 + ' ' + str3 + ' ' + str4 + ' ' + str5);
                    }
                });

            }
        });

        //слушатель кнопки Изменить
        but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame changeForm = new JFrame("Изменить");
                changeForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                changeForm.setSize(400,120);
                changeForm.setLocation(500, 200);
                changeForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(2, 2) );
                JPanel grid3 = new JPanel(new GridLayout(1, 1) );
                final JTextField text1 = new JTextField("", 10);
                JLabel jlab1 = new JLabel("Введите номер абонента:");
                final JTextField text2 = new JTextField("", 10);
                JLabel jlab2 = new JLabel("Введите изменение баланса:");
                JButton but4 = new JButton("Добавить");
                grid2.add(jlab1);
                grid2.add(text1);
                grid2.add(jlab2);
                grid2.add(text2);
                grid3.add(but4);

                JPanel border = new JPanel(new BorderLayout());
                changeForm.add(border, BorderLayout.CENTER);
                border.add(grid2,BorderLayout.CENTER);

                JPanel border2 = new JPanel(new BorderLayout());
                changeForm.add(border2, BorderLayout.SOUTH);
                border2.add(grid3,BorderLayout.CENTER);


                but4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str1 = text1.getText();
                        String str2 = text2.getText();
                        SubscrReaderWriter sr = new SubscrReaderWriter("SubscriberList.txt");
                        List<Subscriber> ss =  sr.readSubscr();
                        for (int i = 0; i < ss.size(); i++) {
                            String subnumber = ss.get(i).getPhnumber();
                            if ( subnumber.equals(str1)) {
                                ss.get(i).replenish(Double.valueOf(str2));
                            }
                        }
                        SubscrReaderWriter srw = new SubscrReaderWriter("SubscriberList.txt");
                            srw.rewriteSubscr(ss);
                    }
                });
            }
        });
        //слушатель кнопки Записать
        but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame registerForm = new JFrame("Записать");
                registerForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registerForm.setSize(500,120);
                registerForm.setLocation(500, 200);
                registerForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(1, 2) );
                JPanel grid3 = new JPanel(new GridLayout(1, 2) );
                JPanel grid4 = new JPanel(new GridLayout(1, 1) );
                SubscrReaderWriter rw = new SubscrReaderWriter("SubscriberList.txt");
                List <Subscriber> sc = rw.readSubscr();
                String items [] = new String[sc.size()];
                for (int i = 0; i < sc.size(); i++)
                    items[i] = sc.get(i).getPhnumber() + ' ' + sc.get(i).getName();
                final JComboBox jcombOut = new JComboBox(items);
                final JComboBox jcombIn = new JComboBox(items);
                final JTextField text1 = new JTextField("", 10);
                JButton but5 = new JButton("Записать");
                JLabel jlab = new JLabel("Стоимость");
                grid2.add(jcombOut);
                grid2.add(jcombIn);
                grid3.add(jlab);
                grid3.add(text1);
                grid4.add(but5);

                JPanel border = new JPanel(new BorderLayout());
                registerForm.add(border, BorderLayout.NORTH);
                border.add(grid2,BorderLayout.CENTER);

                JPanel border2 = new JPanel(new BorderLayout());
                registerForm.add(border2, BorderLayout.CENTER);
                border2.add(grid3,BorderLayout.CENTER);

                JPanel border3 = new JPanel(new BorderLayout());
                registerForm.add(border3, BorderLayout.SOUTH);
                border3.add(grid4,BorderLayout.CENTER);

                //слушатель кнопки Записать на второй форме
                but5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Double cost = Double.valueOf(text1.getText());
                        String strOut = (String)jcombOut.getSelectedItem();
                        String strIn = (String)jcombIn.getSelectedItem();
                        SubscrReaderWriter rw = new SubscrReaderWriter("SubscriberList.txt");
                        List <Subscriber> sc = rw.readSubscr();
                        SubscrReaderWriter srw = new SubscrReaderWriter("CallReg.txt");
                        String outNumber = strOut.split(" ")[0];
                        if (strOut.equals(strIn)) {
                            final JFrame errorForm = new JFrame("Ошибка");
                            errorForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            errorForm.setSize(500, 120);
                            errorForm.setLocation(500, 200);
                            errorForm.setVisible(true);
                            JLabel jl = new JLabel("Невозможно зарегистрировать звонок абонента себе");
                            JPanel grid = new JPanel(new GridLayout(1, 1));
                            grid.add(jl);
                            JPanel border = new JPanel(new BorderLayout());
                            errorForm.add(border, BorderLayout.NORTH);
                            border.add(grid,BorderLayout.CENTER);
                        }
                        else {
                            srw.writeSubscr(strOut + ' ' + strIn + ' ' + cost);
                            for (int i = 0; i < sc.size(); i++)
                            {
                                if (sc.get(i).getPhnumber().equals(outNumber))
                                    sc.get(i).replenish(cost);
                            }
                            rw.rewriteSubscr(sc);
                        }
                    }
                });

            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SubscrUI();
            }
        });
    }*/
}
