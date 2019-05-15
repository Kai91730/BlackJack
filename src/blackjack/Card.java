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
class Card extends Panel {
    
    int suit;
    int point;
    BlackjackGame parent;
    boolean back=false;
    Panel controlPanel;
    Table table;
    Button addCard, stop,surrender;
    
    Card(int s, int p, Table tab)
    {
        this(s,p);
        table = tab;
        parent=tab.parent;
    }
    
    Card(int s, int p)
    {
        suit=s;
        point=p;
        this.setBackground(Color.white);
        
        controlPanel = new Panel();
        controlPanel.setBackground(Color.red);
        addCard = new Button("Hit");
        stop = new Button("Stand");
        surrender = new Button("surrender");
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(surrender,BorderLayout.SOUTH);
        controlPanel.add(addCard,BorderLayout.CENTER);
        controlPanel.add(stop, BorderLayout.NORTH);
        
        addCard.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                table.parent.player[table.parent.currentPlayerCard++].setCard(   //牌數+1
                        table.parent.cards.get(table.parent.currentCard++)
                );
                
                if(table.parent.getPoints(USER.player)<21)
                {    
                    if(table.parent.currentPlayerCard<5) //可加牌
                        table.parent.player[table.parent.currentPlayerCard].showControlPanel(true);
                    else if (table.parent.currentPlayerCard==5&&table.parent.getPoints(USER.player)<21) //過五關
                    {
                        table.parent.tbar.message.setText("5 Card Trick! (x2)");
                        table.parent.pc[1].showFront();
                        parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets*2) + ")");
                        parent.Money+=(parent.bets*2);
                        table.parent.tbar.startGameBtn.setEnabled(false);
                        table.parent.tbar.plus.setEnabled(true);
                        table.parent.tbar.minus.setEnabled(true);
                        table.parent.tbar.betBtn.setEnabled(true);
                        if( parent.Money < 5 )
                        {    
                            table.parent.tbar.Moneytxt.setText(table.parent.Money+" Not enough money!");
                            revalidate();
                        }
                    }
                    
                }
                else if(table.parent.getPoints(USER.player)>21) 
                {             
                   table.parent.tbar.message.setText("You lose!");
                   table.parent.pc[1].showFront();     
                   table.parent.tbar.startGameBtn.setEnabled(false);
                   table.parent.tbar.plus.setEnabled(true);
                   table.parent.tbar.minus.setEnabled(true);
                   table.parent.tbar.betBtn.setEnabled(true);
                   if( parent.Money < 5 )
                   {    
                        table.parent.tbar.Moneytxt.setText(table.parent.Money+" Not enough money!");
                        revalidate();
                   }
                }
                
                else if (p==21&&table.parent.getPoints(USER.player)==21) //點數相同
                {
                    table.parent.pc[1].showFront();
                    table.parent.tbar.message.setText("Draw (x1)");
                    parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                    parent.Money+=(parent.bets);
                    table.parent.tbar.startGameBtn.setEnabled(false);
                    table.parent.tbar.plus.setEnabled(true);
                    table.parent.tbar.minus.setEnabled(true);
                    table.parent.tbar.betBtn.setEnabled(true);
                    if( parent.Money < 5 )
                    {    
                            table.parent.tbar.Moneytxt.setText(table.parent.Money+" Not enough money!");
                            revalidate();
                    }
                }
                
                else if (table.parent.getPoints(USER.player)==21) // 21點
                {
                    table.parent.pc[1].showFront();
                    table.parent.tbar.message.setText("You Win! (x1)");
                    parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                    parent.Money+=(parent.bets);
                    table.parent.tbar.startGameBtn.setEnabled(false);
                    table.parent.tbar.plus.setEnabled(true);
                    table.parent.tbar.minus.setEnabled(true);
                    table.parent.tbar.betBtn.setEnabled(true);
                    if( parent.Money < 5 )
                    {    
                            table.parent.tbar.Moneytxt.setText(table.parent.Money+" Not enough money!");
                            revalidate();
                    }
                } 
              
         
                showControlPanel(false);
                  
            }
        });
        
        stop.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                showControlPanel(false);
                table.parent.pc[1].showFront();
                int p = table.parent.getPoints(USER.pc);
                int u = table.parent.getPoints(USER.player);
                System.out.println("point=" + p);
                
                while((p<17)&&(table.parent.currentPCCard<5)) //自動補牌
                {
                    table.parent.pc[table.parent.currentPCCard++].setCard(
                        table.parent.cards.get(table.parent.currentCard++)
                    );              
                    p=table.parent.getPoints(USER.pc);
                    System.out.println("point=" + p);
                }
                
                //pc --> p
                //player --> u

                if(p>21)
                {
                    if (u==21)
                    {
                        table.parent.tbar.message.setText("You Win! (x1)");
                        parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                        parent.Money+=(parent.bets);
                    }
                    else 
                    {
                        table.parent.tbar.message.setText("You Win! (x1)");
                        parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                        parent.Money+=(parent.bets);
                    }
                }
                else if(p>u)
                {
                    table.parent.tbar.message.setText("You lose!");
                }
                else if(p==u)
                {
                    table.parent.tbar.message.setText("Draw! (x1)");
                    parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                    parent.Money+=(parent.bets);
                }
                else
                {
                    table.parent.tbar.message.setText("You Win! (x1)");
                    parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets) + ")");
                    parent.Money+=(parent.bets);
                }
                
                if( parent.Money<10 )
                {    
                    table.parent.tbar.Moneytxt.setText(parent.Money + " Not enough money!");
                    revalidate();
                }
                else 
                {

                table.parent.tbar.startGameBtn.setEnabled(false);
                table.parent.tbar.plus.setEnabled(true);
                table.parent.tbar.minus.setEnabled(true);
                table.parent.tbar.betBtn.setEnabled(true);
                table.parent.tbar.revalidate();
                }
            }
        });
        
        surrender.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {
                if (parent.currentPlayerCard>2){}
                else
                {
                    table.parent.pc[1].showFront();
                    table.parent.tbar.message.setText("You lose!(Surrender) (x0.5)");
                    parent.tbar.Moneytxt.setText(parent.Money + "+(" + (parent.bets/2) + ")");
                    parent.Money+=(parent.bets*0.5);
                    table.parent.tbar.startGameBtn.setEnabled(false);
                    table.parent.tbar.plus.setEnabled(true);
                    table.parent.tbar.minus.setEnabled(true);
                    table.parent.tbar.betBtn.setEnabled(true);
                    table.parent.tbar.revalidate();
                }
            }
        });
                
    }
    public void paint(Graphics g)
    {
        String suits[]={"\u2660", "\u2665", "\u2666", "\u2663"};
        char[] points={'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
        g.setColor(Color.BLACK);
        if((suit==-1)&&(point==-1))
        {
            Dimension size = this.getSize();

            g.drawRect(1, 1, size.width-2, size.height-2);            
            g.drawLine(1, 1, size.width-2, size.height-2);
            g.drawLine(size.width-2,1,1,size.height-2);
        }
        else if(back)
        {
            g.drawString("Back", 45, 120);
        }
        else
        {
            Dimension size = this.getSize();
            g.drawRect(1, 1, size.width-2, size.height-2);
        
            switch(suit)
            {
                case 0:
                case 3:
                    g.setColor(Color.BLACK);
                    break;
                case 1:
                case 2:
                    g.setColor(Color.red);
                    break;
            }            
            g.drawString(suits[suit], 5, 20);
            g.setColor(Color.BLACK);
            g.drawString("" + points[point-1], 7, 40);
        }
    }

     void setCard(Card c)
    {
        this.setCard(c.suit, c.point);
    }
    
    void setCard(int s, int p)
    {
        suit=s;
        point=p;        
        repaint();
    }
    
    void showControlPanel(boolean b)
    {
        if(b)
        {
            setLayout(new BorderLayout());
            add(controlPanel, BorderLayout.CENTER);
            revalidate();
        }
        else
        {
            remove(controlPanel);
            revalidate();
        }
        
    }
    
    void showFront()
    {
        back=false;
        repaint();
    }
    void showBack()
    {
        back=true;
        repaint();
    }
}
