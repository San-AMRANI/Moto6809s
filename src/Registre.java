import java.util.Stack;

public class Registre {
    private static String line;
    private static String accumulatorA = "00";
    private static String accumulatorB = "00";
    private static String AccumulatorD = "0000";
    private static String indexX = "0000";
    private static String indexY = "0000";
    private static String RPS = "0000";
    private static String RPU = "0000";
    static Stack<String> pileS = new Stack<>();
    static Stack<String> pileSram = pileS;
    public static void setRPS(String S){
        RPS = S;
    }
    public static String getRPS(){
        return RPS;
    }
    public static void setRPU(String U){
        RPS = U;
    }
    public static String getRPU(){
        return RPU;
    }
    public static String getline() {
        return line;
    }
    public static void setline(String xIndexe) {
        line = xIndexe;
    }
    // Getters and setters for registers
    public static String getAccumulatorA(){
        return accumulatorA;
    }
    public static void setAccumulatorA(String xA){
        accumulatorA = xA;
    }
    public static String getAccumulatorB(){
        return accumulatorB;
    }
    public static void setAccumulatorB(String xB){
        accumulatorB=xB;
    }
    public static void setAccumulatorD(String xD){
        String xB = xD.substring(0, 2);
        String xA = xD.substring(2, 4);
        Registre.setAccumulatorA(xA);
        Registre.setAccumulatorB(xB);
        AccumulatorD = xD;
    }
    public static String getAccumulatorD(){
        return AccumulatorD;
    }
    public static void setIndexX(String xX){
        indexX=xX;
    }
    public static String getIndexX(){
        return indexX;
    }
    public static void setIndexY(String xY){
        indexY=xY;
    }
    public static String getIndexY(){
        return indexY;
    }
}
