/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.perft;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;

//
public class Perft4 
{
    static {{
        debug_set_config("MOVE_LEGALS",   false);
        debug_set_config("CACHE_LEGALS",  false);
        debug_set_config("REMAPS_PSEUDO", false);
    }}
    
    //
    public static void main(String[] args) 
    {    
        // 4865609     3817 ms   1274 kNPS
        // 4865609     3834 ms   1269 kNPS
        // 4865609     4404 ms   1104 kNPS
        // 4865609     5421 ms    897 kNPS
        // 4865609     6547 ms    743 kNPS
                
        Krudo.init();
        
        Node n = new Node();
                     
        n.startpos();
        
        print(perft(n, 5));
    }    
}
