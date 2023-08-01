package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Threads;

import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes.Client;

public class SendMessageThread implements Runnable {

    private final Client client;
    private boolean requestedToClose = false;

    public SendMessageThread (Client client) {

        this.client = client;

    }

    public synchronized boolean isRequestedToClose () {
        return this.requestedToClose;
    }

    public synchronized void requestToClose () {
        this.requestedToClose = true;
    }

    /**
     * sendMessages (): This is the method that the user will stay on the most.
     */
    public void sendMessages () {

        while ( !isRequestedToClose() ) {

            client.getClientIO().sendMessage(client.getClientInterface().writeMessage());

        }

    }

    @Override
    public void run() {

        sendMessages();

    }

}
