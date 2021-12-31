package order;




import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ProgController extends Application{  

    @FXML
    private ProgressIndicator prog_indicate;

    @Override  
    public void start(Stage primaryStage) throws Exception {  
        // TODO Auto-generated method stub  
      
    	prog_indicate=new ProgressIndicator();
        VBox root = new VBox();  
        root.getChildren().add(prog_indicate);  
        Scene scene = new Scene(root,300,200);  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("Progress Indicator Example");  
        primaryStage.show();  
          
    }  
    public static void main(String[] args) {  
        launch(args);  
    }  
  
    	
		
}
