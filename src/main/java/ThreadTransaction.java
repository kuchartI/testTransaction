import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadTransaction {

    public String select(Connection connection, int count) {
        String SQL1 = "SELECT * FROM pizza";
        StringBuilder selectData = new StringBuilder();
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(SQL1);
            } catch (SQLException e) {
                System.out.println("GG");
            }
            long end = System.nanoTime();
            selectData.append(" SELECT ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return selectData.toString();
    }

    public String update(Connection connection, int count) {
        StringBuilder updateData = new StringBuilder();
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                        "UPDATE pizza set name = 'Ntasdjakdasd123llSJsk' where id = " + (i + 10));
            } catch (SQLException e) {
                System.out.println("GG");
            }
            long end = System.nanoTime();
            updateData.append(" UPDATE ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return updateData.toString();
    }

    public String insert(Connection connection, int count) {
        String SQL3 =
                "INSERT INTO pizza (name, description, weight, price, size, category_id)" +
                        " VALUES ('Тест1','Тест1', 385, 33.3, 30, 2)";
        StringBuilder updateData = new StringBuilder();
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            try {
                PreparedStatement statement = connection.prepareStatement(SQL3);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("GG");
            }
            long end = System.nanoTime();
            updateData.append(" INSERT ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return updateData.toString();
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
