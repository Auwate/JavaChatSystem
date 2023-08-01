package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Threads;

import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes.Client;

public class ReceiveMessageThread implements Runnable {

    private final Client client;
    private boolean requestedToClose = false;

    public ReceiveMessageThread (Client client) {
        this.client = client;
    }

    public synchronized boolean isRequestedToClose () {
        return this.requestedToClose;
    }

    public synchronized void requestToClose () {
        this.requestedToClose = true;
    }

    /**
     * receiveMessages (): This is a threaded method that will run for as long as the user is
     * using the program.
     */
    public void receiveMessages () {

        while ( !this.requestedToClose ) {

            try {

                String message = client.getClientIO().listenForMessage();

                if ( message == null ) {

                    throw new NullPointerException();

                }

                client.getClientInterface().printMessage( message );

            }

            catch (NullPointerException exception) {

                exception.printStackTrace();
                client.closeEverything();

            }

        }

    }

    @Override
    public void run() {

        receiveMessages();

    }

}
