# directedGraph
Undirected Graph Implementation

Overview
This project implements an undirected graph using parallel adjacency lists. The graph is comprised of vertices and edges. Vertices are unique entities identified by a name, while edges link two distinct vertices together.

Class Structure
UndirectedGraph: The primary class that encapsulates the entire graph structure.

VertexNode: Represents a vertex in the graph. Each vertex has a name and pointers to its edge lists.

EdgeNode: Represents an edge in the graph. An edge connects two distinct vertices.

Features
addVertex(String s): Adds a vertex with the specified name s to the graph.

addEdge(String n1, String n2): Adds an undirected edge between vertices n1 and n2.

bfs(String v): Performs a breadth-first search starting from the vertex named v. It returns the BFS traversal order if all vertices are reachable from v; otherwise, it returns an empty string.

printGraph(): Prints the graph's adjacency list representation.

main(): A tester function which reads from a specified input file to construct the graph and then prints the graph and performs BFS.

Usage
To use this project:

Ensure you have a Java environment set up.
Compile the UndirectedGraph class.
Run the main method, specifying the input file path as an argument.
The program will read the input file, construct the graph, and then print it. It will also demonstrate the BFS traversal from given starting vertices.
Input File Structure
The input file should be structured as follows:

The first line should contain all the vertex names, separated by spaces.
Each subsequent line should represent an edge, with two vertex names separated by a space.
