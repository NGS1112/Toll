import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class for running the TollReport program
 *
 * @author Nicholas Shinn
 */
public class TollReport{

    /**
     * Checks if a file name was provided before using it to populate the database and present all data to the consumer
     *
     * @param args file name to be used
     *
     * @throws FileNotFoundException prevents cases where file does not exist
     */
    public static void main(String args[]) throws FileNotFoundException{
        if(args.length==1){
            TollRoadDatabase data = new TollRoadDatabase(args[0]);
            data.summaryReport();
            data.onRoadReport();
            data.speederReport();
            data.printBills();
            Scanner sc = new Scanner(System.in);
            String input = "c";
            while(!input.equals("q")) {
                System.out.println("'b <string>' to see bill for license tag");
                System.out.println("'e <number>' to see activity at exit");
                System.out.println("'q' to quit");
                input = sc.nextLine();
                System.out.println("> " + input);
                System.out.println();
                String[] lines =input.split("\\s+");
                if(lines[0].equals("b")){
                    data.printCustSummary(lines[1]);
                }
                if(lines[0].equals("e")){
                    data.printExitActivity(Integer.parseInt(lines[1]));
                }
                System.out.println();
            }
            System.exit(0);
        }
        else{
            System.out.println("Usage: java TollReport event-file");
            System.exit(1);
        }
    }
}
