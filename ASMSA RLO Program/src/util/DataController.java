package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.AppPrefs;
import data.Student;
import main.Application;

public class DataController {

	private static final IllegalArgumentException loadSaveFileException = new IllegalArgumentException(
			"Cannot send file to this method");

	public static void saveVolList() {
		File dir = Application.appPrefs.getCurrentSaveDir();
		if (!dir.isDirectory())
			throw loadSaveFileException;
		for (Student stu : Student.ALLSTUDENTS) {
			saveStudent(dir, stu);
		}
	}

	public static void saveStudent(Student stu) {
		saveStudent(Application.appPrefs.getCurrentSaveDir(), stu);
	}

	private static void saveStudent(File directory, Student stu) {
		File file = new File(directory.getAbsolutePath() + "/" + stu.getID());

		try {
			FileOutputStream fis = new FileOutputStream(file);
			ObjectOutputStream ois = new ObjectOutputStream(fis);

			ois.writeObject(stu);

			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Recommended that you clear Student.volunteers before using this method
	 * 
	 * @param dir
	 */
	public static void loadVolList() {
		File dir = Application.appPrefs.getCurrentSaveDir();
		if (dir.isFile())
			throw loadSaveFileException;
		File[] files = dir.listFiles();

		int vols = 0;
		for (File file : files) {
			if (!file.getName().endsWith(Application.STUEXTENSION)) {
				continue;
			}

			loadStudent(file);
			vols++;
		}
		Application.logFileStream
				.println("Successfully loaded " + vols + " volunteers from txt files at " + dir.getAbsolutePath());

	}

	private static Student loadStudent(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			Student stu = (Student) ois.readObject();

			ois.close();
			fis.close();

			Student.ALLSTUDENTS.add(stu);
			return stu;
		} catch (Exception e) {
			return null;
		}
	}

	public static AppPrefs loadAppPrefs() {
		File prefFile = Application.preferenceFile;

		if (!prefFile.exists())
			return null;

		AppPrefs pref = null;

		try {
			FileInputStream fis = new FileInputStream(prefFile);
			ObjectInputStream ois = new ObjectInputStream(fis);

			pref = (AppPrefs) ois.readObject();

			ois.close();
			fis.close();
		} catch (Exception e) {
			return null;
		}

		return pref;
	}

	public static void saveAppPrefs() {
		File prefFile = Application.preferenceFile;

		try {
			FileOutputStream fis = new FileOutputStream(prefFile);
			ObjectOutputStream ois = new ObjectOutputStream(fis);

			ois.writeObject(Application.appPrefs);

			ois.close();
			fis.close();
			saveVolList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteStudent(Student stu) {
		deleteStudent(Application.appPrefs.getCurrentSaveDir(), stu);
	}

	private static void deleteStudent(File dir, Student stu) {
		File file = new File(dir.getAbsolutePath() + "/" + stu.getID() + ".txt");
		if (file.exists()) {
			file.delete();
		}
	}

}
