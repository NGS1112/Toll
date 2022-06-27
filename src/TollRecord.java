/* A few useful items are provided to you. You must write the rest. */

/**
 * Records data about a specific vehicle to be used in determining trips
 *
 * @author Nicholas Shinn
 */
public class TollRecord implements Comparable<TollRecord>{

    /**
     * For printing toll records in reports
     * using {@link String#format(String, Object...)}
     */
    private static final String TOLL_RECORD_FORMAT = "[%11s] on #%2d, time %5d";
    private static final String OFF_FORMAT = "; off #%2d, time %5d";

    /**
     * Value of uninitialized integer fields in this record
     */
    public static final int UNINITIALIZED = -1;

    /**
     * Name of vehicle
     */
    private String VEHICLE_TAG;

    /**
     * Starting exit
     */
    private int INCOMING;

    /**
     * Time vehicle entered
     */
    private int ON_TIME;

    /**
     * Leaving exit
     */
    private int OUT_GOING;

    /**
     * Time vehicle exited
     */
    private int OUT_TIME;

    /**
     * Creates an initially incompleted trip that can be added to
     *
     * @param tag Vehicle name
     * @param incoming Starting exit
     * @param onTime Time vehicle entered
     */
    public TollRecord(String tag, int incoming, int onTime){
        this.VEHICLE_TAG = tag;
        this.INCOMING = incoming;
        this.ON_TIME = onTime;
        this.OUT_GOING = UNINITIALIZED;
        this.OUT_TIME = UNINITIALIZED;
    }

    /**
     * Sets an outgoing exit and time, completing the trip
     *
     * @param outgoing final exit
     * @param outTime time Vehicle exits
     */
    public void setOutgoing(int outgoing, int outTime){
        this.OUT_GOING = outgoing;
        this.OUT_TIME = outTime;
    }

    /**
     * Retrieves the Vehicle tag
     *
     * @return Vehicle tag
     */
    public String getTag(){
        return VEHICLE_TAG;
    }

    /**
     * Retrieves the starting exit
     *
     * @return starting exit
     */
    public int getIncoming(){
        return INCOMING;
    }

    /**
     * Retrieves the Vehicle's entering time
     *
     * @return Entering time
     */
    public int getOnTime(){
        return ON_TIME;
    }

    /**
     * Retrieves the finishing exit
     *
     * @return finishing exit
     */
    public int getOutGoing(){
        return OUT_GOING;
    }

    /**
     * Retrieves the Vehicle's exiting time
     *
     * @return exiting time
     */
    public int getOutTime(){
        return OUT_TIME;
    }

    /**
     * Determines the toll of a completed trip
     *
     * @return toll
     */
    public double getToll(){
        return TollSchedule.getFare(INCOMING, OUT_GOING);
    }

    /**
     * Returns the hash code of a tag
     *
     * @return tag hash code
     */
    public int hashCode(){
        return getTag().hashCode();
    }

    /**
     * Determines if two tags are equal
     *
     * @param toll_record another toll record
     *
     * @return true if equal, false otherwise
     */
    public boolean equals(TollRecord toll_record){
        return this.hashCode() == toll_record.hashCode();
    }

    /**
     * Compares this to another toll record by tag and on-time
     *
     * @param toll_record another toll record
     *
     * @return difference
     */
    public int compareTo(TollRecord toll_record){
        int i = VEHICLE_TAG.compareTo(toll_record.VEHICLE_TAG);
        if(i!=0) return i;

        return Integer.compare(ON_TIME, toll_record.ON_TIME);
    }

    /**
     * Reports a completed trip's details
     *
     * @return string containing details
     */
    private String report(){
        return String.format(TOLL_RECORD_FORMAT, VEHICLE_TAG, INCOMING, ON_TIME) + "; " + String.format(OFF_FORMAT, OUT_GOING, OUT_TIME);
    }

    /**
     * Represents object in string form
     *
     * @return string form of TollRecord object
     */
    public String toString(){
        if(OUT_GOING!= -1) {
            return report();
        }
        else{ return String.format(TOLL_RECORD_FORMAT, VEHICLE_TAG, INCOMING, ON_TIME); }
    }

}
