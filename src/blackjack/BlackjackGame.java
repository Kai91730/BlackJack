/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

enum USER {player, pc}
/**
 *
 * @author User
 */
public class BlackjackGame 
{
    
    ArrayList<Card> cards=null;
    int currentCard=0;
    int currentPlayerCard=0;
    int currentPCCard=0;
    int Money=50,bets=10;
    int i,j,p;
    Card player[] = new Card[5];
    Card pc[] = new Card[5];
    Table table;
    Toolbar tbar;
    Frame mainWin;
    
    BlackjackGame() 
    {

        mainWin = new Frame();
        mainWin.setLocation(300,300);
        mainWin.setSize(600,600);
 
        mainWin.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
 
        mainWin.setBackground(Color.BLUE);
        //mainWin.setLayout(null);
        mainWin.setLayout(new BorderLayout());
            
        tbar = new Toolbar(this);
        tbar.setBackground(Color.orange);
        mainWin.add(tbar,BorderLayout.NORTH);
            
        table = new Table(this);
        table.setBackground(Color.LIGHT_GRAY);
        mainWin.add(table,BorderLayout.CENTER);
            
        mainWin.setVisible(true);
        this.tbar.startGameBtn.setEnabled(false);
  
    }
    
    public void initial()
    {
        if(this.tbar.startGameBtn.getLabel().equals("Start")) //開始遊戲
        {
            newCards();
            shuffling();
            
       
            this.tbar.startGameBtn.setLabel("Continue");
            
            if( this.tbar.message.equals("You Win!") ) 
            {
                Money+=20;
            }
            
            if(table.parent.getPoints(USER.pc)==21)
            {
                table.parent.tbar.message.setText("You lose!");
                table.parent.pc[1].showFront();
                table.parent.tbar.startGameBtn.setEnabled(false);
                this.tbar.plus.setEnabled(true);
                this.tbar.minus.setEnabled(true);
                this.tbar.betBtn.setEnabled(true);
            //    Card.parent.showControlPanel(false);
            }
            else if ( (table.parent.getPoints(USER.player)==21) && (table.parent.getPoints(USER.player)>table.parent.getPoints(USER.pc)) )
            {
                table.parent.tbar.message.setText("Black Jack! (x1.5)");
                tbar.Moneytxt.setText(Money + "+(" + (bets*1.5) + ")");
                Money+=(bets*1.5);
                table.parent.pc[1].showFront();
                player[2].showControlPanel(false);
                table.parent.tbar.startGameBtn.setEnabled(false);
                this.tbar.plus.setEnabled(true);
                this.tbar.minus.setEnabled(true);
                this.tbar.betBtn.setEnabled(true);
            } 
        }
        else //繼續遊戲
        {           
            currentCard = 0;
            table.parent.tbar.message.setText("");
            shuffling();
            this.table.reset();
  
        }
        
        pc[0].setCard(cards.get(currentCard++));
        player[0].setCard(cards.get(currentCard++));
        pc[1].setCard(cards.get(currentCard++));
        pc[1].showBack();
        player[1].setCard(cards.get(currentCard++));
        player[2].showControlPanel(true);

        currentPlayerCard=2;
        currentPCCard=2;
    }
    
    public void newCards()
    {
       if(cards==null)
        {  
           cards = new ArrayList<Card>();
           for(i=0;i<4;i++)
            {
                for(j=1;j<=13;j++)
                {
                    cards.add(new Card(i,j, table ));
                }
            }
        }
    }
    
    public void shuffling()
    {
        for(i=0;i<52;i++)
        {
            p = (int)(Math.round(Math.random()*10000))%52;

            Card tempI=cards.get(i);
            Card tempP=cards.get(p);
            cards.remove(i);
            cards.add(i, tempP);
            cards.remove(p);
            cards.add(p,tempI);
        }
        char[] suits={'\u2660', '\u2665', '\u2666', '\u2663'};
        char[] points={'A','2','3','4','5','6','7','8','9','T','J','Q','K'}; 
        
        i=0;
        for(Card c:cards)
        {
            System.out.print(suits[c.suit]);
            System.out.print(points[c.point-1] +" ");
            if(i++%13==12) System.out.println();
        }
        System.out.println();
 
    }    
    
    public int getPoints(USER u) 
    {
        int points=0;
        int numOfA=0;
        if(u==USER.pc)
        {
            for(int i=0;i<this.currentPCCard;i++)
            { 
               points+=pc[i].point > 10 ? 10:pc[i].point;
               if(pc[i].point==1)
                   numOfA++;
            }
            
            if(numOfA>=1)
                points = (points+10)>21 ? points : points+10;

        }
        else if(u==USER.player)
        {
            for(i=0;i<this.currentPlayerCard;i++)
            { 
               points+=player[i].point > 10 ? 10:player[i].point;
               if(player[i].point==1)
                   numOfA++;
            }
            
            if(numOfA>=1)
                points = (points+10)>21 ? points : points+10;                                          
        }
        return points;
    }
    
}

