import java.io.File;

public class Test {
    public static void main(String[] args) {

        File folder = new File(("res/tiles/"));
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName().split("\\.")[0];
                System.out.println(fileName);
            }
        }
    }
}
