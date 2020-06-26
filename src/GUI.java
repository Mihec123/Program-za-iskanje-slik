import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI {

	private JFrame frmImagesearch;
	private JTextField txtField_directory;
	private JTextField textField_csv;
	private JTextField txtSearchDirectoryPath;
	private JTextField txtCsvFileLocation;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JTextField textField_update;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField textField_search;
	private JTextField txtjpgJpeg;
	private JTextField txtPng;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmImagesearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Search iskanje = new Search();
		ReadWrite read_write = new ReadWrite();
		frmImagesearch = new JFrame();
		frmImagesearch.setTitle("ImageSearch");
		frmImagesearch.setBounds(100, 100, 450, 300);
		frmImagesearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmImagesearch.getContentPane().setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("jpg");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setBounds(10, 99, 54, 23);
		frmImagesearch.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("png");
		chckbxNewCheckBox_1.setSelected(true);
		chckbxNewCheckBox_1.setBounds(10, 125, 54, 23);
		frmImagesearch.getContentPane().add(chckbxNewCheckBox_1);
		
		txtField_directory = new JTextField();
		txtField_directory.setToolTipText("Search directory path");
		txtField_directory.setBounds(10, 47, 122, 20);
		frmImagesearch.getContentPane().add(txtField_directory);
		txtField_directory.setColumns(10);
		
		textField_csv = new JTextField();
		textField_csv.setBounds(211, 47, 121, 20);
		frmImagesearch.getContentPane().add(textField_csv);
		textField_csv.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 189, 362, 61);
		frmImagesearch.getContentPane().add(scrollPane);
		
		list = new JList<String> ();
		scrollPane.setViewportView(list);
		
		textField_update = new JTextField();
		textField_update.setEditable(false);
		textField_update.setBounds(338, 156, 86, 20);
		frmImagesearch.getContentPane().add(textField_update);
		textField_update.setColumns(10);
		
		textField_search = new JTextField();
		textField_search.setEditable(false);
		textField_search.setBounds(109, 156, 86, 20);
		frmImagesearch.getContentPane().add(textField_search);
		textField_search.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				iskanje.resetExtensions();
				
				if(chckbxNewCheckBox.isSelected()) {
					iskanje.appendExtensions("jpg");
					iskanje.appendExtensions("JPG");
					iskanje.appendExtensions("JPEG");
					iskanje.appendExtensions("jpeg");
					
				}
				
				if(chckbxNewCheckBox_1.isSelected()) {
					iskanje.appendExtensions("png");
					iskanje.appendExtensions("PNG");
					
				}
				
				textField_update.setText("");
				textField_search.setText("searching");
				String pot = txtField_directory.getText();
				String pot_csv = textField_csv.getText();
				System.out.println(pot);
				System.out.println(pot_csv);
				File f = new File(pot_csv);
				Set<String> seznam_nasih;
				if(f.exists() && !f.isDirectory()) { 
					seznam_nasih = read_write.readCSV(pot_csv);
				}
				else {
					seznam_nasih = new HashSet<String> ();
				}
				
				Set<String> a = iskanje.isci(pot,seznam_nasih);
				DefaultListModel<String>  DLM = new DefaultListModel<String> ();
				for(String t: a) {
					DLM.addElement(t);
					System.out.println("neki:" + t);
				}
				list.setModel(DLM);
				
				textField_search.setText("finished");
			}
		});
		btnSearch.setBounds(10, 155, 89, 23);
		frmImagesearch.getContentPane().add(btnSearch);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				textField_search.setText("");
				textField_update.setText("processing");
				String pot = txtField_directory.getText();
				String pot_csv = textField_csv.getText();
				try {
				if(pot_csv.split("\\.")[1].equals("csv")) {

					File f = new File(pot_csv);
					Set<String> seznam_nasih;
					if(f.exists() && !f.isDirectory()) { 
						seznam_nasih = read_write.readCSV(pot_csv);
					}
					else {
						seznam_nasih = new HashSet<String> ();
					}
				Set<String> seznam_novih = iskanje.preglej(pot, seznam_nasih);
				try {
					read_write.writeCSV(pot_csv, seznam_novih);
					textField_update.setText("finished");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textField_update.setText("error");
				}
			}
				else{
					textField_update.setText("not csv");
				}
				}catch(ArrayIndexOutOfBoundsException exception){
					textField_update.setText("error1");
				}
			}
		});
		btnUpdate.setBounds(243, 155, 89, 23);
		frmImagesearch.getContentPane().add(btnUpdate);
	
		
		txtSearchDirectoryPath = new JTextField();
		txtSearchDirectoryPath.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtSearchDirectoryPath.setText("Search directory path");
		txtSearchDirectoryPath.setEditable(false);
		txtSearchDirectoryPath.setBounds(10, 11, 122, 20);
		frmImagesearch.getContentPane().add(txtSearchDirectoryPath);
		txtSearchDirectoryPath.setColumns(10);
		
		txtCsvFileLocation = new JTextField();
		txtCsvFileLocation.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCsvFileLocation.setEditable(false);
		txtCsvFileLocation.setText("CSV file location");
		txtCsvFileLocation.setBounds(211, 11, 121, 20);
		frmImagesearch.getContentPane().add(txtCsvFileLocation);
		txtCsvFileLocation.setColumns(10);
		
		btnNewButton = new JButton("...");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			      txtField_directory.setText(chooser.getSelectedFile().toString());
			      
			    } else {
			      System.out.println("No Selection ");
			    }
			}
		});
		btnNewButton.setBounds(142, 46, 40, 22);
		frmImagesearch.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("...");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			      textField_csv.setText(chooser.getSelectedFile().toString());
			      
			    } else {
			      System.out.println("No Selection ");
			    }
			}
		});
		btnNewButton_1.setBounds(342, 46, 40, 23);
		frmImagesearch.getContentPane().add(btnNewButton_1);
		
		txtjpgJpeg = new JTextField();
		txtjpgJpeg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtjpgJpeg.setEditable(false);
		txtjpgJpeg.setText("(jpg, jpeg)");
		txtjpgJpeg.setBounds(70, 100, 86, 20);
		frmImagesearch.getContentPane().add(txtjpgJpeg);
		txtjpgJpeg.setColumns(10);
		
		txtPng = new JTextField();
		txtPng.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtPng.setText("(png)");
		txtPng.setEditable(false);
		txtPng.setBounds(70, 126, 86, 20);
		frmImagesearch.getContentPane().add(txtPng);
		txtPng.setColumns(10);
		

		

		

		

	}
}
