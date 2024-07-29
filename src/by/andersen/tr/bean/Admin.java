package by.andersen.tr.bean;

import by.andersen.tr.utils.ClassUtil;

import java.util.List;

public class Admin extends User implements ClassUtil {
    @Override
    public void printRole(){
        System.out.println("Your role is Admin");
    }

    public void checkTicket(Ticket ticket){
        if(ticket.isPromo()) {
            System.out.println("Ticket " + ticket.getID() + " sold at a discount");
        } else {
            System.out.println("Ticket sold at full price");
        }
    }
}
