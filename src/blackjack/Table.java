/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author U$ER
 */
class Table extends Panel{
    BlackjackGame parent;
    
    Table(BlackjackGame p)
    {
        parent=p;
        setLayout(new GridLayout(2,5));
        
        for(int i=0;i<5;i++)
        {
            parent.player[4-i]=new Card(-1,-1, this);
            add(parent.player[4-i],0,i);
            parent.pc[i]=new Card(-1,-1,this);
            add(parent.pc[i],1,i);
        }
        
        
        
    }
    void reset()
    {
        for(int i=0;i<5;i++)
        {
            parent.player[4-i].setCard(-1, -1);
            parent.pc[i].setCard(-1,-1);
        }

    }
}
