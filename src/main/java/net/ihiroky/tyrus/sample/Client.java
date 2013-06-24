package net.ihiroky.tyrus.sample;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created on 13/06/24, 11:16.
 *
 * @author Hiroki Itoh
 */
public class Client {

    private static final String SENT_MESSAGE = "Hello World.";

    public static void main(String[] args) {
        try {
            final CountDownLatch messageLatch_ = new CountDownLatch(1);
            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
            ClientManager client = ClientManager.createClient();
            Session session = client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {
                            @Override
                            public void onMessage(String message) {
                                System.out.println("Received message: " + message);
                                messageLatch_.countDown();
                            }
                        });
                        session.getBasicRemote().sendText(SENT_MESSAGE);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }, cec, new URI("ws://localhost:8025/websockets/echo"));
            messageLatch_.await(3, TimeUnit.SECONDS);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
