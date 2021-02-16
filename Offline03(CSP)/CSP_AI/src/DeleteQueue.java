import java.util.ArrayList;
import java.util.HashMap;

public class DeleteQueue
{
    public ArrayList<Cell> checkDelete = new ArrayList<>(); // list of candidates(variables) to be deleted
    public ArrayList<Cell> deletedList = new ArrayList<>(); // of deleted variables

    public void fcAddToDelete(Cell c,Grid board)
    {
        fcAddRow(c, board);
        fcAddColumn(c, board);

    }

    private void fcAddRow(Cell c,Grid board)
    {
        for (int i = 0; i < board.getSize(); i++)
        {
            if (i != c.col)
            {
                Cell newCell = new Cell(c.row, i);
                checkDelete.add(newCell);
            }
        }
    }

    private void fcAddColumn(Cell c,Grid board)
    {
        for (int i = 0; i < board.getSize(); i++)
        {
            if (i != c.row)
            {
                Cell newCell = new Cell(i, c.col);
                checkDelete.add(newCell);
            }
        }
    }


    public void executeDeletion(int value, HashMap map)
    {
        for (Cell c : checkDelete)
        {
            updateDomain(c, value, map);
        }
    }

    private boolean fcDeleteValue(ArrayList<Integer> values, int number)
    {
        for (int value : values)
        {
            if (value == number)
            {
                return true;
            }
        }

        return false;
    }

    private void updateDomain(Cell c, int value, HashMap<Cell, ArrayList<Integer>> map)
    {
        ArrayList<Integer> dominio = map.get(c);
        if (fcDeleteValue(dominio, value))
        {
            deletedList.add(c);
            dominio.remove(Integer.valueOf(value));
            map.put(c, dominio);
        }
    }

    public boolean checkForEmptyDomains(HashMap<Cell, ArrayList<Integer>> map)
    {
        for (Cell c : deletedList)
        {
            if (map.get(c).isEmpty())
            {
                return true;
            }
        }

        return false;
    }

    public void restoreDomains(int value, HashMap<Cell, ArrayList<Integer>> map)
    {
        for (Cell c : deletedList)
        {
            ArrayList<Integer> dominio = map.get(c);
            dominio.add(value);
            map.put(c, dominio);
        }
        deletedList.clear();

    }

}
