import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String time;

    Transaction(int id, int amount, String merchant, String time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.time = time;
    }
}

public class TwoSum {

    // Classic Two Sum
    static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction t2 = map.get(complement);

                System.out.println("findTwoSum(target=" + target + ") → [(id:" +
                        t2.id + ", id:" + t.id + ")] // "
                        + t2.amount + " + " + t.amount);
                return;
            }

            map.put(t.amount, t);
        }
    }

    // Detect duplicate payments
    static void detectDuplicates(List<Transaction> transactions) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                System.out.print("detectDuplicates() → {amount:" +
                        list.get(0).amount +
                        ", merchant:\"" + list.get(0).merchant +
                        "\", accounts:[");

                for (Transaction t : list) {
                    System.out.print("id" + t.id + " ");
                }

                System.out.println("]}");
            }
        }
    }

    // K-Sum (example: 3 transactions)
    static void findKSum(List<Transaction> transactions, int k, int target) {

        int n = transactions.size();

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                for (int l = j + 1; l < n; l++) {

                    int sum = transactions.get(i).amount +
                            transactions.get(j).amount +
                            transactions.get(l).amount;

                    if (sum == target) {

                        System.out.println("findKSum(k=3,target=" + target +
                                ") → [(id:" + transactions.get(i).id +
                                ", id:" + transactions.get(j).id +
                                ", id:" + transactions.get(l).id +
                                ")] // " + transactions.get(i).amount +
                                "+" + transactions.get(j).amount +
                                "+" + transactions.get(l).amount);

                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "Store A", "10:00"));
        transactions.add(new Transaction(2, 300, "Store B", "10:15"));
        transactions.add(new Transaction(3, 200, "Store C", "10:30"));
        transactions.add(new Transaction(4, 500, "Store A", "11:00"));

        findTwoSum(transactions, 500);

        detectDuplicates(transactions);

        findKSum(transactions, 3, 1000);
    }
}
