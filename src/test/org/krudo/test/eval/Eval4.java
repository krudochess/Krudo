/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.eval;

//
import org.krudo.*;

//
import static org.krudo.test.debug.Debug.*;
    
//
public class Eval4 
{
    //
    public static void main(String[] args) 
    {    
        //
        DEBUG_SHOW_MOVE_WEIGHT = true;
        
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos();
        
        //
        //Eval.walk(n, 27, 1);
    }
}