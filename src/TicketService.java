import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicketService {
    private static final float MAX_NORMAL_BACKPACK_WEIGTH = 30.0F;
    private static final BigDecimal PROMO = new BigDecimal("0.50");
    static Scanner scanner = new Scanner(System.in);
    private List<Ticket> ticketList = new ArrayList<>();

    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        Ticket emptyTicket = ticketService.createEmptyTicket();

        Ticket limitedTicket = ticketService.createLimitedTicket();
        ZonedDateTime limitedTicketTime =  ticketService.saveTicketCreationTime(limitedTicket);
        System.out.println("Ticket time: " +  limitedTicketTime);

        for (int i=0; i < 10; i++) {
            Ticket fullTicket = ticketService.createFullTicket();
            ZonedDateTime fullTicketTime = ticketService.saveTicketCreationTime(fullTicket);
            System.out.println("Ticket creation time: " + fullTicketTime);
            BigDecimal fullTicketPrice = ticketService.calculateTicketPrice(fullTicket);
            System.out.println("Total ticket price: " + fullTicketPrice);
            ticketService.saveTicketToList(fullTicket);
        }

        Ticket ticketById = ticketService.findTicketById("wrim");
        System.out.println("Your ticket: " + ticketById.toString());
    }

    public Ticket createEmptyTicket(){
        Ticket emptyTicket = new Ticket();
        System.out.println("Empty Ticket created");
        return emptyTicket;
    }

    public Ticket createLimitedTicket(){
        String concertHall = readConcertHallName();
        short eventCode = readEventCode();
        long time = detectTicketTime();

        Ticket limitedTicket = new Ticket(concertHall, eventCode, time);
        System.out.println("Limited Ticket created:" + "\n" + "concertHall: " + concertHall
                + "\n" + "eventCode: " + eventCode+ "\n" + "time: " + time);
        return limitedTicket;
    }

    public Ticket createFullTicket(){
        Random random = new Random();
        String ID = generateRandomID(random);
        String concertHall = readConcertHallName();
        short eventCode = readEventCode();
        long time = detectTicketTime();
        boolean isPromo = Math.random() < 0.5;
        StadiumSector stadiumSector = readStadiumSector();
        float maxAllowedBackpackWeight = generateRandomWeight(random);

        Ticket fullTicket = new Ticket(ID, concertHall, eventCode, time, isPromo,
                maxAllowedBackpackWeight, stadiumSector);
        System.out.println("Full Ticket created:" + "\n" + "ID: " + ID + "\n" +
                "concertHall: " + concertHall + "\n" + "eventCode: " + eventCode + "\n" +
                "time: " + time+ "\n" + "promo ticket: " + isPromo + "\n" +
                "stadiumSector: " + stadiumSector.toString() + "\n" +
                "maxAllowedBackpackWeight: " + maxAllowedBackpackWeight);

        return fullTicket;
    }

    public long detectTicketTime(){
        long time = System.currentTimeMillis() / 1000;
        return time;
    }

    public void saveTicketToList(Ticket fullTicket){
        ticketList.add(fullTicket);
    }

    public Ticket findTicketById(String ticketId) {
        for (Ticket ticket : ticketList) {
            if (ticket.getID().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    public ZonedDateTime saveTicketCreationTime(Ticket ticket){
        Instant instant = Instant.ofEpochSecond(ticket.getTime());
        return instant.atZone(ZoneId.of("UTC"));
    }

    public float generateRandomWeight(Random random) {
        float randomNumber = random.nextFloat(MAX_NORMAL_BACKPACK_WEIGTH);
        randomNumber = Math.round(randomNumber * 1000.0f) / 1000.0f;
        return randomNumber;
    }

    public BigDecimal calculateTicketPrice(Ticket fullTicket){
        boolean isPromo = fullTicket.isPromo();
        StadiumSector ticketSector = fullTicket.getStadiumSector();
        BigDecimal price = BigDecimal.ZERO;

        if (isPromo) {
            price = ticketSector.getPrice().multiply(PROMO);
        } else {
            price = ticketSector.getPrice();
        }
        return price;
    }

    public static String generateRandomID(Random random) {
        int length = random.nextInt(4) + 1;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                char c = (char) ('a' + random.nextInt('z' - 'a' + 1));
                sb.append(c);
            } else {
                int digit = random.nextInt(10);
                sb.append(digit);
            }
        }
        return sb.toString();
    }



    public String readConcertHallName(){
        System.out.println("Enter concert hall name:");
        String concertHall = scanner.nextLine();
        return concertHall;
    }

    public short readEventCode(){
        System.out.println("Enter event code:");
        short eventCode = scanner.nextShort();
        scanner.nextLine();
        return eventCode;
    }

    public StadiumSector readStadiumSector(){
        System.out.println("Enter stadium sector:");
        String eventCode = scanner.nextLine().toUpperCase();
        StadiumSector stadiumSector = StadiumSector.valueOf(eventCode);
        return stadiumSector;
    }
}