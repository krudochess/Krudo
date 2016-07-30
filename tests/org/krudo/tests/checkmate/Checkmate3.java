/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.tests.checkmate;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Constant.*;

//
public class Checkmate3 
{
    //
    public static void main(String[] args) 
    {
        PVs.init();
        
        Moves.init();
        
        Captures.init();
                
        String f = "b7/PP6/8/8/7K/6B1/6N1/4R1bk w";
        
        Node n = new Node();
        
        n.startpos(f);
                      
        Search s = new Search(n);
        
        
        
        try {
            s.start(5);
        } catch (Exception e)
        {
            dump(n);
            dump(n.L);
            e.printStackTrace();
        }
    }    
}
