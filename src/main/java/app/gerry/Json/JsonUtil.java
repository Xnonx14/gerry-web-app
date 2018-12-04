package app.gerry.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {

    public static List<ChunkJson> createChunkJsons(String filepath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            Gson gson = new GsonBuilder().create();
            ChunkJson[] chunkJsons = gson.fromJson(reader, ChunkJson[].class);
            return Arrays.stream(chunkJsons).collect(Collectors.toList());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
