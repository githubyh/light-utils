package org.light4j.utils.QRCode;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MatrixToImageWriterEx {


	private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();
	
	/**
	 * �����������ɶ�ά������
	 * @param content ��ά����������[Ϊ����Ϣ��ȫ�ԣ�һ�㶼Ҫ�Ƚ������ݼ���]
	 * @param width ��ά����Ƭ���
	 * @param height ��ά����Ƭ�߶�
	 * @return
	 */
	public static BitMatrix createQRCode(String content, int width, int height){
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
		//�����ַ�����
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        // ָ������ȼ�
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = null;  
        try {  
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints); 
        } catch (WriterException e) {  
            e.printStackTrace();  
        }
        
        return matrix;
	}

	/**
	 * д���ά�롢�Լ�����Ƭlogoд���ά����
	 * @param matrix Ҫд��Ķ�ά��
	 * @param format ��ά����Ƭ��ʽ
	 * @param imagePath ��ά����Ƭ����·��
	 * @param logoPath logo·��
	 * @throws java.io.IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());
		
		//���logoͼƬ, �˴�һ����Ҫ���½��ж�ȡ��������ֱ��ʹ�ö�ά���BufferedImage ����
		BufferedImage img = ImageIO.read(new File(imagePath));
		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, DEFAULT_CONFIG);
	}
	
	/**
	 * д���ά�롢�Լ�����Ƭlogoд���ά����
	 * @param matrix Ҫд��Ķ�ά��
	 * @param format ��ά����Ƭ��ʽ
	 * @param imagePath ��ά����Ƭ����·��
	 * @param logoPath logo·��
						 * @param logoConfig logo���ö���
						 * @throws java.io.IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
		MatrixToImageWriter.writeToFile(matrix, format, new File(imagePath), new MatrixToImageConfig());
		
		//���logoͼƬ, �˴�һ����Ҫ���½��ж�ȡ��������ֱ��ʹ�ö�ά���BufferedImage ����
		BufferedImage img = ImageIO.read(new File(imagePath));
		MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, logoConfig);
	}

	/**
	 * ����Ƭlogo��ӵ���ά���м�
	 * @param image ���ɵĶ�ά����Ƭ����
	 * @param imagePath ��Ƭ����·��
	 * @param logoPath logo��Ƭ·��
	 * @param formate ��Ƭ��ʽ
	 */
	public static void overlapImage(BufferedImage image, String formate, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) {
		try {
			//��logoд���ά����
			drawImage(logoPath, image, logoConfig);
			
			//д��logo��Ƭ����ά��
			ImageIO.write(image, formate, new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����Ƭ��ӵ���ά���м䣬��������
	 *
	 * @param matrix Ҫд��Ķ�ά��
	 * @param formate ��Ƭ��ʽ
	 * @param logoPath	Ҫд����Ƭ��·��
	 * @param logoConfig logo���ö���  ����Ϊnull��Ϊ null Ĭ�� DEFAULT_CONFIG
	 * @throws java.io.IOException
	 */
	public static void overlapImage(BitMatrix matrix,String formate,String logoPath,MatrixToLogoImageConfig logoConfig,OutputStream out) throws IOException{
		//��matrixת��ΪbufferImage
		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
		
		//��logo��Ƭ���Ƶ���ά���м�
		drawImage(logoPath, image, logoConfig);
		
		//���
		ImageIO.write(image, formate, out);
	}
	
	/**
	 * ����Ƭ��ӵ���ά���м䣬��������
	 *
	 * @param image Ҫд��Ķ�ά��
	 * @param formate ��Ƭ��ʽ
	 * @param logoPath	Ҫд����Ƭ��·��
	 * @param logoConfig logo���ö���  ����Ϊnull��Ϊ null Ĭ�� DEFAULT_CONFIG
	 * @throws java.io.IOException
	 */
	public static void overlapImage(BufferedImage image,String formate,String logoPath,MatrixToLogoImageConfig logoConfig,OutputStream out) throws IOException{
		//��logo��Ƭ���Ƶ���ά���м�
		drawImage(logoPath, image, logoConfig);
		
		//���
		ImageIO.write(image, formate, out);
	}
	
	/**
	 * ��logo��ӵ���ά���м�
	 *
	 * @param logoPath	logo·��
	 * @param image	��Ҫ���ƵĶ�ά��ͼƬ
	 * @param logoConfig	���ò���
	 * @throws java.io.IOException
	 */
	private static void drawImage(String logoPath,BufferedImage image,MatrixToLogoImageConfig logoConfig) throws IOException{
		if(logoConfig == null){
			logoConfig = DEFAULT_CONFIG;
		}
		
		try {
			BufferedImage logo = ImageIO.read(new File(logoPath));
			logo.setRGB(0, 0, BufferedImage.TYPE_INT_BGR);
			Graphics2D g = image.createGraphics();
			
			//���ǵ�logo��Ƭ������ά���У������С��Ҫ������ά���1/5;
			int width = image.getWidth() / logoConfig.getLogoPart();
			int height = image.getHeight() / logoConfig.getLogoPart();
			
			//logo��ʼλ�ã���Ŀ����Ϊlogo������ʾ
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			
			//����ͼ
			g.drawImage(logo, x, y, width, height, null);
			
			//��logo���߿�
			//����һ������ָ����������Լ� cap �� join ����Ĭ��ֵ��ʵ�� BasicStroke
//		g.setStroke(new BasicStroke(logoConfig.getBorder()));
//		g.setColor(logoConfig.getBorderColor());
//		g.drawRect(x, y, width, height);
			
			g.dispose();
		} catch (Exception e) {   //��׽�쳣�����κδ�����ֹͼƬ·����������¶�ά������ʧ��
			
		}
	}
	
}
