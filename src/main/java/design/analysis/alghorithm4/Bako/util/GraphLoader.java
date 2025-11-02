package design.analysis.alghorithm4.Bako.util;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;

public class GraphLoader {

    public static GraphData loadGraph(String filePath) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, GraphData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}