
import java.util.HashMap;
import javax.swing.*;

public class Instruction {
    Registre R = new Registre();
    static HashMap<String, String> codeOpcmd = new HashMap<>();
    Flag flag= new Flag();


    //cmd transfert de donne
    Instruction(){
        //tratement
        codeOpcmd.put("LDA", "86");
        codeOpcmd.put("LDB", "C6");
        codeOpcmd.put("LDD", "CC");
        codeOpcmd.put("LDS", "10CE");
        codeOpcmd.put("LDY", "108E");
        codeOpcmd.put("LDX", "8E");
        codeOpcmd.put("LDU", "CE");

        codeOpcmd.put("STA", "B7");
        codeOpcmd.put("STB", "F7");
        codeOpcmd.put("STD", "FD");
        codeOpcmd.put("STS", "10FF");
        codeOpcmd.put("STU", "FF");
        codeOpcmd.put("STX", "BF");
        codeOpcmd.put("STY", "10B7");

        //cmmd operation
        codeOpcmd.put("ADCA", "89");
        codeOpcmd.put("ADCB", "C9");
        codeOpcmd.put("ADDA", "8B");
        codeOpcmd.put("ADDB", "C8");
        codeOpcmd.put("ADDD", "83");
        codeOpcmd.put("SUBA", "80");
        codeOpcmd.put("SUBB", "C0");
        codeOpcmd.put("SUBD", "83");

        codeOpcmd.put("ANDA", "84");
        codeOpcmd.put("ANDB", "C4");
        codeOpcmd.put("BITA", "85");
        codeOpcmd.put("BITB", "C5");
        codeOpcmd.put("EORA", "88");
        codeOpcmd.put("EORB", "C8");
        codeOpcmd.put("ORA", "8A");
        codeOpcmd.put("ORB", "CA");
        codeOpcmd.put("CMPA", "81");
        codeOpcmd.put("CMPB", "C1");
        codeOpcmd.put("CMPD", "1083");
        codeOpcmd.put("CMPS", "118C");
        codeOpcmd.put("CMPU", "1183");
        codeOpcmd.put("CMPX", "8C");
        codeOpcmd.put("CMPY", "108C");
        codeOpcmd.put("PSHS", "34");
        codeOpcmd.put("PULS", "35");


        //inherant
        codeOpcmd.put("CLRA", "4F");
        codeOpcmd.put("CLRB", "5F");

        codeOpcmd.put("COMA", "43");
        codeOpcmd.put("COMB", "53");

        codeOpcmd.put("INCA", "4C");
        codeOpcmd.put("DECA", "4A");
        codeOpcmd.put("INCB", "5C");
        codeOpcmd.put("DECB", "5A");

        codeOpcmd.put("ROLA", "49");
        codeOpcmd.put("ROLB", "59");
        codeOpcmd.put("RORA", "46");
        codeOpcmd.put("RORB", "56");
        codeOpcmd.put("LSLA", "48");
        codeOpcmd.put("LSLB", "58");
        codeOpcmd.put("LSRA", "44");
        codeOpcmd.put("LSRB", "54");


        codeOpcmd.put("SWI", "3F");
    }
    public static String reforme(String input) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            builder.append(input.charAt(i)).append(" ");
        }

        return builder.toString().trim(); // Trim removes the trailing space at the end
    }
    static String codeOpDATA(String instruction){
        return codeOpcmd.get(instruction);
    }
    void LDD(String menu){
        String xA= null;
        String xB= null;
        if(menu.length() > 0 && menu.length() <= 2){

            xA = menu.substring(0);
            xB = "00";
            StringBuilder D = new StringBuilder();
            D.insert(0, xB);
            D.insert(0, xA);
            R.setAccumulatorD(String.valueOf(D));
        } else if (menu.length() > 0 && menu.length() <= 4) {
            xB = menu.substring(0,2);
            xA = menu.substring(2,4);
            StringBuilder D = new StringBuilder();
            D.insert(0, xB);
            D.insert(2, xA);
            R.setAccumulatorD(String.valueOf(D));

        }else {
            JOptionPane.showMessageDialog(null, "Il y a pas de menu ou il y a plus que 2 octes!!", "Error!!", JOptionPane.ERROR_MESSAGE);
            Instruction.SWI();
        }
        RegisterDisplay.setD(reforme(R.getAccumulatorD()));
        RegisterDisplay.setA(reforme(R.getAccumulatorA()));
        RegisterDisplay.setB(reforme(R.getAccumulatorB()));
    }
    void LDA(String menu){
        if(menu.length() > 0 && menu.length() <= 2){
            R.setAccumulatorA(menu);

            StringBuilder D = new StringBuilder();
            D.insert(0, R.getAccumulatorB());
            D.insert(2, menu);
            LDD(String.valueOf(D));
            RegisterDisplay.setA(reforme(menu));
            RegisterDisplay.setD(reforme(R.getAccumulatorD()));

        }else{
            JOptionPane.showMessageDialog(null, "Accumulator A ne support qu'un octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
            Instruction.SWI();

        }
    }
    public static void SWI() {
        Rom.word.clear();
        MainFRAME.stopReading = true;
    }
    void LDB(String menu){
        if(menu.length() > 0 && menu.length() <= 2){
            StringBuilder D = new StringBuilder();
            D.insert(0, menu);
            D.insert(2, R.getAccumulatorA());
            LDD(String.valueOf(D));
            RegisterDisplay.setB(reforme(menu));
            RegisterDisplay.setD(reforme(R.getAccumulatorD()));
            Registre.setAccumulatorB(menu);
        }else{
            Instruction.SWI();
            JOptionPane.showMessageDialog(null, "Accumulator B ne support qu'un octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    void LDX(String menu){
        if(menu.length() > 0 && menu.length() <= 4){
            R.setIndexX(menu);
            RegisterDisplay.setX(reforme(menu));
        }else{
            Instruction.SWI();
            JOptionPane.showMessageDialog(null, "index X ne support que 2 octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    void LDY(String menu){
        if(menu.length() > 0 && menu.length() <= 4){
            R.setIndexY(menu);
            RegisterDisplay.setY(reforme(menu));
        }else{
            Instruction.SWI();
            JOptionPane.showMessageDialog(null, "index Y ne support que 2 octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    void LDS(String menu){
        if(menu.length() > 0 && menu.length() <= 4){
            Registre.setRPS(menu);
            RegisterDisplay.setS(reforme(menu));
        }else{
            Instruction.SWI();
            JOptionPane.showMessageDialog(null, "S ne support que 2 octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    void LDU(String menu){
        if(menu.length() > 0 && menu.length() <= 4){
            Registre.setRPU(menu);
            RegisterDisplay.setU(reforme(menu));
        }else{
            Instruction.SWI();
            JOptionPane.showMessageDialog(null, "U ne support que 2 octe!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }
    void ADDA(String menu){

        int decA = Integer.parseInt(menu, 16)+Integer.parseInt(Registre.getAccumulatorA(), 16);

        LDA(String.format("%02X", decA));
    }
    void ADDB(String menu){
        int decB = Integer.parseInt(menu, 16)+Integer.parseInt(Registre.getAccumulatorB(), 16);

        //String hexA = String.valueOf(Integer.toHexString(decA)).toUpperCase();
        LDB(String.format("%02X", decB));
    }
    // ... Autres méthodes existantes

    void CLRA() {
        LDA("00");
        RegisterDisplay.setA(reforme(R.getAccumulatorA()));
        RegisterDisplay.setD(reforme(R.getAccumulatorD()));
    }

    void CLRB() {
        LDB("00");
        RegisterDisplay.setD(reforme(R.getAccumulatorD()));
        RegisterDisplay.setB(reforme(R.getAccumulatorB()));
    }

    void COMA() {
        // Récupérer la valeur actuelle de l'accumulateur A
        String binaryString =  hexToBinary(R.getAccumulatorA());

        // Appliquer l'opération de complément à 1 (inversion de chaque bit)
        StringBuilder complement = new StringBuilder();

        for (char bit : binaryString.toCharArray()) {
            if (bit == '0') {
                complement.append('1');
            } else if (bit == '1') {
                complement.append('0');
            }
        }

        R.setAccumulatorA( binaryToHex(complement.toString()));
    }
    void COMB() {
        // Récupérer la valeur actuelle de l'accumulateur B
        String binaryString =  hexToBinary(R.getAccumulatorB());

        // Appliquer l'opération de complément à 1 (inversion de chaque bit)
        StringBuilder complement = new StringBuilder();

        for (char bit : binaryString.toCharArray()) {
            if (bit == '0') {
                complement.append('1');
            } else if (bit == '1') {
                complement.append('0');
            }
        }

        R.setAccumulatorB( binaryToHex(complement.toString()));
    }
    void INCA() {
        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = R.getAccumulatorA();

        // Convertir la valeur hexadécimale en décimal
        int decimalValue = Integer.parseInt(accumulatorA, 16);

        // Vérifier si l'accumulateur est sur plus de 8 bits
        if (decimalValue > 255) {
            Flag.setOverflowFlag(true);
        }else Flag.setOverflowFlag(false);

        // Incrémenter la valeur
        decimalValue++;

        // Assurer que la valeur reste dans la plage de 0 à 255 (8 bits)
        decimalValue %= 256;

        // Convertir la nouvelle valeur en hexadécimal
        String newAccumulatorA = Integer.toHexString(decimalValue).toUpperCase();

        // Mettre à jour l'accumulateur A avec la nouvelle valeur
        R.setAccumulatorA(newAccumulatorA);
    }
    void DECA() {
        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = R.getAccumulatorA();

        // Convertir la valeur hexadécimale en décimal
        int decimalValue = Integer.parseInt(accumulatorA, 16);

        // Vérifier si l'accumulateur est sur plus de 8 bits

            if (decimalValue > 255) {
                Flag.setOverflowFlag(true);
            }else Flag.setOverflowFlag(false);


        // DECREMENTER la valeur
        decimalValue--;

        // Assurer que la valeur reste dans la plage de 0 à 255 (8 bits)
        decimalValue %= 256;

        // Convertir la nouvelle valeur en hexadécimal
        String newAccumulatorA = Integer.toHexString(decimalValue).toUpperCase();

        // Mettre à jour l'accumulateur A avec la nouvelle valeur
        R.setAccumulatorA(newAccumulatorA);
    }
    void DECB() {
        // Récupérer la valeur actuelle de l'accumulateur B
        String accumulatorB = R.getAccumulatorB();

        // Convertir la valeur hexadécimale en décimal
        int decimalValue = Integer.parseInt(accumulatorB, 16);

        // Vérifier si l'accumulateur est sur plus de 8 bits
        if (decimalValue > 255) {
            Flag.setOverflowFlag(true);
        }else Flag.setOverflowFlag(false);

        // DECREMENTER la valeur
        decimalValue--;

        // Assurer que la valeur reste dans la plage de 0 à 255 (8 bits)
        decimalValue %= 256;

        // Convertir la nouvelle valeur en hexadécimal
        String newAccumulatorB = Integer.toHexString(decimalValue).toUpperCase();

        // Mettre à jour l'accumulateur B avec la nouvelle valeur
        R.setAccumulatorB(newAccumulatorB);
    }
    void INCB() {
        // Récupérer la valeur actuelle de l'accumulateur B
        String accumulatorB = R.getAccumulatorB();

        // Convertir la valeur hexadécimale en décimal
        int decimalValue = Integer.parseInt(accumulatorB, 16);

        // Vérifier si l'accumulateur est sur plus de 8 bits
        if (decimalValue > 255) {
            Flag.setOverflowFlag(true);
        }else Flag.setOverflowFlag(false);

        // INCREMENER la valeur
        decimalValue++;

        // Assurer que la valeur reste dans la plage de 0 à 255 (8 bits)
        decimalValue %= 256;

        // Convertir la nouvelle valeur en hexadécimal
        String newAccumulatorB = Integer.toHexString(decimalValue).toUpperCase();

        // Mettre à jour l'accumulateur B avec la nouvelle valeur
        R.setAccumulatorB(newAccumulatorB);
    }
    /*void INC() {
        // Récupérer la valeur actuelle de l'accumulateur indexé
        String accumulatorIndexe = R.getAccumulatorIndexe();

        // Convertir la valeur hexadécimale en décimal
        int decimalValue = Integer.parseInt(accumulatorIndexe, 16);

        // Incrémenter la valeur
        decimalValue++;

        // Vérifier le dépassement de 8 bits
        if (decimalValue > 255) {
            System.out.println("Attention : L'accumulateur indexé était sur plus de 8 bits, dépasse la taille autorisée.");
            R.setAccumulatorIndexe(String.format("%02X", decimalValue));


        }
    }

     */
    void ORA(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = hexToBinary( Registre.getAccumulatorA());

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorA.length(), inputBinaire.length());
        accumulatorA = padZeros(accumulatorA, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique OR bit à bit
        StringBuilder resultatOR = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorA.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatOR.append((bitA == '1' || bitInput == '1') ? '1' : '0');
        }


        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorA(binaryToHex(resultatOR.toString()));
    }
    void ORB(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorB = hexToBinary( Registre.getAccumulatorB());

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorB.length(), inputBinaire.length());
        accumulatorB = padZeros(accumulatorB, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique OR bit à bit
        StringBuilder resultatOR = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorB.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatOR.append((bitA == '1' || bitInput == '1') ? '1' : '0');
        }


        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorB(binaryToHex(resultatOR.toString()));
    }

    void EORA(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = R.getAccumulatorA();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorA.length(), inputBinaire.length());
        accumulatorA = padZeros(accumulatorA, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique XOR bit à bit
        StringBuilder resultatXOR = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorA.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatXOR.append((bitA != bitInput) ? '1' : '0');
        }

        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorA(binaryToHex(resultatXOR.toString()));
    }
    void EORB(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorB = R.getAccumulatorB();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorB.length(), inputBinaire.length());
        accumulatorB = padZeros(accumulatorB, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique XOR bit à bit
        StringBuilder resultatXOR = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorB.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatXOR.append((bitA != bitInput) ? '1' : '0');
        }

        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorB(binaryToHex(resultatXOR.toString()));
    }

    void ANDA(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = R.getAccumulatorA();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorA.length(), inputBinaire.length());
        accumulatorA = padZeros(accumulatorA, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique AND bit à bit
        StringBuilder resultatAND = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorA.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatAND.append((bitA == '1' && bitInput == '1') ? '1' : '0');
        }

        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorA(binaryToHex(resultatAND.toString()));
    }
    void ANDB(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorB = R.getAccumulatorB();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorB.length(), inputBinaire.length());
        accumulatorB = padZeros(accumulatorB, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique AND bit à bit
        StringBuilder resultatAND = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorB.charAt(i);
            char bitInput = inputBinaire.charAt(i);
            resultatAND.append((bitA == '1' && bitInput == '1') ? '1' : '0');
        }

        // Mettre à jour l'accumulateur A avec le résultat
        R.setAccumulatorB(binaryToHex(resultatAND.toString()));
    }
    // Méthode pour convertir une chaîne hexadécimale en binaire

    void BITA(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorA = R.getAccumulatorA();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorA.length(), inputBinaire.length());
        accumulatorA = padZeros(accumulatorA, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique AND bit à bit
        boolean resultNegative = false;
        boolean resultNull = true;
        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorA.charAt(i);
            char bitInput = inputBinaire.charAt(i);

            if (bitA == '1' && bitInput == '1') {
                resultNull = false;
            } else if (bitA == '0' && bitInput == '1') {
                resultNegative = true;
            }
        }
        Flag.setNegativeFlag(resultNegative);
        Flag.setZeroFlag(resultNull);
    }
    void BITB(String input) {
        // Convertir l'input hexadécimal en binaire
        String inputBinaire = hexToBinary(input);

        // Récupérer la valeur actuelle de l'accumulateur A
        String accumulatorB = R.getAccumulatorB();

        // S'assurer que les deux valeurs ont la même longueur
        int maxLength = Math.max(accumulatorB.length(), inputBinaire.length());
        accumulatorB = padZeros(accumulatorB, maxLength);
        inputBinaire = padZeros(inputBinaire, maxLength);

        // Effectuer l'opération logique AND bit à bit
        boolean resultNegative = false;
        boolean resultNull = true;

        for (int i = 0; i < maxLength; i++) {
            char bitA = accumulatorB.charAt(i);
            char bitInput = inputBinaire.charAt(i);

            if (bitA == '1' && bitInput == '1') {
                resultNull = false;
            } else if (bitA == '0' && bitInput == '1') {
                resultNegative = true;
            }
        }
        // Mettre à jour les drapeaux
        Flag.setNegativeFlag(resultNegative);
        Flag.setZeroFlag(resultNull);
    }
    void SUBA(String input){
        String sub = String.format("%02X", (Integer.parseInt(Registre.getAccumulatorA(), 16)-Integer.parseInt(input,16)));
        Registre.setAccumulatorA(sub);
        RegisterDisplay.setA(reforme(sub));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void SUBB(String input){
        String sub = String.format("%02X", (Integer.parseInt(Registre.getAccumulatorB(), 16)-Integer.parseInt(input,16)));
        Registre.setAccumulatorB(sub);
        RegisterDisplay.setB(reforme(sub));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void SUBD(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getAccumulatorD(), 16)-Integer.parseInt(input,16)));
        Registre.setAccumulatorD(sub);
        RegisterDisplay.setD(reforme(sub));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }

    void CMPA(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getAccumulatorA(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void CMPB(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getAccumulatorB(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void CMPX(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getIndexX(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void CMPY(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getIndexY(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void CMPS(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getRPS(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }
    void CMPU(String input){
        String sub = String.format("%04X", (Integer.parseInt(Registre.getRPU(), 16)-Integer.parseInt(input,16)));
        Flag.setZeroFlag(sub.equals("00"));
        Flag.setNegativeFlag(Integer.parseInt(sub,16)<0);
    }


    void LSRA() {
        // Décalage logique à droite pour l'accumulateur A
        R.setAccumulatorA(binaryToHex(logicalShiftRight(hexToBinary(R.getAccumulatorA()))));
    }

    void LSRB() {
        // Décalage logique à droite pour l'accumulateur B
        R.setAccumulatorB(binaryToHex(logicalShiftRight(hexToBinary(R.getAccumulatorB()))));
    }
    void LSLA() {
        // Décalage logique à droite pour l'accumulateur A
        R.setAccumulatorA(binaryToHex(logicalShiftLeft(hexToBinary(R.getAccumulatorA()))));
    }

    void LSLB() {
        // Décalage logique à droite pour l'accumulateur B
        R.setAccumulatorB(binaryToHex(logicalShiftLeft(hexToBinary(R.getAccumulatorB()))));
    }
    void ROLA() {
        // Rotation à gauche pour l'accumulateur A
        String accumulatorA = R.getAccumulatorA();
        String rotatedValue = rotateLeft(hexToBinary(accumulatorA));
        R.setAccumulatorA(binaryToHex(rotatedValue));
        Flag.setCarryFlag(rotatedValue.charAt(rotatedValue.length() - 1) == '1');
    }

    void ROLB() {
        // Rotation à gauche pour l'accumulateur B
        String accumulatorB = R.getAccumulatorB();
        String rotatedValue = rotateLeft(hexToBinary(accumulatorB));
        R.setAccumulatorB(binaryToHex(rotatedValue));
        Flag.setCarryFlag(rotatedValue.charAt(rotatedValue.length() - 1) == '1');
    }

    void RORA() {
        // Rotation à droite pour l'accumulateur A
        String accumulatorA = R.getAccumulatorA();
        String rotatedValue = rotateRight(hexToBinary(accumulatorA));
        R.setAccumulatorA(binaryToHex(rotatedValue));
        Flag.setCarryFlag(rotatedValue.charAt(0) == '1');
    }

    void RORB() {
        // Rotation à droite pour l'accumulateur B
        String accumulatorB = R.getAccumulatorB();
        String rotatedValue = rotateRight(hexToBinary(accumulatorB));
        R.setAccumulatorB(binaryToHex(rotatedValue));
        Flag.setCarryFlag(rotatedValue.charAt(0) == '1');
    }
    private String rotateLeft(String binaryString) {
        // Rotation à gauche en binaire
        return binaryString.substring(1) + Integer.toBinaryString(Flag.getCarryFlag()? 1:0);
    }

    private String rotateRight(String binaryString) {
        // Rotation à droite en binaire
        return Integer.toBinaryString(Flag.getCarryFlag()? 1:0) + binaryString.substring(0, binaryString.length() - 1);
    }


    private String logicalShiftRight(String binaryString) {
        // Décalage logique à droite en binaire

        return Integer.toBinaryString(Flag.getCarryFlag()? 1:0)+ binaryString.substring(0, binaryString.length() - 1);
    }
    private String logicalShiftLeft(String binaryString) {
        // Décalage logique à droite en binaire
        return binaryString.substring(1)+Integer.toBinaryString(Flag.getCarryFlag()? 1:0);
    }



    // Méthode pour convertir une chaîne hexadécimale en binaire
    private String hexToBinary(String hexString) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++) {
            char hexChar = hexString.charAt(i);
            int decimalValue = Integer.parseInt(String.valueOf(hexChar), 16);
            String binaryValue = String.format("%4s", Integer.toBinaryString(decimalValue)).replace(' ', '0');
            binaryString.append(binaryValue);
        }
        return binaryString.toString();
    }
    // Méthode pour compléter avec des zéros à gauche
    private String padZeros(String input, int length) {
        StringBuilder paddedInput = new StringBuilder(input);
        while (paddedInput.length() < length) {
            paddedInput.insert(0, '0');
        }
        return paddedInput.toString();
    }
    // Méthode pour convertir une chaîne binaire en hexadécimal
    private String binaryToHex(String binaryString) {
        int decimalValue = Integer.parseInt(binaryString, 2);
        return String.format("%02X", decimalValue);
    }
    //INST DE STOCK
    void STA(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getAccumulatorA());
    }
    void STB(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getAccumulatorB());
    }
    void STX(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getIndexX().substring(0, 2));
        MemoryRAMDisplay.fromual.put(String.format("%04X", Integer.parseInt(adrs, 16)-1), Registre.getIndexX().substring(2, 4));
    }
    void STY(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getIndexX().substring(0, 2));
        MemoryRAMDisplay.fromual.put(String.format("%04X", Integer.parseInt(adrs, 16)-1), Registre.getIndexY().substring(2, 4));
    }
    void STS(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getIndexX().substring(0, 2));
        MemoryRAMDisplay.fromual.put(String.format("%04X", Integer.parseInt(adrs, 16)-1), Registre.getRPS().substring(2, 4));
    }
    void STU(String adrs){
        MemoryRAMDisplay.fromual.put(adrs, Registre.getIndexX().substring(0, 2));
        MemoryRAMDisplay.fromual.put(String.format("%04X", Integer.parseInt(adrs, 16)-1), Registre.getRPU().substring(2, 4));
    }
    void PSHS(String a ){
        if (a.equals('A')){
            Registre.pileS.push(Registre.getAccumulatorA());
        }else if (a.equals('B')){
            Registre.pileS.push(Registre.getAccumulatorB());
        } else if (a.equals('D')) {
            Registre.pileS.push(Registre.getAccumulatorD().substring(0,2));
            Registre.pileS.push(Registre.getAccumulatorD().substring(2,4));
        }else if (a.equals('X')){
            Registre.pileS.push(Registre.getIndexX().substring(0,2));
            Registre.pileS.push(Registre.getIndexX().substring(2,4));
        } else if (a.equals('Y')) {
            Registre.pileS.push(Registre.getIndexY().substring(0,2));
            Registre.pileS.push(Registre.getIndexY().substring(2,4));
        }else {
            JOptionPane.showMessageDialog(null, "verifier les parametre du pile!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
            SWI();
        }
        int SD = Integer.parseInt(Registre.getRPS(), 16);
        for(int i = SD-Registre.pileS.capacity(); i < SD; i++ ){
            MemoryRAMDisplay.fromual.replace(String.format("%04X", i), Registre.pileSram.pop());
        }
    }
    void PULS(String a ){
        if (a.equals('A')){
            Registre.setAccumulatorA(Registre.pileS.pop())  ;
        }else if (a.equals('B')){
            Registre.setAccumulatorB(Registre.pileS.pop());  ;
        } else if (a.equals('D')) {
            String x = Registre.pileS.pop();
            String y = Registre.pileS.pop();
            Registre.setAccumulatorD(y+x);

        }else if (a.equals('X')){
            String x = Registre.pileS.pop();
            String y = Registre.pileS.pop();
            Registre.setIndexX(y+x);
        } else if (a.equals('Y')) {
            String x = Registre.pileS.pop();
            String y = Registre.pileS.pop();
            Registre.setIndexY(y+x);
        }else {
            JOptionPane.showMessageDialog(null, "verifier les parametre du pile!", "ERROR!!!", JOptionPane.ERROR_MESSAGE);
            SWI();
        }
    }
    void PSHS(Registre a, Registre b){

    }
    void PSHS(Registre a, Registre b, Registre c ){

    }
}