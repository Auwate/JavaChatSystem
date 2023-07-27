package com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Classes;

import com.austinuwate.LocalNetwork.ChatRooms.ClientServerHandlerPackage.Interfaces.CHandlerProperties;

public class ClientHandlerProperties implements CHandlerProperties {

    private static long serial = 0;
    private final String userID;
    private String username;

    public ClientHandlerProperties (String username) {

        this.username = username;
        this.userID = String.valueOf(++serial);

    }

    @Override
    public String getUserID() {
        return this.userID;
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public void setUserName (String username) {
        this.username = username;
    }

}
