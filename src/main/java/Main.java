import com.itss.Boundary.BookRegistrationForm;
import com.itss.utilities.APIClient;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */
public class Main {
    public static void main (String[] args) {
        BookRegistrationForm brf = new BookRegistrationForm();
        brf.pack();
        brf.setVisible(true);
    }
}
