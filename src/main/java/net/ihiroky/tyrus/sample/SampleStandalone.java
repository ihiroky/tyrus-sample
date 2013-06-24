package net.ihiroky.tyrus.sample;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;

/**
 * Created on 13/06/24, 11:25.
 *
 * @author Hiroki Itoh
 */
public class SampleStandalone {

    private static final String HOST = "localhost";
    private static final int WEB_SOCKET_PORT = 8025;
    private static final String WEB_SOCKET_CONTEXT = "/websockets";
    private static final int HTTP_SERVER_PORT = 8080;
    private static final String HTTP_SERVER_CONTEXT_ROOT = ".";

    /*
     * access to http://localhost:8080/ with a web browser.
     */
    public static void main(String[] args) {
        Server server = new Server(HOST, WEB_SOCKET_PORT, WEB_SOCKET_CONTEXT, EchoEndpoint.class);
        HttpServer httpServer = HttpServer.createSimpleServer(HTTP_SERVER_CONTEXT_ROOT, HOST, HTTP_SERVER_PORT);
        try {
            server.start();
            httpServer.start();
            System.out.println("Please press a key to stop the server.");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpServer.stop();
            server.stop();
        }
    }
}
