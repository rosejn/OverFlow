import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextFieldExample3 {

  Display d;

  Shell s;

  TextFieldExample3() {
    d = new Display();
    s = new Shell(d);
    s.setSize(250, 250);
    s.setText("A Text Field Example");
    Text text1 = new Text(s, SWT.MULTI | SWT.BORDER);
    text1.setBounds(0, 0, 250, 250);
    s.open();
    while (!s.isDisposed()) {
      if (!d.readAndDispatch())
        d.sleep();
    }
    d.dispose();
  }

  public static void main(String[] arg) {
    new TextFieldExample3();
  }

}
 