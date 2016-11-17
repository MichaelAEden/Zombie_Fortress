package animation;

public class BackgroundObject 
{
	
	protected double distanceFromViewer;
	
	protected Animation image;
	
	protected double x;
	protected double y;

	public BackgroundObject(double x, double y, double distanceFromViewer) 
	{
		this.x = x;
		this.y = y;
		this.distanceFromViewer = distanceFromViewer;
	}
	
	public void setPosition(double x, double y) 
	{
		this.x = x * distanceFromViewer;
		this.y = y;
	}
}
