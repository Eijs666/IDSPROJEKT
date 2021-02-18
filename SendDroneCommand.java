package sample;

//AN INTERFACE THE OTHER CLASSES CAN CALL UPON
// vi har lavet en funktion der gør det muligt at få sendt kommandoer til dronen. denne funktion kan bruges i alle klasser
//interface gør det muligt at vi kan definere en funktionaliteten og ikke at implementere den.
public interface SendDroneCommand {
    public void SendCommand(String command);
}
