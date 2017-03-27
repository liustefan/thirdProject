package com.zkhk.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.withub.test.TMongoDb;
import com.zkhk.contoller.ReportController;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片压缩工具类
 * 
 * @author 谢美团
 * @version v1.0
 * @date 2015.11.17
 */
public class ImageUtils {
	
	private static Logger logger = Logger.getLogger(ImageUtils.class);
	
    private static Image img;
	private static int width;
	private static int height;
	
    /**
     * 将网络图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(byte[] bs) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bs);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将本地图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(InputStream input) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    	
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(input);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     * 
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
            String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
        }
    }
    


	
	
	private static void readFile(String fileName) throws IOException{
		File file = new File(fileName);// 读入文件
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}
	
	@SuppressWarnings("unused")
	private static void readFile(InputStream input) throws IOException{
		img = ImageIO.read(input);// 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 */
	public static void resizeFix(int w, int h,String fileName) throws IOException {
		readFile(fileName);
		if (width / height > w / h) {
			resizeByWidth(w,"");
		} else {
			resizeByHeight(h,"");
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public static void resizeByWidth(int w,String fileName) throws IOException {
		if(fileName.trim() != null && !"".equals(fileName.trim())){
			readFile(fileName);
		}
		int h = (int) (height * w / width);
		resize(w, h,"");
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public static void resizeByHeight(int h,String fileName) throws IOException {
		if(fileName.trim() != null && !"".equals(fileName.trim())){
			readFile(fileName);
		}
		int w = (int) (width * h / height);
		resize(w, h,"");
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public static void resize(int w, int h,String fileName) throws IOException {
		if(fileName.trim() != null && !"".equals(fileName.trim())){
			readFile(fileName);
		}
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File("D:\\test\\2.jpg");
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
	}
	

	/**
	 * 强制压缩/放大图片到固定的大小
	 * @param w
	 * @param h
	 * @param input
	 * @return
	 */
	public static InputStream resize(int w, int h,InputStream input){
		ByteArrayOutputStream out = null; 
		try{
			readFile(input);
			BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
			
			out = new ByteArrayOutputStream();
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码		
			return new ByteArrayInputStream(out.toByteArray());
		}catch(Exception e){
			logger.error("图片压缩异常", e);
			return null;
		}finally{
			try {  
                if(out !=null){
                	out.close() ;  
                }
            } catch (IOException e) {  
            	logger.error("ByteArrayOutputStream closed exception", e);  
            }  
		}

	}
	
	
	

	public static void main(String[] args) throws Exception {
/*		long start = System.currentTimeMillis();
		System.out.println("压缩开始：");
		ImageUtils.resize(40, 40, "D:\\test\\1.jpg");
		System.out.println("压缩结束,用时：" + (System.currentTimeMillis() - start));*/
		TMongoDb db = new TMongoDb();
		db.getFile();

	}


}

