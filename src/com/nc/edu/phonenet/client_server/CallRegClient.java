package com.nc.edu.phonenet.client_server;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Ксения on 4/12/2016.
 */
public class CallRegClient {
    public static final String HOST = "localhost";
    public static final int PORT = 7777;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = null;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        socket = new Socket(HOST, PORT);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        String outPhNumber = "9065976843";
        String inPhNumber = "9089403587";
        Double cost = 20.0;
        JSONObject obj = new JSONObject();
        obj.put("outPhNumber", outPhNumber);
        obj.put("inPhNumber", inPhNumber);
        obj.put("cost", cost);
        String outPutJSONString = obj.toJSONString();
        objectOutputStream.writeObject(outPutJSONString);
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object results = objectInputStream.readObject();
        System.out.println(results.toString());
        objectInputStream.close();
        objectOutputStream.close();
        outputStream.close();
        socket.close();
    }
}

