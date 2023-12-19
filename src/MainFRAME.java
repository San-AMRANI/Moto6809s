import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
public class MainFRAME {

    private static UAL ual = new UAL();
    private static Instruction Inst = new Instruction();
    private static File originalFile; // Stocker le fichier original

    static JTextArea textArea = new JTextArea();
    static MemoryROMDisplay memoryROMDisplay;
    static MemoryRAMDisplay memoryRAMDisplay;

    //static ProcesseurGUI RGUAL = new ProcesseurGUI();
    static JPanel panel = new JPanel();

    static volatile boolean stopReading;



    public static void mainwindow(){
        JFrame frame = new JFrame("MOTO 6809 S");

        //JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLACK);

        JButton saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalFile != null ) {
                    saveToFile(textArea.getText(), originalFile);
                } else {
                    createAndSaveFile(textArea);
                }
            }
        });

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalFile != null) {

                    Execute.printFileContent(originalFile);

                } else {
                    JOptionPane.showMessageDialog(null, "Aucun fichier n'est chargé ou creé!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton programmeButton = new JButton("Programme");
        programmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (programFrame != null){
                    programFrame.dispose();
                }
                showMessage( textArea.getText());
            }
        });

        JButton loadButton = new JButton("Load File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile(textArea);
            }
        });


        JButton newFileButton = new JButton("New File");
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Efface le contenu actuel
                originalFile = null; // Réinitialise le fichier original
            }
        });

        JButton ROM = new JButton("ROM");
        ROM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open another window when the button is clicked
                if (memoryROMDisplay != null){
                    memoryROMDisplay.dispose();
                }
                memoryROMDisplay = new  MemoryROMDisplay();
            }
        });

        JButton RAM = new JButton("RAM");
        RAM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open another window when the button is clicked
                if (memoryRAMDisplay != null){
                    memoryRAMDisplay.dispose();
                }
                memoryRAMDisplay = new  MemoryRAMDisplay();
            }
        });

        PasPasButton pasPasButton = new PasPasButton("Pas à Pas");

        ResetButton resetButton = new ResetButton("Reset", pasPasButton);


        panel.setLayout(null);

        loadButton.setBounds(10, 5, 100, 30);
        newFileButton.setBounds(120, 5, 100, 30);
        saveButton.setBounds(230, 5, 100, 30);
        executeButton.setBounds(340, 5, 100, 30);
        programmeButton.setBounds(450, 5, 100, 30);
        ROM.setBounds(10, 40, 100, 30);
        RAM.setBounds(120, 40, 100, 30);
        pasPasButton.setBounds(230, 40, 100, 30);
        resetButton.setBounds(340, 40, 100, 30);

        panel.add(loadButton);
        panel.add(newFileButton); // Ajoute le bouton pour créer un nouveau fichier
        panel.add(saveButton);
        panel.add(executeButton);
        //panel.add(PasPas);
        panel.add(programmeButton);
        panel.add(ROM); // Add the button to open another window
        panel.add(RAM);
        panel.add(pasPasButton);
        panel.add(resetButton);


        //registerDisplay.setVisible(true);


        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new RegisterDisplay(), panel);
        leftSplitPane.setResizeWeight(0.83);
        leftSplitPane.setEnabled(false);

        Font textFieldFont = new Font(Font.SERIF, Font.LAYOUT_NO_LIMIT_CONTEXT, 15);
        textArea.setFont(textFieldFont);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane, scrollPane);
        mainSplitPane.setResizeWeight(0.75);
        mainSplitPane.setEnabled(false);

        ImageIcon mticon = new ImageIcon("res/pic/mymoto6809Sicon.png");
        frame.setIconImage(mticon.getImage());

        frame.add(mainSplitPane);
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void saveToFile(String content, File file) {
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Aucun fichier n'est chargé !", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    static JFrame programFrame ;
    private static void showMessage(String message) {
        programFrame = new JFrame("Programme");
        JTextArea programArea = new JTextArea(message);
        programArea.setEditable(false);
        JScrollPane programScrollPane = new JScrollPane(programArea);



        programFrame.add(programScrollPane);
        programFrame.setSize(170, 300);

        programFrame.setLocation(1250, 100);
        programFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        programFrame.setResizable(false);
        programFrame.setUndecorated(true);
        programScrollPane.setBorder(BorderFactory.createTitledBorder("Programme"));
        programFrame.setVisible(true);


    }

    private static File loadFile(JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            originalFile = file; // Stockage du fichier original
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                if (MainFRAME.programFrame != null){
                    programFrame.dispose();
                }

                //showMessage(content.toString());
                textArea.setText(content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }
    private static void createAndSaveFile(JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            originalFile = file; // Stocke le nouveau fichier comme fichier original
            saveToFile(textArea.getText(), file);
        }
    }
    public static class Execute {
        public static void printFileContent(File file) {

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                ual.Torom.clear();
                stopReading = false;

                while ((line = reader.readLine()) != null && !stopReading) {


                    if (MainFRAME.programFrame != null) {
                        programFrame.dispose();
                    }
                    showMessage(textArea.getText());
                    saveToFile(textArea.getText(), originalFile);

                    if (line.length() != 0) {
                        if (line.contains("ORG")) {
                            if (line.contains("$")) {
                                if (line.substring(5).length() > 2 && line.substring(5).length() <= 4) {
                                    Rom.startAddress = line.substring(5);
                                } else {
                                    JOptionPane.showMessageDialog(null, "l'addres de depart est en de 2octes!!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                                    Instruction.SWI();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "ORG besion de specifier l'addres de depart", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                                Instruction.SWI();
                            }
                        } else{
                            ual.Traitement(line);
                            RegisterDisplay.setflag(Flag.displayFlags());
                        }
                    }

                    if (memoryROMDisplay != null) {
                        memoryROMDisplay.dispose();
                    }
                    if (memoryRAMDisplay != null) {
                        memoryRAMDisplay.dispose();
                    }

                }
                reader.close();
                Rom.startAddress = "AC00";//Reinitialiser le depart de l'execution suivant

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static class PasPasButton extends JButton {
        private AtomicBoolean stopReading = new AtomicBoolean(false);
        private BufferedReader reader;
        private String currentLine;

        public PasPasButton(String buttonText) {
            super(buttonText);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (originalFile != null && !stopReading.get()) {
                        try {
                            if (reader == null) {
                                reader = new BufferedReader(new FileReader(originalFile));
                                ual.Torom.clear();
                                Rom.startAddress = "AC00"; // Initialize start address
                            }

                            currentLine = reader.readLine();

                            if (currentLine != null) {
                                // Your logic here to process the line
                                if (currentLine.length() != 0) {
                                    if (currentLine.contains("ORG")) {
                                        if (currentLine.contains("$")) {
                                            if (currentLine.substring(5).length() > 2 && currentLine.substring(5).length() <= 4) {
                                                Rom.startAddress = currentLine.substring(5);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "l'adresse de départ est en de 2 octets!!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                                                Instruction.SWI();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "ORG besoin de spécifier l'adresse de départ", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                                            Instruction.SWI();
                                        }
                                    } else{
                                        ual.Traitement(currentLine);
                                        RegisterDisplay.setflag(Flag.displayFlags());
                                    }
                                }
                                if (memoryROMDisplay != null) {
                                    memoryROMDisplay.dispose();
                                }
                                if (memoryRAMDisplay != null) {
                                    memoryRAMDisplay.dispose();
                                }
                                memoryROMDisplay = new MemoryROMDisplay();
                                memoryRAMDisplay = new MemoryRAMDisplay();
                            } else {
                                reader.close();
                                Rom.startAddress = "AC00"; // Reset the start address for next execution
                                MemoryRAMDisplay.fromual.clear();
                                JOptionPane.showMessageDialog(null, "End of file", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                                stopReading.set(true); // Stop further reading after reaching the end of the file
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }
    }
    public static class ResetButton extends JButton {
        private PasPasButton pasPasButton;

        public ResetButton(String buttonText, PasPasButton pasPasButton) {
            super(buttonText);
            this.pasPasButton = pasPasButton;

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pasPasButton.stopReading.set(false); // activate the "Pas à Pas" button process
                    pasPasButton.reader = null; // Reset the reader
                    pasPasButton.currentLine = null; // Reset the current line
                    Inst.LDD("00");
                    Inst.LDS("0000");
                    Inst.LDU("0000");
                    Inst.LDX("0000");
                    Inst.LDY("0000");
                    Flag.setAllFlagsFalse();
                    MemoryRAMDisplay.fromual.clear();
                    ual.Torom.clear();
                    RegisterDisplay.setflag(Flag.displayFlags());
                    RegisterDisplay.setPC(Rom.startAddress);
                    RegisterDisplay.setline(null);

                    // You can perform any other necessary reset operations here
                }
            });
        }
    }


}