import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;

public class StubMapper extends Mapper<Object, Text, Text, LongWritable> {

	@Override
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		// From input create an array of strings one word per index
		String[] fragment = value.toString().split("[^ a-zA-Z]+");
		String[] words;
		int w;
		
		for( int s=0; s<fragment.length; s++ ){
			//System.out.println( fragment[s] );
			words = fragment[s].split(" ");
			w = 0;
			while(w<words.length-1 ){
				if( !words[w].isEmpty() && !words[w+1].isEmpty() ){
					System.out.println( words[w]+"\t"+words[w+1] + "\t1" );
					context.write(new Text(words[w].toLowerCase()+"\t"+words[w+1].toLowerCase() ), new LongWritable(1) );
				}
				w+=1;
			}
			// System.out.print("\n");
		}
		// System.out.println( "" );
	}
}

