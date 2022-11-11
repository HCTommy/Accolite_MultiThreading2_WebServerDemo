import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        // keep sending request to server
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println("Sent");
                Socket socket = new Socket("localhost", 8080);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(new Request("client"+(int) (Math.random()*10),"request"+i));
                oos.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
