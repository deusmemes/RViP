import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var logic = new Logic();

//        logic.print();
//        logic.runSync();
        logic.runAsync();
    }
}
