import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class MainPage extends Frame implements ActionListener
{
	Button root,addprod,delprod,check,addexprod,worth,vendor,refill;
	Label title;
	Connection con = null;
	Statement st = null;
    ResultSet rs = null;
	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	String str="Inventory Management System";
	Font f1;
	Panel p1;
	
	MainPage()
	{
		super("Welcome to your inventory");
		setSize(600,450);
		setVisible(true);
		
		setLayout(null);
		root=new Button("root Products in Inventory");
		addprod=new Button("Add New Products to Inventory");
		check=new Button("Checkout");
		delprod=new Button("Delete Products from Inventory");
		addexprod=new Button("Add Existing Products to Inventory");
		worth=new Button("Total Inventory Worth");
		vendor=new Button("Vendors");
		refill=new Button("Refill Remainder");
		f1=new Font("Serif",Font.BOLD,25);
		p1=new Panel();
		title=new Label("Inventory Management System");
		
		p1.setBounds(0,0,600,120);
		p1.setLayout(null);
		title.setBounds(130,60,350,30);
		p1.add(title);
		p1.setBackground(Color.BLACK);
		p1.setForeground(Color.YELLOW);
		p1.setFont(f1);
		
		root.setBounds(70,170,200,30);
		addprod.setBounds(70,230,200,30);
		addexprod.setBounds(70,290,200,30);
		delprod.setBounds(70,350,200,30);
		check.setBounds(330,170,200,30);
		worth.setBounds(330,230,200,30);
		refill.setBounds(330,290,200,30);
		vendor.setBounds(330,350,200,30);

		add(p1);
		add(root);
		add(addprod);
		add(addexprod);
		add(delprod);
		add(vendor);
		add(check);
		add(worth);
		add(refill);
		
		MyMainWindowAdapter adapter=new MyMainWindowAdapter(this);
		addWindowListener(adapter);
		
		root.addActionListener(this);
		addprod.addActionListener(this);
		addexprod.addActionListener(this);
		check.addActionListener(this);
		delprod.addActionListener(this);
		worth.addActionListener(this);
		vendor.addActionListener(this);
		refill.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==root)
		{
				new rootprodFrame();
		}
		else if(ae.getSource()==addprod)
		{
			new AddprodFrame();
		}
		else if(ae.getSource()==check)
		{
			new CheckoutFrame();
		}
		else if(ae.getSource()==delprod)
		{
			new DelprodFrame();
		}
		else if(ae.getSource()==addexprod)
		{
			new AddexprodFrame();
		}
		else if(ae.getSource()==worth)
		{
			double d=0;
			int x;
			float y;
			try
			{
				con = DriverManager.getConnection(url, user, password);
				st = con.createStatement();
				rs = st.executeQuery("select * from t5");
				while(rs.next())
				{
					x=Integer.parseInt(rs.getString(3));
					y=Float.parseFloat(rs.getString(4));
					d=d+(x*y);
				}
				JOptionPane.showMessageDialog(null,"Net worth of your inventory is Rs."+d,"Inventory Worth", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (SQLException ex) 
			{
				System.out.println(ex);
			}
		}
		else if(ae.getSource()==vendor)
		{
			new VendorFrame();
		}
		else if(ae.getSource()==refill)
		{
			new RefillFrame();
		}
	}
	public void paint(Graphics g)
	{
	}
}
class RefillFrame extends Frame                                      
{
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	Font f1,f2;
	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	
	RefillFrame()
	{
		super("Refill Remainder window");
		setSize(300,400);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		setForeground(Color.DARK_GRAY);
		f1=new Font("Serif",Font.BOLD,20);
		f2=new Font("Times New Roman",Font.PLAIN,18);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){dispose();}});
	}
	public void paint(Graphics g)
	{	
		int a,b,x=140,y=1;
		g.setFont(f1);
		g.drawString("Items to Refill:",90,100);
		g.drawLine(80,110,223,110);
		g.setFont(f2);
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from t5");
			while(rs.next())
			{
				a=Integer.parseInt(rs.getString(3));
				b=Integer.parseInt(rs.getString(5));
				if(a<b)
				{
						g.drawString(y+")",80,x);
						g.drawString(rs.getString(2),100,x);
						x+=30;
						y+=1;
				}
			}
			if(y==1)
			{
				setVisible(false);
				JOptionPane.showMessageDialog(null,"There are no items in the refill remainder","No items", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
}

class VendorFrame extends Frame implements ActionListener                                   
{
	Connection con = null;
	Statement st = null,st2=null;
	ResultSet rs = null,rs2=null;
	Font f1,f2;
	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	Button entire;
	VendorFrame()
	{
		super("Vendor Details Window");
		setSize(480,500);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		setForeground(Color.DARK_GRAY);
		f1=new Font("Serif",Font.BOLD,18);
		f2=new Font("Times New Roman",Font.PLAIN,18);
		entire=new Button("Click here to see the list of your vendors");
		entire.setBounds(80,420,320,25);
		add(entire);
		entire.addActionListener(this);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){dispose();}});
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==entire)
		{	
			new VenList();
			setVisible(false);
		}
	}
	public void paint(Graphics g)
	{
		int x=140;
		int flag=0;
		g.setFont(f1);
		g.drawString("Vendors",80,100);
		g.drawString("List of products supplied",220,100);
		g.drawLine(0,110,480,110);
		g.setFont(f2);
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select distinct Vendor from t5");
			while(rs.next())
			{
				flag+=1;
				g.drawString(rs.getString(1),90,x);
				st2 = con.createStatement();
				rs2 = st2.executeQuery("select Productname from t5 where Vendor=\""+rs.getString(1)+"\"");
				while(rs2.next())
				{
					g.drawString(rs2.getString(1),280,x);                                                          
					x=x+30;
				}
				x=x+20;
			}
			if(flag==0)
			{
				setVisible(false);
				JOptionPane.showMessageDialog(null,"No vendors!","Oops..", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
}
class VenList extends Frame
{
	Connection con = null;
	Statement st = null;
    ResultSet rs = null;

	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	Font f1,f2;
	VenList()
	{
		super("Your list of Vendors");
		setSize(300,400);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		setForeground(Color.DARK_GRAY);
		f1=new Font("Serif",Font.BOLD,20);
		f2=new Font("Times New Roman",Font.PLAIN,18);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){dispose();}});
	}
	public void paint(Graphics g)
	{	
		int x=140,y=1;
		g.setFont(f1);
		g.drawString("You Vendors:",90,100);
		g.drawLine(80,110,223,110);
		g.setFont(f2);
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select distinct Vendor from v2");
			while(rs.next())
			{
				g.drawString(y+")",80,x);
				g.drawString(rs.getString(1),100,x);
				x+=30;
				y+=1;
			}
			if(y==1)
			{
				setVisible(false);
				JOptionPane.showMessageDialog(null,"You have no vendors","No vendors", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
	
}
class CheckoutFrame extends Frame implements ActionListener,ItemListener
{
	Label prod1,quan1,price1,des1;;
	Choice prod2,des2;
	TextField quan2,price2,checkavail2;
	Button checkavail1,cart,chkout;
	double total;
	String str;
	
	Connection con = null;
	Statement st = null;
    ResultSet rs = null;
	PreparedStatement pst = null;

	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
    
	CheckoutFrame()
	{
		super("Checkout window");
		setSize(470,550);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		total=0;
		
		prod1=new Label("Product:");
		quan1=new Label("Quantity:");
		price1=new Label("Price:");
		des1=new Label("Description:");
		des2=new Choice();
		prod2=new Choice();
		prod2.add("Select item");
		quan2=new TextField(5);
		price2=new TextField(10);
		checkavail1=new Button("Check Availability");
		checkavail2=new TextField(10);
		cart=new Button("Add to cart");
		chkout=new Button("Checkout");
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){dispose();}});
		
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from t5");

            while(rs.next()) 
			{
				prod2.add(rs.getString(2));                          
            }
		}
		catch (SQLException ex) 
		{
			System.out.println(ex);
		}
		try
		{
			rs = st.executeQuery("select * from t5");
			while(rs.next()) 
			{	
				des2.add(rs.getString(6));
			}	
        }
		catch (SQLException ex) 
		{
			System.out.println(ex);
		}
		
		prod1.setBounds(150,80,50,20);
		des1.setBounds(130,150,70,20);
		price1.setBounds(150,220,50,20);
		quan1.setBounds(150,290,50,20);
		prod2.setBounds(220,80,100,20);
		des2.setBounds(220,150,100,20);
		price2.setBounds(220,220,100,20);
		quan2.setBounds(220,290,100,20);
		checkavail1.setBounds(90,360,110,22);
		checkavail2.setBounds(220,360,100,20);
		cart.setBounds(110,420,100,30);
		chkout.setBounds(220,420,100,30);
		
		add(prod1);
		add(prod2);
		add(des1);
		add(des2);
		add(quan1);
		add(price1);
		add(prod2);
		add(quan2);
		add(price2);
		add(checkavail1);
		add(checkavail2);
		add(cart);
		add(chkout);
		
		prod2.addItemListener(this);
		checkavail1.addActionListener(this);
		cart.addActionListener(this);
		chkout.addActionListener(this);
		
		MyCheckWindowAdapter adapter=new MyCheckWindowAdapter(this);
		addWindowListener(adapter);
		
		
	}
	public void itemStateChanged(ItemEvent e)
	{
		String item = (String)e.getItem();
		if (e.getStateChange() == ItemEvent.SELECTED)
        {
		try
		{
			rs = st.executeQuery("select * from t5 where Productname=\""+item+"\"");
            if(rs.next()) 
			{
				price2.setText(rs.getString(4));
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==checkavail1)
		{
			try
			{
				String str="";
				int x;
				rs = st.executeQuery("select * from t5 where Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\""); 
				rs.next();
				str=rs.getString(2);
				x=Integer.parseInt(rs.getString(3));
				if(x>=(Integer.parseInt(quan2.getText())))
				{
					checkavail2.setText("Available");
				}
				else
				{
					checkavail2.setText("Not Available");
				}
			}
			catch (SQLException ex) 
			{
				System.out.println(ex);
				JOptionPane.showMessageDialog(null,"Check your fields and try again","Oops..Try again", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ae.getSource()==cart&&quan2.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null,"Check your fields and try again","Oops..Try again", JOptionPane.ERROR_MESSAGE);
		}
		else if(ae.getSource()==cart&&checkavail2.getText().equals("Available"))
		{
			try
			{
				int q,q1;
				String s;
				rs = st.executeQuery("select * from t5 where Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\"");
				rs.next();
				q=Integer.parseInt(quan2.getText());
				total+=Double.parseDouble(rs.getString(4))*q;
				q1=Integer.parseInt(rs.getString(3));
				q1=q1-q;
				quan2.setText("");
				checkavail2.setText("");
				if(q1<Integer.parseInt(rs.getString(5)))
				{
					JOptionPane.showMessageDialog(null, "Refill Item: "+rs.getString(2),"Refill Remainder", JOptionPane.PLAIN_MESSAGE);          
				}
				s=q1+"";
				con = DriverManager.getConnection(url, user, password);
				st.executeUpdate("UPDATE t5 SET Quantity="+q1+" WHERE Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\"");
				JOptionPane.showMessageDialog(null,"Products added to cart","Success!", JOptionPane.PLAIN_MESSAGE);
			}
			catch (SQLException ex) 
			{
				System.out.println(ex);
				JOptionPane.showMessageDialog(null,"Check your fields and try again","Oops..Try again", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ae.getSource()==cart&&checkavail2.getText().equals("Not Available"))
		{
			JOptionPane.showMessageDialog(null, "Product not available","Not available", JOptionPane.PLAIN_MESSAGE);
		}
		else if(ae.getSource()==chkout)
		{
			String s1=total+"";
			setVisible(false);
			PaymentFrame p1=new PaymentFrame();
			p1.total2.setText(s1);	
		}
	}
	public void paint(Graphics g)
	{
		String s="*Always check for availabilty before adding to cart";
		g.drawString(s,80,520);
	}
}

class PaymentFrame extends Frame
{
	Button cash,debit;
	Label total1;
	TextField total2;
	
	PaymentFrame()
	{
		super("Checkout");
		setSize(500,430);
		setVisible(true);
		setLayout(null);
		setBackground(new Color(230,230,230));
		
		total1=new Label("Total Amount");
		total2=new TextField(10);
		cash=new Button("Pay by cash");
		debit=new Button("Pay by debit / credit card");
		
		total1.setBounds(150,100,100,20);
		total2.setBounds(260,100,100,20);
		cash.setBounds(150,200,200,30);
		debit.setBounds(150,270,200,30);
		
		add(total1);
		add(total2);
		add(cash);
		add(debit);
		
		MyPaymentWindowAdapter adapter=new MyPaymentWindowAdapter(this);
		addWindowListener(adapter);
	}
}

class AddexprodFrame extends Frame implements ActionListener
{
	Label prod1,quan1;
	Choice prod2;
	TextField quan2;
	Button addprod;
	Connection con = null;
	ResultSet rs=null;
	Statement st=null;
    String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "root";
    String password = "ethylene";
	int flag=0;
	
	AddexprodFrame()
	{
		super("Add Products to inventory");
		setSize(300,300);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		
		MyAddexWindowAdapter adapter=new MyAddexWindowAdapter(this);
		addWindowListener(adapter);
		
		prod1=new Label("Product:");
		quan1=new Label("Quantity:");
		prod2=new Choice();
		quan2=new TextField(5);
		addprod=new Button("Add to inventory");
	
		prod1.setBounds(60,80,50,20);
		quan1.setBounds(60,150,50,20);
		prod2.setBounds(130,80,100,20);
		quan2.setBounds(130,150,100,20);
		addprod.setBounds(100,220,100,30);
		
		add(prod1);
		add(prod2);
		add(quan1);
		add(quan2);
		add(addprod);
		
		addprod.addActionListener(this);
		
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from t5");
			while(rs.next()) 
			{
				prod2.add(rs.getString(2));
				flag+=1;
            }
			if(flag==0)
			{
				setVisible(false);
				JOptionPane.showMessageDialog(null,"There are no items in the inventory","No items", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch (SQLException ex) 
		{
			System.out.println(ex);
			JOptionPane.showMessageDialog(null,"Check your fields and try again","Oops..Try again", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==addprod)
		{
			try
			{
				con = DriverManager.getConnection(url, user, password);
				rs = st.executeQuery("select * from t5 where Productname=\""+prod2.getSelectedItem()+"\""); 
				rs.next();
				int x=Integer.parseInt(rs.getString(3));
				x=x+Integer.parseInt(quan2.getText());
				st.executeUpdate("UPDATE t5 SET Quantity="+x+" WHERE Productname=\""+prod2.getSelectedItem()+"\"");
				quan2.setText("");
				JOptionPane.showMessageDialog(null, "Products successfully added","Success!", JOptionPane.PLAIN_MESSAGE);
			}
			catch(SQLException ex)
			{
				System.out.println(ex);
			}
		}
	}
}
class AddprodFrame extends Frame implements ActionListener
{
	Label prod1,quan1,price1,min1,vend1,des1;
	TextField prod2,quan2,price2,min2,vend2,des2;
	Button addinvent;
	Connection con = null;
	ResultSet rs=null;
	Statement st=null;
	PreparedStatement pst = null;
    String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "root";
    String password = "ethylene";
	int k=0;
	
	AddprodFrame()
	{
		super("Add Products to inventory");
		setSize(500,600);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		
		MyAddWindowAdapter adapter=new MyAddWindowAdapter(this);
		addWindowListener(adapter);
		
		prod1=new Label("Product:");
		quan1=new Label("Quantity:");
		price1=new Label("Price:");
		vend1=new Label("Vendor:");
		des1=new Label("Description:");
		des2=new TextField(20);
		prod2=new TextField(20);
		quan2=new TextField(5);
		price2=new TextField(5);
		vend2=new TextField(20);
		addinvent=new Button("Add to inventory");
		min1=new Label("*Min Limit:");
		min2=new TextField(5);
		
		prod1.setBounds(140,80,70,20);
		prod2.setBounds(230,80,100,20);
		des1.setBounds(140,150,70,20);
		des2.setBounds(230,150,100,20);
		quan1.setBounds(140,220,50,20);
		quan2.setBounds(230,220,100,20);
		price1.setBounds(140,290,70,20);
		price2.setBounds(230,290,100,20);
		vend1.setBounds(140,360,70,20);
		vend2.setBounds(230,360,100,20);
		min1.setBounds(140,430,70,20);
		min2.setBounds(230,430,100,20);
		addinvent.setBounds(170,500,150,30);
		
		add(prod1);
		add(prod2);
		add(des1);
		add(des2);
		add(quan1);
		add(quan2);
		add(price1);
		add(price2);
		add(vend1);
		add(vend2);
		add(min1);
		add(min2);
		add(addinvent);
		
		addinvent.addActionListener(this);
	}
	public void paint(Graphics g)
	{
		String str="*MinLimit: If the product quantity goes below this value, you will be notified";
		g.setColor(new Color(50,50,50));
		g.drawString(str,50,575);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==addinvent)
		{
			try 
			{
				String str1 = prod2.getText();
				String str2=quan2.getText();
				String str3=price2.getText();
				String str4=min2.getText();
				String str5=des2.getText();
				String str6=vend2.getText();
		
				con = DriverManager.getConnection(url, user, password);
				st = con.createStatement();
				rs = st.executeQuery("select * from t5");
				while(rs.next())
				{
					k=Integer.parseInt(rs.getString(1));
					k+=1;
				}
				String str7=Integer.toString(k);
				st.executeUpdate("UPDATE t5 SET Price=\""+str3+"\" WHERE Productname=\""+str1+"\"");
				pst = con.prepareStatement("INSERT INTO t5(pid,Productname,Quantity,Price,Minlimit,Description,Vendor) VALUES(?,?,?,?,?,?,?)");
				pst.setString(1,str7);
				pst.setString(2, str1);
				pst.setString(3,str2);
				pst.setString(4,str3);
				pst.setString(5,str4);
				pst.setString(6,str5);
				pst.setString(7,str6);
				pst.executeUpdate();
				
				prod2.setText("");
				quan2.setText("");
				price2.setText("");
				min2.setText("");
				des2.setText("");
				vend2.setText("");
				JOptionPane.showMessageDialog(null, "Products successfully added","Success!", JOptionPane.PLAIN_MESSAGE);
				pst = con.prepareStatement("INSERT INTO v2(Vendor,Productname) VALUES(?,?)");
				pst.setString(1, str6);
				pst.setString(2,str1);
				pst.executeUpdate();
			}
			catch (SQLException ex)
			{
				System.out.println(ex);
			}	
		}	
	}
}

class DelprodFrame extends Frame implements ActionListener
{
	Label prod1,quan1,des1;
	Choice prod2,des2;
	TextField quan2;
	Button delbut,del,rem;
	Connection con = null;
	Statement st = null;
    ResultSet rs = null,rs2=null;
	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	int flag=1;
	
	DelprodFrame()
	{
		super("Delete Products from inventory");
		setSize(650,330);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(230,230,230));
		
		MyDeleteWindowAdapter adapter=new MyDeleteWindowAdapter(this);
		addWindowListener(adapter);
		
		prod1=new Label("Product:");
		quan1=new Label("Quantity:");
		prod2=new Choice();
		delbut=new Button("Delete product");
		del=new Button("Delete specific products:");
		rem=new Button("Delete entire product details:");
		des1=new Label("Description:");
		des2=new Choice();
		quan2=new TextField(5);
		prod2.add("Select Item");
		des2.add("Select description");
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from t5");
			while(rs.next()) 
			{
				prod2.add(rs.getString(2));
            }
		}
		catch (SQLException ex) 
		{
			System.out.println(ex);
		}
		try
		{
			rs = st.executeQuery("select * from t5");
			while(rs.next()) 
			{	
				des2.add(rs.getString(6));
			}	
        }
		catch (SQLException ex) 
		{
			System.out.println(ex);
		}
		prod1.setBounds(240,110,50,20);
		quan1.setBounds(240,180,50,20);
		prod2.setBounds(310,110,100,20);
		quan2.setBounds(310,180,100,20);
		delbut.setBounds(280,230,100,30);
		del.setBounds(30,110,180,25);
		rem.setBounds(30,180,180,25);
		des1.setBounds(430,110,70,20);
		des2.setBounds(520,110,100,20);
		
		add(del);
		add(rem);
		add(prod1);
		add(prod2);
		add(des1);
		add(des2);
		add(quan1);
		add(quan2);
		add(delbut);
		
		delbut.addActionListener(this);
		del.addActionListener(this);
		rem.addActionListener(this);
	}
	 
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==delbut&&flag==1)
		{
			try
			{
				int q,q1;
				String s;
				con = DriverManager.getConnection(url, user, password);
				rs = st.executeQuery("select * from t5 where Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\"");
				rs.next();
				q=Integer.parseInt(quan2.getText());
				q1=Integer.parseInt(rs.getString(3));
				if(q<=q1)
				{
					q1=q1-q;
					s=q1+"";
					st.executeUpdate("UPDATE t5 SET Quantity="+q1+" WHERE Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\"");
					quan2.setText("");
					JOptionPane.showMessageDialog(null, "Products successfully deleted","Success!", JOptionPane.PLAIN_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Check the product quantity you have specified..","Oops...Try again", JOptionPane.PLAIN_MESSAGE);
				}
			}
			catch (SQLException ex) 
			{
				JOptionPane.showMessageDialog(null, "Check the fields and try again","Oops...Try again", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Check the fields and try again","Oops...Try again", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ae.getSource()==delbut&&flag==2)
		{
			try
			{
				Boolean b;
				 con = DriverManager.getConnection(url, user, password); 
				String sql="delete from t5 where Productname=\""+prod2.getSelectedItem()+"\" AND Description=\""+des2.getSelectedItem()+"\"";
				int delete=st.executeUpdate(sql);
				if(delete==1)
				{
					prod2.remove(prod2.getSelectedItem());
					des2.remove(des2.getSelectedItem());
					JOptionPane.showMessageDialog(null, "Product details successfully deleted","Success!", JOptionPane.PLAIN_MESSAGE);   
				}
				else
				{
						JOptionPane.showMessageDialog(null, "Check the fields and try again","Oops...Try again", JOptionPane.ERROR_MESSAGE);
				}	
			}
			catch(SQLException ex) 
			{
				JOptionPane.showMessageDialog(null, "Check the fields and try again","Oops...Try again", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ae.getSource()==del)
		{
			prod1.setVisible(true);
			prod2.setVisible(true);
			quan1.setVisible(true);
			quan2.setVisible(true);
			des1.setVisible(true);
			des2.setVisible(true);
			repaint();
			flag=1;
		}
		else if(ae.getSource()==rem)
		{
			prod1.setVisible(true);
			prod2.setVisible(true);
			quan1.setVisible(false);
			quan2.setVisible(false);
			des1.setVisible(true);
			des2.setVisible(true);
			repaint();
			flag=2;
		}
	}
}


class rootprodFrame extends Frame
{
	Connection con = null;
	Statement st = null;
    ResultSet rs = null;
	Font f1,f2;
	int x;

	String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "testuser";
    String password = "ethylene";
	
	rootprodFrame()
	{
		super("root Products in inventory");
		setSize(900,600);
		setLayout(null);
		setVisible(true);
		setBackground(new Color(240,240,240));
		f1=new Font("Serif",Font.BOLD,20);
		f2=new Font("Times New Roman",Font.PLAIN,18);
		
		MyrootWindowAdapter adapter=new MyrootWindowAdapter(this);
		addWindowListener(adapter);	
	}
	public void paint(Graphics g)
	{
		int flag=0;
		g.setFont(f1);
		g.setColor(Color.DARK_GRAY);
		g.drawString("Product",100,80);
		g.drawString("Quantity",220,80);
		g.drawString("Price",350,80);
		g.drawString("Minlimit",460,80);
		g.drawString("Description",580,80);
		g.drawString("Vendor",740,80);
		g.drawLine(0,100,900,100);
		g.setFont(f2);
		g.setColor(new Color(100,100,100));
		x=120;
		
		try
		{
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from t5");
			while(rs.next())
			{
				g.drawString(rs.getString(2),100,x);
				g.drawString(rs.getString(3),240,x);
				g.drawString(rs.getString(4),360,x);
				g.drawString(rs.getString(5),480,x);
				g.drawString(rs.getString(6),580,x);
				g.drawString(rs.getString(7),740,x);
				x=x+30;
				flag+=1;
			}
			if(flag==0)
			{
				setVisible(false);
				JOptionPane.showMessageDialog(null,"There are no items in the inventory","Inventory empty", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
}

class MyMainWindowAdapter extends WindowAdapter
{
	MainPage ob;
	MyMainWindowAdapter(MainPage ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		System.exit(0);
	}
}

class MyrootWindowAdapter extends WindowAdapter
{
	rootprodFrame ob;
	MyrootWindowAdapter(rootprodFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}

class MyAddWindowAdapter extends WindowAdapter
{
	AddprodFrame ob;
	MyAddWindowAdapter(AddprodFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}

class MyCheckWindowAdapter extends WindowAdapter
{
	CheckoutFrame ob;
	MyCheckWindowAdapter(CheckoutFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}

class MyPaymentWindowAdapter extends WindowAdapter
{
	PaymentFrame ob;
	MyPaymentWindowAdapter(PaymentFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}
class MyDeleteWindowAdapter extends WindowAdapter
{
	DelprodFrame ob;
	MyDeleteWindowAdapter(DelprodFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}
class MyAddexWindowAdapter extends WindowAdapter
{
	AddexprodFrame ob;
	MyAddexWindowAdapter(AddexprodFrame ob1)
	{
		ob=ob1;
	}
	public void windowClosing(WindowEvent we)
	{
		ob.dispose();
	}
}

public class InventoryManagementSystem extends Frame
{
	public static void main(String[] args) 
	{
		new MainPage();
    }
}
