package ra.service;

import ra.models.Product;
import ra.models.User;
import ra.models.User;
import ra.repository.IShop;
import ra.service.FileService.FileRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ra.constant.Contant.Status.INACTIVE;

public class UserService implements IShop<User> {
    private FileRepo file;
    private List<User> users = new ArrayList<>();

    public UserService(FileRepo file) {
        this.file = file;
    }

    @Override

    public void save(User user) {
        List<User> users = findAll();
        int index = -1;

        for (int i = 0; i < users.size(); i++) {
            if (user.getId() == users.get(i).getId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            users.set(index, user); // Cập nhật user hiện có
        } else {
            users.add(user); // Thêm user mới nếu không tồn tại
        }

        file.saveToFile(users); // Lưu danh sách user vào tệp
    }

    @Override
    public List<User> findAll() {
        List<User> users = file.findAll();
        return users;
    }

    @Override
    public User findById(int id) {
        List<User> users = file.findAll();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;



    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(List<User> t) {
    }

    @Override
    public int findByIndex(int id) {
        return 0;
    }

    public User getUserByUsename(String userName) {
        users = file.findAll();
        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int autoInc() {
        int max = 0;
        List<User> users = findAll();
        if (users != null) {
            for (User user : users) {
                if (max < user.getId()) {
                    max = user.getId();
                }
            }
            return max + 1;
        } else {
            return max = -1;
        }
    }

    public User login(String userName, String pass) {
        User user = getUserByUsename(userName);
        if (user != null && user.getPassword().equals(pass)) {
            return user;
        }
        return null;
    }

    public void setStatusLogin(String userName, Boolean newStatus) {
        List<User> users = file.findAll();
        boolean foundUser = false;

        for (User user : users) {
            if (user.getUsername().equals(userName)) {
                user.setStatus(newStatus);
                foundUser = true;
            } else {
                user.setStatus(INACTIVE);
            }
        }

        if (!foundUser) {
            System.err.println("Không tìm thấy người dùng có tên đăng nhập: " + userName);
        } else {
            file.saveToFile(users);
        }
    }


    public User userActive() {
        List<User> users = file.findAll();
        for (User user : users) {
            if (user != null && user.isStatus()) {
                return user;
            }
        }
        return null;
    }

    public void updateImportance(boolean status, String username) {
        users = file.findAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setImportance(status);
            }
        }

        file.saveToFile(users);

    }

    public List<User> getUserListByUsername(String username) {
        List<User> allUsers = file.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for (User us : allUsers
        ) {
            if (us.getUsername().toLowerCase().contains(username.toLowerCase())) {
                filteredUsers.add(us);
            }
        }
        return filteredUsers;
    }

    public List<User> getSortedUserList() {
        List<User> sorteUsers = file.findAll();
        Collections.sort(sorteUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
        return sorteUsers;

    }

}
