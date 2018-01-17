package codeChallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
	static ArrayList <PastureCover> coverList = new ArrayList<PastureCover>();
	static ArrayList <Integer> January = new ArrayList<Integer>();
	static ArrayList <Integer> Febuary = new ArrayList<Integer>();
	static ArrayList <Integer> March = new ArrayList<Integer>();
	static ArrayList <Integer> April = new ArrayList<Integer>();
	static ArrayList <Integer> May = new ArrayList<Integer>();
	static ArrayList <Integer> June = new ArrayList<Integer>();
	static ArrayList <Integer> July = new ArrayList<Integer>();
	static ArrayList <Integer> August = new ArrayList<Integer>();
	static ArrayList <Integer> September = new ArrayList<Integer>();
	static ArrayList <Integer> October = new ArrayList<Integer>();
	static ArrayList <Integer> November = new ArrayList<Integer>();
	static ArrayList <Integer> December = new ArrayList<Integer>();
	
	
	public static void main(String[] args){
		readCSV("pasturecoverdatasorted.csv");
		System.out.println("Enter the paddockId of the pasture cover you want to see the grass growth of: ");
		Scanner in = new Scanner(System.in); // Get the users input
		try {
			int num = in.nextInt();
			in.close();
			findGrowthRate(num, coverList);
			getAverageOfMonths();
			//System.out.println("Arraylist size: "+ coverList.size()); // Check to make sure all data is in
		}
		catch(InputMismatchException e) {
			System.out.println("This is not an integer, Rerun the program");
		}


	}
	
	/*Get the average of the ArrayLists and prints them out*/
	private static void getAverageOfMonths() {
		System.out.println("Average growth rate on January: "+getAverage(January));
		System.out.println("Average growth rate on Febuary: "+getAverage(Febuary));
		System.out.println("Average growth rate on March: "+getAverage(March));
		System.out.println("Average growth rate on April: "+getAverage(April));
		System.out.println("Average growth rate on May: "+getAverage(May));
		System.out.println("Average growth rate on June: "+getAverage(June));
		System.out.println("Average growth rate on July: "+getAverage(July));
		System.out.println("Average growth rate on August: "+getAverage(August));
		System.out.println("Average growth rate on September: "+getAverage(September));
		System.out.println("Average growth rate on October: "+getAverage(October));
		System.out.println("Average growth rate on November: "+getAverage(November));
		System.out.println("Average growth rate on December: "+getAverage(December));
	}
	
	/* Calculates the average of month entered */
	private static int getAverage(ArrayList<Integer> month) {
		int sum = 0;
		for(Integer i : month) {
			//System.out.println("Integer in month"+i);
			sum+=i;
		}
		//sum /= month.size(); //sum = sum/month.size()
		//System.out.println("Month size "+month.size());
		return sum;
	}

	/*Finds the growth rate of the paddock covers. Based on the paddock ID provided */
	private static void findGrowthRate(int paddockId, ArrayList<PastureCover> list) {
		PastureCover first = null;
		PastureCover second = null;
		String month = "";
		
		if (canLoopList(list)) {
			//Get the first value.
			for(int i = 0; i < list.size(); i++) {
				
				if (paddockId==list.get(i).paddockId) {
					first = list.get(i);
					break;
				}

			}
			
		}
		
		//Get the rest of the values in the whole csv
		for(int i = 0; i < list.size(); i++) {
			
			//Checks the pasturecovers past i with similar Ids to i and if they are 40 days apart.
			for(int j = i+1; j < list.size(); j++) {
				
				
				if (first == null) {
					System.out.println("PaddockId does not match anything");
					System.exit(-1); //Stops the entire program. Should be changed for a better error check
				}
				//Successfully found a match
				else if(first.paddockId == list.get(j).paddockId && first.noMore40daysApart(list.get(j).date)) {
					second = list.get(j);					
					//System.out.println("First "+first.value+first.date+" "+"Second "+second.value+second.date);
					int growthRatePerDay = (second.value - first.value) / Math.abs(first.daysBetween(second.date));
					//System.out.println("Growth per day " +growthRatePerDay);
					if (growthRatePerDay >= 0 && growthRatePerDay < 200) {
						
						//Double the growthRatePerDay if paddockSize is twice the initial size.
						if (second.paddockSize >= first.paddockSize*2) { growthRatePerDay = growthRatePerDay*2;}
						
						//Get the neccessary details to record them on the Months array lists
						month = first.determineMonth(second.date); // Determine the month to put the result
						//System.out.println("The month is " + month);
						
						//switch statement to put the growthRatePerDay to its proper month
						switch(month) {
							case "January": January.add(growthRatePerDay);
									break;
							case "Febuary": Febuary.add(growthRatePerDay);
									break;
							case "March": March.add(growthRatePerDay);
									break;
							case "April": April.add(growthRatePerDay);
									break;
							case "May": May.add(growthRatePerDay);
									break;
							case "June": June.add(growthRatePerDay);
									break;
							case "July": July.add(growthRatePerDay);
									break;
							case "August": August.add(growthRatePerDay);
									break;
							case "September": September.add(growthRatePerDay);
									break;
							case "October": October.add(growthRatePerDay);
									break;
							case "November": November.add(growthRatePerDay);
									break;
							case "December": December.add(growthRatePerDay);
									break;
						}

						first=second; //Make first be second's value. Then the loop will once again search for a match.
						second=null;
						i = j;
						break;
					}
					else {
						
						//Disregard them if not
						//System.out.println("This growth rate will be disregarded");
						first=second;
						second=null;
						i = j;
						break;
					}


				}
				//Id matches but the days are >40
				else if(first.paddockId == list.get(j).paddockId && !first.noMore40daysApart(list.get(j).date)) { 
					//System.out.println("A match is found but the gap is too big. Making second instance to first");	
					first= list.get(j); //Make first be second's value. Then the loop will once again search for a match.
					i=j;
					break;
				}
			
			}
		}
		
		
		
	}
	/*Reads the CSV file inputted and puts them into an array list*/
	private static void readCSV(String filename) {
		BufferedReader csvReader = null;
		
		try {
			String csvLine="";
			csvReader = new BufferedReader(new FileReader(filename));
			
			//System.out.println("Start here:"); // To make sure that all values are being scanned from the top to bottom
			csvReader.readLine(); //Skip the headers
			while ((csvLine = csvReader.readLine()) != null) {
				
				//System.out.println("Raw CSV data: " + csvLine);
				PastureCover pc = convertToPastureCover(csvLine);
				coverList.add(pc);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/*Converts the csv Line to a Pasture Cover object*/
	private static PastureCover convertToPastureCover(String csvLine) {
		String[] splitData = csvLine.split("\\s*,\\s*");
		Date date=null;
		SimpleDateFormat formatter =  new SimpleDateFormat("dd/MM/yyyy");
		try {
			 date = formatter.parse(splitData[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int paddockId = Integer.parseInt(splitData[1]);
		int value = Integer.parseInt(splitData[2]);
		double paddockSize = Double.parseDouble(splitData[3]);
		
		//System.out.println(formatter.format(date)+" "+paddockId+" "+value+" "+paddockSize); // .format(string) will convert it to string
		PastureCover pc = new PastureCover(date, paddockId, value, paddockSize);
		return pc;
	}
	
	/*Error checking methods*/
	public static boolean canLoopList(ArrayList<?> list) {
	    if (list != null && !list.isEmpty()) {
	        return true;
	    }
	    return false;
	}
}
