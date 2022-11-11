import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }

    public Server() {
        try {
            // initialise the server socket, listening to port 8080
            this.serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            try {
                if (this.serverSocket!=null){
                    this.serverSocket.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        this.pool = Executors.newFixedThreadPool(5);
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("Listening to port 8080");
                Socket socket = serverSocket.accept();
                System.out.println("received a request");
                handleRequest(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    private void handleRequest(Socket socket){
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = null;
            request= (Request) ois.readObject();
            String task = request.task;
            String name = request.clientName;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Handling the "+ task +" from "+name );
                    try {
                        Thread.sleep(100+(int) (Math.random()*2000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            ois.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
