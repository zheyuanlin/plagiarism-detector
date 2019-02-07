import java.io.*;
import java.util.*;

public class FileScanner {
	
	private Scanner s;
	public List<String> words;
	
	/**
	 * Creates the file scanner
	 * @param filename: the filename for the file to be scanned
	 * @throws FileNotFoundException if filename is invalid
	 */
	public FileScanner(String filename) throws FileNotFoundException {
		s = new Scanner(new File(filename));
		words = new ArrayList<>();
		scanForWords();
	}
	
	/**
	 * Stores all the words in the file in order in a list
	 * ASSUMPTION: - Words in the file are separated by whitespaces ONLY
	 */
	public void scanForWords() {
	    while(s.hasNext()) {
	    		// Gets the next word, by delimiter matches whitespace by default
	    		words.add(s.next());
	    }
	}
}
