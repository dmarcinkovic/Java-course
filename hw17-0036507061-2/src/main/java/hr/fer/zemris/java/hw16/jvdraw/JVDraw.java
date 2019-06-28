package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;

/**
 * Program that creates program similar to Paint program presented on Windows
 * Operating System. In this version of paint user can only add three graphical
 * objects: lines, circles and filled circles. User can create those objects by
 * clicking on the toobar button and creating them with the mouse. First click
 * for lines is starting point of the line, then by dragging mouse user can
 * choose the length of the line and the direction. In similar way user can
 * create circle. By clicking at the paint area user defines the circle center
 * and then by dragging defines the radius of the circle. User is allowed to
 * edit drawn component. Also, it is possible to change the background color and
 * the filling color of the filled circle by clicking on the toolbar icon.
 * 
 * @author David
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to foreground color area.
	 */
	private JColorArea fgColorArea;

	/**
	 * Reference to background color area.
	 */
	private JColorArea bgColorArea;

	/**
	 * Reference to State object.
	 */
	private Tool currentState;

	/**
	 * Central component.
	 */
	private JDrawingCanvas canvas;

	/**
	 * Reference to drawing model. This model has information about the all
	 * GeometricalObjects presented at the canvas.
	 */
	private DrawingModel drawingModel;

	/**
	 * Save as action. It is used to save as .jvd file that contains all
	 * informations about the GeometricalObjects presented in canvas.
	 */
	private final SaveAsAction saveAsAction = new SaveAsAction(this);

	/**
	 * Save action. It is used to save .jvd file that contains all informations
	 * about the GeometricalObjects presented in canvas. about the
	 */
	private final SaveAction saveAction = new SaveAction(this, saveAsAction);

	/**
	 * Open action. It is used to load .jvd and present all GeometricalObjects to
	 * canvas.
	 */
	private final OpenAction openAction = new OpenAction(getContentPane());

	/**
	 * Export action. It allows user to export presented image in .gif, .jpg and
	 * .png format.
	 */
	private final ExportAction exportAction = new ExportAction();

	/**
	 * Exit action. It asks user if he want to save modified document.
	 */
	private final ExitAction exitAction = new ExitAction(this, saveAsAction);

	/**
	 * Initializes gui for this application.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");

		setSize(800, 600);
		initGUI();

		setLocationRelativeTo(null);
	}

	/**
	 * Method used to initialize GUI. This method add all JComponents this GUI
	 * contains. It adds menubars, toolbars and creates main window component.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());

		fgColorArea = new JColorArea(Color.RED);
		bgColorArea = new JColorArea(Color.BLUE);

		fgColorArea.addMouseListener(new MouseListenerImpl(cp));
		bgColorArea.addMouseListener(new MouseListenerImpl(cp));

		drawingModel = new DrawingModelImpl();

		canvas = new JDrawingCanvas(drawingModel);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		cp.add(splitPane, BorderLayout.CENTER);
		splitPane.add(canvas, JSplitPane.LEFT);

		addToolbar();
		addMenuBar();
		addStatusBar();

		addListOfObjects(splitPane);

		addWindowListener(new MyWindowListener(drawingModel, this, saveAsAction));
	}

	/**
	 * Method that adds new panel to the left of the window. In that panel, all
	 * GeometricalObjects are listed with information about the location and radius
	 * if GeometricalObject is Circle or Filled circle.
	 * 
	 * @param splitPane Split pane on which new panel with list is added.
	 */
	private void addListOfObjects(JSplitPane splitPane) {
		DrawingObjectListModel drawingObjectModel = new DrawingObjectListModel(drawingModel);
		JList<String> jList = new JList<>(drawingObjectModel);

		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(new JScrollPane(jList));

		splitPane.add(panel, JSplitPane.RIGHT);
		splitPane.setDividerLocation(500);
	}

	/**
	 * Method that adds toolbar. There are three buttons presented at toolbar. One
	 * for adding new lines, second one for adding circles and third one for adding
	 * filled circles.
	 */
	private void addToolbar() {
		Container cp = getContentPane();

		JToolBar tb = new JToolBar();
		tb.setFloatable(false);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tb.add(panel);

		cp.add(tb, BorderLayout.PAGE_START);

		panel.add(fgColorArea);
		panel.add(bgColorArea);

		addButtonsToToolbar(panel);
	}

	/**
	 * Method that adds buttons to toolbar. Button are called 'Line', 'Circle' and
	 * 'Filled Circle'. They are user to crate lines, circles and filled circles.
	 * Those buttons are added to button group so that only one button can be
	 * toggled at the time.
	 * 
	 * @param panel Component on which buttons are added.
	 */
	private void addButtonsToToolbar(JComponent panel) {
		JPanel panel2 = new JPanel(new GridLayout(1, 0));

		panel.add(panel2);

		JToggleButton button1 = new JToggleButton("Line");
		JToggleButton button2 = new JToggleButton("Circle");
		JToggleButton button3 = new JToggleButton("<html>Filled circle</html>");

		addButtonListeners(button1, button2, button3);

		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);

		ButtonGroup buttonGroup = new ButtonGroup();

		buttonGroup.add(button3);
		buttonGroup.add(button2);
		buttonGroup.add(button1);
	}

	/**
	 * Method that adds appropriate listeners to button named line, circle,
	 * filledCircle.
	 * 
	 * @param line         ToggleButton that allows users to add lines to the
	 *                     drawing canvas.
	 * @param circle       ToggleButton that allows users to add circles to the
	 *                     drawing canvas.
	 * @param filledCircle ToggleButton that allows users to add filled circles to
	 *                     the drawing canvas.
	 */
	private void addButtonListeners(JToggleButton line, JToggleButton circle, JToggleButton filledCircle) {
		line.addActionListener((e) -> {
			currentState = new AddLine(canvas, fgColorArea, drawingModel);
			canvas.setCurrentState(currentState);
		});

		circle.addActionListener((e) -> {
			currentState = new AddCircle(canvas, fgColorArea, drawingModel);
			canvas.setCurrentState(currentState);
		});

		filledCircle.addActionListener((e) -> {
			currentState = new AddFilledCircle(canvas, fgColorArea, bgColorArea, drawingModel);
			canvas.setCurrentState(currentState);
		});
	}

	/**
	 * Method that adds menu bar. There is only 'file' menubar created.
	 */
	private void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");

		menuBar.add(file);

		configureActions();

		file.add(new JMenuItem(openAction));
		file.add(new JMenuItem(saveAction));
		file.add(new JMenuItem(saveAsAction));
		file.add(new JMenuItem(exportAction));
		file.add(new JMenuItem(exitAction));

		setJMenuBar(menuBar);
	}

	/**
	 * Method used to configure actions. It sets shortcuts and accelerator keys for
	 * every action.
	 */
	private void configureActions() {
		configureOpenAction();
		configureSaveAsAction();
		configureSaveAction();
		configureExportAction();
		configureExitAction();
	}

	/**
	 * Method used to configure export action. It sets name, accelerator key,
	 * mnemonic key and short description.
	 */
	private void configureExportAction() {
		exportAction.setCanvas(canvas);
		exportAction.setDrawingModel(drawingModel);
		exportAction.setEnabled(false);
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Export image");
	}

	/**
	 * Method used to configure open action. It sets name, accelerator key, mnemonic
	 * key and shot description.
	 */
	private void configureOpenAction() {
		openAction.setDrawingModel(drawingModel);
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.SHORT_DESCRIPTION, "Open .jvd file.");
	}

	/**
	 * Method used to configure save as action. It sets name, accelerator key,
	 * mnemonic key and shot description.
	 */
	private void configureSaveAsAction() {
		saveAsAction.setDrawingModel(drawingModel);
		saveAsAction.setEnabled(false);
		saveAsAction.putValue(Action.NAME, "Save as...");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Save as... .jvd file.");
	}

	/**
	 * Method used to configure save action. It sets name, accelerator key, mnemonic
	 * key and short description.
	 */
	private void configureSaveAction() {
		saveAction.setDrawingModel(drawingModel);
		saveAction.setEnabled(false);
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Save .jvd file.");
	}

	/**
	 * Method used to configure exit action. It sets name, accelerator key, mnemonic
	 * key and short description.
	 */
	private void configureExitAction() {
		exitAction.setDrawingModel(drawingModel);
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
	}

	/**
	 * Method used to add status at the bottom of the window. In this status bar
	 * current background and foreground color is being tracked.
	 */
	private void addStatusBar() {
		JStatusBar statusBar = new JStatusBar(fgColorArea, bgColorArea);

		getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Method invoked when running the program. This method creates new window.
	 * 
	 * @param args Arguments provided via command line. In this program they are not
	 *             used.
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				new JVDraw().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
