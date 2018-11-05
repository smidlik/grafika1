package solids;

import transforms.Point3D;

public class Cube extends SolidData {


    public Cube(double size) {
        verticies.add(new Point3D(0,0,0));            //0
        verticies.add(new Point3D(size,0,0));            //1
        verticies.add(new Point3D(size,size,0));            //2
        verticies.add(new Point3D(size,size,size));            //3
        verticies.add(new Point3D(0,size,size));            //4
        verticies.add(new Point3D(size,0,size));            //5
        verticies.add(new Point3D(0,size,0));            //6
        verticies.add(new Point3D(0,0,size));            //7


        //USEČKY
        indicies.add(0); indicies.add(1);
        indicies.add(0); indicies.add(6);
        indicies.add(0); indicies.add(7);

        indicies.add(1); indicies.add(2);
        indicies.add(1); indicies.add(5);

        indicies.add(2); indicies.add(3);
        indicies.add(2); indicies.add(6);

        indicies.add(6); indicies.add(4);
        indicies.add(0); indicies.add(0);

        indicies.add(0); indicies.add(1);
        indicies.add(0); indicies.add(1);
        indicies.add(0); indicies.add(1);
    }
}
