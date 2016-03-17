package com.nc.edu.phonenet.handlers;

import com.nc.edu.phonenet.model.Subscriber;
import com.nc.edu.phonenet.read_write.JSONSubscrReaderWriter;

import javax.swing.*;
import java.util.List;

/**
 * Created by Ксения on 3/14/2016.
 */
public class SubscrHandler {
    private JSONSubscrReaderWriter jsrw;
    private List <Subscriber> sslist;
    public SubscrHandler(String filename) {
        jsrw = new JSONSubscrReaderWriter(filename);
    }
    public void findSubscr (String str, DefaultListModel listModel)
    {
        if (str.isEmpty())
            listModel.addElement("Пожалуйста, введите запрос");
        else {
            sslist =  jsrw.readSubscr();
            for (Subscriber subscr : sslist) {
                String subname = subscr.getFullName();
                String subnumber = subscr.getPhnumber();
                if ((subname.contains(str) || subnumber.contains(str)) && !str.isEmpty()) {
                    listModel.addElement(subnumber + ' ' + subname + ' ' + subscr.getBalance());
                }
            }
            if (listModel.isEmpty())
                listModel.addElement("Совпадений не найдено");
        }
    }

    public void changeBalance (Subscriber ss, Double cost)
    {
        sslist =  jsrw.readSubscr();
        for (int i = 0; i < sslist.size(); i++) {
            sslist =  jsrw.readSubscr();
            if (sslist.get(i).equalsSubscr(ss))
                sslist.get(i).replenish(cost);
        }
        jsrw.rewriteSubscr(sslist);
    }

    public void changeBalance(String phNumber, Double cost)
    {
        sslist =  jsrw.readSubscr();
        for (int i = 0; i < sslist.size(); i++) {
            String subnumber = sslist.get(i).getPhnumber();
            if ( subnumber.equals(phNumber)) {
                sslist.get(i).replenish(cost);
            }
        }
        jsrw.rewriteSubscr(sslist);
    }

    public List<Subscriber> getSslist() {
        sslist =  jsrw.readSubscr();
        return sslist;
    }

    public JSONSubscrReaderWriter getJsrw() {
        return jsrw;
    }
}
