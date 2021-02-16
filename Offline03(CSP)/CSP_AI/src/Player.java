import java.util.ArrayList;
import java.util.HashMap;

public class Player
{
    ArrayList<Cell> emptyCells = new ArrayList<>();
    HashMap<Cell, ArrayList<Integer>> cellToDomainsMap = new HashMap<>();
    Grid grid;
     public int recursiveCalls = 0;
     public int numberOfBacktracks = 0;
     public int numberOfNodes = 0;


    public Player(Grid grid)
    {
        this.grid = grid;
    }

    public void fillMap()
    {
        for (int i = 0; i < grid.size; i++)
        {
            for (int j = 0; j < grid.size; j++)
            {
                if (grid.getCell(i, j) == 0)
                {
                    Cell c = new Cell(i, j);
                    ArrayList<Integer> elements = fillDomain(c);
                    emptyCells.add(c);
                    cellToDomainsMap.put(c, elements);
                } else
                {
                    Cell c = new Cell(i, j);
                    ArrayList<Integer> elements = new ArrayList<>();
                    elements.add(grid.getCell(i, j));
                    cellToDomainsMap.put(c, elements);
                }
            }
        }
    }

    private ArrayList<Integer> fillDomain(Cell c)
    {
        ArrayList<Integer> elements = new ArrayList<>();
        ArrayList<Integer> reverse = new ArrayList<>();
        for (int i = 0; i < grid.getSize(); i++)
        {
            if (i != c.col && grid.getCell(c.row, i) != 0)
            {
                reverse.add(grid.getCell(c.row, i));

            }
        }
        for (int i = 0; i < grid.getSize(); i++)
        {
            if (i != c.row && grid.getCell(i, c.col) != 0)
            {
               reverse.add(grid.getCell(i, c.col));
            }
        }

        for (int dom = 1; dom <= grid.getSize(); dom++)
        {
            if (!reverse.contains(dom))
                elements.add(dom);
        }
//        for (Integer element : elements)
//        {
//            System.out.print(element + " ");
//        }
//        System.out.println();

        return elements;
    }

    private boolean endOfGrid()
    { // returns true if the whole grid is full
        for (int i = 0; i < grid.size; i++)
        {
            for (int j = 0; j < grid.size; j++)
            {
                if (grid.getCell(i, j) == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(Cell cell, int value) {
        for (int i = 0; i < grid.getSize(); i++) {
            if (i != cell.col) {
                if (grid.getCell(cell.row, i) == value) {
                    return false;
                }
            }
        }
        for (int j = 0; j < grid.getSize(); j++) {
            if (j != cell.row) {
                if (grid.getCell(j, cell.col) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public int dynamicDegree(Cell cell)
    {
        int degree = 0;
        for (int i = 0; i < grid.getSize(); i++)
        {
            if (i != cell.col && grid.getCell(cell.row, i) == 0)
            {
                degree++;

            }
        }
        for (int i = 0; i < grid.getSize(); i++)
        {
            if (i != cell.row && grid.getCell(i, cell.col) == 0)
            {
                degree++;
            }

        }
        return degree;
    }
    public Cell nextCellUsingSDF()
    {
        int min = Integer.MAX_VALUE;
        ArrayList<Integer> domain;
        Cell cell = null;
        for (Cell emptyCell : emptyCells)
        {
            domain = cellToDomainsMap.get(emptyCell);
            int temp = domain.size();

            if (temp < min)
            {
                min = temp;
                cell = emptyCell;
            }
        }

        return cell;
    }

    public Cell nextCellUsingDomDeg()
    {
        double min = Double.MAX_VALUE;
        int degree = 2 * (grid.getSize() - 1);
        ArrayList<Integer> domain;
        Cell cell = null;
        for (Cell emptyCell : emptyCells)
        {
            domain = cellToDomainsMap.get(emptyCell);
            double temp = domain.size() / degree;

            if (temp < min)
            {
                min = temp;
                cell = emptyCell;
            }
        }

        return cell;
    }

    public Cell nextCellUsingBrelaz()
    {

        int min = Integer.MAX_VALUE;
        ArrayList<Integer> domain;
        Cell cell = null;
        Cell tempCell = null;
        for (Cell emptyCell : emptyCells)
        {
            domain = cellToDomainsMap.get(emptyCell);
            int temp = domain.size();

            if (temp < min)
            {
                min = temp;
                cell = emptyCell;
            }
            else if (temp == min)
            {
                if (dynamicDegree(cell) < dynamicDegree(emptyCell))
                {
                    temp = min;
                    cell = emptyCell;
                }

            }
        }

        return cell;
    }

    public boolean backtrack(Cell cell, int option) {
        numberOfNodes++;
        if (endOfGrid()) {
            return true;
        }

        ArrayList<Integer> valuesCell = cellToDomainsMap.get(cell);

        int value = 0;

        for (int i = 0; i < valuesCell.size(); i++) {
            value = valuesCell.get(i);
            grid.setCell(value, cell.row, cell.col);
            emptyCells.remove(cell);
            Cell nextCell = null;
            if (option == 1)
            {
                nextCell = nextCellUsingSDF();
            }
            else if (option == 2)
            {
                nextCell = nextCellUsingDomDeg();
            }
            else if (option == 3)
            {
                nextCell = nextCellUsingBrelaz();
            }
            if (isValid(cell, value)) {
                if (backtrack(nextCell, option)) {
                    return true;
                }
            }

        }
        numberOfBacktracks++;
        grid.setCell(0, cell.row, cell.col);
        emptyCells.add(cell);

        return false;
    }

    public boolean forwardChecking(Cell cell, int option)
    {
        numberOfNodes++;
        if (endOfGrid())
        {
            return true;
        }

        DeleteQueue delQueue = new DeleteQueue();
        delQueue.fcAddToDelete(cell, grid);
        ArrayList<Integer> valuesCell = cellToDomainsMap.get(cell);
        int value;

        for (int i = 0; i < valuesCell.size(); i++)
        {
            value = valuesCell.get(i);
            delQueue.executeDeletion(value, cellToDomainsMap);
            if (delQueue.checkForEmptyDomains(cellToDomainsMap))
            {
                delQueue.restoreDomains(value, cellToDomainsMap);
            } else
            {
                ArrayList<Integer> newDomain = new ArrayList<>();
                newDomain.add(value);
                cellToDomainsMap.put(cell, newDomain);
                grid.setCell(value, cell.row, cell.col);
                emptyCells.remove(cell);
                Cell nextCell = null;
                if (option == 1)
                {
                    nextCell = nextCellUsingSDF();
                }
                else if (option == 2)
                {
                    nextCell = nextCellUsingDomDeg();
                }
                else if (option == 3)
                {
                    nextCell = nextCellUsingBrelaz();
                }
                if (forwardChecking(nextCell, option))
                {
                    return true;
                } else
                {
                    delQueue.restoreDomains(value, cellToDomainsMap);
                    cellToDomainsMap.put(cell, valuesCell);
                    numberOfBacktracks++;
                }
            }
        }

        grid.setCell(0, cell.row, cell.col);
        emptyCells.add(cell);

        return false;
    }


}