import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Control extends JPanel {
    JButton start, clear, oneMove, oneClass, fast, confirm;
    JTextField startX, startY, endX, endY;
    JLabel lX, lY;
    JPanel queue;
    Control() {
        Border borderQueue = BorderFactory.createTitledBorder("Queue");
        start = new JButton("Bắt đầu");
        clear = new JButton("Mới");
        oneMove = new JButton("Một bước");
        oneClass = new JButton("Một lớp");
        fast = new JButton("Nhanh");
        confirm = new JButton("Xác nhận");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(start);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(clear);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(oneMove);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(oneClass);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(fast);
        queue = new JPanel();
        queue.setLayout(new BoxLayout(queue, BoxLayout.Y_AXIS));
        queue.setBorder(borderQueue);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(queue);
    }
}
