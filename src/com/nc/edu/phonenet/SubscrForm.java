package com.nc.edu.phonenet;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;
import com.nc.edu.phonenet.read_write.CallRegReaderWriter;
import com.nc.edu.phonenet.read_write.JSONSubscrReaderWriter;
import com.nc.edu.phonenet.read_write.SubscrReaderWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Ксения on 3/1/2016.
 */
public class SubscrForm extends JFrame{
    private JButton clickMeButton;
    private JPanel rootPane;
    private JTabbedPane selectPane;
    private JPanel findPanel;
    private JPanel addPanel;
    private JPanel changePanel;
    private JPanel callRegPanel;
    private JTextField findTextField;
    private JButton findBut;
    private JTextField surNameTextField;
    private JTextField fNameTextField;
    private JTextField sNameTextField;
    private JButton addBut;
    private JTextField phNumTextField;
    private JTextField balTextField;
    private JTextField chPhTextField;
    private JTextField chBalTextField;
    private JButton changeBut;
    private JLabel surNameLab;
    private JLabel fNameLab;
    private JLabel sNameLab;
    private JLabel phNumLab;
    private JLabel balLab;
    private JComboBox combOut;
    private JComboBox combIn;
    private JTextField costRegTextField;
    private JButton regBut;
    private JList findList;

    public SubscrForm()
    {
        super("Абоненты");
        setTitle("Абоненты");
        setPreferredSize(new Dimension(570, 300));
        setLocation(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setContentPane(rootPane);
        setVisible(true);
        final DefaultListModel listModel = new DefaultListModel();
        findList.setModel(listModel);
        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findList.setSelectedIndex(0);
                findList.setFocusable(false);
                listModel.removeAllElements();
                String str = findTextField.getText();
                if (str.isEmpty())
                    listModel.addElement("Пожалуйста, введите запрос");
                JSONSubscrReaderWriter sr = new JSONSubscrReaderWriter("SubscriberList.txt");
                java.util.List<Subscriber> ss =  sr.readSubscr();
                for (Subscriber subscr : ss) {
                    String subname = subscr.getFullName();
                    String subnumber = subscr.getPhnumber();
                    if ((subname.contains(str) || subnumber.contains(str)) && !str.isEmpty()) {
                        listModel.addElement(subnumber + ' ' + subname + ' ' + subscr.getBalance());

                    }
                }
                if (listModel.isEmpty())
                    listModel.addElement("Совпадений не найдено");

            }
        });
        addBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1 = surNameTextField.getText();
                String str2 = fNameTextField.getText();
                String str3 = sNameTextField.getText();
                String str4 = phNumTextField.getText();
                String str5 = balTextField.getText();
                JSONSubscrReaderWriter sw = new JSONSubscrReaderWriter("SubscriberList.txt");
                sw.writeSubscr(new Subscriber( str1, str2, str3, str4,Double.valueOf(str5)));
            }
        });

        changeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1 = chPhTextField.getText();
                String str2 = chBalTextField.getText();
                JSONSubscrReaderWriter sr = new JSONSubscrReaderWriter("SubscriberList.txt");
                List<Subscriber> ss =  sr.readSubscr();
                for (int i = 0; i < ss.size(); i++) {
                    String subnumber = ss.get(i).getPhnumber();
                    if ( subnumber.equals(str1)) {
                        ss.get(i).replenish(Double.valueOf(str2));
                    }
                }
                sr.rewriteSubscr(ss);
            }
        });
        final DefaultComboBoxModel comboModelOut = new DefaultComboBoxModel();
        final DefaultComboBoxModel comboModelIn = new DefaultComboBoxModel();
        combOut.setModel(comboModelOut);
        combIn.setModel(comboModelIn);
        JSONSubscrReaderWriter rw = new JSONSubscrReaderWriter("SubscriberList.txt");
        List <Subscriber> sc = rw.readSubscr();
        for (int i = 0; i < sc.size(); i++) {
            comboModelOut.addElement(sc.get(i).toString());
            comboModelIn.addElement(sc.get(i).toString());
        }
        regBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONSubscrReaderWriter rw = new JSONSubscrReaderWriter("SubscriberList.txt");
                List <Subscriber> sc = rw.readSubscr();
                Double cost = Double.valueOf(costRegTextField.getText());
                String strOut = (String)combOut.getSelectedItem();
                String strIn = (String)combIn.getSelectedItem();
                if (strOut.equals(strIn))
                    JOptionPane.showMessageDialog(callRegPanel, "Невозможно зарегистрировать звонок абонента самому себе");
                else {
                    CallRegReaderWriter crw = new CallRegReaderWriter("CallReg.txt");
                    String[] chunksOut = strOut.split(" ");
                    String[] chunksIn = strIn.split(" ");
                    String outNumber = chunksOut[0];
                    Subscriber outSub = new Subscriber(chunksOut[1], chunksOut[2], chunksOut[3], chunksOut[0], Double.valueOf(chunksOut[4]));
                    Subscriber inSub = new Subscriber(chunksIn[1], chunksIn[2], chunksIn[3], chunksIn[0], Double.valueOf(chunksIn[4]));
                    crw.writeCallReg(new CallRegister(outSub, inSub, cost));
                    for (int i = 0; i < sc.size(); i++) {
                        if (sc.get(i).getPhnumber().equals(outNumber))
                            sc.get(i).replenish(cost);
                    }
                    rw.rewriteSubscr(sc);
                }
            }
        });
    }
}
