package se.nikoci.legions;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import se.nikoci.legions.structs.Legion;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void disconnect(@NotNull Connection conn){
        try { conn.close(); } catch (SQLException e) { throw new RuntimeException(e); }
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

        Legions.logger.info("parseStructure: " + yaml);

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

    @Getter
    @AllArgsConstructor
    private static class SelectObject {
        private String table;
        private String[] targets;
        private String[] conditions;
        private Object[] results;

        public <T> T map(Class<T> classOfT) {

        }
    }

    public SelectObject select(String table, String[] targets, String[] conditions) {
        var sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(table);

        if (conditions != null && conditions.length >= 1){ //Appending conditions
            sql.append(" WHERE ");
            for (String c : conditions) sql.append(c).append(" ");
        }

        var conn = connect();
        var result = new ArrayList<>();

        try {
            var st = conn.createStatement();
            var rs = st.executeQuery(sql.toString());

            while(rs.next()) {
                for (var t : targets) result.add(rs.getObject(t)); }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); }

        return new SelectObject(table, targets, conditions, result.toArray());
    }

    public SelectObject select(String table, String[] targets) {
        return select(table, targets, null); }

    public HashMap<Integer, Legion> getLegions() {
        var res = select("legions", new String[]{
                "id",
                "name",
                "description",
                "icon",
                "leader",
                "members",
                "power",
                "core"
        }, null);

        var test = res.map(Legion.class);
    }
}
