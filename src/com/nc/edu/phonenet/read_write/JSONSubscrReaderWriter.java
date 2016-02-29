
package com.nc.edu.phonenet.read_write;

import com.nc.edu.phonenet.model.Subscriber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ксения on 2/14/2016.
 */

public class JSONSubscrReaderWriter implements SubscrReadWriteable {
    private String fileName;
    public JSONSubscrReaderWriter (String fName)
    {
        fileName = fName;
    }
    public JSONArray readJSON()
    {
        JSONParser parser = new JSONParser();
        JSONArray slist = new JSONArray();
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(fileName));
            if (!jsonObj.isEmpty())
                slist = (JSONArray) jsonObj.get("s_list");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't read the file");
        }
        return slist;
    }
    public List<Subscriber> readSubscr()
    {
        List<Subscriber> sublist = new ArrayList<>();
        String surname;
        String firstname;
        String secondname;
        JSONArray slist = readJSON();
        List <JSONObject> slistObj = new ArrayList<>();
            for(int i = 0; i< slist.size(); i++) {
                slistObj.add(i, (JSONObject) slist.get(i));
                surname = slistObj.get(i).get("s_surname").toString();
                firstname = slistObj.get(i).get("s_fname").toString();
                secondname = slistObj.get(i).get("s_sname").toString();
                sublist.add(i, new Subscriber(surname, firstname, secondname, slistObj.get(i).get("s_number").toString(), (Double) slistObj.get(i).get("s_balance")));
            }
        return sublist;
    }

    public void writeSubscr(Subscriber ss) {
        JSONArray slist = readJSON();
        JSONObject ob = new JSONObject();
        ob.put("s_number", ss.getPhnumber());
        ob.put("s_surname", ss.getSurName());
        ob.put("s_fname", ss.getFirstName());
        ob.put("s_sname", ss.getSecondName());
        ob.put("s_balance", ss.getBalance());
        slist.add(ob);
        JSONObject jo = new JSONObject();
        jo.put("s_list", slist);
        try (FileWriter writer = new FileWriter(fileName)){
            writer.write(jo.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't write to the file");
        }
    }

    public void rewriteSubscr(List<Subscriber> sslist) {
        for (Subscriber ss:sslist) {
            writeSubscr(ss);
        }
    }
}

