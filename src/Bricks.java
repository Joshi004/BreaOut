import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;
import java.awt.*;

public class Bricks extends GCompound
{
	RandomGenerator temp;//For random color
	int x=0,y,brickCount,BRow;
	int brickR=12,brickC=16;
	GRect brick;

public Bricks(double brickW,double brickH)
{
	temp=RandomGenerator.getInstance();// For random colour
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