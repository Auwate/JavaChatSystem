package com.austinuwate.LocalNetwork.ChatRooms.ClientPackage.Interfaces;

/**
 * UserInteraction Interface: A method of abstraction where the Client class will not
 * see the inner workings of the ClientInterface class. This is so I can easily test and
 * use different implementations, such as through the console or JavaFX.
 */
public interface UserInteraction {

    void printMessage ( String message );
    String writeMessage();
    void close ();
    String askForUsername ();

}
