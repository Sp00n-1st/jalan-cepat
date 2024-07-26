import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JalanCepat {
    static int[] koordinatX = { 0, 0, -1, 1 };
    static int[] koordinatY = { -1, 1, 0, 0 };
    static String[] arah = { "kiri", "kanan", "atas", "bawah" };

    public static void main(String[] args) {
        List<List<Character>> pola = new ArrayList<>();
        while (true) {
            String input = System.console().readLine();
            if (input.equalsIgnoreCase("ok")) {
                break;
            }

            pola.add(input.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }

        System.out.println(jalurTercepat(pola, getPoint(pola, '^'), getPoint(pola, '*')));
    }

    private static Point getPoint(List<List<Character>> pola, Character flag) {
        for (int i = 0; i < pola.size(); i++) {
            for (int j = 0; j < pola.get(i).size(); j++) {
                if (pola.get(i).get(j).equals(flag)) {
                    return new Point(i, j, 0, "");
                }
            }
        }
        return null;
    }

    private static String jalurTercepat(List<List<Character>> pola, Point start, Point end) {
        boolean[][] ditemukan = new boolean[pola.size()][pola.get(0).size()];
        List<Point> data = new ArrayList<>();
        data.add(start);

        while (!data.isEmpty()) {
            Point point = data.remove(0);

            if (point.x == end.x && point.y == end.y) {
                return formatJalur(point.jalur, point.jarak);
            }

            for (int i = 0; i < 4; i++) {
                int newX = point.x + koordinatX[i];
                int newY = point.y + koordinatY[i];

                if (gerakanValid(pola, newX, newY, ditemukan)) {
                    ditemukan[newX][newY] = true;
                    data.add(new Point(newX, newY, point.jarak + 1, point.jalur + arah[i] + " "));
                }
            }
        }

        return "Tidak Aja Jalan";
    }

    private static boolean gerakanValid(List<List<Character>> pola, int x, int y, boolean[][] ditemukan) {
        return x >= 0 && y >= 0 && x < pola.size() && y < pola.get(0).size() &&
                pola.get(x).get(y) != '#' && !ditemukan[x][y];
    }

    private static String formatJalur(String jalur, int jarak) {
        String[] gerakan = jalur.trim().split(" ");
        StringBuilder formatJalur = new StringBuilder();
        int count = 1;

        for (int i = 1; i < gerakan.length; i++) {
            if (gerakan[i].equals(gerakan[i - 1])) {
                count++;
            } else {
                formatJalur.append(count).append(" ").append(gerakan[i - 1]).append("\n");
                count = 1;
            }
        }
        formatJalur.append(count).append(" ").append(gerakan[gerakan.length - 1]).append("\n");
        formatJalur.append(jarak).append(" langkah");

        return formatJalur.toString();
    }
}

class Point {
    int x;
    int y;
    int jarak;
    String jalur;

    public Point(int x, int y, int jarak, String jalur) {
        this.x = x;
        this.y = y;
        this.jarak = jarak;
        this.jalur = jalur;
    }
}
