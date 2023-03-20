import java.io.FileWriter;
import java.io.IOException;

public class FileCreater {

    public static void createObjectFile(int[][] objects, String str, Tile[] tiles) {
        try {
            FileWriter myWriter = new FileWriter("res/maps/" + str + "_objects.txt");

            for (int i = 0; i < objects.length; i++) {
                for (int j = 0; j < objects[i].length; j++) {
                    if (objects[i][j] != 0) {
                        myWriter.write("X: " + i + " Y: " + j + " " + tiles[objects[i][j]].getName() + "\n");
                    }
                }
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void createFile(Map map, String str) {
        int[][] indexMap = map.getMap();

        try {
            FileWriter myWriter = new FileWriter("res/maps/" + str + ".txt");

            for (int[] col : indexMap) {
                for (int row : col) {
                    myWriter.write(row + " ");
                }
                myWriter.write("\n");
            }


            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
