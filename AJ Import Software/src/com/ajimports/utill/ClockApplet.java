package com.ajimports.utill;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;



public class ClockApplet extends JApplet implements Runnable {
	static final long serialVersionUID = 101;
    final static String version = "V1.8";
    Label         loadLabel;
    int           centerX = 64;
    int           centerY = 64;
    int           shadowX = 0;   
    int           shadowY = 0;
    int           handScale = 44;
    Color         BGColor = Color.white;
    String        BGImageName = "Image.png";

    Image         BGImage;
    BufferedImage buffer;
    Graphics2D    gBuffer;
    BufferedImage handsBuffer;
    Graphics2D    gHandsBuffer;
    BufferedImage shadowBuffer;

    int           w;
    int           h;

    Timer         timer;
    ClockTask     clockTask = new ClockTask (this);
    GregorianCalendar calendar;
    TimeZone      tz;
    Date          date;
    int           hour;
    int           minute;
    int           second = -1;
    int           drawnHour   = -1;
    int           drawnMinute = -1;
    int           drawnSecond = -1;

    SecondHand    secondHand;
    MinuteHand    minuteHand;
    HourHand      hourHand;

    /* We use a filter matrix to create a drop shadow of the hands in order
     * to create a more realistic look.
     */
    float[] filterMatrix = {
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f,
         0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f, 0.01f
    };
    Kernel filterKernel = new Kernel (9, 9, filterMatrix);
    ConvolveOp convolve = new ConvolveOp (filterKernel, ConvolveOp.EDGE_NO_OP, null);

    float [][] combineMatrix = {
        { 0.0f, 0.0f, 0.0f, -1.0f, 255.0f },
        { 0.0f, 0.0f, 0.0f, -1.0f, 255.0f },
        { 0.0f, 0.0f, 0.0f, -1.0f, 255.0f },
        { 0.0f, 0.0f, 0.0f,  0.8f,   0.0f }
    };

    BandCombineOp combineOp = new BandCombineOp (combineMatrix, null);
    WritableRaster shadowRaster;

    boolean       initialized = false;
    boolean       running = false;



    public synchronized void init () {
        int i = 0;
        loadLabel = new Label ("Initializing ...", Label.CENTER);
        getContentPane ().add ("Center", loadLabel);

        try {
                      
            centerX = 64;
            centerY = 64;
            shadowX = 2;
            shadowY = 1; 
            handScale =44;
            BGColor = new Color(0,78,152);
            	
        }
        catch (NumberFormatException e) {
            System.out.println ("Wrong number format: " + e.getMessage ());
            System.out.println ("Using default value.");
        }

       
        BGImageName = "Images/Image.png";


        
        
         tz = TimeZone.getDefault();
        
        getContentPane ().remove (loadLabel);
        loadLabel = new Label ("Loading background image ...", Label.CENTER);
        getContentPane ().add ("Center", loadLabel);

        while (i < 3 && !loadImage ()) {
            i++;
        }

        w = BGImage.getWidth (this);
        h = BGImage.getHeight (this);
        if (w <= 0 || h <= 0) {
            loadLabel.setText ("Image '" + BGImageName + "' not found.");
            System.out.println ("Error: Cannot load image '" + BGImageName + "'.");
            return;
        }
        getContentPane ().remove (loadLabel);

        buffer = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB);
        gBuffer = buffer.createGraphics ();
        if (BGColor != null) {
            gBuffer.setBackground (BGColor);
        }

        handsBuffer = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB);
        gHandsBuffer = handsBuffer.createGraphics ();
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                       RenderingHints.VALUE_ANTIALIAS_ON);
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_RENDERING,
                                       RenderingHints.VALUE_RENDER_QUALITY);
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_INTERPOLATION,
                                       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_COLOR_RENDERING,
                                       RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_ALPHA_INTERPOLATION,
                                       RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        gHandsBuffer.setBackground (new Color (0, 0, 0, 0));

        if (shadowX != 0 || shadowY != 0) {
            shadowBuffer = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB);
        }

        secondHand = new SecondHand (handScale);
        minuteHand = new MinuteHand (handScale);
        hourHand   = new HourHand   (handScale);

        calendar = new GregorianCalendar (tz);
        date = new Date ();
        calendar.setTime (date);
        if (!tz.useDaylightTime () && tz.inDaylightTime (date)) {
            calendar.roll (Calendar.HOUR, true);
        }
        hour        = calendar.get (Calendar.HOUR);
        minute      = calendar.get (Calendar.MINUTE);
        second      = calendar.get (Calendar.SECOND);
        
        initialized = true;
        
    } // End of init ().


    public boolean loadImage () {
        BGImage = Toolkit.getDefaultToolkit().getImage (BGImageName);
        if (BGImage == null) {
            loadLabel.setText ("Image '" + BGImageName + "' not found.");
            System.out.println ("Error: Image '" + BGImageName + "' not found.");
            return false;
        }
        try {
            MediaTracker  tracker = new MediaTracker (this);
            tracker.addImage (BGImage, 0);

            loadLabel.setText ("Loading background image ...");

            try {
                tracker.waitForID (0);
            } catch (InterruptedException e) {
                return false;
            }

        } catch (Exception e) {
            System.out.println ("Error in loadImage ()");
            System.out.println (e.getMessage ());
            loadLabel.setText (e.getMessage ());
            return false;
        }

        return true;
    } // End of loadImage ().


    /* For setting the background color from a JavaScript
     * in the HTML page:
     */
    public synchronized void setBGColor (String sBGColor) {
        if (sBGColor != null) {
            try {
                BGColor = Color.decode (sBGColor);
                
            }
            catch (NumberFormatException e) {
                System.out.println ("Wrong number format: " + e.getMessage ());
                System.out.println ("Background color not changed.");
            }
            gBuffer.setBackground (BGColor);
            System.out.println ("Background color changed to \"" + sBGColor + "\".");
        }
    } // End of setBGColor ().

    // Return Applet information
    public String getAppletInfo () {
        return "Siemens Clock by Stefan Oskamp";
    } // End of getAppletInfo ().


    public void start () {
        if (initialized && !running) {
            timer = new Timer ();
            timer.scheduleAtFixedRate (clockTask, 0, 1000);
            running = true;
        }
    } // End of start ().


    public void stop () {
        if (running) {
            timer.cancel ();
            running = false;
        }
    } // End of stop ().


    /* One-time action that is triggered once every second
     * by a timer:
     */
    public void run () {
        try {
            date = new Date ();
            calendar.setTime (date);
            if (!tz.useDaylightTime () && tz.inDaylightTime (date)) {
                calendar.roll (Calendar.HOUR, true);
            }

            hour   = calendar.get (Calendar.HOUR);
            minute = calendar.get (Calendar.MINUTE);
            second = calendar.get (Calendar.SECOND);

            repaint ();
        } catch (Exception e) {
            System.out.println (e.getMessage ());
        } // End try/catch.
    } // End of run ().


    /* paint () paints the clock. It is either called by the
     * system when something needs to be repainted or by our
     * timer task (once every second).
     */
    public synchronized void paint (Graphics g) {
        if (!initialized) return;
        if (getSize ().width <= 0 || getSize ().height <= 0) return;
        if (!isShowing ()) return;

        Graphics2D gScreen = (Graphics2D) g;

        /* Clear the hands buffer:
         * Note: clearRect() does not work with antialias on in Java 1.4.
         * (See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4720890)
         */
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                       RenderingHints.VALUE_ANTIALIAS_OFF);
        gHandsBuffer.clearRect (0, 0, w, h);
        gHandsBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                       RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform savedTransform = gHandsBuffer.getTransform();

        gHandsBuffer.translate (centerX, centerY); // Center hands.

        /* Rotate all hands:
         */
        hourHand.setToRotation (hour, minute);
        minuteHand.setToRotation (minute);
        secondHand.setToRotation (second);

        /* Draw the hands:
         */
        hourHand.  draw (gHandsBuffer);
        minuteHand.draw (gHandsBuffer);
        secondHand.draw (gHandsBuffer);

        drawnHour   = hour;
        drawnMinute = minute;
        drawnSecond = second;

        gHandsBuffer.setTransform (savedTransform);

        /* Create the shadow:
         */
        if (shadowX != 0 || shadowY != 0) {
            convolve.filter (handsBuffer, shadowBuffer);
            combineOp.filter (shadowBuffer.getRaster (), shadowBuffer.getRaster ());
        }

        /* Compose the total image off-screen:
         */
        gBuffer.clearRect (0, 0, w, h);
        gBuffer.drawImage (BGImage, 0, 0, this);

        /* Draw the shadow:
         */
        if (shadowX != 0 || shadowY != 0) {
            savedTransform = gHandsBuffer.getTransform();
            gBuffer.translate (shadowX, shadowY);
            gBuffer.drawImage (shadowBuffer, 0, 0, this);
            gBuffer.setTransform (savedTransform);
        }

        gBuffer.drawImage (handsBuffer, 0, 0, this);

        /* Draw onto the screen:
         */
        gScreen.drawImage (buffer, null, 0, 0);
    } 


   
    class ClockTask extends TimerTask {
        Runnable runner; 
        public ClockTask (Runnable runner) {
            this.runner = runner;
        }

        public void run () {
            runner.run();
        }
    } 
    class Hand {
        protected GeneralPath     path = new GeneralPath ();
        protected int             scale = 100;
        protected AffineTransform rotation = new AffineTransform ();
        protected Color           fillColor = new Color (0x20, 0x20, 0x20); //Color.black;
        protected Color           drawColor = fillColor;
        protected BasicStroke     stroke = new BasicStroke (1.0f,
                                                            BasicStroke.CAP_ROUND,
                                                            BasicStroke.JOIN_ROUND);

        public Hand (int scale) {
            this.scale = scale;
        }

        public Shape getRotatedShape () {
            return path.createTransformedShape (rotation);
        }

        public void draw (Graphics2D g) {
            Shape shape = path.createTransformedShape (rotation);
            g.setPaint (fillColor);
            g.fill (shape);
            g.setColor (drawColor);
            g.setStroke (stroke);
            g.draw (shape);
        }
    } 
    class SecondHand extends Hand {

        public SecondHand (int scale) {
            super (scale);
            drawColor = Color.red;
            fillColor = Color.red;
            path.append (new Ellipse2D.Float (-0.020f, -0.020f, 0.040f, 0.040f), false);
            path.moveTo (0, -0.020f);
            path.lineTo (0, -1);
            path.transform (AffineTransform.getScaleInstance (scale, scale));
        }

        public void setToRotation (int second) {
            rotation.setToRotation ((double) second * Math.PI / 30.0);
        }
    }


    
    class MinuteHand extends Hand {

        public MinuteHand (int scale) {
            super (scale);
            path.moveTo  (0.000f,  0.230f);
            path.curveTo (0.030f,  0.230f,  0.030f,  0.230f,  0.060f,  0.220f);
            path.curveTo (0.050f,  0.190f,  0.040f,  0.090f,  0.040f,  0.050f);
            path.curveTo (0.080f,  0.030f,  0.080f, -0.030f,  0.040f, -0.050f);
            path.lineTo  (0.060f, -0.480f);
            path.lineTo  (0.000f, -1.000f);  // Minute hand extends to -1.0 * scale
            path.lineTo  (0.000f, -0.020f);
            path.curveTo (0.030f, -0.020f,  0.030f,  0.020f,  0.000f,  0.020f);
            path.lineTo  (0.000f,  0.230f);
            path.append  (AffineTransform.getScaleInstance (-1, 1).
                          createTransformedShape (path), true);
            path.transform (AffineTransform.getScaleInstance (scale, scale));
        } 

        public void setToRotation (int minute) {
            rotation.setToRotation ((double) minute * Math.PI / 30.0);
        }
    } 
    class HourHand extends Hand {

        public HourHand (int scale) {
            super (scale);
            path.moveTo  (0.000f,  0.230f);
            path.curveTo (0.030f,  0.230f,  0.030f,  0.230f,  0.060f,  0.220f);
            path.curveTo (0.050f,  0.190f,  0.040f,  0.090f,  0.040f,  0.050f);
            path.curveTo (0.080f,  0.030f,  0.080f, -0.030f,  0.040f, -0.050f);
            path.lineTo  (0.040f, -0.360f);
            path.curveTo (0.120f, -0.380f,  0.132f, -0.460f,  0.100f, -0.520f);
            path.lineTo  (0.000f, -0.680f);
            path.lineTo  (0.000f, -0.020f);
            path.curveTo (0.030f, -0.020f,  0.030f,  0.020f,  0.000f,  0.020f);
            path.lineTo  (0.000f,  0.230f);
            path.append  (AffineTransform.getScaleInstance (-1, 1).
                          createTransformedShape (path), true);
            path.transform (AffineTransform.getScaleInstance (scale, scale));
        } 

        public void setToRotation (int hour, int minute) {
            rotation.setToRotation (((double) hour + (double) minute / 60.0) *
                                    Math.PI / 6.0);
        }
    } 
} 


