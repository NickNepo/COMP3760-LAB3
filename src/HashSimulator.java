/** Simulates inserting keys into a hash table using three
 * different hash functions (H1, H2, H3) and closed hashing with
 * linear probing for collision resolution.
 *
 * @author Nicholas Nepomuceno
 * Student Num: A01431279
 * Set: B
 * @version 1.0
 */
public class HashSimulator
{
    /** Simulates inserting keys into a hash table using three hash functions.
     *
     * @param keys An array of strings representing the keys to be inserted into the hash table.
     * @param HTSize The size of the hash table to use.
     * @return results An int array of length 6:
     */
    public int[] runHashSimulation (String[] keys, int HTSize)
    {
        int[] results = new int[6]; // [H1coll, H1probe, H2coll, H2probe, H3coll, H3probe]

        // Run for all 3 hash functions
        results[0] = simulateHash(keys, HTSize, 1)[0]; // H1 collisions
        results[1] = simulateHash(keys, HTSize, 1)[1]; // H1 probes
        results[2] = simulateHash(keys, HTSize, 2)[0]; // H2 collisions
        results[3] = simulateHash(keys, HTSize, 2)[1]; // H2 probes
        results[4] = simulateHash(keys, HTSize, 3)[0]; // H3 collisions
        results[5] = simulateHash(keys, HTSize, 3)[1]; // H3 probes

        return results;
    }

    /**
     * Calculates the sum of letter values (A=1, B=2, ... Z=26)
     * modulo the hash table size.
     * @param name person's name to hash
     * @param HTSize size of the hash table
     * @return An integer index between 0 and HTSize-1.
     */
    public int H1(String name, int HTSize)
    {
        int hashValue = 0;
        for (char c : name.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                hashValue += (c - 'A' + 1);
            }
        }
        return hashValue % HTSize;
    }

    /**
     * Uses a positional polynomial hash: each letter value is multiplied
     * by 26^position. Applies modulo at each step to avoid overflow.
     *
     * @param name name to hash
     * @param HTSize size of hash table
     * @return index between 0 and HTSize - 1
     */
    public int H2(String name, int HTSize)
    {
        long sum = 0;
        long p = 26;
        long power = 1;

        for (int i = 0; i < name.length(); i++) {
            int val = name.charAt(i) - 'A' + 1;
            sum = (sum + (val * power) % HTSize) % HTSize;
            power = (power * p) % HTSize;
        }
        return (int) sum;
    }

    /**
     * Hash function Polynomial rolling hash using prime 31 (Robin-Karp)
     * reference: https://cp-algorithms.com/string/string-hashing.html
     * @param name name to hash
     * @param HTSize size of Hash table
     * @return index between 0 and HTSize - 1.
     */
    public int H3(String name, int HTSize)
    {
        long hash = 0;
        long p = 31; // small prime number used as base for polynomial
        long power = 1; //used to track p^i for a characters position in the string

        for (char c : name.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                //convert char to numerical value
                int letterValue = c - 'A' + 1;
                //multiply letter value by p^i, then mod by table size
                hash = (hash + letterValue * power) % HTSize;
                //Update power for next character (p^i+1)
                //Apply modulo HTsize to keep it within bounds and avoid overflow
                power = (power * p) % HTSize;
            }
        }
        //return the hash value
        return (int) hash;
    }

    /**
     * Private helper method to simulate inserting keys into the hash table
     * using a specific hash function and counting collisions and probes.
     * @param keys Array of names to insert
     * @param tableSize HashTable size
     * @param hashType decides which hash funtion to use, 1 = H1, 2 = H2, 3 = H3
     * @return int array that stores the amount of collisions and amount of probes
     */
    private int[] simulateHash(String[] keys, int tableSize, int hashType) {
        String[] table = new String[tableSize];
        int collisions = 0;
        int probes = 0;

        for (String key : keys) {
            int index = 0;

            // Choose which hash function to use
            if (hashType == 1) index = H1(key, tableSize);
            else if (hashType == 2) index = H2(key, tableSize);
            else index = H3(key, tableSize);

            if (table[index] == null) {
                // No collision, place key
                table[index] = key;
            } else {
                // Collision occurred
                collisions++;
                int probeIndex = (index + 1) % tableSize;

                // Linear probing loop
                while (table[probeIndex] != null) {
                    probes++; // count each slot checked after collision
                    probeIndex = (probeIndex + 1) % tableSize;
                }

                // Count the final empty slot too
                probes++;
                table[probeIndex] = key;
            }
        }

        return new int[]{collisions, probes};
    }


}
