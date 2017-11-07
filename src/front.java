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
moveComponents();
checkPause();
faceobject();
collision();
pause(delay);	
if(brickCount==0)
{
	fly=false;
	level++;
	removeAll();
	setup();
}
}// Game is Finshed or over 

	
}//Run Ends	

private void setup()
{
// Setting Window Size initiating info
delay=5;
lastDelay=5;
powerVisible=false;
this.setSize(windowW, windowH);
pause(20);							// To make susre this pointer is changed for new dimensions 
//this.setBackground(Color.GREEN);
background = new GImage("file:///E:/work_space/JAVA/BreakIt/images/back.jpg");
background.setSize(getWidth(),getHeight()+70);
background.sendToBack();
background.setLocation(0,-70);
add(background);


info=new GLabel("Game BreakOut");
add(info);
info.setFont("Serif-25");
info.setLocation(0,upMargin-info.getDescent());

info1=new GLabel("Game BreakOut");
info1.setLabel("Your Pointer is OUT Please Come Back...");
info1.setFont("Serif-50");
info1.setLocation(0,upMargin-info.getHeight());

bar=new GLine(0,getHeight()-250,getWidth(),getHeight()-250);
bar.setColor(Color.gray);

// Adding slab 
slab = new GImage("file:///E:/work_space/JAVA/BreakIt/images/slab.jpg");	
slab.setSize(this.getWidth()/6,this.getWidth()/80);
add(slab,25,getHeight()-downMargin);
//slab.setFillColor(Color.blue);
//slab.setFilled(true);



//Adding mouse listeners
addMouseListeners();

// Setting up ball

ball=new GOval(getHeight()/24,getHeight()/24);
ball.setFilled(true);
ball.setFillColor(Color.red);
add(ball,slab.getX()+slab.getWidth()/2,slab.getY()-ball.getHeight());

topBar=new GLine(0,upMargin,getWidth(),upMargin);
add(topBar);
topBar.setColor(Color.LIGHT_GRAY);
addBricks();
}//Setup method ends

private void addBricks()
{

/*
 Seting brick dimensions ad numbers 16 signifies that horizontaly 16 bricks can be placed
24 is used so that only half of the screen height is used for bricks 
that is if height is height/24 implies if number of vertical bricks is 12 height/24*12 =1/2 of total height 
how ever these numbers of bricks can be changed according to the values of i & j in the following loop 
*/

brickW=getWidth()/16;
brickH=getHeight()/24;
//initial brick count is 0 which i increased as evry brick is added
int y=upMargin; // Pile starts from this vertical height
pile = new Bricks(brickW,brickH,level);
add(pile);
pile.setLocation(0,y);
pile.setVisible(true);
brickCount=pile.getCount();
bar.setLocation(0,y+pile.getextentY());
//	return 0;
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
delay=lastDelay;
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

private void moveComponents()
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
//Code to move ball
ball.move(dx,dy); // dx & dy were set to 1 in mouse clicked event
info.setLabel("Remaining Bricks : "+brickCount);//+ " Delay :"+delay + " PowerNum :" +powerNum );

// Code To move Power
	if(power!=null)
	{
		power.move(0,.5);
			
			if(power.getY()>slab.getY()+slab.getHeight())
			{
			remove(power);
			powerNum=0;
			power=null;
			}
			}

	}

}// if ganme!over
}//move method ends

//Checks collision with walls only

// Checks if game is in pause condition that is if pointer is out

private void checkPause()
{
	if(fly && !gamePaused)
	{
		if( (event.getY()>=slab.getY() + slab.getHeight() || event.getY() <= info.getY()+info.getDescent()) && ball.getY() > bar.getY()+2)// 2 is for margin of safety as it may cling to th bar otherwise
		{
			gamePaused=true;
			add(info1);
			lastDelay=delay;
			delay=100;
			add(bar);
		}
	}

}// method check pause over

private void collision()
{
//Following code Checks collision of power with slab
	if(power!=null)
	{
		if(power.getY()+power.getHeight()>=slab.getY() && ( (power.getX()>=slab.getX() && power.getX()+power.getWidth()<=slab.getX()+slab.getWidth()) || (power.getX()+power.getWidth()/2>=slab.getX() && power.getX()+power.getWidth()/2<=slab.getX()+slab.getWidth()) ) )
		{
			powerAct();
		}
	}
	
	
// Checks collision of ball with walls	
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

private int powerAct()
{

	switch (powerNum)
	{
	case 1:
		expandSlab();
		break;
	case 2:
		shrinkSlab();
		break;
	
	case 3:
		expandBall();
		break;

	case 4:
		shrinkBall();
		break;

	case 5:
		speedInc();
		break;

	case 6:
		speedDec();
		break;

	case 7:
		powerVisible=true;
		break;
}

	remove(power);
	powerNum=0;
	power=null;
	
	
return 1;	
}

private void shrinkSlab()
{
if(slab.getWidth()-slab.getWidth()*.25>=ball.getWidth())
	slab.scale(.75,1);
}
private void expandSlab()
{
if(slab.getWidth()+slab.getWidth()*.25<=getWidth())
	slab.scale(1.25,1);
}
private void expandBall()
{
if(ball.getWidth()+ball.getWidth()*.25<=slab.getWidth())
	ball.scale(1.25);
}
private void shrinkBall()
{
//if(ball.getWidth()-ball.getWidth()*.25>=slab.getWidth())
	ball.scale(.75);
}
private void speedDec()
{
if(delay<40)
	delay=delay + 5/delay;
}
private void speedInc()
{
double dt=delay/5;
if(delay-dt>0)
	delay=delay-dt;
}

/*Differenet cases in the method signify diffrent positions of the ball by which it can
stricke any object case numbers are analogus to poition in a wall clock 
as 12 is top 6 bottom and 7 is actualy 7.5 and 4 = 4.5
Case 6 is treated differently for the case when ball strikes the slab 
slab is decided into 5 section with diffrent throwing angles or values of dx*/

// Contains 12 cases in which ball can colide with object with dircetion reversal code as per required
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
point=0;
}//check fase ends

// Creates a new power called in getPoint function with every case of facing pile object
public int emitPower()
{
double num;
int section=7;
num=probab*100;
random=RandomGenerator.getInstance();
int powerVal=random.nextInt(100);
//Probbablity of creating a random power is num/100 rest specic probbablities are specified in powerAct Method according to value of powerNum
if(powerVal<=num)
{

power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/5.png");

for(int i=0;i<section;i++)
{
if(powerVal>section*i && powerVal<=section*(i+1))	
powerNum=i+1;
}


if(powerVisible || powerNum==7)
{
	switch(powerNum)
	{
	case 1:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/expandSlab.png"); 
		break;	
	case 2:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/shrinkSlab.png"); 
		break;
	case 3:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/expandBall.png"); 
		break;
	case 4:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/shrinkBall.png"); 
		break;
	case 5:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/plus.png"); 
		break;
	case 6:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/minus.png"); 
		break;
	case 7:
		remove(power);
		power = new GImage("file:///E:/work_space/JAVA/BreakIt/images/unBox.png"); 
		break;

}
}
power.setSize(50,50);
add(power,ball.getX(),ball.getY());

return 1;
}
	return 0;	
}

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
		if(power==null)
			emitPower();

	}
	if(obj!=power && obj!=background)
		return 12;
}

if(getElementAt(point3X,point3Y)!=null)
{
	obj=getElementAt(point3X,point3Y);
	if(obj==pile)
	{
		pile.rmv(point3X,point3Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 3;
}

if(getElementAt(point6X,point6Y)!=null)
{
	obj=getElementAt(point6X,point6Y);
	if(obj==pile)
	{
		pile.rmv(point6X,point6Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 6;
}

if(getElementAt(point9X,point9Y)!=null)
{
	obj=getElementAt(point9X,point9Y);
	if(obj==pile)
	{
		pile.rmv(point9X,point9Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 9;
}

if(getElementAt(point1X,point1Y)!=null)
{
	obj=getElementAt(point1X,point1Y);
	if(obj==pile)
	{
		pile.rmv(point1X,point1Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 1;
}

if(getElementAt(point4X,point4Y)!=null)
{
	obj=getElementAt(point4X,point4Y);
	if(obj==pile)
	{
		pile.rmv(point4X,point4Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 4;
}

if(getElementAt(point7X,point7Y)!=null)
{
	obj=getElementAt(point7X,point7Y);
	if(obj==pile)
	{
		pile.rmv(point7X,point7Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
	return 7;
}

if(getElementAt(point10X,point10Y)!=null)
{
	obj=getElementAt(point10X,point10Y);
	if(obj==pile)
	{
		pile.rmv(point10X,point10Y-upMargin);
		brickCount--;
		if(power==null)
			emitPower();
	}
	if(obj!=power && obj!=background)	
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
	GRect brick;    					
	GOval ball;
	int windowW=1400,windowH=660,iww,iwh;
	double brickH,brickW,dx=0,dy=0;
	
	/*Window height and width are initiated as above
	brick height and brick width are defined in setup method
	dx,dy hold speed in x and y dir which are set to 1 in mouse click event 
	howevr dx changs as the ball strikes the slab
	brick count is used to check if the game is finshed or not 
	it is used in finish method and also in move method to display remaining bricks	*/
	
	int brickCount=0,level=1,powerNum=0;
	double delay=5,lastDelay=5,probab=.5;// Probab is the probablity of power occourence manupulated in emitPower Method
	int upMargin=windowH/8;
	int downMargin=windowH/8;
	boolean gameOver=false,fly=false,gamePaused=false,powerPresent=false,powerVisible=false;
	RandomGenerator random;
	/*fly is initaly set to false hat means ball is not flying as the user clicks mouse button
	fly is set to true and also dx & dy are set to 1 Object obj is used in facecheck method in collision method
	to check if the curent object is brick or not*/
	Bricks pile;
	GObject obj;
	GImage power,background,slab;
	GLabel info,info1;
	GLine bar,topBar;
	MouseEvent event;
	// End Declaration	
}//Class Front Ends


