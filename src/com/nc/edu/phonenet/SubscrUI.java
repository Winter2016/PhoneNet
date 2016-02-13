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
    public SubscrUI() {
        super("Абоненты");
        setTitle("Абоненты");
        setSize(300, 100);
        setLocation(500, 200);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setResizable(false);

        JPanel grid = new JPanel(new GridLayout(1, 3) );
        // добавление кнопок
        JButton but1 = new JButton("Найти");
        JButton but2 = new JButton("Добавить");
        JButton but3 = new JButton("Изменить");
        grid.add(but1);
        grid.add(but2);
        grid.add(but3);

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
                addForm.setSize(300,100);
                addForm.setLocation(500, 200);
                addForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(1, 2) );
                final JTextField text1 = new JTextField("", 10);
                JButton but4 = new JButton("Добавить");
                grid2.add(text1);
                grid2.add(but4);

                JPanel border = new JPanel(new BorderLayout());
                addForm.add(border, BorderLayout.NORTH);
                border.add(grid2,BorderLayout.CENTER);

                but4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str = text1.getText();
                        SubscrReaderWriter sw = new SubscrReaderWriter("SubscriberList.txt");
                            sw.writeSubscr(str);
                    }
                });

            }
        });

        //слушатель кнопки Изменить
        //пока работает так: надо ввести номер абонента и через пробел сумму, на которую изменяется баланс
        but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame changeForm = new JFrame("Изменить");
                changeForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                changeForm.setSize(300,100);
                changeForm.setLocation(500, 200);
                changeForm.setVisible(true);
                JPanel grid2 = new JPanel(new GridLayout(1, 2) );
                final JTextField text1 = new JTextField("", 10);
                JButton but4 = new JButton("Добавить");
                grid2.add(text1);
                grid2.add(but4);

                JPanel border = new JPanel(new BorderLayout());
                changeForm.add(border, BorderLayout.NORTH);
                border.add(grid2,BorderLayout.CENTER);

                but4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str = text1.getText();
                        String chunks[] = str.split(" ");
                        SubscrReaderWriter sr = new SubscrReaderWriter("SubscriberList.txt");
                        List<Subscriber> ss =  sr.readSubscr();
                        for (int i = 0; i < ss.size(); i++) {
                            String subnumber = ss.get(i).getPhnumber();
                            if ( subnumber.equals(chunks[0])) {
                                ss.get(i).replenish(Double.valueOf(chunks[1]));
                            }
                        }
                        SubscrReaderWriter srw = new SubscrReaderWriter("SubscriberList.txt");
                            srw.rewriteSubscr(ss);
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
    }
}
