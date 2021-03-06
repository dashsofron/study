package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.sql.SQLOutput;


public class EventListener {
    private DrawClass drawer;
    Canvas canvas;
    TextField threds;
    Label text;
    EventListener(DrawClass drawer, Canvas canvas, TextField threds, Label text) {
        this.drawer = drawer;
        this.canvas = canvas;
        this.threds = threds;
        this.text=text;
    }

    public void mousePressedHandler(MouseEvent event) {
        text.setText("Resizing...");
        MyMouse.saveOld();
        MyMouse.setSX((int) event.getSceneX());
        MyMouse.setSY((int) event.getSceneY());
        event.consume();
    }

    public void mouseReleasedHandler(MouseEvent event) {
        MyMouse.setEX((int) event.getSceneX());
        MyMouse.setEY((int) event.getSceneY());
        if ((MyMouse.getRightX() - MyMouse.getLeftX() > 5) && (MyMouse.getBot() - MyMouse.getTop() > 5))
            drawer.reactOnResize(MyMouse.getLeftX(), MyMouse.getRightX(), MyMouse.getTop(), MyMouse.getBot(), canvas);
        event.consume();
    }

    public void keyHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            drawer.reactOnResize(MyMouse.getLeftXl(), MyMouse.getRightXl(), MyMouse.getTopl(), MyMouse.getBotl(), canvas);
            event.consume();
            return;
        }
        if (event.getCode() == KeyCode.ENTER) {
            if( !threds.getText().equals(""))
            drawer.setThreadsNum(Integer.parseInt(threds.getText()));
            else text.setText("You need to write some digit");
            event.consume();
        }
    }

    public void mouseScrollHandler(ScrollEvent event) {
        text.setText("Resizing...");
        event.consume();
        double deltaY = event.getDeltaY();
        if (deltaY < 0)
            MyMouse.zoomFactor = 2.0 - MyMouse.zoomFactor;
        deltaY = Math.abs(deltaY) * MyMouse.zoomFactor;
        drawer.reactOnResize((float) deltaY, drawer.getWidth() - (float) deltaY, drawer.getHeight() - (float) deltaY, (float) deltaY, canvas);
        event.consume();

    }
}
