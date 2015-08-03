
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

public class StubTest {

	/*
	 * Declare harnesses that let you test a mapper, a reducer, and a mapper and
	 * a reducer working together.
	 */
	MapDriver<Object, Text, Text, LongWritable> mapDriver;
	ReduceDriver<Text, LongWritable, Text, LongWritable> reduceDriver;
	MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable> mapReduceDriver;

	/*
	 * Set up the test. This method will be called before every test.
	 */
	@Before
	public void setUp() {

		/*
		 * Set up the mapper test harness.
		 */
		StubMapper mapper = new StubMapper();
		mapDriver = new MapDriver<Object, Text, Text, LongWritable>();
		mapDriver.setMapper(mapper);
		/*
		 * Set up the reducer test harness.
		 */
		StubReducer reducer = new StubReducer();
		reduceDriver = new ReduceDriver<Text, LongWritable, Text, LongWritable>();
		reduceDriver.setReducer(reducer);

		/*
		 * Set up the mapper/reducer test harness.
		 */
		mapReduceDriver = new MapReduceDriver<Object, Text, Text, LongWritable, Text, LongWritable>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}

	/*
	 * Test the mapper and reducer working together.
	 */
	@Test
	public void testMapReduce() throws IOException {

		// Read input file and setup mapReduceDrive input
		String str;
		Double i=0.0;
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		fileReader = new FileReader("A4_P2input.txt");
		bufferedReader = new BufferedReader(fileReader);
		while ((str = bufferedReader.readLine()) != null) {
			i=i+1;
			mapReduceDriver.addInput(new Pair<Object, Text>(String.valueOf(i), new Text(str)));
			System.out.print(".");
		}
		System.out.print("\n\n");
		fileReader.close();

		List<Pair<Text, LongWritable>> output = mapReduceDriver.run();
		
		for (Pair<Text, LongWritable> p : output) {
			System.out.println(p.getFirst() + "\t" + p.getSecond());
		}
	}
}
