package CI;

public class PSO{
	
	// 迭代次数
	private final int step = 50;
	// 粒子数应在20-40
	private final int particleNum = 20; 
	// 惯性权重
	private final double w = 0.8;
	// 个体最佳位置的权重0-4
	private final double c1 = 2;
	// 全局最佳位置的权重0-4
	private final double c2 = 2;

	// 全局最优解
	private Particle g_best; 
	// 粒子本身历史最优解
	private Particle[] p_best;
	// 粒子位置
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
		for (int j = 0; j < step; j++) // 迭代
		{
			for (int i = 0; i < particleNum; i++) // 更新
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