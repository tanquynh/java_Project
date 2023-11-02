package ra.service;
import ra.models.Cart;
import ra.models.User;
import ra.repository.IShop;

import java.util.ArrayList;
import java.util.List;
public class CartService implements IShop<Cart> {
    private UserService userService;
    public CartService(UserService userService) {
        this.userService = userService;
    }
    public User userLogin() {
        User userLogin = userService.userActive();
        return userLogin;
    }


    @Override
    public void save(Cart cart) {
        User user = userLogin();
        List<Cart> carts = user.getCart();
        Cart existingCart = findByProductId(cart.getProduct().getProductId());
        if (existingCart != null) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            int index = findByIndex(cart.getProduct().getProductId());
            carts.set(index, existingCart);

        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
            carts.add(cart);
        }

        userService.save(user);
    }

    @Override
    public List<Cart> findAll() {
        return userLogin().getCart();
    }





    @Override
    public Cart findById(int id) {
        for (Cart ci : userLogin().getCart()) {
            if (ci != null && ci.getCartId() == id) {
                return ci;
            }
        }
        return null;
    }

    public Cart findByProductId(int id) {
        for (Cart ci : userLogin().getCart()) {
            if (ci.getProduct().getProductId() == id) {
                return ci;
            }
        }
        return null;
    }

    @Override
    public void delete(Cart cart) {

    }


    public void deleteCart(int index) {
        User user = userLogin();
        List<Cart> carts = user.getCart();
        carts.remove(index);
        user.setCart(carts);
        for (Cart ca : carts
        ) {
            ca.display();
        }
        userService.save(user);
    }

    public void deleteAll() {
        User user = userLogin();
        user.setCart(new ArrayList<>());
        userService.save(user);
    }

    @Override
    public void update(List<Cart> t) {
        User user = userLogin();
     userLogin().setCart(t);
        userService.save(user);
    }


    @Override
    public int findByIndex(int id) {
        List<Cart> carts = findAll();
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getCartId() == id) {
                return i;
            }
        }
        return -1;
    }

    public int autoInc() {
        int max = 0;
        for (Cart cart : userLogin().getCart()) {
            if (cart.getCartId() > max) {
                max = cart.getCartId();
            }
        }
        return max + 1;
    }


    public List<Cart> cartAll() {
        return userLogin().getCart();
    }


}
