import java.util.*;

class DNSEntry{
    String ip;
    long expiry;

    DNSEntry(String ip,long expiry){
        this.ip=ip;
        this.expiry=expiry;
    }
}

public class solution {

    static HashMap<String,DNSEntry> cache = new HashMap<>();

    static String resolve(String domain){

        long now = System.currentTimeMillis();

        if(cache.containsKey(domain)){

            DNSEntry entry = cache.get(domain);

            if(entry.expiry > now){
                System.out.println("Cache HIT");
                return entry.ip;
            }
            else{
                System.out.println("Cache EXPIRED");
                cache.remove(domain);
            }
        }

        System.out.println("Cache MISS");

        String ip="172.217.14.206";

        cache.put(domain,new DNSEntry(ip, now + 300000));

        return ip;
    }

    public static void main(String[] args){

        System.out.println(resolve("google.com"));
        System.out.println(resolve("google.com"));
    }
}