package app.entities;

import java.util.List;

public class Carport
{
    private final int HEIGHT = 0;
    private final int WIDTH = 0;
    private final int LENGTH = 0;
    private boolean hasShed;
    private RoofType Rooftype;
    public List<Material>materialList;
    public Carport(){}
    public int calcCarportMaterial(){}
    public calcCarportPrice(){}
    public Material calcPosts(Material material){}
    public Material calcBeams(Material material){}
    public Material calcRafters(Material material){}
    public int getPosts();
    public int getBeams();
    public int getRafters();
}
