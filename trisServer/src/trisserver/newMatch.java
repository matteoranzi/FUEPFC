/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trisserver;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tris.Dati;
/**
 *
 * @author Lorenzo
 */
public class newMatch extends Thread{
    
    ArrayList<Connect> c;
    char[][] field = new char[3][3];
    Connect c1, c2;
    Boolean ready = true;
    int areReady = 0;
    String X, O;
    boolean winner;
    Connect winClient;
    public newMatch(Connect c1, Connect c2)
    {
        c1.setParent(this);
        c2.setParent(this);
        this.c1 = c1;
        this.c2 = c2;
        fillField();
        
        
        
        this.start();        
    }
    
    public newMatch(Connect c1)
    {
        c1.setParent(this);

        c.add(c1);
         
        this.start();        
    }
    
    @Override
    public void run()
    {
        while(c1.ID == "" || c2.ID == "")
        {
            System.out.println("waiting for ID " + c1.ID + " " + c2.ID);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {}
        }
        System.out.println("IDONE " + c1.ID + " " + c2.ID);
        
        X = c1.ID;
        O = c2.ID;
        
        while(winner == false)
        {
            
            c1.sendMsg(new Dati("WAIT_FOR_TURN"));
            System.out.println("areReady: " + areReady);
            while(areReady != 1){System.out.print("");}
            if(winner == true)
            {
                break;
            }
            c1.sendMsg(new Dati("FIELD", field));
            c2.sendMsg(new Dati("FIELD", field));
            
            
            c2.sendMsg(new Dati("WAIT_FOR_TURN"));
            System.out.println("areReady: " + areReady);
            while(areReady != 2){System.out.print("");}
            if(winner == true)
            {
                break;
            }
            c1.sendMsg(new Dati("FIELD", field));
            c2.sendMsg(new Dati("FIELD", field));
            
            System.out.println("areReady: " + areReady);
            areReady = 0;
            System.out.println("NOW WAITING FOR USERS");
            
        }
        
        System.out.println("GAME OVER, " + winClient.ID + " WON");
//        try{
//                
//        }catch(Exception e){}
    }
    
    public void communicate(Connect c, Dati s)
    {
        //System.out.println("CLIENT SAYS: " + s);
//        try{
//        outC1.writeObject(s);
//        }catch(IOException e){}
        elabMessage(c, s);
    }
    
    private void elabMessage(Connect c, Dati s)
    {

        if(s.getType().compareTo("MOVE")==0)
        {
            String cooS[] = s.getCoo().split("");
            Integer coo[] = {Integer.valueOf(cooS[0]), Integer.valueOf(cooS[1])};
            if(coo[0] < 3 && coo[1] < 3)
            {
                if(field[coo[0]][coo[1]] == '-')
                {
                    if(c.ID.compareTo(X) == 0)
                    {
                        field[coo[0]][coo[1]] = 'X';
                    }else
                    {
                        field[coo[0]][coo[1]] = 'O';
                    }
                    printGame();
                    if(checkWinner(c))
                    {
                        System.out.println("WINNER");
                    }
                    areReady++;
                }else
                {
                    c.sendMsg(new Dati("WAIT_FOR_TURN"));
                }
            }
            
            
        }
        
    }
    
    public boolean checkWinner(Connect c)
    {
        char cha;
        boolean found = false;
        int cont = 0;
        for(int i=0;i<3;i++)
        {
            if(field[i][0] != '-')
            {
                cha = field[i][0];
                for(int j=0;j<3;j++)
                {
                    if(field[i][j] == cha)
                    {
                        cont++;
                        if(cont == 3)
                        {
                            found = true;
                            j=3;
                            i=3;
                        }
                    }else
                    {
                        cont = 0;
                        j=3;
                    }
                }
            }
        }
        cont =0;
        for(int i=0;i<3;i++)
        {
            if(field[0][i] != '-')
            {
                cha = field[0][i];
                for(int j=0;j<3;j++)
                {
                    if(field[j][i] == cha)
                    {
                        cont++;
                        if(cont == 3)
                        {
                            found = true;
                            j=3;
                            i=3;
                        }
                    }else
                    {
                        cont = 0;
                        j=3;
                    }
                }
            }
        }
        cont =0;
        
        if(field[0][0] != '-' && field[0][2] != '-')
        {        
            if((field[0][0] == field[1][1] && field[1][1] == field[2][2])
                || (field[0][2] == field[1][1] && field[1][1] == field[2][0]))
            {
                System.out.println("AHAHAH");
                found = true;
            }
        }
        
        if(found)
        {
            winClient = c;
            winner = true;
            System.out.println("foundWinner");
            c1.sendMsg(new Dati("WINNER", c.ID));
            c2.sendMsg(new Dati("WINNER", c.ID));
            c1.sendMsg(new Dati("FIELD", field));
            c2.sendMsg(new Dati("FIELD", field));
            c1.sendMsg(new Dati("FIELD", field));
            c2.sendMsg(new Dati("FIELD", field));
        }
        
        return found;
    }
    
    public void printGame()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
                {
                    System.out.print(field[i][j] + " ");
                }
            
            System.out.println("");
        }
    }
    
    public void fillField()
    {
        for(int i=0;i<3;i++)
        {
        for(int j=0;j<3;j++)
            {
                    field[i][j] = '-';
            }
        }
    }  
}
