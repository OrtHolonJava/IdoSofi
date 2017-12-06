package images;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The Img Class (Represents an image)- 
 */
public class Img
{
	private Image _image;
	private int _x, _y, _width, _height;
	
	/**
	 * The Constructor Method - 
	 * Initializes an instance of the class with the given attributes.
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Img(String path, int x, int y, int width, int height)
	{
		_image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage(); 
		
		this.setImgCords(x, y);
		this.setImgSize(width, height);
	}
	
	/**
	 * Method: Draws the image.
	 * @param g
	 */
	public void drawImg(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
       	g2d.drawImage(_image, _x, _y, _width, _height, null);
	}
	
	/**
	 * Method: Sets the image's cords to the given cords.
	 * @param x
	 * @param y
	 */
	public void setImgCords(int x, int y)
	{
		this._x = x;
		this._y = y;
	}
	
	/**
	 * Sets the image's size in accordance to the given dimensions.
	 * @param width
	 * @param height
	 */
	public void setImgSize(int width, int height)
	{
		this._width = width;
		this._height = height;
	}
	
	/**
	 * Sets the actual image of this instance.
	 * @param image
	 */
	public void setImg(Image image)
	{
		_image = image;
	}
}


