package main.java.it.polimi.ingsw.Network.Messages;

/**
 * A simple ping message
 */
public class Ping extends Message{
    /**
     * Constructs the ping message.
     */
    public Ping() {
        super("Server", MessageType.Ping);
    }
}
