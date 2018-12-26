package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import main.Program;
import data.Gender;
import data.Student;
import data.StudentList;

public class DataController {
	
	/**
	 * Method will save the students to the their respective files
	 * with serialization
	 * @param file
	 */
	public static void saveStudentList(StudentList list){
		
		for(int i=0; i <list.getSize();i ++){
			saveStudent(list.get(i));
		}
		System.out.println("Success! StudentList saved to "+ Program.mainDirectory);
		
	}
	public static void saveStudent(Student stu){
		File file = stu.path;
		// SAVE STUFF AND CLOSE PROGRAM
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(stu);

			System.out.println("Success! Student saved to "
					+ file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method will read students in file directory and
	 * return the studentlist for these
	 * @param file
	 */
	public static StudentList loadStudentList(File directory){
		StudentList result = new StudentList();
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		
		File[] studentFiles = directory.listFiles();
		
		try {
			for (int i =0; i <studentFiles.length; i++) {
				
				fis = new FileInputStream(studentFiles[i]);
				ois = new ObjectInputStream(fis);
				Student stu = (Student) ois.readObject();
				System.out.println("Success! Student read from " + studentFiles[i]);
				
				result.add(stu);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Success! StudentList read from "+ Program.mainDirectory);
		//System.out.println(result);
		
		
		result = organizeList(result);
		
		return result;
	}
	/**
	 * Reorganizes the pointers in the students' matches
	 * to point to a student in the list or a celebrity in the student class
	 * rather than a random serialized instance of a object "copy"
	 * @return
	 */
	private static StudentList organizeList(StudentList input){
		
		for(int i=0 ; i < input.getSize(); i ++){
			Student match = input.get(i).getMatch();
			if(match==null) continue;
			
			for(int j=0 ; j < input.getSize(); j ++){
				if(match.equals(input.get(j))){
					input.get(i).setMatch(input.get(j));
					
				}
			}
			for(Student c: Student.MALECELEBRITIES){
				if(match.equals(c)){
					input.get(i).setMatch(c);
				}
			}
			for(Student c: Student.FEMALECELEBRITIES){
				if(match.equals(c)){
					input.get(i).setMatch(c);
				}
			}
			
			
		}
		
		System.gc();
		
		return input;
	}
	/**
	 * Method will prompt user for a txt file location and
	 * will load the studentlist from that file
	 *      FILE SHOULD BE IN THE FORMAT OF:
        
        
        ID,Name,Grade,Gender,Advisor
        ex. 73893292,Akarsh Kumar,10,M,Kunzelman
        
	 */
	public static StudentList loadStudentList(){
        
        System.out.println("Enter .txt file path to load the students");
        Scanner scan = new Scanner(System.in);
        String path = null;
        File file = null;
        boolean wrong = false;
        do{
        	if(wrong)System.out.println("File does not exist. - Try again");
	        path = scan.nextLine();
	        
	        file = new File(path);
	        wrong = true;
        }while(file.isDirectory() || !file.exists());
        Scanner filescan = null;
        try{
            filescan = new Scanner(file);
        }catch(FileNotFoundException e){
        	e.printStackTrace();
        }
        StudentList result = new StudentList();
        while(filescan.hasNext()){
            String a = filescan.nextLine();
            
            String[] stuff = a.split(",");
            
            for(String s: stuff){
                System.out.print(s+" ");
            }
            result.add(new Student(
                stuff[0], stuff[1], Integer.parseInt(stuff[3]),
                    stuff[2].equals("M")?Gender.MALE:Gender.FEMALE,
                    stuff[4]));
        
            System.out.println();
            
            
        }
        //System.out.println(result);
        Program.logFile.delete();
        saveStudentList(result);
        
        scan.close();
        filescan.close();
        
        
		return result;
        
	}
	
}
