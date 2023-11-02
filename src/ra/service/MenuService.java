package ra.service;

import ra.models.Category;
import ra.models.Product;
import ra.repository.IShop;
import ra.service.FileService.FileRepo;

import java.util.ArrayList;
import java.util.List;

import static ra.config.ConsoleColor.*;
import static ra.config.InputMethods.getInteger;
import static ra.config.InputMethods.scanner;

public class MenuService implements IShop<Product> {
    private FileRepo file;

    private List<Product> products = new ArrayList<>();

    public MenuService(FileRepo file) {
        this.file = file;
    }

    @Override
//    public void save(Product product) {
//        products = file.findAll();
//        products.add(product);
//        file.saveToFile(products);
//    }
    public void save(Product product) {
        List<Product> products = findAll();
        int index = -1;

        for (int i = 0; i < products.size(); i++) {
            if (product.getProductId() == products.get(i).getProductId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            products.set(index, product); // Cập nhật sản phẩm hiện có
        } else {
            products.add(product); // Thêm sản phâmc mới nếu không tồn tại
        }

        file.saveToFile(products); // Lưu danh sách sản phẩm vào tệp
    }
    @Override
    public List<Product> findAll() {
        List<Product> products = file.findAll();
        return products;
    }

    @Override
    public Product findById(int id) {
        List<Product> products = file.findAll();
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void update(List<Product> t) {
//        products = file.findAll();
        file.saveToFile(t);
    }

    @Override
    public int findByIndex(int id) {
        products = file.findAll();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int autoInc() {
        int max = 0;
        for (Product product : findAll()) {
            if (max < product.getProductId()) {
                max = product.getProductId();
            }
        }
        return max + 1;
    }

    public List<Product> getSortPriceproducts() {
        List<Product> sortProducts = file.findAll();
        sortProducts.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        return sortProducts;
    }

    public List<Product> getProductList() {
        List<Product> products = file.findAll();
        return products;
//        if (products.size() > 10) {
//            List<Product> find10Product = new ArrayList<>();
//            for (int i = 0; i < 10 && i < products.size(); i++) {
//                find10Product.add(products.get(i));
//            }
//            return find10Product;
//        } else {
//            return products;
//        }
    }

    public List<Product> getSerchProduct() {
        List<Product> products = file.findAll();
        List<Product> findProduct = new ArrayList<>();
        System.out.println("Mời nhập tên sản phẩm cần tìm kiếm!!");
        String searchName = scanner().nextLine();
        if(products.isEmpty()) {
            return new ArrayList<>();
        } else {
            for (Product product: products) {
                if(product.getProductName().toLowerCase().trim().contains(searchName.trim().toLowerCase())) {
                    findProduct.add(product);
                }
            }
            return findProduct;
        }
    }




//    public List<Product> showProductByStyle() {
//        List<Product> products = file.findAll();
//        List<Category> categories = file.findAll();
//        List<Product> findProduct = new ArrayList<>();
//        for (Category category: categories) {
//            System.out.println(category);
//        }
//        System.out.println("Mời bạn nhập style");
//        String searchStyle = scanner().nextLine();
//        for (Product product: products) {
//            if(product.getStyle().trim().toLowerCase().equals(searchStyle.trim().toLowerCase())) {
//                findProduct.add(product);
//            }
//        }
//
//        return products;
//    }

    public void updateProductStatus(boolean Status , int id) {
        List<Product> productList = file.findAll();
        for (Product product : productList) {
            if (product.getProductId() == id) {
                product.setProductStatus(Status);
            }
        }

        file.saveToFile(productList);
    }

    public void updateQuantity(Product product) {
        List<Product> allMenu = file.findAll();
        // Tìm sản phẩm trong danh sách
        for (Product existingProduct : allMenu) {
            if (existingProduct.getProductId() == product.getProductId()) {
                // Cập nhật số lượng
                existingProduct.setStock(product.getStock());
                break;
            }
        }
        file.saveToFile(allMenu);
    }
}
