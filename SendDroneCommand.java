package sample;

//AN INTERFACE THE OTHER CLASSES CAN CALL UPON
// vi har lavet en funktion der gør det muligt at få sendt kommandoer til dronen. denne funktion kan bruges i alle klasser
public interface SendDroneCommand {
    public void SendCommand(String command);
}
