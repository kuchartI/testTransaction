import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;

public class ConnectionBD {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/network_of_pizzerias_db";
    static final String USER = "postgres";
    static final String PASS = "12345";
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Pizza-%d")
            .setDaemon(true)
            .build();

    public String runThreads(int count, int connectionTransaction) throws ClassNotFoundException, InterruptedException, ExecutionException {
        Class.forName("org.postgresql.Driver");
        StringBuilder stringBuilder = new StringBuilder();
        Connection connectionSelect = null;
        Connection connectionUpdate = null;
        Connection connectionInsert = null;
        try {
            connectionSelect = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            connectionUpdate = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            connectionInsert = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            connectionSelect.setTransactionIsolation(connectionTransaction);
            connectionUpdate.setTransactionIsolation(connectionTransaction);
            connectionInsert.setTransactionIsolation(connectionTransaction);
        } catch (SQLException e) {
            System.out.println("Sql connection exception!");
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);

        final ThreadTransaction threadTransaction = new ThreadTransaction();

        ExecutorService executorService = Executors.newFixedThreadPool(3, threadFactory);
        Connection finalConnectionSelect = connectionSelect;
        Future<String> s1 = executorService.submit(() -> {
            String select = threadTransaction.select(finalConnectionSelect, count);
            countDownLatch.countDown();
            return select;
        });
        Connection finalConnectionUpdate = connectionUpdate;
        Future<String> s2 = executorService.submit(() -> {
            String update = threadTransaction.update(finalConnectionUpdate, count);
            countDownLatch.countDown();
            return update;
        });
        Connection finalConnectionInsert = connectionInsert;
        Future<String> s3 = executorService.submit(() -> {
            String insert = threadTransaction.insert(finalConnectionInsert, count);
            countDownLatch.countDown();
            return insert;
        });
        executorService.shutdown();
        countDownLatch.await();
        return stringBuilder.append(s1.get()).append(s2.get()).append(s3.get()).toString();
    }
}
