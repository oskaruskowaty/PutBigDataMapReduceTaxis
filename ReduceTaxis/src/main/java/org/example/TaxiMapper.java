package org.example;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.net.URI;

public class TaxiMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            Path zoneFilePath = new Path("zones");
            LocalCache.BuildLocationIdToZoneMap(zoneFilePath.toString());
        }
    }

    private final IntWritable outValue = new IntWritable();
    private final Text outKey = new Text();

    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        var taxiRide = TaxiRideParser.parseLine(line);

        var mapping = TaxiRideAgnosticMapper.Process(taxiRide);

        if(mapping == null){
            return;
        }
        outKey.set(mapping.getKey());
        outValue.set(mapping.getValue());

        context.write(outKey, outValue);
    }
}
