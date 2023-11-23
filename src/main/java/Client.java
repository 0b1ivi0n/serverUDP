import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;
    private static Scanner scanner = new Scanner(System.in);
    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive() {

        ObjectMapper mapper = new ObjectMapper ();
        String switcher = "Yes";

        while (!switcher.equals("No")) {
            try {

                Point point = initialisePoint();
                String json_str = mapper.writeValueAsString(point);

                buffer = json_str.getBytes ();


                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234 );

                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("The server:" + messageFromServer);

                System.out.println("Do you want to continue?(Yes/No)");
                scanner.nextLine();
                switcher = scanner.nextLine();

            } catch( IOException e){
                e.printStackTrace();
                break;
            }
        }

        datagramSocket.close();
    }

    static public Point initialisePoint(){
        System.out.println("Enter x");
        float x = scanner.nextFloat();
        System.out.println("Enter y");
        float y = scanner.nextFloat();
        System.out.println("Enter z");
        float z = scanner.nextFloat();
        return new Point(x, y, z);
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
         DatagramSocket datagramSocket =  new DatagramSocket();
         InetAddress inetAddress = InetAddress.getByName("localhost");
         Client client = new Client(datagramSocket, inetAddress);
         System.out.println("send datagram packets to a server");
         client.sendThenReceive();

    }



}
