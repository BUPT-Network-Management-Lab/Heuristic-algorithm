package Graph;

public class Data {
	public static int FLOW_TABLE_NUM=1000;
	public static int CORE_NUM=16;
	public static int CAPACITY=10000;
	public static int MEMORY=16000;
	public static int BAND_WIDTH=1000;
	public static int LATENCY=5;
	public static String[] TOPO_TYPE = { "FatTree", "BCube", "DCell", "VL2", "CommonTree" };
	public static int P_NODE=55;
	public static int V_NODE=14;
	public static int P_LINK=103;
	public static int V_LINK=21;
	public static int MAX_COST=Integer.MAX_VALUE;
	public static int MAX_V=15;
	public static Graph physic= new Graph("P.txt", true);// 55-103
	public static Graph virtual = new Graph("V.txt", false);// 14-21
}
