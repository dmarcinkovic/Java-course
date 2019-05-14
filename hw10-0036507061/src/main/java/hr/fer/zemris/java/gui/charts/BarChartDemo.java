package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Demo example to show how classes BarChart and BarChartComponent work. This
 * program creates new window in which bar chart is presented.
 * 
 * @author david
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Bar chart that will be displayed to window.
	 */
	private static BarChart barChart;

	/**
	 * Path to file from which this program reads data.
	 */
	private static Path path;
	
	/**
	 * Initialize window size and gui components.
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(600, 500);
		initGui();
	}

	/**
	 * Method used to initialize gui components.
	 */
	private void initGui() {
		Container cp = getContentPane();

		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);
		
		String text = path.toAbsolutePath().normalize().toString();
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		cp.add(label, BorderLayout.PAGE_START);
	}

	/**
	 * Method invoked when running the program. This method creates new window in
	 * which bar chart is drawn.
	 * 
	 * @param args Accepts only one argument: path to file in which all informations
	 *             about bar charts are presented. File should have this
	 *             structure(each one is in new line): X-axis description, Y-axis
	 *             description, information to show in bar chart, minimum number
	 *             that will be presented next to y-axis, maximum number that will
	 *             be presented next to y-axis, gap between two numbers.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of program arguments. I am going to stop this program");
			return;
		}

		List<String> lines = null;
		try {
			path = Paths.get(args[0]);
			lines = Files.readAllLines(path);
		} catch (InvalidPathException e) {
			System.out.println("Invalid path.");
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			barChart = parse(lines);
		} catch (Exception e) {
			System.out.println("Error.");
			System.out.println(e.getMessage());
			return;
		}

		try {
			SwingUtilities.invokeAndWait(() -> {
				new BarChartDemo().setVisible(true);
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse content from given file and returns BarChar instance if parsing
	 * finished successfully.
	 * 
	 * @param lines Content of the file presented as List of Strings.
	 * @return Instance of BarChart.
	 * @throws IllegalArgumentException if error during parsing occurs.
	 * @throws NumberFormatException    if could not parse numbers presented in
	 *                                  string line to integer.
	 */
	private static BarChart parse(List<String> lines) {
		if (lines.size() < 6) {
			throw new IllegalArgumentException("There should be at least 6 lines in the file.");
		}

		String xDescription = lines.get(0);
		String yDescription = lines.get(1);

		List<XYValue> list = getList(lines.get(2));

		if (list.size() == 0) {
			throw new IllegalArgumentException("List of informations is empty.");
		}

		int minY = Integer.parseInt(lines.get(3).trim());
		int maxY = Integer.parseInt(lines.get(4).trim());
		int gap = Integer.parseInt(lines.get(5).trim());
		
		return new BarChart(list, xDescription, yDescription, minY, maxY, gap);
	}

	/**
	 * Return information that will be displayed to BarChartComponent.
	 * 
	 * @param line String in which all information are stored.
	 * @return List of XYValue objects.
	 * @throws IllegalArgumentException if error during parsing occurs.
	 * @throws NumberFormatException    if could not parse numbers presented in
	 *                                  string line to integer.
	 */
	private static List<XYValue> getList(String line) {
		List<XYValue> list = new ArrayList<>();

		String[] values = line.split("\\s+");

		for (String s : values) {
			int index = s.indexOf(",");

			if (index == -1) {
				throw new IllegalArgumentException("List of informations must be seperated with comma.");
			}
			
			int x = Integer.parseInt(s.substring(0, index).trim());
			int y = Integer.parseInt(s.substring(index + 1).trim());
			
			list.add(new XYValue(x, y));
		}

		return list;
	}
}
