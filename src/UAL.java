import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class UAL {
    private static Registre R = new Registre();
    private static Instruction Inst = new Instruction();


    ArrayList<String> Torom = new ArrayList<>();

    private static int cpt=0;

    static void verifierhex(String input){
        // Ensure the input string is either 2 or 4 characters long
        if (input.length() == 0 && input.length() > 4) {
            JOptionPane.showMessageDialog(null, "verifier le nombre d'octer!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
            Instruction.SWI();
        }

        // Check if each character is a valid hexadecimal digit (0-9, A-F, or a-f)
        for (char c : input.toCharArray()) {
            if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F'))) {
                JOptionPane.showMessageDialog(null, "verifier s le nombre est en hexadecimale!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                Instruction.SWI();
            }
        }
    }


    public void Traitement(String line) {

        try {

            Integer adpc = Integer.parseInt(Rom.startAddress, 16);
            R.setline(line);//la line courant //ajouter PC pour laddress le la ligne suivant!!!!!!!!!!
            RegisterDisplay.setline(R.getline());
            String[] words = new String[2];

            if (line.contains("$")) {
                words = line.toUpperCase().split(" ");
            } else {
                words[0] = line;
                words[1] = null;

            }
            if (words[0].equalsIgnoreCase("PSUS")) {
                Inst.PSHS(words[1].trim());
            }

            if (Instruction.codeOpDATA(words[0]).length() <= 2) {
                Torom.add(Instruction.codeOpDATA(words[0]));

            } else if (Instruction.codeOpDATA(words[0]).length() > 2) {
                Torom.add(Instruction.codeOpDATA(words[0]).substring(0, 2));
                Torom.add(Instruction.codeOpDATA(words[0]).substring(2, 4));
                //envoi le code operatoire de l'instruction au rom apres diveser en 1 oct
            }
            // passer la valeur saisi au rom
            //verifier le octe
            if (words[1] != null && words[1].substring(0, 1).equalsIgnoreCase("#")) {//immediat
                if (words[1].substring(2).length() <= 2) {
                    //envoi le menu au rom
                    Torom.add(words[1].substring(2));

                } else if (words[1].substring(2).length() > 2 && words[1].substring(2).length() <= 4) {
                    Torom.add(words[1].substring(2, 4));
                    Torom.add(words[1].substring(4, 6));

                } else {
                    JOptionPane.showMessageDialog(null, "tu peut pas entrer plus que 2 octes", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                    Instruction.SWI();
                }
                //cd d'accepter l'instruction
                String menu = words[1].substring(2); //word1= #$0000, menu= 0000
                //verification du hexadecimale
                verifierhex(menu);

                //realisation de commande et update du registre afficher
                switch (words[0]) {
                    case "LDD":
                        Inst.LDD(menu);

                        break;
                    case "LDA":
                        Inst.LDA(menu);
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);

                        break;
                    case "LDB":
                        Inst.LDB(menu);
                        break;
                    case "LDX":
                        Inst.LDX(menu);
                        break;
                    case "LDY":
                        Inst.LDY(menu);
                        break;
                    case "LDS":
                        Inst.LDS(menu);
                        break;
                    case "LDU":
                        Inst.LDU(menu);
                        break;
                    case "ADDA":
                        Inst.ADDA(menu);
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        break;
                    case "ANDA":
                        Inst.ANDA(menu);
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        break;
                    case "ADDB":
                        Inst.ADDB(menu);
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "ANDB":
                        Inst.ANDB(menu);
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "ORA":
                        Inst.ORA(menu);
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        break;
                    case "ORB":
                        Inst.ORB(menu);
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "EORA":
                        Inst.EORA(menu);
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        break;
                    case "EORB":
                        Inst.EORB(menu);
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "BITA":
                        Inst.BITA(menu);
                        break;
                    case "BITB":
                        Inst.BITB(menu);
                        break;
                    case "SUBA":
                        Inst.SUBA(menu);
                        break;
                    case "SUBB":
                        Inst.SUBB(menu);
                        break;
                    case "SUBD":
                        Inst.SUBD(menu);
                        break;
                    case "CMPA":
                        Inst.CMPA(menu);
                        break;
                    case "CMPB":
                        Inst.CMPB(menu);
                        break;
                    case "CMPX":
                        Inst.CMPX(menu);
                        break;
                    case "CMPY":
                        Inst.CMPY(menu);
                        break;
                    case "CMPS":
                        Inst.CMPS(menu);
                        break;
                    case "CMPU":
                        Inst.CMPU(menu);
                        break;
                    case "PSHS"://empiler vl par val
                        Inst.PSHS(words[1].substring(1).trim());
                        break;
                    case "PULS"://empiler vl par val
                        Inst.PULS(words[1].substring(1).trim());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "L'instruction est pas encore ajouter! \n(imediat '#')", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                        Instruction.SWI();

                        break;
                }

            } else if (words[1] != null && words[1].substring(0, 1).equalsIgnoreCase(">")) {
                String adrs = words[1].substring(2);

                if (adrs.length() != 4) {
                    JOptionPane.showMessageDialog(null, "Verifier le nombre d'octes!!\n(Etendut '>' )", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                    Instruction.SWI();
                }
                if (words[1].substring(2).length() <= 2) {
                    //envoi le menu au rom
                    Torom.add(words[1].substring(2));

                } else if (words[1].substring(2).length() > 2 && words[1].substring(2).length() <= 4) {
                    Torom.add(words[1].substring(2, 4));
                    Torom.add(words[1].substring(4, 6));

                } else {
                    JOptionPane.showMessageDialog(null, "tu peut pas entrer plus que 2 octes", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                    Instruction.SWI();
                }

                switch (words[0]) {
                    case "STA":
                        Inst.STA(adrs);
                        break;
                    case "STB":
                        Inst.STB(adrs);
                        break;
                    case "STX":
                        Inst.STX(adrs);
                        break;
                    case "STY":
                        Inst.STY(adrs);
                        break;
                    case "STS":
                        Inst.STS(adrs);
                        break;
                    case "STU":
                        Inst.STU(adrs);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "L'instruction est pas encore Ajouter! \n(Etendut)", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                        break;
                }

            } else if (words[1] == null) {
                //inherant, pas de menu, seulment l'instrution

                switch (words[0]) {
                    case "CLRA":
                        Inst.CLRA();
                        Flag.setZeroFlag(true);
                        break;
                    case "CLRB":
                        Inst.CLRB();
                        break;
                    case "INCA":
                        Inst.INCA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "DECA":
                        Inst.DECA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "INCB":
                        Inst.INCB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "DECB":
                        Inst.DECB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "COMA":
                        Inst.COMA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "COMB":
                        Inst.COMB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "LSLB":
                        Inst.LSLB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "LSRB":
                        Inst.LSRB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        break;
                    case "LSLA":
                        Inst.LSLA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "LSRA":
                        Inst.LSRA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "RORA":
                        Inst.RORA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "ROLA":
                        Inst.ROLA();
                        RegisterDisplay.setA(reforme(Registre.getAccumulatorA()));
                        if (Registre.getAccumulatorA().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "ROLB":
                        Inst.ROLB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "RORB":
                        Inst.RORB();
                        RegisterDisplay.setB(reforme(Registre.getAccumulatorB()));
                        if (Registre.getAccumulatorB().equalsIgnoreCase("00")) {
                            Flag.setZeroFlag(true);
                        } else Flag.setZeroFlag(false);
                        break;
                    case "SWI":
                        Instruction.SWI();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "L'instruction n'existe pas! \n(inherant)", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                        Instruction.SWI();
                        break;
                }


            } else {
                JOptionPane.showMessageDialog(null, "ce mode d'addressage n'éxiste pas ou pas encore mettre à jour!!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
                Instruction.SWI();
            }

            RegisterDisplay.setPC(String.format("%04X", (adpc + Torom.size())));
            Sendromddata(Torom);
        }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Somethings Went Wrong!!\n(Syntax \"input values\")", "ERROr!!!", JOptionPane.ERROR_MESSAGE);
            Instruction.SWI();
         }

    }

    public static String reforme(String input) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            builder.append(input.charAt(i)).append(" ");
        }

        return builder.toString().trim(); // Trim removes the trailing space at the end
    }

    private static void Sendromddata(ArrayList torom){
        SwingUtilities.invokeLater(() -> {
            Rom.word = torom;
        });
    }
    /*private static void Sendramddata(ArrayList toram){
        SwingUtilities.invokeLater(() -> {
            Ram.word = toram;
        });
    }

     */
}
