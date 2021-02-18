package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

//vi erklærer variablerne for vores udpPackage
public class UdpPackage {
    private Date date;
    private String name;
    private byte[] data;
    private InetAddress fromIp;
    private InetAddress toIp;
    private int fromPort;
    private int toPort;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    //vi laver en constructor så vi definere alle de værdier udpPackage skal indeholde
    public UdpPackage(String name, String data, InetAddress fromIp, InetAddress toIp, int formPort, int toPort) {
        this(name, data.getBytes(), fromIp, toIp,  formPort,  toPort);
    }
    //denne contsructor indikere når en ip adresse ikke kan blive fundet
    public UdpPackage(String name, byte[] data, String fromIp, String toIp, int formPort, int toPort) throws UnknownHostException {
        this(name, data, InetAddress.getByName(fromIp), InetAddress.getByName(toIp),  formPort,  toPort);
    }

    public UdpPackage(String name, byte[] data, InetAddress fromIp, InetAddress toIp, int formPort, int toPort) {
        this.name = name;
        this.data = data;
        this.fromIp = fromIp;
        this.toIp = toIp;
        this.fromPort = formPort;
        this.toPort = toPort;
        this.setDate(new Date(System.currentTimeMillis()));
    }
    //vi henter info fra vores controller og sætter dem ind i vores udpPackage.
    public InetAddress getToAdd(){
        return toIp;
    }

    public String getHEX(){
        return getDataAsHex();
    }

    public String getDataAsHex()
    {
        StringBuilder hex = new StringBuilder();
        for (byte b : data) {
            hex.append(String.format("%02X", (int)b & 0x0FFFFF));
            hex.append(":");
        }
        return hex.toString();
    }

    public String getFormattedDate()
    {
        return formatter.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDataAsBytes() {
        return data;
    }

    public String getDataAsString() {
        return new String(data);
    }


    public void setData(String data) {
        this.data = data.getBytes();
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public InetAddress getFromIp() {
        return fromIp;
    }

    public void setFromIp(InetAddress fromIp) {
        this.fromIp = fromIp;
    }

    public InetAddress getToIp() {
        return toIp;
    }

    public void setToIp(InetAddress toIp) {
        this.toIp = toIp;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public int getToPort() {
        return toPort;
    }

    public void setToPort(int toPort) {
        this.toPort = toPort;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //CALLING ASCII/NAME OF COMMAND AND RETURNING AS A STRING
    public String getASCII(){
        return getDataAsString();

    }
}
