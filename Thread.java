import java.io.*;
import java.net.Socket;

public class Thread implements Runnable {
    File file;
    String host;
    int port;

    public Thread(File file , String host , int port) {
        this.file = file;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            int n;
            byte[] bytes = new byte[1000000];
            dataOutputStream.writeUTF("File");
            dataOutputStream.flush();
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.flush();
            FileInputStream fileInputStream = new FileInputStream(file);
            while ((n = fileInputStream.read(bytes)) != -1) {
                dataOutputStream.write(bytes, 0, n);
                dataOutputStream.flush();
            }
            fileInputStream.close();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
