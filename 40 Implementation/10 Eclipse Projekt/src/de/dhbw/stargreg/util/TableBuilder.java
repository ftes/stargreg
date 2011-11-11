package de.dhbw.stargreg.util;

import java.util.Vector;

public class TableBuilder {
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private Vector<String> row;
	
	public TableBuilder() {
		row = new Vector<String>();
		data.add(row);
	}
	
	public TableBuilder(Object... elements) {
		this();
		addNewRow(elements);
	}
	
	public TableBuilder(String[][] data) {
		this();
		for (int i=0; i<data.length; i++) {
			for (int j=0; j<data[i].length; j++) {
				row.add(data[i][j]);
			}
			row = new Vector<String>();
			this.data.add(row);
		}
	}
	
	public TableBuilder(Vector<String[]> data) {
		for (String[] row : data) {
			for (int i=0; i<row.length; i++) {
				this.row.add(row[i]);
			}
			this.row = new Vector<String>();
			this.data.add(this.row);
		}
	}
	
	public void add(Object... elements) {
		for (Object element : elements) {
			if (element == null) newRow();
			else row.add(element.toString());
		}
	}
	
	public void addNewRow(Object... elements) {
		add(elements);
		newRow();
	}
	
	public void newRow() {
		row = new Vector<String>();
		data.add(row);
	}
	
	public void hline() {
		data.add(data.size() - 1, null);
	}
	
	public void print(boolean kopfZeile) {
		if (kopfZeile && data.size() == 2 || ! kopfZeile && data.size() == 1) {
			System.out.println();
			return;
		}
		
		int[] max = new int[data.get(0).size()];
		for (int i=0; i<max.length; i++) max[i] = Integer.MIN_VALUE;
		for (Vector<String> row : data) {
			if (row == null) continue;
			for (int i=0; i<row.size(); i++) {
				if (row.get(i).length() > max[i]) {
					max[i] = row.get(i).length();
				}
			}
		}
		
		String format = "|";
		int width = 4 + (max.length - 1) * 3;
		for (int i=0; i<max.length; i++){
			format += " %" + max[i] + "s |";
			width += max[i];
		}
		format += "\n";
		
		System.out.println(repeat("-", width));
		for (Vector<String> row : data) {
			if (row == null) {
				System.out.println(repeat("-", width));
				continue;
			}
			if (! row.isEmpty()) System.out.printf(format, row.toArray());
			if (data.indexOf(row) == 0 && kopfZeile) System.out.println(repeat("-", width));
		}
		if (data.get(data.size() - 2) != null) System.out.println(repeat("-", width));
		System.out.println();
	}
	
	public void print() {
		print(true);
	}
	
	private static String repeat(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
}
