package DataAccessLayer.DAOClasses;

import java.sql.*;
import java.util.ArrayList;

public abstract class DBDAO implements IDAO {

    private static Connection connection;
    private static final String databaseName = "scdAs3";
    private final String table;
    private final int noOfFields;
    private final String[] fields;

    protected DBDAO(String table, int noOfFields, String[] fields) {
        this.table = table;
        this.noOfFields = noOfFields;
        this.fields = fields;
    }

    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:sqlserver://<HOST_NAME>\\<INSTANCE_NAME>;"
                        + "databaseName=" + databaseName + ";"
                        + "integratedSecurity=false;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true";
                connection = DriverManager.getConnection(url, "<USERNAME>", "<PASSWORD>");
                System.out.println("Successfully Connected to Database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void setPreparedStatementValue(PreparedStatement stmt, int index, Object value) throws SQLException {
        if (value instanceof String) {
            stmt.setString(index, (String) value);
        } else if (value instanceof Integer) {
            stmt.setInt(index, (Integer) value);
        } else if (value instanceof Double) {
            stmt.setDouble(index, (Double) value);
        } else if (value instanceof java.sql.Date) {
            stmt.setDate(index, (java.sql.Date) value);
        } else if (value instanceof java.sql.Time) {
            stmt.setTime(index, (java.sql.Time) value);
        } else if (value instanceof Boolean) {
            stmt.setBoolean(index, (Boolean) value);
        } else {
            System.out.println(value.toString());
            throw new IllegalArgumentException("Unsupported data type at index " + index);
        }
    }

    private Object getResultSetValue(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex);
        if (value instanceof Integer) {
            return rs.getInt(columnIndex);
        } else if (value instanceof String) {
            return rs.getString(columnIndex);
        } else if (value instanceof Double) {
            return rs.getDouble(columnIndex);
        } else if (value instanceof java.sql.Date) {
            return rs.getDate(columnIndex);
        } else if (value instanceof java.sql.Time) {
            return rs.getTime(columnIndex);
        } else if (value instanceof Boolean) {
            return rs.getBoolean(columnIndex);
        }
        return value;
    }

    @Override
    public boolean insert(ArrayList<Object> data) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + table + " VALUES (");
        for (int i = 0; i < noOfFields; i++) {
            sql.append("?");
            if (i < noOfFields - 1) {
                sql.append(", ");
            }
        }
        sql.append(");");

        connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < noOfFields; i++) {
                setPreparedStatementValue(stmt, i + 1, data.get(i));
            }

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ArrayList<Object> data, Object id, String idName) {
        StringBuilder sql = new StringBuilder("UPDATE " + table + " SET ");
        for (int i = 0; i < noOfFields; i++) {
            sql.append(fields[i]).append(" = ?");
            if (i < noOfFields - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE "+idName+" = ?");

        connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < noOfFields; i++) {
                setPreparedStatementValue(stmt, i + 1, data.get(i));
            }
            setPreparedStatementValue(stmt, noOfFields + 1, id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Object id, String idName) {
        String sql = "DELETE FROM " + table + " WHERE "+idName+" = ?";

        connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setPreparedStatementValue(stmt, 1, id);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Object> load(Object id, String idName) {
        ArrayList<Object> record = new ArrayList<>();
        String sql = "SELECT * FROM " + table + " WHERE "+idName+" = ?";

        connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setPreparedStatementValue(stmt, 1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                for (int i = 1; i <= noOfFields; i++) {
                    record.add(getResultSetValue(rs, i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return record;
    }

    @Override
    public ArrayList<ArrayList<Object>> load() {
        ArrayList<ArrayList<Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM " + table;

        connection = getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ArrayList<Object> record = new ArrayList<>();
                for (int i = 1; i <= noOfFields; i++) {
                    record.add(getResultSetValue(rs, i));
                }
                records.add(record);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    @Override
    public int getRowCount() {
        String sql = "SELECT COUNT(*) FROM " + table;
        int rowCount = 0;

        connection = getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public static void clearDataBase() {
        String sql = "DELETE FROM Seminars;";  // Construct the SQL DELETE statement to remove all records

        connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            int rowsDeleted = stmt.executeUpdate(sql);  // Execute the DELETE query

            System.out.println(rowsDeleted + " rows have been deleted from the Seminars table.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while clearing the database.", e);
        }
    }


}
