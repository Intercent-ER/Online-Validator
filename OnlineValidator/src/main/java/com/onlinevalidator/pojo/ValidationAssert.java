package com.onlinevalidator.pojo;

/**
 * @author Manuel Gozzi
 */
public class ValidationAssert {

	private String test;
	private String location;
	private String testo;
	private boolean warning;
	private boolean fatal;

	public ValidationAssert() {
	}

	public ValidationAssert(String test, String location, String testo, String flag) {
		super();
		this.test = test;
		this.location = location;
		this.testo = testo;
		if (flag != null) {
			if (flag.equalsIgnoreCase("fatal")) {
				fatal = true;
			} else if (flag.equalsIgnoreCase("warning")) {
				warning = true;
			}
		}
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public boolean isFatal() {
		return fatal;
	}

	@Override
	public String toString() {
		return "ValidazioneSchematronResult [test=" + test + ", location=" + location + ", testo=" + testo + ", warning=" + warning + ", fatal=" + fatal + "]";
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

}
