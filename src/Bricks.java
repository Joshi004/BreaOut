import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;
import java.awt.*;

public class Bricks extends GCompound
{
	RandomGenerator temp;//For random color
	int brickR,brickC;
	int x,y,brickCount,BRow;
	GRect brick;

public Bricks(double brickW,double brickH,int level)
{
	temp=RandomGenerator.getInstance();// For random colour

	
switch (level)
{
case 1:
	brickR=10;
	brickC=16;
	
	for(int i=0;i<brickR;i++)
	{
		for(int j=0;j<brickC;j++)
		{	
			brick=new GRect(brickW,brickH);
			brick.setFilled(true);
			brick.setFillColor(temp.nextColor());
			if(j%2==0)
			{
			add(brick,x,y);
			brickCount++;
			}
		x+=brickW;
		}
	x=0;
	y+=brickH;
	}	
break;//case 1 ends

case 2:
	brickR=10;
	brickC=16;
	
	for(int i=0;i<brickR;i++)
	{
		for(int j=0;j<brickC;j++)
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
break;//case 1 ends



}// Case scope ends
	
	
	}

public int getCount()
{
return brickCount;	
}

public int getextentY()
{
return y;	
}

public int rmv(double x,double y)
{
remove(getElementAt(x,y));
	brickCount--;
	return brickCount;	
}




public void setCount(int num)
{
brickCount=num;	
}


}