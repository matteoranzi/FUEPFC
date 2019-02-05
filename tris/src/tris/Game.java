/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tris;

/**
 *
 * @author lorenzo.orsingher
 */
public class Game {
    
    char[][] field = new char[3][3];

    
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
    
    public void updateTable(String newf)
    {
        System.out.println("newF: " + newf);
        //field = newF;
    }
    
}




