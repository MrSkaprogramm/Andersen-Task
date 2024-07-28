package by.andersen.tr;

import by.andersen.tr.bean.*;
import by.andersen.tr.utils.ClassUtil;
import by.andersen.tr.utils.IDUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicketService extends IDUtil implements ClassUtil {
    private static final float MAX_NORMAL_BACKPACK_WEIGTH = 30.0F;
    private static final int MAX_CONCERT_HALL_NAME_LENGTH = 10;
    private static final short EVENT_CODE_MAX_NUMBER = 999;
    private static final String STADIUM_SECTOR_PATTERN = "[A-C]";
    private static final String PHONE_PATTERN = "^\\+?\\d{1,15}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final BigDecimal PROMO = new BigDecimal("0.50");
    private static List<Ticket> ticketList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.printRole();

        Client client = new Client();
        client.printRole();

        TicketService ticketService = new TicketService();

        Ticket emptyTicket = ticketService.createEmptyTicket();

        Ticket limitedTicket = ticketService.createLimitedTicket();

        ticketService.createSomeFullTickets(2);

        Ticket ticketById = ticketService.findTicketById(Long.parseLong("1"));

        List<Ticket> tickets = ticketService.findTicketByStadiumSector(StadiumSector.A);
        ticketService.showTicketsByStadiumSector(tickets);

        admin.checkTicket(ticketList.getFirst());

        ticketService.getClientTicket(client, (short) 501);

        ticketService.printClassContent(ticketList.getFirst());
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
        String concertHall = readConcertHallName();
        short eventCode = readEventCode();
        long time = detectTicketTime();
        boolean isPromo = Math.random() < 0.5;
        StadiumSector stadiumSector = readStadiumSector();
        float maxAllowedBackpackWeight = generateRandomWeight(random);

        Ticket fullTicket = new Ticket(concertHall, eventCode, time, isPromo,
                stadiumSector, maxAllowedBackpackWeight);
        System.out.println("Ticket created:" + "\n" + "ID: " + fullTicket.getID() + "\n" +
                "concertHall: " + concertHall + "\n" + "eventCode: " + eventCode + "\n" +
                "time: " + time+ "\n" + "promo ticket: " + isPromo + "\n" +
                "stadiumSector: " + stadiumSector.toString() + "\n" +
                "maxAllowedBackpackWeight: " + maxAllowedBackpackWeight);

        return fullTicket;
    }

    public void createSomeFullTickets(int quantity) {
        for (int i=0; i<quantity; i++) {
            Ticket fullTicket = createFullTicket();
            BigDecimal fullTicketPrice = calculateTicketPrice(fullTicket);
            System.out.println("Total ticket price: " + fullTicketPrice);
            saveTicketToList(fullTicket);

            shareTicket(fullTicket);
        }
    }

    private String readConcertHallName(){
        System.out.println("Enter concert hall name:");
        for(int i = 0; i < 2; i++) {
            String concertHall = scanner.nextLine();
            if (concertHall.length() <= MAX_CONCERT_HALL_NAME_LENGTH) {
                return concertHall;
            } else {
                System.out.println("Wrong name!");
            }
        }
        throw new IllegalArgumentException("Wrong name!");
    }

    private short readEventCode(){
        System.out.println("Enter event code:");
        for(int i = 0; i < 2; i++) {
            short eventCode = scanner.nextShort();
            if (eventCode <= EVENT_CODE_MAX_NUMBER) {
                scanner.nextLine();
                return eventCode;
            } else {
                System.out.println("Wrong event code!");
            }
        }
        throw new IllegalArgumentException("Wrong event code!");
    }

    private StadiumSector readStadiumSector(){
        for(int i = 0; i < 2; i++) {
            System.out.println("Enter stadium sector:");
            String stadiumSectorString = scanner.nextLine().toUpperCase();
            if (stadiumSectorString.matches(STADIUM_SECTOR_PATTERN)) {
                StadiumSector stadiumSector = StadiumSector.valueOf(stadiumSectorString);
                return stadiumSector;
            } else {
                System.out.println("Wrong stadium sector!");
            }
        }
        throw new IllegalArgumentException("Wrong stadium sector!");
    }

    private long detectTicketTime(){
        long time = System.currentTimeMillis() / 1000;
        return time;
    }

    private float generateRandomWeight(Random random) {
        float randomNumber = random.nextFloat(MAX_NORMAL_BACKPACK_WEIGTH);
        randomNumber = Math.round(randomNumber * 1000.0f) / 1000.0f;
        return randomNumber;
    }

    private void saveTicketToList(Ticket fullTicket){
        ticketList.add(fullTicket);
    }

    public Ticket findTicketById(long ticketId) {
        for (Ticket ticket : ticketList) {
            if (ticket.getID() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    private BigDecimal calculateTicketPrice(Ticket fullTicket){
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

    public List<Ticket> findTicketByStadiumSector(StadiumSector stadiumSector) {
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getStadiumSector().equals(stadiumSector)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public void showTicketsByStadiumSector(List<Ticket> tickets) {
        if (tickets.isEmpty()){
            System.out.println("There are no tickets with this stadium sector");
        } else{
            System.out.println("Your tickets with the stadium sector A:");
            for (Ticket ticket : tickets) {
                System.out.println(ticket.toString());
            }
        }
    }

    public void getClientTicket(Client client, short eventCode) {
        if (client.getTicket(eventCode, ticketList) != null) {
            System.out.println("Ticket found");
        } else {
            System.out.println("Ticket not found");
        }
    }

    public void shareTicket(Ticket ticket) {
         String phone = readPhoneNum();
         ticket.shared(phone);

         String  email = readEmail();
         ticket.shared(phone, email);
    }

    private String readPhoneNum() {
        System.out.println("Enter phone:");
        for(int i = 0; i < 2; i++) {
            String phone = scanner.nextLine();
            if (phone.matches(PHONE_PATTERN)) {
                return phone;
            } else {
                System.out.println("Wrong phone!");
            }
        }
        throw new IllegalArgumentException("Wrong phone!");
    }

    private String readEmail() {
        System.out.println("Enter email:");
        for(int i = 0; i < 2; i++) {
            String email = scanner.nextLine();
            if (email.matches(EMAIL_PATTERN)) {
                return email;
            } else {
                System.out.println("Wrong email!");
            }
        }
        throw new IllegalArgumentException("Wrong email!");
    }

    public <T extends ClassUtil> void printClassContent(T instance) {
        instance.print();
    }
}