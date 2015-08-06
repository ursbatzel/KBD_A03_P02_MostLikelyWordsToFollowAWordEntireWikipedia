public class wordScore implements Comparable<Object> { 
	String word; 
	int score; 

	public wordScore (String word, int score) { 
		this.word = word; 
		this.score = score; 
	} 

	public int compareTo (Object obj) { 
		wordScore that = (wordScore) obj; 

		int a = this.score; 
		int b = that.score; 

		if (a<b) return 1; 
		if (a>b) return -1; 
		return 0; 
	} 

} 
