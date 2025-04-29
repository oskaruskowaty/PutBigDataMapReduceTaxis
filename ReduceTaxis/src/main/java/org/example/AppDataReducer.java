package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import com.google.gson.Gson;

import java.io.IOException;

public class AppDataReducer extends Reducer<Text, Text, Text, Text> {
    private final Text result = new Text();
    private final Gson gson = new Gson();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double totalRatings = 0;
        int totalRatingCount = 0;
        int appCount = 0;

        for (Text val : values) {
            AppDataAgnosticMapper.AppDataAggregate aggregate = gson.fromJson(val.toString(), AppDataAgnosticMapper.AppDataAggregate.class);

            totalRatings += aggregate.totalRatings;
            totalRatingCount += aggregate.totalRatingCount;
            appCount += aggregate.appCount;
        }

        double averageRating = totalRatingCount == 0 ? 0 : totalRatings / totalRatingCount;

        AppDataResult resultData = new AppDataResult(totalRatings, totalRatingCount, appCount, averageRating);
        result.set(gson.toJson(resultData));

        context.write(key, result);
    }

    public static class AppDataResult {
        public double totalRatings;
        public int totalRatingCount;
        public int appCount;
        public double averageRating;

        public AppDataResult(double totalRatings, int totalRatingCount, int appCount, double averageRating) {
            this.totalRatings = totalRatings;
            this.totalRatingCount = totalRatingCount;
            this.appCount = appCount;
            this.averageRating = averageRating;
        }
    }
}
