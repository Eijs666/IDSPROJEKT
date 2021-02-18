package sample;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

public class UdpPackageReceiver implements Runnable {

    boolean running = false;
    DatagramSocket socket; //datagramsocket er en kombination af ip adresse, port og protokol. 
    private byte[] buf = new byte[256]; // størrelsen af internetpakken
    int port;
    SendDroneCommand commander; //sender kommandoer 
    ObservableList<UdpPackage> udpPackages; //det er ligesom en arraylist men her kan vi se når der sker nogle ændringer. en liste af datatyper

    public UdpPackageReceiver(List udpPackages, int port, SendDroneCommand commander) {
        this.running = true;
        this.udpPackages = (ObservableList) udpPackages;
        this.commander = commander;
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void shutDown(){
        running = false;
    }

    @Override
    public void run() {
        while (running)
        {
            buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                //System.out.println("package arrived!");
                UdpPackage udpPackage = new UdpPackage("name", packet.getData(), packet.getAddress(), socket.getLocalAddress(), packet.getPort(), socket.getLocalPort());
                udpPackages.add(udpPackage);
                System.out.println(udpPackage.getASCII());

                //RECEIVING UDPPACKETS
                String msg = new String(packet.getData()).trim();
                commander.SendCommand(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
