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

    static volatile List<ChatRoomHandler> listOfRooms = new ArrayList<>();

    private volatile List<ClientHandler> listOfClients;
    private String chatRoomName;

    public ChatRoomHandler (String chatRoomName) {

        listOfClients = new ArrayList<>();
        listOfRooms.add(this);
        this.chatRoomName = chatRoomName;

    }

    //public synchronized void addChatRoom () {this.getListOfRooms().add(this);}

    public static synchronized List<ChatRoomHandler> getListOfRooms () {return listOfRooms;}

    //public synchronized void setChatRoomName ( String chatRoomName ) {this.chatRoomName = chatRoomName;}

    //public synchronized String getChatRoomName () {return this.chatRoomName;}

    public synchronized void addClient (ClientHandler client) {
        listOfClients.add(client);
    }

    public synchronized void removeClient (ClientHandler client) {
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
