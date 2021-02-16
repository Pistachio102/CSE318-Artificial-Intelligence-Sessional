import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dsatur
{
    public static void main(String[] args)
    {
        String CourseFileName = "car-s-91.crs";
        String StudentFileName = "car-s-91.stu";
        Graph graph1 = Dsatur.createGraph(CourseFileName, StudentFileName);
        Map<Node, Integer> resultingColor1;
        resultingColor1 = Dsatur.dsaturOfDataset(graph1);
        double penalty = Dsatur.calculatePenalty(graph1, resultingColor1, StudentFileName);
        System.out.println("Penalty : " + penalty);
        Dsatur.kempeChainOnDsatur(graph1, resultingColor1, StudentFileName);

        CourseFileName = "car-f-92.crs";
        StudentFileName = "car-f-92.stu";
        graph1 = Dsatur.createGraph(CourseFileName, StudentFileName);
        resultingColor1 = Dsatur.dsaturOfDataset(graph1);
        penalty = Dsatur.calculatePenalty(graph1, resultingColor1, StudentFileName);
        System.out.println("Penalty : " + penalty);
        Dsatur.kempeChainOnDsatur(graph1, resultingColor1, StudentFileName);

        CourseFileName = "kfu-s-93.crs";
        StudentFileName = "kfu-s-93.stu";
        graph1 = Dsatur.createGraph(CourseFileName, StudentFileName);
        resultingColor1 = Dsatur.dsaturOfDataset(graph1);
        penalty = Dsatur.calculatePenalty(graph1, resultingColor1, StudentFileName);
        System.out.println("Penalty : " + penalty);
        Dsatur.kempeChainOnDsatur(graph1, resultingColor1, StudentFileName);

        CourseFileName = "tre-s-92.crs";
        StudentFileName = "tre-s-92.stu";
        graph1 = Dsatur.createGraph(CourseFileName, StudentFileName);
        resultingColor1 = Dsatur.dsaturOfDataset(graph1);
        penalty = Dsatur.calculatePenalty(graph1, resultingColor1, StudentFileName);
        System.out.println("Penalty : " + penalty);
        Dsatur.kempeChainOnDsatur(graph1, resultingColor1, StudentFileName);

        CourseFileName = "yor-f-83.crs";
        StudentFileName = "yor-f-83.crs";
        graph1 = Dsatur.createGraph(CourseFileName, StudentFileName);
        resultingColor1 = Dsatur.dsaturOfDataset(graph1);
        penalty = Dsatur.calculatePenalty(graph1, resultingColor1, StudentFileName);
        System.out.println("Penalty : " + penalty);
        Dsatur.kempeChainOnDsatur(graph1, resultingColor1, StudentFileName);




    }

    public static Map<Node, Integer> dsaturOfDataset(Graph graph1)
    {
        Map<Node, Integer> resultingColor = new LinkedHashMap<>();
        List<Node> coloredVertices = new ArrayList<>();
        List<Node> notColored = new ArrayList<>();
        for (int i = 0; i < graph1.numberOfNodes(); i++)
        {
            notColored.add(graph1.nodeAtPosition(i));
        }
        int[] coloring = new int[graph1.numberOfNodes()];
        for (int i = 0; i < graph1.numberOfNodes(); i++)
        {
            coloring[i] = -1;
        }

        Node highestDegreeVertex = graph1.getHighestDegreeVertex();
        coloring[graph1.getPositionOfNode(highestDegreeVertex)] = 0;
        coloredVertices.add(highestDegreeVertex);
        resultingColor.put(highestDegreeVertex, 0);
        notColored.remove(highestDegreeVertex);

        while (notColored.size() > 0)
        {
            Node vertice = Dsatur.getHighestSaturation(graph1, coloring);
            while (!notColored.contains(vertice))
            {
                vertice = Dsatur.getHighestSaturation(graph1, coloring);
            }
            boolean[] availableColors = new boolean[graph1.numberOfNodes()];
            for (int j = 0; j < graph1.numberOfNodes(); j++)
            {
                availableColors[j] = true;
            }

            int lastColor = 0;
            for (Node currentVertex : coloredVertices)
            {
                if (graph1.areAdjacent(vertice, currentVertex))
                {
                    int color = resultingColor.get(currentVertex);
                    availableColors[color] = false;
                }
            }
            for (int j = 0; j < availableColors.length; j++)
            {
                if (availableColors[j])
                {
                    lastColor = j;
                    break;
                }
            }
            resultingColor.put(vertice, lastColor);
            notColored.remove(vertice);
            coloredVertices.add(vertice);
            coloring[graph1.getPositionOfNode(vertice)] = lastColor;

        }


        Set<Map.Entry<Node, Integer>> set = resultingColor.entrySet();


        int max = 0;
        Iterator<Map.Entry<Node, Integer>> iterator = set.iterator();
        while (iterator.hasNext())
        {
            Map.Entry<Node, Integer> me = iterator.next();
            int x = me.getValue();
            if (x > max)
                max = x;

        }
        System.out.print("Timeslots : ");
        System.out.println(max + 1);

        return resultingColor;

    }

    public static double calculatePenalty(Graph graph1, Map<Node, Integer> resultingColor, String studentFileName)
    {
        int totalPenalty = 0;
        int totalStudents = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(studentFileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                // process the line.
                // System.out.println(line);

                String[] splited = line.split("\\s+");
                int[] splitedInt = new int[splited.length];
                for (int i = 0; i < splited.length; i++)
                {
                    splitedInt[i] = Integer.parseInt(splited[i]);
                }
                for (int i = 0; i < splitedInt.length - 1; i++)
                {
                    for (int j = i + 1; j < splitedInt.length; j++)
                    {
                        int difference = Math.abs(resultingColor.get(graph1.getNodeOfID(splitedInt[i])) - resultingColor.get(graph1.getNodeOfID(splitedInt[j])));
                        if (difference == 5)
                        {
                            totalPenalty += 1;
                        } else if (difference == 4)
                        {
                            totalPenalty += 2;
                        } else if (difference == 3)
                        {
                            totalPenalty += 4;
                        } else if (difference == 2)
                        {
                            totalPenalty += 8;
                        } else if (difference == 1)
                        {
                            totalPenalty += 16;
                        }
                    }
                }
                totalStudents += 1;
            }

            return totalPenalty / (double) totalStudents;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public static ArrayList<Node> BFS(Graph graph, int i)

    {
        ArrayList<Node> tree = new ArrayList<>();
        boolean[] visited = new boolean[graph.numberOfNodes()];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[i] = true;
        queue.add(i);

        while (queue.size() != 0)
        {
            i = queue.poll();
            tree.add(graph.nodeAtPosition(i));

            for (int j = 0; j < graph.nodeAtPosition(i).getAdjacentNodeList().size(); j++)
            {
                Node adjNode = graph.nodeAtPosition(i).getAdjacentNodeList().get(j);
                int n = graph.getPositionOfNode(adjNode);
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return tree;
    }

    public static void kempeChainOnDsatur(Graph graph, Map<Node, Integer> resultingColorSet, String StudentFileName)
    {
        ArrayList<Node> tree;
        Map<Node, Integer> tempResultingColor = resultingColorSet;
        double minPenalty = Dsatur.calculatePenalty(graph, resultingColorSet,StudentFileName);
        for (int i = 0; i < graph.numberOfNodes() - 1; i++)
        {
            for (int j = i + 1; j < graph.numberOfNodes(); j++)
            {
               // System.out.println("Calculating for (" + i + ", " + j + ")");
                if (graph.areAdjacent(graph.nodeAtPosition(i), graph.nodeAtPosition(j)))
                {
                    tree = Dsatur.BFS(graph, i);
                    for (Node node : tree)
                    {
                        if (tempResultingColor.get(node).equals(tempResultingColor.get(graph.nodeAtPosition(i))))
                        {
                            tempResultingColor.put(node, tempResultingColor.get(graph.nodeAtPosition(j)));
                            //System.out.println("Checking if equals to the first slot.");

                        } else if (tempResultingColor.get(node).equals(tempResultingColor.get(graph.nodeAtPosition(j))))
                        {
                            tempResultingColor.put(node, tempResultingColor.get(graph.nodeAtPosition(i)));
                          //  System.out.println("Checking if equals to the second slot.");
                        }
                    }
                    double tempPenalty = Dsatur.calculatePenalty(graph,resultingColorSet, StudentFileName);
                    if(tempPenalty < minPenalty)
                    {
                        minPenalty = tempPenalty;
                       // System.out.println(minPenalty);
                    }

                }
            }
        }
        System.out.println("Penalty after Kempe Chain Optimization : " + minPenalty);

    }

    public static Graph createGraph(String courseFileName, String studentFileName)
    {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(courseFileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                // process the line.
                // System.out.println(line);

                String[] splited = line.split("\\s+");
                Node node = new Node(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
                graph.addNodeToTheGraph(node);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(studentFileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {

                String[] splited = line.split("\\s+");
                int[] splitedInt = new int[splited.length];
                for (int i = 0; i < splited.length; i++)
                {
                    splitedInt[i] = Integer.parseInt(splited[i]);
                }
                for (int i = 0; i < splitedInt.length - 1; i++)
                {
                    for (int j = i + 1; j < splitedInt.length; j++)
                    {
                        if (!graph.getNodeOfID(splitedInt[i]).inTheAdjacentList(splitedInt[j]))
                        {
                            graph.createEdge(graph.getNodeOfID(splitedInt[i]), graph.getNodeOfID(splitedInt[j]));
                        }
                    }
                }

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        return graph;
    }

    public static Node getHighestSaturation(Graph graph, int[] coloring)
    {
        int maxSaturation = 0;
        int vertexWithMaxSaturation = -1;
        int length = graph.numberOfNodes();

        for (int i = 0; i < length; i++)
        {
            if (coloring[i] == -1)
            {
                Set<Integer> colors = new TreeSet<>();
                for (int j = 0; j < length; j++)
                {
                    if (graph.areAdjacent(graph.nodeAtPosition(i), graph.nodeAtPosition(j)) && coloring[j] != -1)
                    {
                        colors.add(coloring[j]);
                    }
                }
                int tempSaturation = colors.size();
                if (tempSaturation > maxSaturation)
                {
                    maxSaturation = tempSaturation;
                    vertexWithMaxSaturation = i;
                } else if (tempSaturation == maxSaturation)
                {
                    if (vertexWithMaxSaturation != -1 && graph.degreeArray()[i] >= graph.degreeArray()[vertexWithMaxSaturation])
                    {
                        vertexWithMaxSaturation = i;
                    }
                }
            }
        }

        if (vertexWithMaxSaturation == -1)
        {
            for (int i = 0; i < coloring.length; i++)
            {
                if (coloring[i] == -1)
                {
                    vertexWithMaxSaturation = i;
                }
            }
        }

        return graph.nodeAtPosition(vertexWithMaxSaturation);
    }
}
