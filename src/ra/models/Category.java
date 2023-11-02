package ra.models;

import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private int categoryId;
    private String categoryName;
    private String categoryDes;
    private boolean categoryStatus;
    public Category() {
    }

    public Category(int categoryId, String categoryName, String categoryDes, boolean categoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDes = categoryDes;
        this.categoryStatus = categoryStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDes() {
        return categoryDes;
    }

    public void setCategoryDes(String categoryDes) {
        this.categoryDes = categoryDes;
    }

    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public void displayCategory() {
        System.out.println("Mã danh mục: " + this.categoryId + " -- Tên danh mục: " + this.categoryName + " -- Trạng thái danh mục: " + (this.categoryStatus == true ? "UNHIDE" : "HIDE"));
    }
}
