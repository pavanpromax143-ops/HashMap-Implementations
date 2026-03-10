import java.util.*;

public class solution {

    static HashMap<String,Integer> stock = new HashMap<>();
    static Queue<Integer> waitingList = new LinkedList<>();

    static void checkStock(String product){
        System.out.println("Stock: "+stock.get(product));
    }

    static void purchaseItem(String product,int userId){

        if(stock.get(product)>0){
            stock.put(product, stock.get(product)-1);
            System.out.println("Purchase successful. Remaining: "+stock.get(product));
        }
        else{
            waitingList.add(userId);
            System.out.println("Out of stock. Added to waiting list position "+waitingList.size());
        }
    }

    public static void main(String[] args){

        stock.put("IPHONE15_256GB",100);

        checkStock("IPHONE15_256GB");

        purchaseItem("IPHONE15_256GB",12345);
        purchaseItem("IPHONE15_256GB",67890);
    }
}