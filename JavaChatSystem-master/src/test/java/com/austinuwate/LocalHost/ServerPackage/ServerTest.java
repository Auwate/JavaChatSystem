package com.austinuwate.LocalHost.ServerPackage;

import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes.Client;
import com.austinuwate.LocalNetwork.ChatRooms.ServerPackage.Classes.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    /**
     * This test returns the value of isRequestedToClose () after requesting
     * to close
     */
    @Test
    void requestCloseShouldReturnTrue () {

        ServerSocket testServerSocket = null;

        try {
            testServerSocket = new ServerSocket(1234);
        }

        catch (IOException exception) {

            exception.printStackTrace();
            fail();

        }

        Server testServer = new Server(testServerSocket);
        testServer.requestClose();
        assertTrue(testServer.isRequestedToClose());

        testServer.closeEverything();

    }

    /**
     * This test sets the isRequestedToClose field to true and tests that it closes
     * immediately.
     */
    @Test
    void serverShouldImmediatelyClose () {

        ServerSocket testServerSocket = null;

        try {
            testServerSocket = new ServerSocket(1234);
        }

        catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }

        Server testServer = new Server(testServerSocket);
        testServer.requestClose();
        assertTrue (testServer.startServer());

        testServer.closeEverything();

    }

    /**
     * This test verifies that the Server accepts the Client and terminates after
     * being called.
     */
    @Test
    void serverShouldAcceptClientAndTerminate () {

        ServerSocket testServerSocket = null;
        Socket testSocket = null;

        try {
            testServerSocket = new ServerSocket(1234);
        }

        catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }

        Server testServer = new Server(testServerSocket);
        Thread test = new Thread (testServer);
        test.start();
        testServer.requestClose();

        try {
            testSocket = new Socket ("localhost", 1234);
        }

        catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }

        new Client(testSocket);

        assertFalse(test.isAlive());

        testServer.closeEverything();

    }

}
