package ra.views;

import ra.config.InputMethods;
import ra.config.Validate;
import ra.models.Cart;
import ra.models.Category;
import ra.models.Product;
import ra.models.User;
import ra.service.CartService;
import ra.service.CategoryService;
import ra.service.MenuService;
import ra.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ra.config.ConsoleColor.*;
import static ra.config.InputMethods.*;
import static ra.constant.Contant.ProductStatus.Hide;
import static ra.constant.Contant.ProductStatus.UnHide;

public class MenuView {
    private CartView cartView;
    private MenuService menuService;
    private CartService cartService;
    private CategoryService categoryService;
    private UserService userService;
    private UserView userView;

    public MenuView(CartView cartView, MenuService menuService, CartService cartService, CategoryService categoryService, UserService userService) {
        this.cartView = cartView;
        this.menuService = menuService;
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.userView = userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }


    public void displayUserMenuProduct() {
        int choice;

        do {
            print(BLUE);
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.println("║                😍🧡USER-PRODUCT😍😍              ║");
            System.out.println("╟────────┬──────────────────────────────────────────║");
            System.out.println("║   1    │    Tìm kiếm sản phẩm                     ║");
            System.out.println("║   2    │    Hiển thị sản phẩm theo danh mục       ║");
            System.out.println("║   3    │    Danh sách sản phẩm                    ║");
            System.out.println("║   4    │    Hiển thị theo giá giảm dần            ║");
            System.out.println("║   5    │    Thêm vào  giỏ hàng                    ║");
            System.out.println("║   6    │    Giỏ hàng                              ║");
            System.out.println("║   7    │    Quay lại menu trước                   ║");
            System.out.println("║   8    │    Đăng xuất                             ║");
            System.out.println("╚════════╧══════════════════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡: ");
            printFinish();
            choice = getInteger();

            switch (choice) {
                case 1:
                    searchProduct();
                    break;
                case 2:
                    showProductByCategory();
                    break;
                case 3:
                    displayProductList();
                    break;
                case 4:
                    SortProduct();
                    break;
                case 5:
                    addToCart();
                    break;
                case 6:
                    cartView.displayMenuCart();
                    break;
                case 7:
                    return;
                case 8:
                    userView.logout();
                default:
                    break;
            }

        } while (choice != 5);
    }


    public List<Product> showProductByCategory() {
        List<Product> products = menuService.findAll();
        List<Category> categories = categoryService.findAll();
        List<Product> findProducts = new ArrayList<>();

        if (categories.isEmpty()) {
            System.err.println("Danh sách Category rỗng");
        } else {
            System.out.println("Danh sách các Category có sẵn:");
            for (Category category : categories) {
                category.displayCategory();
            }
        }

        boolean categoryFound = false;
        int searchId = -1;

        while (!categoryFound) {
            System.out.println("Mời bạn nhập id category:");
            searchId = getInteger();

            for (Category category : categories) {
                if (category.getCategoryId() == searchId) {
                    categoryFound = true;
                    break;
                }
            }

            if (!categoryFound) {
                System.err.println("ID không hợp lệ, mời nhập lại.");
            }
        }

        for (Product product : products) {
            if (product.getCategory().getCategoryId() == searchId) {
                findProducts.add(product);
            }
        }

        if (findProducts.isEmpty()) {
            System.err.println("Không tìm thấy sản phẩm trong danh mục này.");
        } else {
            System.out.println("Danh sách sản phẩm trong danh mục:");
            for (Product product : findProducts) {
                product.display();
            }
        }

        return findProducts;
    }

    private void addToCart() {
        List<Product> products = menuService.findAll();
        if (products.isEmpty()) {
            printlnError("Chưa có sản phẩm");
            return;
        }
        for (Product product : products) {
            System.out.println("ID: " + product.getProductId() + ", Name: " + product.getProductName());
        }
        System.out.println("Nhập vào ID sản phẩm để thêm vào giỏ hàng");
        int idPro;
       while (true) {
            idPro = getInteger();

       }
        Product product = menuService.findById(idPro);
        if (product == null) {
            System.err.println("Không tìm thấy sản phẩm với ID " + idPro);
            return;
        }
        Cart cart = new Cart();
        cart.setProduct(product);

        cart.setCartId(cartService.autoInc());

        while (true) {
            System.out.println("Nhập vào số lượng muốn thêm vào giỏ hàng: ");
            int count = getInteger();

            if (count > product.getStock()) {
                printlnError("Số lượng này lớn hơn hàng chúng tôi có sẵn. Vui lòng giảm số lượng xuống.");
            } else {
                cart.setQuantity(count);
                break;
            }
        }

        printlnSuccess("Thêm vào  giỏ hàng thành công🎈🎈!!");
        cartService.save(cart);
    }


    private void searchProduct() {
        List<Product> products = menuService.getSerchProduct();
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống!!");

        } else {
            System.out.println("Danh sách sản phẩm");
            for (Product product : products
            ) {
                product.display();
            }
        }
    }

    private void displayProductList() {
        List<Product> productList = menuService.getProductList();
        System.out.println("Danh sách sản phẩm!!!");
        for (Product product : productList) {
            product.display();
        }
//
    }

    private void SortProduct() {
        List<Product> sortProduct = menuService.getSortPriceproducts();
        if (sortProduct.isEmpty()) {
            System.out.println("Danh sách rỗng !!!");
        } else {
            System.out.println("Danh sách đã được sắp xếp theo giá:");
            for (Product product : sortProduct) {
                product.display();
            }
        }
    }

    public void MyAcount() {
        int choice;
        do {

            print(BLUE);
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║             😍🧡USER-ACOUNT😍😍          ║");
            System.out.println("╟────────┬──────────────────────────────────╢");
            System.out.println("║   1    │    Đổi mật khẩu                  ║");
            System.out.println("║   2    │    Hiển thị thông tin cá nhân    ║");
            System.out.println("║   3    │    Chỉnh sửa thông tin cá nhân   ║");
            System.out.println("║   4    │    Quay lại menu trước           ║");
            System.out.println("║   5    │    Đăng xuất                     ║");
            System.out.println("╚════════╧══════════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡: ");
            printFinish();

            choice = getInteger();
            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    showInforUser();
                    break;
                case 3:
                    changeInforUser();
                    break;
                case 4:
                    return;
                case 5:
                    userView.logout();
                    break;
                default:
                    break;
            }

        } while (choice != 5);
    }

    private void changeInforUser() {
        System.out.println("Thay đổi thông tin User");
        int userId = userService.userActive().getId();
        List<User> users = userService.findAll();
        User user = userService.findById(userId);
        while (true) {
            System.out.println("Hãy nhâp vào họ và tên đầy đủ :(Enter để bỏ qua) ");
            String fullName = InputMethods.scanner().nextLine();
            if (fullName.isEmpty()) {
                break;
            } else if (Validate.isValidFullName(fullName)) {
                user.setFullName(fullName);
                break;
            }
        }

        while (true) {
            System.out.println("Hãy nhập tên đăng nhập mới: (Enter để bỏ qua)");
            String username = scanner().nextLine();
            if (username.isEmpty()) {
                break;
            } else if (Validate.isValidFullName(username)) {
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

        while (true) {
            System.out.println("Hãy nhập vào mật khẩu mới: (Enter để bỏ qua)");
            String password = scanner().nextLine();
            if (password.isEmpty()) {
                break;
            } else if (Validate.isValidPassword(password)) {
                user.setPassword(password);
                break;
            }
        }

        while (true) {
            System.out.println("Hãy nhập vào email mới:(Enter để bỏ qua) ");
            String email = scanner().nextLine();
            if (email.isEmpty()) {
                break;
            } else if (Validate.isValidEmail(email)) {
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

        while (true) {
            System.out.println("Hãy nhập vào số điện thoại: (Enter để bỏ qua) ");
            String phone = scanner().nextLine();
            if (phone.isEmpty()) {
                break;
            } else if (Validate.isValidPhone(phone)) {
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
            System.out.println("Hãy nhập vào địa chỉ: (Enter để bỏ qua) ");
            String address = scanner().nextLine();
            if (address.isEmpty()) {
                break;
            } else if (Validate.isValidAddress(address)) {
                user.setAddress(address);
                break;
            }

        }
        user.setUpdatedAt(LocalDate.now());
        userService.save(user);
        System.out.println("Thay đổi thông tin thành công!");

    }

    private void showInforUser() {
        System.out.println("THÔNG TIN USER");
        int userId = userService.userActive().getId();
        User user = userService.findById(userId);
        user.display();
    }

    private void changePassword() {
        System.out.println("Mời bạn nhập mật khẩu cũ:");
        String oldPassword = scanner().nextLine();

        if (userService.userActive().getPassword().equals(oldPassword)) {
            boolean newPasswordValid = false;

            while (!newPasswordValid) {
                System.out.println("Hãy nhập vào mật khẩu mới:");
                String newPassword = InputMethods.scanner().nextLine();

                if (Validate.isValidPassword(newPassword)) {
                    int userId = userService.userActive().getId();
                    User user = userService.findById(userId);
                    user.setPassword(newPassword);
                    userService.save(user);
                    newPasswordValid = true;
                    System.out.println("Đổi mật khẩu thành công!");
                } else {
                    System.err.println("Mật khẩu mới không hợp lệ. Hãy thử lại.");
                }
            }
        } else {
            System.err.println("Mật khẩu cũ không chính xác. Thay đổi mật khẩu thất bại.");
        }
    }


    public void displayMenuAdminMenuProduct() {
        int choice;
        do {

            print(BLUE);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          😍🧡ADMIN-PRODUCT😍😍      ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Thêm mới sản phẩm        ║");
            System.out.println("║   2    │    Hiển thị ds sản phẩm     ║");
            System.out.println("║   3    │    Sửa sản phẩm             ║");
            System.out.println("║   4    │    Ẩn sản phẩm theo mã      ║");
            System.out.println("║   5    │    Ẩn nhiều sản phẩm        ║");
            System.out.println("║   6    │    Tìm kiếm sản phẩm        ║");
            System.out.println("║   7    │    Quay lại menu trước      ║");
            System.out.println("║   8    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡: ");
            printFinish();

            choice = getInteger();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProductList();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    hideProduct();
                    break;
                case 5:
                    hideAllProduct();
                    break;
                case 6:
                    searchProduct();
                    return;
                case 7:
                    return;
                case 8:
                    userView.logout();
                    break;
                default:
                    break;
            }

        } while (choice != 5);
    }

    private void hideProduct() {
        System.out.println("Hãy nhập id sản phẩm bạn muốn thay đổi trạng thái:");
        int idProduct = getInteger();
        Product product = menuService.findById(idProduct);
        if (product == null) {
            printlnError("Không tìm thấy sản phẩm bạn muốn đổi trạng thái !!");
        } else {

            menuService.updateProductStatus((product.isProductStatus() == Hide ? UnHide : Hide), idProduct);

            printlnSuccess("Thay đổi trạng thái thành công!");
        }
    }


    private void hideAllProduct() {
        List<Product> products = menuService.findAll();
        System.out.println("Nhập danh sách mã sản phẩm cần ẩn/hiện (cách nhau bằng dấu phẩy):");
        String inputIds = scanner().nextLine();
        // Tách danh sách mã danh mục thành mảng các ID
        String[] idStrings = inputIds.split(",");
        boolean anyChanges = false;

        for (String idString : idStrings) {
            try {
                int idProduct = Integer.parseInt(idString);
                Product product = menuService.findById(idProduct);

                if (product == null) {
                    System.err.println("ID " + idProduct + " không tồn tại.");


                } else {
                    boolean newStatus = (product.isProductStatus() == Hide) ? UnHide : Hide;
                    menuService.updateProductStatus(newStatus, idProduct);
                    anyChanges = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: " + idString + " không phải là một số nguyên hợp lệ.");
            }
        }

        if (anyChanges) {
            printlnSuccess("Thay đổi trạng thái thành công!");
            // Lưu trạng thái của danh mục sau khi thay đổi

        }
    }

    private void editProduct() {
        System.out.println("Nhập ID sản phẩm cần sửa: ");
        int id = getInteger();
        List<Product> products = menuService.findAll();
        int index = -1; // Khởi tạo index bằng -1 để xác định xem sản phẩm có tồn tại hay không

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == id) {
                index = i;
                break; // Thoát vòng lặp khi tìm thấy sản phẩm với ID tương ứng
            }
        }

        if (index != -1) {
            Product productToEdit = products.get(index);
            boolean isExit = false;

            while (true) {
                System.out.println("Nhập tên sản phẩm mới (Enter để bỏ qua):");
                String productName = scanner().nextLine();
                if (!productName.trim().isEmpty()) {
                    boolean isNameExists = false;

                    for (Product pro : products) {
                        if (pro.getProductId() != id && pro.getProductName().equalsIgnoreCase(productName)) {
                            isNameExists = true;
                            System.err.println("Tên sản phẩm đã tồn tại, mời nhập tên mới.");
                            break;
                        }
                    }

                    if (!isNameExists) {
                        productToEdit.setProductName(productName);
                        break; // Kết thúc vòng lặp khi tên hợp lệ và không trùng lặp
                    }
                } else {
                    break;
                }
            }

            // Nhập giá sản phẩm
            System.out.println("Nhập giá sản phẩm (Enter để bỏ qua):");
            while (true) {
                String priceInput = scanner().nextLine();
                if (priceInput.trim().isEmpty()) {
                    break;
                }
                try {
                    double price = Double.parseDouble(priceInput);
                    if (price >= 0) {
                        productToEdit.setPrice(price);
                        break;
                    } else {
                        System.err.println("Giá sản phẩm phải lớn hơn hoặc bằng 0.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi: Giá sản phẩm không hợp lệ.");
                }
            }

            System.out.println("Nhập mô tả sản phẩm (Enter để bỏ qua):");
            while (true) {
                String productDes = scanner().nextLine();
                if (productDes.isEmpty()) {
                    break;
                } else {
                    productToEdit.setProductDes(productDes);
                    break;
                }
            }


            // Nhập số lượng
            System.out.println("Nhập số lượng (Enter để bỏ qua):");
            while (true) {
                String stockInput = scanner().nextLine();
                if (stockInput.trim().isEmpty()) {
                    break;
                }
                try {
                    int stock = Integer.parseInt(stockInput);
                    if (stock >= 0) {
                        productToEdit.setStock(stock);
                        break;
                    } else {
                        System.err.println("Số lượng sản phẩm phải lớn hơn hoặc bằng 0.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi: Số lượng sản phẩm không hợp lệ.");
                }
            }

            System.out.println("Danh sách danh mục:");
            List<Category> categories = categoryService.findAll();
            for (Category category : categories) {
                category.displayCategory();
            }

            System.out.println("Nhập ID danh mục mới (Enter để bỏ qua):");
            while (!isExit) {
                int newCategoryId = getInteger();
                if (newCategoryId == 0) {
                    break; // Người dùng bỏ qua việc nhập danh mục mới
                } else {
                    Category newCategory = categoryService.findById(newCategoryId);
                    if (newCategory != null) {
                        productToEdit.setCategory(newCategory);
                        isExit = true; // Thoát khỏi vòng lặp sau khi nhập thành công ID danh mục
                    } else {
                        System.err.println("Danh mục không tồn tại. Mời nhập lại.");
                    }
                }
            }

            menuService.save(productToEdit); // Cập nhật thông tin sản phẩm
            System.out.println("Sửa sản phẩm thành công");
        } else {
            System.err.println("Không tìm thấy sản phẩm cần sửa !!!");
        }
    }


    private void addProduct() {
        System.out.println("Nhập số sản phẩm cần thêm mới:");
        int numberOfProducts = getInteger();

        if (numberOfProducts <= 0) {
            System.err.println("Số sản phẩm phải lớn hơn 0");
            return; // Thoát ngay khi số lượng không hợp lệ
        }

        for (int i = 0; i < numberOfProducts; i++) {
            List<Product> products = menuService.findAll();
            System.out.println("Sản phẩm thứ " + (i + 1));
            Product product = new Product();

            // Nhập tên sản phẩm và kiểm tra xem tên đã tồn tại chưa
            while (true) {
                System.out.println("Nhập tên sản phẩm:");
                String productName = getString();
                boolean isNameExists = false;

                for (Product pro : products) {
                    if (pro.getProductName().equalsIgnoreCase(productName)) {
                        isNameExists = true;
                        System.err.println("Tên sản phẩm đã tồn tại, mời nhập tên mới.");
                        break;
                    }
                }

                if (!isNameExists) {
                    product.setProductName(productName);
                    break; // Kết thúc vòng lặp khi tên hợp lệ và không trùng lặp
                }
            }

            // Nhập giá sản phẩm
            System.out.println("Nhập giá sản phẩm:");
            double price = InputMethods.getDouble();
            product.setPrice(price);

            System.out.println("Nhập mô tả sản phẩm:");
            String productDes = getString();
            product.setProductDes(productDes);

            // Nhập số lượng
            System.out.println("Nhập số lượng:");
            int quantity = getInteger();
            product.setQuantity(quantity);

            // Hiển thị danh sách danh mục
            List<Category> categories = categoryService.findAll();
            if (categories.isEmpty()) {
                printlnError("Danh sách danh mục rỗng. Vui lòng thêm danh mục trước!!");
                return; // Thoát nếu không có danh mục
            }

            System.out.println("Chọn danh mục cho sản phẩm:");
            for (Category category : categories) {
                category.displayCategory();
            }

            while (true) {
                System.out.println("Nhập id danh mục sản phẩm:");
                int categoryId = getInteger();
                Category selectedCategory = null;

                // Tìm danh mục được chọn bởi người dùng
                for (Category category : categories) {
                    if (category.getCategoryId() == categoryId) {
                        selectedCategory = category;
                        break;
                    }
                }

                if (selectedCategory != null) {
                    product.setCategory(selectedCategory);
                    product.setProductStatus(UnHide);
                    product.setProductId(menuService.autoInc());
                    menuService.save(product);
                    System.out.println("Tạo sản phẩm thành công");
                    break; // Kết thúc vòng lặp sau khi sản phẩm đã được tạo
                } else {
                    System.out.println("Id danh mục không tồn tại, mời nhập lại");
                }
            }
        }
    }


}
