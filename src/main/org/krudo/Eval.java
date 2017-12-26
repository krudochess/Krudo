
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Config.*;
import static org.krudo.Constants.*;

//
public final class Eval 
{  
    // capture piece weights from black pawn to white king
    private final static int[] CPW = new int[] {
        /*bp*/-100,  /*wp*/+100,  /*bn*/-285,  /*wn*/+285, 
        /*bb*/-305,  /*wb*/+305,  /*br*/-500,  /*wr*/+500, 
        /*bq*/-900,  /*wq*/+900,  /*bk*/-6090, /*wk*/+6090
    };
    
    // opening to ending weights
    public static final int[] OTE = new int[] {
        0, 0, 10, 10, 12, 12, 21, 21, 42, 42, 0, 0
    };
                
    // opening piece sqaure weights
    public final static int[][] OPW = {       
        // opening white pawn from a8 to h1
        new int[64],{
            +0,    +0,    +100,  +200,  +200,  +100,  +0,    +0,
            +0,    +0,    +70,   +84,   +85,   +60,   +0,    +0,              
            +0,    +0,    +50,   +64,   +65,   +50,   +0,    +0,
            +0,    +0,    +30,   +44,   +45,   +0,    +0,    +0,
            +0,    +0,    +20,   +34,   +25,   +0,    +0,    +0,              
            +0,    +0,    +10,   +24,   +15,   +0,    +0,    +0,
            +0,    +0,    +0,    -2,    -2,    +10,   +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,     
        },                
        // opening white knight from a8 to h1
        new int[64],{
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
            -20,   +0,    +50,   +0,    +0,    +50,   +0,    -20,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -10,   +0,    +5,    +20,   +24,   +5,    +0,    -10,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -10,   +0,    +11,   +0,    +0,    +12,   +0,    -10,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -30,   -10,   -20,   -20,   -20,   -20,   -10,   -30,    
        },            
        // white bishop from a8 to h1
        new int[64],{
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +8,    
            +0,    +10,   +0,    +0,    +0,    +0,    +10,   +0,    
            +0,    +0,    +11,   +0,    +0,    +10,   +0,    +0,    
            +0,    +0,    +0,    +5,    +5,    +0,    +0,    +5,    
            +0,    +5,    +0,    +0,    +0,    +0,    +20,   +0,    
            +0,    +0,    -10,   +0,    +0,    -10,   +0,    +0,    
        },    
        // white rook from a8 to h1
        new int[64],{
            +40,   +40,   +40,   +40,   +40,   +40,   +40,   +40,    
            +30,   +30,   +30,   +30,   +30,   +30,   +30,   +30,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +10,   +0,    +0,    +0,    +0,    
            -2,    +0,    +0,    +7,    +7,    -1,    +0,    +0,    
         },    
        // opening white queen from a8 to h1
        new int[64],{
            +5,    +10,   +10,   +10,   +10,   +20,   +10,   +5,    
            +30,   +40,   +40,   +40,   +40,   +40,   +40,   +30,    
            +20,   +30,   +32,   +14,   +12,   +30,   +30,   +20,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +0,    +10,   +0,    +18,   +4,    +10,   +0,    +0,    
            +0,    +0,    +10,   +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    -2,    +0,    +0,    +0,    +0,    
        },            
        // white king from a8 to h1
        new int[64],{
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,  
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,  
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,    
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,   
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,    
            +0,    +0,    -100,  -100,  -100,  -100,  +0,    +0,    
            +5,    +5,    +10,   -12,   -10,   -15,   +10,   +5,  
        }
    }; 

    // ending piece by sqaure weights
    public final static int[][] EPW = {                
        // ending white pawn fron a8 to h1
        new int[64],{
            +100,  +180,  +190,  +200,  +200,  +190,  +180,  +100,    
            +50,   +80,   +90,   +100,  +100,  +90,   +80,   +50,    
            +30,   +40,   +50,   +80,   +80,   +50,   +40,   +30,
            +8,    +10,   +10,   +16,   +16,   +20,   +10,   +8,
            +0,    +0,    +0,    +10,   +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },                
        /*wn*/
        new int[64],{
            -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
            -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
            -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
            -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
            -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
            -29,   -29,   -29,   -29,   -29,   -29,   -29,   -29,    
        },            
        /*wb*/
        new int[64],{
            -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
            -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
            -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
            -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
            -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
            -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*wr*/
        new int[64],{
            -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
            -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
            -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
            -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
            -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
            -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
            -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*wq*/
        new int[64],{
            -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
            -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,            
            -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
            -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
            -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
            -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
            -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,    
            -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
        },            
        /*wk*/
        new int[64],{
            -99,   -99,   -59,   -59,   -59,   -59,   -99,   -99,    
            -99,   -59,   -59,   -59,   -59,   -59,   -59,   -99,    
            -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
            -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
            -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
            -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
            -99,   -59,   -59,   -59,   -59,   -59,   -59,   -99,    
            -99,   -99,   -59,   -59,   -59,   -59,   -99,   -99,    
        }
    }; 
       
    // MVV/LAA 
    private final static int[][] MVV_LAA = {
             /* bp     wp     bn     wn     bb     wb     br     wr     bq     wq     bk     wk*/
        /*bp*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wp*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*bn*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wn*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*bb*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wb*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*br*/{ +5,    +5,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wr*/{ +5,    +5,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*bq*/{ +2,    +2,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wq*/{ +2,    +2,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*bk*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},
        /*wk*/{ +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0}
    };    
    
    // weight of five-pawn hash structure
    private final static int[] QPSW = {
        /*00000*/+0,    /*00001*/-0,    /*00010*/-0,    /*00011*/+18,    
        /*00100*/+0,    /*00101*/-0,    /*00110*/+18,   /*00111*/+0,    
        /*01000*/-0,    /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+15,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/-0,    /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+12,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] KPSW = {
        /*00000*/+0,    /*00001*/-0,    /*00010*/-0,    /*00011*/+12,    
        /*00100*/+0,    /*00101*/-0,    /*00110*/+15,   /*00111*/+0,    
        /*01000*/+0,    /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+18,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/-0,    /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+18,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] QPPW = {
        /*00000*/+0,    /*00001*/+30,   /*00010*/+10,   /*00011*/+0,    
        /*00100*/+26,   /*00101*/-0,    /*00110*/+0,    /*00111*/+0,    
        /*01000*/+24,   /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+45,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/+20,   /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/+10,   /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+20,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] KPPW = {
        /*00000*/+0,    /*00001*/+20,   /*00010*/+24,   /*00011*/+20,    
        /*00100*/+26,   /*00101*/+10,   /*00110*/+45,   /*00111*/+0,    
        /*01000*/+10,   /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+0,    /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/+30,   /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+0,    /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    //
    private final static int[][] WKS = {
        /* PIECE:  bp   wp   bn   wn  bb  wb  br  wr  bq  wq  bk  wk
        /* SS */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* NN */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* EE */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* WW */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* SW */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* SE */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* NE */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /* NW */{  +0,  +5,  +0,  +0, +0, +0, +0, +0, +0, +0, +0, +0 },
    };
    
    //
    private final static int[][] BKS = {
              /*bp wp bn wn bb wb br wr bq wq bk wk */
        /*SS*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*NN*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*EE*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*WW*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*SW*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*SE*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*NE*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },
        /*NW*/{ +5, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0, +0 },        
    };

    // white+black boardmap improve piece lookup on board
    public final static int[] bm = new int[]
    {
        c3, f3, d3, e3, c4, d4, e4, f4,
        a7, b7, c7, d7, e7, f7, g7, h7,
        a8, b8, c8, d8, e8, f8, g8, h8,
        c5, d5, e5, f5, b1, g1, c1, d1,
        f1, e1, h1, a1, e2, d2, c2, f2,
        h2, b2, g2, a2, a3, b3, h3, g3,
        a4, b4, g4, h4, a5, b5, g5, h5,
        a6, b6, c6, f6, g6, h6, d6, e6
    };

    //
    public void remaps(final int si, final int pi, final int s)
    {
        //
        bm[si] = bm[pi];

        //
        bm[pi] = s;
    }


    // init
    public final static void init()
    {
        int p, r, c, s;        
        
        // fill black weights by white specular
        for (p=0; p<12; p+=2) for (r=0; r<8; r++) for (c=0; c<8; c++)
        {          
            int s0 = r * 8 + c;
            int s1 = (7 - r) * 8 + c;          
            OPW[p][s0] = -OPW[p+1][s1];
            EPW[p][s0] = -EPW[p+1][s1];
        }
          
        // revert from a8..h1 into 0..63 board square
        for (p=0; p<12; p++) for (r=0; r<4; r++) for (c=0; c<8; c++)
        {
            //
            int s0 = r * 8 + c;
            int s1 = (7 - r) * 8 + c;

            //
            int t0 = OPW[p][s0];
            OPW[p][s0] = OPW[p][s1];
            OPW[p][s1] = t0;

            //
            int t1 = EPW[p][s0];
            EPW[p][s0] = EPW[p][s1];
            EPW[p][s1] = t1;
        }    
        
        // subtract opening to ending
        for (p=0; p<12; p++) for (s=0; s<64; s++) 
        {
            EPW[p][s] -= OPW[p][s];
        }
    }
    
    //
    public final static int node(final Node n) 
    {
        // if node eval is enabled pass-throu else return zero forever
        if (!EVAL_NODE) { return 0; }
        
        //
 //       if (MATERIAL.has(n.mhk)) { return MATERIAL.get(n.mhk); }
        
        //
 //       if (POSITION.has(n.phk)) { return POSITION.get(n.phk); } 
        
        //
        int w = cache_node(n);

        //
 //       POSITION.add(n.phk, w);

        //
        return w;
    }
    
    // eval 
    private static int cache_node(final Node node) 
    {   
        //
        int wps = 0;
        
        //
        int bps = 0;
        
        //
        int score = 0;
        
        // index count squares
        int si = 0;
        
        // index count pieces
        int pi = node.cw + node.cb;
        
        // looking for pieces
        do 
        {        
            // next observed square
            final int s = bm[si++];

            // get piece in start square
            final int p = node.B[s];
            
            // square not have a side to move piece skip
            if (p == O) { continue; }
            
            switch (p) 
            {
                //
                case wp: wps |= 1 << (s % 8); break;
                
                //
                case bp: bps |= 1 << (s % 8); break;
            }
            
            //
            final int i = p & lo;
                
            // decrease piece count
            pi--;
            
            // remap square in wbm 
            //if (REMAPS_EVAL) { node.remaps(si, pi, s); }

            // piece value
            score += CPW[i];
            
            //
            score += OPW[i][s] + ((EPW[i][s] * node.ote) >> 8);    
        } 
        
        //
        while (pi != 0);
        
        //
        score += pawn_structure(wps, bps);
        
        //
        score += white_king_safety(node);
        
        //
        score -= black_king_safety(node);
                
        //
        return node.t == w ? score : -score;
    }        
        
    //
    public final static int pawn_structure(int wps, int bps)
    {
        //
        int wsw = QPSW[wps >> 3] + KPSW[wps & 0b11111];
        
        //
        int bsw = QPSW[bps >> 3] + KPSW[bps & 0b11111];
        
        //
        int wis = ~(bps|(bps>>1)|(bps<<1)) & wps;
        
        //
        int bis = ~(wps|(wps>>1)|(wps<<1)) & bps;
                
        //
        int wiw = QPPW[wis >> 3] + KPPW[wis & 0b11111];
        
        //
        int biw = QPPW[bis >> 3] + KPPW[bis & 0b11111];
        
        // used for tuning
        if (false) 
        {
            //
            print("black struct:", bin(bps, 8), bsw);
            print("white struct:", bin(wps, 8), wsw);
            print(bin(bps >> 3, 5), bin(bps & 0b11111, 5));
            print(bin(wps >> 3, 5), bin(wps & 0b11111, 5));
            print("-");
            print("black passed:", bin(bis, 8), biw);
            print("white passed:", bin(wis, 8), wiw);
            print(bin(bis >> 3, 5), bin(bis & 0b11111, 5));
            print(bin(wis >> 3, 5), bin(wis & 0b11111, 5));
            print("-");
        }
        
        //
        return wsw + wiw - bsw - biw;
    }
    
    //
    public final static int white_king_safety(final Node node) 
    {
        //
        int score = 0;
        
        //
        for (int i = 0; i != 8; i++) 
        {        
            //
            int v = SPAN[node.wks][i];

            //
            if (v == xx) { continue; }
            
            //
            int p = node.B[v];
            
            //
            //print(square(v), piece(p), p != O ? WKS[i][p & lo] : 0);
                        
            //
            if (p != O) { score += WKS[i][p & lo]; } 
            
            //
            else if (node.black_attack(v)) { score -= 30; }
        }

        //
        return score;
    }
    
    //
    public final static int black_king_safety(
        final Node node
    ) {
        //
        int score = 0;
        
        //
        for (int i = 0; i != 8; i++) 
        {        
            //
            int v = SPAN[node.bks][i];

            //
            if (v == xx) { continue; }
            
            //
            int p = node.B[v];
            
            //
            //print(square(v), piece(p), p != O ? BKS[i][p & lo] : 0);
            
            //
            if (p != O) { score += BKS[i][p & lo]; } 
            
            //
            else if (node.white_attack(v)) { score -= 30; }
        }

        //
        return score;
    }
    
    // score move without play it used in search
    public final static void legals(
        final Node n
    ) {  
        //
        if (!EVAL_LEGALS) { return; }
        
        //
        int b = node(n);
        
        //
        for (int i = 0; i < n.legals.count; i++)            
        {
            //
            int score = b;
            
            //            
            final int s = n.legals.s[i];         
            
            // get versus square
            final int v = n.legals.v[i];
            
            // get moved piece
            final int p = n.B[s] & lo;
                      
            // get captured piece
            final int x = n.B[v] & lo;
                                  
            //
            if (EVAL_POSITIONAL)
            {
                //
                score += OPW[p][v] + ((EPW[p][v] * n.ote) >> 8); 
                
                //
                score -= OPW[p][s] + ((EPW[p][s] * n.ote) >> 8);
            }
            
            /*
            //
            if (EVAL_MVV_LAA) {
                w += x != O ? ml[p][x] : 0;
            }
            
            //
            if (EVAL_CAPTURE) {
                w += cw[x];
            }
            
            //
            if (EVAL_TAPERED_ENDING) {
                w += ew[p][v] >> op[n.cw];
            }
            
            //
            if (EVAL_TAPERED_OPENING) {
                w += ow[p][v] >> ep[n.cw];
            }
            */
            
            //print(s2s(s)+s2s(v)+"="+OPW[p][v]);
            
            // assign tapered value
            n.legals.w[i] = n.t == w ? score : -score;
        }   
    }          
}
