public class Functions {


    public static int otstup = 6;

    public static int[] ReadLinesLeft(int png[][], int height, int width, int count, int y) {
        int[] est = new int[height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width / 2; i++) {


                if (png[i][j] <= 255 && png[i][j] >= 207) count++;
                if (i == width / 2 - 1) {
                    if (count >= width / 2) {
                        est[y] = 255;
                        y++;
                        count = 0;
                    } else {

                        est[y] = 0;
                        y++;
                        count = 0;
                    }
                }
            }
        }
        return est;
    }

    public static int[] ReadLinesRight(int png[][], int height, int width, int count, int y) {
        int[] est = new int[height];
        for (int j = 0; j < height; j++) {
            for (int i = width / 2; i < width; i++) {

                if (png[i][j] <= 255 && png[i][j] >= 200) count++;
                if (i == width - 1) {
                    if (count >= width / 2) {
                        est[y] = 255;
                        y++;
                        count = 0;

                    } else {

                        est[y] = 0;
                        y++;
                        count = 0;

                    }
                }
            }
        }
        return est;
    }

    public static int[][] Pereopredelenie(int[][] pickcha, int left, int right, int up, int down) {
        int[][] newpickcha = new int[right - left][down - up];
        for (int i = up, i1 = 0; i < down; i++, i1++) {
            for (int j = left, j1 = 0; j < right; j++, j1++) {
               // System.out.println(i + " " + j + " " + pickcha[j][i]);
                newpickcha[j1][i1] = pickcha[j][i];
            }
        }
        return newpickcha;
    }

    public static int[] Obrez(int[][] pickcha, int height, int width) {

        //снизу вверх слева направо
        int findup = 5000;
        int finddown = 0;
        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j > 0; j--) {
                if (pickcha[i][j] <= 255 && pickcha[i][j] >= 200) {
                    if (findup > j) findup = j;
                    if (finddown < j) finddown = j;
                    break;
                }
            }
        }
        finddown -= otstup;
        findup -= otstup;
        //слева направо_сверху вниз
        int findleft = 0;
        for (int j = 0; j < finddown; j++) {
            for (int i = 0; i < width; i++) {
                if (pickcha[i][j] <= 255 && pickcha[i][j] >= 200) {
                    if (findleft < i) findleft = i;
                    break;
                }
            }
        }
        //справа на лево_сверху вниз
        int findright = 5000;
        for (int j = 0; j < finddown; j++) {
            for (int i = width - 1; i > 0; i--) {
                if (pickcha[i][j] <= 255 && pickcha[i][j] >= 200) {
                    if (findright > i) findright = i;
                    break;
                }
            }
        }

        int[] allstats = new int[]{findleft, findright, findup, finddown};
        return allstats;
    }


    public static int[] findchips(int[][] png, int size) {
        int sizes = 0;
        int otstup = 5;
        int[] finds = new int[10];
        int weight = png.length;
        finds[0] = 0;
        while (sizes < size - 1) {
            for (int i = finds[sizes*2]; i < weight; i++) {
                if (png[i][otstup] <= 200) {
                    finds[sizes*2+1] = i - 1;
                    break;
                }
            }
            for (int j = finds[sizes*2+1] + 1; j < weight; j++) {
                if (png[j][otstup] >= 200) {
                    finds[sizes*2+2] = j;
                    break;
                }
            }
            sizes ++;
        }
        finds [size*2 - 1] = png.length;
        for (int g = 0; g<size*2; g++) System.out.println(finds[g]);
        System.out.println("////////////////////////////////////////"+ png.length);
        return finds;
    }
}
