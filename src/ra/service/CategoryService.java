package ra.service;

import ra.models.User;
import ra.service.FileService.FileRepo;
import ra.models.Category;
import ra.repository.IShop;

import java.util.List;

public class CategoryService implements IShop<Category> {

    private final FileRepo file;

    public CategoryService(FileRepo file) {
        this.file = file;
    }


    @Override
    public int autoInc() {
        int max = 0;
        for (Category ca : findAll()) {
            if (max < ca.getCategoryId()) {
                max = ca.getCategoryId();
            }
        }
        return max + 1;
    }


    @Override
//    public void save(Category category) {
//        List<Category> categories = findAll();
//        categories.add(category);
//        file.saveToFile(categories);
//    }
    public void save(Category category) {
        List<Category> categories = findAll();
        int index = -1;

        for (int i = 0; i < categories.size(); i++) {
            if (category.getCategoryId() == categories.get(i).getCategoryId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            categories.set(index, category); // Cập nhật danh mục hiện có
        } else {
            categories.add(category); // Thêm danh mục mới nếu không tồn tại
        }

        file.saveToFile(categories); // Lưu danh sách danh mục vào tệp
    }


    @Override
    public void delete(Category category) {
        List<Category> categories = findAll();
        categories.remove(category);
        file.saveToFile(categories);
    }

    @Override
    public void update(List<Category> t) {
        file.saveToFile(t);
    }

    @Override
    public int findByIndex(int id) {
        List<Category> categories = findAll();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId() == id) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public List<Category> findAll() {
        List<Category> allCategory = file.findAll();
        return allCategory;

    }

    @Override
    public Category findById(int id) {
        for (Category ca : findAll()) {
            if (ca.getCategoryId() == id) {
                return ca;
            }
        }
        return null;
    }

    public void updateCategoryStatus(boolean categoryStatus , int id) {
        List<Category> categories = file.findAll();
        for (Category category : categories) {
            if (category.getCategoryId() == id) {
                category.setCategoryStatus(categoryStatus);
            }
        }
        file.saveToFile(categories);
    }


}
