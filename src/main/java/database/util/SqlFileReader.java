package database.util;

import com.crowdar.core.PropertyManager;

import java.io.*;

public class SqlFileReader {

    public static String getQueryString(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(buildPath(file)));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            sb.append(line);
        }
        return sb.toString();
    }

    private static String buildPath(String filename) {
        String base = PropertyManager.getProperty("crowdar.base.path");
        return base + filename;
    }


}
