import com.itss.Boundary.BookCopyRegistrationForm;
import com.itss.Boundary.BookRegistrationForm;
import com.itss.Boundary.MainWindow;
import com.itss.utilities.APIClient;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by HarDToBelieve on 10/17/2017.
 */
public class Main {
    public static void main (String[] args) {
        MainWindow mw = new MainWindow("librarian");
        mw.pack();
        mw.setVisible(true);
    }
}
