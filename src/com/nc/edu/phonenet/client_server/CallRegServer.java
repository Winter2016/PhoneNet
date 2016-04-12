package com.nc.edu.phonenet.client_server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ксения on 4/12/2016.
 */
public class CallRegServer implements Runnable {
    private Socket socket;
    private int ID;
    public static final int PORT = 7777;
    public static void main(String[] args) {
        int count = 0;
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("MultipleSocketServer Initialized");
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new CallRegServer(socket, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }
        catch (Exception e) {}
    }
    CallRegServer(Socket s, int i) {
        this.socket = s;
        this.ID = i;
    }
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            JSONParser parser = new JSONParser();
            String str = (String) inputStream.readObject();
            JSONObject jsonObj = (JSONObject) parser.parse(str);
            String outPhNumber = jsonObj.get("outPhNumber").toString();
            String inPhNumber = jsonObj.get("inPhNumber").toString();
            String results = outPhNumber + " calls " + inPhNumber;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(results);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e){}
        }
    }
}

