import com.itss.utilities.APIClient;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */
public class Main {
    public static void main (String[] args) {
        try {
            System.out.println(APIClient.get("http://localhost:3000/db", new HashMap<>()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
