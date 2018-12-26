package ui.view;

import data.Student;

public class ViewCnfg {

	public Student stu;
	public boolean admin;

	public boolean editnow;

	public ViewCnfg() {
		stu = null;
		admin = false;
		editnow = false;
	}

	public ViewCnfg(ViewCnfg another) {
		stu = another.stu;
		admin = another.admin;
		editnow = another.editnow;
	}

	@Override
	public boolean equals(Object another) {
		if (another.getClass() != ViewCnfg.class)
			throw new RuntimeException("Tried to compare view cnfg with something else");
		ViewCnfg an = (ViewCnfg) another;
		return (stu == an.stu && admin == an.admin && editnow == an.editnow);
	}

}
