import javafx.scene.image.Image;

public class Tile extends Image {

    private int index;
    private String name;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile(String s, String name, int index) {
        super(s);
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        return name;
    }
}
