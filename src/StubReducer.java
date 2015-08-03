import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.*;

public class StubReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	public void reduce(Text key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {

		long sum = 0;
		for(LongWritable v:values){
			sum += v.get();
		}

		// System.out.println(key.toString() + " - " String.valueOf(sum) );

		if( sum > 2 ){
			context.write(key, new LongWritable(sum) );
		}
	}
}
