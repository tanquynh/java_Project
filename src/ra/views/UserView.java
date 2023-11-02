package ra.views;

import ra.config.InputMethods;
import ra.config.Validate;
import ra.models.User;
import ra.service.UserService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.sun.deploy.security.CertStore.USER;
import static ra.config.ConsoleColor.*;
import static ra.config.ConsoleColor.printlnError;
import static ra.config.InputMethods.*;
import static ra.config.Validate.isValidFullName;
import static ra.config.Validate.isValidPassword;
import static ra.constant.Contant.ADMIN_CODE;
import static ra.constant.Contant.Inportance.BLOCK;
import static ra.constant.Contant.Inportance.OPEN;
import static ra.constant.Contant.Role.ADMIN;
import static ra.constant.Contant.Status.ACTIVE;
import static ra.constant.Contant.Status.INACTIVE;

public class UserView {
    private UserService userService;
    private MenuView menuView;
    private CartView cartView;
    private CategoryView categoryView;
    private OrderHistoryView orderHistoryView;

    public UserView(UserService userService, MenuView menuView, CartView cartView, CategoryView categoryView, OrderHistoryView orderHistoryView) {
        this.userService = userService;
        this.menuView = menuView;
        this.cartView = cartView;
        this.categoryView = categoryView;
        this.orderHistoryView = orderHistoryView;
    }

    static String userName;

    public UserService getUserService() {
        return userService;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public CartView getCartView() {
        return cartView;
    }

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public OrderHistoryView getOrderHistoryView() {
        return orderHistoryView;
    }

    public static String getUserName() {
        return userName;
    }

    public void loginOrRegister() {
        print(BLUE);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   😍🧡  QUẢN LÝ CỬA HÀNG KARA 😍😍  ║");
        System.out.println("╟────────┬─────────────────────────────╢");
        System.out.println("║   1    │       Đăng nhập             ║");
        System.out.println("║   2    │       Đăng ký               ║");
        System.out.println("║   0    │       Thoát                 ║");
        System.out.println("╚════════╧═════════════════════════════╝");
        System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");

        int choice = InputMethods.getInteger();
        switch (choice) {
            case 1:
                User user = login();
                break;
            case 2:
                User user1 = registerUser();
                userService.save(user1);
                printlnSuccess("Đăng ký thành công !");
                loginOrRegister();
                break;
            case 0:
                break;
        }

    }

    private User registerUser() {
        List<User> users = userService.findAll();
        User user = new User();
        user.setId(userService.autoInc());
        printlnMess("Vui lòng đăng ký tài khoản !!");

        // Chọn role của người dùng
        System.out.println("Hãy chọn role của bạn: ");
        System.out.println("1: ADMIN");
        System.out.println("2: USER");
        int role = getInteger();

        if (role == ADMIN) {
            // Nếu là ADMIN, yêu cầu nhập mã xác nhận ADMIN
            printlnMess("Nhập vào mã xác nhận ADMIN: ");
            String adminCode = getString();

            if (!adminCode.equals(ADMIN_CODE)) {
                printlnError("Mã xác thực không đúng, vui lòng nhập lại.");
                return registerUser(); // Gọi lại phương thức để người dùng nhập lại
            }
        }
        user.setRole(role);

        // Nhập họ và tên đầy đủ
        while (true) {
            System.out.println("Hãy nhâp vào họ và tên đầy đủ: ");
            String fullName = InputMethods.scanner().nextLine();

            if (Validate.isValidFullName(fullName)) {
                user.setFullName(fullName);
                break;
            }
        }

        // Nhập tên đăng nhập
        while (true) {
            System.out.println("Hãy nhập tên đăng nhập: ");
            String username = InputMethods.scanner().nextLine();

            if (Validate.isValidFullName(username)) {
                boolean isUsernameAvailable = true;

                if (users != null) {
                    for (User existingUser : users) {
                        if (existingUser.getUsername().trim().equals(username)) {
                            printlnError("Tên đăng nhập đã được sử dụng, mời nhập tên đăng nhập mới.");
                            isUsernameAvailable = false;
                            break;
                        }
                    }
                } else {
                    isUsernameAvailable = false;
                }

                if (isUsernameAvailable) {
                    user.setUsername(username);
                    break; // Kết thúc vòng lặp khi tên đăng nhập hợp lệ và không trùng lặp
                }
            }
        }

        // Nhập mật khẩu
        while (true) {
            System.out.println("Hãy nhập vào mật khẩu: ");
            String password = InputMethods.scanner().nextLine();

            if (Validate.isValidPassword(password)) {
                user.setPassword(password);
                break;
            }
        }

        // Nhập email
        while (true) {
            System.out.println("Hãy nhập vào email đăng ký: ");
            String email = InputMethods.scanner().nextLine();

            if (Validate.isValidEmail(email)) {
                boolean isEmailAvailable = true;

                if (users != null) {
                    for (User existingUser : users) {
                        if (existingUser.getEmail().trim().equals(email)) {
                            printlnError("Email đã được sử dụng, mời nhập email mới.");
                            isEmailAvailable = false;
                            break;
                        }
                    }
                } else {
                    isEmailAvailable = false;
                }

                if (isEmailAvailable) {
                    user.setEmail(email);
                    break; // Kết thúc vòng lặp khi email hợp lệ và không trùng lặp
                }
            }
        }

        // Nhập số điện thoại
        while (true) {
            System.out.println("Hãy nhập vào số điện thoại: ");
            String phone = InputMethods.scanner().nextLine();

            if (Validate.isValidPhone(phone)) {
                boolean isPhoneAvailable = true;
                if (users != null) {
                    for (User existingUser : userService.findAll()) {
                        if (existingUser.getPhone().trim().equals(phone)) {
                            printlnError("Số điện thoại đã được sử dụng, mời nhập số điện thoại mới.");
                            isPhoneAvailable = false;
                            break;
                        }
                    }
                } else {
                    isPhoneAvailable = false;
                }

                if (isPhoneAvailable) {
                    user.setPhone(phone);
                    break; // Kết thúc vòng lặp khi số điện thoại hợp lệ và không trùng lặp
                }
            }
        }

        // Nhập địa chỉ
        while (true) {
            System.out.println("Hãy nhập vào địa chỉ: ");
            String address = InputMethods.scanner().nextLine();

            if (Validate.isValidAddress(address)) {
                user.setAddress(address);
                break;
            }
        }
        user.setCreatedAt(LocalDate.now());

        // Đăng ký hoàn thành, trả về đối tượng User đã tạo
        return user;
    }



    private User login() {
        String pass;
        String userName;
        printlnMess("Thực hiện đăng nhập 🧡😍:");
        while (true) {
            System.out.println("UserName: ");
            userName = getString();
            if (isValidFullName(userName)) {
                break;
            }
        }
        while (true) {
            System.out.println("Password: ");
            pass = scanner().nextLine();
            if (isValidPassword(pass)) {
                break;
            }
        }
        User user;
        user = userService.login(userName, pass);

        if (user != null) {
            if (user.isImportance()) {
                userService.setStatusLogin(userName, ACTIVE);
                if (user.getRole() == ADMIN) {

                    displayAdminMenu();

                } else {
                    displayUserMenu();
                }

            } else {
                printlnError("Tài khoản của bạn đã bị khóa😂😂 !!");
                loginOrRegister();
            }


        } else {
            printlnError("Đăng nhập thấy bại,Mật khẩu hoặc UserName ko trùng hợp!!! ");
            loginOrRegister();

        }
        return user;
    }


    private void displayUserMenu() {
        int choice;
        do {
            print(PURPLE);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       😍🧡  QUẢN LÝ USER 😍😍       ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Trang chủ                ║");
            System.out.println("║   2    │    Giỏi hàng                ║");
            System.out.println("║   3    │    My Account               ║");
            System.out.println("║   4    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            printFinish();

            choice = getInteger();
            switch (choice) {
                case 1:
                    menuView.displayUserMenuProduct();
                    break;

                case 2:
                    cartView.displayMenuCart();
                    break;

                case 3:
                    menuView.MyAcount();
                    break;
                case 4:
                    logout();
                    break;
            }
        } while (choice != 5);
    }

    public void logout() {
        System.out.println("Bạn chắc chắn muốn thoát chứ ??");
        System.out.println("1. Có                2.Không");
        int choice = InputMethods.getInteger();
        if(choice == 1) {
            userService.setStatusLogin(userName, INACTIVE);
            loginOrRegister();
        }
    }

    public void displayAdminMenu() {
        int choice;
        do {
            print(PURPLE);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          😍🧡  ADMIN 😍😍           ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Quản lý người dùng       ║");
            System.out.println("║   2    │    Quản lý danh mục         ║");
            System.out.println("║   3    │    Quản lý sản phẩm         ║");
            System.out.println("║   4    │    Quản lý Đơn hàng         ║");
            System.out.println("║   5    │    Quay lại menu trước      ║");
            System.out.println("║   6    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            printFinish();

            choice = getInteger();
            switch (choice) {
                case 1:
                    userManagement();
                    break;

                case 2:
                    categoryView.displayAdminCategory();
                    break;
                case 3:
                    menuView.displayMenuAdminMenuProduct();
                    break;
                case 4:
                    orderHistoryView.menuAdminOrder();
                    break;
                case 5:
                    return;
                case 6:
                    logout();
                    break;
                default:
                    break;
            }
        } while (choice != 7);
    }


    private void userManagement() {
        int choice;

        do {
            print(CYAN);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       😍🧡  QUẢN LÝ USER 😍😍       ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Danh sách user           ║");
            System.out.println("║   2    │    Tìm kiếm user theo tên   ║");
            System.out.println("║   3    │    Khóa/ mở user            ║");
            System.out.println("║   4    │    Quay lại menu trước      ║");
            System.out.println("║   5    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            choice = getInteger();
            printFinish();

            switch (choice) {
                case 1:
                    displayUserList();
                    break;
                case 2:
                    displayUserByUserName();

                    break;
                case 3:
                    changeUserImportance();
                    break;
                case 4:
                    return;
                case 5:
                    logout();
                    break;
                default:
                    break;
            }
        } while (true);


    }

    private void changeUserImportance() {
        System.out.println("Hãy nhập username bạn muốn thay đổi trạng thái:");
        String username = getString();
        User user = userService.getUserByUsename(username);
        if (user == null) {
            printlnError("Không tìm thấy username bạn muốn đổi trạng thái !!");
        } else {
            if (user.getRole() == ADMIN) {
                printlnError("Không thể khóa user ADMIN !!");
            } else {
                userService.updateImportance((user.isImportance() == OPEN ? BLOCK : OPEN), username);
                printlnSuccess("Thay đổi trạng thái thành công!");
            }
        }

    }

    private void displayUserByUserName() {
        printlnMess("Nhập vào từ khóa tìm kiếm theo tên: !!");
        String username = getString();
        List<User> fitterUsers = userService.getUserListByUsername(username);
        for (User user : fitterUsers
        ) {
            user.display();
        }
    }

    private void displayUserList() {
        List<User> sortedUsers = userService.getSortedUserList();
        printlnMess("Danh sách user được sắp xếp theo tên !!!");
        for (User user : sortedUsers
        ) {
            user.display();

        }
    }

}
