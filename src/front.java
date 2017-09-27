/*There are a total of 4 objects in the program 
1 slab that user can move with mouse
2 ball that is moved according to the move function and movement initiated by click of mouse
3 brick similer brick is created in a for loop nested 
4  label info to provide necesery instructions

Sequence Of program

Methods operate as in the sequence in main 
setup is called only oce and post fisish is called once 
rest all methods are in loop that run untill game is over or finshed
*/

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;
import java.awt.*;

public class front extends GraphicsProgram
{	
public void run()
{
	setup();
while(!gameOver)
{
moveBall();
faceobject();
collision();
if(finish()==1)
break;
pause(delay);	
}// Game is Finshed or over 

if(brickCount==0)
{
postwin();
}	
}//Run Ends	

private void setup()
{
// Setting Window Size initiating info
this.setSize(windowW, windowH);
pause(20);							// To make susre this pointer is changed for new dimensions 
this.setBackground(Color.GREEN);
info=new GLabel("Game BreakOut");
add(info);
info.setFont("Serif-25");
info.setLocation(0,25);
info1=new GLabel("Game BreakOut");
info1.setLabel("Your Pointer is OUT Please Come Back...");
info1.setFont("Serif-50");
info1.setLocation(200,getHeight()-300);

bar=new GLine(0,getHeight()-250,getWidth(),getHeight()-250);


// Adding slab 
slab = new G3DRect(this.getWidth()/6,this.getWidth()/80);	
add(slab,25,getHeight()-2*slab.getHeight());
slab.setFillColor(Color.blue);
slab.setFilled(true);

//Adding mouse listeners
addMouseListeners();

/*
 Seting brick dimensions ad numbers 16 signifies tat horizontaly 16 bricks can be placed
24 is used so that only half of the screen height is used for bricks 
that is if height is height/24 implies if number of vertical bricks is 12 height/24*12 =1/2 of total height 
how ever these numbers of bricks can be changed according to the values of i & j in the following loop 
*/
temp=RandomGenerator.getInstance();// For random colour 
brickW=getWidth()/16;
brickH=getHeight()/24;
//initial brick count is 0 which i increased as evry brick is added
brickCount=0;
int x=0,y=50;
for(int i=0;i<10;i++)
{
	for(int j=0;j<16;j++)
	{	
		brick=new GRect(brickW,brickH);
		brick.setFilled(true);
		brick.setFillColor(temp.nextColor());
		if(i%2==0)
		{
			add(brick,x,y);
		brickCount++;
		}
x+=brickW;
	}
	x=0;
	y+=brickH;
}

// Setting up ball
ball=new GOval(brickH,brickH);
ball.setFilled(true);
ball.setFillColor(Color.red);
add(ball,slab.getX()+slab.getWidth()/2,slab.getY()-ball.getHeight());
}//Setup method ends

public void mouseMoved(MouseEvent event)
{
// if is used so that slab is never out of the window dimensions

if(event.getX()>=getWidth()-20 || event.getY()>=getHeight()-20 || event.getX()<=20 || event.getY()<=20)
{
gamePaused=true;
	if(ball.getY() < getHeight()-250)
	{
	ball.setLocation(ball.getX(),420);
	repaint();
	}
pause(50);
add(info1);
delay=50;
add(bar);
}
else if(event.getX()>=0 && event.getX()<=getWidth()-slab.getWidth())
{
	remove(info1);
	gamePaused=false;
	repaint();
	slab.setLocation(event.getX(),getHeight()-2*slab.getHeight());
	delay=5;
	remove(bar);
	remove(info1);
}
}

public void mouseClicked(MouseEvent event)
{
if(fly==false)
{
	//Now ball will fly
	fly=true;
	dx=1;	
	dy=1;	
}
if(gameOver)
	{
	removeAll();
	}
}

private void moveBall()
{
if(!gameOver)
{
	if(!fly)
{
// if ball is not flying ball should stick to th slab
		ball.setLocation(slab.getX()+slab.getWidth()/2,slab.getY()-ball.getHeight());	
		info.setLabel("Click And Move To start the game");
}
	else
	{
ball.move(dx,dy); // dx & dy were set to 1 in mouse clicked event
info.setLabel("Remaining Bricks : "+brickCount);
if(gamePaused==true)
slab.setLocation(ball.getX()-slab.getWidth()/2,slab.getY());
	}

}// if ganme!over
}//move method ends

//Checks collision with walls only
private void collision()
{
if(leftwall() || rightwall())
{
dx=-dx;	
}
if(topwall())
{
dy=-dy;	
}
if(bottom())
{

	gameOver=true;
	removeAll();
	info=new GLabel("Game BreakOut");
	add(info);
	info.setFont("Serif-50");
	info.setLocation(getWidth()/2-info.getWidth()/2,getHeight()/2);
	info.setLabel("You Loose !!!!");
}
} // Collision Check end


private boolean leftwall()
{
	if(ball.getX()<=0)
	return true;
	return false;
}
private boolean rightwall()
{
if(ball.getX()+ball.getWidth()>=getWidth())
	return true;
	return false;	
}

private boolean topwall()
{
if(ball.getY()<=0)
	return true;
	return false;	
}
private boolean bottom()
{
if(ball.getY()>=getHeight())
	return true;
	return false;	
}

/*Differenet cases in the method signify diffrent positions of the ball by which it can
stricke any object case numbers are analogus to poition in a wall clock 
as 12 is top 6 bottom and 7 is actualy 7.5 and 4 = 4.5
Case 6 is treated differently for the case when ball strikes the slab 
slab is decided into 5 section with diffrent throwing angles or values of dx*/

private void faceobject()
{
int point = getPoint();

switch (point)
{
	case 12:
		dy=-dy;
		break;
	case 3:
		dx=-dx;
		break;
	case 6:
		if(obj==slab)
		{
			if(ball.getX()+ball.getWidth()/2>=slab.getX() && ball.getX()+ball.getWidth()/2<slab.getX()+slab.getWidth()/5)
			{
				dx=-3;
				
			}
			else if(ball.getX()+ball.getWidth()/2>=slab.getX()+slab.getWidth()/5 && ball.getX()+ball.getWidth()/2<slab.getX()+slab.getWidth()*2/5  )
			{
				dx=-2;
			}
			else if(ball.getX()+ball.getWidth()/2>=slab.getX()+slab.getWidth()*2/5 && ball.getX()+ball.getWidth()/2<slab.getX()+slab.getWidth()*3/5  )
			{
				//Do nothing
			}
			else if(ball.getX()+ball.getWidth()/2>=slab.getX()+slab.getWidth()*3/5 && ball.getX()+ball.getWidth()/2<slab.getX()+slab.getWidth()*4/5  )
			{
				dx=2;
			}
			else if(ball.getX()+ball.getWidth()/2>=slab.getX()+slab.getWidth()*4/5 && ball.getX()+ball.getWidth()/2<=slab.getX()+slab.getWidth())
			{
				dx=3;
			}		
		}// if obj==slab in case 3
		
		dy=-1;
		break;
	case 9:
		dx=-dx;
		break;
	case 1:
		dx=-dx;
		dy=-dy;
		break;
	case 4:
		dy=-dy;
		dx=-dx;
		break;
	case 7:
		dy=-dy;
		dx=-dx;
		break;
	case 10:
		dx=-dx;
		dy=-dy;
		break;
}// Swith scope ends	

}//check fase ends

/*Prsense of any object is check at these provided 8 points on the ball as the extreme corners 
at each point 1 is ades or subtracted to elivate the checkd point from the ball itself 
otherwise program will check element at that point and find that ball is present whic will actualy 
remove the ball and ball wont move in any direction and other problems 

for position 7,4,1,10 a substrate factor is introduced to calculate cordinates of that point
sustrate can be eaisly calculate using pythagoras theoram
and geometry 

*At every removal ball count is reduced */
private int getPoint()
{
double point12X,point1X,point3X,point4X,point6X,point7X,point9X,point10X;
double point12Y,point1Y,point3Y,point4Y,point6Y,point7Y,point9Y,point10Y;


point12X=ball.getX()+ball.getWidth()/2;
point12Y=ball.getY()-1;

point3X=ball.getX()+ball.getWidth()+1;
point3Y=ball.getY()+ball.getHeight()/2;

point6X=ball.getX()+ball.getWidth()/2;
point6Y=ball.getY()+ball.getHeight()+1;

point9X=ball.getX()-1;
point9Y=ball.getY()+ball.getHeight()/2;

double substrate =Math.sqrt(ball.getWidth()*0.35355339);

point10X=ball.getX()+substrate-1;
point10Y=ball.getY()+substrate-1;

point1X=ball.getX()+ ball.getWidth()-substrate+1;
point1Y=ball.getY()+substrate-1;

point4X=ball.getX()+ball.getWidth()-substrate+1;
point4Y=ball.getY()+ball.getHeight()-substrate+1;

point7X=ball.getX()+substrate-1;
point7Y=ball.getY()+ball.getHeight()-substrate+1;

if(getElementAt(point12X,point12Y)!=null )
{
	obj=getElementAt(point12X,point12Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point12X,point12Y));
		brickCount--;
	}
	
		return 12;
}

if(getElementAt(point3X,point3Y)!=null)
{
	obj=getElementAt(point3X,point3Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point3X,point3Y));
		brickCount--;
	}
		return 3;
}

if(getElementAt(point6X,point6Y)!=null)
{
	obj=getElementAt(point6X,point6Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point6X,point6Y));
		brickCount--;
	}
		return 6;
}

if(getElementAt(point9X,point9Y)!=null)
{
	obj=getElementAt(point9X,point9Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point9X,point9Y));
		brickCount--;
	}
		return 9;
}

if(getElementAt(point1X,point1Y)!=null)
{
	obj=getElementAt(point1X,point1Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point1X,point1Y));
		brickCount--;
	}
		return 1;
}

if(getElementAt(point4X,point4Y)!=null)
{
	obj=getElementAt(point4X,point4Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point4X,point4Y));
		brickCount--;
	}
		return 4;
}

if(getElementAt(point7X,point7Y)!=null)
{
	obj=getElementAt(point7X,point7Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point7X,point7Y));
		brickCount--;
	}
		return 7;
}

if(getElementAt(point10X,point10Y)!=null)
{
	obj=getElementAt(point10X,point10Y);
	if(obj!=slab && obj!=info && obj!=info1 && obj!=bar)
	{
		remove(getElementAt(point10X,point10Y));
		brickCount--;
	}
		return 10;
}


return 0;
}

//Checks if bricks reaining are 0 or not
private int finish()
{
if(brickCount==0)
	{	
	return 1;
	}

return 0;
}

//Just for post win display
private void postwin()
{
	removeAll();
	info=new GLabel("Game BreakOut");
	add(info);
	info.setFont("Serif-50");
	info.setLocation(getWidth()/2-info.getWidth()/2,getHeight()/2);
	info.setLabel("Weee Won Weee WON Once AgAiN :-)");
	
}


//Declaration of class variables
	GRect brick,slab;    					
	GOval ball;
	int windowW=1400,windowH=650,brickH,brickW,dx=0,dy=0;
	
	/*Window height and width are initiated as above
	brick height and brick width are defined in setup method
	dx,dy hold speed in x and y dir which are set to 1 in mouse click event 
	howevr dx changs as the ball strikes the slab
	brick count is used to check if the game is finshed or not 
	it is used in finish method and also in move method to display remaining bricks	*/
	
	int brickCount=0,delay=5;
	boolean gameOver=false,fly=false,gamePaused=false;
	
	/*fly is initaly set to false hat means ball is not flying as the user clicks mouse button
	fly is set to true and also dx & dy are set to 1 Object obj is used in facecheck method in collision method
	to check if the curent object is brick or not*/
	
	GObject obj;
	GLabel info,info1;
	RandomGenerator temp;//For random color
	GLine bar;
	// End Declaration	
}//Class Front Ends


