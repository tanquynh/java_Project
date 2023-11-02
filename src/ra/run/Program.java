package ra.run;

import ra.models.*;
import ra.service.*;
import ra.service.FileService.FileRepo;
import ra.views.*;
import java.util.Scanner;
import static ra.constant.Contant.FilePath.*;
public class Program {


    public static void main(String[] args) {
        //user
        FileRepo<User> ioFile = new FileRepo<>(USER_FILE);
        UserService userService = new UserService(ioFile);
        //menu
        FileRepo<Product> ioFile1 = new FileRepo<>(PRODUCT_FILE);
        MenuService menuService = new MenuService(ioFile1);
        //cart
        //oder
        FileRepo < Order> ioFile4 = new FileRepo<>(ORDER_FILE);
        OrderService orderService = new OrderService(ioFile4);
        //----------
        CartService cartService = new CartService(userService);
        //oderHistory
        OrderHistoryView orderHistoryView = new OrderHistoryView(orderService,userService,null);
        //category
        FileRepo<Category> ioFile2 = new FileRepo<>(CATEGORY_FILE);
        CategoryService categoryService = new CategoryService(ioFile2);
        CartView cartView = new CartView(cartService,null,userService,orderService,menuService,orderHistoryView);

        CategoryView categoryView = new CategoryView(categoryService, null,menuService);

        MenuView menuView = new MenuView(cartView, menuService, cartService, categoryService,userService);
        //user
        UserView userView = new UserView(userService, menuView, cartView, categoryView,orderHistoryView);

        //dung chung
        categoryView.setUserView(userView);
        menuView.setUserView(userView);
        cartView.setUserView(userView);
        userView.loginOrRegister();
        orderHistoryView.setUserView(userView);

    }
}

