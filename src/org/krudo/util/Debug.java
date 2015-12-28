package org.krudo.util;

//
import static org.krudo.Const.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Trans.*;
import static org.krudo.Zobrist.hash;

//
import org.krudo.Line;
import org.krudo.Move;
import org.krudo.Node;

//
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileInputStream;
import java.util.regex.*;
import java.io.FileNotFoundException;


//
public final class Debug {

	//
	public final static void dump(String t,Line l) {		
		/*
		String d = "";
		String s = "";
		
		for(int i=LINE_OFFSET;i<l.i;i++) {
			d += s + i2m(l.s[i],l.v[i],l.k[i])+" ("+i2k(l.k[i])+")" ;
			s = i%3==2 ? "\n" : " ";
		}	
		echo(t+d);
		*/
	}

	public final static void line(int d, Move m) {		
		String l = d+" "+m.l+" ";
		for(int i=0;i<m.l;i++) {
			l += " "+i2m(m.s[i],m.v[i],m.k[i]);
		}	
		echo(l);
	}

	//
	public final static void dump(Line l) {		
		dump("",l);
	}

	//
	public final static void dump(Line l, int i) {
		
	}
	
	//
	public final static void dump(Move m) {				
		String d = "";
		String s = "";
		for(int i=0;i<m.l;i++) {
			d += s + i2m(m.s[i],m.v[i],m.k[i])+" " ;
			s = i%6==5 ? "\n" : " ";
		}	
		echo(d);
	}
	
	//
	public final static void dump(Move[] pv) {				
		for (int j=0; j<pv.length; j++) {
			Move m = pv[j];
			String d = "";
			String s = "";
			for(int i=0;i<m.i;i++) {
				d += s + i2m(m.s[i],m.v[i],m.k[i])+" ("+i2k(m.k[i])+m.w[i]+")" ;
				s = i%10==9 ? "\n" : " ";
			}	
			echo("("+j+")",d);
		}
	}
	
	//
	public final static void dump(Move m, int i) {		
		echo(i2m(m.s[i],m.v[i],m.k[i])+" ("+i2k(m.k[i])+m.w[i]+")");		
	}

	//
	public final static void dump(Node n) {
		for(int r=0;r<8;r++) {
			for(int c=0;c<8;c++) {
				System.out.print(i2p(n.B[(7-r)*8+c])+" ");
			}
			System.out.print(r==0 && n.T==b || r==7 && n.T==w ? "<" : " ");
			switch(r) {
				case 0: keys("e:",i2s(n.e),"c:",Integer.toBinaryString(n.c)); break;
				case 1: keys("cw:",n.cw,"cb:",n.cb); break;
				case 2: keys("wks:",i2s(n.wks),"bks:",i2s(n.bks)); break;
				case 3: keys("wrs:",i2s(n.wks),"brs:",i2s(n.bks)); break;
				case 4: keys("ph:",n.cw,"ew:",n.wks); break;
				case 5: keys("wpw:",n.wks,"bpw:",n.wks); break;
				case 6: keys("hm:",n.hm,"n:",n.n); break;
				case 7: keys("h:",Long.toHexString(hash(n))); break;					
			}
		}		
	}
		
	//
	public final static void dump(Exception e) {
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		System.out.print(s.toString()); 	
	}
	
	//
	public final static void dump(int[] s) {
	
		for (int i=0; i<s.length; i++) {
			System.out.print(i2s(s[i])+" "); 			
		}
		
	
	
	}
	
	
	public final static String desc(Node n, Move m, int i) {
	
		String a = "";
			
		int p = n.B[m.s[i]];
		int x = n.B[m.v[i]];

		if ((m.k[i] & cast) != cast) {
			if (p!=wp && p!=bp) {
				a += i2f(p);
			} 

			if (x != 0) {
				a += "x";
			}

			a += i2s(m.v[i]);

			/*
			if (x != 0) {
				a += " ("+i2f(x)+")";
			}
			*/
		} 

		//
		else if ((m.k[i]&ksca) == ksca) {
			a += "O-O";
		} 

		//
		else if ((m.k[i]&qsca) == qsca) {
			a += "O-O-O";
		}

		//
		n.domove(m,i);
		if (n.T==w?n.black_attack(n.wks):n.white_attack(n.bks)) {
			a += "+";
		}
		n.unmove();
		
		//
		return a;	
	}
	
	
	//
	public final static void desc(Line l) {
		/*
		//
		String o = "";
		
		//
		int n = 1;
		
		//
		for(int i=0; i<l.i; i++) {
			
			if (mask(l.p[i],t,w)) {
				o += n+".";				
			}
			
			if (l.p[i]!=wp && l.p[i]!=bp) {
				o += i2f(l.p[i]);
			}
			
			o += i2s(l.v[i]);
			
			o += " ";
			
			if (mask(l.p[i],t,b)) {
				n++;				
			}			
		}
			
		//
		echo(o);*/
	}
	
	//
	public final static void desc(Node n, Move m) {
		
		String o = "";

		//
		for(int i=0; i<m.l; i++) {
			
			
			
			String a = "";
			
			int p = n.B[m.s[i]];
			int x = n.B[m.v[i]];
			
			if ((m.k[i] & cast) != cast) {
				if (p!=wp && p!=bp) {
					a += i2f(p);
				} 

				if (x != 0) {
					a += "x";
				}

				a += i2s(m.v[i]);
				
				if (x != 0) {
					a += " ("+i2f(x)+")";
				}
			} 
			
			//
			else if ((m.k[i]&ksca) == ksca) {
				a += "O-O";
			} 
			
			//
			else if ((m.k[i]&qsca) == qsca) {
				a += "O-O-O";
			}
			
			//
			o += lpad(a,8) + rpad(m.w[i],4)+"\n";;
		}
		
		echo(o);
	}
	
	//
	public final static void table(Move moves) {
		for(int l=0; l<moves.l; l++) {
			echo(i2m(moves,l),moves.w[l]);		
		}	
	}
	
	//
	public final static void tune(Node n) {
		
		dump(n);
		echo("-------------");
		Move m = n.legals().sort();
		
		for(int l=0; l<m.l; l++) {
			echo(
				pad(i2m(m.s[l],m.v[l],m.k[l],n.B[m.s[l]]),5),
				pad(m.w[l],5)
			);
		}
		
	}
	
	public static final class Perft {
	
		//
		public int n; // nodes
		public int e; // en-passant captures	
		public int c; // castling
		public int x; // captures
		public int h; // checks 
		public int m; // chack-mates

		public long t; // time elapsed
		public long r; // ratio

		//
		public final static void start(Node n, int d, Perft p) {

			long s;
			long e;

			s = System.currentTimeMillis();		
			doing(n,d,p,0,0,0);
			e = System.currentTimeMillis();

			p.t = e - s;
			p.r = p.t>0 ? p.n/p.t : 0;

		}

		//
		public final static void doing(Node n, int d, Perft p, int s, int v, int k) {		
			if (d>0) {			
				Move m = n.legals().sort();
				for(int l=0; l<m.l; l++) {								
					n.domove(m,l);				
					doing(n,d-1,p,m.s[l],m.v[l],m.k[l]);
					n.unmove();
				} 
			} else {
				p.n++;
				if (mask(k,capt)) {
					p.x++;
				}
				if (mask(k,ecap)) {
					p.e++;
				}			
				//n.T ^= t;
				//if (n.attack(n.T == wt ? n.bks : n.wks)) {
				//	p.h++;
				//}
				//n.T ^= t;			
			}
		}

	
		//
		public final static void table(Node n, int d) {				
			echo("#     nodes      ms   rx    capt   ec   check");		
			echo("----------------------------------------------");
			for(int i=1; i<=d; i++) {
				Perft p = new Perft();
				start(n,i,p);
				echo(
					pad(i,1),
					pad(p.n,9),
					pad(p.t,7),
					pad(p.r,4),
					pad(p.x,7),
					pad(p.e,4),
					pad(p.h,7)
				);
				// "perft("+d+"): "+c+" ("+String.valueOf(m)+" ms) ["+r+"]";
			}	
		}

	}


	
	//
	public final static String perft(Node n, int d) {
		long s = System.currentTimeMillis();
		long c = doing(n,d);
		long e = System.currentTimeMillis();
		long m = (e - s);
		long r = m>0 ? c/m : 0;
		return "perft("+d+"): "+rpad(c,10)+rpad(String.valueOf(m)+" ms",12)+rpad(r+" kNPS",12);
	}
	
	//
	public final static long doing(Node n, int d) {		
		if (d>0) {
			int c = 0;
			Move m = n.legals().sort();
			m.loop();
			for(int i=0; i<m.l; i++) {								
				n.domove(m,i);				
				c += doing(n,d-1);
				n.unmove();
			} 
			m.stop();
			return c;
		}
		return 1;
	}
	
	public final static String[][] EPDReader(String f) {
		
		try {
			File file = new File(f);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String epd = new String(data);
						
			String[] row = epd.split("\\r?\\n");
			String [][] ret = new String[row.length][3];
			
			//
			for(int i=0; i< row.length; i++) {
			
				//
				Pattern p = Pattern.compile("(.*) bm ([^;]+); id \"([^\"]+)\"");
				Matcher m = p.matcher(row[i]);

				//	
				if (m.find()) {
					ret[i][1] = m.group(1);
					ret[i][2] = m.group(2);
					ret[i][0] = m.group(3);
				}				
			}
			
			//
			return ret; 
			
			//echo (epd);
		} catch (Exception e) {
		
		}
		
		return null;
	}
	
}
