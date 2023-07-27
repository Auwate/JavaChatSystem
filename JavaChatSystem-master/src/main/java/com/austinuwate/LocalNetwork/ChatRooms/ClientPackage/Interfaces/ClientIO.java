package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Interfaces;

/**
 * IO Interface: An method of abstraction where the Client class does not see the
 * inner workings of the ClientIO class.
 */
public interface ClientIO {

    public void sendMessage ( String message );
    public String listenForMessage ();
    public void close ();

}
