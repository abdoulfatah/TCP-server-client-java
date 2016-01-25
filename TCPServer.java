package tcpserver;

import java.io.*;
import java.net.*;
import sun.security.util.Length;

/**
 *
 * @author Utilisateur
 */
public class TCPServer {

    /**
     * @param args the command line arguments
     */
    public static final int port = 6789;

    public static void main(String argv[]) throws IOException, InterruptedException {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(port);
        String data;
        BufferedWriter out = null;
        Socket connectionSocket = null;
        int i = 0;
        while (true) {
            data = "<h1>Exercice 2</h1>";
            System.out.println("Le Serveur attend une connexion");
            try {
                connectionSocket = welcomeSocket.accept();
                System.out.print("Le Serveur est connecté à un client!\n");
                out = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
                data += "Local address " + welcomeSocket.getInetAddress() + "\r\nPort " + welcomeSocket.getLocalPort();
                out.write("HTTP/1.1 200 OK\r\n Content-Type: text/html \r\n Content-Length:" + data.length() + "\r\n", 1, 0);
                out.write(data + "\r\n");
                out.write("\r\n");
                System.out.print("Fin de la communication\n");
            } catch (IOException t) {
                System.err.println(t);
            } finally {
                out.flush();
                out.close();
                connectionSocket.close();
            }
        }
    }
}
