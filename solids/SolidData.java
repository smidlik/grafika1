package solids;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public abstract class SolidData implements Solid{

    protected List<Point3D> verticies;
    protected List<Integer> indicies;

    @Override
    public List<Point3D> getVerticies() {
        return verticies;
    }

    @Override
    public List<Integer> getIndicies() {
        return indicies;
    }

    public SolidData() {
        this.verticies = new ArrayList<>();
        this.indicies = new ArrayList<>();
    }
}
