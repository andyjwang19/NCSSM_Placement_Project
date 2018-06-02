import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AppMain extends Application {

    final Slider redSlider = new Slider(0, 255, 255);
    final Slider greenSlider = new Slider(0, 255, 255);
    final Slider blueSlider = new Slider(0, 255, 255);

    final Label redCaption = new Label("Red Level:");
    final Label greenCaption = new Label("Green Level:");
    final Label blueCaption = new Label("Blue Level:");

    final Label redValue = new Label(Double.toString(redSlider.getValue()));
    final Label greenValue = new Label(Double.toString(greenSlider.getValue()));
    final Label blueValue= new Label(Double.toString(blueSlider.getValue()));

    final TextField hexValue = new TextField("#000000");

    final Label savedValues = new Label("");

    GridPane grid = new GridPane();



    ArrayList<String> swatches = new ArrayList<>();
    public void saveValues(){
        DecimalFormat df = new DecimalFormat("0.00");
        swatches.add("RGB(" + df.format(redSlider.getValue()) + ", " + df.format(greenSlider.getValue()) + ", " + df.format(blueSlider.getValue()) + ")\n");
        String temp = "";
        for(String a: swatches){
            temp += a;
        }
        savedValues.setText(temp);
    }

    public void updateBackground(Label a, Number new_val){
        a.setText(String.format("%.2f", new_val));
        grid.setStyle("-fx-background-color: rgb("+ redSlider.getValue() +", " + greenSlider.getValue() + ", " + blueSlider.getValue() + ")");
    }



    @Override
    public void start(Stage stage) {
        Group root = new Group();
        stage.setTitle("Color App");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(100);


        redSlider.onMouseReleasedProperty().set(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){ saveValues(); }
        });

        greenSlider.onMouseReleasedProperty().set(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){ saveValues(); }
        });

        blueSlider.onMouseReleasedProperty().set(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){ saveValues(); }
        });


        redSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                updateBackground(redValue, new_val);
            }
        });

        greenSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                updateBackground(greenValue, new_val);
            }
        });

        blueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                updateBackground(blueValue, new_val);
            }
        });


        hexValue.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isInt = false;
            try {
                if(newValue.length() > 0) {
                    if (newValue.charAt(0) == '#') {
                        newValue = newValue.substring(1, newValue.length());
                        Integer.parseInt(newValue, 16);
                        isInt = true;
                    }
                }
            }
            catch (NumberFormatException ex) {}

            if(newValue.length() == 6 && isInt){
                grid.setStyle("-fx-background-color: #" + newValue);
                redSlider.setValue(Integer.parseInt(newValue.substring(0, 2), 16));
                greenSlider.setValue(Integer.parseInt(newValue.substring(2, 4), 16));
                blueSlider.setValue(Integer.parseInt(newValue.substring(4, 6), 16));
                saveValues();
            }
        });



        GridPane.setConstraints(hexValue, 0, 3);
        grid.getChildren().add(hexValue);

        GridPane.setConstraints(redCaption, 0, 0);
        grid.getChildren().add(redCaption);
        GridPane.setConstraints(redSlider, 1, 0);
        grid.getChildren().add(redSlider);
        GridPane.setConstraints(redValue, 2, 0);
        grid.getChildren().add(redValue);

        GridPane.setConstraints(greenCaption, 0, 1);
        grid.getChildren().add(greenCaption);
        GridPane.setConstraints(greenSlider, 1, 1);
        grid.getChildren().add(greenSlider);
        GridPane.setConstraints(greenValue, 2, 1);
        grid.getChildren().add(greenValue);

        GridPane.setConstraints(blueCaption, 0, 2);
        grid.getChildren().add(blueCaption);
        GridPane.setConstraints(blueSlider, 1, 2);
        grid.getChildren().add(blueSlider);
        GridPane.setConstraints(blueValue, 2, 2);
        grid.getChildren().add(blueValue);

        Label t = new Label("Saved Swatches"); t.setUnderline(true);
        GridPane.setConstraints(t, 3,0);
        grid.getChildren().add(t);
        GridPane.setConstraints(savedValues, 3,1, 1,10, HPos.CENTER, VPos.TOP);
        grid.getChildren().add(savedValues);


        final Scene scene = new Scene(root, 1000, 400);

        scene.setRoot(grid);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        System.out.println(Integer.toHexString(10));

        launch(args);
    }
}