package sample.Figure;

import javafx.scene.canvas.GraphicsContext;
//Benytter point systemet
import java.awt.*;
//herunder giver vi vores figur et start og slut punkt så vi kan illustrere den i vores canvas
public abstract class Figure {
    public Point start;
    public Point end;

    public abstract void draw(GraphicsContext graphicsContext);

    public abstract Figure getCopy();



}
