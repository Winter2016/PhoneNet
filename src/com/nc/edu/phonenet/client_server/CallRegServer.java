package com.nc.edu.phonenet.client_server;

import com.nc.edu.phonenet.DAO.CallRegisterDAO;
import com.nc.edu.phonenet.DAO.DAO;
import com.nc.edu.phonenet.DAO.SubscriberDAO;
import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by Ксения on 4/12/2016.
 */
public class CallRegServer implements Runnable {
    private Socket socket;
    private int ID;
    public static final int PORT = 7777;

    public static void main(String[] args) {
        int count = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("MultipleSocketServer Initialized");
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new CallRegServer(socket, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } catch (Exception e) {
        }
    }

    CallRegServer(Socket s, int i) {
        this.socket = s;
        this.ID = i;
    }

    public void run() {
        DAO dao = null;
        CallRegisterDAO callRegisterDAO;
        SubscriberDAO subscriberDAO;
        String results = "Can't register";
        dao = new DAO();
        callRegisterDAO = new CallRegisterDAO();
        subscriberDAO = new SubscriberDAO();
        try {
            dao.connect();
            subscriberDAO.createTableSubscriber();
            callRegisterDAO.createTableCallRegister();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            JSONParser parser = new JSONParser();
            String str = (String) inputStream.readObject();
            if (str != null) {
                JSONObject jsonObj = (JSONObject) parser.parse(str);
                String outPhNumber = jsonObj.get("outPhNumber").toString();
                String inPhNumber = jsonObj.get("inPhNumber").toString();
                Double cost = (Double) jsonObj.get("cost");
                int outID = subscriberDAO.findIDByPhnumber(outPhNumber);
                int inID = subscriberDAO.findIDByPhnumber(inPhNumber);
                Subscriber outSubscr = subscriberDAO.findSubscrByID(outID);
                Subscriber intSubscr = subscriberDAO.findSubscrByID(inID);
                    if (subscriberDAO.findIsFreeByPhnumber(outPhNumber) && subscriberDAO.findIsFreeByPhnumber(inPhNumber)) {
                        while (true) {
                            subscriberDAO.setIsFree(false, outID);
                            subscriberDAO.setIsFree(false, inID);
                        }
                        subscriberDAO.changeBalance(outPhNumber, cost);
                        CallRegister callRegister = new CallRegister(outSubscr, intSubscr, cost);
                        callRegisterDAO.writeTableCallRegister(callRegister);
                        results = "Successfully written";
                        subscriberDAO.setIsFree(true, outID);
                        subscriberDAO.setIsFree(true, inID);
                    } else
                        results = "Subscriber is busy";
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(results);

            }
            else{
                    //не видит
                    //thread.sleep(500);
                }
            }catch(Exception e){
                System.out.println(e);
            }finally{
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }


