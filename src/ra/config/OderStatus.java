package ra.config;

public class OderStatus {
    public  static  byte WAITING =0;
    public  static  byte ACCEPT =1;
    public  static  byte CANCEL =2;



    public static String getStatusByCode(byte code){

        switch (code){
            case 0 :
                return "Đang chờ xác nhận";
            case 1:
                return "Đã được chấp nhận";
            case 2:
                return "Đã bị hủy";
            default:
                return  "Không hợp lệ";
        }
    }
}
