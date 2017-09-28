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
checkPause();
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
info.setLocation(0,upMargin-info.getDescent());
info1=new GLabel("Game BreakOut");
info1.setLabel("Your Pointer is OUT Please Come Back...");
info1.setFont("Serif-50");
info1.setLocation(0,upMargin-info.getHeight());

bar=new GLine(0,getHeight()-250,getWidth(),getHeight()-250);


// Adding slab 
slab = new G3DRect(this.getWidth()/6,this.getWidth()/80);	
add(slab,25,getHeight()-downMargin);
slab.setFillColor(Color.blue);
slab.setFilled(true);

addBricks();

//Adding mouse listeners
addMouseListeners();

// Setting up ball
ball=new GOval(brickH,brickH);
ball.setFilled(true);
ball.setFillColor(Color.red);
add(ball,slab.getX()+slab.getWidth()/2,slab.getY()-ball.getHeight());
}//Setup method ends

public int addBricks()
{

/*
 Seting brick dimensions ad numbers 16 signifies that horizontaly 16 bricks can be placed
24 is used so that only half of the screen height is used for bricks 
that is if height is height/24 implies if number of vertical bricks is 12 height/24*12 =1/2 of total height 
how ever these numbers of bricks can be changed according to the values of i & j in the following loop 
*/

topBar=new GLine(0,upMargin,getWidth(),upMargin);
add(topBar);
topBar.setColor(Color.LIGHT_GRAY);

brickW=getWidth()/16;
brickH=getHeight()/24;
//initial brick count is 0 which i increased as evry brick is added
brickCount=0;
int x=0,y=upMargin;

pile = new Bricks(brickW,brickH);
add(pile,0,upMargin);
brickCount=pile.getCount();
bar.setLocation(0,y+pile.getextentY());
	return 0;
}


public void mouseMoved(MouseEvent event)
{
// if is used so that slab is never out of the window dimensions
this.event=event;

if (event.getY()<slab.getY()+slab.getHeight() && event.getY()>info.getY()+info.getDescent() && gamePaused==true)
{
gamePaused=false;
remove(info1);
remove (bar);
delay=5;
}

if(event.getX()>=0 && event.getX()<=getWidth()-slab.getWidth() && gamePaused==false)
{
	slab.setLocation(event.getX(),getHeight()-2*slab.getHeight());	
}
}

public void mouseClicked(MouseEvent event)
{
if(!fly && !gameOver)
{
	//Now ball will fly
	fly=true;
	dx=1;	
	dy=1;	
}}

private void moveBall()
{
if(!gameOver)
{
	if(!fly)
{
// if ball is not flying ball should stick to th slab

		ball.setLocation(slab.getX()+slab.getWidth()/2,slab.getY()-ball.getHeight());	
		info.setLabel("Click Anywhere to start");
}
	else
	{

ball.move(dx,dy); // dx & dy were set to 1 in mouse clicked event
info.setLabel("Remaining Bricks : "+brickCount+"   Pointer Position"+ event.getXOnScreen()+" , "+event.getY() );
	}

}// if ganme!over
}//move method ends

//Checks collision with walls only


private void checkPause()
{
	if(fly)
	{
		if( (event.getY()>=slab.getY() + slab.getHeight() || event.getY() <= info.getY()+info.getDescent()) && ball.getY() > bar.getY()+2)// 2 is for margin of safety as it may cling to th bar otherwise
		{
			gamePaused=true;
			add(info1);
			delay=100;
			add(bar);
		}
	}
if(windowH-getHeight() !=0 || windowW-getWidth()!=0)
{
removeAll();
windowH=getHeight();
windowW=getWidth();
setup();
}

}


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
	if(obj==pile)
	{
		pile.rmv(point12X,point12Y-upMargin);
		brickCount--;
	}
	
		return 12;
}

if(getElementAt(point3X,point3Y)!=null)
{
	obj=getElementAt(point3X,point3Y);
	if(obj==pile)
	{
		pile.rmv(point3X,point3Y-upMargin);
		brickCount--;
	}
		return 3;
}

if(getElementAt(point6X,point6Y)!=null)
{
	obj=getElementAt(point6X,point6Y);
	if(obj==pile)
	{
		pile.rmv(point6X,point6Y-upMargin);
		brickCount--;
	}
		return 6;
}

if(getElementAt(point9X,point9Y)!=null)
{
	obj=getElementAt(point9X,point9Y);
	if(obj==pile)
	{
		pile.rmv(point9X,point9Y-upMargin);
		brickCount--;
	}
		return 9;
}

if(getElementAt(point1X,point1Y)!=null)
{
	obj=getElementAt(point1X,point1Y);
	if(obj==pile)
	{
		pile.rmv(point1X,point1Y-upMargin);
		brickCount--;
	}
		return 1;
}

if(getElementAt(point4X,point4Y)!=null)
{
	obj=getElementAt(point4X,point4Y);
	if(obj==pile)
	{
		pile.rmv(point4X,point4Y-upMargin);
		brickCount--;
	}
		return 4;
}

if(getElementAt(point7X,point7Y)!=null)
{
	obj=getElementAt(point7X,point7Y);
	if(obj==pile)
	{
		pile.rmv(point7X,point7Y-upMargin);
		brickCount--;
	}
		return 7;
}

if(getElementAt(point10X,point10Y)!=null)
{
	obj=getElementAt(point10X,point10Y);
	if(obj==pile)
	{
		pile.rmv(point10X,point10Y-upMargin);
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
	int windowW=1400,windowH=660;
	double brickH,brickW,dx=0,dy=0;
	
	/*Window height and width are initiated as above
	brick height and brick width are defined in setup method
	dx,dy hold speed in x and y dir which are set to 1 in mouse click event 
	howevr dx changs as the ball strikes the slab
	brick count is used to check if the game is finshed or not 
	it is used in finish method and also in move method to display remaining bricks	*/
	
	int brickCount=0,level=1;
	double delay=5,tbh,tbw;//tbh and tbw are not ised yet probbably use for total brick height and width
	int upMargin=windowH/8;
	int downMargin=windowH/8;
	boolean gameOver=false,fly=false,gamePaused=false;
	
	/*fly is initaly set to false hat means ball is not flying as the user clicks mouse button
	fly is set to true and also dx & dy are set to 1 Object obj is used in facecheck method in collision method
	to check if the curent object is brick or not*/
	Bricks pile;
	GObject obj;
	GLabel info,info1;
	GLine bar,topBar;
	MouseEvent event;
	// End Declaration	
}//Class Front Ends


