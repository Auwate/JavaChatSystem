package com.austinuwate.LocalNetwork.ChatRooms.ChatRoomPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes.ClientHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatRoomHandler: The ChatRoomHandler class is used to help ServerClientHandler instances
 * talk to each other. They do not have the capability to talk to each other automatically
 * (as only clients in the same chatroom should be able to), thus the ChatRoomHandler has a
 * list of ServerClientHandlers.
 */
public class ChatRoomHandler {

    private final List<ClientHandler> listOfClients;

    public ChatRoomHandler () {

        listOfClients = new ArrayList<>();

    }

    public void addClient (ClientHandler client) {
        listOfClients.add(client);
    }

    public void removeClient (ClientHandler client) {
        listOfClients.remove(client);
    }

    /**
     * broadcastMessage (String): This method broadcasts a message to all clients in a chat
     * room.
     * @param message -> The message written by ta user
     * @param userID -> The serial number of the user.
     * @param username -> The username to send to everyone else
     */
    public void broadcastMessage (String message, String username, String userID) {

        for (ClientHandler clientHandler : listOfClients) {

            if (!clientHandler.getUserID().equals(userID)) {

                clientHandler.toClient(username + ": " + message);

            }

        }

    }

}
