import java.util.ArrayList;

public class Graph
{
    private ArrayList<Node> GNodes = new ArrayList<Node>();

    public Graph()
    {
    }

    public int numberOfNodes()
    {
        return GNodes.size();
    }

    public Node nodeAtPosition(int index)
    {
        return GNodes.get(index);
    }

    public int getPositionOfNode(Node node)
    {
        for (int i = 0; i < GNodes.size(); i++)
        {
            if (GNodes.get(i).getCourseID() == node.getCourseID())
                return i;
        }
        return -1;

    }

    public void addNodeToTheGraph(Node node)
    {
        GNodes.add(node);
    }

    public void createEdge(Node node1, Node node2)
    {
        node1.getAdjacentNodeList().add(node2);
        node2.getAdjacentNodeList().add(node1);
    }

    public Node getNodeOfID(int coarseID)
    {
        for (int i = 0; i < GNodes.size(); i++)
        {
            if (GNodes.get(i).getCourseID() == coarseID)
                return GNodes.get(i);
        }
        return null;
    }

    public int[] degreeArray()
    {
        int[] DegreeArray = new int[GNodes.size()];
        for (int i = 0; i < GNodes.size(); i++)
        {
            DegreeArray[i] = GNodes.get(i).getAdjacentNodeList().size();
        }
        return DegreeArray;
    }

    public Node getHighestDegreeVertex() {
        int highestDegVertexIndex = 0;

        for (int i = 0; i < degreeArray().length; i++) {
            if (degreeArray()[i] > degreeArray()[highestDegVertexIndex])
                highestDegVertexIndex = i;
        }

        return GNodes.get(highestDegVertexIndex);
    }

    public boolean areAdjacent(Node node1, Node node2)
    {
        return node1.inTheAdjacentList(node2);
    }

    public void printGraph()
    {
        for (Node gNode : GNodes)
        {
            System.out.print(gNode.getCourseID() + "--->");
            for (int j = 0; j < gNode.getAdjacentNodeList().size(); j++)
            {
                System.out.print(gNode.getAdjacentNodeList().get(j).getCourseID() + "--->");
            }
            System.out.println();
        }
    }

    public ArrayList<Node> getGNodes()
    {
        return GNodes;
    }
}
