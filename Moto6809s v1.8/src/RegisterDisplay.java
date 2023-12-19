import javax.swing.*;
import java.awt.*;

public class RegisterDisplay extends JPanel {
    Flag flag = new Flag();

    private static JTextField pcTextField=new JTextField();
    private static JTextField lineTextField=new JTextField();
    private static JTextField reTextField=new JTextField();
    private static JTextField aTextField=new JTextField();
    private static JTextField bTextField=new JTextField();
    private static JTextField dTextField=new JTextField();
    private static JTextField dpTextField=new JTextField();
    private static JTextField sTextField=new JTextField();
    private static JTextField xTextField=new JTextField();
    private static JTextField uTextField=new JTextField();
    private static JTextField yTextField=new JTextField();

    Registre R = new Registre();
    public RegisterDisplay(){


        registerpanel();
    }
    public void registerpanel() {

        setSize(600, 450);
        setLayout(null); // Using absolute positioning

        setBackground(new Color(66, 133, 244)); // Set background color to #4285f4ff

        ImageIcon icon = new ImageIcon("res/pic/UALFIGURE.png"); // Replace with your icon path
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(85, 60, 300, 200); // Positioning the icon with specific pixel values
        add(iconLabel);

        addLabel("PC", 7, 30);
        addLabel("Flag", 125, 70);
        addLabel("A", 50, 125);
        addLabel("B", 50, 190);
        addLabel("D", 10, 230);
        addLabel("DP", 8, 270);
        addLabel("S", 10, 350);
        addLabel("X", 10, 310);
        addLabel("U", 160, 350);
        addLabel("Y", 160, 310);

        // Flag letters: E F H I N Z V C
        addLabel("E", 163, 45);
        addLabel("F", 188, 45);
        addLabel("H", 210, 45);
        addLabel("I", 235, 45);
        addLabel("N", 256, 45);
        addLabel("Z", 280, 45);
        addLabel("V", 303, 45);
        addLabel("C", 326, 45);




        pcTextField.setBounds(35, 30, 80, 30);
        pcTextField.setEditable(false);
        add(pcTextField);


        lineTextField.setBounds(370, 30, 200, 30);
        add(lineTextField);

        reTextField.setBounds(160, 70, 200, 30);
        add(reTextField);

        aTextField.setBounds(70, 125, 50, 30);
        add(aTextField);

        bTextField.setBounds(70, 190, 50, 30);
        add(bTextField);

        dTextField.setBounds(30, 230, 80, 30);
        add(dTextField);

        dpTextField.setBounds(30, 270, 80, 30);
        add(dpTextField);

        sTextField.setBounds(30, 350, 80, 30);
        add(sTextField);

        xTextField.setBounds(30, 310, 80, 30);
        add(xTextField);

        uTextField.setBounds(175, 350, 80, 30);
        add(uTextField);

        yTextField.setBounds(175, 310, 80, 30);
        add(yTextField);

        lineTextField.setEditable(false);
        reTextField.setEditable(false);
        aTextField.setEditable(false);
        bTextField.setEditable(false);
        dTextField.setEditable(false);
        dpTextField.setEditable(false);
        sTextField.setEditable(false);
        xTextField.setEditable(false);
        uTextField.setEditable(false);
        yTextField.setEditable(false);

        Font textFieldFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);

        pcTextField.setFont(textFieldFont);
        lineTextField.setFont(textFieldFont);
        reTextField.setFont(textFieldFont);
        aTextField.setFont(textFieldFont);
        bTextField.setFont(textFieldFont);
        dTextField.setFont(textFieldFont);
        dpTextField.setFont(textFieldFont);
        sTextField.setFont(textFieldFont);
        xTextField.setFont(textFieldFont);
        uTextField.setFont(textFieldFont);
        yTextField.setFont(textFieldFont);

        pcTextField.setText("A C 0 0");
        lineTextField.setText(null);
        reTextField.setText(flag.displayFlags());
        aTextField.setText("0 0");
        bTextField.setText("0 0");
        dTextField.setText("0 0 0 0");
        dpTextField.setText("0 0 0 0");
        sTextField.setText("0 0 0 0");
        xTextField.setText("0 0 0 0");
        uTextField.setText("0 0 0 0");
        yTextField.setText("0 0 0 0");

        ImageIcon icon1 = new ImageIcon("res/pic/mymoto6809Sicon.png");
        Image img = icon1.getImage();
        Image resizedImage = img.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel pic = new JLabel(resizedIcon);

        pic.setLayout(null);
        pic.setBounds(450, 300, 150,150);
        pic.setVisible(true);


        add(pic);

        setBackground(new Color(66, 133, 244)); // Set background color to #4285f4ff

        add(iconLabel);
    }

    // Method to add a label and a text field together with specific pixel positions
    private void addLabel(String labelText, int x, int y) {
        JLabel label = new JLabel(labelText);
        Font textFieldFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        label.setForeground(Color.WHITE); // Set label text color to white
        label.setFont(textFieldFont);
        label.setBounds(x, y, 80, 30); // Assuming 80x30 size for labels
        add(label);
    }
    public static void setPC(String PC ) {
        pcTextField.setText(PC);
    }

    public static void setline(String line) {
        lineTextField.setText(line);
    }

    public static void setflag( String flag) {
        reTextField.setText(flag);
    }

    public static void setA( String A) {
        aTextField.setText(A);
    }

    public static void setB( String B) {
        bTextField.setText(B);
    }

    public static void setD(String D) {

        dTextField.setText(D);

    }

    public static void setS(  String S) {
        sTextField.setText(S);
    }

    public static void setX(  String X) {
        xTextField.setText(X);
    }

    public static void setU(String U) {
        uTextField.setText(U);
    }

    public static void setY( String Y) {
        yTextField.setText(Y);
    }

}
