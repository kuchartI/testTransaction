import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.ExecutionException;

public class ThreadTransactionTest {
    static final ConnectionBD connectionBD = new ConnectionBD();

    @Test
    public void serializableTest() throws ClassNotFoundException, InterruptedException, ExecutionException {
        String serializable = connectionBD.runThreads(100, Connection.TRANSACTION_SERIALIZABLE);
        DataWriter.write(serializable, "serializable");
    }

    @Test
    public void repeatableReadTest() throws ClassNotFoundException, InterruptedException, IOException, ExecutionException {
        String repeatableRead = connectionBD.runThreads(100, Connection.TRANSACTION_REPEATABLE_READ);
        DataWriter.write(repeatableRead, "repeatableRead");
    }

    @Test
    public void readCommittedTest() throws ClassNotFoundException, InterruptedException, ExecutionException {
        String readCommitted = connectionBD.runThreads(100, Connection.TRANSACTION_READ_COMMITTED);
        DataWriter.write(readCommitted, "readCommitted");
    }


}