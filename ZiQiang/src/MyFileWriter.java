
import java.io.File;
import java.io.FileWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuanHao
 */
public class MyFileWriter {

    public static boolean writeIntoFile(String filePath, String content, boolean isAppend) {
        boolean isSuccess = true;
        //去掉文件名
        int index = filePath.lastIndexOf("/");
        String dir = filePath.substring(0, index);
        //创建路径
        File fileDir = new File(dir);
        fileDir.mkdirs();
        //创建文件夹下的文件
        File file = null;
        try {
            file = new File(filePath);
            file.createNewFile();

        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, isAppend);
            fileWriter.write(content);
            fileWriter.flush();

        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {

                    fileWriter.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return isSuccess;

    }
    public static void main(String args[]){
        MyFileWriter.writeIntoFile("C:/hehe/123.txt", "你忙吧", true);
    }

}
