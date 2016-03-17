package com.nc.edu.phonenet;

import com.nc.edu.phonenet.handlers.CallRegHandler;
import com.nc.edu.phonenet.handlers.SubscrHandler;
import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;
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
    private JComboBox<Subscriber> combOut;
    private JComboBox<Subscriber> combIn;
    private JTextField costRegTextField;
    private JButton regBut;
    private JList findList;

    public SubscrForm()
    {
        super("Абоненты");
        setTitle("Абоненты");
        setPreferredSize(new Dimension(640, 280));
        setLocation(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPane);
        pack();
        setVisible(true);
        final DefaultListModel listModel = new DefaultListModel();
        final SubscrHandler sh = new SubscrHandler("SubscriberList.txt");
        final CallRegHandler crh = new CallRegHandler("CallReg.txt");

        findList.setModel(listModel);
        findBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findList.setSelectedIndex(0);
                findList.setFocusable(false);
                listModel.removeAllElements();
                String str = findTextField.getText();
                sh.findSubscr(str,listModel);

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
                if(str3.isEmpty())
                    str3 = " ";
                if (str1.isEmpty()||str2.isEmpty()||str4.isEmpty()||str5.isEmpty())
                    JOptionPane.showMessageDialog(addPanel, "Заполните пустые поля");
                else
                sh.getJsrw().writeSubscr(new Subscriber( str1, str2, str3, str4,Double.valueOf(str5)));
            }
        });

        changeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phNumber = chPhTextField.getText();
                Double cost = Double.valueOf(chBalTextField.getText());
                sh.changeBalance(phNumber, cost);
            }
        });

        List <Subscriber> sc = sh.getSslist();
        Subscriber[] subscribers = sc.toArray(new Subscriber[sc.size()]);
        final DefaultComboBoxModel<Subscriber> comboModelOut = new DefaultComboBoxModel<>(subscribers);
        final DefaultComboBoxModel<Subscriber> comboModelIn = new DefaultComboBoxModel<>(subscribers);
        combOut.setModel(comboModelOut);
        combIn.setModel(comboModelIn);

        regBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List <Subscriber> sc = sh.getSslist();
                Double cost = Double.valueOf(costRegTextField.getText());
                Subscriber strOut = (Subscriber) combOut.getSelectedItem();
                Subscriber strIn = (Subscriber) combIn.getSelectedItem();
                if (strOut.equalsSubscr(strIn))
                    JOptionPane.showMessageDialog(callRegPanel, "Невозможно зарегистрировать звонок абонента самому себе");
                else {
                    sh.changeBalance(strOut, cost);
                    crh.getCrrw().writeCallReg(new CallRegister(strOut, strIn, cost));
                }
            }
        });
    }
}
