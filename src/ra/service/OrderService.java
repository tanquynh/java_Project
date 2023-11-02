package ra.service;

import ra.service.FileService.FileRepo;
import ra.models.Order;
import ra.repository.IShop;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IShop<Order> {
    private FileRepo file;
    private List<Order> orderList;

    public OrderService(FileRepo file) {
        this.file = file;
        this.orderList = file.findAll();
    }

    @Override
    public void save(Order order) {
        orderList.add(order);
        file.saveToFile(orderList);
    }

    @Override
    public List<Order> findAll() {
        return  file.findAll();
    }

    @Override
    public Order findById(int id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void delete(Order order) {
        orderList.remove(order);
        file.saveToFile(orderList);
    }

    @Override
    public void update(List<Order> t) {
        orderList = t;
        file.saveToFile(orderList);
    }

    @Override
    public int findByIndex(int id) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getId() == id) { // Change to getId() for comparison
                return i;
            }
        }
        return -1;
    }


    @Override
    public int autoInc() {
        int max = 0;
        for (Order o : orderList) {
            if (o.getId() > max) {
                max = o.getId();
            }
        }
        return max + 1;
    }

    public List<Order> getOrdersByStatus(byte statusCode) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == statusCode) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }


}
