import java.io.File;

public class ImageLoader {

    private Tile[] tiles;
    public ImageLoader() {
        setup();
    }
    public Tile[] getTiles() {
        return tiles;
    }

    public void setup() {

        File folder = new File(("res/tiles/"));
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;

        tiles = new Tile[listOfFiles.length+1];

        tiles[0] = new Tile(String.valueOf(getClass().getResource("tools/eraser.png")), "Eraser", 0);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName().split("\\.")[0];
                tiles[i+1] = loadImage(fileName, i+1);
            }
        }
    }

    public Tile loadImage(String str, int index) {
        return new Tile((String.valueOf(getClass().getResource("tiles/" + str +  ".png"))), str, index);
    }
}
