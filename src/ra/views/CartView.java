package ra.views;

import ra.service.CartService;
import ra.service.MenuService;
import ra.service.OrderService;
import ra.service.UserService;
import ra.models.Cart;
import ra.models.Order;
import ra.models.Product;
import ra.models.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static ra.config.ConsoleColor.*;
import static ra.config.InputMethods.getInteger;
import static ra.config.InputMethods.getString;



public class CartView {
    private CartService cartService;
    private UserView userView;
    private UserService userService;
    private OrderService orderService;

    private MenuService menuService;
    private OrderHistoryView orderHistoryView;

    public CartView(CartService cartService, UserView userView, UserService userService, OrderService orderService, MenuService menuService, OrderHistoryView orderHistoryView) {
        this.cartService = cartService;
        this.userView = userView;
        this.userService = userService;
        this.orderService = orderService;
        this.menuService = menuService;
        this.orderHistoryView = orderHistoryView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void displayMenuCart() {
        int selectCart;
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║              Menu-Gio hang           ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │   Xem danh sách giỏ hàng    ║");
            System.out.println("║   2    │   CHỉnh sửa số lượng        ║");
            System.out.println("║   3    │   Xóa 1 sản phẩm            ║");
            System.out.println("║   4    │   Xóa toàn bộ sản phẩm      ║");
            System.out.println("║   5    │   Thanh toán                ║");
            System.out.println("║   6    │   Lịch sử mua hàng          ║");
            System.out.println("║   7    │   Quay lại menu trước       ║");
            System.out.println("║   8    │   Đăng xuất                 ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhap lua chon cua ban : ");
            selectCart = getInteger();
            switch (selectCart) {
                case 1:

                    displayAllCart();
                    break;
                case 2:
                    updateQuantity();
                    break;
                case 3:
                    deleteProductInCart();
                    break;
                case 4:
                    deleteAllProductInCart();
                    break;
                case 5:
                    endPay();
                    break;
                case 6:
                    orderHistoryView.OrderMenuHistory();
                    break;
                case 7:
                    return;
                case 8:
                    userView.logout();
                    break;
                default:
                    System.err.println("--->> Lua chon khong phu hop. Vui long chon lai ❤ ");
                    break;
            }
        }
    }


    private void endPay() {
        User user = cartService.userLogin();
        if (user.getCart().isEmpty()) {
            printlnMess("Chưa có đơn hàng cần thanh toán !!.");
            return;
        }
        Order newOrder = new Order();
        newOrder.setId(orderService.autoInc());

        // Cập nhật tổng tiền
        double total = 0;
        for (Cart ca : user.getCart()) {
            total += ca.getProduct().getPrice() * ca.getQuantity();
        }
        newOrder.setTotal(total);
        newOrder.setIdUser(user.getId());
        System.out.println("Nhập tên người nhận hàng: ");
        newOrder.setReceiver(getString());
        System.out.println("Nhập số điện thoại người nhận: ");
        newOrder.setNumberPhone(getString());
        System.out.println("Nhập vào địa chỉ người nhận:");
        newOrder.setAddress(getString());
        newOrder.setBuyDate(LocalDateTime.now());

        // Tiến hành trừ đi số lượng trong kho hàng
        for (Cart ca : user.getCart()) {
            Product pt = menuService.findById(ca.getProduct().getProductId());
            pt.setStock(pt.getStock() - ca.getQuantity());
            menuService.updateQuantity(pt);
        }

        printlnSuccess("Đặt hàng thành công🎈🎈.Vui lòng chờ xác nhận !!.");
        orderService.save(newOrder);
        Order order = new Order();
        userService.save(user);
        order.setOrderDetail(user.getCart());
        user.setCart(new ArrayList<>());

    }


    private void deleteAllProductInCart() {
        cartService.deleteAll();
    }

    private void deleteProductInCart() {
        System.out.println("Nhập vào ID");
        int idCart = getInteger();
        int index = cartService.findByIndex(idCart);

        if (index == -1) {
            System.err.println("Sản phẩm không tồn tại trong giỏ hàng.");
            return;
        }

        cartService.deleteCart(index);
    }


    private void updateQuantity() {
        System.out.println("Nhập vào ID: ");
        int idCart = getInteger();
        User user =cartService.userLogin();
        List<Cart> carts = user.getCart();
        Cart cart = cartService.findById(idCart);

        if (cart == null) {
            printlnError("Sản phẩm không tồn tại trong giỏ hàng!!");
            return;
        }

        System.out.println("Nhập vào số lượng muốn cập nhật mới: ");
        int updateQuantity = getInteger();

        if (updateQuantity > cart.getProduct().getStock()) {
            printlnError("Số lượng sản phẩm vượt quá tồn kho.");
        } else {
            cart.setQuantity(updateQuantity);
            int index = cartService.findByIndex(idCart);
            carts.set(index, cart);
            printlnSuccess("Cập nhật số lượng thành công 🎈🎈.");
            userService.save(user);
        }
    }


    private void displayAllCart() {
        List<Cart> carts = cartService.userLogin().getCart();

        if (carts.isEmpty()) {
            printlnError("Giỏ hàng rỗng !!.");
            return;
        }
        System.out.println("|-----------------GIỎ HÀNG------------------|");
        for (Cart ca : carts
        ) {
            ca.display();
        }
    }
}
