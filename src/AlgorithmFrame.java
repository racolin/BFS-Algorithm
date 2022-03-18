import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class AlgorithmFrame extends JPanel {
    Queue<Integer> queue = new LinkedList<>();
    JButton[][] square;
    int rows, cols, count = 0, old;
    // count = 1 thì chọn điểm bắt đầu, =2 thì chọn điểm kết thúc, =3 thì chọn vật cản
    // old lưu ô vừa đi đi qua
    static int NUMBERSTART = -1, NUMBEREND = -2, NUMBERWALL = -3, NUMBERDEFAULT = -4;
    int width, height, valueOfGoal;
    static String brick = "src/images/brick.jpg",
            car = "src/images/car.jpg",
            goal = "src/images/goal.jpg";
    int result = -5;
    JPanel queueJPane;
    JLabel[][] lbl;
    boolean notFound = false;
    // find: đã tìm thấy đường đi hay chưa

    AlgorithmFrame(int rows, int cols, int width, int height) {

        this.width = width;
        this.height = height;

        this.rows = rows;
        this.cols = cols;

        square = new JButton[rows][cols];

        add(new JLabel());
        JLabel[] lbr = new JLabel[cols];
        JLabel[] lbc = new JLabel[rows];
        for (int i = 0; i < cols; i++) {
            lbr[i] = new JLabel(String.valueOf(i));
            lbr[i].setHorizontalAlignment(JLabel.CENTER);
            add(lbr[i]);
        }
        lbl = new JLabel[rows][cols];
        for (int i = 0; i < rows; i++) {

            lbc[i] = new JLabel(String.valueOf(i));
            lbc[i].setHorizontalAlignment(JLabel.CENTER);

            add(lbc[i]);

            for (int j = 0; j < cols; j++) {

                lbl[i][j] = new JLabel("(x, y)=>(" + i + ", " + j + ")");

                square[i][j] = new JButton();
                square[i][j].setMnemonic(NUMBERDEFAULT);
                fillBackground(square[i][j], Color.WHITE);
                add(square[i][j]);

                int finalJ = j;
                int finalI = i;
                square[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (count != -1) {
                            count++;
                            if (count == 1) {
                                square[finalI][finalJ].setMnemonic(NUMBERSTART);
//                            fillBackground(square[finalI][finalJ], Color.GREEN);
                                old = finalI * cols + finalJ;
                                queue.offer(old);
                                square[finalI][finalJ].setIcon(fitImage(car));
                                queueJPane.add(lbl[finalI][finalJ]);
                            }
                            else {
                                if (count == 2) {
                                    square[finalI][finalJ].setMnemonic(NUMBEREND);
//                                fillBackground(square[finalI][finalJ], Color.RED);
                                    square[finalI][finalJ].setIcon(fitImage(goal));
                                    valueOfGoal = finalI * cols + finalJ;
                                }
                                else {
                                    square[finalI][finalJ].setMnemonic(NUMBERWALL);
//                                fillBackgroheightund(square[finalI][finalJ], Color.DARK_GRAY);
                                    square[finalI][finalJ].setIcon(fitImage(brick));
                                }
                            }
                        }
                    }
                });
            }
        }
        setSize(width, height);
        setLayout(new GridLayout(rows + 1, cols + 1));
    }

    public void setQueueJPane(JPanel p) {
        queueJPane = p;
    }

    private ImageIcon fitImage(String path) {
        ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width / cols, height / rows,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }

    public void clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                square[i][j].setBackground(Color.WHITE);
                square[i][j].setMnemonic(NUMBERDEFAULT);
                count = 0;
                queue.clear();
                square[i][j].setIcon(null);
            }
        }
        queueJPane.removeAll();
        result = -5;
        notFound = false;
    }

    private void fillBackground(JButton btn, Color color) {
        btn.setBackground(color);
    }

    private void block(JButton btn) {
        btn.setBackground(Color.YELLOW);
    }

    private void added(JButton btn) {
        btn.setBackground(Color.CYAN);
    }

    public boolean oneMove() {
        if (!queue.isEmpty()) {

            // Khoá ô hiện tại được chọn(Yellow) vì nó đã được đi qua
            block(square[old / cols][old % cols]);
            // Cập nhật ô hiện tại được chọn
            old = queue.poll();

            queueJPane.remove(lbl[old / cols][old % cols]);
            // x, y là chia toạ độ từ old
            int x = old / cols, y = old % cols;

            // Chọn ô đang được xét đến(Green)
            fillBackground(square[x][y], Color.GREEN);
            if (square[old / cols][old % cols].getMnemonic() == result) {
                JOptionPane.showMessageDialog(null, "Đã đến đích!");
                notFound = true;
                showRoute();
                return true;
            }

            // Kiểm tra toạ độ đó đã được đi chưa, nếu chưa thì đưa vào queue
            // Nếu là vị trí cuối cùng thì dẫn đường
            checkAndMove(x - 1, y);
            checkAndMove(x, y - 1);
            checkAndMove(x + 1, y);
            checkAndMove(x, y + 1);
        }
        else {
//            if (square[old / cols][old % cols].getMnemonic() != NUMBEREND) {
                notFound = true;
                JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi!");
//            }
        }
        return false;
    }

    private void showRoute() {
        int cur = result;
        while (square[cur / cols][cur % cols].getMnemonic() != NUMBERSTART) {
            square[cur / cols][cur % rows].setBackground(Color.LIGHT_GRAY);
            cur = square[cur / cols][cur % rows].getMnemonic();
        }
    }

    private void checkAndMove(int x, int y) {
        if (canMove(x, y)) {
            if (square[x][y].getMnemonic() == NUMBEREND) {
                result = old;
            }
            square[x][y].setMnemonic(old);
            queue.offer(x * cols + y);
            queueJPane.add(lbl[x][y]);
            added(square[x][y]);
        }
    }

    private boolean canMove(int x, int y) {
        return x < rows && y < cols && x > -1 && y > -1 && square[x][y].getMnemonic() != NUMBERWALL
                && square[x][y].getMnemonic() < 0 && square[x][y].getMnemonic() != NUMBERSTART;
    }

    public boolean oneClass() {
        int len = queue.size();
        if (len == 0 && !notFound) {
            notFound = true;
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi!");
        }
        else {
            for (int i = 0; i < len; i++) {
                if (oneMove()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void fast() {
        while (!queue.isEmpty()) {
            if (oneClass()) {
                break;
            }
        }
        if (queue.isEmpty() && !notFound) {
            notFound = true;
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi!");
        }
    }
}
