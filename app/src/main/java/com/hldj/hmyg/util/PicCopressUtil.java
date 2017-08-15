package com.hldj.hmyg.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class PicCopressUtil {


    public void compress(String path) throws IOException {
        //先将所选图片转化为流的形式，path所得到的图片路径
        FileInputStream is = new FileInputStream(path);
        //定义一个file，为压缩后的图片
        File f = new File("图片保存路径", "图片名称");
        int size = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        //将图片缩小为原来的  1/size ,不然图片很大时会报内存溢出错误
        Bitmap image = BitmapFactory.decodeStream(is, null, options);

        is.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100表示不压缩，将不压缩的数据存放到baos中
        int per = 100;
        while (baos.toByteArray().length / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, per, baos);// 将图片压缩为原来的(100-per)%，把压缩后的数据存放到baos中
            per -= 10;// 每次都减少10

        }
        //回收图片，清理内存
        if (image != null && !image.isRecycled()) {
            image.recycle();
            image = null;
            System.gc();
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        baos.close();
        FileOutputStream os;
        os = new FileOutputStream(f);
        //自定义工具类，将输入流复制到输出流中
//        StreamTransferUtils.CopyStream(btinput, os);
//        btinput.close();
        os.close();
    }
}
