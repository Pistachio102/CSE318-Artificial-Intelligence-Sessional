public class Cell
{
    int row, col;

    public Cell()
    {
        row = 0;
        col = 0;
    }

    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Cell)
        {
            Cell c = (Cell) o;
            return (row == c.row && col == c.col);
        } else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    { // so the hashmap can search for cells
        return (row * 50000000 + col);
    }

}
