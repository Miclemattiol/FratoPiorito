import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CellSquare extends StackPane implements PropertyChangeListener {
    Cell alias;
    Rectangle background;
    Text content;

    public CellSquare(Cell alias){
        background = new Rectangle(40, 40, Color.GRAY);
        background.setStroke(Color.DARKGRAY);
        this.getChildren().add(background);
        this.alias = alias;
        this.alias.registerPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.getChildren().remove(content);
        switch(evt.getPropertyName()){
            case Cell.CELLOPENED:{
                this.background.setFill(Color.WHITE);
                if(this.alias instanceof NumberCell){
                    content = new Text(Integer.toString(((NumberCell) this.alias).getValue()));
                    content.setFont(new Font(20));
                    this.getChildren().add(content);
                }else if(this.alias instanceof BombCell){
                    content = new Text("B");
                    content.setFont(new Font(20));
                    this.getChildren().add(content);
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "You lose");
                    a.setTitle("Coglione");
                    a.showAndWait();
                    Platform.exit();
                }
            }break;

            case Cell.CELLMARKED:{
                if((boolean) evt.getNewValue()){
                    content = new Text("M");
                    content.setFont(new Font(20));
                    this.getChildren().add(content);
                }

            }break;
        }
    }
}
