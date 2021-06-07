import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class Field extends Application {

    public static final int STDHEIGHT = 10;
    public static final int STDWIDTH = 10;
    public static final int STDPROB = 20;
    private Cell[][] field;

    public Field(){
        this(STDWIDTH, STDHEIGHT, STDPROB);
    }

    public Field(int width, int height, int prob){
        this.field = new Cell[height][width];
        for(int y = 0; y< field.length; y++){
            for(int x=0; x< field[0].length; x++){
                int b = new Random().nextInt(100);
                System.out.println(b);
                if(b < prob){
                    field[y][x] = new BombCell();
                }else{
                    field[y][x] = null;
                }
            }
        }

        for(int y=0; y< field.length; y++){
            for(int x=0; x<field[0].length; x++){
                if(field[y][x] == null){
                    int bombs = 0;
                    for(int i=y-1; i<=y+1; i++){
                        for(int j=x-1; j<=x+1; j++){
                            if(i<field.length && j<field[0].length && i>=0 && j>=0){
                                if(field[i][j] instanceof BombCell) {
                                    bombs++;
                                }
                            }
                        }
                    }
                    field[y][x] = bombs > 0 ? new NumberCell(bombs) : new EmptyCell();
                }
            }
        }
    }

    public boolean openCell(int x, int y){
        System.out.println("Opening cell at x:" + x + " y:" + y);
        boolean ret = this.field[y][x].open();
        if(ret && this.field[y][x] instanceof EmptyCell) {
            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (i < field.length && j < field[0].length && i >= 0 && j >= 0) {
                        openCell(j, i);
                    }
                }
            }
        }
        int nCells = 0;
        for(int cY=0; cY<this.field.length && nCells ==0; cY++){
            for(int cX =0; cX<this.field[0].length && nCells == 0; cX++){
                if(!(field[cY][cX] instanceof BombCell) && !field[cY][cX].isOpened()){
                    nCells++;
                }
            }
        }

        if(nCells == 0){
            Alert a = new Alert(Alert.AlertType.INFORMATION, "You won");
            a.setTitle("We la");
            a.showAndWait();
            Platform.exit();
        }

        return ret;
    }

    private void showBombs(){
        for(int cY=0; cY<this.field.length; cY++){
            for(int cX =0; cX<this.field[0].length; cX++){

            }
        }
    }

    public void markCell(int x, int y){
        System.out.println("Marking/Unmarking cell");
        if(!field[y][x].isOpened()){
            field[y][x].mark();
        }
    }

    public void printField(){
        for(Cell[] cellArray : field){
            for(Cell cell: cellArray){
                System.out.print(cell.cellString());
            }
            System.out.println();
        }
    }

    public Pane draw(){
        GridPane pane = new GridPane();
        for(int y=0; y< field.length; y++){
            for(int x=0; x<field[0].length; x++){
                Pane tmp = new CellSquare(field[y][x]);
                int finalY = y;
                int finalX = x;
                tmp.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                    if(e.getButton() == MouseButton.PRIMARY){
                        openCell(finalX, finalY);
                    }else if(e.getButton() == MouseButton.SECONDARY){
                        markCell(finalX, finalY);
                    }
                });
                pane.add(tmp, x, y);

            }
        }
        return pane;
    }

    public static void main(String[] args) {
        /*Field game = new Field(10, 5, 20);
        game.printField();
        game.openCell(1,3);
        game.printField();*/
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Field game = new Field(30, 15, 15);
        Scene scene = new Scene(game.draw());
        stage.setScene(scene);
        stage.show();

    }
}
