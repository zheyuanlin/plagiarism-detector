import java.io.FileNotFoundException;
import java.util.HashSet;

public class PlagiarismDetector {
	
	
	private FileScanner file1Scanner;
	private FileScanner file2Scanner;
	private SynonymFileScanner synonymScanner;
	private int N;
	
	/**
	 * Constructs the plagiarism detector.
	 * @param syns: the synonym filename
	 * @param file1: the first input file's filename
	 * @param file2: the second input file's filename
	 * @param N: the size of each tuple, default = 3
	 */
	public PlagiarismDetector(String syns, String file1, String file2, int N)  {
		try {
			this.file1Scanner = new FileScanner(file1);
			this.file2Scanner = new FileScanner(file2);
			this.synonymScanner = new SynonymFileScanner(syns);
			this.N = N;
			
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: One or more filename is invalid!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the percentage for plagiarism detection.
	 * It was assumed each different line on the synonym file represents a different set of synonyms,
	 * thus using the disjoint set data structure for quick synonym lookup (different synonym has the same representative)
	 * Detailed implementation of the DisjointSet structure is in its respective .java file.
	 * @return
	 */
	public String detect() {
		int wordCountFile1 = 0;
		int matchedWordCount = 0;
		HashSet<String> matchingTable = new HashSet<>();
		
		StringBuilder sb = new StringBuilder();
		// Iterate through each N-sized tuple in file1, add them to the matchingTable for detection
		for (int i = 0; i <= this.file1Scanner.words.size() - this.N; i++) {
			for (int j = 0; j < N; j++) {
				String curr = this.file1Scanner.words.get(i + j);
				// Check if curr is in the synonym file
				// If so, replace curr with its representative in the disjoint set
				if (this.synonymScanner.mapping.get(curr) != null) {
					curr = this.synonymScanner.words.get(this.synonymScanner.ds.find(this.synonymScanner.mapping.get(curr)));
				}
				sb.append(curr);
			}
			matchingTable.add(sb.toString());
			sb = new StringBuilder();
			wordCountFile1 += 1;
		}
		
		// Iterate through each N-sized tuple in file2, match them with the tuples in matchingTable
		for (int i = 0; i <= this.file2Scanner.words.size() - this.N; i++) {
			for (int j = 0; j < N; j++) {
				String curr = this.file2Scanner.words.get(i + j);
				// Check if curr is in the synonym file
				// If so, replace curr with its representative in the disjoint set
				if (this.synonymScanner.mapping.get(curr) != null) {
					curr = this.synonymScanner.words.get(this.synonymScanner.ds.find(this.synonymScanner.mapping.get(curr)));
				}
				sb.append(curr);
			}
			if (matchingTable.contains(sb.toString())) {
				matchedWordCount += 1;
			}
			sb = new StringBuilder();
		}
		
		double percentage = (double)matchedWordCount / wordCountFile1;
		return Math.round(percentage * 100) + "%";
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// Check for valid number of arguments
		if (args.length < 3 || args.length > 4) {
			System.err.println("This program requires 3 arguments!\n1. Synonym file name\n2. Input file 1\n3. Input file 2\n3. (Optional)The tuple size");
		}
		else {
			int n = 3; // Default tuple size to 3
			if (args.length == 4) {
				// update n if the optional argument is inputted
				try {
					n = Integer.valueOf(args[3]);
				} catch (NumberFormatException e) {
					System.err.println("ERROR: The 4th argument needs to be an integer!");
					e.printStackTrace();
				}
			}
			
			// Creates the plagiarism detector and prints out the result of the detection
			PlagiarismDetector pd = new PlagiarismDetector(args[0], args[1], args[2], n);
			System.out.println(pd.detect());
			
		}
		
		
	}
	
	
} // end class
