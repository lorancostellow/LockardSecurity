package PiComAPI.Core;

import PiComAPI.Payload.Payload;

import java.net.Socket;

/**
 * Created by dylan on 19/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class PiNodeInst implements PiNode {
    private Boolean isDelegator;
    private String role;
    private String name;
    private Socket socket;

    public PiNodeInst(Boolean isDelegator, String role, String name, Server.ClientConnection connection) {
        this.isDelegator = isDelegator;
        this.role = role;
        this.name = name;
    }

    @Override
    public Boolean getDelegator() {
        return isDelegator;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Payload sendPayload(Payload payload) {
        return null;
    }

    @Override
    public String toString() {
        return "PiNode{" +
                "isDelegator=" + isDelegator +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
