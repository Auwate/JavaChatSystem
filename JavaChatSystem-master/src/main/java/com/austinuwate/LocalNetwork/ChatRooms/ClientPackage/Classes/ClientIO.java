package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Classes;

import java.io.*;

/**
 * ClientIO: The ClientIO class is only interested in backend operations, being tasked
 * with various input/output commands.
 * It contains two fields, bufferedWriter and bufferedReader, which are of type
 * BufferedWriter and BufferedReader respectively.
 * It implements the IO interface, abstracting the methods away from the main Client
 * class.
 */
public class ClientIO implements com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Interfaces.ClientIO {

    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ClientIO(InputStream input, OutputStream output) {

        this.bufferedWriter = new BufferedWriter( new OutputStreamWriter (output) );
        this.bufferedReader = new BufferedReader( new InputStreamReader (input) );

    }

    /**
     * close (): Tries to close all fields in the ClientIO class
     */
    @Override
    public void close () {

        try {

            bufferedWriter.close();
            bufferedReader.close();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

    /**
     * sendMessage ( String message ): Sends the message requested by the user
     *
     * @param message -> Message that the user wants to send
     */
    @Override
    public void sendMessage ( String message ) {

        try {

            this.bufferedWriter.write(message);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();

        }

        catch (IOException exception) {

            exception.printStackTrace();

        }

    }

    /**
     * listenForMessage (): Returns the message sent by other users by using the BufferedReader
     * object.
     * @return -> Returns the message sent by other users
     */
    @Override
    public String listenForMessage () {

        try {
            return bufferedReader.readLine();
        }

        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

    }

}
