package SudokuGame;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 * view part of the Sudoku game
 */
public class View extends Application {
	
	static Image startImage = new Image("/image/startscreen.png"),
			startBImage = new Image("/image/playB.png"),
			helpBImage = new Image("/image/helpB.png"),
			instructionImage = new Image("/image/instructionscreen.png"),
			boardBackImage = new Image("/image/bordBack.png"),
			numBackImage = new Image("/image/numBack.png"),
			numBackSelImage = new Image("/image/numBackSel.png"),
			gearImage = new Image("/image/gear.png");
	
	ImageView numView[][][][],
	//[x coordinate for box][y coordinate for box][x coordinate for temp num][y coordinate for temp num] 
	//the last two count start at 1. 0 for the big view of that box
		numBackView[][], gearView;
	Group numViewGroup[][];
	Text timeText;
	
	SudokuModel model;
	Controller controller;
	ButtonPanel buttonPanel;
	Stage stage;
	
	long timeCounter;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initUI(primaryStage);
	}
	

	/**
	 * Constructs the Instruction Scene with image of
	 *  Sudoku rules and start button.
	 * @return Scene
	 */
	public Scene initGameStartScene(Stage stage) {

		Group view = new Group();

		ImageView gameStartView = new ImageView(startImage);
		gameStartView.setFitHeight(640);
		gameStartView.setFitWidth(810);
		gameStartView.relocate(0, 0);
		Button buttonGameStart = new Button(null, new ImageView(startBImage));
		Button buttonHelp = new Button(null, new ImageView(helpBImage));

		// Instruction Screen
		view.getChildren().add(gameStartView);

		//Start Button
		view.getChildren().add(buttonGameStart);
		buttonGameStart.setStyle("-fx-border-color: transparent;"
				+ "-fx-border-width: 0;"
				+ "-fx-background-radius: 0;"
				+ "-fx-background-color: transparent;"
				+ "-fx-font-family:'Segoe UI', Helvetica, Arial, sans-serif;"
				+ "-fx-font-size: 1em;"
				+ "-fx-text-fill: #828282;");
		buttonGameStart.setTranslateX(150);
		buttonGameStart.setTranslateY(400);

		view.getChildren().add(buttonHelp);
		buttonHelp.setStyle("-fx-border-color: transparent;"
				+ "-fx-border-width: 0;"
				+ "-fx-background-radius: 0;"
				+ "-fx-background-color: transparent;"
				+ "-fx-font-family:'Segoe UI', Helvetica, Arial, sans-serif;"
				+ "-fx-font-size: 1em;"
				+ "-fx-text-fill: #828282;");
		buttonHelp.setTranslateX(450);
		buttonHelp.setTranslateY(400);

		buttonGameStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.setScene(initGameScene());
			}
		});

		buttonHelp.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.setScene(initInstructionScene(stage));
			}
		});

		Scene scene = new Scene(view, 810, 640);
		return scene;

	}

	public Scene initInstructionScene(Stage stage) {

		Group view = new Group();

		ImageView gameView = new ImageView(instructionImage);
		Button buttonGameStart = new Button(null, new ImageView(startBImage));
		
		view.getChildren().add(gameView);
		
		view.getChildren().add(buttonGameStart);
		buttonGameStart.setStyle("-fx-border-color: transparent;"
				+ "-fx-border-width: 0;"
				+ "-fx-background-radius: 0;"
				+ "-fx-background-color: transparent;"
				+ "-fx-font-family:'Segoe UI', Helvetica, Arial, sans-serif;"
				+ "-fx-font-size: 1em;"
				+ "-fx-text-fill: #828282;");
		buttonGameStart.setTranslateX(500);
		buttonGameStart.setTranslateY(450);
		
		buttonGameStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.setScene(initGameScene());
			}
		});

		//Create the scene with the Instruction Components
		Scene scene = new Scene(view, 810, 640);
		return scene;

	}
	
	/**
	 * the constructor of the game scene
	 * (include show some numbers as sample for now)
	 * @return Scene
	 * 		the scene that show to player when in
	 * 		sudoku game
	 */
	public Scene initGameScene() {
		controller = new SudokuController(this);
		Group view = new Group();
		//back ground imageView
		ImageView boardBackView = new ImageView(boardBackImage);
		boardBackView.setFitHeight(640);
		boardBackView.setFitWidth(810);
		boardBackView.relocate(0, 0);
		view.getChildren().add(boardBackView);
		
		//image of the black lines to show the sudoku board
		Rectangle boardUp = new Rectangle(540, 2);
		boardUp.relocate(50, 50);
		view.getChildren().add(boardUp);
		
		Rectangle boardDown = new Rectangle(540, 2);
		boardDown.relocate(50, 588);
		view.getChildren().add(boardDown);
		
		Rectangle boardLeft = new Rectangle(2, 540);
		boardLeft.relocate(50, 50);
		view.getChildren().add(boardLeft);
		
		Rectangle boardRight = new Rectangle(2, 540);
		boardRight.relocate(588, 50);
		view.getChildren().add(boardRight);
		
		Rectangle inBoardH1 = new Rectangle(540, 2);
		inBoardH1.relocate(50, 229);
		view.getChildren().add(inBoardH1);
		
		Rectangle inBoardH2 = new Rectangle(540, 2);
		inBoardH2.relocate(50, 409);
		view.getChildren().add(inBoardH2);
		
		Rectangle inBoardV1 = new Rectangle(2, 540);
		inBoardV1.relocate(229, 50);
		view.getChildren().add(inBoardV1);
		
		Rectangle inBoardV2 = new Rectangle(2, 540);
		inBoardV2.relocate(409, 50);
		view.getChildren().add(inBoardV2);
		
		model = new SudokuModel();
		model.genNewGame();
		model.diGenGame("easy");
		Scene scene = new Scene(view, 810, 640);
		//imageView of background of number boxes
		numBackView = new ImageView[9][9];
		numView = new ImageView[9][9][4][4];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				numBackView[x][y] = new ImageView(numBackImage);
				numBackView[x][y].relocate(55+x*60, 55+y*60);
				view.getChildren().add(numBackView[x][y]);
				numView[x][y][0][0] = new ImageView();
				if (model.board.getNum(x, y) > 0) {
					numView[x][y][0][0].setImage(Box.cnums[model.board.getNum(x, y)]);
				}
				numView[x][y][0][0].setFitHeight(40);
				numView[x][y][0][0].setFitWidth(32);
				numView[x][y][0][0].relocate(64+x*60, 60+y*60);
				view.getChildren().add(numView[x][y][0][0]);
				for (int sx = 1; sx < 4; sx++) {//add temp views for that box
					for (int sy = 1; sy < 4; sy++) {
						numView[x][y][sx][sy] = new ImageView(Box.nums[(sx) + (3 - sy) * 3]);
						numView[x][y][sx][sy].setFitHeight(13);
						numView[x][y][sx][sy].setFitWidth(10);
						numView[x][y][sx][sy].relocate(64+x*60+(sx-1)*16, 60+y*60+(sy-1)*16);
						numView[x][y][sx][sy].setVisible(false);
						view.getChildren().add(numView[x][y][sx][sy]);
					}
				}
			}
		}
		
		buttonPanel = new ButtonPanel(view);
		
		
		//set up timer related view
		gearView = new ImageView(gearImage);
		gearView.setFitHeight(200);
		gearView.setFitWidth(200);
		gearView.relocate(595, 525);
		view.getChildren().add(gearView);
		timeText = new Text(648, 630, "00:00");
		timeText.setFont(new Font(40));
		view.getChildren().add(timeText);
		initController(scene);
		return scene;
	}
	
	/**
	 * update the ImageViews of the Box at the given
	 * coordinate
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void updateUserNum(int x, int y) {
		if (model.board.getUserNum(x, y).size() == 1) { //show big view
			for (int i = 0; i < 9; i++) {//disable all temp views
				numView[x][y][i % 3 + 1][i / 3 + 1].setVisible(false);;
			}
			numView[x][y][0][0].setImage(Box.nums[model.board.getUserNum(x, y).get(0)]);
			//System.out.println(model.board.getUserNum(x, y).get(0));
		}else if (model.board.getUserNum(x, y).size() >= 1){ //show temp views
			numView[x][y][0][0].setImage(null);
			for (int i = 0; i < 9; i++) {
				numView[x][y][i % 3 + 1][i / 3 + 1].setVisible(false);;
			}
			for (int num: model.board.getUserNum(x, y)) {
				num --;
				numView[x][y][num % 3 + 1][3 - num / 3].setVisible(true);;
			}
		}else { //show nothing
			numView[x][y][0][0].setImage(null);
		}
	}
	
	/**
	 * set EventHandlers of scene to the current controller
	 * @param scene the scene want to update
	 */
	void initController(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
			public void handle(KeyEvent event) {
				controller.keyPressed(event);
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
					
			public void handle(KeyEvent event) {
				controller.keyReleased(event);
			}
		});
		
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				controller.mouseMoved(e);
			}
		});
		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				controller.mouseDragged(e);
			}
		});
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				controller.mousePressed(e);
			}
		});

		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				controller.mouseReleased(e);
			}
		});
	}
	
	void initUI(Stage stage) {
		this.stage = stage;
		Scene scene = initGameStartScene(this.stage);//changed for test
		controller = new StartController(this);
		initController(scene);
		
		
		this.stage.setTitle("Sudoku game");
		this.stage.setScene(scene);
		this.stage.show();
		
		timeCounter = 0;
		AnimationTimer timer = new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	        	controller.updateTimer();
	        }
	    };
	    
        Timer FPStimer = new Timer();
        TimerTask Task1 = new TimerTask() {
            @Override
            public void run() {
            	timeCounter ++;
            }
        };
        FPStimer.scheduleAtFixedRate(Task1, 0l, 1000);
	    
	    timer.start();
	}

}
