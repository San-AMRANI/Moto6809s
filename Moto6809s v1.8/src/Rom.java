import java.util.ArrayList;
import java.util.HashMap;

public class Rom {

    static int cpt;
    static ArrayList<String> word = new ArrayList<>();
    HashMap<String, String> RomInitial(){
        HashMap<String, String> ROMdata = new HashMap<String, String>();

        String startstaticAddress = "AC00";
        String endstaticAddress = "AEFF";

        int SD = Integer.parseInt(startstaticAddress, 16);
        int ED = Integer.parseInt(endstaticAddress, 16);

        for (int i = SD; i <= ED; i++) {
            String address = String.format("%04X", i); // Formater l'adresse en hexadécimal sur 4 chiffres
            ROMdata.put(address, "FF");
            // Incrémenter la valeur hexadécimale
        }
        return ROMdata;
    }

    static String startAddress = "AC00";
    static String endAddress = "AEFF";

    static ArrayList<String> PC =new ArrayList<>();
    static HashMap<String, String> getROMdata(){

        HashMap<String, String> ROMdata = new Rom().RomInitial();

        int SD = Integer.parseInt(startAddress, 16);

        int j=0;
        int endstock = SD + word.size();

        for (int i =SD; i<endstock; i++){
            //Registre.setProgramCounter(String.format("%04X", i+1));

            //PC.add(String.format("%04X",i+1));
            //RegisterDisplay.setPC(Registre.getProgramCounter());
            String address = String.format("%04X",i);
            //decimalValue = Integer.parseInt(address, 16) + 1;
            ROMdata.replace(address, (String) word.get(j));
            j++;
        }


        return ROMdata;
    }


}
