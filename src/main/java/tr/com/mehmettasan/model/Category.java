package tr.com.mehmettasan.model;

import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Category {
	public Category(String title) {
		this.title = title;
	}
 
	SortedSet<LinkedList<Set<Product>>> categoryTree = new TreeSet<LinkedList<Set<Product>>>();
	
	public String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/*
	public void addRootCategory(String parent) {
		LinkedList<Set<Product>> categoryList = new LinkedList<Set<Product>>()
		categoryTree.add("sss");
	}
	*/
}
