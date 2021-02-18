package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import sample.Figure.Figure;

public class Controller {

    public TableView tableViewLog;
    public TableColumn logColumnFromIp;
    public TableColumn logColumnToIp;
    public TableColumn logColumnToPort;
    public TableColumn logColumnAscii;
    public TableColumn logColumnHex;
    public TableColumn logColumnTime;
    public TableColumn logColumnFromPort;

    //DRONE VARIABLER
    private GraphicsContext graphicsContext;
    @FXML
    private Canvas canvas;
    Figure activeFigure;

    private ObservableList<UdpPackage> loggedPackages = FXCollections.observableArrayList();

    private UdpPackageReceiver receiver;

    //PLACERING PÅ CANVAS
    private int droneX = 450;
    private int droneY = 200;

    //STØRRELSEN PÅ DRONEN
    private int moveX = droneX + 50;
    private int moveY = droneY + 50;

    private int distance = 5;

    private ObservableList<UdpPackage> savedPackages = FXCollections.observableArrayList();
    private DatagramSocket sender;

    //Vi indlæser komponenter
    public void initialize() throws UnknownHostException {
        //Vi printer nogle udppakker, for at bekrædte at vores komponenter er indlæst
        System.out.println("creates list of packages");
        UdpPackage test1 = new UdpPackage("name", "data", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        UdpPackage test2 = new UdpPackage("name", "hello world", InetAddress.getByName("127.0.0.1"), InetAddress.getByName("127.0.0.1"), 4000,4000);
        loggedPackages.addAll(test1, test2);
        savedPackages.addAll(test1, test2);

        //DRONE
        graphicsContext = canvas.getGraphicsContext2D();

        //Overblik over modtaget kommandoer, som bliver vist i en tabel
        tableViewLog.setItems(loggedPackages);

        //Visuelle elementer som fortæller om info vedrørende kommandoen som er tilsendt.
        logColumnTime.setCellValueFactory(
                new PropertyValueFactory<UdpPackage,String>("formattedDate")
        );
        logColumnAscii.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsString")
        );
        logColumnHex.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("dataAsHex")
        );
        logColumnFromPort.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, Integer>("fromPort")
        );
        logColumnFromIp.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("fromIp")
        );
        logColumnToPort.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, Integer>("toPort")
        );
        logColumnToIp.setCellValueFactory(
                new PropertyValueFactory<UdpPackage, String>("toIp")
        );
        
        //Alle udpakker som modtages skal indsættes i loggedpackages.
        receiver = new UdpPackageReceiver(loggedPackages, 4000, this::sendDroneMessage);
        //Vi starter en ny tråd som kan modtage og samtidig behandle udppakker, i vores tilfælde modtager vi pakker og behandler sådan så dronen kan bevæge sig.
        new Thread(receiver).start();
        
        //Try = vi tester kode der står indenfor tuborgklapper - støder vi ind i fejl, så kør koden der i catch.
        try {
            sender = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void drawFigure(){
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
            //Danner en kopi af en firkan, som er vores aktive figur(dronen) til emulatoren
            activeFigure = new sample.Figure.Rectangle();
        
            //Vi angiver en startposition til vores figur
            activeFigure.start = new Point((int) droneX, (int) droneY);
            activeFigure.end = new Point((int) moveX, (int) moveY);
        
            //Vi indsætter vores dannede figur ind på vores canvas(lærred)
            drawActiveFigure(activeFigure);
    }


    private void drawActiveFigure(Figure activeFigure) {
        graphicsContext.setStroke(Color.BLACK);
        activeFigure.draw(graphicsContext);
    }


    //LOGIC FOR DRONE MOVEMENT
    public void move(int xAxis, int yAxis) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        activeFigure = new sample.Figure.Rectangle();
        droneX += xAxis;
        droneY += yAxis;
        moveX += xAxis;
        moveY += yAxis;
        activeFigure.start = new Point((int) droneX, (int) droneY);
        activeFigure.end = new Point((int) moveX, (int) moveY);
        drawActiveFigure(activeFigure);

    }

    //SWITCH STATEMENT FOR THE DIFFERENT INPUT
    public void sendDroneMessage(String command){

        switch (command){

            case "UP":
                System.out.println("Drone is going up.");
                //GO UP
                move(0, distance);
                break;

            case "DOWN":
                System.out.println("Drone is going down.");
                //GO DOWN
                move(0, -distance);
                break;

            case "LEFT":
                System.out.println("Drone is going left.");
                //GO LEFT
                move(-distance, 0);
                break;

            case "RIGHT":
                System.out.println("Drone is going up.");
                //GO RIGHT
                move(distance, 0);
                break;

            case "BUTTON":
                System.out.println("Drone is created.");
                //DRAW DRONE
                System.out.println("creating the drone");
                drawFigure();
                break;

            default:
                //UNKOWN INPUT
                System.out.println("Error 404");
                break;

        }

    }

    public void sendUdpMessage(ActionEvent actionEvent) {
        System.out.println("Drone is going down.");
        //GO DOWN
        move(0, -distance);

        // sends a basic test message to localhost port 4000!
        String message = "test message";
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("127.0.0.1"), 4000);
            sender.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
