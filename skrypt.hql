DROP TABLE IF EXISTS zone_lookup;
CREATE TABLE zone_lookup (
  LocationID INT,
  Borough STRING,
  Zone STRING,
  service_zone STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

DROP TABLE IF EXISTS rides;
CREATE TABLE rides (
  month STRING,
  PULocationID INT,
  passengers INT
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "([0-9]{4}-[0-9]+),([0-9]+)\\s+([0-9]+)",
  "output.format.string" = "%1$s,%2$s,%3$s"
)
STORED AS TEXTFILE;


LOAD DATA INPATH '/user/oskarcukrowicz/zone_lookup/zone_lookup_no_header.csv'
INTO TABLE zone_lookup;

LOAD DATA INPATH '/user/oskarcukrowicz/output2'
INTO TABLE rides;

DROP TABLE IF EXISTS top3_boroughs_json_emulated;
CREATE TABLE top3_boroughs_json_emulated
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '/user/oskarcukrowicz/top3_output_json' AS

SELECT
  CONCAT(
    '{',
    '"month":"', month, '",',
    '"borough":"', Borough, '",',
    '"passengers":', passengers,
    '}'
  ) AS json_record
FROM (
  SELECT
    r.month,
    z.Borough,
    SUM(r.passengers) AS passengers,
    ROW_NUMBER() OVER (PARTITION BY r.month ORDER BY SUM(r.passengers) DESC) AS rn
  FROM rides r
  JOIN zone_lookup z ON r.PULocationID = z.LocationID
  GROUP BY r.month, z.Borough
) ranked
WHERE rn <= 3;