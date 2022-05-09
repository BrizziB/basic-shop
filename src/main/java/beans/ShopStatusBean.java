package beans;

import model.Order;
import model.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ShopStatusBean {

    List<Order> completedOrderList;
    
    List<User> activeUsers;

    @PostConstruct
    private void init(){
    	System.out.println("sono stato creato ");
        activeUsers = new ArrayList<>();
        completedOrderList = new ArrayList<>();
    }
    
    @PreDestroy
    private void close() {
    	System.out.println("sto per essere distrutto ");
    }

    public int completedOrderNum(){
        return completedOrderList.size();
    }

    public int activeUserNum(){
        return activeUsers.size();
    }

    public List<Order> getCompletedOrderList() {
        return completedOrderList;
    }

    public void setCompletedOrderList(List<Order> completedOrderList) {
        this.completedOrderList = completedOrderList;
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<User> activeUsers) {
        this.activeUsers = activeUsers;
    }
}
