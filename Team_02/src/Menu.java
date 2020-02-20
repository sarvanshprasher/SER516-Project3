/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author kunnu
 */
public class Menu extends JFrame implements ActionListener {
    java.util.List<Point> displayList = new ArrayList<Point>();
    String pathname = "data.dat";
    JButton clearBtn = new JButton("Clear");
  JButton saveBtn = new JButton("Save");
  JButton restoreBtn = new JButton("Restore");
  JButton quitBtn = new JButton("Quit");
    public Panel CreateMenu()
    {
        //addMouseListener(this);

   // setLayout(new BorderLayout());
    Panel pan = new Panel();
   clearBtn.addActionListener(this);
    pan.add(clearBtn);
   saveBtn.addActionListener(this);
    pan.add(saveBtn);
   restoreBtn.addActionListener(this);
    pan.add(restoreBtn);
    quitBtn.addActionListener(this);
    pan.add(quitBtn);
   add("North", pan);
    pan.setSize(100, 100);
    return pan;
   
  }
    public void LoadFileChooser()
  {
      try {
            JFileChooser selectFile = new JFileChooser();
            //selectFile.showOpenDialog(null);
            selectFile.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Data File", "dat");
            selectFile.addChoosableFileFilter(filter);
            selectFile.showOpenDialog(null);
            File f = selectFile.getSelectedFile();
           // jTextField1.setText(f.getAbsolutePath());
            if (f.exists()) {

                if (!getFileExtension(f).equals("txt")) {
                   // jTextField3.setText("Invalid File format");
                } else {
                   // data = objWordEditor.readFileAsString(jTextField1.getText());
                   /* if (data.isEmpty()) {
                        JOptionPane.showMessageDialog(rootPane, "Some error occurred ");
                        jTextField3.setText("Some error occurred.");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "File loaded successfully.");
                    }
                    */

                }

            } else {

                //jTextField3.setText("File not found");
            }

        } catch (Exception ex) {
        }
  }
  public void SaveFileChooser()
  {
      try {
            JFileChooser selectFile = new JFileChooser();

            selectFile.setDialogTitle("Save As");
            selectFile.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Data File", "dat");
            selectFile.addChoosableFileFilter(filter);
            int result = selectFile.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                // selectFile.showOpenDialog(null);
                File f = selectFile.getSelectedFile();

               // jTextField2.setText(f.getAbsolutePath());
                if (f.exists()) {
                    //jTextField3.setText("File already exist.");
                } else if (!getFileExtension(f).equals("dat")) {
                   // jTextField3.setText("Invalid File format");
                } else if (getFileExtension(f).equals("dat")) {
                   /* int saveResult = objWordEditor.SaveFile(output, jTextField2.getText());
                    if (saveResult == 1) {
                        JOptionPane.showMessageDialog(rootPane, "File saved successfully.");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Some error occurred ");
                       // jTextField3.setText("Some error occurred.");
                    }
*/
                }

            }
        } catch (Exception ex) {
        } // TODO add your handling code here:
  }
  private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == clearBtn) {
      displayList = new Vector();
      repaint();
    } else if (e.getSource() == saveBtn) {
      try {
          SaveFileChooser();
        FileOutputStream fos = new FileOutputStream(pathname);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(displayList);
        oos.flush();
        oos.close();
        fos.close();
      } catch (Exception ex) {
        System.out.println("Trouble writing display list vector");
      }
    } else if (e.getSource() == restoreBtn) {
      try {
          LoadFileChooser();
        FileInputStream fis = new FileInputStream(pathname);
        ObjectInputStream ois = new ObjectInputStream(fis);
        displayList =   (ArrayList<Point>)(ois.readObject());
        ois.close();
        fis.close();
        repaint();
      } catch (Exception ex) {
        System.out.println("Trouble reading display list vector");
      }
    } else if (e.getSource() == quitBtn) {
      setVisible(false);
      dispose();
      System.exit(0);
    }
    }
 
}
