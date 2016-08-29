package org.light4j.utils.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ��Ǯ��������
 * 
 * @author longjiazuo
 */
public class MoneyUtils {
	
	/**
	 * ���������ִ�д
	 */
	 private static final String[] CN_UPPER_NUMBER = {"��","Ҽ","��","��","��","��","½","��","��","��" };
	 
	 /**
	  * �����л��ҵ�λ��д
	  */
	 private static final String[] CN_UPPER_MONETRAY_UNIT = { "��", "��", "Ԫ","ʰ", "��", "Ǫ", "��", "ʰ", 
		 													  "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ",
		 													  "��", "Ǫ" };
	 /**
	  * �����ַ�����
	  */
	 private static final String CN_FULL = "";
	 
	 /**
	  * �����ַ�����
	  */
	 private static final String CN_NEGATIVE = "��";
	 /**
	  * ��Ԫ��
	  */
	 private static final String CN_ZEOR_FULL = "��Ԫ��";
	 
	 /**
	  * ���ľ��ȣ�Ĭ��ֵΪ2
	  */
	 private static final int MONEY_PRECISION = 2;
	 
	 /**
	  * �����ת��Ϊ��д,��ʽΪ��x��xǧx��xʮxԪx��x��
	  * 
	  * @param numberOfMoney ����Ľ��
	  * @return
	  */
	 public static String number2CNMontray(String numberOfMoney) {
		 return number2CNMontray(new BigDecimal(numberOfMoney));
	 }
	 

	/**
	 * �����ת��Ϊ��д,��ʽΪ��x��xǧx��xʮxԪx��x��
	 * 
	 * @param numberOfMoney
	 * 					����Ľ��
	 * @return
	 */
	public static String number2CNMontray(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
        int signum = numberOfMoney.signum();
        // ��Ԫ�������
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //�������н�����������
        long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        // �õ�С�������λֵ
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // �ж������λ����һ�������������00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // ÿ�λ�ȡ�����һ����
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // ��numberÿ�ζ�ȥ�����һ����
            number = number / 10;
            ++numIndex;
        }
        // ���signum == -1����˵�����������Ϊ������������ǰ��׷�������ַ�����
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // ���������С�������λΪ"00"���������Ҫ�����׷�������ַ�����
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
	}
	
	/**
	 * �������ת��Ϊ��Ƹ�ʽ���(xxxx,xxxx,xxxx.xx),������λС��
	 * 
	 * @param money
	 * 				��ת���Ľ��
	 * @return
	 */
	public static String accountantMoney(BigDecimal money){
		return accountantMoney(money, 2, 1);
	}
	
	/**
	 * ��ʽ������ʾΪxxx��Ԫ��xxx����,xxx��
	 *
	 * @param money 
	 * 				������Ľ��
	 * @param scale  
	 * 				С���������λ��
	 * @param divisor 
	 * 				��ʽ��ֵ��10:ʮԪ��100:��Ԫ,1000ǧԪ��10000��Ԫ......��
	 * @return
	 */
	public static String getFormatMoney(BigDecimal money,int scale,double divisor){
		return formatMoney(money, scale, divisor) + getCellFormat(divisor);
	}
	
	/**
	 * ��ȡ��Ƹ�ʽ�������(��ʽΪ:xxxx,xxxx,xxxx.xx)
	 *
	 * @param money 
	 * 				������Ľ��
	 * @param scale 
	 * 				С���������λ��
	 * @param divisor 
	 * 				��ʽ��ֵ��10:ʮԪ��100:��Ԫ,1000ǧԪ��10000��Ԫ......��
	 * @return
	 */
	public static String getAccountantMoney(BigDecimal money, int scale, double divisor){  
        return accountantMoney(money, scale, divisor) + getCellFormat(divisor);
    }  
	
	/**
	 * �������ת��Ϊ��Ƹ�ʽ���(xxxx,xxxx,xxxx.xx)
	 *
	 * @param money 
	 * 				������Ľ��
	 * @param scale 
	 * 				С���������λ��
	 * @param divisor 
	 * 				��ʽ��ֵ
	 * @return
	 */
	private static String accountantMoney(BigDecimal money,int scale,double divisor){
		String disposeMoneyStr = formatMoney(money, scale, divisor);  
        //С���㴦��  
        int dotPosition = disposeMoneyStr.indexOf(".");  
        String exceptDotMoeny = null;//С����֮ǰ���ַ���  
        String dotMeony = null;//С����֮����ַ���  
        if(dotPosition > 0){  
            exceptDotMoeny = disposeMoneyStr.substring(0,dotPosition);  
            dotMeony = disposeMoneyStr.substring(dotPosition);  
        }else{  
            exceptDotMoeny = disposeMoneyStr;  
        }  
        //��������  
        int negativePosition = exceptDotMoeny.indexOf("-");  
        if(negativePosition == 0){  
            exceptDotMoeny = exceptDotMoeny.substring(1);  
        }  
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);  
        reverseExceptDotMoney.reverse();//�ַ�����ת  
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();  
        StringBuffer returnMeony = new StringBuffer();//����ֵ  
        for(int i = 0; i < moneyChar.length; i++){  
            if(i != 0 && i % 3 == 0){  
                returnMeony.append(",");//ÿ��3λ��','  
            }  
            returnMeony.append(moneyChar[i]);  
        }  
        returnMeony.reverse();//�ַ�����ת  
        if(dotPosition > 0){  
            returnMeony.append(dotMeony);  
        }  
        if(negativePosition == 0){  
            return "-" + returnMeony.toString();  
        }else{  
            return returnMeony.toString();  
        }  
	}
	
	/**
	 * ��ʽ������ʾΪxxx��Ԫ��xxx����,xxx��
	 *
	 * @param money 
	 * 				������Ľ��
	 * @param scale  
	 * 				С���������λ��
	 * @param divisor 
	 * 				��ʽ��ֵ
	 * @return
	 */
	private static String formatMoney(BigDecimal money,int scale,double divisor){
		if (divisor == 0) {
			return "0.00";
		}
		if (scale < 0) {
			return "0.00";
		}
		BigDecimal divisorBD = new BigDecimal(divisor);
		return money.divide(divisorBD, scale, RoundingMode.HALF_UP).toString();
	}
	
	private static String getCellFormat(double divisor){
		String str = String.valueOf(divisor);
		int len = str.substring(0,str.indexOf(".")).length();
		String cell = "";
		switch(len){
			case 1:
				cell = "Ԫ";
				break;
			case 2:
				cell = "ʮԪ";
				break;
			case 3:
				cell = "��Ԫ";
				break;
			case 4:
				cell = "ǧԪ";
				break;
			case 5:
				cell = "��Ԫ";
				break;
			case 6:
				cell = "ʮ��Ԫ";
				break;
			case 7:
				cell = "����Ԫ";
				break;
			case 8:
				cell = "ǧ��Ԫ";
				break;
			case 9:
				cell = "��Ԫ";
				break;
			case 10:
				cell = "ʮ��Ԫ";
				break;
		}
		return cell;
	}
}
