import java.util.*;
class Chicken {
	public static void main(String[]a){
		if (a.length==0) {
			Scanner u = new Scanner(System.in);
			Vector<String> w = new Vector();
			while (u.hasNextLine())
				w.add(u.nextLine());
			a = w.toArray(a);
		}
		int n=a.length, m=0;//a[0].length();
		char[][] r = new char[n][];
		int i=0,j=0,h=0,p=0,q=0,s=0,t=0,b=0,c=0;
		for (String k : a) m = (k.length() > m)?k.length():m;
		for (String k : a) r[i++] = Arrays.copyOf(k.toCharArray(), m);

		// Solve right to left, using DP min-path marking technique. '|' is effectively ignored, it's simply a transit to spaces.
		int[][][] d = new int[n][m][2];
		b = -1;
		c = Integer.MAX_VALUE;

		// marks: 0-- unmarked. 1 --diagup, 2--forward, 3--diagdown, 4--up, 6--down, -1 --skip
		for(j=m-1;j>=0;j--){
			for(i=0;i<n;i++){
				if (j==m-1) // fill marks for "final" column.
					d[i][j][1] = (r[i][j]=='\0'||r[i][j]==' '||r[i][j]=='|'?0:r[i][j] - '0');
				else {
					if (r[i][j] == '|') {
						d[i][j][0] = -1; // these are special case, computed after whole row.
						continue;
					}
					p = (r[i][j]=='\0'||r[i][j]==' '||r[i][j]=='|'?0:r[i][j] - '0');
					// check each angle.
					for(h=-1;h<2;h++) {
						if (i+h < 0||i+h>=n) continue;
						if (d[i+h][j+1][0] == -1) { // found pipe, compute.
							// find which end to land on.
							// check up, then down.
							s = i+h-1;
							while (s>=0&&r[s][j+1]=='|') s--;
							t = i+h+1; //down
							while (t<n&&r[t][j+1]=='|') t++;
							if ((s>=0&&t<n&&d[s][j+1][1]<d[t][j+1][1])||(s>=0&&t>=n)) {
								t=d[s][j+1][1];
								s=4;
							} else {
								s=6;
								t=d[t][j+1][1];
							}
								
							d[i+h][j+1][0]=s;
							d[i+h][j+1][1]=t;
						}
						// now carry on.
						q = d[i+h][j+1][1]+p;
						if (d[i][j][0]==0 || q < d[i][j][1]) {
							d[i][j][0]=h+2;
							d[i][j][1]=q;
						}
					}
				}
				if (j==0&&(b<0||d[i][0][1]<c)) {
					b=i;
					c=d[i][0][1];
				}
			}
		}
		System.out.println("best start: " + (b+1) + " cost: " + c);
		i=b;
		j=0;
		while(j<m) {
			if (r[i][j]=='\0')j=m; else {
				System.out.print(r[i][j] + " -> ");
				h=d[i][j][0]-2;
				if(h>1) i+=h-3;
				else {i+=h;j++;}
			}
		}
		System.out.println(c);
		i=b;
		j=0;
		while(j<m) {
			if (r[i][j]=='\0')j=m; else {
				h=d[i][j][0];
				char u = (h==1?'/':h==2?'-':h==3?'\\':h==4?'^':h==6?',':'?');
				System.out.print(u+" -> ");
				h=d[i][j][0]-2;
				if(h>1) i+=h-3;
				else {i+=h;j++;}
			}
		}
		System.out.println("across");
	}
}
