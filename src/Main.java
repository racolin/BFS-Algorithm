import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BFS Algorithm");

        Control control = new Control();

        AlgorithmFrame algorithmFrame = new AlgorithmFrame(15, 15, 900, 900);
        algorithmFrame.setQueueJPane(control.queue);

        frame.add(algorithmFrame, BorderLayout.CENTER);
        frame.setSize(1150, 1200);
        frame.setVisible(true);

        control.oneMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.oneMove();
                algorithmFrame.count = -1;
            }
        });

        control.oneClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.oneClass();
                algorithmFrame.count = -1;
            }
        });

        control.fast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.fast();
                algorithmFrame.count = -1;
            }
        });

        control.clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algorithmFrame.clear();
            }
        });

        frame.add(control, BorderLayout.WEST);
    }
}
