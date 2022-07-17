import socket.MyServer;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        File directory = new File("./");
//        System.out.println(directory.getCanonicalPath());

        MyServer server = new MyServer(4000);
        server.connect();
    }
}
