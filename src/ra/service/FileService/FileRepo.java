package ra.service.FileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ra.constant.Contant.FilePath.COMMON_PATH;

public class FileRepo<T>{
    private File file;
    public  FileRepo(){

    }

    public FileRepo(String filePath) {
        File dataDir = new File(COMMON_PATH);
        if(!dataDir.exists()) {
            dataDir.mkdir();
        }
        this.file = new File(COMMON_PATH + filePath);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }

        } catch(IOException e) {
            System.out.println("Lỗi khi khởi tạo file");
        }
    }

    public void saveToFile(List<T> t) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(t);
            outputStream.close();
        } catch (IOException e){
            System.out.println("Có lỗi xảy ra khi ghi file");
        }
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            list = (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
        return list;
    }

}
