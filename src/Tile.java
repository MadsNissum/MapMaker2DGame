import javafx.scene.image.Image;

public class Tile extends Image {

    private final int index;
    private final String name;

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public Tile(String s, String name, int index) {
        super(s, 16, 16, false ,false);
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        return name;
    }
}
