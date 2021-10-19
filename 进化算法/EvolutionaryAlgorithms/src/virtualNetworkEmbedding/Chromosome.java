package virtualNetworkEmbedding;

import java.util.Random;
import java.util.Vector;

import Graph.Graph;

/* ʹ�õ�TSP����Ĵ���ʵ��VNE���� */

public class Chromosome implements Cloneable {
	private Graph physicGraph;
	private Graph virtualGraph;
	private int geneNum; // ��������ڵ���
	private int chromLen; // ��������ڵ���
	private int[] geneSequence; // Ⱦɫ���ڵĻ������У�����ڵ����У�
	private double fitness; // ��Ӧ��

	public Chromosome(int geneNum, int chromLen) {
		this.physicGraph = new Graph("P.txt", true);
		this.virtualGraph = new Graph("V.txt", false);
		this.geneNum = geneNum;
		this.chromLen = chromLen;
		this.geneSequence = new int[this.chromLen];
		this.fitness = 0.0;
	}

	public int getChromLen() {
		return chromLen;
	}

	public void setChromLen(int chromLen) {
		this.chromLen = chromLen;
	}

	public int[] getGeneSequence() {
		return geneSequence;
	}

	public void setGeneSequence(int[] geneSequence) {
		this.geneSequence = geneSequence;
	}

	public double getFitness() {
		this.fitness = calculatefitness();
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	// ���������
	public void randomGeneration() {
		Vector<Integer> allowedGenes = new Vector<Integer>();
		for (int i = 0; i < geneNum; i++) {
			allowedGenes.add(Integer.valueOf(i));
		}
		for (int i = 0; i < chromLen; i++) {
			Random r = new Random(System.currentTimeMillis() + new Random().nextInt(1000));
			int index = r.nextInt(allowedGenes.size());
			geneSequence[i] = allowedGenes.get(index).intValue();
			allowedGenes.remove(index);
		}
	}

	// ������Ӧ�Ⱥ���
	private double calculatefitness() {
		double nodeWeight = 1.0; // �ڵ㿪��Ȩ�ز���
		double nodeCost = 0.0; // �ڵ㿪��
		double linkWeight = 1.0; // ��·����Ȩ�ز���
		double linkCost = 0.0; // ��·����
		double fitness = 0.0;
		
		for (int i = 0; i < chromLen; i++) {
			nodeCost += virtualGraph.nodes[i].getCapacity();
		}
		for(int i=0;i<virtualGraph.links.length;i++){
			int left = geneSequence[virtualGraph.links[i].getLeftID()];
			int right = geneSequence[virtualGraph.links[i].getRightID()];
			int [] path = physicGraph.dijkstra(left, right, virtualGraph.links[i].getBandWidth());
			if (path == null) {
				//System.out.println("ERROR!");
				physicGraph.refreshMatrix();
				fitness = Double.MIN_VALUE;
				return fitness;
			} else {
				linkCost += virtualGraph.links[i].getBandWidth() * (path.length - 1);
			}
		}
		fitness = 1000000.0 /(nodeWeight * nodeCost + linkWeight * linkCost);
		physicGraph.refreshMatrix();
		return fitness;
	}

	public void print() {
		System.out.print("��������ڵ�ӳ�������ڵ�����Ϊ��");
		for (int i = 0; i < chromLen; i++) {
			System.out.print(geneSequence[i] + ", ");
		}
		System.out.println("��Ӧ��Ϊ�� " + getFitness());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Chromosome chromosome = (Chromosome) super.clone();
		chromosome.chromLen = this.chromLen;
		chromosome.geneSequence = this.geneSequence.clone();
		chromosome.fitness = this.fitness;
		return chromosome;
	}
}
