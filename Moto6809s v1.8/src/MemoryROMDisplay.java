import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class MemoryROMDisplay extends JFrame {
    private JTextArea displayArea;
    private Map<String, String> memoryMap;

    public MemoryROMDisplay() {
        this.memoryMap = Rom.getROMdata();
        initializeUI();
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

        updateDisplay();

        displayArea.setCaretPosition(0);//display the top

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(scrollToTopButton, BorderLayout.SOUTH);


        add(panel);

        setSize(150,300);
        setResizable(false);
        setLocation(850, 100);
        setUndecorated(true);
        panel.setBorder(BorderFactory.createTitledBorder("ROM"));

        //setLocationRelativeTo(null);
        setVisible(true);

    }

    public void updateDisplay() {
        displayArea.setText(""); // Clear previous content
        for (Map.Entry<String, String> entry : sortHashMapByKey(memoryMap).entrySet()) {
            displayArea.append( "      "+ entry.getKey() + " -  " + entry.getValue() + "\n");
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
