package  com.example.newapp;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import java.util.List;

public class UserRepository {

    private final UserDao userDao;
    private final TransactionDao txDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context ctx) {
        AppDatabase db = AppDatabase.getInstance(ctx);
        userDao = db.userDao();
        txDao = db.transactionDao();

    }

    public User login(String email, String password) {
        return userDao.login(email, password);
    }

    public User getUser(int id) {
        return userDao.getUserById(id);
    }

    public long signUp(User user) {
        return userDao.insert(user);
    }


    public void sendMoney(
            int senderId,
            String receiverAccount,
            double amount,
            String note,
            ResultCallback callback

    ) {
        executor.execute(() -> {

            User sender = userDao.getUserById(senderId);
            User receiver = userDao.getUserByAccount(receiverAccount);

            if (sender == null) {
                callback.error("Sender not found");
                return;
            }

            if (receiver == null) {
                callback.error("Recipient not found");
                return;
            }

            if (sender.balance < amount) {
                callback.error("Insufficient balance");
                return;
            }


            userDao.updateBalance(sender.id, sender.balance - amount);
            userDao.updateBalance(receiver.id, receiver.balance + amount);


            txDao.insert(new Transaction(sender.id,sender.matricno, "SEND", amount, receiver.matricno,note ));
            txDao.insert(new Transaction(receiver.id, receiver.matricno, "RECEIVE", amount, sender.matricno, note));

            callback.success();
        });
    }

    public interface ResultCallback {
        void success();
        void error(String msg);
    }


    public void insertTransaction(Transaction tx) {
        txDao.insert(tx);
    }

    public List<Transaction> getTransactions(int userId) {
        return txDao.getUserTransactions(userId);
    }


}
