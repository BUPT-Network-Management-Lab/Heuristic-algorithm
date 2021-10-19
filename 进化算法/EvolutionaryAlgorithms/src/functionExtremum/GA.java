package functionExtremum;

public class GA {

	private int ChrNum = 50; // Ⱦɫ������
	public static final int GENE = 46; // ��������Ⱦɫ�峤�ȣ�
	private String[] ipop = new String[ChrNum]; // һ����Ⱥ��Ⱦɫ������
	private int generation = 0; // Ⱦɫ�����
	private double crossover = 0.90;  // ����������Ž�
	private double mutation = 0.01;  // ����������Ž�
	private double bestfitness = Double.MAX_VALUE; // �������Ž�
	private int bestgenerations; // �����Ӵ��븸������õ�Ⱦɫ��
	private String beststr; // ���Ž��Ⱦɫ��Ķ�������

	// ��ʼ��һ��Ⱦɫ�壨�ö������ַ�����ʾ��
	private String initChr() {
		String res = "";
		for (int i = 0; i < GENE; i++) {
			if (Math.random() > 0.5) {
				res += "0";
			} else {
				res += "1";
			}
		}
		return res;
	}

	// ��ʼ��һ����Ⱥ(10��Ⱦɫ��)
	private String[] initPop() {
		String[] ipop = new String[ChrNum];
		for (int i = 0; i < ChrNum; i++) {
			ipop[i] = initChr();
		}
		return ipop;
	}

	// ��Ⱦɫ��ת����x,y������ֵ
	private double[] calculatefitnessvalue(String str) {
		// ��������ǰ23λΪx�Ķ������ַ�������23λΪy�Ķ������ַ���
		//���Ը����Լ��ĺ�������
		int a = Integer.parseInt(str.substring(0, 23), 2);
		int b = Integer.parseInt(str.substring(23, 46), 2);

		double x = a * (6.0 - 0) / (Math.pow(2, 23) - 1); // x�Ļ���
		double y = b * (6.0 - 0) / (Math.pow(2, 23) - 1); // y�Ļ���
		// ���Ż��ĺ�������Ӧ�ȣ�
		double fitness = 3 - Math.sin(2 * x) * Math.sin(2 * x) - Math.sin(2 * y) * Math.sin(2 * y);
		double[] returns = { x, y, fitness };
		return returns;

	}

	// ����ѡ�� ����Ⱥ����ÿ���������Ӧ��ֵ; ���ɸ�����Ӧ��ֵ��������ĳ������ѡ�񽫽�����һ���ĸ���;
	private void select() {
		double evals[] = new double[ChrNum]; // ����Ⱦɫ����Ӧֵ
		double p[] = new double[ChrNum]; // ��Ⱦɫ��ѡ�����
		double q[] = new double[ChrNum]; // �ۼƸ���
		double F = 0; // �ۼ���Ӧֵ�ܺ�
		for (int i = 0; i < ChrNum; i++) {
			evals[i] = calculatefitnessvalue(ipop[i])[2];
			if (evals[i] < bestfitness) { // ��¼����Ⱥ�е���Сֵ�������Ž�
				bestfitness = evals[i];
				bestgenerations = generation;
				beststr = ipop[i];
			}

			F = F + evals[i]; // ����Ⱦɫ����Ӧֵ�ܺ�
		}

		for (int i = 0; i < ChrNum; i++) {
			p[i] = (F - (ChrNum - 1) * evals[i]) / F;
			if (i == 0)
				q[i] = p[i];
			else {
				q[i] = q[i - 1] + p[i];
			}
		}
		String[] ipopnew = new String[ChrNum];
		for (int i = 0; i < ChrNum; i++) {
			double r = Math.random();
			for (int j = 0; j < ChrNum; j++) {
				if (r < q[j]) {
					ipopnew[i] = ipop[j];
				}
			}
		}
		ipop = ipopnew;
	}

	// �������
	private void cross() {
		String temp1, temp2;
		for (int i = 0; i < ChrNum; i++) {
			if (Math.random() < crossover) {
				int pos = (int) (Math.random() * GENE) + 1; // posλ��ǰ������ƴ�����
				temp1 = ipop[i].substring(0, pos) + ipop[(i + 1) % ChrNum].substring(pos);
				temp2 = ipop[(i + 1) % ChrNum].substring(0, pos) + ipop[i].substring(pos);
				ipop[i] = temp1;
				ipop[(i + 1) / ChrNum] = temp2;
			}
		}
	}

	// ����ͻ�����
	private void mutation() {
		for (int i = 0; i < (int)(ChrNum*mutation); i++) {
			int chromosomeNum = (int) (Math.random() * ChrNum);// Ⱦɫ��ţ��������У���Ӧ��������һλ�����Բ���+1
			int mutationNum = (int) (Math.random() * GENE); // �����
			String temp;
			String a; // ��¼����λ������ı���
			if (ipop[chromosomeNum].charAt(mutationNum) == '0') { // ������λ��Ϊ0ʱ
				a = "1";
			} else {
				a = "0";
			}
			// ������λ�����ס��жκ�βʱ��ͻ�����
			if (mutationNum == 0) {
				temp = a + ipop[chromosomeNum].substring(mutationNum);
			} else {
				if (mutationNum != GENE - 1) {
					temp = ipop[chromosomeNum].substring(0, mutationNum) + a
							+ ipop[chromosomeNum].substring(mutationNum);
				} else {
					temp = ipop[chromosomeNum].substring(0, mutationNum) + a;
				}
			}
			// ��¼�±�����Ⱦɫ��
			ipop[chromosomeNum] = temp;
		}
	}

	public void solve() {
		ipop = initPop(); // ������ʼ��Ⱥ
		String str = "";
		// ��������
		for (int i = 0; i < 100000; i++) {
			select();
			cross();
			mutation();
			generation = i;
		}
		double[] x = calculatefitnessvalue(beststr);
		str = "��Сֵ" + bestfitness + '\n' + "��" + bestgenerations + "��Ⱦɫ��:<" + beststr + ">" + '\n' + "x=" + x[0] + '\n'
				+ "y=" + x[1];
		System.out.println(str);

	}
}
