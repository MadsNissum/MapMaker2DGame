import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map {
    private int[][] map;

    private final Tile[] tiles;

    public Map() {
        ImageLoader imageLoader = new ImageLoader();
        this.tiles = imageLoader.getTiles();
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Tile[] getImages() {
        return tiles;
    }

    public int[][] getMap() {
        return map;
    }

    public int getRows() {
        return map.length;
    }

    public int getColums() {
        return map[0].length;
    }

    public double getPixelWidth() {
        return Window.WIDTH / (map.length + 0.);
    }

    public double getPixelHeight() {
        return Window.HEIGHT / (map[0].length + 0.);
    }

    public void draw(GraphicsContext graphicsContext) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                }
                graphicsContext.drawImage(tiles[map[i][j]], i * getPixelWidth(), j * getPixelHeight(), getPixelWidth(), getPixelHeight());
            }
        }

    }
}
