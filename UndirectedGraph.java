// Matthew Lutz, CS340, Project One

import java.io.*;
import java.util.*;

//import TopologicalSort.VertexNode;

public class UndirectedGraph {
	

	private class VertexNode {
		private String name;
		private VertexNode nextV; //next vertex in the vertex list
		private EdgeNode edges1; //head of the edge list 1 for this vertex
		private EdgeNode edges2; //head of the edge list 2 for this vertex
		private boolean queued;
		
		private VertexNode(String n, VertexNode v) {
			name = n;
			nextV = v;
			edges1 = null;
			edges2 = null;
			queued = false;
		}
	}
	
	private class EdgeNode {
		private VertexNode vertex1; //first vertex in the edge
		private VertexNode vertex2; //second vertex in the edge
		 //vertex1.name < vertex2.name
		private EdgeNode nextE1; //next edge in edge list 1
		 private EdgeNode nextE2; //next edge in edge list 2
		private EdgeNode(VertexNode v1, VertexNode v2) {
		 //PRE: v1.name < v2.name
		vertex1 = v1;
		vertex2 = v2;
		nextE1 = null;
		nextE2 = null;
		}
		 private void setNextE1(EdgeNode e) {
		 nextE1 = e;
		 }
		 private void setNextE2(EdgeNode e) {
		 nextE2 = e;
		 }
	}
	private VertexNode vertices; //head of the vertex list
    private int numVertices;

	public UndirectedGraph() {
	vertices = null; //the vertex list is initially empty(no sentinel node is used)
	 numVertices = 0;
	}
	public void addVertex(String s) {
	 //PRE: The vertex list is sorted in ascending order (based on the name)
	 /* insert a new vertex into the vertex list and
	 keep the vertex list sorted in ascending order (based on the name)
	 */
		//Checks first node
		if (vertices == null) {
            vertices = new VertexNode(s, null);
            numVertices++;
            return;
        }
		
		VertexNode prev = null;
		VertexNode curr = vertices;
		
		while (curr != null && s.compareTo(curr.name) > 0) {
            prev = curr;
            curr = curr.nextV;
        }
        
        // Check for duplicate vertices
        if (curr != null && s.equals(curr.name)) {
            return; // Don't add duplicate vertices
        }
        
        VertexNode newNode = new VertexNode(s, curr);

        if (prev == null) {
            vertices = newNode;
        } else {
            prev.nextV = newNode;
        }


        numVertices++;

	}
	
	public void addEdge(String n1, String n2) {
		/*PRE: the vertices with names n1 and n2 have already been added
		 and the edge list 1 for lesser of n1 and n2 is sorted in
		 ascending order based on the greater of n1 and n2
		 and the edge list 2 for the greater of n1 and n2
		 is sorted in ascending order based on the lesser of n1 and n2
		 */
		 /* add the new edge to the adjacency list and keep the edge lists sorted
		 see the diagrams on the previous slides for more details
		 */
		VertexNode vertex1 = null;
        VertexNode vertex2 = null;
        VertexNode current = vertices;
        
        while (current != null) {
        	if(current.name.equals(n1)) {
        		vertex1 = current;
        	} else if(current.name.equals(n2)) {
        		vertex2 = current;
        	}
        	current = current.nextV;
        }
        if (vertex1 == null || vertex2 == null) {
            // One of the vertices was not found.
            return;
        }
        if (vertex1.name.compareTo(vertex2.name)>0) {
        	VertexNode temp = vertex1;
            vertex1 = vertex2;
            vertex2 = temp;
        }
        
        EdgeNode newEdge = new EdgeNode( vertex1, vertex2);
        
        EdgeNode prevE1 = null;
        EdgeNode currE1 = vertex1.edges1;
        while (currE1 != null && currE1.vertex2.name.compareTo(vertex2.name) < 0) {
        	prevE1 = currE1;
        	currE1 = currE1.nextE1;
        }
        
        newEdge.nextE1 = currE1;
        
        if (prevE1 == null) {
            vertex1.edges1 = newEdge;
        } else {
            prevE1.nextE1 = newEdge;
        }
        
        EdgeNode prevE2 = null;
        EdgeNode currE2 = vertex2.edges2;
        while(currE2 != null && currE2.vertex2.name.compareTo(vertex2.name) > 0) {
        	prevE2 = currE2;
        	currE2 = currE2.nextE2;
        }
        
        newEdge.nextE2 = currE2;
        
        if(prevE2 == null) {
        	vertex2.edges2 = newEdge;
        }else {
        	prevE2.nextE2 = newEdge;
        }
        
	}
	
	public String bfs(String v) {
		/* if a breadth first search beginning with a vertex with name v contains all
		 vertices in the graph then return a string of the vertex names (separated by a space) in
		 a breadth first search order otherwise return the empty string.
		 */
		//String that we return
		String bfsString = "";
		int bfsCount = 0;
        Queue<VertexNode> BFSQueue = new LinkedList<>();
        VertexNode start = null;
        VertexNode curr = vertices;
        while(curr != null) {
        	if(curr.name.equals(v)) {
        		start = curr;
        	}
        	curr.queued = false;
        	curr = curr.nextV;
        }
        if (start == null) {
        	return "";
        }
        BFSQueue.add(start);
        start.queued = true;
        while(!BFSQueue.isEmpty()) {
        	VertexNode x = BFSQueue.poll();
        	bfsString += x.name + " ";
        	
        	EdgeNode currE = x.edges1;
        	while(currE != null) {
        		VertexNode y = currE.vertex2;
        		if(!y.queued) {
        			BFSQueue.add(y);
        			y.queued = true;
        		}
        		currE = currE.nextE1;
        	}
        	currE = x.edges2;
        	if(currE !=null) {
        		VertexNode y = currE.vertex1;
        		while(!y.queued) {
        			BFSQueue.add(y);
        			y.queued = true;
        		}
        		currE = currE.nextE2;
        	}
            bfsCount++;

        }
        if (bfsCount == numVertices) {
        	return bfsString;
        }else {
        	return "";
        }
        

	}
	
	public void printGraph() {
		 /*print the graph.
		 each line should contain a vertex, n, following
		 by the vertices adjacent to the n as shown in the
		 example on the following slide
		 */
		VertexNode curr = vertices;
		while(curr != null) {
			String edge1Str = "";
	        EdgeNode edge1Curr = curr.edges1;
	        while (edge1Curr != null) {
	            edge1Str += edge1Curr.vertex2.name + " "; // For edges1, you want to print the vertex2 of each edge
	            edge1Curr = edge1Curr.nextE1;
	        }

	        String edge2Str = "";
	        EdgeNode edge2Curr = curr.edges2;
	        while (edge2Curr != null) {
	            edge2Str += edge2Curr.vertex1.name + " "; // For edges2, you want to print the vertex1 of each edge
	            edge2Curr = edge2Curr.nextE2;
	        }

	        System.out.println("Node: " + curr.name + "	Edges1: " + edge1Str.trim() + "	Edges2: " + edge2Str.trim());
	        curr = curr.nextV;
	    
		}
	}
	
	//Tester
	public static void main(String args[]) throws IOException{
		//this code assumes the syntax of the import file is correct
		BufferedReader b = new BufferedReader(new FileReader(args[0]));
		UndirectedGraph g = new UndirectedGraph();
		String line = b.readLine();
		Scanner scan = new Scanner(line);
		while (scan.hasNext()) {
		g.addVertex(scan.next());
		}
		line = b.readLine();
		while (line != null) {
		scan = new Scanner(line);
		g.addEdge(scan.next(), scan.next());
		line = b.readLine();
		}
		g.printGraph();
		System.out.println("BFS Starting at " + args[1] + ": " + g.bfs(args[1]));
		System.out.println("BFS Starting at " + args[2] + ": " + g.bfs(args[2]));
		b.close();
		scan.close();
		
	}
}
