import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main {


    private static int height;
    private static int width;
    private static int png[][];

    public static void main(String[] args) throws IOException {
        int size = 4;
        Point[] points = new Point[8*size];
        int count = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) points[i] = new Point();

//U have for test:    PhotoTest.png    RealTest.png    Test.png    Test64_64.png   Test160_200.png    Test1000_800.png  allPlata.jpeg    All_Plate.png  All_Plate_Test All_Plate_Test (1)  All_Plate_Test2greb
        // All_Plate_Test2greb (1)  All_Plate_Test2greb (2)  All_Plate_Test3greb  All_Plate_Test3greb (1)  All_Plate_Test4greb
        try{
            File f = new File("src/All_Plate_Test4greb.png");
            BufferedImage input = ImageIO.read(f);
            width = input.getWidth();
            height = input.getHeight();
            png = new int[width][height];

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    Color RGBIMG = new Color(input.getRGB(i, j));
                    int redI = RGBIMG.getRed();
                    png[i][j] = redI;
                }
            }
            int [] edges = Functions.Obrez (png, height, width);
            System.out.println(edges[0] +" "+ edges[1] +" "+ edges[2] +" "+ edges[3]);
            int [][] newpng = Functions.Pereopredelenie(png, edges[0], edges[1], edges[2], edges[3]);
            BufferedImage image = new BufferedImage(edges[1] - edges[0], edges[3] - edges[2],
                    BufferedImage.TYPE_3BYTE_BGR);
            for (int i = 0; i < edges[1] - edges[0]; i++) {
                for (int j = 0; j < edges[3] - edges[2]; j++) {
                    Color RGBIMG = new Color(newpng[i][j],newpng[i][j],newpng[i][j]);
                    image.setRGB(i, j, RGBIMG.getRGB());
                    //image.setRGB(i, j, (1 - png[i][j] * 0xFFFFFFFF));
                }
            }
            ImageIO.write(image, "PNG", new FileOutputStream("./bitmap-mis.png"));
            //TODO{
            int [] finds = Functions.findchips(newpng, size);
            for (int Chups = 0; Chups < size; Chups++) {
                System.out.println( finds[Chups*2]+" "+ finds[Chups*2+1]);
                int [][] lastObrez = Functions.Pereopredelenie(newpng, finds[Chups*2]+3, finds[Chups*2+1]-3, 0 , newpng[0].length);
                //-----------------------------------------------------------
                BufferedImage imagens = new BufferedImage (finds[Chups*2+1] - finds[Chups*2] - 6, newpng[0].length,
                        BufferedImage.TYPE_3BYTE_BGR);
                for (int i = 0; i < finds[Chups*2+1] - finds[Chups*2]-6; i++) {
                    for (int j = 0; j < newpng[0].length; j++) {
                        Color RGBIMG1 = new Color(lastObrez[i][j],lastObrez[i][j],lastObrez[i][j]);
                        imagens.setRGB(i, j, RGBIMG1.getRGB());
                        //image.setRGB(i, j, (1 - png[i][j] * 0xFFFFFFFF));
                    }
                }
                ImageIO.write(imagens, "PNG", new FileOutputStream("./bitmp-mis"+Chups+".png"));

                //-----------------------------------------------------------
                System.out.println(lastObrez.length+" "+ lastObrez[0].length);
                int Lwidth = lastObrez.length;
                int Lheight = lastObrez[0].length;
                int[] est = Functions.ReadLinesLeft(lastObrez, Lheight, Lwidth, count, y);

                int m = 0;
                for (int j = 0; j < Lheight; j++) {
                    for (int i = 0; i < Lwidth / 2; i++) {
                        if (lastObrez[i][j] <= 207) {
                            if (est[j] == 0) {
                                points[m].Sum(lastObrez[i][j]);
                            }
                        }
                        if (j < Lheight - 1 && j > 2 && est[j] == 255 && est[j - 1] == 0) {
                            j++;
                            m++;
                        }
                    }
                }

                count = 0;
                y = 0;
                est = Functions.ReadLinesRight(lastObrez, Lheight, Lwidth, count, y);

                for (int j = 0; j < Lheight; j++) {
                    for (int i = Lwidth / 2; i < Lwidth; i++) {
                        if (lastObrez[i][j] <= 200) {
                            if (est[j] == 0) {
                                points[m].Sum(lastObrez[i][j]);
                            }
                        }
                        if (j < Lheight - 1 && j > 2 && est[j] == 255 && est[j - 1] == 0) {
                            j++;
                            m++;
                        }

                    }
                }
                for (int k = 0; k != m; k++) {
                    points[k].Result(k);
                }
                //}TODO
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
