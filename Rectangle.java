package sample.Figure;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {
    @Override
    public String toString() {
        return "Square";
    }

    @Override
    public Figure getCopy() {
        return new Rectangle();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.strokeRect(start.getX(), start.getY(), end.getX()-start.getX(), end.getY()-start.getY());
    }
}