import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.PriorityQueue;


public class GetTopFiveWordCombinations {

	public static void main(String[] args) throws Exception {

		String[] arg = new String[2];
		arg[0] = new String( "SampleProjectInput" );
		arg[1] = new String( "SampleProjectOutput.txt" );

		if (arg.length != 2) {
			System.out.printf("Usage: GetTopFiveWordCombinations <input dir> <output dir>\n");
			System.exit(-1);
		}

		// Test if output file exists and if so return error
		/*File outFile = new File(arg[1]);
		if (outFile.exists()){
			System.out.printf("GetTopFiveWordCombinations: arg[1] output file already exists.\n");
			System.exit(-1);			
		}*/

		//File directory = new File (".");
		//System.out.println ("Current directory's canonical path: " + directory.getCanonicalPath()); 
		//System.out.println ("Current directory's absolute  path: " + directory.getAbsolutePath());

		// Test if input directory exists and if so return error
		File inDir = new File(arg[0]);
		if (!inDir.isDirectory()){
			System.out.printf("GetTopFiveWordCombinations: arg[1] output file already exists.\n");
			System.exit(-1);			
		}

		// Determine filenames in input directory and sort them
		File[] inFiles = inDir.listFiles();

		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		FileReader fileReader;
		BufferedReader bufferedReader;

		fileWriter = new FileWriter(arg[arg.length-1]);
		bufferedWriter = new BufferedWriter(fileWriter);

		int N = 5; // number of next words to retain
		String line;
		String words[];
		String wordCurrent = "";
		int wsIndex;

		// Initialize priority list
		PriorityQueue pq = new PriorityQueue(N); 

		for(File file:inFiles ){

			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {

				words = line.split("\\t");
				if( words.length==3 ){
					if( !words[0].equals(wordCurrent) ){ // if a new word was reached

						// create a string listing the current word and the top N next words
						line = wordCurrent +" "; // write the current word
						// write each of the words in the priority queue to the string
						for( wsIndex = 0; wsIndex < pq.size() && wsIndex<N; wsIndex++ ){ 
							line = line + ((wordScore) pq.remove()).word + " "; 
						}
						line = line +"\n";
						fileWriter.write(line); // write the string to file.

						wordCurrent = words[0];// set the current word equal to the new word
						pq.clear(); // clear the priority queue	
					}
					pq.add( new wordScore(words[1], Integer.valueOf(words[2])));// add the first next word to the queue
				}
			}
			fileReader.close();
		}

		// Write last word to file
		// create a string listing the current word and the top N next words
		line = wordCurrent + ":"; // write the current word
		// write each of the words in the priority queue to the string
		for( wsIndex = 0; wsIndex < pq.size(); wsIndex++ ){ 
			line = line + ((wordScore) pq.remove()).word + " "; 
		}
		line = line +"\n";
		fileWriter.write(line); // write the string to file.
		fileWriter.close();
	}
}