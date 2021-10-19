package CI;

public class PSO{
	
	// ��������
	private final int step = 50;
	// ������Ӧ��20-40
	private final int particleNum = 20; 
	// ����Ȩ��
	private final double w = 0.8;
	// �������λ�õ�Ȩ��0-4
	private final double c1 = 2;
	// ȫ�����λ�õ�Ȩ��0-4
	private final double c2 = 2;

	// ȫ�����Ž�
	private Particle g_best; 
	// ���ӱ�����ʷ���Ž�
	private Particle[] p_best;
	// ����λ��
	private Particle[] position;
	
	
	public static void main(String[] args) {
		PSO pso = new PSO();
		pso.initialize();
		pso.search();
	}
	
	public PSO() {
		// TODO Auto-generated constructor stub
		position=new Particle[particleNum];
		p_best=new Particle[particleNum];
	}
	
	public void initialize() {
		for (int i = 0; i < particleNum; i++) {			
			position[i]=new Particle();
			p_best[i]=new Particle(position[i]);
			if(g_best==null){
				g_best=new Particle(p_best[i]);
			}else if(g_best.evaluate>
					p_best[i].evaluate){
				g_best.setParticle(p_best[i]);
			}
		}
	}
	
	public void search() {
		// TODO Auto-generated method stub
		for (int j = 0; j < step; j++) // ����
		{
			for (int i = 0; i < particleNum; i++) // ����
			{
				position[i].update(w, c1, c2, p_best[i], g_best);
				//position[i].print();				
				if (p_best[i].evaluate > position[i].evaluate) {
					p_best[i].setParticle(position[i]);
					if (g_best.evaluate > p_best[i].evaluate) {
						g_best.setParticle(p_best[i]);
					}
				}
			}
		}
	}
}