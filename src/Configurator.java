import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configurator {
    String inputAddress;
    String outputAddress;
    String data;
    File inputFile;
    File outputFile;
    FileReader fr;
    FileWriter fw;
    // Constructor
    public Configurator(String inputAddress, String outputAddress) throws IOException {
        this.inputAddress = inputAddress;
        this.outputAddress = outputAddress;
        inputFile = new File(inputAddress);
        outputFile = new File(outputAddress);
        fr = new FileReader(inputFile);
    }
    // Methods

    /**
     * Method that reads data of inputFile
     * @throws IOException
     */
     String getData() throws IOException {
        int i = 0;
        String s = "";
        while((i = fr.read()) != -1) {
            s += (char)i;
        }
        fr.close();
        data = s;
        return s;
     }

    /**
     * Writes the parameter into the file at the given path
     * @param s String you want to be saved
     * @throws IOException
     */
     void writeData(String s) throws IOException {
         outputFile.createNewFile();
         fw = new FileWriter(outputFile, false);
         fw.write(s);
         fw.close();
         data = s;
     }
}
