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
    public static String outPhNumber = "";
    public static String inPhNumber = "";
    public static Double cost = 0.0;

    public CallRegClient(String outPhNumber, String inPhNumber, Double cost) {
        this.outPhNumber = outPhNumber;
        this.inPhNumber = inPhNumber;
        this.cost = cost;
    }

    //public static void main(String[] args) throws IOException, ClassNotFoundException {
    public static void runMain() throws IOException, ClassNotFoundException {
        Socket socket = null;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        socket = new Socket(HOST, PORT);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
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

