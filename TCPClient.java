package tcpclient;

import java.io.*;
import java.net.*;

/**
 * 19/01/2016
 *
 * @author Alban && Lucas TCP Client TP 1 UQAC
 * BUT : Le programme écrit dans un fichier HTML ( sur le bureau) 
 * le contenu du flux HTML du site www.uqac.ca/etudiant et se connecter en TCP sur le port 80
 */
public class TCPClient {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    final static int port = 80;
    private static Socket socket = null;
    private static OutputStream oStream = null;
    private static InputStream iStream = null;
    private static String affichage = "";
    private static String sauvegarde = "";
    private static FileWriter writer = null;
    private static int bitsRecus = 0;
    private static byte[] b;

    // Permet d'afficher le contenu HTML sur site 
    private static void affichage() {

        if (bitsRecus > 0) {
            affichage = new String(b, 0, bitsRecus);
            sauvegarde += affichage;
            System.out.println(affichage);
        }
    }
   
    public static void main(String args[]) throws Exception {

        final String chemin = "C:/Users/Utilisateur/Desktop/test1.html";

        try {
            socket = new Socket("www.uqac.ca", port);

            String getHttp = "GET / HTTP/1.1\n" + "Host: www.uqac.ca/etudiants\n\n";
            oStream = socket.getOutputStream();

            oStream.write(getHttp.getBytes());

            iStream = socket.getInputStream();

            b = new byte[1000];

            // Creation du fichier
            File fichier = new File(chemin);
            writer = new FileWriter(fichier);
            
            // si le fichier existe
            if (fichier.exists()) {
                BufferedWriter out = new BufferedWriter(new FileWriter(fichier, false)); // on écrase le ocntenu du fichier
                while ((bitsRecus = iStream.read(b)) >= 0) {
                    affichage();
                }
                out.write(sauvegarde);
            // si le fichier n'existe pas 
            } else {
                BufferedWriter out = new BufferedWriter(new FileWriter(fichier, true)); // on ajoute du contenu au fichier
                fichier.createNewFile();
                while ((bitsRecus = iStream.read(b)) >= 0) {
                    affichage();
                }
                out.write(sauvegarde);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //fermeture des flux et des sockets
            oStream.close();
            iStream.close();
            socket.close();
            writer.close();
        }
    }
}
