/* A few useful items are provided to you. You must write the rest. */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class that holds all the entries regarding trips
 *
 * @author Nicholas Shinn
 */
public class TollRoadDatabase {
    /**
     * For printing floating point values in dollar/cents format. Example:
     * System.out.println( String.format( DOLLAR_FORMAT, 10.5 );  // $10.50
     */
    private static final String DOLLAR_FORMAT = "$%5.2f";
    private static final String SPEED_FORMAT = "%5.1f MpH";

    /**
     * Universal new line
     */
    private static final String NL = System.lineSeparator();

    /**
     * Conversion constant from minutes to hours
     */
    public static final double MINUTES_PER_HOUR = 60.0;

    /**
     * This toll road's speed limit, in miles per hour
     */
    public static final double SPEED_LIMIT = 65.0;

    /**
     * Array list for all incomplete trips
     */
    private ArrayList<TollRecord> INCOMPLETE;

    /**
     * Array list for all complete trips
     */
    private ArrayList<TollRecord> COMPLETE;

    /**
     * Retrieves all events from file, sorts the trips as complete or incomplete
     *
     * @param eventFileName file containing all events
     *
     * @throws FileNotFoundException prevents error where file can not be found
     */
    public TollRoadDatabase(String eventFileName) throws FileNotFoundException{
        ArrayList<TollRecord> records = new ArrayList<>();
        ArrayList<TollRecord> incomplete = new ArrayList<>();
        ArrayList<TollRecord> complete = new ArrayList<>();
        File file = new File(eventFileName);
        if(file.exists()){
            Scanner lines = new Scanner(file);
            while(lines.hasNextLine()){
                String[] line = lines.nextLine().split(",[ ]*");
                records.add(new TollRecord(line[1], Integer.parseInt(line[2]), Integer.parseInt(line[0])));
            }
            while(records.size()>0){
                TollRecord a = records.get(0);
                for (int y = 1; y <= records.size() - 1; y++) {
                    TollRecord b = records.get(y);
                    if (a.equals(b)) {
                        a.setOutgoing(b.getIncoming(), b.getOnTime());
                        complete.add(a);
                        records.remove(a);
                        records.remove(b);
                        break;
                    }
                }
                if (a.getOutGoing() < 0) {
                        incomplete.add(a);
                        records.remove(a);
                }
            }
        }
            Collections.sort(incomplete);
            INCOMPLETE = incomplete;
            Collections.sort(complete);
            COMPLETE = complete;
    }

    /**
     * Reports the amount of completed and incomplete trips
     */
    public void summaryReport(){
        System.out.println("Completed Trips: " + COMPLETE.size());
        System.out.println("Incomplete Trips: " + INCOMPLETE.size());
        System.out.println(NL);
    }

    /**
     * Reports which vehicles are still on road
     */
    public void onRoadReport(){
        System.out.println("On-Road Report");
        System.out.println("==============");
        for(int i=0; i<=INCOMPLETE.size()-1; i++){
            TollRecord x = INCOMPLETE.get(i);
            System.out.println(x.toString());
        }
        System.out.println(NL);
    }

    /**
     * Prints out the completed trips and their associated tolls before totalling up the amount of fares due
     */
    public void printBills(){
        System.out.println("BILLING INFORMATION");
        System.out.println("===================");
        double total = 0;
        for(int i=0; i<=COMPLETE.size()-1; i++){
            TollRecord x = COMPLETE.get(i);
            System.out.println( x.toString() + ": " + String.format( DOLLAR_FORMAT, x.getToll() ));
            total += x.getToll();
        }
        System.out.println("Total: " + String.format(DOLLAR_FORMAT, total));
        System.out.println(NL);
    }

    /**
     * Reports which vehicles were speeding where and when they were speeding, and how fast they were going
     */
    public void speederReport(){
        System.out.println("Speeder Report");
        System.out.println("==============");
        for(int i=0; i<=COMPLETE.size()-1; i++){
            TollRecord x = COMPLETE.get(i);
            double distance = Math.abs(TollSchedule.getLocation(x.getIncoming())-TollSchedule.getLocation(x.getOutGoing()));
            double time = (x.getOutTime()-x.getOnTime())/MINUTES_PER_HOUR;
            double speed = distance/time;
            if(speed>SPEED_LIMIT) {
                System.out.println("Vehicle " + x.getTag() + ", " + "starting at time " + x.getOnTime());
                System.out.println("        from " + TollSchedule.getInterchange(x.getIncoming()));
                System.out.println("        to " + TollSchedule.getInterchange(x.getOutGoing()));
                System.out.println("        " + String.format(SPEED_FORMAT, speed));
            }
        }
        System.out.println(NL);
    }

    /**
     * Summarizes a particular vehicles trips
     *
     * @param tag vehicle tag to be examined
     */
    public void printCustSummary(String tag){
        double total = 0;
        for(int i=0; i<=COMPLETE.size()-1; i++){
            TollRecord x = COMPLETE.get(i);
            if(x.getTag().equals(tag)){
                System.out.println(x.toString() + ": " + String.format( DOLLAR_FORMAT, x.getToll() ));
                total += x.getToll();
            }
        }
        for(int i=0; i<=INCOMPLETE.size()-1; i++){
            TollRecord x = INCOMPLETE.get(i);
            if(x.getTag().equals(tag)){
                System.out.println(x.toString());
                break;
            }
        }
        System.out.println(NL);
        System.out.println("Vehicle Total Due: " + String.format( DOLLAR_FORMAT, total));
        System.out.println(NL);
    }

    /**
     * Summarizes the events that occured involving a particular exit
     *
     * @param exit exit to be examined
     */
    public void printExitActivity(int exit){
        System.out.println("EXIT " + exit + " REPORT");
        System.out.println("===============");
        for (int i = 0; i <= COMPLETE.size()-1; i++) {
            TollRecord x = COMPLETE.get(i);
            if(x.getIncoming()==exit||x.getOutGoing()==exit){
                System.out.println(x.toString());
            }
        }
        for (int i = 0; i <= INCOMPLETE.size()-1; i++) {
            TollRecord x = INCOMPLETE.get(i);
            if(x.getIncoming()==exit){
                System.out.println(x.toString());
            }
        }
        System.out.println(NL);
    }

}
