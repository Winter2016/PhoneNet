package com.nc.edu.phonenet.read_write;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 3/8/2016.
 */
public class CallRegReaderWriter implements CallRegReadWriteable {
    private String fileName;
    public CallRegReaderWriter (String fName)
    {
        fileName = fName;
    }
    public JSONArray readJSON()
    {
        JSONParser parser = new JSONParser();
        JSONArray crlist = new JSONArray();
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(fileName));
            if (!jsonObj.isEmpty())
                crlist = (JSONArray) jsonObj.get("cr_list");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't read the file");
        }
        return crlist;
    }
    public List<CallRegister> readCallReg()
    {
        List<CallRegister> calllist = new ArrayList<>();
        Subscriber subOut;
        Subscriber subIn;
        double cost;
        JSONArray clist = readJSON();
        List <JSONObject> clistObj = new ArrayList<>();
        for(int i = 0; i< clist.size(); i++) {
            clistObj.add(i, (JSONObject) clist.get(i));
            subOut = (Subscriber)clistObj.get(i).get("c_out");
            subIn = (Subscriber)clistObj.get(i).get("c_in");
            cost = (Double) clistObj.get(i).get("c_in");
            calllist.add(i, new CallRegister(subOut, subIn, cost));
        }
        return calllist;
    }

    public void writeCallReg(CallRegister cr) {
        JSONArray clist = readJSON();
        JSONObject ob = new JSONObject();
        ob.put("c_out", cr.getOutCaller());
        ob.put("c_in", cr.getInCaller());
        ob.put("c_cost", cr.getCost());
        clist.add(ob);
        JSONObject jo = new JSONObject();
        jo.put("c_list", clist);
        try (FileWriter writer = new FileWriter(fileName)){
            writer.write(jo.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't write to the file");
        }
    }

    public void rewriteCallReg(List<CallRegister> crlist) {
        for (CallRegister cr:crlist) {
            writeCallReg(cr);
        }
    }
}
