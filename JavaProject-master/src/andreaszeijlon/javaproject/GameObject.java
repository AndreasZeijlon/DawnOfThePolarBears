package andreaszeijlon.javaproject;

import java.awt.*;

/**
 * Created by Andreas Zeijlon on 2015-05-04.
 */
public interface GameObject {
    int getX();
    int getY();
    Image getImage();
    void setImage(Image image);
    int getDamage();
    void setDamage(int damage);
    double getAngle();
    void setAngle(double angle);

}
