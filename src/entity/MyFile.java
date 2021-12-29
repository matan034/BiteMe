package entity;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

public class MyFile implements Serializable {
	
	private String Description=null;
	private String fileName=null,quarter,year;	
	private int BranchID=0;
	private int size=0;
	public  byte[] mybytearray;
	
	private File file;


	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getBranchID() {
		return BranchID;
	}

	public void setBranchID(int branchID) {
		BranchID = branchID;
	}

	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	


	public MyFile( String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}	
}