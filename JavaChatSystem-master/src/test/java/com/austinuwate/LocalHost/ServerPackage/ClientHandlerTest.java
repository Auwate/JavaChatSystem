package com.austinuwate.LocalHost.ServerPackage;

import com.austinuwate.LocalNetwork.ChatRooms.ChatRoomPackage.Classes.ChatRoomHandler;
import com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes.Client;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes.ClientHandler;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes.ClientHandlerIO;
import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces.CHandlerIO;
import com.austinuwate.LocalNetwork.ChatRooms.ServerPackage.Classes.Server;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ClientHandlerTest {

    static ClientHandler test;
    static Server serverTest;
    static Client clientTest;
    static ChatRoomHandler chatRoomTest;

    /**
     * resetTest(): Private method that resets the test object each time it is called.
     */
    private void resetTest () {
        if (test != null) {
            test.closeEverything();
        }
        if (serverTest != null) {
            serverTest.closeEverything();
        }
        if (clientTest != null) {
            clientTest.closeEverything();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            serverTest = new Server(serverSocket);
            clientTest = new Client(new Socket("localhost", 1234));
            chatRoomTest = ChatRoomHandler.getListOfRooms().get(0);
            test = new ClientHandler(chatRoomTest, new Socket("localhost", 1234));

        } catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }
    }

    /**
     * setIO(): Sets the IO of the ClientHandler to test-defined values
     * REMEMBER TO RUN resetTest() BEFORE THIS
     */
    private void setIO (BufferedReader br, BufferedWriter bw) {
        CHandlerIO IO;
        ClientHandlerIO testIO = null;
        try {
            testIO = new ClientHandlerIO(new Socket("localhost", 1234));
        } catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }
        testIO.setIOForTests(br, bw);
        IO = testIO;
        test.setIO(IO);
    }

    /**
     * fromClientShouldReturnWrittenMessage (): This test gives the IO a message
     * and expects the same message back.
     * COMPONENT TEST (Interacts with ChatRoomHandler, ClientHandler)
     */
    @Test
    void fromClientShouldReturnWrittenMessage () {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        resetTest();
        setIO(br, bw);

        try {
            bw.write("Test");
            bw.newLine();
            bw.flush();
            test.fromClient();
            assertEquals(br.readLine(), "Test");
        }
        catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }

    }

    /**
     * chatRoomHandlerShouldNotBeNull (): Makes sure the chatroom object is not null
     */
    @Test
    void chatRoomHandlerShouldNotBeNull () {

        resetTest();
        assertNotNull(test.getCurrentChatRoom());

    }

    /**
     * CHandlerPropertiesShouldNotBeNull (): Makes sure the properties object is
     * not null
     */
    @Test
    void CHandlerPropertiesShouldNotBeNull () {

        resetTest();
        assertNotNull(test.getCHandlerProperties());

    }

    /**
     * CHandlerIOShouldNotBeNull (): Makes sure the IO object is not null
     */
    @Test
    void CHandlerIOShouldNotBeNull () {

        resetTest();
        assertNotNull(test.getIO());

    }

    /**
     * SocketShouldNotBeNull (): Makes sure the Socket object is not null
     */
    @Test
    void SocketShouldNotBeNull () {

        resetTest();
        assertNotNull(test.getSocket());

    }

}
