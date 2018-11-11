package solids;

import transforms.Point3D;

public class Cube extends SolidData {


    public Cube(double size) {

        // BODY
        verticies.add(new Point3D(0,0,0));            //0 - A
        verticies.add(new Point3D(size,0,0));            //1 - B
        verticies.add(new Point3D(size,size,0));            //2 - C
        verticies.add(new Point3D(size,size,size));            //3 - G
        verticies.add(new Point3D(0,size,size));            //4 - H
        verticies.add(new Point3D(size,0,size));            //5 - F
        verticies.add(new Point3D(0,size,0));            //6 - D
        verticies.add(new Point3D(0,0,size));            //7 - E


        //USEČKY
        indicies.add(0); indicies.add(1); // Usečka a - b
        indicies.add(0); indicies.add(6); // Usečka a - d
        indicies.add(0); indicies.add(7); // Usečka a - e

        indicies.add(1); indicies.add(2); // Usečka b - c
        indicies.add(1); indicies.add(5); // Usečka b - f

        indicies.add(2); indicies.add(3); // Usečka c - g
        indicies.add(2); indicies.add(6); // Usečka c - d

        indicies.add(6); indicies.add(4); // Usečka d - h

        indicies.add(7); indicies.add(5); // Usečka e - f
        indicies.add(5); indicies.add(3); // Usečka f - g
        indicies.add(3); indicies.add(4); // Usečka g - h
        indicies.add(4); indicies.add(7); // Usečka h - e
    }
}
