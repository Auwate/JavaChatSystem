package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Interfaces.UserInteraction;

import java.io.IOException;
import java.net.Socket;

/**
 * Client: The Client class is designed as a hub for various user related activities.
 */
public class Client {

    private final Socket socket;
    private ClientIO clientIO;
    private UserInteraction clientInterface;

    public Client (Socket socket) {

        this.socket = socket;

        try {
            this.clientIO = new ClientIO(socket.getInputStream(), socket.getOutputStream());
            this.clientInterface = new ClientInterface();
        }
        catch (IOException exception) {
            exception.printStackTrace();
            closeEverything(this.socket, this.clientIO, clientInterface);
        }

    }

    /**
     * sendMessages (): This is the method that the user will stay on the most.
     */
    public void sendMessages () {

        while ( socket.isConnected() ) {

            clientIO.sendMessage(clientInterface.writeMessage());

        }

    }

    /**
     * receiveMessages (): This is a threaded method that will run for as long as the user is
     * using the program.
     */
    public void receiveMessages () {

        new Thread (() -> {

            while ( socket.isConnected() ) {

                try {

                    String message = clientIO.listenForMessage();

                    if ( message == null ) {

                        throw new NullPointerException();

                    }

                    clientInterface.printMessage( message );

                }

                catch (NullPointerException exception) {

                    exception.printStackTrace();
                    closeEverything( socket, clientIO, clientInterface );

                }

            }

        }).start();

    }

    /**
     * closeEverything (): This method attempts to closeEverything available to the Client.
     */
    public void closeEverything (Socket socket, ClientIO clientIO, UserInteraction clientInterface) {

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
            client.receiveMessages();
            client.sendMessages();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

}
