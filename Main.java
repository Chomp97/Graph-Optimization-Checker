package com.company;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void answerQueriesFromPath(String fileName, String testNumber, String correctPath) {
        long startTime = System.currentTimeMillis();
        int size, i = 0;
        File myAnswers = new File("myAnswers" + testNumber + ".txt");
        try{
            myAnswers.createNewFile();
        }
        catch(IOException e){e.printStackTrace();}
        try{
            Path filePath = Paths.get(fileName);
            Scanner scanner = new Scanner(filePath);
            size = scanner.nextInt();
            Graph res = new Graph(size);
            while (i < size-1) {
                if (scanner.hasNextInt()) {
                    Edge e = new Edge(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                    res.insertEdge(e);
                    i++;
                } else {
                    scanner.next();
                }
            }
            res.rankTree();
            answerQueries(res, scanner, myAnswers);
            System.out.println(System.currentTimeMillis()-startTime);
            compareFiles(myAnswers.getPath(),correctPath);
        }
        catch (IOException e){e.printStackTrace();}
    }

    public static void answerQueries(Graph graph, Scanner scanner, File answers){
        int queries = scanner.nextInt();
        try{
            FileWriter fwr = new FileWriter(answers.getName());
            int i = 0;
            while (i < queries) {
                if (scanner.hasNextInt()) {
                    Edge q = new Edge(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                    if(graph.queryMeetsConditions(q)){
                        fwr.write("YES\n");
                    }
                    else {
                        fwr.write("NO\n");
                    }
                    i++;
                } else {
                    scanner.next();
                }
            }
            fwr.close();
        }
        catch (IOException e){e.printStackTrace();}
    }

    public static void compareFiles(String firstPath, String secondPath){
        try{
            BufferedReader br1 = new BufferedReader(new FileReader(firstPath));
            BufferedReader br2 = new BufferedReader(new FileReader(secondPath));
            int line = 1;
            String br1Line = br1.readLine();
            String br2Line = br2.readLine();
            while(br1Line != null || br2Line != null){
                if((br1Line == null || br2Line == null) || !br1Line.equalsIgnoreCase(br2Line)){
                    System.out.println("Files not equal on line " + line);
                    return;
                }
                br1Line = br1.readLine();
                br2Line = br2.readLine();
                line++;
            }
        } catch (IOException e){e.printStackTrace();}
    }

    public static void main(String[] args) {
        String basePath = new File("").getAbsolutePath();
        String filepath = basePath.concat("\\src\\test1\\input.txt");
        String correctPath = basePath.concat("\\src\\test1\\correct.txt");
        answerQueriesFromPath(filepath, "1", correctPath);
        /*answerQueriesFromPath("test2/input.txt");
        answerQueriesFromPath("test3/input.txt");
        answerQueriesFromPath("test4/input.txt");
        answerQueriesFromPath("test5/input.txt");
        answerQueriesFromPath("test6/input.txt");
        answerQueriesFromPath("test7/input.txt");
        answerQueriesFromPath("test8/input.txt");
        answerQueriesFromPath("test9/input.txt");
        answerQueriesFromPath("test10/input.txt");
        answerQueriesFromPath("test11/input.txt");*/
    }
}
