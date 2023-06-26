import Engine.*;
import Engine.Object;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class TestRuang {
        private Window window = new Window(800, 800, "Dummy Main");
        Camera camera = new Camera();
        Projection projection = new Projection(window.getWidth(), window.getHeight());
        Player player ;
        boolean gelap = false;
        int counterLampu =0;
        boolean flashLight = false;
        int counterFlashLight =0;
        SkyBox skyBox ;


        ArrayList<Object> Ruang = new ArrayList<Object>();
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = Arrays.asList(
                new ShaderProgram.ShaderModuleData(
                        "Project Grafkom-22\\Main\\resources\\shaders\\scene.vert", GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData(
                        "Project Grafkom-22\\Main\\resources\\shaders\\scene.frag", GL_FRAGMENT_SHADER)
        );
        Model m = null;
        public void run() {
            init();
            loop();
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();

        }

        public void init() {
            window.init();
            GL.createCapabilities();
            glEnable(GL_DEPTH_TEST);
            glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
            glDepthMask(true);
            glDepthFunc(GL_LEQUAL);
            glDepthRange(0.0f, 1.0f);

            camera.setPosition(0.0f, 2.3f, 0.5f);

            camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(180.0f));

            //skybox
            skyBox = new SkyBox();


            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/mainCharacter.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            player = new Player(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(0f/255, 26f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    10.0f,
                    10f,
                    10f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m);
            player.scaleObject(2.5f,2.5f,2.5f);
            player.rotateObject(1f,0f,0f,0f);


            //Lantai
            Ruang.add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(124f/255, 124f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    20.0f,
                    0.2f,
                    28.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));

            Ruang.get(0).translateObject(0.0f,0.0f,4.0f);

            //Dinding Belakang
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    22.0f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).translateObject(0.0f,6.5f,-10.5f);


            //Dinding Kiri 1
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    16.0f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(0).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(0).translateObject(-10.5f,6.5f,-2.0f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/Door.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //pintu
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ), new Vector4f(104f/255, 42f/255, 0, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(0).getChildObject().get(1).rotateObject((float)Math.toRadians(60.0f), 0.0f, -1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(1).translateObject(-12.7f,1.0f,5.8f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(1).scaleObject(1.0f,1.4f,1.2f);

            //Dinding Kiri 2
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    7.2f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(2).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(2).translateObject(-10.5f,6.5f,14.4f);

            //Dinding Kiri 3
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    4.8f,
                    2.05f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(3).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(3).translateObject(-10.5f,11.9f,8.4f);

            //Dinding Kanan 1
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    13.0f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(4).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(4).translateObject(10.5f,6.5f,-3.5f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/win.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //jendela 1
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
//                new Vector4f(104f/255, 42f/255, 0, 1.0f/255),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(5).scaleObject(2.0f,2.0f,2.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(5).rotateObject((float)Math.toRadians(90.0f), 1.0f, 0.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(5).translateObject(10.5f,6.0f,5.0f);

            //jendela 2
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
//                new Vector4f(104f/255, 42f/255, 0, 1.0f/255),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(6).scaleObject(2.0f,2.0f,2.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(6).rotateObject((float)Math.toRadians(90.0f), 1.0f, 0.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(6).translateObject(10.5f,6.0f,1.0f);

            //Dinding Kanan 2
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    7.0f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(7).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(7).translateObject(10.5f,6.5f,14.5f);

            //Dinding Kanan 3
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    8.0f,
                    3.5f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(8).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(8).translateObject(10.5f,11.2f,7.0f);

            //Dinding Kanan 4
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    8.0f,
                    2.7f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(9).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(9).translateObject(10.5f,1.3f,7.0f);

            //plafon
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(124f/255, 124f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    20.0f,
                    0.2f,
                    28.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(10).translateObject(0.0f,12.9f,4.0f);


            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/lampuPlafon.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            //lampu plafon
            Ruang.get(0).getChildObject().get(0).getChildObject().get(10).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(0).getChildObject().get(10).getChildObject().get(0).scaleObject(2.0f,0.5f,2.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(10).getChildObject().get(0).rotateObject((float)Math.toRadians(180.0f), 1.0f, 0.0f, 0.0f);
            Ruang.get(0).getChildObject().get(0).getChildObject().get(10).getChildObject().get(0).translateObject(0.0f,12.9f,4.0f);



            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/bed.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //Kasur
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
//                new Vector4f(104f/255, 42f/255, 0, 1.0f/255),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(1).scaleObject(4.2f,6.0f,4.5f);
            Ruang.get(0).getChildObject().get(1).translateObject(6.8f,0.0f,-8.8f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/kursi.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //Kursi
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
//                new Vector4f(104f/255, 42f/255, 0, 1.0f/255),
                    new Vector4f(255f/255, 187f/255, 166f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(2).scaleObject(7.0f,6.0f,7.0f);
            Ruang.get(0).getChildObject().get(2).translateObject(4.0f,0.0f,11.8f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/mejaKecil.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //mejaKecil
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(3).rotateObject((float)Math.toRadians(90.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(3).scaleObject(5.0f,7.0f,9.0f);
            Ruang.get(0).getChildObject().get(3).translateObject(-8.7f,0.0f,2.5f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/meja.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //meja belajar
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));
            Ruang.get(0).getChildObject().get(4).rotateObject((float)Math.toRadians(180.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(4).scaleObject(7.0f,7.0f,7.0f);
            Ruang.get(0).getChildObject().get(4).translateObject(4.0f,0.0f,16.3f);


            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/buku.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //Buku
            Ruang.get(0).getChildObject().get(4).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(0f/255, 185f/255, 31f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().get(4).getChildObject().get(0).scaleObject(9.5f,9.5f,9.5f);
            Ruang.get(0).getChildObject().get(4).getChildObject().get(0).translateObject(6.5f,5.0f,17.0f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/lampu.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //lampu tidur
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().get(5).scaleObject(9.0f,4.0f,9.0f);
            Ruang.get(0).getChildObject().get(5).translateObject(-8.5f,3.8f,2.5f);


            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/lemari.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //lemari
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(165f/255, 38f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().get(6).rotateObject((float)Math.toRadians(180.0f), 0.0f, 1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(6).scaleObject(6.0f,6.0f,6.0f);
            Ruang.get(0).getChildObject().get(6).translateObject(-5.0f,0.0f,16.6f);


            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/bulan.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            //bulan
            Ruang.add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 255f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(1).scaleObject(2.0f,2.0f,2.0f);
            Ruang.get(1).translateObject(25.0f,17.0f,9.0f);

            // Mainan bola
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(247f/255, 255f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(7).scaleObject(0.8f,0.8f,0.8f);
            Ruang.get(0).getChildObject().get(7).translateObject(-2.0f,0.9f,3.6f);

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(247f/255, 255f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(8).scaleObject(0.8f,0.8f,0.8f);
            Ruang.get(0).getChildObject().get(8).translateObject(5.0f,0.9f,-6.6f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/toy2.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            // Rubik
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 0f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(9).scaleObject(9.5f,9.5f,9.5f);
            Ruang.get(0).getChildObject().get(9).translateObject(7.0f,0.1f,2.5f);

            Ruang.get(0).getChildObject().get(4).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(255f/255, 0f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(4).getChildObject().get(1).scaleObject(9.5f,9.5f,9.5f);
            Ruang.get(0).getChildObject().get(4).getChildObject().get(1).translateObject(1.0f,5.0f,16.0f);

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/toy1.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            // Mainan tumpuk
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(213f/255, 0f/255, 255f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(10).scaleObject(5.0f,5.0f,5.0f);
            Ruang.get(0).getChildObject().get(10).translateObject(8.0f,0.1f,7.5f);


            //Mainan motor
            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/motorcycle.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            //Mainan motor
            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(0f/255, 0f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(11).scaleObject(2.0f,2.0f,2.0f);
            Ruang.get(0).getChildObject().get(11).translateObject(-5.0f,0.1f,8.5f);

            // tempat sampah
            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/trash.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(126f/255, 138f/255, 149f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));


            Ruang.get(0).getChildObject().get(12).scaleObject(9.0f,9.0f,9.0f);
            Ruang.get(0).getChildObject().get(12).rotateObject((float)Math.toRadians(30.0f), 0.0f, -1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(12).translateObject(9.0f,0.1f,-9.0f);

            //tembok depan
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(178f/255, 169f/255, 124f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    22.0f,
                    13.0f,
                    1.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));

            Ruang.get(0).getChildObject().get(0).getChildObject().get(11).translateObject(0.0f,6.5f,18.5f);

            //Atap
            Ruang.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(List.of(
                    )
                    ),
                    new Vector4f(107f/255, 107f/255, 107f/255, 1.0f/255),0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    28.0f,
                    0.5f,
                    34.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    0));

            Ruang.get(0).getChildObject().get(0).getChildObject().get(12).translateObject(0.0f,13.25f,4.0f);

            //Kunci

            try{
                m = ObjLoader.loadModelwFace(new File("Project Grafkom-22/Main/src/blenderAssets/key.obj"), false);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().add(new Sphere(
                    shaderModuleDataList,
                    new ArrayList<>(
                    ),
                    new Vector4f(232f/255, 216f/255, 0f/255, 1.0f/255),
                    0.0,
                    new ArrayList<>(List.of(0f, 0f, 0f)),
                    0.0f,
                    0.0f,
                    0.0f,
                    15, // Stack -->
                    30, // Sector --> Titik
                    m));

            Ruang.get(0).getChildObject().get(13).scaleObject(1.5f,1.5f,1.5f);
//            Ruang.get(0).getChildObject().get(13).rotateObject((float)Math.toRadians(30.0f), 0.0f, -1.0f, 0.0f);
            Ruang.get(0).getChildObject().get(13).translateObject(0.0f,0.2f,7.0f);

            Ruang.get(0).getChildObject().get(14).scaleObject(1.0f,1.0f,1.0f);
            Ruang.get(0).getChildObject().get(14).translateObject(4.0f,0.2f,15.0f);

            Ruang.get(0).getChildObject().get(15).scaleObject(1.0f,1.0f,1.0f);
            Ruang.get(0).getChildObject().get(15).translateObject(5.0f,0.2f,-9.0f);

            Ruang.get(0).getChildObject().get(16).scaleObject(1.0f,1.0f,1.0f);
            Ruang.get(0).getChildObject().get(16).translateObject(-6.0f,0.2f,-1.0f);

            Ruang.get(0).getChildObject().get(17).scaleObject(1.0f,1.0f,1.0f);
            Ruang.get(0).getChildObject().get(17).translateObject(-9.0f,0.2f,16.0f);

            Ruang.get(0).getChildObject().get(18).scaleObject(1.0f,1.0f,1.0f);
            Ruang.get(0).getChildObject().get(18).translateObject(8.0f,0.2f,-4.0f);

        }


    private float count = 0;
    private boolean hold= false;
    private boolean fromCCTV = false;


    //Menyimpan posisi kamera akhir karakter
//    float tempCamX = camera.getPositionX();
//    float tempCamY = camera.getPositionY();
//    float tempCamZ = camera.getPositionZ();
    Vector3f firsttempCam;
    Vector3f tempCam = new Vector3f(0.0f, 2.3f, 0.5f);
    Vector2f tempRotate = new Vector2f((float) Math.toRadians(0.0f), (float) Math.toRadians(180.0f));

    Vector3f baseCam = new Vector3f(0f, 2.3f, 0f);

    Vector3f baseCharPos = new Vector3f(0.0f, 0.0f, 0.0f);

    public void input() {
        //( 9.949E+0  1.275E+1 -9.942E+0)
//        ( 9.994E+0  1.279E+1  1.798E+1)
//        System.out.println(camera.getPositionZ());


        if (window.isKeyPressed(GLFW_KEY_2) || window.isKeyPressed(GLFW_KEY_3)) {
            hold=true;
        }
        else{
            hold = false;
        }
        //CCTV 1 -- HOLD KEY 2
        if(hold && window.isKeyPressed(GLFW_KEY_2)){
            camera.setPosition(9.949f, 12.7f, -9.942f);
            camera.setRotation((float) Math.toRadians(30), (float) Math.toRadians(220));
            projection.setFOV(1.4f);
            fromCCTV = true;
        }
        //CCTV 2 -- HOLD KEY 3
        else if(hold && window.isKeyPressed(GLFW_KEY_3)) {
            camera.setPosition(9.949f , 12.7f , 16.942f);
            camera.setRotation((float) Math.toRadians(30), (float) Math.toRadians(-50));
            projection.setFOV(1.4f);
            fromCCTV = true;
        }

        else{
            if(fromCCTV) {
                //camera nanti mengikuti posisi player
                projection.setFOV(projection.getBaseProj());
                camera.setPosition(tempCam.x, tempCam.y, tempCam.z);
                camera.setRotation(tempRotate.x, tempRotate.y);
                fromCCTV = false;

            }
        }

        //get posisi camera pada karakter
        Vector3f characterPos = player.getPosition();
        //asumsi kamera dan karakter ga bisa naik dan turun
        Vector3f cameraPosition = new Vector3f(characterPos.x + baseCam.x, baseCam.y, characterPos.z + baseCam.z);

        if (window.isKeyPressed(GLFW_KEY_W)) {
            Vector3f forward = camera.getForwardVector().mul(player.getCurrSpeed());
            Vector3f newPosition = player.getPosition().add(forward);
            //karakter ga bisa naik turun
            player.setPosition(newPosition.x, baseCharPos.y, newPosition.z);

            // Mengatur posisi kamera sesuai objectnya
            camera.setPosition(cameraPosition.x,cameraPosition.y,cameraPosition.z);
            //simpan posisi untuk pindah dari cctv
            tempCam = new Vector3f(cameraPosition.x,cameraPosition.y,cameraPosition.z);


        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            Vector3f backward = camera.getForwardVector().mul(-player.getCurrSpeed());
            Vector3f newPosition = player.getPosition().add(backward);
            player.setPosition(newPosition.x, baseCharPos.y, newPosition.z);

            camera.setPosition(cameraPosition.x,cameraPosition.y,cameraPosition.z);

            tempCam = new Vector3f(cameraPosition.x,cameraPosition.y,cameraPosition.z);

        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            Vector3f left = camera.getRightVector().mul(-player.getCurrSpeed());
            Vector3f newPosition = player.getPosition().add(left);
            player.setPosition(newPosition.x, baseCharPos.y, newPosition.z);

            camera.setPosition(cameraPosition.x,cameraPosition.y,cameraPosition.z);


            tempCam = new Vector3f(cameraPosition.x,cameraPosition.y,cameraPosition.z);

        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            Vector3f right = camera.getRightVector().mul(player.getCurrSpeed());
            Vector3f newPosition = player.getPosition().add(right);
            player.setPosition(newPosition.x, baseCharPos.y, newPosition.z);

            camera.setPosition(cameraPosition.x,cameraPosition.y,cameraPosition.z);


            tempCam = new Vector3f(cameraPosition.x,cameraPosition.y,cameraPosition.z);
        }
        //update matrix player
        player.updateModelMatrix();

        //Kontrol Player
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveForward(0.2f);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_ALT)) {
            camera.moveBackwards(0.2f);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(0.2f);
        }

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(0.2f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.moveLeft(0.2f);
        }

        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.moveRight(0.2f);
        }

        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
//            camera.addRotation(tempRotate.x, (float) Math.toRadians(displayVec.y * 0.1f));
            camera.addRotation((float) Math.toRadians(displayVec.x * 0.1f), (float) Math.toRadians(displayVec.y * 0.1f));

            tempRotate = new Vector2f (camera.getRotationX(), camera.getRotationY());
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()-(window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());

        }

        if(window.isKeyPressed(GLFW_KEY_Z)){
            camera.rotateObjectCamera(0f,(float)(Math.toRadians(1)));
        }
        if(window.isKeyPressed(GLFW_KEY_X)){
            camera.rotateObjectCamera(0f,(float)(Math.toRadians(-1)));
        }
        if(window.isKeyPressed(GLFW_KEY_C)){
            camera.rotateObjectCamera((float)(Math.toRadians(1)),0);
        }
        if(window.isKeyPressed(GLFW_KEY_V)){
            camera.rotateObjectCamera((float)(Math.toRadians(-1)),0);
        }
        if(window.isKeyPressed(GLFW_KEY_COMMA))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(-1f));
        }
        if(window.isKeyPressed(GLFW_KEY_SLASH))
        {
            camera.addRotation((float) Math.toRadians(0f), (float) Math.toRadians(1f));
        }
        if(window.isKeyPressed(GLFW_KEY_CAPS_LOCK))
        {
            camera.addRotation((float) Math.toRadians(1f), (float) Math.toRadians(0f));
        }
        if(window.isKeyPressed(GLFW_KEY_TAB))
        {
            camera.addRotation((float) Math.toRadians(-1f), (float) Math.toRadians(0f));
        }

//        camera.printPosition();

        if(window.isKeyPressed(GLFW_KEY_P)){
            if (counterLampu == 60) {
                if (!gelap) {
                    gelap = true;
                } else gelap = false;
                counterLampu=0;
            }
            else {
                counterLampu+= 1;
            }
        }

        if(window.getMouseInput().isRightButtonPressed()){
            if(counterFlashLight == 60){
                if (!flashLight) {
                    flashLight = true;
                } else flashLight = false;
                counterFlashLight=0;
            }
            else {
                counterFlashLight+= 1;
            }
        }
    }

    public void loop() {

        while (window.isOpen()) {
            window.update();
            glClearColor(0f,0f,0f, 1.0f); // RapidTables.com (RGB color code chart)
            GL.createCapabilities();
            glClearDepth(1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            input();
            player.draw(camera, projection,gelap,flashLight);

            for (Object obj3D : Ruang) {
                obj3D.draw(camera, projection,gelap,flashLight);
            }
            skyBox.draw(camera,projection);

//            System.out.println("Cam X"+camera.getPosition().get(0));
//            System.out.println("Cam Y"+camera.getPosition().get(1));
//            System.out.println("Cam Z"+camera.getPosition().get(2));
            //Restore State
            glDisableVertexAttribArray(0);
            // Pull for window events
            // The key callback above will only be
            // invoked during this call
            glfwPollEvents();
        }
    }


    public static void main (String[]args){
        new TestRuang().run();
    }
}
