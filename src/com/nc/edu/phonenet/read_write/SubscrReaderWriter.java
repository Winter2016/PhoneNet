package com.nc.edu.phonenet.read_write;

import com.nc.edu.phonenet.model.Subscriber;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 2/13/2016.
 */
public class SubscrReaderWriter implements Readable,ReWriteable, Writeable{
    private String fileName;
    public SubscrReaderWriter (String fName)
    {
        fileName = fName;
    }
    public List<Subscriber> readSubscr()
    {
        List<Subscriber> ss = new ArrayList<Subscriber>();
        String line;
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while ((line = reader.readLine()) != null) {
                String chunks[] = line.split(" ");
                ss.add(new Subscriber(chunks[1].toString() + ' ' + chunks[2].toString() +' ' + chunks[3].toString(), chunks[0].toString(), Double.valueOf(chunks[4])));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ss;
    }

    public void writeSubscr(String line) {
        try(FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.append(line);
            writer.append(System.getProperty("line.separator"));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rewriteSubscr(List<Subscriber> sslist)  {
        try(FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (int i = 0; i < sslist.size(); i++) {
                writer.write(sslist.get(i).getPhnumber() + ' ' + sslist.get(i).getName() + ' ' + Double.valueOf(sslist.get(i).getBalance()).toString());
                writer.write(System.getProperty("line.separator"));
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
