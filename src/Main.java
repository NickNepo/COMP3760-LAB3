import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        try {
            // === 1. Read file into an array ===
            String filename = "src/5746names.txt"; // or 798names.txt, etc.
            String[] keys = readNamesFromFile(filename);

            // === 2. Choose hash table sizes ===
            int[] tableSizes = {keys.length, keys.length * 2, keys.length * 5, keys.length * 10, keys.length * 100};

            // === 3. Create simulator ===
            HashSimulator simulator = new HashSimulator();

            // === 4. Run and display results ===
            for (int size : tableSizes) {
                int[] results = simulator.runHashSimulation(keys, size);

                System.out.println("\n-----------------------------------");
                System.out.println("Hash Table Size: " + size);
                System.out.println("File: " + filename);
                System.out.println("-----------------------------------");
                System.out.println("H1 Collisions: " + results[0]);
                System.out.println("H1 Probes:     " + results[1]);
                System.out.println("H2 Collisions: " + results[2]);
                System.out.println("H2 Probes:     " + results[3]);
                System.out.println("H3 Collisions: " + results[4]);
                System.out.println("H3 Probes:     " + results[5]);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Helper function to read all names from a text file into a String[].
     * Each line in the file should contain one name.
     */
    private static String[] readNamesFromFile(String filename) throws IOException {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    names.add(line.trim());
                }
            }
        }
        return names.toArray(new String[0]); // convert to plain array

    }
}