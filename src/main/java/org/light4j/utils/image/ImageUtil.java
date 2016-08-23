package org.light4j.utils.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ͼ����<br>
 * ��ͼƬ����ѹ����ˮӡ�������任��͸��������ʽת������
 * 
 * @author longjiazuo
 */
public class ImageUtil {

    public static final float DEFAULT_QUALITY = 0.2125f ;
    
    
    /**
     * 
     * ���ͼƬˮӡ����(�������,ʹ��Ĭ�ϸ�ʽ)
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param markPath
     *            ˮӡͼƬ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @param destPath
     *                 �ļ����·��  
     * @throws Exception          
     * 
     */
     public static void addWaterMark(String imgPath, String markPath, int x, int y, float alpha,String destPath) throws Exception{
         try {
                BufferedImage bufferedImage = addWaterMark(imgPath, markPath, x, y, alpha);
                ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
            } catch (Exception e) {
                throw new RuntimeException("���ͼƬˮӡ�쳣");
            }
     }
    
        
    /**
     * 
     * ���ͼƬˮӡ����(�������,�Զ����ʽ)
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param markPath
     *            ˮӡͼƬ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @param format
     *                 ���ˮӡ��洢�ĸ�ʽ
     * @param destPath
     *                 �ļ����·��  
     * @throws Exception          
     * 
     */
     public static void addWaterMark(String imgPath, String markPath, int x, int y, float alpha,String format,String destPath) throws Exception{
         try {
                BufferedImage bufferedImage = addWaterMark(imgPath, markPath, x, y, alpha);
                ImageIO.write(bufferedImage,format , new File(destPath));
            } catch (Exception e) {
                throw new RuntimeException("���ͼƬˮӡ�쳣");
            }
     }
    
     
    /**
     * 
     * ���ͼƬˮӡ����,����BufferedImage����
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param markPath
     *            ˮӡͼƬ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @return
     *                 ������ͼƬ����
     * @throws Exception          
     * 
     */
    public static BufferedImage addWaterMark(String imgPath, String markPath, int x, int y, float alpha) throws Exception{
        BufferedImage targetImage = null;
        try {
                        // ���ش�����ͼƬ�ļ�
            Image img = ImageIO.read(new File(imgPath));

                        //����Ŀ��ͼ���ļ�
            targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            
                        // ����ˮӡͼƬ�ļ�
            Image markImg = ImageIO.read(new File(markPath));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(markImg, x, y, null);
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException("���ͼƬˮӡ�����쳣");
        }
        return targetImage;
       
    }

    /**
     * 
     * �������ˮӡ����(�������,ʹ��Ĭ�ϸ�ʽ)
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param text
     *            ˮӡ����    
     * @param font
     *            ˮӡ������Ϣ    ��дĬ��ֵΪ����
     * @param color
     *            ˮӡ������ɫ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @param format
     *                 ���ˮӡ��洢�ĸ�ʽ
     * @param destPath
     *                 �ļ����·��     
     * @throws Exception          
     */
    public static void addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha,String destPath) throws Exception{
        try {
            BufferedImage bufferedImage = addTextMark(imgPath, text, font, color, x, y, alpha);
            ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ�������ˮӡ�쳣");
        }
    }
    
    /**
     * 
     * �������ˮӡ����(�������,�Զ����ʽ)
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param text
     *            ˮӡ����    
     * @param font
     *            ˮӡ������Ϣ    ��дĬ��ֵΪ����
     * @param color
     *            ˮӡ������ɫ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @param format
     *                 ���ˮӡ��洢�ĸ�ʽ
     * @param destPath
     *                 �ļ����·��     
     * @throws Exception          
     */
    public static void addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha,String format,String destPath) throws Exception{
        try {
            BufferedImage bufferedImage = addTextMark(imgPath, text, font, color, x, y, alpha);
            ImageIO.write(bufferedImage, format, new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ�������ˮӡ�쳣");
        }
    }
    
    /**
     * 
     * �������ˮӡ����,����BufferedImage����
     * 
     * @param imgPath
     *            ������ͼƬ
     * @param text
     *            ˮӡ����    
     * @param font
     *            ˮӡ������Ϣ    ��дĬ��ֵΪ����
     * @param color
     *            ˮӡ������ɫ
     * @param x
     *            ˮӡλ��ͼƬ���Ͻǵ� x ����ֵ
     * @param y
     *            ˮӡλ��ͼƬ���Ͻǵ� y ����ֵ
     * @param alpha
     *            ˮӡ͸���� 0.1f ~ 1.0f
     * @return
     *                 ������ͼƬ����
     * @throws Exception          
     */

    public static BufferedImage addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha) throws Exception{
        BufferedImage targetImage = null;
        try {
            Font Dfont = (font == null) ? new Font("����", 20, 13) : font;    
            Image img = ImageIO.read(new File(imgPath));
                        //����Ŀ��ͼ���ļ�
            targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.setColor(color);
            g.setFont(Dfont);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawString(text, x, y);
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException("�������ˮӡ�����쳣");
        }
        return targetImage;
    }
    
    
    
    /**
     * 
     * 
     * 
     * ѹ��ͼƬ����(�ļ��������,ʹ��Ĭ�ϸ�ʽ)
     * 
     * @param imgPath
     *                 ������ͼƬ
     * @param quality
     *                 ͼƬ����(0-1֮�g��floatֵ)
     * @param width
     *                 ���ͼƬ�Ŀ��    ���븺��������ʾ��ԭ��ͼƬ��
     * @param height
     *                 ���ͼƬ�ĸ߶�    ���븺��������ʾ��ԭ��ͼƬ��
     * @param autoSize
     *                 �Ƿ�ȱ����� true��ʾ���еȱ����� false��ʾ�����еȱ�����
     * @param format
     *                 ѹ����洢�ĸ�ʽ
     * @param destPath
     *                 �ļ����·��
     * 
     * @throws Exception
     */
    public static void compressImage(String imgPath,float quality,int width, int height, boolean autoSize,String destPath)throws Exception{
        try {
            BufferedImage bufferedImage = compressImage(imgPath, quality, width, height, autoSize);
            ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬѹ���쳣");
        }
        
    }
    
    
    /**
     * 
     * ѹ��ͼƬ����(�ļ��������,���Զ����ʽ)
     * 
     * @param imgPath
     *                 ������ͼƬ
     * @param quality
     *                 ͼƬ����(0-1֮�g��floatֵ)
     * @param width
     *                 ���ͼƬ�Ŀ��    ���븺��������ʾ��ԭ��ͼƬ��
     * @param height
     *                 ���ͼƬ�ĸ߶�    ���븺��������ʾ��ԭ��ͼƬ��
     * @param autoSize
     *                 �Ƿ�ȱ����� true��ʾ���еȱ����� false��ʾ�����еȱ�����
     * @param format
     *                 ѹ����洢�ĸ�ʽ
     * @param destPath
     *                 �ļ����·��
     * 
     * @throws Exception
     */
    public static void compressImage(String imgPath,float quality,int width, int height, boolean autoSize,String format,String destPath)throws Exception{
        try {
            BufferedImage bufferedImage = compressImage(imgPath, quality, width, height, autoSize);
            ImageIO.write(bufferedImage, format, new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬѹ���쳣");
        }
    }
    
    
    /**
     * 
     * ѹ��ͼƬ����,����BufferedImage����
     * 
     * @param imgPath
     *                 ������ͼƬ
     * @param quality
     *                 ͼƬ����(0-1֮�g��floatֵ)
     * @param width
     *                 ���ͼƬ�Ŀ��    ���븺��������ʾ��ԭ��ͼƬ��
     * @param height
     *                 ���ͼƬ�ĸ߶�    ���븺��������ʾ��ԭ��ͼƬ��
     * @param autoSize
     *                 �Ƿ�ȱ����� true��ʾ���еȱ����� false��ʾ�����еȱ�����
     * @return
     *                 ������ͼƬ����
     * @throws Exception
     */
    public static BufferedImage compressImage(String imgPath,float quality,int width, int height, boolean autoSize)throws Exception{
        BufferedImage targetImage = null;
        if(quality<0F||quality>1F){
            quality = DEFAULT_QUALITY;
        }
        try {
            Image img = ImageIO.read(new File(imgPath));
                        //����û������ͼƬ�����Ϸ����û�����ĸ���,��ֵ������ʾִ��Ĭ��ֵ
            int newwidth =( width > 0 ) ? width : img.getWidth(null);
                        //����û������ͼƬ�����Ϸ����û�����ĸ���,��ֵ������ʾִ��Ĭ��ֵ
            int newheight = ( height > 0 )? height: img.getHeight(null);    
                        //���������Ӧ��С����б�������
            if(autoSize){                                                    
                        // Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
                double Widthrate = ((double) img.getWidth(null)) / (double) width + 0.1;
                double heightrate = ((double) img.getHeight(null))/ (double) height + 0.1;
                double rate = Widthrate > heightrate ? Widthrate : heightrate;
                newwidth = (int) (((double) img.getWidth(null)) / rate);
                newheight = (int) (((double) img.getHeight(null)) / rate);
            }
                        //����Ŀ��ͼ���ļ�
            targetImage = new BufferedImage(newwidth,newheight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, newwidth, newheight, null);
                        //������ˮӡ��������������������,����ӵĻ�ֱ�ӷ���Ŀ���ļ�----------------------
            g.dispose();
            
        } catch (Exception e) {
            throw new RuntimeException("ͼƬѹ�������쳣");
        }
        return targetImage;
    }
    
    
  
    /**
     * ͼƬ�ڰ׻�����(�ļ��������,ʹ��Ĭ�ϸ�ʽ)
     * 
     * @param bufferedImage
     *                 �����ͼƬ����
     * @param destPath
     *                 Ŀ���ļ���ַ
     * @throws Exception  
     *
     */
    public static void imageGray(String imgPath, String destPath)throws Exception{
        imageGray(imgPath, imageFormat(imgPath), destPath);
    }
    
    
    /**
     * ͼƬ�ڰ׻�����(�ļ��������,���Զ����ʽ)
     * 
     * @param bufferedImage
     *                 �����ͼƬ����
     * @param format
     *                 ͼƬ��ʽ
     * @param destPath
     *                 Ŀ���ļ���ַ
     * @throws Exception 
     * 
     */
    public static void imageGray(String imgPath,String format, String destPath)throws Exception{
        try {
             BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
             ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);            
             ColorConvertOp op = new ColorConvertOp(cs, null);  
             bufferedImage = op.filter(bufferedImage, null);
             ImageIO.write(bufferedImage, format , new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ�Ұ׻��쳣");
        }
    }
    
    
    
    /**
     * ͼƬ͸��������(�ļ��������,ʹ��Ĭ�ϸ�ʽ)
     * 
     * @param imgPath
     *                 ͼƬ·��
     * @param destPath
     *                 ͼƬ���·��
     * @throws Exception
     */
    public static void imageLucency(String imgPath,String destPath)throws Exception{
        try {
            BufferedImage bufferedImage = imageLucency(imgPath);
            ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ͸�����쳣");
        }
    }
    
    
    /**
     * ͼƬ͸��������(�ļ��������,���Զ����ʽ)
     * 
     * @param imgPath
     *                 ͼƬ·��
     * @param format
     *                 ͼƬ��ʽ
     * @param destPath
     *                 ͼƬ���·��
     * @throws Exception
     */
    public static void imageLucency(String imgPath,String format,String destPath)throws Exception{
        try {
            BufferedImage bufferedImage = imageLucency(imgPath);
            ImageIO.write(bufferedImage, format, new File(destPath));
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ͸�����쳣");
        }
    }
    
    /**
     * ͼƬ͸������������BufferedImage����
     * 
     * @param imgPath
     *                 ͼƬ·��
     * @return
     *                 ͸�������ͼƬ����
     * @throws Exception 
     */
    public static BufferedImage imageLucency(String imgPath)throws Exception{
        BufferedImage targetImage = null;
        try {
                        //��ȡͼƬ   
            BufferedImage img = ImageIO.read(new FileInputStream(imgPath));
                        //͸����
            int alpha = 0;    
                        //ִ��͸����
            executeRGB(img, alpha);
            targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = targetImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
        } catch (Exception e) {
            throw new RuntimeException("ͼƬ͸����ִ���쳣");
        }
        return targetImage;
    }
    
    /**
     * ִ��͸�����ĺ����㷨
     * 
     * @param img
     *                 ͼƬ����
     * @param alpha
     *                 ͸����
     * @throws Exception 
     */
    public static  void executeRGB(BufferedImage img, int alpha) throws Exception{
        int rgb = 0;//RGBֵ
                    //x��ʾBufferedImage��x���꣬y��ʾBufferedImage��y����
        for(int x=img.getMinX();x<img.getWidth();x++){
            for(int y=img.getMinY();y<img.getHeight();y++){
                     //��ȡ��λ��RGBֵ���бȽ������趨
                rgb = img.getRGB(x, y); 
                int R =(rgb & 0xff0000 ) >> 16 ; 
                int G= (rgb & 0xff00 ) >> 8 ; 
                int B= (rgb & 0xff ); 
                if(((255-R)<30) && ((255-G)<30) && ((255-B)<30)){ 
                    rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff); 
                    img.setRGB(x, y, rgb);
                }
            }
        }
    }
    
    
    /**
     * ͼƬ��ʽת������(�ļ��������)
     * 
     * @param imgPath    
     *                     ԭʼͼƬ��ŵ�ַ
     * @param format
     *                     ��ת���ĸ�ʽ jpeg,gif,png,bmp��
     * @param destPath
     *                     Ŀ���ļ���ַ
     * @throws Exception
     */
    public static void formatConvert(String imgPath, String format, String destPath)throws Exception{
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
            ImageIO.write(bufferedImage, format, new File(destPath));
        } catch (IOException e) {
            throw new RuntimeException("�ļ���ʽת������");
        }
    }
    
    
    
    /**
     * ͼƬ��ʽת����������BufferedImage����
     * 
     * @param bufferedImage    
     *                     BufferedImageͼƬת������
     * @param format
     *                     ��ת���ĸ�ʽ jpeg,gif,png,bmp��
     * @param destPath
     *                     Ŀ���ļ���ַ
     * @throws Exception
     */
    public static void formatConvert(BufferedImage bufferedImag, String format, String destPath)throws Exception{
        try {
            ImageIO.write(bufferedImag, format, new File(destPath));
        } catch (IOException e) {
            throw new RuntimeException("�ļ���ʽת������");
        }
    }
    
    
    /**
     * ��ȡͼƬ�ļ�����ʵ��ʽ��Ϣ
     * 
     * @param imgPath
     *                     ͼƬԭ�ļ���ŵ�ַ
     * @return
     *                     ͼƬ��ʽ
     * @throws Exception
     */
    public static String imageFormat(String imgPath)throws Exception{
        String[] filess = imgPath.split("\\\\");
        String[] formats = filess[filess.length - 1].split("\\.");
        return formats[formats.length - 1];
     }

}