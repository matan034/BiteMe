	import java.util.ArrayList;
	import java.util.List;
	import javafx.collections.FXCollections;
	import javafx.collections.ListChangeListener;
	import javafx.collections.ObservableList;
public class Test {

	    public static void main(String[] args) {
	        List<String> list = new ArrayList<String>();
	        ObservableList<String> observableList = FXCollections.observableList(list);
	        observableList.addListener(new ListChangeListener() {
	            @Override//    ww    w.    d  em   o  2   s .   co    m
	            public void onChanged(ListChangeListener.Change change) {
	                System.out.println("change!");
	            }

	        });
	        observableList.add("item one");
	       list.add("item two");
	        System.out.println("Size: " + observableList.size());
	        System.out.println("Size: " + list.size());

	    }
	}

