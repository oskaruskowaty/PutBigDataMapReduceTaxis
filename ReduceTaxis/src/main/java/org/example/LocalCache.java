package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocalCache {
   public static Map<Integer, String> locationIdToZone = new HashMap<>();

   public static void BuildLocationIdToZoneMap(String path) throws IOException {
       try (BufferedReader br = new BufferedReader(new FileReader(path))) {
           br.readLine();
           String line;
           while ((line = br.readLine()) != null) {
               String[] parts = line.split(",");
               if (parts.length >= 3) {
                   int locId = Integer.parseInt(parts[0]);
                   String zoneName = parts[2];
                   locationIdToZone.put(locId, zoneName);
               }
           }
       }
   }
}
