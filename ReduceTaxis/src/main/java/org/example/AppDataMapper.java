package org.example;

import com.google.gson.Gson;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class AppDataMapper extends Mapper<LongWritable, Text, Text, Text> {
    private final Text outValue = new Text();
    private final Text outKey = new Text();
    private final Gson gson = new Gson();

    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        var taxiRide = AppDataParser.parseLine(line);

        var mapping = AppDataAgnosticMapper.process(taxiRide);

        if(mapping == null){
            return;
        }
        String json = gson.toJson(mapping.getValue());

        outKey.set(mapping.getKey());
        outValue.set(json);

        context.write(outKey, outValue);
    }
}
