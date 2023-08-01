package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Interfaces.ClientUserInteraction;
import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Threads.ReceiveMessageThread;
import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Threads.SendMessageThread;

import java.io.IOException;
import java.net.Socket;

/**
 * Client: The Client class is designed as a hub for various user related activities.
 */
public class Client {

    private final Socket socket;
    private ClientIO clientIO;
    private ClientUserInteraction clientInterface;

    public Client (Socket socket) {

        this.socket = socket;

        try {
            this.clientIO = new ClientIO(socket.getInputStream(), socket.getOutputStream());
            this.clientInterface = new ClientInterface();
        }
        catch (IOException exception) {
            exception.printStackTrace();
            closeEverything();
        }

    }

    /**
     * Returns the clientIO, which is used in the receiveMessage/sendMessage threads
     * @return -> clientIO object
     */
    public synchronized ClientIO getClientIO () {
        return clientIO;
    }

    /**
     * Returns the clientInterface, which is used in the sendMessage threads
     * @return UserInterface object
     */
    public synchronized ClientUserInteraction getClientInterface () {
        return clientInterface;
    }

    /**
     * startClient(): Similar to the startServer() method in the Server class, this will
     * start the important operations and processes required to run the client.
     * Index 0 = Thread that receives messages
     * Index 1 = Thread that sends messages
     */
    public boolean startClient () {

        Thread[] messageThreads = new Thread[2];
        preOperations();

        Thread listenForMessages = new Thread(new ReceiveMessageThread(this));
        listenForMessages.start();
        Thread sendMessages = new Thread (new SendMessageThread(this));
        sendMessages.start();

        messageThreads[0] = listenForMessages;
        messageThreads[1] = sendMessages;

        return true;

    }

    /**
     * preOperations(): This method gets the username for the client.
     */
    public void preOperations () {

        clientIO.sendMessage(clientInterface.preOperations());

    }

    /**
     * closeEverything (): This method attempts to closeEverything available to the Client.
     */
    public void closeEverything () {

        try {

            if (socket != null) {
                socket.close();
            }

            if (clientIO != null) {
                clientIO.close();
            }

            if (clientInterface != null) {
                clientInterface.close();
            }

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

    /**
     * Main: Simply attempts to create a Socket on the Server's port and create a Client object.
     * @param args -> Ignored for now
     */
    public static void main (String[] args) {

        try {

            Client client = new Client (new Socket("localhost", 1234));
            client.startClient();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

}
