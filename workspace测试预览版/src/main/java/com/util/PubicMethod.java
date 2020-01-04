package com.util;

import java.util.Random;

public class PubicMethod {
	public static String getAcademeCode() {
		Random random = new Random();
		String AcademeCode = "";
		StringBuffer sb = new StringBuffer();
		long time = System.currentTimeMillis();
		long randonvalue = 0;
		String str = "";
		for (int i = 0; i < 5; i++) {
			int casevalue = PubicMethod.getCase(random.nextInt(3) % 4);
			switch (casevalue) {
			case 1:
				/* A-Z 的 ASCII 码值[65,90] */
				str = (char) (Math.random() * 26 + 'A') + "";
				sb.append(str);
				break;
			case 2:
				/* a-z 的 ASCII 码值[97,122] */
				str = (char) (Math.random() * 26 + 'a') + "";
				sb.append(str);
				break;
			default:
				break;
			}
		}
		AcademeCode = String.valueOf(time) + sb;
		return AcademeCode;
	}

	public static int getCase(int casevalue) {
		Random random = new Random();
		return casevalue == 0 ? getCase(random.nextInt(3) % 4) : casevalue;
	}
}
