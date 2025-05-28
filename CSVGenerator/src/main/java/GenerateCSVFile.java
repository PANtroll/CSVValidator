import java.io.*;

public class GenerateCSVFile {

    public static final int NUMBER_OF_GENERATED_FIRST_SECTION_LINES = 5;
    public static final int NUMBER_OF_GENERATED_SECOND_SECTION_LINES = 15;

    public File generateFile(){
        GenerateLine generateLine = new GenerateLine();
        File newFile = new File("generated.csv");
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(newFile))){

            for (int i = 0; i < NUMBER_OF_GENERATED_FIRST_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionFirst());
                if(i%10 == 0){
                    System.out.println("Progrss 1: " + (double) i/NUMBER_OF_GENERATED_FIRST_SECTION_LINES);
                }
            }
            for (int i = 0; i < NUMBER_OF_GENERATED_SECOND_SECTION_LINES; i++) {
                bf.write(generateLine.generateLineSectionSecond());
                if(i%10 == 0){
                    System.out.println("Progrss 2: " + (double) i/NUMBER_OF_GENERATED_SECOND_SECTION_LINES);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFile;
    }
}
