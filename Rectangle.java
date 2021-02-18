package sample.Figure;
//herunder henter vi vores canvas bibliotek
import javafx.scene.canvas.GraphicsContext;
//vi nedarver fra vores superclass som er figure og den bruger vi til at kunne tegne vores rectangle i vores canvas
public class Rectangle extends Figure {
    @Override
    public String toString() {
        return "Square";
    }
// getcopy funktionen er den vi nedarver fra vores superclass.
    @Override
    public Figure getCopy() {
        return new Rectangle();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.strokeRect(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
    }
}
