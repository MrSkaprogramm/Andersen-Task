package by.andersen.tr.bean;

import by.andersen.tr.utils.ClassUtil;
import by.andersen.tr.utils.IDUtil;
import by.andersen.tr.utils.NullableWarning;

import java.util.Objects;

public class Ticket extends IDUtil implements ClassUtil{
    @NullableWarning
    private String concertHall;
    @NullableWarning
    private short eventCode;
    @NullableWarning
    private long time;
    @NullableWarning
    private boolean isPromo;
    @NullableWarning
    private StadiumSector stadiumSector;
    @NullableWarning
    private float maxAllowedBackpackWeight;

    public Ticket() {
        validateFields();
    }

    public Ticket(String concertHall, short eventCode, long time) {
        validateFields();
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
    }

    public Ticket(String concertHall, short eventCode, long time, boolean isPromo,
                  StadiumSector stadiumSector, float maxAllowedBackpackWeight) {
        validateFields();
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
        this.isPromo = isPromo;
        this.maxAllowedBackpackWeight = maxAllowedBackpackWeight;
        this.stadiumSector = stadiumSector;
    }

    public void shared(String phone){
        System.out.println("You shared ticked id" + getID() + " to phone:" + phone);
    }

    public void shared(String phone, String email){
        System.out.println("You shared ticked id" + getID() + " to phone:" + phone + "and email:" + email);
    }

    public String getConcertHall() {
        return concertHall;
    }

    public short getEventCode() {
        return eventCode;
    }

    public long getTime() {
        return time;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public StadiumSector getStadiumSector() {
        return stadiumSector;
    }

    public float getMaxAllowedBackpackWeight() {
        return maxAllowedBackpackWeight;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
        this.stadiumSector = stadiumSector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return getID() == ticket.getID()
                && eventCode == ticket.eventCode
                && time == ticket.time
                && isPromo == ticket.isPromo
                && Float.compare(ticket.maxAllowedBackpackWeight, maxAllowedBackpackWeight) == 0
                && Objects.equals(concertHall, ticket.concertHall)
                && stadiumSector == ticket.stadiumSector;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), concertHall, eventCode, time, isPromo, stadiumSector, maxAllowedBackpackWeight);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ID=" + getID() + // output ID first
                ", concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", time=" + time +
                ", isPromo=" + isPromo +
                ", stadiumSector=" + stadiumSector +
                ", maxAllowedBackpackWeight=" + maxAllowedBackpackWeight +
                '}';
    }
}
