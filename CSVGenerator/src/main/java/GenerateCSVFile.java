import java.io.*;

public class GenerateCSVFile {

    public static final int NUMBER_OF_GENERATED_FIRST_SECTION_LINES = 5;
    public static final int NUMBER_OF_GENERATED_SECOND_SECTION_LINES = 10;

    public File generateFile(){
        GenerateLine generateLine = new GenerateLine();
        File newFile = new File("generated.csv");
        try {
//            newFile.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(newFile));

            for (int i = 0; i < NUMBER_OF_GENERATED_FIRST_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionFirst());
            }
            for (int i = 0; i < NUMBER_OF_GENERATED_SECOND_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionSecond());
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
