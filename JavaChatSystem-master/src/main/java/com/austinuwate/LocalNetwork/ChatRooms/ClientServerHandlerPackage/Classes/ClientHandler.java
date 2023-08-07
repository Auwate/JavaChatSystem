package com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ChatRoomPackage.Classes.ChatRoomHandler;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces.CHandlerIO;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces.CHandlerProperties;

import java.net.Socket;

/**
 * ServerClientHandler: The ServerClientHandler class is used to communicate between the
 * Client and Server. We want some abstraction between the Client and Server, thus having a
 * middleman to parse messages, understand commands, and a variety of other commands would
 * be helpful.
 */
public class ClientHandler implements Runnable {

    private final ChatRoomHandler currentChatRoom;
    private final CHandlerProperties clientProperties;
    private CHandlerIO IO;
    private final Socket socket;

    public ClientHandler(ChatRoomHandler room, Socket socket) {

        currentChatRoom = room;
        this.socket = socket;
        IO = new ClientHandlerIO(this.socket);
        clientProperties = new ClientHandlerProperties("N/A");

    }

    /*
     * setCurrentChatRoom(): Sets the clientHandler's chat room.
     * @param name -> Chat room our client would like to join.
     */
    /*public void setCurrentChatRoom (String name) {

        for (ChatRoomHandler room : currentChatRoom.getListOfRooms()) {

            if (room.getChatRoomName().equals(name)) {
                this.currentChatRoom = room;
            }

        }

    }*/

    /**
     * getClientProperties (): Returns this object's CHandlerProperties
     * @return clientProperties
     */
    public CHandlerProperties getCHandlerProperties () {
        return this.clientProperties;
    }

    /**
     * getSocket(): Returns this object's Socket object
     * @return socket
     */
    public Socket getSocket () {
        return this.socket;
    }

    /**
     * getCurrentChatRoom (): Returns the clientHandler's chat room.
     * @return -> Chat room the user is in currently.
     */
    public ChatRoomHandler getCurrentChatRoom () {
        return this.currentChatRoom;
    }


    /**
     * getUserID (): Gets the userID of its properties object
     * @return -> UserID
     */
    public String getUserID () {
        return this.clientProperties.getUserID();
    }

    /**
     * getIO (): Returns the IO object held by this object
     * @return -> ClientHandlerIO
     */
    public CHandlerIO getIO () {
        return this.IO;
    }

    /**
     * setIO (CHandlerIO): Sets our current IO to the new implementation.
     * CURRENTLY ONLY USED IN TESTING
     * @param IO -> New IO
     */
    public void setIO (CHandlerIO IO) {
        this.IO = IO;
    }

    /*
     * setUsername (String): Because of the way the program is structured, the
     * user should be able to change their username whenever they want
     * @param username -> Username to change to
     */
    /*public void setUsername (String username ) {
        this.clientProperties.setUserName(username);
    }*/


    /**
     * fromClient (): This method sends messages FROM the Client
     */
    public void fromClient () {

        String message;

        try {
            message = IO.getMessageFromClient();
            if (message == null) {
                throw new NullPointerException();
            }
            else if (message.startsWith("~/")) {
                clientProperties.setUserName(message.substring(2));
                currentChatRoom.addClient(this);
            }
            else {
                currentChatRoom.broadcastMessage( message, this.clientProperties.getUserName() ,this.clientProperties.getUserID() );
            }
        }

        catch (NullPointerException exception) {
            exception.printStackTrace();
            closeEverything();
        }

    }

    public void closeEverything () {

        if (this.currentChatRoom != null) {
            this.currentChatRoom.removeClient(this);
        }
        IO.close();

    }

    /**
     * toClient (String): This method sends messages TO the Client
     * @param message -> Message to be sent
     */
    public void toClient ( String message ) {

        IO.sendMessageToClient( message );

    }

    @Override
    public void run () {

        while ( socket.isConnected() ) {

            fromClient();

        }

    }

}
