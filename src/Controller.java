
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Controller {

    private final String path;
    private final String file;
    private final int minY = -125;
    private final int maxY = -50;
    private final int minX = 0;
    private final int maxX = 50;
    private final int xLimits = maxX - minX;
    private final int yLimits = maxY - minY;
    private final double distanceArray[] = {1, 2, 5};
    private final double xArray[] = {33.5 - 0.7, 36.7 - 0.7, 38.5 + 0.7, 44.7 - 0.7, 48.8 + 0.7};
    private final double yArray[] = {-117.9 + 0.7, -119.8 - 0.7, -121.4 + 0.7, -85.6 + 0.7, -122.3 - 0.7};

    public Controller(String path, String file) throws FileNotFoundException {
        this.path = path;
        this.file = file;

        File[][] grid = createGrid();
        fillGrid(path, file, grid);
        searchGrid(grid);

        countFiles(grid[0].length);
    }

    private void fillGrid(String path, String file, File[][] grid) {

        int N = grid[0].length;

        try {

            File myObj = new File("hotels.csv");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                Hotel hotel = readLine(line);

                double lx = xLimits / (double) N;
                double ly = yLimits / (double) N;

                int iPosition = (int) floor(((hotel.getLatitude() - minX) / lx));
                int yPosition = (int) floor(((hotel.getLongitude() - minY) / ly));

                fillCell(hotel, grid[iPosition][yPosition]);

            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private Hotel readLine(String line) {
        StringTokenizer st = new StringTokenizer(line, ",");

        Double id = Double.parseDouble(st.nextToken());
        String name = st.nextToken();
        Double reviews = Double.parseDouble(st.nextToken());
        Double price = Double.parseDouble(st.nextToken());
        Double x = Double.parseDouble(st.nextToken());
        Double y = Double.parseDouble(st.nextToken());

        return new Hotel(id, name, reviews, price, x, y);

    }

    private void fillCell(Hotel hotel, File cell) {
        try {
            FileWriter myWriter = new FileWriter(cell.getAbsolutePath(), true);

            myWriter.write((int) hotel.getId() + ","
                    + hotel.getName() + ","
                    + hotel.getNumberOfReviews() + ","
                    + hotel.getPrice() + ","
                    + hotel.getLatitude() + ","
                    + hotel.getLongitude() + "\n"
            );
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private File[][] createGrid() {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter N number: ");

        int N = myObj.nextInt() + 1;  // Read user input
        File[][] grid = new File[N][N];

        try {

            for (int j = 0; j < N; j++) {
                for (int i = 0; i < N; i++) {
                    grid[i][j] = new File("grid" + File.separator + "cell " + i + "," + j + ".csv");

                    if (grid[i][j].createNewFile()) {
                        System.out.println("File created: " + grid[i][j].getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return grid;

    }

    private void countFiles(int N) {

        int countEmptyFiles = 0;
        int countFilledFiles = 0;
        int temp = 0;
        int maxCounter = 0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                File file = new File("grid" + File.separator + "cell " + i + "," + j + ".csv");

                if (file.length() == 0) {
                    countEmptyFiles++;
                } else {
                    countFilledFiles++;
                }

                try {

                    Scanner myReader = new Scanner(file);
                    temp = 0;
                    while (myReader.hasNextLine()) {
                        temp++;
                        myReader.nextLine();
                    }
                    if (temp > maxCounter) {
                        maxCounter = temp;
                    }

                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
        }
        System.out.println("\n\n====================================");
        System.out.println("Number of empty cells:  \t" + countEmptyFiles);
        System.out.println("Number of filled cells: \t" + countFilledFiles);
        System.out.println("Number of the most logs: \t" + maxCounter);
        System.out.println("====================================");

    }

    private void searchGrid(File[][] grid) throws FileNotFoundException {

        ArrayList<String> results = new ArrayList<>();

        for (int i = 0; i < xArray.length; i++) {
            for (int j = 0; j < distanceArray.length; j++) {

                int counterHotelsFound = 0;//deutero
                int counterHotelsRead = 0;//prwto
                int counterCellsRead = 0;//trito
                Set<String> cellIds = new TreeSet();

                double searchXMax = xArray[i] + distanceArray[j];
                double searchXMin = xArray[i] - distanceArray[j];
                double searchYMax = yArray[i] + distanceArray[j];
                double searchYMin = yArray[i] - distanceArray[j];

                //Keep the search inside the grid
                if (searchXMax > maxX) {
                    searchXMax = maxX;
                }
                if (searchXMin < minX) {
                    searchXMin = minX;
                }
                if (searchYMax > maxY) {
                    searchYMax = maxY;
                }
                if (searchYMin < minY) {
                    searchYMin = minY;
                }

                double lx = xLimits / (double) grid.length;
                double ly = yLimits / (double) grid.length;

                int iMinPosition = (int) floor(((searchXMin - minX) / lx));
                int jMinPosition = (int) floor(((searchYMin - minY) / ly));

                int iMaxPosition = (int) floor(((searchXMax - minX) / lx));
                int jMaxPosition = (int) floor(((searchYMax - minY) / ly));

                if (iMinPosition < 0) {
                    iMinPosition = 0;
                }
                if (jMinPosition < 0) {
                    jMinPosition = 0;
                }
                if (iMaxPosition >= grid.length) {
                    iMaxPosition = grid.length - 1;
                }
                if (jMaxPosition >= grid.length) {
                    jMaxPosition = grid.length - 1;
                }

                for (int iCurrent = iMinPosition; iCurrent <= iMaxPosition; iCurrent++) {

                    for (int jCurrent = jMinPosition; jCurrent <= jMaxPosition; jCurrent++) {
                        counterCellsRead++;
                        File myObj = new File("grid" + File.separator + "cell " + iCurrent + "," + jCurrent + ".csv");
                        try (Scanner myReader = new Scanner(myObj)) {

                            while (myReader.hasNextLine()) {
                                counterHotelsRead++;
                                String line = myReader.nextLine();
                                Hotel hotel = readLine(line);

                                if ((searchXMax >= hotel.getLatitude() && searchXMin <= hotel.getLatitude()) && (searchYMax >= hotel.getLongitude() && searchYMin >= hotel.getLongitude())) {
                                    //System.out.println(hotel.toString());
                                    counterHotelsFound++;
                                    cellIds.add(hotel.getName());
                                }
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }

                    }
                }
                String result = "Number of hotels read from disk: " + counterHotelsRead + " for x,y: " + xArray[i] + "," + yArray[i] + " and w: " + distanceArray[j]
                        + "\nNumber of hotels found : " + counterHotelsFound
                        + "\nNumber of cells/blocks read from disk : " + counterCellsRead;

                results.add(result);
                //System.out.println(cellIds);
            }

        }

        for (String result : results) {
            System.out.println("============================================================");
            System.out.println(result);
        }

    }

}
