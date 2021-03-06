package com.server.chatServer.Sockets;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

@Component
public class ServerSocket {
    //FIELDS
    private java.net.ServerSocket serverSocket;
    private static ArrayList<ClientSocket> clients;
    private int portNumber;
    private boolean active = false;
    private ApplicationContext context;
    // ARRAY OF CLIENTS SOCKETS CONNECTED TO THE SERVER
    public static ArrayList<ClientSocket> getClients() {
        return clients;
    }

    //METHODS
    public ServerSocket(ApplicationContext context) throws IOException {
        this.portNumber = 6666;
        serverSocket = new java.net.ServerSocket(this.portNumber);
        this.active = true;
        this.clients = new ArrayList<>();
        this.context = context;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /* METHOD USED TO START THE SERVER*/
    public void start() throws IOException {
        while (true){
            Socket clientSocket = serverSocket.accept();
            ClientSocket client = (ClientSocket) this.context.getBean("clientSocket");
            client.setClientConnection(clientSocket);
            Thread th = new Thread(client);
            th.start();
            this.clients.add(client);
        }
    }
}
