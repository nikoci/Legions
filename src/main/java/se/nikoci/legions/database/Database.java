package se.nikoci.legions.database;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import se.nikoci.legions.Legions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Database {
    private final String path;

    public Database(@NotNull String path){
        this.path = "jdbc:sqlite:./data/" + path;
        connect();
    }

    public Connection connect() {
        try {
            Files.createDirectories(Paths.get("./data"));
            return DriverManager.getConnection(path);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Database init(){
        var is = getClass().getClassLoader().getResourceAsStream("db-structure.yaml");
        Map<String, String> structure;

        //Making sure input stream is not null
        if (is != null) structure = parseStructure(is, null);
        else {
            Legions.logger.severe("Could not read db-structure.yml resource");
            return null;
        }

        var conn = connect();
        for (var es : structure.entrySet()) {
            try {
                Statement stmt = conn.createStatement();
                stmt.execute(es.getKey()+es.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try { conn.close(); } catch (SQLException e) {
            throw new RuntimeException(e); }

        return this;
    }

    private Map<String, String> parseStructure(InputStream is, Map<?, ?> yaml) {
        var snake = new Yaml();
        Map<String, Object> yis = null;
        if (is != null) yis = snake.load(is);
        if (yis != null) yaml = yis;
        var structure = new HashMap<String, String>();

        for (var es : yaml.entrySet()) {
            var key = es.getKey().toString();
            var value = "";

            // Making sure to append on first loop through
            if (yis != null) key = "CREATE TABLE IF NOT EXISTS " + es.getKey().toString() + " (";

            if (es.getValue() instanceof Map<?, ?> rval) {
                var valueB = new StringBuilder();
                for (var pes : parseStructure(null, rval).entrySet()) {
                    valueB.append(pes.getKey())
                            .append(" ").append(pes.getValue())
                            .append(",\n");
                }
                value = valueB.toString();
            } else {
                value = es.getValue().toString();
            }

            structure.put(key, value.replaceAll(",$", ");"));
        }

        return structure;
    }

    public void insert(String table, Map<String, Object> props) {
        var keys = props.keySet().toString().replaceAll("\\[|\\]|\\s", "");
        var placeholders = props.keySet().stream()
                .map(x -> "?,").collect(Collectors.joining()).replaceAll(",$", "");
        var sql = new StringBuilder();

        sql.append("INSERT INTO ")
                .append(table).append("(")
                .append(keys).append(") VALUES (")
                .append(placeholders).append(")");

        Connection conn = connect();
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            var values = props.values().toArray();
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i+1, values[i]);
            }

            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray select(String table, String[] conditions){
        var sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(table);

        if (conditions != null && conditions.length >= 1){ //Appending conditions
            sql.append(" WHERE ");
            for (String c : conditions) sql.append(c).append(" ");
        }

        var conn = connect();
        var result = new JSONArray();

        try {
            var st = conn.createStatement();
            var rs = st.executeQuery(sql.toString());

            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();
            List<String> colNames = IntStream.range(0, numCols)
                    .mapToObj(i -> {
                        try {
                            return md.getColumnName(i + 1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return "?";
                        }
                    }).toList();

            while (rs.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(cn -> {
                    try {
                        row.put(cn, rs.getObject(cn));
                    } catch (JSONException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                result.put(row);
            }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); }

        return result;
    }

    public <T> List<T> selectParse(String table, String[] conditions, Class<T> tClass) {
        List<T> lt = new ArrayList<>();
        for (var o : select(table, conditions)) lt.add(new Gson().fromJson(o.toString(), tClass));
        return lt;
    }

    public <T> List<T> selectParse(String table, Class<T> tClass) {
        return selectParse(table, null, tClass); }
}
