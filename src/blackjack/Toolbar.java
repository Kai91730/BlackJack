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
 * @author User
 */
public class Toolbar extends Panel {
    
    BlackjackGame parent;
    Button exitBtn,startGameBtn,betBtn,plus,minus;
    Label message,betMoneytxt,Moneytxt,Text;
    
    Toolbar(BlackjackGame p)
    {
        parent = p;
        message = new Label("");
        message.setSize(100, 20);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Text = new Label("Money:");
        this.add(Text);
        Moneytxt = new Label(""+parent.Money);
        this.add(Moneytxt);
        startGameBtn = new Button("Start");
        this.add(startGameBtn);
        exitBtn = new Button("Exit");
        this.add(exitBtn);
        minus = new Button("-10");
        this.add(minus);
        betMoneytxt = new Label(""+parent.bets);
        this.add(betMoneytxt);
        plus = new Button("+10");
        this.add(plus);
        betBtn = new Button("Bet");
        this.add(betBtn);
        
        exitBtn.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
        
        startGameBtn.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                startGameBtn.setEnabled(false);
                parent.initial();
                Moneytxt.setText(""+parent.Money);
                revalidate(); 
            }
        });
       
        
        minus.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                
                    if (parent.bets==10) //歸0鎖定
                    {
                       parent.bets-=10;
                       parent.tbar.minus.setEnabled(false);
                       betMoneytxt.setText(""+parent.bets);
                       System.out.println("賭金:"+parent.bets);
                       
                    }
                    else if( parent.bets >10 ) 
                    {
                        parent.bets-=10;
                        betMoneytxt.setText(""+parent.bets);
                        System.out.println("賭金:"+parent.bets);
                        parent.tbar.plus.setEnabled(true);       
                    }
                
            }
        });
        
        plus.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                if( parent.bets < parent.Money) 
                {
                   parent.bets+=10;
                   betMoneytxt.setText(""+parent.bets);
                   System.out.println("賭金:"+parent.bets);
                   parent.tbar.minus.setEnabled(true);       
                }
                else if (parent.bets < parent.Money-10) //不超出本金上限
                {
                   parent.bets+=10;
                   betMoneytxt.setText(""+parent.bets);
                   System.out.println("賭金:"+parent.bets);
                   parent.tbar.plus.setEnabled(false);
                }
            }
        });
        
        betBtn.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                if (parent.bets<10 || (parent.bets>parent.Money) )
                {
                    parent.tbar.Moneytxt.setText(parent.Money+" Not enough bets!");
                    revalidate();
                }
                else 
                {    
                    parent.tbar.minus.setEnabled(false);
                    parent.tbar.plus.setEnabled(false);
                    Moneytxt.setText(parent.Money + "-(" + parent.bets + ")");
                    parent.Money-=parent.bets;
                    
                    System.out.println("本金:"+parent.Money);
                    revalidate();
                    parent.tbar.betBtn.setEnabled(false); 
                    parent.tbar.startGameBtn.setEnabled(true);
                }
            }
        });
        this.add(message);
        System.out.println("本金:"+parent.Money);
        System.out.println("賭金:"+parent.bets);
        
    }  
}
