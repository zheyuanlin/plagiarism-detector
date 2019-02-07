import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SynonymFileScanner {
	
	private Scanner s; // The scanner for scanning
	public List<String> words; // The list of words
	public Map<String, Integer> mapping; // String to index mapping for quick lookup
	public DisjointSet ds; // Disjoint sets using indices (in mapping and words)
	
	/**
	 * Constructor for the SynonymFileScanner
	 * @param filename: filename of the synonym file
	 * @throws FileNotFoundException if the filename is invalid
	 */
	public SynonymFileScanner(String filename) throws FileNotFoundException {
		s = new Scanner(new File(filename));
		words = new ArrayList<>();
		mapping = new HashMap<>();
		ds = scanSynonyms();
	}
	
	/**
	 * Builds the disjoint set for synonyms
	 * ASSUMPTION: - Every line in the file is a set of synonyms
	 * @return
	 */
	public DisjointSet scanSynonyms() {
		List<List<String>> lines = new ArrayList<>();
		int size = 0;
		while (s.hasNextLine()) {
			// ASSUMPTION: - Words are separated by whitespaces, not commas, periods, or any other symbol
    		
	    		// Gets the next word, by delimiter matches whitespace by default
	    		// Removes commas and/or periods at the end of each word
			String[] line = s.nextLine().split("\\s+");
    			List<String> temp = new ArrayList<>();
    			for (String word : line) {
    				temp.add(word);
    				words.add(word);
    				mapping.put(word, size);

        			size += 1;
    			}
    			lines.add(temp);	
		}
		
		DisjointSet ans = new DisjointSet(size);
		// Union each line
		int curr = 0;
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).size() - 1; j++) {
				ans.union(curr, curr + 1);
				curr += 1;
			}
			curr += 1;
		}
	    
	    return ans;
	}
}
