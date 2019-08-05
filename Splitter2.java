import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class Splitter2 extends JFrame implements Runnable ,ActionListener,ItemListener
{
	JButton btn_in,btn_out,btn_exit,btn_reset,btn_split,btn_merge;
	ButtonGroup rbg;
	JRadioButton chu_size1,chu_size2,chu_size3,chu_size4,chu_num;
	JTextField in_text,out_text,user_size_text;
	JTextArea chunks_text;
	JTextArea howarea;
	JTextArea aboutarea;
	Label lbl,lbl_date,lbl_time,size_type;
	JScrollPane jp;
	long size=0,filesize;
	FileDialog dialog1,dialog2;
	JMenuBar jmb;
	JMenu file,help;
	JMenuItem open,exit,about,howtouse,history,merge;	
	JPanel panel1;
	String curr_date,time;
	Date dd;
	Thread th;
	int rbs=1;
	File filetoread;
	File filetowrite;
	FileInputStream filein;
	FileOutputStream fileout;
	FileInputStream ffilein;
	FileOutputStream ffileout;
	Font ff;
	
	public Splitter2()
	{
		super("File Splitter and Merger");
		lbl=new Label("");
		lbl_date=new Label("");
		lbl_time=new Label("");
	
		th=new Thread(this);
		th.start();
		setLayout(null);
		open=new JMenuItem("open");
		exit=new JMenuItem("exit");
		about=new JMenuItem("About");
		howtouse=new JMenuItem("How to use");
		history=new JMenuItem ("History");
		

		file=new JMenu("File");
		help=new JMenu("Help");
		jmb=new JMenuBar();
		
		file.add(open);
		file.addSeparator();
		file.add(exit);
		help.add(about);
		help.add(howtouse);
		help.addSeparator();
		help.add(history);
		jmb.add(file);
		jmb.add(help);
		setJMenuBar(jmb);
		panel1=new JPanel();
		
		btn_in=new JButton("Input...");
		btn_out=new JButton("Output...");
		btn_exit=new JButton("Exit",new ImageIcon("images\\exit.png"));
		btn_reset=new JButton("Reset",new ImageIcon("images\\reset.png"));
		btn_split=new JButton(new ImageIcon("images\\SPLIT.jpg"));
		btn_merge=new JButton(new ImageIcon("images\\merge.jpg"));
		chu_size1=new JRadioButton("1.44 MB",true);
		chu_size2=new JRadioButton("1.0 MB",false);
		chu_size3=new JRadioButton("0.5 MB",false);
		chu_size4=new JRadioButton("User Defined");
		chu_num=new JRadioButton ("no of chunks");
		in_text=new JTextField(20);
		out_text=new JTextField(20);
		user_size_text=new JTextField(20);
		size_type=new Label();
		chunks_text=new JTextArea(20,10);
		chunks_text.setEditable(false);
		rbg=new ButtonGroup();
		jp=new JScrollPane(chunks_text);
	// setting editable false of user_size_text
		user_size_text.setEditable(false);
	// setting colors of textfields 
		ff=new Font("courier",Font.BOLD,13);
		in_text.setFont(ff);
		out_text.setFont(ff);
	//setting bounds ....

		in_text.setBounds(20,50,200,30);
		out_text.setBounds(20,90,200,30);
		
		btn_in.setBounds(230,50,100,30);
		btn_out.setBounds(230,90,100,30);
		btn_exit.setBounds(360,380,100,30);
		btn_split.setBounds(345,50,140,70);
		btn_merge.setBounds(345,130,140,50);
		btn_reset.setBounds(60,380,100,30);
		panel1.setLayout(new GridLayout(5,1));
		panel1.setBounds(230,140,100,200);
		jp.setBounds(20,140,200,200);
		user_size_text.setBounds(330,300,130,30);
		size_type.setBounds(465,307,20,30);
		lbl.setBounds(0,420,250,30);
		lbl_date.setBounds(250,420,150,30);
		lbl_time.setBounds(400,420,100,30);

		setdate();

		add(btn_in);add(btn_out);add(btn_exit);add(btn_split);add(btn_reset); 
		add(in_text);add(out_text);
		add(jp);
		add(lbl);add(lbl_date);add(lbl_time);
		rbg.add(chu_size1);rbg.add(chu_size2);rbg.add(chu_size3);rbg.add(chu_size4);rbg.add(chu_num);
		panel1.add(chu_size1);panel1.add(chu_size2);panel1.add(chu_size3);panel1.add(chu_size4);panel1.add(chu_num);add(btn_merge);
		this.add(panel1); add(user_size_text);add(size_type);
		chunks_text.setBackground(new Color(240,240,240));
	// howtouse menu item ..... text area.....
		howarea=new JTextArea(200,50);
		howarea.append("   To split a file first click on the open in file menu or  ");		
		howarea.append("\n input button and then select the file, then open the   ");		
		howarea.append("\n file and then click on the output button and select   ");		
		howarea.append("\n destination directory and output file name and then ");
		howarea.append("\n click  the radio button specifying the size of the      ");
		howarea.append("\n chunks, and then click the SPLIT button.Now there  ");
		howarea.append("\n will a msg print that u'r file has been splitted.            ");
		howarea.append("\n Now if you want to merge the chunks to get the        ");
		howarea.append("\n file then there is a batch file created in your               ");
		howarea.append("\n specified output directory where chunks were           ");
		howarea.append("\n created. By clicking on that file you can get back       ");
		howarea.append("\n your file.");
		howarea.append(" \nYou can merge the chunks by clicking on merge button");
		howarea.append("\n To merge files first click on the open in file menu or  ");		
		howarea.append("\n input button and then select the destination directory file and    ");		
		howarea.append("\ninputfilename and then click on the output button and select   ");		
		howarea.append("\n destination directory and output file name ");
		howarea.append("\n  and then click the Merge button.Now there  ");
		howarea.append("\n will a msg print that u'r files has been merged.            ");
		howarea.setEditable(false);
		howarea.setForeground(new Color(210,210,219));		
		howarea.setBackground(new Color(52,120,7));
	// about project developer
		aboutarea=new JTextArea(5,50);
		aboutarea.append("TANNU");		
		aboutarea.append("\nROLL NO- 16001001059");
                aboutarea.append("\nDeenBandhu ChotuRam University Of Science and Technology ");		
		aboutarea.append("\nMurthal,Sonipat ");
		aboutarea.setEditable(false);
		aboutarea.setForeground(new Color(210,210,219));		
		aboutarea.setBackground(new Color(52,120,7));		
			
	//registering to listeners......
		open.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
		howtouse.addActionListener(this);
		history.addActionListener(this);
		btn_in.addActionListener(this);
		btn_out.addActionListener(this);
		btn_exit.addActionListener(this);
		btn_reset.addActionListener(this);
		btn_split.addActionListener(this);
		btn_merge.addActionListener(this);
	// RadioButtons registering to ItemListener....
		chu_size1.addItemListener(this);
		chu_size2.addItemListener(this);
		chu_size3.addItemListener(this);
		chu_size4.addItemListener(this);
		chu_num.addItemListener(this);
	// size of splitter frame and location of it....
		
		setSize(500,510);
		setLocation(200,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem mitm=null;
		JButton btn=null;
		
		if(e.getSource() instanceof JMenuItem)
		{
			 mitm=(JMenuItem) e.getSource();
		}
		else
		{
			 btn=(JButton) e.getSource();
		}
		 
		
		if(mitm==open||btn==btn_in)
		{
			dialog1 =new FileDialog(this,"open file to split or merge",FileDialog.LOAD);
			dialog1.setVisible(true);	
			in_text.setText(dialog1.getDirectory()+"\\"+ dialog1.getFile());
			filetoread=new File(in_text.getText());
		 
			filesize=filetoread.length();
			chunks_text.setText("File size is"+filesize+"bytes."); 
		}
		else if(btn==btn_merge)
		{
			try{
			if(out_text.getText()!=null&&( new File(out_text.getText()).getParentFile().exists()==true))
			{
				
				filetoread=new File(in_text.getText());
				if(filetoread.isFile()==true)
				{
				
				File fmerge=filetoread.getParentFile();
				File filee;
				File farr[]=fmerge.listFiles();
				String fname=filetoread.getAbsolutePath();
				int chh=fname.indexOf('.');
				String ffname =fname.substring(0,chh);
				fileout=new FileOutputStream(out_text.getText(),true);
				
				byte barr[];
				for(int i=0;i<farr.length;i++)
				{
				if(farr[i].getAbsolutePath().equals(ffname+".bat"))
				{
					continue;
				}	
				else if(farr[i].getAbsolutePath().startsWith(ffname))
				{
				filee=new File(farr[i].getAbsolutePath());
				filein=new FileInputStream(filee);
				barr =new byte[(int)filee.length()]; 
				filein.read(barr);
				fileout.write(barr);
				filein.close();}
				
				}
					ffileout=new FileOutputStream("e:\\history\\log.txt",true);
				String msgg1=""+in_text.getText() +" merged successfully on "+curr_date +" at "+ time;
					ffileout.write(msgg1.getBytes());
					ffileout.write("\n".getBytes());
				
				fileout.close();
				ffileout.close();
				JOptionPane.showMessageDialog(this,"Your files are merged successfully","Message",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
			JOptionPane.showMessageDialog(this,"Input path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);
			}	
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Output path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);
			}

		}
			catch(IOException ee)
		{
			JOptionPane.showMessageDialog(this," Specify output file.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);					
		}
	catch(NumberFormatException ee)
		{
				JOptionPane.showMessageDialog(this," Not a valid size.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);			
		}
	catch(Exception ee)
	{
		JOptionPane.showMessageDialog(this,"  Error path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);			
	}
	
}
			
		else if(mitm==exit||btn==btn_exit)
		{
			System.exit(0);
		}
		else if(mitm==about)
		{
			JDialog frmabout=new JDialog(this,"About");
			frmabout.add(aboutarea);	
			frmabout.setSize(380,160);
			frmabout.setLocation(200,200);
			frmabout.setVisible(true);
		}
		else if(mitm==howtouse)
		{
			JDialog frmhow=new JDialog(this,"How to use");
			frmhow.add(howarea);	
			frmhow.setSize(350,350);
			frmhow.setLocation(200,200);
			frmhow.setVisible(true);
		
		}
		else if(mitm==history)
		{
			JTextArea ta1=new JTextArea(10,5);
			ta1.setText("");	
			ta1.setEditable(true);	
			
			try{
			ffilein =new FileInputStream("e:\\history\\log.txt");
			JFrame f=new JFrame("history");
			
			JScrollPane jp=new JScrollPane( ta1);	
			f.add(jp);
			StringBuffer sb=new StringBuffer(100);
			int x;
			
			while(true)
			{
				 x=ffilein.read();
				if(x==-1)	
				{
					break;
				}
				sb.append((char)x);
				
			}
			ta1.setFont(ff);
			ta1.setBackground(Color.cyan);
			ta1.setText(String.valueOf(sb));
		
			f.setSize(600,350);
			f.setLocation(400,200);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ta1.setEditable(false);
			ffilein.close();
			}catch(IOException ee){}
		}
		 if(btn==btn_out)
		{
			dialog2=new FileDialog(this,"save file ",FileDialog.SAVE);
			dialog2.setVisible(true);
			out_text.setText(dialog2.getDirectory()+"\\"+dialog2.getFile());
			filetowrite=new File(out_text.getText());
		}
		else if(btn==btn_reset)
		{
			in_text.setText("");
			out_text.setText("");
			chunks_text.setText("");
			user_size_text.setText("");
			
		}
		
		else if(btn==btn_split)
		{
			try{
					
	
			filetoread=new File(in_text.getText());
			if(new File(out_text.getText()).getParentFile().exists()==true&&out_text.getText()!=null)
			{
			if(filetoread.isFile()==true)
			{
				filesize =filetoread.length();
			if(rbs==1)
			{size= (long)(1.44*1024*1024);}
			else if(rbs==2)
			{	size=(long)(0.5*1024*1024);
			}		
			else  if(rbs==3)
			{
				size=(long)(1.0*1024*1024);
			}
			else if(rbs==4)
			{
				if(user_size_text.getText().trim()!=null)
				{
					size=(long)(Integer.parseInt(user_size_text.getText())*1024);
				}
				else
				{
				JOptionPane.showMessageDialog(this," Specify size of chunks ","Error!!!!!!",JOptionPane.ERROR_MESSAGE);				
				}			
			}
			else if(rbs==5)
			{ 
				if(user_size_text.getText().trim()!=null)
				{
				size=(long)(filesize+2)/Integer.parseInt(user_size_text.getText());
				}
				else
				{
				JOptionPane.showMessageDialog(this," Specify no of chunks ","Error!!!!!!",JOptionPane.ERROR_MESSAGE);				
				}			
			
			}
	
	
			filetowrite =new File(out_text.getText());
			filein=new FileInputStream (filetoread);
			String comm="copy /b ";
			filesize=filetoread.length();
			int chunks=0;
		
				byte arr[]=new byte[(int)size];
				for(int i=0;i<filesize;i+=size)
				{
					chunks++;
					 fileout=new FileOutputStream(filetowrite.getAbsolutePath()+chunks,true);
					comm=comm+(filetowrite.getName()+chunks);
	
					
					filein.read(arr);
	
						
				
					
					if(filesize-(i+1)<size)
					{
						
						fileout.write(arr,0,(int)filesize-(i+1));	
									
					}
					else
					{
						fileout.write(arr);
						comm=comm+"+";
					}
					
					chunks_text.append("\n File"+(filetowrite.getName()+chunks )+"get completed");
			
				}
					comm=comm+" \""+filetoread.getName()+"\"";
					byte temp[];
					temp=comm.getBytes();
					fileout=new FileOutputStream(filetowrite.getAbsolutePath()+".bat");
					fileout.write(temp);
					

JOptionPane.showMessageDialog(this,"Your File has been splitted succesfully.","Congratulations!!!!!",JOptionPane.INFORMATION_MESSAGE);
					ffileout=new FileOutputStream("e:\\history\\log.txt",true);
					String msgg=""+in_text.getText() +" splitted successfully on "+curr_date +" at "+ time;
					ffileout.write(msgg.getBytes());
					ffileout.write("\n".getBytes());
				fileout.close();
				filein.close();
				ffileout.close();
				
				}
				else
				{
					JOptionPane.showMessageDialog(this," Input path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);			
				}
			}

		else
		{
			
			JOptionPane.showMessageDialog(this," Output path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);			
		}
		
	}catch(IOException ee)
		{
			JOptionPane.showMessageDialog(this," Specify output file.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);					
		}
	catch(NumberFormatException ee)
		{
				JOptionPane.showMessageDialog(this," Not a valid size.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);			
		}
	catch(Exception ee)
	{
		JOptionPane.showMessageDialog(this,"  Error path does not exists.","Error!!!!!!",JOptionPane.ERROR_MESSAGE);
					
	}
	
    }
}

	public void run()
	{
		while(true)
		{
			setdate();
		}
	}
	public void setdate()
	{
		Calendar cdr=new GregorianCalendar();
		curr_date=""+cdr.get(Calendar.DAY_OF_MONTH)+"/"+(cdr.get(Calendar.MONTH)+1)+"/"+cdr.get(Calendar.YEAR);
		lbl_date.setText(curr_date);
		time=""+cdr.get(Calendar.HOUR)+":"+cdr.get(Calendar.MINUTE)+":"+cdr.get(Calendar.SECOND)+" ";
		if(cdr.get(Calendar.AM_PM)==1)
		{
			time=time+"PM";
		}
		else
		{
			time=time+"AM";
		}
		lbl_time.setText(time);
	}
	public void itemStateChanged(ItemEvent e)
	{
		size_type.setText("");
		JRadioButton jrbtn=(JRadioButton )e.getSource();
		if(jrbtn==chu_size1)
		{
			user_size_text.setText("");
			rbs=1;
			user_size_text.setEditable(false);
		}
		else if(jrbtn==chu_size2)
		{
			user_size_text.setText("");
			rbs=2;
			user_size_text.setEditable(false);
			
		}
		else if(jrbtn ==chu_size3)
		{
			user_size_text.setText("");	
			rbs=3;
			user_size_text.setEditable(false);
		
		}
		else if(jrbtn==chu_size4)
		{
			rbs=4;
			user_size_text.setText("");	
			user_size_text.setEditable(true);
			size_type.setText("kb");
		}
		else if(jrbtn==chu_num)
		{
			user_size_text.setText("");
			rbs=5;
			user_size_text.setEditable(true);
					size_type.setText("cks");	

		}
	}	

		public static void main(String args[])
	{
		new Splitter2();
	}
}
