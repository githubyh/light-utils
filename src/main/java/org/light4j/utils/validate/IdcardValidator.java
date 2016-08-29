package org.light4j.utils.validate;

import java.util.regex.Pattern;

/**
 * --15λ���֤���룺��7��8λΪ�������(��λ��)����9��10λΪ�����·ݣ���11��12λ����������ڣ���15λ�����Ա�����Ϊ�У�ż��ΪŮ��
 * --18λ���֤����
 * ����7��8��9��10λΪ�������(��λ��)����11����12λΪ�����·ݣ���13��14λ����������ڣ���17λ�����Ա�����Ϊ�У�ż��ΪŮ��
 * 
 * @author longjiazuo
 */
public class IdcardValidator {

	/**
	 * ʡ��ֱϽ�д���� { 11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",
	 * 21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",
	 * 33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",
	 * 42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",
	 * 51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",
	 * 63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}
	 */

	// ÿλ��Ȩ����
	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
			8, 4, 2 };

	/**
	 * ��֤���֤�Ƿ�Ϸ�
	 *
	 * @param idcard
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean isValidatedAllIdcard(String idcard) {
		return this.isValidate18Idcard(idcard);
	}

	/**
	 * 
	 <p>
	 * �ж�18λ���֤�ĺϷ���
	 * </p>
	 * ���ݡ��л����񹲺͹����ұ�׼GB11643-1999�����йع�����ݺ���Ĺ涨��������ݺ�������������룬��ʮ��λ���ֱ������һλ����У������ɡ�
	 * ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬��λ����˳�����һλ����У���롣
	 * <p>
	 * ˳����: ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ �ճ������˱ඨ��˳��ţ�˳�����������������ԣ�ż������ ��Ů�ԡ�
	 * </p>
	 * <p>
	 * 1.ǰ1��2λ���ֱ�ʾ������ʡ�ݵĴ��룻 2.��3��4λ���ֱ�ʾ�����ڳ��еĴ��룻 3.��5��6λ���ֱ�ʾ���������صĴ��룻
	 * 4.��7~14λ���ֱ�ʾ�������ꡢ�¡��գ� 5.��15��16λ���ֱ�ʾ�����ڵص��ɳ����Ĵ��룻
	 * 6.��17λ���ֱ�ʾ�Ա�������ʾ���ԣ�ż����ʾŮ�ԣ�
	 * 7.��18λ������У���룺Ҳ�е�˵�Ǹ�����Ϣ�룬һ��������������������������������֤����ȷ�ԡ�У���������0~9�����֣���ʱҲ��x��ʾ��
	 * </p>
	 * <p>
	 * ��ʮ��λ����(У����)�ļ��㷽��Ϊ�� 1.��ǰ������֤����17λ���ֱ���Բ�ͬ��ϵ�����ӵ�һλ����ʮ��λ��ϵ���ֱ�Ϊ��7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2
	 * </p>
	 * <p>
	 * 2.����17λ���ֺ�ϵ����˵Ľ����ӡ�
	 * </p>
	 * <p>
	 * 3.�üӳ����ͳ���11���������Ƕ��٣�
	 * </p>
	 * 4.����ֻ������0 1 2 3 4 5 6 7 8 9 10��11�����֡���ֱ��Ӧ�����һλ���֤�ĺ���Ϊ1 0 X 9 8 7 6 5 4 3
	 * 2��
	 * <p>
	 * 5.ͨ�������֪���������2���ͻ������֤�ĵ�18λ�����ϳ����������ֵĢ������������10�����֤�����һλ�������2��
	 * </p>
	 * 
	 * @param idcard
	 *            ����֤�����֤
	 * @return
	 */
	public static boolean isValidate18Idcard(String idcard) {
		// ��18λΪ��
		if (idcard.length() != 18) {
			return false;
		}
		// ��ȡǰ17λ
		String idcard17 = idcard.substring(0, 17);
		// ��ȡ��18λ
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		// �Ƿ�Ϊ����
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {
			int bit[] = new int[idcard17.length()];
			bit = converCharToInt(c);
			int sum17 = 0;
			sum17 = getPowerSum(bit);
			// ����ֵ��11ȡģ�õ���������У�����ж�
			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			// �����֤�ĵ�18λ���������У�����ƥ�䣬����Ⱦ�Ϊ��
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 18λ���֤����Ļ������ֺ�λ����У
	 * 
	 * @author : chenssy
	 * @date : 2016��6��1�� ����12:31:49
	 *
	 * @param idcard
	 *            ����֤�����֤
	 * @return
	 */
	public static boolean is18Idcard(String idcard) {
		return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",idcard);
	}

	/**
	 * ������֤
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * �����֤��ÿλ�Ͷ�Ӧλ�ļ�Ȩ�������֮���ٵõ���ֵ
	 * 
	 * @param bit
	 * @return
	 */
	private static int getPowerSum(int[] bit) {
		int sum = 0;
		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}

		return sum;
	}

	/**
	 * ����ֵ��11ȡģ�õ���������У�����ж�
	 * 
	 * @param sum17
	 * @return
	 */
	private static String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	/**
	 * ���ַ�����תΪ��������
	 * 
	 * @param c
	 * @return
	 * @throws NumberFormatException
	 */
	private static int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	/**
	 * 
	 * @param idno
	 * @return ���֤��Ϣ�д����Ա����ֵ
	 */
	public static int getUserSex(String idno) {
		String sex = "1";
		if (idno != null) {
			if (idno.length() > 15) {
				sex = idno.substring(16, 17);
			}
		}

		return Integer.parseInt(sex) % 2 == 0 ? 0 : 1;
	}
}
