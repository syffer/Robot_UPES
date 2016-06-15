package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JHistogram extends JPanel {
	private static final long serialVersionUID = 1L;
	// http://stackoverflow.com/questions/12518496/drawing-a-graphical-histogram 
	// http://sebastien-estienne.developpez.com/tutoriels/java/exercices-graphisme-java2d/?page=exo1 
	// http://stackoverflow.com/questions/2228735/how-do-i-fade-an-image-in-swing/2234020#2234020 
	
	private static final int xGapHistogram = 35; 	// décalage histogramme en X par rapport à la gauche 
	private static final int yGapHistogram = 30;	// décalage histogramme en Y par rapport au bas de la fenetre 
	
	private static final int xGapAxis = 30;			// décalage axes en X par rapport à la gauche de la fenetre
	private static final int yGapAxis = 25;			// décalage axes en Y par rapport au bas de la fenetre
	
	private static final int heigthArrowGap = 4;	// décalage flèche axe en hauteur 
	private static final int widthArrowGap  = 8;	// décalage flèche axe en largeur 
		
	
	private List<Map<Integer, Integer>> frequenciesMap;
	private List<Color> colors;
	private int maximalFrequency;
			
	public JHistogram(List<Map<Integer, Integer>> frequenciesMap) {
		this(frequenciesMap, new ArrayList<Color>(frequenciesMap.size()));
	}
	
	public JHistogram(List<Map<Integer, Integer>> frequenciesMap, List<Color> colors) {
		this.frequenciesMap = frequenciesMap;
		this.colors = colors;
		
		// find the maximum frequency 
		this.maximalFrequency = 0;
		
		for(Map<Integer, Integer> frequencies : this.frequenciesMap) {
			for(int value : frequencies.values()) {
				this.maximalFrequency = Math.max(this.maximalFrequency, value);
			}
		}
		
		// add colors if not enough 
		for(int i = 0; i < frequenciesMap.size() - colors.size(); i++) {
			colors.add(Color.GRAY); 
		}
		
		this.setPreferredSize(new Dimension(256*2 + 60, 256 + 60));
		//this.setMinimumSize(new Dimension(256*2 + 60, 256 + 60));
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		int frameWidth = this.getWidth();
		int frameHeight = this.getHeight();
		
		// histogram window
		int histogramHeigth = frameHeight - 50;
		int histogramWidth = frameWidth - 50;
		
		int barWidth = histogramWidth / 256;
		
		// for each dimension (RGB, grey, ...)
		for(int i = 0; i < this.frequenciesMap.size(); i++) {
			Map<Integer, Integer> frequencies = this.frequenciesMap.get(i);
			
			Color color = this.colors.get(i);
			
			for(int pixelValue : frequencies.keySet()) {
				int frequency = frequencies.get(pixelValue);
				
				// draw a box 
				int barHeight = frequency * histogramHeigth / this.maximalFrequency;
								
				int xBarPosition = pixelValue * barWidth + JHistogram.xGapHistogram;
				int yBarPosition = histogramHeigth - barHeight - JHistogram.yGapHistogram + 50;
								
				Rectangle2D bar = new Rectangle2D.Double(xBarPosition, yBarPosition, barWidth, barHeight);
				
				g2d.setColor(color); 
				g2d.fill(bar);
                g2d.setColor(Color.DARK_GRAY);
                g2d.draw(bar);
            }
			
		}
				
		// draw x axis
		g2d.setColor(Color.BLACK);
		int x1AxisX = JHistogram.xGapAxis;
		int y1AxisX = frameHeight - JHistogram.yGapAxis;
		int x2AxisX = x1AxisX + (barWidth * 256) + 25;
		int y2AxisX = y1AxisX;
		g2d.drawLine(x1AxisX, y1AxisX, x2AxisX, y2AxisX);
		
		// draw x axis arrow
		g2d.drawLine(x2AxisX, y2AxisX, x2AxisX - JHistogram.widthArrowGap, y2AxisX - JHistogram.heigthArrowGap); 
		g2d.drawLine(x2AxisX, y2AxisX, x2AxisX - JHistogram.widthArrowGap, y2AxisX + JHistogram.heigthArrowGap); 
		
		// x axis labels
		for(int i = 0; i < 255; i += 15) {
			
			// line 
			int xLabel = i * barWidth + JHistogram.xGapHistogram + (barWidth / 2);
			int yLabelUp = frameHeight - JHistogram.yGapAxis - 3;
			int yLabelDown = frameHeight - JHistogram.yGapAxis + 3;
			
			g2d.drawLine(xLabel, yLabelUp, xLabel, yLabelDown); 
			
			// text
			String text = "" + i;
			
			int widthText2 = g.getFontMetrics().stringWidth(text) / 2;	// for centering the text 
			int xText = xLabel - widthText2;
			int yText = yLabelDown + 15;
			
			g2d.drawString(text, xText, yText);
		}

		
		// draw y axis 
		g2d.setColor(Color.BLACK);
		int x1AxisY = JHistogram.xGapAxis;
		int y1AxisY = frameHeight - JHistogram.yGapAxis;
		int x2AxisY = x1AxisX;
		int y2AxisY = y1AxisX - histogramHeigth - 25;
		g2d.drawLine(x1AxisY, y1AxisY, x2AxisY, y2AxisY);
		
		// draw y axis arrow 
		g2d.drawLine(x2AxisY, y2AxisY, x2AxisY - JHistogram.heigthArrowGap, y2AxisY + JHistogram.widthArrowGap); 
		g2d.drawLine(x2AxisY, y2AxisY, x2AxisY + JHistogram.heigthArrowGap, y2AxisY + JHistogram.widthArrowGap); 
		
		// draw y axis labels
		for(int i = 0; i < this.maximalFrequency; i += 30) {
			
			// line 
			int yLabel = (i * histogramHeigth / this.maximalFrequency) - JHistogram.yGapHistogram + 50;
			int xLabelUp = JHistogram.xGapAxis - 3;
			int xLabelDown = JHistogram.xGapAxis + 3;
			
			g2d.drawLine(xLabelUp, yLabel, xLabelDown, yLabel); 
			
			// text 
			String text = "" + (this.maximalFrequency - i);
			
			int widthText = g.getFontMetrics().stringWidth(text);	// for centering the text 
			int heigthText2 = g.getFontMetrics().getDescent();
			
			int xText = xLabelUp - widthText;
			int yText = yLabel + heigthText2;
			
			g2d.drawString(text, xText, yText);
		}
		
	}
		
	
	
	public static class Test {
		public static void main(String[] args) {
			int width = 256;
	        int height = 256;
	        
	        int[][] data = new int[width][height];
	        for (int c = 0; c < height; c++) {
	            for (int r = 0; r < width; r++) {
	                data[c][r] = (int) (256 * Math.random());
	                //data[c][r] = 255;
	            }
	        }
	        
	        Map<Integer, Integer> mapHistory = new TreeMap<Integer, Integer>();
	        for (int c = 0; c < data.length; c++) {
	            for (int r = 0; r < data[c].length; r++) {
	                int value = data[c][r];
	                
	                if(!mapHistory.containsKey(value)) mapHistory.put(value, 0);
	                
	                int amount = mapHistory.get(value) + 1;
	                mapHistory.put(value, amount); 
	            }
	        }
	        
	        for (int c = 0; c < height; c++) {
	            for (int r = 0; r < width; r++) {
	                data[c][r] = (int) (256 * Math.random());
	                //data[c][r] = 0;
	            }
	        }
	        
	        Map<Integer, Integer> mapHistory2 = new TreeMap<Integer, Integer>();
	        for (int c = 0; c < data.length; c++) {
	            for (int r = 0; r < data[c].length; r++) {
	                int value = data[c][r];
	                
	                if(!mapHistory2.containsKey(value)) mapHistory2.put(value, 0);
	                
	                int amount = mapHistory2.get(value) + 1;
	                mapHistory2.put(value, amount); 
	            }
	        }
	        
	        List<Map<Integer, Integer>> list = new ArrayList<Map<Integer,Integer>>();
	        list.add(mapHistory);
	        list.add(mapHistory2);
	        
	        ArrayList<Color> colors = new ArrayList<Color>();
	        colors.add(Color.BLUE);
	        colors.add(Color.RED);
	        
	        JFrame frame = new JFrame("Test");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	        frame.add(new JScrollPane(new JHistogram(list, colors)));
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
		}
	}
	
}
