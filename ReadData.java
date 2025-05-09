import java.util.Scanner;
import java.io.File;


public class ReadData{
    //I hard-coded the number of rows and columns so 
    //I could use a 2D array
    private double[][] data = new double[21907][13];

    //This should read in the csv file and store the data in a 2D array,
    //data -- don't forget to skip the header line and parse everything
    //as doubles  
    public void read(){
        try{
            Scanner scanner = new Scanner(new File("cps.csv"));
            int row = 0;
            scanner.nextLine(); // Skip the header line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineArr = line.split(",");
                for(int col = 0; col < lineArr.length; col++){ 
                    data[row][col] = Double.parseDouble(lineArr[col]); 
                }
                row++;
            }
            scanner.close();
    
        } catch(Exception e){
            e.printStackTrace();
        }
    }

   
   
    
    //this returns the correlation between the two columns of data passed in
    //the correlation is the sum of the products of the standard units
    //of the two columns divided by the number of values - 1
    //this should return a double
    //the correlation is a measure of the strength of the linear relationship
    //between the two columns of data
    //the correlation is between -1 and 1
    public double correlation(double[] x, double[] y){ 
        double sum = 0; 
        double[] xStd = standardUnits(x); 
        double[] yStd = standardUnits(y); 
        for(int b = 0; b < x.length; b++){ 
            sum += xStd[b] * yStd[b]; 
        } 
        return sum/(x.length -1); 
    }
    
    public void runRegression(){
        double[] x = getColumn(7);
        double[] y = getColumn(9);
        double[] xStd = standardUnits(x);
        double[] yStd = standardUnits(y);
        double correlation = correlation(xStd, yStd);
        double slope = correlation * stdDeviation(y) / stdDeviation(x);
        double intercept = mean(y) - slope * mean(x);
        System.out.println("Correlation: " + correlation);
        System.out.println("Slope: " + slope);
        System.out.println("Intercept: " + intercept);
        Scatter s = new Scatter();
        s.displayScatterPlot(x, y);
    }

    //this prints the array passed in - you may want this for debugging
    public void print(double[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        ReadData rd = new ReadData();
        rd.read();
        rd.runRegression();
    }

    public double[] getColumn(int col){ 
        double[] column = new double[data.length]; 
        for(int row = 0; row < data.length; row++){ 
            column[row] = data[row][col];
        } 
        return column; 
    }

    public double mean(double[] arr){ 
        double sum = 0; 
        for(double val : arr){ 
            sum += val; 
        } 
        return sum/arr.length; 
    }

    public double stdDeviation(double[] arr){ 
        double sum = 0; 
        double mean = mean(arr); 
        for(double val : arr){ 
            sum += Math.pow(val - mean, 2); 
        } 
        double variance = sum/(arr.length -1); 
        return Math.sqrt(variance); //sample variance! 
    }

    public double[] standardUnits(double[] arr){ 
        double[] stdArr = new double[arr.length]; 
        double stdDeviation = stdDeviation(arr); 
        double mean = mean(arr); 
        for(int x = 0; x < arr.length; x++){ 
            stdArr[x] = (arr[x] - mean)/stdDeviation; 
        } 
        return stdArr; 
    }

}
