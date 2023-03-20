import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Window extends Application {

    public static final int WIDTH = 650;
    public static final int HEIGHT = 650;
    public boolean isPen;
    private boolean editingObjects = false;
    private final TextField txfMapSize = new TextField();
    private final TextField txfMapName = new TextField();
    private final Button btnCreateCanvas = new Button("Create canvas");
    private final Button btnCreateMap = new Button("Create map");
    private final CheckBox chbBoxTool = new CheckBox();
    private final CheckBox chbEditObjects = new CheckBox();
    private final ListView<Tile> listView = new ListView<>();
    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Map map = new Map();
    private int[][] objects;
    private final Image[] images = map.getImages();
    private final ObjectLoader objectLoader = new ObjectLoader();
    private final Image[] objectImages = objectLoader.getTiles();
    private int firstX;
    private int firstY;
    private int lastX;
    private int lastY;

    public void start(Stage stage) {
        stage.setTitle("2D Map Maker");

        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);

        //scene.addEventFilter(MouseEvent.ANY, event -> System.out.println(event.getEventType()));

        stage.setScene(scene);

        stage.show();
    }

    private void initContent(GridPane pane) {
        //DEBUGGING TODO Remove later
        pane.setGridLinesVisible(false);

        pane.setPadding(new Insets(20));
        pane.setVgap(10);
        pane.setHgap(10);

        System.out.println(Arrays.toString(objectLoader.getTiles()));

        isPen = true;

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

        //canvas.addEventFilter(MouseEvent.ANY, action -> System.out.println(action.getEventType()));


        canvas.setOnMousePressed(action -> {
            if (!isPen) {
                firstX = (int) Math.floor(action.getX() / map.getPixelWidth());
                firstY = (int) Math.floor(action.getY() / map.getPixelHeight());
            }

        });
        canvas.setOnMouseReleased(action -> {
            if (!isPen) {
                lastX = (int) Math.floor(action.getX() / map.getPixelWidth());
                lastY = (int) Math.floor(action.getY() / map.getPixelHeight());
                fillSquare(firstX, firstY, lastX, lastY);
            }
        });


        pane.add(new Label("Map Size:"), 1, 0);
        pane.add(txfMapSize, 2, 0);

        pane.add(btnCreateCanvas, 2, 1);
        btnCreateCanvas.setOnAction(actionEvent -> this.btnAction());

        pane.add(new Label("Square tool:"), 1, 2);
        pane.add(chbBoxTool, 2, 2);
        chbBoxTool.setOnAction(actionEvent -> isPen = !chbBoxTool.isSelected());

        pane.add(new Label("Edit objects:"), 1, 3);
        pane.add(chbEditObjects, 2, 3);
        chbEditObjects.setOnAction(actionEvent -> editObjectAction());


        pane.add(listView, 2, 4);

        setListViewIcons();

        pane.add(new Label("Map name:"), 1, 5);

        pane.add(txfMapName, 2, 5);

        pane.add(btnCreateMap, 2, 6);

        btnCreateMap.setOnAction(actionEvent -> this.createAction());

    }

    private void editObjectAction() {
        editingObjects = chbEditObjects.isSelected();
        setListViewIcons();
    }

    private void createAction() {
        if (!txfMapName.getText().isEmpty()) {
            FileCreater.createFile(map, txfMapName.getText());
            FileCreater.createObjectFile(objects, txfMapName.getText(), objectLoader.getTiles());
        }
    }

    private void fillSquare(int firstX, int firstY, int lastX, int lastY) {
        int firstXIndex = Math.min(firstX, lastX);
        int lastXIndex = Math.max(firstX, lastX);
        int firstYIndex = Math.min(firstY, lastY);
        int lastYIndex = Math.max(firstY, lastY);

        for (int i = firstXIndex; i <= lastXIndex; i++) {
            for (int j = firstYIndex; j <= lastYIndex; j++) {
                if (i < map.getRows() && j < map.getColums() && i >= 0 && j >= 0) {
                    map.getMap()[i][j] = listView.getSelectionModel().getSelectedItem().getIndex();
                }
            }
        }
        draw();
    }

    private void mouseClickAction(MouseEvent action) {
        if (isPen) {
            int x = (int) Math.floor(action.getX() / map.getPixelWidth());
            int y = (int) Math.floor(action.getY() / map.getPixelHeight());
            if (x >= 0 && x <= map.getRows() - 1 && y >= 0 && y <= map.getColums() - 1) {
                if (chbEditObjects.isSelected()) {
                    objects[x][y] = listView.getSelectionModel().getSelectedItem().getIndex();
                } else {
                    map.getMap()[x][y] = listView.getSelectionModel().getSelectedItem().getIndex();
                }
                draw();
            }
        }
    }

    private void btnAction() {
        map.setMap(new int[Integer.parseInt(txfMapSize.getText())][Integer.parseInt(txfMapSize.getText())]);
        objects = new int[Integer.parseInt(txfMapSize.getText())][Integer.parseInt(txfMapSize.getText())];
        System.out.println(objects[0][0]);
        draw();
    }

    private void draw() {
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
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

        double pixelWidth = WIDTH / (objects.length + 0.);
        double pixelHeight = HEIGHT / (objects[0].length + 0.);

        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < objects[0].length; j++) {
                graphicsContext.drawImage(objectLoader.getTiles()[objects[i][j]], i * pixelWidth, j * pixelHeight, pixelWidth, pixelHeight);
            }
        }

    }

    private void setListViewIcons() {
        if (editingObjects) {
            if (chbBoxTool.isSelected()) {
                chbBoxTool.fire();
            }
            chbBoxTool.setDisable(true);

            ArrayList<Tile> list = new ArrayList<>(Arrays.asList(objectLoader.getTiles()));

            listView.setItems(FXCollections.observableList(list));

            listView.setCellFactory(param -> new ListCell<>() {
                private final ImageView imageView = new ImageView();
                @Override
                public void updateItem(Tile tile, boolean empty) {
                    super.updateItem(tile, empty);
                    if (empty || !editingObjects) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (tile != null) {
                            imageView.setImage(objectImages[tile.getIndex()]);
                            setText(tile.getName());
                            setGraphic(imageView);
                        }
                    }
                }
            });

        } else {
            chbBoxTool.setDisable(false);
            ArrayList<Tile> list = new ArrayList<>(Arrays.asList(map.getImages()));

            listView.setItems(FXCollections.observableList(list));

            listView.setCellFactory(param -> new ListCell<>() {
                private final ImageView imageView = new ImageView();
                @Override
                public void updateItem(Tile tile, boolean empty) {
                    super.updateItem(tile, empty);
                    if (empty || editingObjects) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (tile != null) {
                            imageView.setImage(images[tile.getIndex()]);
                            setText(tile.getName());
                            setGraphic(imageView);
                        }
                    }
                }
            });
        }
    }
}
