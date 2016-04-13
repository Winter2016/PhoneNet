package com.nc.edu.phonenet;

import com.nc.edu.phonenet.client_server.CallRegClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Ксения on 4/13/2016.
 */
public class CallRegForm extends JFrame{
    private JTextField outPhNumTextField;
    private JButton regButton;
    private JTextField costTextField;
    private JTextField inPhNumTextField;
    private JPanel rootPane;

    public CallRegForm() throws SQLException, ClassNotFoundException {
    super("Зарегестрировать звонок");
    setTitle("Зарегестрировать звонок");
    setPreferredSize(new Dimension(440, 180));
    setLocation(500, 200);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(rootPane);
    pack();
    setVisible(true);
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outPhNum = outPhNumTextField.getText();
                String inPhNum = inPhNumTextField.getText();
                Double cost = Double.valueOf(costTextField.getText());
                CallRegClient callRegClient = new CallRegClient(outPhNum, inPhNum, cost);
                try {
                    callRegClient.runMain();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println(e1);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                    System.out.println(e1);
                }
            }
        });
    }
}
