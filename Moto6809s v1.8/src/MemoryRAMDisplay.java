import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class MemoryRAMDisplay extends JFrame {
    private static JTextArea displayArea;

    public MemoryRAMDisplay() {
        initializeUI();
    }
    static HashMap<String, String> fromual = new HashMap<>();
    static HashMap<String, String> RamInitial(){
        HashMap<String, String> Ramdatainitial = new HashMap<String, String>();

        String startAddress = "0000";
        String endAddress = "03FF";

        int SD = Integer.parseInt(startAddress, 16);
        int ED = Integer.parseInt(endAddress, 16);

        for (int i = SD; i <= ED; i++) {
            String address = String.format("%04X", i); // Formater l'adresse en hexadécimal sur 4 chiffres
            Ramdatainitial.put(address, "00");
            // Incrémenter la valeur hexadécimale
        }
        return Ramdatainitial;
    }

    private void initializeUI() {
        setTitle("ROM");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        displayArea = new JTextArea(20, 10);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton scrollToTopButton = new JButton("Go to Top");
        scrollToTopButton.addActionListener(e -> {
            JViewport viewport = scrollPane.getViewport();
            Rectangle viewRect = viewport.getViewRect();
            viewRect.y = 0; // Set the view position to the top
            viewport.setViewPosition(viewRect.getLocation());
        });

        MemoryRAMDisplay.updateDisplay(RamInitial(),fromual);
        displayArea.setCaretPosition(0);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(scrollToTopButton, BorderLayout.SOUTH);


        add(panel);

        setSize(150,300);
        setResizable(false);
        setLocation(1050, 100);
        setUndecorated(true);
        panel.setBorder(BorderFactory.createTitledBorder("RAM"));

        //setLocationRelativeTo(null);
        setVisible(true);

    }
    public static void updateDisplay(HashMap<String, String> initialRam, HashMap<String, String> updateRam) {

        for (Map.Entry<String, String> entry : updateRam.entrySet()) {
            String address = entry.getKey();
            String value = entry.getValue();


            if (initialRam.containsKey(address)) {
                initialRam.replace(address, value);
            }
        }
        displayArea.setText(""); // Clear previous content
        for (Map.Entry<String, String> entry : sortHashMapByKey(initialRam).entrySet()) {
            displayArea.append( "      "+ entry.getKey() + "   -   " + entry.getValue() + "\n");
        }
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortHashMapByKey(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        // Trier la liste par clé
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));

        // Recréer une LinkedHashMap à partir de la liste triée pour conserver l'ordre
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
