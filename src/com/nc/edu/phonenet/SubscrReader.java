package com.nc.edu.phonenet;

import com.nc.edu.phonenet.model.Subscriber;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 2/4/2016.
 */
public class SubscrReader {
        private String fileName;
        public SubscrReader (String fName)
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
}
