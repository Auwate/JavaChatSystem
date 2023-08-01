package com.austinuwate.LocalNetwork.ChatRooms.ServerPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ChatRoomPackage.Classes.ChatRoomHandler;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server: The Server class is designed to fulfill requests from various processes and establish
 * incoming connections.
 */
public class Server {

    private final ServerSocket serverSocket;

    public Server (ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    /**
     * startServer (): Most important method, as it is where the Server will be most of the time.
     * Specifically, it connects clients to the ServerSocket and redirects them.
     */
    public void startServer () {

        try {

            ChatRoomHandler room = new ChatRoomHandler("DefaultChatRoom");

            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(room, socket);
                Thread thread = new Thread (clientHandler);
                thread.start();

            }

        }

        catch ( IOException exception ) {

            closeEverything(serverSocket);

        }

    }

    /**
     * closeEverything (): If exceptions occur where the Server is operating, try to close everything.
     * @param serverSocket -> Server's socket hub
     */
    public void closeEverything (ServerSocket serverSocket) {

        try {

            /*
            Avoid errors
             */
            if (!(serverSocket == null)) {

                serverSocket.close();

            }

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

    /**
     * Main: Simply attempts to create a ServerSocket and start the server
     * @param args -> Arguments that are currently ignored
     */
    public static void main (String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

}
