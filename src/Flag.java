public class Flag {
    private static boolean carryFlag;
    private static boolean zeroFlag;
    private static boolean irqDisableFlag;
    private static boolean halfCarryFlag;
    private static boolean interruptFlag;
    private static boolean negativeFlag;
    private static boolean overflowFlag;
    private static boolean extendedFlag;

    public Flag() {
        setAllFlagsFalse();
    }
    public static void setAllFlagsFalse() {
        // Initialiser tous les drapeaux à false par défaut
        carryFlag = false;
        zeroFlag = false;
        irqDisableFlag = false;
        halfCarryFlag = false;
        interruptFlag = false;
        negativeFlag = false;
        overflowFlag = false;
        extendedFlag = false;
    }

    // Méthodes pour définir les drapeaux
    public static void setCarryFlag(boolean value) {
        carryFlag = value;
    }

    public static void setZeroFlag(boolean value) {
        zeroFlag = value;
    }

    public static void setIrqDisableFlag(boolean value) {
        irqDisableFlag = value;
    }

    public static void setHalfCarryFlag(boolean value) {
        halfCarryFlag = value;
    }

    public static void setInterruptFlag(boolean value) {
        interruptFlag = value;
    }

    public static void setNegativeFlag(boolean value) {
        negativeFlag = value;
    }

    public static void setOverflowFlag(boolean value) {
        overflowFlag = value;
    }

    public static void setExtendedFlag(boolean value) {
        extendedFlag = value;
    }

    // Méthodes pour obtenir les drapeaux
    public static boolean getCarryFlag() {
        return carryFlag;
    }

    public static boolean getZeroFlag() {
        return zeroFlag;
    }

    public static boolean getIrqDisableFlag() {
        return irqDisableFlag;
    }

    public static boolean getHalfCarryFlag() {
        return halfCarryFlag;
    }

    public static boolean getInterruptFlag() {
        return interruptFlag;
    }

    public static boolean getNegativeFlag() {
        return negativeFlag;
    }

    public static boolean getOverflowFlag() {
        return overflowFlag;
    }

    public static boolean getExtendedFlag() {
        return extendedFlag;
    }

    public static String displayFlags() {


    String display = (extendedFlag ? "1" : "0") + "  " +
            (irqDisableFlag  ? "1" : "0") + "  " +
            (halfCarryFlag ? "1" : "0") + "  " +
            (interruptFlag ? "1" : "0") + "  " +
            (negativeFlag ? "1" : "0") + "  " +
            (zeroFlag ? "1" : "0") + "  " +
            (overflowFlag ? "1" : "0") + "  " +
            (carryFlag ? "1" : "0");

        return display;
}
}
