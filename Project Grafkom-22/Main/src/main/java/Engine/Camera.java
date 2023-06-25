package Engine;

import org.joml.*;

public class Camera {

    private Vector3f direction;
    private Vector3f position;
    private Vector3f right;
    private Vector2f rotation;
    private Vector3f up;
    private Matrix4f viewMatrix;

    public Camera() {
        direction = new Vector3f();
        right = new Vector3f();
        up = new Vector3f();
        position = new Vector3f();
        viewMatrix = new Matrix4f();
        rotation = new Vector2f();
    }

    public void addRotation(float x, float y) {
        rotation.add(x, y);
        recalculate();
    }

    public Vector3f getForwardVector() {
        return new Vector3f(-viewMatrix.m20(), -viewMatrix.m21(), -viewMatrix.m22()).normalize();
    }

    public Vector3f getRightVector() {
        return new Vector3f(viewMatrix.m00(), viewMatrix.m01(), viewMatrix.m02()).normalize();
    }

    public void setOrientation(Vector3f forwardVector, Vector3f upVector) {
        direction.set(forwardVector).normalize();
        up.set(upVector).normalize();
        right.set(direction).cross(up).normalize();
        recalculate();
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }
    public float getPositionZ() {
        return position.z;
    }

    public float getRotationX(){
        return rotation.x;
    }
    public float getRotationY(){
        return rotation.y;
    }


    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void moveBackwards(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.sub(direction);
        recalculate();
    }

    public void moveDown(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.sub(up);
        recalculate();
    }

    public void moveForward(float inc) {
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.add(direction);
        recalculate();
    }

    public void moveLeft(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.sub(right);
        recalculate();
    }

    public void moveRight(float inc) {
        viewMatrix.positiveX(right).mul(inc);
        position.add(right);
        recalculate();
    }

    public void moveUp(float inc) {
        viewMatrix.positiveY(up).mul(inc);
        position.add(up);
        recalculate();
    }

    private void recalculate() {
        viewMatrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }

    public void setPositionTemp(Vector3f tempCam) {
        position = tempCam;
        recalculate();
    }



    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void rotateObjectCamera(float x, float y){
        viewMatrix.rotateX(x).rotateY(y);
    }

    public void printPosition(){
        System.out.println("X : "+position.x+" Y : "+position.y+" Z : "+position.z);
    }



}