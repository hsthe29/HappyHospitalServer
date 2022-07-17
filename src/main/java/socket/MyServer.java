package socket;

import algorithm.AStar;
import algorithm.AStarDirectedPath;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private AStar aStar;
    private AStarDirectedPath aStarDirectedPath;

    public MyServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void connect() throws IOException {
        System.out.println("Server connecting ...");
        try {
            socket = serverSocket.accept();
            System.out.println("Connected!");
        } catch (IOException e) {
            System.out.println("Time out!");
            disconnect();
        }
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message msg;

        while (true) {
            try {
                msg = (Message) ois.readObject();
                if (msg.mode == 1) {
                    aStar = new AStar(52, 28, msg.getGroundPos());
                } else if (msg.mode == 2) {
                    aStarDirectedPath = new AStarDirectedPath(52, 28, msg.getAdjacencyList(), msg.getPathPos());
                } else if(msg.mode == 3) {
                    oos.writeObject(new Path(aStar.cal(msg.getStartPos(), msg.getEndPos())));
                } else if(msg.mode == 4) {

                    oos.writeObject(new Path(aStarDirectedPath.calPathAStar(msg.getStartPos(), msg.getEndPos())));
                } else {
                    if(msg.mode != 0)
                        System.out.println("Error! ");
                    System.out.println("Server has stopped!");
                    disconnect();
                    break;
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Class not found!");
            }
        }
    }

    public void disconnect() throws IOException {
        socket.close();
        serverSocket.close();
    }
}
