import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Window extends Application {
    public void start(Stage stage) {
        stage.setTitle("2D Map Maker");

        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);

        //scene.addEventFilter(MouseEvent.ANY, event -> System.out.println(event.getEventType()));

        stage.setScene(scene);

        stage.show();
    }

    public static final int WIDTH = 650;
    public static final int HEIGHT = 650;
    public int pen;
    private final TextField txfRow = new TextField();
    private final TextField txfCol = new TextField();
    private final Button btnCreateCanvas = new Button("Create canvas");
    private final Button btnBrick = new Button("Brick");
    private final Button btnDirt = new Button("Dirt");
    private final Button btnGrass = new Button("Grass");
    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Image image = new Image(String.valueOf(getClass().getResource("tiles/brick.png")));
    private Map map = new Map();

    private void initContent(GridPane pane) {
        //DEBUGGING TODO Remove later
        pane.setGridLinesVisible(false);

        pane.setPadding(new Insets(20));
        pane.setVgap(10);
        pane.setHgap(10);

        //OUTLINE ON CANVAS
        Rectangle rectangle = new Rectangle(WIDTH + 2, HEIGHT + 2);
        pane.add(rectangle, 0, 0, 1, 10);


        GridPane.setHalignment(canvas, HPos.CENTER);
        GridPane.setValignment(canvas, VPos.CENTER);
        pane.add(canvas, 0, 0, 1, 10);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        canvas.setOnMouseDragged(this::mouseClickAction);
        canvas.setOnMouseClicked(this::mouseClickAction);


        pane.add(new Label("Rows:"), 1, 0);
        pane.add(txfRow, 2, 0);

        pane.add(new Label("Columns:"), 1, 1);
        pane.add(txfCol, 2, 1);

        pane.add(btnCreateCanvas, 2, 2);
        btnCreateCanvas.setOnAction(actionEvent -> this.btnAction());

        pane.add(btnBrick, 2,3);
        pane.add(btnDirt, 2, 4);
        pane.add(btnGrass, 2, 5);

        btnBrick.setOnAction(event -> pen = 1);
        btnDirt.setOnAction(event -> pen = 2);
        btnGrass.setOnAction(event -> pen = 3);

    }

    private void mouseClickAction(MouseEvent action) {
        int x = (int) Math.floor(action.getX() / map.getPixelWidth());
        int y = (int) Math.floor(action.getY() / map.getPixelHeight());
        map.getMap()[x][y] = pen;
        System.out.println("Map: " + map.getMap()[x][y] + " X: " + x + " Y: " + y);
        draw();
    }

    private void btnAction() {
        System.out.println(txfCol.getText());
        map.setMap(new int[Integer.parseInt(txfRow.getText())][Integer.parseInt(txfCol.getText())]);
        draw();
    }

    private void draw() {
        graphicsContext.clearRect(0,0,WIDTH,HEIGHT);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        if (map.getMap() != null) {
            graphicsContext.setFill(Color.BLACK);

            double x = map.getPixelWidth();
            for (int i = 0; i < map.getRows(); i++) {
                graphicsContext.fillRect(x, 0, 1, HEIGHT);
                x += map.getPixelWidth();
            }


            double y = map.getPixelHeight();
            for (int i = 0; i < map.getColums(); i++) {
                graphicsContext.fillRect(0, y, WIDTH, 1);
                y += map.getPixelHeight();
            }
        }

        map.draw(graphicsContext);
    }
}
