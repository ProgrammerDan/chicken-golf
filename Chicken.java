public class Chicken {
	public static void main(String[] args) {
		char[][] road = new char[args.length][]; int i = 0;
		for (String k : args) {
			road[i++] = k.toCharArray();
		}

		int[] minpath = null, best, rbest, extent;
		int mincost = 73; int rs, re,j;

		// algorithm. Solve left, find possible right positions, solve, capture best.
		for(i=0; i < road.length; i++) {
			best = solveSingle(i, i, 0, road); // returns the best end idx and cost from the given starting point(s).
			System.out.println("bst " + best[0] + " " + best[1] + " " + best[2] + " " + best[3] + " " + best[4]);
			if (best[0] > mincost) continue; // shortcut, remove for golf.
			extent = solveBridge(best[4], road);
			rs = extent[0];
			re = extent[0];
			for (j=extent[0];j<=extent[1];j++) {
				// check reachable.
				boolean go = canGo(best[4], j, road);
				if (go) {
					rs = (rs<0)?j:rs;
					re = j;
				}
				if (((!go)&&rs>=0)||j==extent[1]) {
					rbest = solveSingle(rs, re, 7, road);
					if (rbest[0] < mincost) {
						mincost = best[0]+rbest[0];
						minpath = new int[]{best[1],best[2],best[3],best[4],rbest[1],rbest[2],rbest[3],rbest[4]};
					}
					rs = -1;
					re = -1;
				}
			}
		}
		if (minpath != null) {
			int k = 0;
			for (int n : minpath) {
				System.out.print(road[n][k] + " ");
				if (k<3||k>6) k++;
				else k = 7;
			}
			System.out.println(mincost);
		} else {
			System.out.println("no path");
		}
	}

	public static int[] solveSingle(int start, int end, int roadOff, char[][] road) {
		System.out.println("ss " + start + " " + end + " " + roadOff);
		int r = road.length-1;
		int sc = 0, ac = 0, bc = 0, cc = 0;
		int mc = 73;
		int[] ms = new int[4];
		for (int s=start;s<=end;s++) {
			sc = road[s][0+roadOff]-49;
			for (int a=(s<1?0:s-1); a<=(s==r?r:s+1); a++) {
				ac = road[a][1+roadOff]-49;
				for (int b=(a<1?0:a-1); b<=(a==r?r:a+1);b++) {
					bc = road[b][2+roadOff]-49;
					for (int c=(b<1?0:b-1); c<=(b==r?r:b+1);c++) {
						cc = road[c][3+roadOff]-49;
						System.out.println("ssc " + (sc + ac + bc + cc) + " " + s + " " + a + " " + b + " " + c);
						if (sc+ac+bc+cc<mc) {
							mc = sc+ac+bc+cc;
							ms = new int[]{s,a,b,c};
						}
					}
				}
			}
		}
		return new int[]{mc, ms[0], ms[1], ms[2], ms[3]};
	}

	/**
	 * Figures out how to cross the "lines" as far ABOVE start and as far BELOW
	 *   start as possible. Deals with both "bad tail" conditions where the lines
	 *   impact the begin/end of the road and the chicken gets "stuck".
	 * This is used to figure out how much of the right-hand side of the road we
	 * should both solving.
	 */
	public static int[] solveBridge(int start, char[][] road) {
		int r = road.length-1;
		int s = start;
		// up direction
		int a=s<1?0:s-1;
		int b=a;
		while (b>-1&&road[b][5]=='|') {b--;}
		if (b<0) while(road[++b][5]!=' ');
		b=b<2?0:b-2; // final spot is x - 1. // space col
		// down direction
		int c=s==r?r:s+1;
		int d=c;
		while (d<=r&&road[d][5]=='|') {d++;}
		if (d>r) while(road[--d][5]!=' ');
		d=d>r-2?r:d+2; // space col
		return new int[]{b, d};
	}

	public static boolean canGo(int start, int end, char[][] road) {
		int r = road.length - 1;
		int s = start;
		int e = end;
		// reachable extent from s.
		for (int i=s<2?0:s-2;i<(s>r-2?r:s+2);i++) {
			if (road[i][5]==' ') {
				if (e >= i-2 && e <= i+2) return true;
			} else {
				// check up, then down
				int j = i-1;
				while(j>-1&&road[j][5]=='|') {j--;}
				if (j>-1 && e>=j-2 && e<=j+2) return true;
				j = i+1;
				while(j<=r&&road[j][5]=='|') {j++;}
				if (j<=r && e>=j-2 && e<=j+2) return true;
			}
		}
		return false;
	}
}
