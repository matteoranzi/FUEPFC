package tictactoe.messages;

import tictactoe.CellStatus;

import static tictactoe.messages.GameParameters.TABLE_SIZE;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 14/03/19
 * Time: 22.23
 */
public class MatchStatusMessage extends Message {
    private CellStatus[][] tableStatus;

    public MatchStatusMessage(CellStatus[][] tableStatus){
        this.tableStatus = new CellStatus[TABLE_SIZE][TABLE_SIZE];

        for(int i = 0; i < TABLE_SIZE; i++){
            for(int j = 0; j < TABLE_SIZE; j++){
                this.tableStatus[i][j] = tableStatus[i][j];
            }
        }
    }

    public CellStatus[][] getTableStatus(){
        return this.tableStatus;
    }
}