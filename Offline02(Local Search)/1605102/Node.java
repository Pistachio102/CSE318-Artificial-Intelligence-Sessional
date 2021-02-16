import java.util.ArrayList;

public class Node {
    private int CourseID;
    private int NumberOfStudents;
    private ArrayList<Node> AdjacentNodeList = new ArrayList<Node>();

    public Node(int courseID, int numberOfStudents) {
        CourseID = courseID;
        NumberOfStudents = numberOfStudents;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getNumberOfStudents() {
        return NumberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        NumberOfStudents = numberOfStudents;
    }

    public ArrayList<Node> getAdjacentNodeList() {
        return AdjacentNodeList;
    }

    public void setAdjacentNodeList(ArrayList<Node> adjacentNodeList) {
        AdjacentNodeList = adjacentNodeList;
    }

    public boolean inTheAdjacentList(int coarseID)
    {
        for (int i = 0; i < getAdjacentNodeList().size(); i++)
        {
            if (getAdjacentNodeList().get(i).getCourseID() == coarseID)
            {
                return true;
            }
        }
        return false;
    }

    public boolean inTheAdjacentList(Node node)
    {
        for (int i = 0; i < getAdjacentNodeList().size(); i++)
        {
            if (getAdjacentNodeList().get(i).getCourseID() == node.getCourseID())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "" + CourseID ;
    }
}
