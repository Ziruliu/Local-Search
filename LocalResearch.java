import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class LocalResearch {

    private static JFrame frame;
    private static JFrame frmNnPuzzle;
    private static JTextPane textPane;
    Random rand = new Random();

    static int n;

    public static Queue<Node> queue = new LinkedList<Node>();
    public static Queue<Node> Treequeue = new LinkedList<Node>();
    public static Node current;
    public static String s = "";


    /**
     * Launch the application.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        String ANswer = JOptionPane.showInputDialog(null,"Do you have file to read ? (Y = Yes/N = NO) :");
        
        while(ANswer.charAt(0) != 'Y' && ANswer.charAt(0) != 'N'){
        	
        	ANswer = JOptionPane.showInputDialog(null,"Please enter only Y or N (Y = Yes/N = NO) :");
        
        }
        
        n = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the an odd size of the puzzle (If read a file, Input 1):"));
        
        while(n%2 == 0 ){
        	
        	n = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter only odd numbers (If read a file, Input 1):"));
        }
        
        int[][] matrixInput = new int[n][n];
        
        if(ANswer.charAt(0) == 'Y'){

            String fileAddr = JOptionPane.showInputDialog(null, "Enter the address of the input file:");

            // Path of File
            File file = new File(fileAddr);
            int counttt = 0;
            int[] araaay = new int[10000];
            Scanner input = new Scanner(file);
            while (input.hasNext() != false){
                araaay[counttt] = input.nextInt();
                counttt++;
            }
            n = araaay[0];
            matrixInput = new int[n][n];

            counttt = 1;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrixInput[i][j] = araaay[counttt];
                    counttt++;
                }
                System.out.println("");
            }

            input.close();

        } else	{
        	matrixInput = matrixInitialization(matrixInput);
        }
        
        char x = 'x';
        String input;

        do{
        	
        	int taskNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the task No. that you want to implement (Puzzle Representation =1, Puzzle Evaluation = 2, Hill Climbing = 3, Random Restarts = 4, Random Walk = 5, Simulated Annealing = 6, Genetic Algorithm = 7): "));
            
            while (taskNum <= 0 || taskNum >7){
            	taskNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Please enter a proper task No.(Puzzle Representation =1, Puzzle Evaluation = 2, Hill Climbing = 3, Random Restarts = 4, Random Walk = 5, Simulated Annealing = 6, Genetic Algorithm = 7): "));	
            }
        //	Task 1
        if(taskNum ==1){
        	initialize(matrixInput);
        	frmNnPuzzle.setVisible(true);
        }


        //	Task 2
        if(taskNum ==2)
        {
        	int[][] matrixB = new int[n][n];
        	s = "Puzzle Evaluation";
        	matrixB = puzzleEvaluation(matrixInput, 'c');
        	frame.setVisible(true);
        	JOptionPane.showMessageDialog(null, "The final value is: " + matrixB[n-1][n-1]);
        }

        //	Task 3
        if (taskNum == 3) {
            int InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of iterations: "));
            while(InputIteration <= 0){
            	InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "please enter a positive number for the number of iterations: "));
            }
            s = "Hill Climbing";
            hillClimbing(matrixInput, InputIteration);
        }

        // Task 4
        if (taskNum == 4) {
            int InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of iterations: "));
            while(InputIteration <= 0){
            	InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "please enter a positive number for the number of iterations: "));
            }
            
            int numOfRestarts = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of restarts: "));
        	

            int iterationsPerRestart = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of iterations per restart: "));
            
            while(numOfRestarts * iterationsPerRestart != InputIteration)
            {
            	JOptionPane.showMessageDialog(null, "Please make sure number of restarts * iterations per restart = input iterations");
            	
            	numOfRestarts = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Enter the number of restarts: "));
            	
            	iterationsPerRestart = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Enter the number of iterations per restart: "));
            	
            }
            s = "Random Restarts";
            randomRestarts(matrixInput, InputIteration, numOfRestarts, iterationsPerRestart);
        }

        //	Task5
        if (taskNum == 5) {
        	
            int InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of iterations: "));
            while(InputIteration <= 0){
            	InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "please enter a positive number for the number of iterations: "));
            }
        	
            double InputPossibility = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Enter the possibility to accept worse result: "));
            
            while(InputPossibility < 0 || InputPossibility > 1){
            	InputPossibility = Double.parseDouble(JOptionPane.showInputDialog(null,
                        "Please enter a possibility that is between 0 and 1: "));
            }
            s = "Random Walk";
            RandomWalk(matrixInput, InputIteration, InputPossibility);
        }

        //	Task6
        if (taskNum == 6) {
            int InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter the number of iterations: "));
            
            while(InputIteration <= 0){
            	InputIteration = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "please enter a positive number for the number of iterations: "));
            }
      
            double InitialTemperature = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Enter the initial temperature:"));
            
            while(InitialTemperature < 0){
            	InitialTemperature = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "please enter a positive number for the initial temperature: "));
            }
        	
            double decayRate = Double.parseDouble(JOptionPane.showInputDialog(null,
                    "Enter the initial decay rate:"));
            
            while(decayRate < 0 || decayRate > 1){
            	decayRate = Double.parseDouble(JOptionPane.showInputDialog(null,
                        "Please enter a decay rate that is between 0 and 1: "));
            }
    
            s = "Simulated Annealing";
            SimulatedWalk(matrixInput, InputIteration, InitialTemperature, decayRate);
         
        }

        //	Task7
        if (taskNum == 7) {
         
        	 double counter = Integer.parseInt(JOptionPane.showInputDialog(null,
                     "Enter a constant time to compute the algorithm: "));
             while(counter <= 0){
            	 counter = Integer.parseInt(JOptionPane.showInputDialog(null,
                         "Please enter a positive number for the computed time: "));
             }
                     
            s = "Genetic Algorithm";
            GeneticAlgorithm(matrixInput, x, counter);
           
        }
        input = JOptionPane.showInputDialog(null, "Do you want to perfrom another another task? (Y = Yes/N = No)");
        }while(input.charAt(0) == 'Y');

    }


    /**** Initialize a new matrix ****/

    public static int[][] matrixInitialization(int[][] matrixInput){
        Random rand = new Random();
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++){
                if((r == n/2)&&(c == n/2)){
                    matrixInput[r][c] = rand.nextInt(r) + 1;

                    continue;
                }

                if((r == n-1)&&(c == n-1)){
                    matrixInput[r][c] = 0;

                    continue;
                }

                matrixInput[r][c] = rand.nextInt(Math.max(Math.max(n-r-1,r),Math.max(n-c-1,c))) + 1;
            }
        }
        return matrixInput;
    }
    /**
     * Initialize the contents of the frame.
     */
    public static void initialize(int[][] matrixInput) {
        int[][] matrixA = new int[n][n];
        matrixA = matrixInput;
        frmNnPuzzle = new JFrame();
        frmNnPuzzle.setBackground(Color.WHITE);
        frmNnPuzzle.getContentPane().setBackground(Color.WHITE);
        frmNnPuzzle.setTitle(n + "*" + n + " Puzzle");
        frmNnPuzzle.setResizable(false);
        int sl = 60*n;
        frmNnPuzzle.setBounds(100, 100, sl, sl);
        frmNnPuzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmNnPuzzle.getContentPane().setLayout(null);


        for(int r = 0; r < n; r++) {
   
            for(int c = 0; c < n; c++){

                if((r == n/2)&&(c == n/2)){
                    textPane = new JTextPane();
                    textPane.setForeground(Color.BLACK);
                    textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                    textPane.setText(Integer.toString(matrixA[r][c]));
                    textPane.setEditable(false);
                    textPane.setBounds((sl/n)*c, (sl/n)*r, sl/n, sl/n);
                    frmNnPuzzle.getContentPane().add(textPane);
                    continue;
                }

                if((r == n-1)&&(c == n-1)){
                    textPane = new JTextPane();
                    textPane.setForeground(Color.BLACK);
                    textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                    textPane.setText("0");
                    textPane.setEditable(false);
                    textPane.setBounds((sl/n)*c, (sl/n)*r, sl/n, sl/n);
                    frmNnPuzzle.getContentPane().add(textPane);
                    continue;
                }

                textPane = new JTextPane();
                textPane.setForeground(Color.BLACK);
                textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                textPane.setText(Integer.toString(matrixA[r][c]));
                textPane.setEditable(false);
                textPane.setBounds((sl/n)*c, (sl/n)*r, sl/n, sl/n);
                frmNnPuzzle.getContentPane().add(textPane);
            }
        }
    }

    public static int[][] puzzleEvaluation(int[][] matrixA, char z){



        int matrixB[][] = new int[n][n];
        // loop for rows
        for(int r = 0; r < n; r++) {
            // loop for columns
            for(int c = 0; c < n; c++) {
                matrixB[r][c] = 0;
            }
        }

        Node root=new Node();
        matrixB[0][0]=0;
        //       root.index = 0;
        root.r = 0;
        root.c = 0;
        int x ;
        int y ;
        Treequeue.add(root);

        while (Treequeue.isEmpty()== false)
        {
            current = Treequeue.remove();
            x = current.r;
            y = current.c;
            initializeQueue(matrixB);

            while (queue.isEmpty() == false){
                dequeue(x, y, current, matrixA, matrixB);
            }
        }

        frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setTitle(s);
        frame.setResizable(false);
        int sl = 60*n;
        frame.setBounds(100, 100, sl, sl);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        for(int r = 0; r < n; r++) {

            for(int c = 0; c < n; c++){

                if(matrixB[r][c] == 0){
                    if((r==0)&&(c==0)){
                        textPane = new JTextPane();
                        textPane.setForeground(Color.BLACK);
                        textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                        textPane.setText(Integer.toString(matrixB[r][c]));
                        textPane.setEditable(false);
                        textPane.setBounds((sl/n)*c, (sl/n)*r, sl/n, sl/n);
                        frame.getContentPane().add(textPane);
                        continue;
                    } else {
                        textPane = new JTextPane();
                        textPane.setForeground(Color.BLACK);
                        textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                        textPane.setText("X");
                        textPane.setEditable(false);
                        textPane.setBounds((sl / n) * c, (sl / n) * r, sl / n, sl / n);
                        frame.getContentPane().add(textPane);
                        continue;
                    }
                }

                textPane = new JTextPane();
                textPane.setForeground(Color.BLACK);
                textPane.setFont(new Font("Tahoma", Font.BOLD, 14));
                textPane.setText(Integer.toString(matrixB[r][c]));
                textPane.setEditable(false);
                textPane.setBounds((sl/n)*c, (sl/n)*r, sl/n, sl/n);
                frame.getContentPane().add(textPane);
            }
        }
        return matrixB;

    }

    public static void initializeQueue(int[][] matrixB)
    {
        // Initialize queue
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(i==0&&j==0){
                    continue;
                }
                if(matrixB[i][j]==0)
                {
                    Node temp = new Node();
                    temp.r = i;
                    temp.c = j;
                    queue.add(temp);
                }
            }
        }
    }

    public static void dequeue(int x, int y, Node current, int[][] matrixA, int[][] matrixB){
        int moveNum = matrixA[x][y];
        Node dequeuenode  = queue.remove();
        int r = dequeuenode.r;
        int c = dequeuenode.c;
        int parentstepcounter = matrixB[x][y];

        if (x + moveNum == r && y == c) {
            parentstepcounter = parentstepcounter+1;
            matrixB[r][c] = parentstepcounter;
            Treequeue.add(dequeuenode);

        }

        if (x - moveNum == r && y == c) {
            parentstepcounter = parentstepcounter+1;
            matrixB[r][c] = parentstepcounter;
            Treequeue.add(dequeuenode);
        }

        if (x == r && y + moveNum == c) {
            parentstepcounter = parentstepcounter+1;
            matrixB[r][c] = parentstepcounter;
            Treequeue.add(dequeuenode);
        }

        if (x == r && y - moveNum == c) {
            parentstepcounter = parentstepcounter+1;
            matrixB[r][c] = parentstepcounter;
            Treequeue.add(dequeuenode);
        }
    }

    /** Hill Climbing *********/

    public static void hillClimbing(int[][] matrixInput, int InputIteration)
    {
    	
        Random rand = new Random();
        int randomrow;
        int randomcol;
        int oldMoveNum;
        int lastBestResult;
        int randomnumber;
        double timeBegin, timeEnd,timeDiff;
        timeBegin = System.currentTimeMillis();
        int matrixB[][] = new int[n][n];
        int matrixA[][] = new int[n][n];
        matrixA = matrixInput;
        matrixB = puzzleEvaluation(matrixA, 'c');

        /***Loop for n iteration times ***/
        for(int i=0; i < InputIteration;i++) {
            lastBestResult = matrixB[n-1][n-1];
            do{
                randomrow = rand.nextInt(n);
                randomcol = rand.nextInt(n);
            }while(((randomrow == n-1) && (randomcol == n-1))||((randomrow == 0) && (randomcol == 0)));
            oldMoveNum = matrixA[randomrow][randomcol];    
            randomnumber = rand.nextInt(Math.max(Math.max(n - randomrow - 1, randomrow), Math.max(n - randomcol - 1, randomcol))) + 1;
            /** Getting a new legal movenum for the random node ***/
            matrixA[randomrow][randomcol] = randomnumber;
            matrixB = puzzleEvaluation(matrixA,'c');
            if(lastBestResult==matrixB[n-1][n-1]){
                continue;
            }
            else if(lastBestResult<matrixB[n-1][n-1]){
                continue;
            }
            else if((lastBestResult>matrixB[n-1][n-1]) || ((matrixB[n-1][n-1])==0)){

                matrixA[randomrow][randomcol] = oldMoveNum;
                matrixB = puzzleEvaluation(matrixA,'c');

            }
            
        }
        timeEnd =  System.currentTimeMillis();
        timeDiff =timeEnd - timeBegin;
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "The final value is: " + matrixB[n-1][n-1] + ", and the computed time is " + timeDiff + "ms.");
    }

    /** Hill Climbing with random restarts *********/

    public static void randomRestarts(int[][] matrixInput, int InputIteration, int numOfRestarts, int iterationsPerRestart) {
        Random rand = new Random();
        int[][] matrixA =  matrixInput;
        int[][] matrixAFixed =  matrixInput;
        double timeBegin, timeEnd,timeDiff;
        timeBegin = System.currentTimeMillis();
        // Get a new matrix
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                matrixA[i][j] = matrixAFixed[i][j];
            }
        }
        int matrixB[][] = new int[n][n];
        matrixB = puzzleEvaluation(matrixA,'c');
        int randomrow;
        int randomcol;
        int oldMoveNum;
        int lastBestResult;
        int randomnumber;
        int counter1 = 0;
        int counter2 = 0;
        int bestMatrix[][] = new int[n][n];
        
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                bestMatrix[r][c] = 0;
            }
        }
        /***Loop for n iteration times ***/
        for(int i=0; i < InputIteration;i++) {
            lastBestResult = matrixB[n-1][n-1];
            

            counter1++;
            if(counter1 == iterationsPerRestart)
            {
                if(counter2 == 0){
                    for(int r = 0; r < n; r++){
                        for(int c = 0; c < n; c++){
                            bestMatrix[r][c] = matrixA[r][c];
                        }
                    }
                }  
                // Get a new matrix
                initialize(matrixA);
                //	Puzzle evaluation
                matrixB = puzzleEvaluation(matrixA,'c');
                counter1 = 0;
                counter2++;
            }
   
            do{
                randomrow = rand.nextInt(n);
                randomcol = rand.nextInt(n);
            }while(((randomrow == n-1) && (randomcol == n-1))||((randomrow == 0) && (randomcol == 0)));
            
            
            oldMoveNum = matrixA[randomrow][randomcol];        
            randomnumber = rand.nextInt(Math.max(Math.max(n - randomrow - 1, randomrow), Math.max(n - randomcol - 1, randomcol))) + 1;
            /** Getting a new legal movenum for the random node ***/
            matrixA[randomrow][randomcol] = randomnumber;
            matrixB = puzzleEvaluation(matrixA,'c');
            if(lastBestResult==matrixB[n-1][n-1]){      
                for(int r = 0; r < n; r++){
                    for(int c = 0; c < n; c++){
                        bestMatrix[r][c] = matrixA[r][c];
                    }
                }
                continue;
            }
            else if(lastBestResult<matrixB[n-1][n-1]){
            
                for(int r = 0; r < n; r++){
                    for(int c = 0; c < n; c++){
                        bestMatrix[r][c] = matrixA[r][c];
                    }
                }
                continue;
            }
            else if((lastBestResult>matrixB[n-1][n-1]) || ((matrixB[n-1][n-1])==0)){
              
                matrixA[randomrow][randomcol] = oldMoveNum;
                if(counter2 < 1){
                    matrixB = puzzleEvaluation(matrixA,'c');;
                }else{
                    matrixB = puzzleEvaluation(bestMatrix,'c');
                }
            }

        }
        
        
        timeEnd =  System.currentTimeMillis();
        timeDiff =timeEnd - timeBegin;
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                System.out.print(bestMatrix[i][j] + " ");
            }
            System.out.println();
        }
   	 System.out.println();
   	 for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                System.out.print(matrixB[i][j] + " ");
            }
            System.out.println();
        }
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "The final value is: " + matrixB[n-1][n-1] + ", and the computed time is " + timeDiff + "ms.");
    }

    /****  Hill Climbing with random walk *****/

    public static void RandomWalk(int[][] matrixInput, int InputIteration, double fixedpossiblity)
    {
    	
        Random rand = new Random();
        int randomrow;
        int randomcol;
        float generatedpossiblity;
        int oldMoveNum;
        int lastBestResult;
        int randomnumber;
        int[][] matrixA =  matrixInput;
        int[][] matrixAFixed =  matrixInput;
        double timeBegin, timeEnd,timeDiff;
        timeBegin = System.currentTimeMillis();
        int bestMatrix[][] = new int[n][n];
        int GoodA[][] = new int[n][n];
        int bestResult = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                bestMatrix[r][c] = 0;
            }
        }
        
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                matrixA[i][j] = matrixAFixed[i][j];
            }
        }

        int matrixB[][] = new int[n][n];
        matrixB = puzzleEvaluation(matrixA,'c');

        /***Loop for n iteration times ***/
        for(int i=0; i < InputIteration;i++) {
        	
            generatedpossiblity = rand.nextFloat();
            lastBestResult = matrixB[n-1][n-1];

            do{
                randomrow = rand.nextInt(n);
                randomcol = rand.nextInt(n);
            }while(((randomrow == n-1) && (randomcol == n-1))||((randomrow == 0) && (randomcol == 0)));
            oldMoveNum = matrixA[randomrow][randomcol];

            randomnumber = rand.nextInt(Math.max(Math.max(n - randomrow - 1, randomrow), Math.max(n - randomcol - 1, randomcol))) + 1;
            /** Getting a new legal movenum for the random node ***/
            matrixA[randomrow][randomcol] = randomnumber;
            matrixB = puzzleEvaluation(matrixA,'c');

            if(lastBestResult==matrixB[n-1][n-1]){

               

            }
            else if(lastBestResult<matrixB[n-1][n-1]){

              
            }
            else if ((lastBestResult>matrixB[n-1][n-1]) || ((matrixB[n-1][n-1])==0)){
            	
                if(generatedpossiblity<fixedpossiblity){
                   
                } else{
                matrixA[randomrow][randomcol] = oldMoveNum;
                matrixB = puzzleEvaluation(matrixA,'c');
                }
            }
            
            lastBestResult = matrixB[n-1][n-1];
            /*********** Keep Best *****/
            if(lastBestResult>bestResult){
            for(int ii=0; ii<n;ii++){
                for(int j =0;j<n;j++){
                	bestMatrix[ii][j] = matrixA[ii][j];
                }
            }
            GoodA = puzzleEvaluation(bestMatrix,'c');
            bestResult = GoodA[n-1][n-1];
            }
            
            
            /*********** Keep Best *****/

        } 
        matrixB = puzzleEvaluation(bestMatrix,'c');
        timeEnd = System.currentTimeMillis();
    	timeDiff = timeEnd - timeBegin;
    	frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "The final value is: " + matrixB[n-1][n-1] + ", and the computed time is " + timeDiff + "ms.");
    }

    /****  Simulated Method *****/

    public static void SimulatedWalk(int[][] matrixInput, int InputIteration, double InitialTemperature, double decayRate)
    {
        Random rand = new Random();
        int randomrow;
        int randomcol;
        double generatedpossiblity;
        int oldMoveNum;
        int lastBestResult;
        int randomnumber;
        double currentworsenum;
        double SimulatedTemperature;
        double CurrentTemperature = InitialTemperature;
        int[][] matrixA =  matrixInput;
        int[][] matrixAFixed =  matrixInput;
        double timeBegin, timeEnd,timeDiff;
        timeBegin = System.currentTimeMillis();
        int bestMatrix[][] = new int[n][n];
        int GoodA[][] = new int[n][n];
        int bestResult = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                bestMatrix[r][c] = 0;
            }
        }
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                matrixA[i][j] = matrixAFixed[i][j];
            }
        }

        int matrixB[][] = new int[n][n];
        matrixB = puzzleEvaluation(matrixA,'c');
        /***Loop for n iteration times ***/
        for(int i=0; i < InputIteration;i++) {
            CurrentTemperature = CurrentTemperature * decayRate;
            generatedpossiblity = rand.nextFloat();

            lastBestResult = matrixB[n-1][n-1];

            do{
                randomrow = rand.nextInt(n);
                randomcol = rand.nextInt(n);
            }while(((randomrow == n-1) && (randomcol == n-1))||((randomrow == 0) && (randomcol == 0)));

            oldMoveNum = matrixA[randomrow][randomcol];

            randomnumber = rand.nextInt(Math.max(Math.max(n - randomrow - 1, randomrow), Math.max(n - randomcol - 1, randomcol))) + 1;
            /** Getting a new legal movenum for the random node ***/
            matrixA[randomrow][randomcol] = randomnumber;


            matrixB = puzzleEvaluation(matrixA,'c');

            if(lastBestResult==matrixB[n-1][n-1]){


            }
            else if(lastBestResult<matrixB[n-1][n-1]){

            }
            else if((lastBestResult>matrixB[n-1][n-1]) || ((matrixB[n-1][n-1])==0)){

                currentworsenum = (matrixB[n-1][n-1])-lastBestResult;

                SimulatedTemperature = Math.pow(Math.E,(currentworsenum/CurrentTemperature));


                if(generatedpossiblity<SimulatedTemperature){

                }else{

                matrixA[randomrow][randomcol] = oldMoveNum;

                matrixB = puzzleEvaluation(matrixA,'c');}
            }
            
            lastBestResult = matrixB[n-1][n-1];
            /*********** Keep Best *****/
            if(lastBestResult>bestResult){
            for(int ii=0; ii<n;ii++){
                for(int j =0;j<n;j++){
                	bestMatrix[ii][j] = matrixA[ii][j];
                }
            }
            GoodA = puzzleEvaluation(bestMatrix,'c');
            bestResult = GoodA[n-1][n-1];
            }
            
            /*********** Keep Best *****/
        }
        
        matrixB = puzzleEvaluation(bestMatrix,'c');
        timeEnd = System.currentTimeMillis();
    	timeDiff = timeEnd - timeBegin;
    	 for(int i=0; i<n;i++){
             for(int j =0;j<n;j++){
                 System.out.print(bestMatrix[i][j] + " ");
             }
             System.out.println();
         }
    	 System.out.println();
    	 for(int i=0; i<n;i++){
             for(int j =0;j<n;j++){
                 System.out.print(matrixB[i][j] + " ");
             }
             System.out.println();
         }


    	frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "The final value is: " + matrixB[n-1][n-1] + ", and the computed time is " + timeDiff + "ms.");
    }

    /*******************************************
     *
     * @param InputIteration
     * @param currr
     */
    public static void GeneticAlgorithm(int[][] matrixInput, char currr, double Time){
        Random rand =new Random();
        int sumelement = n*n;
        int bestone;
        int str1[] = new int[sumelement];
        int str2[] = new int[sumelement];
        int str3[] = new int[sumelement];
        int str4[] = new int[sumelement];
        int beststr[] = new int[sumelement];
        int[][] matrixA =  matrixInput;
        int[][] matrixAFixed =  matrixInput;
        int ccc=0;
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        int s4 = 0;
        int crosscopy;
        int dividesize;
        int tempstr[] = new int[sumelement];
        double guidecross;

        double timeBegin, timeEnd, timediff, counter;
        counter = Time;
        timeBegin = System.currentTimeMillis();
        timediff = -1;
        int bestMatrix[][] = new int[n][n];
        int GoodA[][] = new int[n][n];
        int bestResult = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                bestMatrix[r][c] = 0;
            }
        }
        
        int countnum = 0;
        for(int i=0; i<n;i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = matrixAFixed[i][j];
                str1[countnum] = matrixA[i][j];
                countnum++;
            }
        }
        
        countnum =0;
        matrixInitialization(matrixA);
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                str2[countnum] = matrixA[i][j];
                countnum++;
            }
        }
        
        countnum =0;
        matrixInitialization(matrixA);
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                str3[countnum] = matrixA[i][j];
                countnum++;
            }
        }

        countnum =0;
        matrixInitialization(matrixA);
        for(int i=0; i<n;i++){
            for(int j =0;j<n;j++){
                str4[countnum] = matrixA[i][j];
                countnum++;
            }
        }

        
        int i = 0;
        while(0 == 0){
        	if((timediff >counter)||(timediff == counter)){
        		break;
        	}
        	i++;
            crosscopy = 0;
            dividesize = 0;
            s1 = FitnessFunction(str1);
            s2 = FitnessFunction(str2);
            s3 = FitnessFunction(str3);
            s4 = FitnessFunction(str4);
            guidecross = PossibilitySelect(s1, s2, s3, s4);

            /*********** CrossCopy ************/
            if(guidecross>4.0){

                if(guidecross==4.3){

                    while(crosscopy<countnum){
                        str4[crosscopy] = str3[crosscopy];
                        crosscopy++;
                    }
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str2[read];
                        str2[read] = tempstr[read];
                    }
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];
                        str4[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/

                } else if(guidecross==4.2){


                    while(crosscopy<countnum){
                        str4[crosscopy] = str2[crosscopy];
                        crosscopy++;
                    }
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];
                        str4[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/

                } else {

                    while(crosscopy<countnum){
                        str4[crosscopy] = str1[crosscopy];
                        crosscopy++;
                    }
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str2[read];
                        str2[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];
                        str4[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                }
            } else if(guidecross>3.0){

                if(guidecross==3.4){


                    while(crosscopy<countnum){
                        str3[crosscopy] = str4[crosscopy];
                        crosscopy++;
                    }
              
                    
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];

                        str4[read] = str2[read];
                        str2[read] = tempstr[read];
                    }

                    
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else if(guidecross==3.2){

                    while(crosscopy<countnum){
                        str3[crosscopy] = str2[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else {

                    while(crosscopy<countnum){
                        str3[crosscopy] = str1[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str2[read];
                        str2[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                }
            } else if(guidecross>2.0){

                if(guidecross==2.4){

                    while(crosscopy<countnum){
                        str2[crosscopy] = str4[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];
                        str4[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else if(guidecross==2.3){


                    while(crosscopy<countnum){
                        str2[crosscopy] = str3[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str1[read];
                        str1[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else {

                    while(crosscopy<countnum){
                        str2[crosscopy] = str1[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                }
            } else {

                if(guidecross==1.4){

                    while(crosscopy<countnum){
                        str1[crosscopy] = str4[crosscopy];
                        crosscopy++;
                    }
                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str4[read];
                        str4[read] = str2[read];
                        str2[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else if(guidecross==1.3){


                    while(crosscopy<countnum){
                        str1[crosscopy] = str3[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str3[read];
                        str3[read] = str2[read];
                        str2[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.max(Math.max(s1,s2),Math.max(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);

                    }

                    /**** Completed for this case ***/
                } else {
                    while(crosscopy<countnum){
                        str1[crosscopy] = str2[crosscopy];
                        crosscopy++;
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str2[read];
                        str2[read] = str3[read];
                        str3[read] = tempstr[read];
                    }

                    dividesize = rand.nextInt(sumelement-1)+1;
                    for(int read = 0; read<dividesize; read++){
                        tempstr[read] = str1[read];
                        str1[read] = str4[read];
                        str4[read] = tempstr[read];
                    }

                    s1 = FitnessFunction(str1);
                    s2 = FitnessFunction(str2);
                    s3 = FitnessFunction(str3);
                    s4 = FitnessFunction(str4);

                    if(s1==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){

                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                        s2 = FitnessFunction(str2);
                        s3 = FitnessFunction(str3);
                        s4 = FitnessFunction(str4);

                    } else if (s2==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);

                        str3 = Mutation(str3);
                        str4 = Mutation(str4);
                    } else if (s3==(Math.min(Math.min(s1,s2),Math.min(s3,s4)))){
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);

                        str4 = Mutation(str4);
                    } else {
                        str1 = Mutation(str1);
                        str2 = Mutation(str2);
                        str3 = Mutation(str3);
                    }
                    /**** Completed for this case ***/
                }
            }
            /***** CopyCompleted ****/

            s1 = FitnessFunction(str1);
            s2 = FitnessFunction(str2);
            s3 = FitnessFunction(str3);
            s4 = FitnessFunction(str4);
            bestone = (Math.max(Math.max(s1,s2),Math.max(s3,s4)));
            
            /*********** Keep Best *****/
            if(bestone==s1){
            	for(ccc = 0;ccc<sumelement;ccc++){
            		beststr[ccc]=str1[ccc];
            	}
            	
            } else if(bestone==s2){
            	for(ccc = 0;ccc<sumelement;ccc++){
            		beststr[ccc]=str2[ccc];
            	}
            }else if(bestone==s3){
            	for(ccc = 0;ccc<sumelement;ccc++){
            		beststr[ccc]=str3[ccc];
            	}
            }else{
            	for(ccc = 0;ccc<sumelement;ccc++){
            		beststr[ccc]=str4[ccc];
            	}
            }
            
            if(bestone>bestResult){
            	ccc = 0;
            	 for(int r = 0; r < n; r++)
                 {
                     for(int c = 0; c < n; c++)
                     {
                         matrixA[r][c] = beststr[ccc];

                         ccc++;
                     }
                 }
            	
            for(int ii=0; ii<n;ii++){
                for(int j =0;j<n;j++){
                	bestMatrix[ii][j] = matrixA[ii][j];
                }
            }
            GoodA = puzzleEvaluation(bestMatrix,'c');
            bestResult = GoodA[n-1][n-1];
            }
            
            timeEnd =  System.currentTimeMillis();
            timediff =timeEnd - timeBegin ;
            /*********** Keep Best *****/
        }
        /***** Output Best One ****/
        GoodA = puzzleEvaluation(bestMatrix,'c');
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "The final value is: " + GoodA[n-1][n-1] + ", and required number of iterations is " + i);
 }


   

    public static double PossibilitySelect(int ss1, int ss2, int ss3, int ss4){
        Random rand = new Random();
        double s1 = ss1;
        double s2 = ss2;
        double s3 = ss3;
        double s4 = ss4;
        double sumstep = (s1+s2+s3+s4);
        double p1 = s1/sumstep;
        double p2 = s2/sumstep;
        double p3 = s3/sumstep;
        double p4 = s4/sumstep;
        double minmin = Math.min(Math.min(p1,p2),Math.min(p3,p4));
        double cross = 1.0;
        if(minmin == p1){
            sumstep = s2+s3+s4;
            p2 = s2/sumstep;
            p3 = (s3/sumstep)+p2;
            p4 = (s4/sumstep)+p3;
            double random = rand.nextDouble();
            if(random<p2){
                cross = 1.2;
                return cross;

            } else if((random>p3)&&(random<p4)){
                cross = 1.3;
                return cross;

            } else {
                cross = 1.4;
                return cross;

            }
        } else if(minmin == p2){

            sumstep = s1+s3+s4;
            p1 = s1/sumstep;
            p3 = (s3/sumstep)+p1;
            p4 = (s4/sumstep)+p3;
            double random = rand.nextDouble();
            if(random<p1){
                cross = 2.1;
                return cross;

            } else if((random>p3)&&(random<p4)){
                cross = 2.3;
                return cross;

            } else {
                cross = 2.4;
                return cross;

            }

        } else if(minmin == p3){
            sumstep = s1+s2+s4;
            p1 = s1/sumstep;
            p2 = (s2/sumstep)+p1;
            p4 = (s4/sumstep)+p2;
            double random = rand.nextDouble();
            if(random<p1){
                cross = 3.1;
                return cross;

            } else if((random>p2)&&(random<p4)){
                cross = 3.2;
                return cross;

            } else {
                cross = 3.4;
                return cross;

            }
        } else {
            sumstep = s1+s2+s3;
            p1 = s1/sumstep;
            p2 = (s2/sumstep)+p1;
            p3 = (s3/sumstep)+p2;
            double random = rand.nextDouble();
            if(random<p1){

                cross = 4.1;
                return cross;

            } else if((random>p2)&&(random<p3)){

                cross = 4.2;
                return cross;

            } else {
                cross = 4.3;
                return cross;

            }
        }
    }

    /**** Mutation Stage of GA ****/

    public static int[] Mutation(int[] array)
    {
        Random rand = new Random();
        int[][] matrixAfter = new int[n][n];
        int count = 0;
        int rr;
        int rc;

        do
        {
            rr = rand.nextInt(n);
            rc = rand.nextInt(n);
        }	while(rr == n-1 && rc == n-1);
        
        for(int r = 0; r < n; r++)
        {
            for(int c = 0; c < n; c++)
            {
                matrixAfter[r][c] = array[count];

                count++;
            }
        }

        if((rr == n/2)&&(rc == n/2)){
            matrixAfter[rr][rc] = rand.nextInt(rr) + 1; 
            int countnum = 0;
            for(int r = 0; r < n; r++){
                for(int c = 0; c < n; c++){
                
                    array[countnum] = matrixAfter[r][c];	

                    countnum++;
                }
            }
            return array;
        }

        matrixAfter[rr][rc] = rand.nextInt(Math.max(Math.max(n-rr-1,rr),Math.max(n-rc-1,rc))) + 1;
        int countnum = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
            	
                array[countnum] = matrixAfter[r][c];

                countnum++;
            }
        }
        return array;
    }

    public static int FitnessFunction(int[] array)
    {
        int[][] matrixAfter = new int[n][n];
        int[][] matrixB = new int[n][n];
        int count = 0;

        for(int r = 0; r < n; r++)
        {
            for(int c = 0; c < n; c++)
            {
                matrixAfter[r][c] = array[count];
                count++;
            }
        }
        matrixB = puzzleEvaluation(matrixAfter,'c');

        return matrixB[n-1][n-1];
    }
}

