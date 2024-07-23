import java.sql.Time;
import java.util.List;

public class Ticket {
    private String ID;
    private String concertHall;
    private short eventCode;
    private long time;
    private boolean isPromo;
    private StadiumSector stadiumSector;
    private float maxAllowedBackpackWeight;

    public Ticket() {
    }

    public Ticket(String concertHall, short eventCode, long time) {
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
    }

    public Ticket(String ID, String concertHall, short eventCode, long time, boolean isPromo,
                  float maxAllowedBackpackWeight, StadiumSector stadiumSector) {
        this.ID = ID;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
        this.isPromo = isPromo;
        this.maxAllowedBackpackWeight = maxAllowedBackpackWeight;
        this.stadiumSector = stadiumSector;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getConcertHall() {
        return concertHall;
    }

    public void setConcertHall(String concertHall) {
        this.concertHall = concertHall;
    }

    public short getEventCode() {
        return eventCode;
    }

    public void setEventCode(short eventCode) {
        this.eventCode = eventCode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public void setPromo(boolean promo) {
        isPromo = promo;
    }

    public StadiumSector getStadiumSector() {
        return stadiumSector;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
        this.stadiumSector = stadiumSector;
    }

    public double getMaxAllowedBackpackWeight() {
        return maxAllowedBackpackWeight;
    }

    public void setMaxAllowedBackpackWeight(float maxAllowedBackpackWeight) {
        this.maxAllowedBackpackWeight = maxAllowedBackpackWeight;
    }
}
