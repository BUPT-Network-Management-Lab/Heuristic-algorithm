package TSP;

import java.util.Random;
import java.util.Vector;

/* ʵ�������Ⱦɫ�壬�����ʾΪ0,1,2....n-1
 *  ���������Լ��޸���Ӧ�Ⱥ���calculatefitness()
 *  ������Ӧ��Ϊһ��Ⱦɫ���и�������֮��ľ��루TSP�ĳ��м���룩�ĵ���
 *  ���������Ը�·����Ȩ����Լ�������ȵ�
*/

public class Chromosome implements Cloneable {

	private int geneNum; // �ܵĻ�����Ŀ��TSP�����еĳ�����Ŀ��
	private int[][] distance; // ����֮��Ķ�����TSP�����еĳ���֮��ľ��룩
	private int chromLen; // ÿ��Ⱦɫ��Я���Ļ�����Ŀ��TSP�����е�Ҫ���еĳ�����Ŀ��Ҳ��Ⱦɫ�峤�ȣ�һ��Ϊ�ܻ�������
	private int[] geneSequence; // Ⱦɫ���ڵĻ�������
	private double fitness; // ��Ӧ��

	public Chromosome() {
		this.geneNum = 30;
		this.distance = new int[geneNum][geneNum];
		this.chromLen = geneNum;
		this.geneSequence = new int[geneNum];
	}

	public Chromosome(int num, int[][] distance, int chromlen) {
		this.geneNum = num;
		this.distance = distance;
		this.chromLen = chromlen;
		this.geneSequence = new int[chromLen];
	}

	public void randomGeneration() {
		Vector<Integer> allowedGenes = new Vector<Integer>();
		for (int i = 0; i < geneNum; i++) {
			allowedGenes.add(Integer.valueOf(i));
		}
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < geneNum; i++) {
			int index = r.nextInt(allowedGenes.size());
			int selectedGene = allowedGenes.get(index).intValue();
			geneSequence[i] = selectedGene;
			allowedGenes.remove(index);
		}
	}

	public void print() {
		for (int i = 0; i < geneNum; i++) {
			System.out.print(geneSequence[i] + ",");
		}
		System.out.println();
		System.out.println("Its fitness measure is  " + getFitness());
	}

	// ������Ӧ�Ⱥ���
	private double calculatefitness() {
		double fitness = 0.0;
		int len = 0;
		for (int i = 0; i < geneNum - 1; i++) {
			len += distance[this.geneSequence[i]][this.geneSequence[i + 1]];
		}
		fitness = 1.0 / len; // ��Ӧ��Ϊ·�̵���
		return fitness;
	}

	public int[] getgeneSequence() {
		return geneSequence;
	}

	public void setgeneSequence(int[] geneSequence) {
		this.geneSequence = geneSequence;
	}

	public int[][] getDistance() {
		return distance;
	}

	public void setDistance(int[][] distance) {
		this.distance = distance;
	}

	public int getgeneNum() {
		return geneNum;
	}

	public void setgeneNum(int geneNum) {
		this.geneNum = geneNum;
	}

	public double getFitness() {
		this.fitness = calculatefitness();
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Chromosome chromosome = (Chromosome) super.clone();
		chromosome.geneNum = this.geneNum;
		chromosome.distance = this.distance.clone();
		chromosome.geneSequence = this.geneSequence.clone();
		chromosome.fitness = this.fitness;
		return chromosome;
	}

}
