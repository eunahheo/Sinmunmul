package sinmunmul;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.GenericOptionsParser;

public class Tfidf {

	public static class FrequencyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		// variable declairations
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			// value.toString() : get a line
			StringTokenizer st = new StringTokenizer(value.toString());
			String filename = ((FileSplit) context.getInputSplit()).getPath().getName();
			String seq = "";
			for(int i=0; i<filename.length(); i++) {
				if(filename.charAt(i)=='_') {
					i++;
					while(filename.charAt(i)!='.') {
						seq += filename.charAt(i++);
					}
				}
			}
			while ( st.hasMoreTokens() ) {
				word.set(st.nextToken()+"@"+seq);
				context.write(word,one);
			}
		}
	}

	// FrequencyMapper 결과 : 단어@뉴스시퀀스, 1

	public static class FrequencyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

			int sum = 0;
			for ( IntWritable val : values ) {
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	// FrequencyReducer 결과 : 단어@뉴스시퀀스 = 단어 출현 횟수

	public static class WordCountMapper extends Mapper<LongWritable,Text,Text,Text> {
				
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] keyAndvalue = value.toString().split("\t");
			String[] keyAndseq = keyAndvalue[0].split("@");
			context.write(new Text(keyAndseq[1]),new Text(keyAndseq[0]+"="+keyAndvalue[1]));
		}
	}

	// WordCountMapper 결과 : 뉴스시퀀스, 단어 = 단어 출현 횟수

	public static class WordCountReducer extends Reducer<Text,Text,Text,Text> {

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

			int N = 0;
			Map<String, Integer> map = new HashMap<>();

			for(Text val : values) {
				String[] wordCount = val.toString().split("=");
				map.put(wordCount[0],Integer.parseInt(wordCount[1]));
				N += Integer.parseInt(wordCount[1]);
			}

			for(String mapkey : map.keySet()) {
				context.write(new Text(mapkey+"@"+key.toString()), new Text(map.get(mapkey) + "/" + N));
			}
		}
	}

	// WordCountReducer 결과 : 단어@뉴스시퀀스, 단어출현횟수/문서의 전체 단어수

	public static class TfidfMapper extends Mapper<LongWritable,Text,Text,Text> {		

		// map function (Context -> fixed parameter)
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] keyAndvalue = value.toString().split("\t");
			String[] keyAndseq = keyAndvalue[0].split("@");
			if(keyAndvalue.length == 2 && keyAndseq.length == 2)
				context.write(new Text(keyAndseq[0]),new Text(keyAndseq[1]+"="+keyAndvalue[1]));
			
		}
	}

	// TfidfMapper 결과 : 단어, 뉴스시퀀스 = 단어출현횟수/문서의 전체 단어수
	 
	public static class TfidfReducer extends Reducer<Text,Text,Text,Text> {

		private static final DecimalFormat DF = new DecimalFormat("###.########");

		private DoubleWritable result = new DoubleWritable();
		private String param;

		private class News implements Comparable<News>{
			String seq;
			String word;
			Double tfidf;

			public News(String seq, String word, Double tfidf) {
				this.seq = seq;
				this.word = word;
				this.tfidf = tfidf;
			}

			@Override
			public int compareTo(News o) {
				return -(int)(this.tfidf - o.tfidf);
			}
		}

		public void setup(Context context) throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			param = conf.get("mode");
		}		

		public void reduce(Text key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {

			int D = Integer.parseInt(context.getJobName());
			int d = 0;
			Map<String, String> map = new HashMap<>();
			//Map<String, Integer> temp_map = new HashMap<>();
			//PriorityQueue<News> pq = new PriorityQueue<>();
			for(Text val : values) {
				String[] seqandfre = val.toString().split("=");
				d++;
				String seq = seqandfre[0];
				String f = seqandfre[1];
				map.put(seq, f);
			}
			
			for(String doc : map.keySet()) {
				String[] word = map.get(doc).split("/");
					
				String n = word[0];
				String N = word[1];

				double tf = 0;
				if(param.equals("boolean")) tf = 1;
				else if (param.equals("logscale")) tf = Math.log10(Double.parseDouble(n)+1);
				else if (param.equals("augmented")) tf = ((0.5 * Double.parseDouble(n))) / Double.parseDouble(N) + 0.5;

				double idf = Math.abs((double) D / (double) d);
				double tfidf = d == D? tf : tf * Math.log10(idf);

				if(doc.toString().equals(key.toString())) continue;
				// pq.offer(new News(doc.toString(),key.toString(),Double.parseDouble(DF.format(tfidf))));
	                         context.write(new Text(key+"@"+doc), new Text(DF.format(tfidf)));

			}
			
			/*
			int cnt = 0;
			while(!pq.isEmpty()) {
				News news = pq.poll();
				if(temp_map.get(news.seq)==null) {
					cnt++;
					temp_map.put(news.seq, 1);
					context.write(new Text(news.word + "@" + news.seq), new Text(news.tfidf+""));
				}
				else {
					if(temp_map.get(news.seq)<3) {
						cnt++;
						temp_map.put(news.seq, temp_map.get(news.seq)+1);
					 	context.write(new Text(news.word + "@" + news.seq), new Text(news.tfidf+""));
					}
				}
			}
			context.write(new Text("카운트"), new Text(cnt+""));
			*/
		}
	}

	// TfidfReducer 결과 : 단어@뉴스시퀀스, TF-IDF

	/* Main function */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		if ( otherArgs.length != 2 ) {
			System.err.println("Usage: <in> <out>");
			System.exit(2);
		}
		
		conf.set("mode", "logscale");
		FileSystem hdfs = FileSystem.get(conf);
		Path input = new Path(args[0]);

		Job job1 = Job.getInstance(conf, "job1");
		job1.setJarByClass(Tfidf.class);

		// let hadoop know my map and reduce classes
		job1.setMapperClass(FrequencyMapper.class);
		job1.setReducerClass(FrequencyReducer.class);
		job1.setCombinerClass(FrequencyReducer.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);

		// set number of reduces
		job1.setNumReduceTasks(1);

		// set input and output directories	
		Path job1_output = new Path("recommend/job1");
		if (hdfs.exists(job1_output))
				hdfs.delete(job1_output, true);
		FileInputFormat.addInputPath(job1,input);
		FileOutputFormat.setOutputPath(job1,job1_output);

		job1.waitForCompletion(true);

		Job job2 = Job.getInstance(conf, "job2");
		job2.setJarByClass(Tfidf.class);

		// let hadoop know my map and reduce classes
		job2.setMapperClass(WordCountMapper.class);
		job2.setReducerClass(WordCountReducer.class);

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		// set number of reduces
		job2.setNumReduceTasks(1);

		// set input and output directories
		Path job2_output = new Path("recommend/job2");
		if (hdfs.exists(job2_output))
				hdfs.delete(job2_output, true);

		FileInputFormat.addInputPath(job2,new Path("recommend/job1"));
		FileOutputFormat.setOutputPath(job2,job2_output);

		job2.waitForCompletion(true);

		Job job3 = Job.getInstance(conf, "job3");
		job3.setJarByClass(Tfidf.class);

		// let hadoop know my map and reduce classes
		job3.setMapperClass(TfidfMapper.class);
		job3.setReducerClass(TfidfReducer.class);

		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);

		// set number of reduces
		job3.setNumReduceTasks(1);

		// set input and output directories
		Path job3_output = new Path(otherArgs[1]);
		if (hdfs.exists(job3_output))
				hdfs.delete(job3_output, true);

		FileInputFormat.addInputPath(job3,new Path("recommend/job2"));
		FileOutputFormat.setOutputPath(job3,job3_output);

		FileSystem fs = input.getFileSystem(conf);
		FileStatus[] stat = fs.listStatus(input);

		job3.setJobName(String.valueOf(stat.length));

		System.exit(job3.waitForCompletion(true) ? 0 : 1 );
	}
}


