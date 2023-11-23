import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Math.*;
import java.util.Arrays;

import static java.lang.Math.*;

public class Server {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

    public Server(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend() {
        ObjectMapper mapper = new ObjectMapper ();
        while (true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String json_str = new String (datagramPacket.getData (), 0, datagramPacket.getLength ());
                Point point = mapper.readValue (json_str, Point.class);
                buffer = calculate(point).getBytes();
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
                buffer = new byte[256];
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
//            finally {
//                if (datagramSocket != null)
//                    datagramSocket.close();
//            }
        }
    }

    static public String calculate(Point point){
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();

        double arg1 = pow(y, x);
        double arg2 = sqrt(abs(x) + pow(2.71828, y));
        double arg3 = (pow(z, 3) * sin(y) * sin(y)) / (y + (pow(z, 3) / (y - pow(z, 3))));

        return String.valueOf(arg1 + arg2 - arg3);
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();
    }

}
