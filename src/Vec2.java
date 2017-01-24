/**
 * Created by rei on 11/20/2016.
 */
public class Vec2 {
    float x;
    float y;

    public Vec2(float x,float y){
        this.x = x;
        this.y = y;
    }

    public Vec2(){
        x = 0; y = 0;
    }

    public Vec2(Vec2 other){
        x = other.x;
        y = other.y;
    }
    public Vec2 add(Vec2 other){
        return new Vec2(x+other.x, y +other.y);
    }
    public Vec2 sub(Vec2 other){
        return new Vec2(x-other.x, y -other.y);
    }
    public float dot(Vec2 other){
        return (x * other.x ) + (y * other.y);
    }

    public Vec2 mul(float c){
        return new Vec2(x*c, y*c);
    }

    public Vec2 rotate(float deg){
        return new Vec2((float)(Math.cos(deg) * x + -Math.sin(deg)* y), (float)(Math.sin(deg) * x+Math.cos(deg) * y));
    }

    public Vec2 norm(){
        float magn = mag();
        return new Vec2(x / magn, y / magn);
    }

    public float mag(){
        return (float)Math.sqrt(this.x*this.x + this.y*this.y);
    }

    public float magsqr(){
        return this.x*this.x + this.y*this.y;
    }

    public Vec2 getPerpendicular(){
        return new Vec2(-this.y, this.x);
    }
}
