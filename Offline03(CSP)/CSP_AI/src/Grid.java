import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Grid
{
    private File file;
    private int[][] m_board;
    public int size ;





    public Grid(File file1)
    {
        file = file1;


    }

    public int getSize()
    {
        return size;
    }


    public int getCell(int i, int j)
    {
        return m_board[i][j];
    }


    public void setCell(int value, int row, int col)
    {
        m_board[row][col] = value;
    }


    public File getFile()
    {
        return file;
    }


    public void setFile(File fi)
    {
        file = fi;
    }


    public void loadBoard()
    {
        FileReader fr = null;
        String line;
        int row;
        int column;

        try
        {
            fr = new FileReader(file.getPath());
            BufferedReader bf = new BufferedReader(fr);
            try
            {
                String sizeCalc = bf.readLine();
                sizeCalc = sizeCalc.substring(2,sizeCalc.length() - 1);
                size = Integer.parseInt(sizeCalc);
                m_board = new int[size][size];

               emptyBoard();

                bf.readLine();
                bf.readLine();

                row = 0;
                while ((line = bf.readLine()) != null)
                {

                    if (row == size - 1)
                    {
                        line = line.substring(0, line.length() - 4);

                    }
                    else
                    {
                        line = line.substring(0, line.length() - 2);

                    }
                    String[] numeros = line.split(", ");


                    for ( column = 0; column < numeros.length; column++)
                    {
                        int value = Integer.parseInt(numeros[column].trim());
                        m_board[row][column] = value;
                    }
                    row++;
                }
            } catch (IOException e1)
            {
                System.out.println("Error reading file:" + file.getName());
            }
        } catch (FileNotFoundException e2)
        {
            System.out.println("Error opening file: " + file.getName());
        } finally
        {
            try
            {
                if (null != fr)
                {
                    fr.close();
                }
            } catch (Exception e3)
            {
                System.out.println("Error closing file: " + file.getName());
            }
        }
    }


    public void printBoard()
    {

        System.out.println("--------------------------------------------");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                System.out.print(m_board[i][j] + " ");;
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }


    public boolean emptyBoard()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                m_board[i][j] = 0;

            }
        }

       return true;
    }


}
