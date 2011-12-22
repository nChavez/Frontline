/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.
 */
package frontline.model.util;

import frontline.model.gfx.Bullets;
import frontline.model.gfx.core.tiles.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.awt.image.RGBImageFilter;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;


/**
 * TODO: type comment.
 *
 * @version Aug 17, 2011, submitted by Nick Chavez
 */
public class Services {
    
    public static int topInset = 0;
    public static int leftInset = 0;
    public static int bottomInset = 0;
    public static int rightInset = 0;
    public static Bullets enemyBullets;
    public static Vector2 playerPos;
    public static Map grid;
    
    public static double getAngle(double x1, double y1, double x2, double y2){
       double ans = 75;
       double radAngle;
       
       double x_dist = x2 - x1;
       double y_dist = y2 - y1;
       
       radAngle = Math.atan2(x_dist, y_dist);
       
       ans = (int)((radAngle * 180) / Math.PI);
       ans = ans + 180;
       ans = ans * -1;
       ans = ans + 360;
       
       return ans;
   }
    
    public static Image makeColorTransparent
    (Image im, final Color color) {
    ImageFilter filter = new RGBImageFilter() {
      // the color we are looking for... Alpha bits are set to opaque
      public int markerRGB = color.getRGB() | 0xFF000000;
      
      public final int filterRGB(int x, int y, int rgb) {
        if ( ( rgb | 0xFF000000 ) == markerRGB ) {
          // Mark the alpha bits as zero - transparent
          return 0x00FFFFFF & rgb;
        }
        else {
          // nothing to do
          return rgb;
        }
      }
    }; 
    
    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(ip);
  }
  
  public static BufferedImage toBufferedImage(Image image) {
    
    if (image instanceof BufferedImage) {
      return (BufferedImage) image;
    }
    
    // This code ensures that all the pixels in the image are loaded
    image = new ImageIcon(image).getImage();
    
    // Determine if the image has transparent pixels
    boolean hasAlpha = false;
    
    // Create a buffered image with a format that's compatible with the
    // screen
    BufferedImage bimage = null;
    GraphicsEnvironment ge = GraphicsEnvironment
      .getLocalGraphicsEnvironment();
    try {
      // Determine the type of transparency of the new buffered image
      int transparency = Transparency.OPAQUE;
      if (hasAlpha == true) {
        transparency = Transparency.BITMASK;
      }
      
      // Create the buffered image
      GraphicsDevice gs = ge.getDefaultScreenDevice();
      GraphicsConfiguration gc = gs.getDefaultConfiguration();
      bimage = gc.createCompatibleImage(image.getWidth(null), image
                                          .getHeight(null), transparency);
    } catch (HeadlessException e) {
    } // No screen
    
    if (bimage == null) {
      // Create a buffered image using the default color model
      int type = BufferedImage.TYPE_INT_RGB;
      if (hasAlpha == true) {
        type = BufferedImage.TYPE_INT_ARGB;
      }
      bimage = new BufferedImage(image.getWidth(null), image
                                   .getHeight(null), type);
    }
    
    // Copy image to buffered image
    Graphics g = bimage.createGraphics();
    
    // Paint the image onto the buffered image
    g.drawImage(image, 0, 0, null);
    g.dispose();
    
    return bimage;
  }
  
  public static boolean hasAlpha(Image image) {
    // If buffered image, the color model is readily available
    if (image instanceof BufferedImage) {
      return ((BufferedImage) image).getColorModel().hasAlpha();
    }
    
    // Use a pixel grabber to retrieve the image's color model;
    // grabbing a single pixel is usually sufficient
    PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
    try {
      pg.grabPixels();
    } catch (InterruptedException e) {
    }
    
    // Get the image's color model
    return pg.getColorModel().hasAlpha();
  }
  
  public static BufferedImage crop(BufferedImage src, Rectangle rect) { 
    BufferedImage dest = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_INT_RGB); 
    Graphics g = dest.getGraphics();        
    g.drawImage(src, 0, 0, (int)rect.getWidth(), (int)rect.getHeight(), (int)rect.getX(), (int)rect.getY(), (int)(rect.getX() + rect.getWidth()), (int)(rect.getY() + rect.getHeight()), null);   
    g.dispose();   
    return dest;     
  } 
  
  public static Image loadImage(URL url){
      return new ImageIcon(url).getImage();
  }
  
  public static double randomize(double angle, int accuracy){
      double ans = angle;
      double offset = (new Random()).nextDouble();
      offset = offset * 2;
      offset = offset - 1;
      offset = offset * (5 - (accuracy - 1));
      ans = angle + offset;    
      return ans;
  }
  
  
}
