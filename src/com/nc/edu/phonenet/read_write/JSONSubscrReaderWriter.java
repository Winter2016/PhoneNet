
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

public class JSONSubscrReaderWriter implements Readable, Writeable {
    private String fileName;
    public JSONSubscrReaderWriter (String fName)
    {
        fileName = fName;
    }
    public List<Subscriber> readSubscr()
    {
        List<Subscriber> sublist = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String name;
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray slist = (JSONArray) jsonObj.get("s_list");
            List <JSONObject> slistObj = new ArrayList<>();
            for(int i = 0; i< slist.size(); i++) {
                slistObj.add(i, (JSONObject) slist.get(i));
                name = slistObj.get(i).get("s_surname").toString() + ' ' + slistObj.get(0).get("s_fname").toString() + ' ' + slistObj.get(0).get("s_sname").toString();
                sublist.add (i, new Subscriber(name, slistObj.get(0).get("s_number").toString(), (Double) slistObj.get(0).get("s_balance")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sublist;
    }

    public void writeSubscr(String line) {
        JSONParser parser = new JSONParser();
        String chunks[] = line.split(" ");
        try (FileReader reader = new FileReader(fileName)) {
            Object object = parser.parse(reader);
            JSONObject obj = (JSONObject) object;
            JSONArray ar = (JSONArray) obj.get("s_list");
            JSONObject ob = new JSONObject();
            ob.put("s_number", chunks[0]);
            ob.put("s_surname", chunks[1]);
            ob.put("s_fname", chunks[2]);
            ob.put("s_sname", chunks[3]);
            ob.put("s_balance", chunks[4]);
            ar.add(ob);
            try (FileWriter writer = new FileWriter(fileName)){
                writer.append(ob.toJSONString());
                writer.close();
            } catch (IOException ex) {
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

