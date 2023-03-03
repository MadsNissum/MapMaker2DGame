import javafx.scene.image.Image;

public class ImageLoader {

    private Image[] images = new Image[100];

    public ImageLoader() {
        setup();
    }

    public Image[] getImages() {
        return images;
    }

    public void setup() {
        images[1] = loadImage("brick");
        images[2] = loadImage("dirt");
        images[3] = loadImage("grass");
        images[4] = loadImage("palm_tree");
    }

    public Image loadImage(String str) {
        return new Image(String.valueOf(getClass().getResource("tiles/" + str +  ".png")));
    }
}
