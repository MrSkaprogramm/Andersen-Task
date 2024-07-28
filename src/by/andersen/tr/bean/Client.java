package by.andersen.tr.bean;

import by.andersen.tr.utils.ClassUtil;

import java.util.List;

public class Client extends User implements ClassUtil {
    @Override
    public void printRole(){
        System.out.println("Your role is Client");
    }

    public Ticket getTicket(short eventCode, List<Ticket> tickets){
        for(Ticket ticket : tickets) {
            if (ticket.getEventCode() == eventCode) {
                return ticket;
            }
        }
        return null;
    }
}
