package de.guruz.p300;

import de.guruz.guruzsplash.interfaces.GuruzsplashManager;
import de.guruz.guruztray.interfaces.GuruztrayManager;
import de.guruz.p300.logging.D;

import java.net.URL;

/**
 * Created by Leo on 01/03/15.
 */
public class MainInterface {

    //
    private static MainInterface instance = new MainInterface();

    private MainInterface() {
    }

    public static MainInterface getInstance() {
        return instance;
    }
    /* ATTRIBUTES */

    //
    // SIGNLETON DESIGN
    // 
    
    private GuruzsplashManager guruzsplashManager;
    
    /**
     *
     *
     * @author guruz
     * @see de.guruz.guruztray.interfaces.GuruztrayManager
     * @see #createTrayIcon()
     */
    GuruztrayManager guruztrayManager = null;

    //PRIVATE METHOD
    /**
     * Hide the splash screen after one second
     *
     * @author guruz
     * @see #guruzsplashManager
     * @see #initSplashScreen()
     */
    public void hideSplashScreen() {
        if (this.guruzsplashManager != null) {
            try {
                Thread.sleep(1000);
                this.guruzsplashManager.hide();
            } catch (Throwable t) {
                // e.printStackTrace();
                D.out("Splashscreen: " + t.toString());
            }

        }
    }

    //

    /**
     * Initialize and show the splash screen
     *
     * @return True: Splash is visible; False: Splash not visible (might be no
     *         GUI)
     * @author guruz
     * @see #guruzsplashManager
     * @see #hideSplashScreen()
     */
    public boolean initSplashScreen() {
        try {
            String className = "de.guruz.guruzsplash.implementation.GuruzsplashManagerImplementation";
            Class<?> c = Class.forName(className);
            Object o = c.newInstance();
            this.guruzsplashManager = (GuruzsplashManager) o;
            //this.guruzsplashManager.setImageURL(new URL("./splash.png"));
            System.out.println("Ca devrait afficher l'image");
            if (this.guruzsplashManager.isVisible()) {
                return true;
            } else {
                this.guruztrayManager = null;
                return false;
            }

        } catch (Exception t) {
            D
                    .out("No splash screen supported on this platform or java version" + t.getMessage());
            this.guruzsplashManager = null;
            return false;
        }
    }
}
