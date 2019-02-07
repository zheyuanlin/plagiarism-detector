// The following was implemented in my data structures class, re-using it if that is okay.


// A disjoint set data structure using path compression and union by rank
public class DisjointSet {
	
	private int setSize;
	private int[] parent;
	private int[] rank;
	
	public DisjointSet(int setSize) {
		this.setSize = setSize;
		this.parent = new int[setSize];
		this.rank = new int[setSize];
		createSet();
	}
	
	/**
	 * Initializes the set
	 */
	private void createSet() {
		for (int i = 0; i < this.setSize; i++) {
			parent[i] = i; // Set each elems parent to be itself
		}
	}
	
	/**
	 * Returns the representative of the set which i is in
	 * @param i
	 * @return
	 */
	public int find(int i) {
		if (parent[i] != i) {
			parent[i] = find(parent[i]);
		}
		return parent[i];
	}
	
	/**
	 * Joins the sets i and j are in
	 * @param i
	 * @param j
	 */
	public void union(int i, int j) {
		int repOfi = find(i);
		int repOfj = find(j);
		
		// Already in the same set
		if (repOfi == repOfj) return;
		// Otherwise, join them
		if (rank[repOfi] < rank[repOfj]) parent[repOfi] = repOfj;
		else if (rank[repOfi] > rank[repOfj]) parent[repOfj] = repOfi;
		else {
			// ranks are the same, move under j's set, increase rank of j's set by 1
			parent[repOfi] = repOfj;
			rank[repOfj] += 1;
		}
	}
	
	
	
	
}
