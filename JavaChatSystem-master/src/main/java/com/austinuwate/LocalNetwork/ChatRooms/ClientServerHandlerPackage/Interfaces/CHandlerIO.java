package com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces;

/**
 * Used by the ServerClientHandlerProperties class, it abstracts away some of the details of the
 * IO class from the main Handler class. This allows for future editing of code, such as
 * refactoring.
 */
public interface CHandlerIO {

    public void sendMessageToClient ( String message );
    public String getMessageFromClient ( );
    public void close ( );

}
