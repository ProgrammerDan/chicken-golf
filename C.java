import java.util.*;
class C{
	public static void main(String[]a){
		if (a.length==0) {
			Scanner u = new Scanner(System.in);
			Vector<String> w = new Vector();
			while (u.hasNextLine())
				w.add(u.nextLine());
			a = w.toArray(a);
		}
		int n=a.length,m=0,i=0,j=0,h=0,p=0,q=0,s=0,t=0,b=-1,c=2147483647,x=0,y=0;
		char[][]r=new char[n][];char u;
		for(String k:a){j=k.length();m=(j>m)?j:m;}
		for(String k:a)r[i++]=Arrays.copyOf(k.toCharArray(),m);
		int[][][]d=new int[n][m][2];
		for(j=m-1;j>=0;j--){
			for(i=0;i<n;i++){
				u=r[i][j];
				p=(u=='\0'||u==' '||u=='|'?0:u-'0');
				if(j==m-1)d[i][j][1]=p;
				else{
					if(u=='|')d[i][j][0]=-1;
					else{
						for(h=-1;h<2;h++){
							x=i+h;
							y=j+1;
							if(x>=0&&x<n){
								if(d[x][y][0]==-1){
									s=x-1;
									while(s>=0&&r[s][y]=='|')s--;
									t=x+1; //down
									while(t<n&&r[t][y]=='|')t++;
									if((s>=0&&t<n&&d[s][y][1]<d[t][y][1])||(s>=0&&t>=n)){
										t=d[s][y][1];
										s=4;
									}else{
										s=6;
										t=d[t][y][1];
									}
									d[x][y][0]=s;
									d[x][y][1]=t;
								}
								// now carry on.
								q=d[x][y][1]+p;
								if(d[i][j][0]==0||q<d[i][j][1]){
									d[i][j][0]=h+2;
									d[i][j][1]=q;
								}
							}
						}
					}
				}
				if(j==0&&(b<0||d[i][j][1]<c)){
					b=i;
					c=d[i][j][1];
				}
			}
		}
		String o="";
		i=b;
		j=0;
		while(j<m){
			if(r[i][j]=='\0')j=m;else{
				o+=r[i][j]+",";
				h=d[i][j][0]-2;
				if(h>1)i+=h-3;
				else{i+=h;j++;}
			}
		}
		System.out.println(o+"\b:"+c);
	}
}

