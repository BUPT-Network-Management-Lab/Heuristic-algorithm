package CI;

import java.util.ArrayList;
import java.util.HashSet;

import Graph.Data;

public class Particle {
	int[] velocity;
	int[] position;
	int evaluate;

	public Particle() {
		position = randomPosition();
		velocity = randomVelocity();
		evaluate = evaluate();
	}
	
	private int[] randomPosition() {
		// TODO Auto-generated method stub
		int[] ret = new int[Data.V_NODE];
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < Data.P_NODE; i++) {
			list.add(i);
		}
		for (int i = 0; i < ret.length; i++) {
			int index = (int) (Math.random() * list.size());
			while (Data.physic.nodes[index].getCapacity() < 
					Data.virtual.nodes[i].getCapacity()) {
				index = (int) (Math.random() * list.size());
			}
			ret[i] = list.get(index);
			list.remove(index);
		}
		return ret;
	}

	private int[] randomVelocity() {
		// TODO Auto-generated method stub
		int[] ret = new int[Data.V_NODE];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int) (Math.random() 
					* Data.MAX_V);
		}
		return ret;
	}

	public Particle(Particle best) {
		position = new int[Data.V_NODE];
		for (int i = 0; i < position.length; i++) {
			position[i] = best.position[i];
		}
		velocity = new int[Data.V_NODE];
		for (int i = 0; i < velocity.length; i++) {
			velocity[i] = best.velocity[i];
		}
		evaluate = best.evaluate;
	}

	public void setParticle(Particle best) {
		for (int i = 0; i < position.length; i++) {
			position[i] = best.position[i];
		}
		for (int i = 0; i < velocity.length; i++) {
			velocity[i] = best.velocity[i];
		}
		evaluate = best.evaluate;
	}

	private int evaluate() {
		int ret = 0;
		for (int i = 0; i < Data.virtual.links.length; i++) {
			int[] path = Data.physic.dijkstra(
					position[Data.virtual.links[i].getLeftID()],
					position[Data.virtual.links[i].getRightID()], 
					Data.virtual.links[i].getBandWidth());

			// ��·��ԴԼ��
			if (path == null) {
				return Data.MAX_COST;
			}
			ret += (path.length - 1) * 
					Data.virtual.links[i].getBandWidth();
		}
		Data.physic.refreshMatrix();
		return ret;
	}

	public void update(double w, double c1, double c2, Particle p_best, Particle g_best) {
		// ���������ٶ�
		for (int i = 0; i < velocity.length; i++) {
			velocity[i] = (int) (w * velocity[i] + c1 * Math.random() * (p_best.position[i] - position[i])
					+ c2 * Math.random() * (g_best.position[i] - position[i]));
			if (Math.abs(velocity[i]) > Data.MAX_V) {
				velocity[i] /= (Math.abs(velocity[i]) / Data.MAX_V + 1);
			}
		}

		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < position.length; i++) {
			// ����λ�ø���
			position[i] = position[i] + velocity[i];

			// Խ�紦��
			if (position[i] >= Data.P_NODE || position[i] < 0) {
				position[i] = Math.abs(position[i]) % Data.P_NODE;
				while (set.contains(position[i])) {
					position[i] += 5 * Math.random();
					if (position[i] >= Data.P_NODE) {
						position[i] -= Data.P_NODE;
					}
				}
			}

			// ͬһ������ڵ�ֻ��ӳ��һ������ڵ�
			if (set.contains(position[i])) {
				evaluate = Data.MAX_COST;
				return;
			}
			set.add(position[i]);

			// �ڵ���ԴԼ��
			if (Data.physic.nodes[position[i]].getCapacity() < 
						Data.virtual.nodes[i].getCapacity()) {
				evaluate = Data.MAX_COST;
				return;
			}
		}
		evaluate = evaluate();
	}
}
