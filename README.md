Commands:

gsutil cp gs://$BUCKET_NAME/projekt/datasource1/* ./datasource1/
gsutil cp gs://$BUCKET_NAME/projekt/datasource4/* ./datasource4/

hadoop fs -mkdir -p /datasource1
hadoop fs -put ./datasource1/* /datasource1

hadoop fs -mkdir -p /datasource4
hadoop fs -put ./datasource4/* /datasource4

hadoop jar map-reduce.jar /datasource1 output /datasource4/taxi_zone_lookup.csv
