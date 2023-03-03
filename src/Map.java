import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Map {
    private int[][] map;

    private Image[] images;

    public void setMap(int[][] map) {
        this.map = map;
        ImageLoader imageLoader = new ImageLoader();
        this.images = imageLoader.getImages();
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
                    System.out.println("TEST");
                }
                System.out.println("TEST!");
                graphicsContext.drawImage(images[map[i][j]], i * getPixelWidth(), j * getPixelHeight(), getPixelWidth(), getPixelHeight());
            }
        }

    }
}
