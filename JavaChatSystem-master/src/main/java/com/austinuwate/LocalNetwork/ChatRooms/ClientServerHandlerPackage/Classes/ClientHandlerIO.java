package com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces.CHandlerIO;

import java.io.*;
import java.net.Socket;

/**
 * ServerClientHandlerIO: This class holds the input/output operations for talking with its
 * client.
 */
public class ClientHandlerIO implements CHandlerIO{

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public ClientHandlerIO(Socket socket) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    /**
     * setIOForTests (BufferedReader, BufferedWriter): This method is ONLY used in
     * the testing methods, as it changes the IO to whatever the test requires.
     * @param br -> BufferedReader object
     * @param bw -> BufferedWriter object
     */
    public void setIOForTests (BufferedReader br, BufferedWriter bw) {

        this.bufferedReader = br;
        this.bufferedWriter = bw;

    }

    /**
     * sendMessageToClient (String): This method echos a message sent from
     * other clients in a chatroom.
     * @param message -> Message that will be sent to the client
     */
    @Override
    public void sendMessageToClient ( String message ) {

        try {

            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }

        catch (IOException exception) {
            exception.printStackTrace();
            close();
        }

    }

    /**
     * getMessageFromClient(): This method sends the message from the client
     * to the chatroom.
     */
    @Override
    public String getMessageFromClient () {

        String message = null;

        try {

            message = bufferedReader.readLine();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

        return message;

    }

    /**
     * close(): This tries to close the BufferedReader and BufferedWriter objects
     */
    @Override
    public void close () {

        try {

            if ( bufferedReader != null ) {
                bufferedReader.close();
            }

            if ( bufferedWriter != null ) {
                bufferedWriter.close();
            }

        }

        catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
