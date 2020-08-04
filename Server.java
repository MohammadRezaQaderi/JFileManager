import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {


    public static void main(String[] args) throws IOException {
        ArrayList<String> LastPaths;
        ArrayList<File> firstSocket;
        ArrayList<File> secondSocket;
        ArrayList<String> firstSocket1;
        ArrayList<String> secondSocket1;
        ArrayList<String> currentPath = new ArrayList<>();
        ServerSocket Server = new ServerSocket(8888);
        while (true) {
            Socket socket = Server.accept();
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String request = dataInputStream.readUTF();

            if (request.equals("Start")) {
                firstSocket1 = new ArrayList<>();
                System.out.println("Start request");
                int size = dataInputStream.readInt();
                System.out.println("First File Size  " + size);
                for (int i = 0; i < size; i++) {
                    firstSocket1.add(dataInputStream.readUTF());
                }
                for (int i = 0; i < size; i++) {

                }
            }
        }

    }
}
